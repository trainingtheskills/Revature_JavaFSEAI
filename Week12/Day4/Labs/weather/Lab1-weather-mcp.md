<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/d2db184e-5aea-41c5-872a-d6d09ff56528" />

### weather-mcp-server.py
```

import requests
from mcp.server.fastmcp import FastMCP

# Initialize FastMCP Server
mcp = FastMCP("WeatherServer")


def weather(city: str) -> dict:

    # Step 1: Convert city name into latitude and longitude
    geo_url = "https://geocoding-api.open-meteo.com/v1/search"

    geo_response = requests.get(
        geo_url,
        params={
            "name": city,
            "count": 1,
            "language": "en",
            "format": "json"
        },
        timeout=15
    )

    geo_response.raise_for_status()
    geo_data = geo_response.json()

    if not geo_data.get("results"):
        return {"error": f"City '{city}' not found"}

    # Extract geographical coordinates
    location = geo_data["results"][0]

    lat = location["latitude"]
    lon = location["longitude"]

    city_name = location["name"]

    # Step 2: Fetch current weather using Open-Meteo API
    weather_url = "https://api.open-meteo.com/v1/forecast"

    weather_response = requests.get(
        weather_url,
        params={
            "latitude": lat,
            "longitude": lon,
            "current": (
                "temperature_2m,"
                "relative_humidity_2m,"
                "wind_speed_10m"
            ),
            "temperature_unit": "celsius",
            "wind_speed_unit": "kmh",
            "timezone": "auto"
        },
        timeout=15
    )

    weather_response.raise_for_status()

    weather_data = weather_response.json()

    # Step 3: Extract current weather measurements
    current = weather_data["current"]

    # Step 4: Prepare weather information
    return {
        "city": city_name,
        "temperature": f"{current['temperature_2m']}°C",
        "humidity": f"{current['relative_humidity_2m']}%",
        "wind_speed": f"{current['wind_speed_10m']} km/h",
        "time": current["time"]
    }


# Step 5: Register the tool with MCP Server
@mcp.tool()
def get_weather(city: str) -> dict:
    """
    Get current temperature, humidity and wind speed
    for a specified city.
    """
    try:
        data = weather(city)
        return data

    except requests.RequestException as e:
        return {"error": f"Weather API request failed: {str(e)}"}

    except (KeyError, IndexError, TypeError) as e:
        return {"error": f"Unexpected weather API response: {str(e)}"}


# Step 6: Start MCP server using STDIO transport
if __name__ == "__main__":
    mcp.run(transport="stdio")
```

### weather-mcp-client.py
```
import asyncio
import sys
import os

from pathlib import Path
from dotenv import load_dotenv

from langchain_openai import ChatOpenAI
from langchain_mcp_adapters.client import MultiServerMCPClient
from langchain_core.messages import ToolMessage

# Load environment variables
load_dotenv()


async def main():

    # Read OpenAI API Key from .env
    OPENAI_API_KEY = os.getenv("OPENAI_API_KEY")

    if not OPENAI_API_KEY:
        raise ValueError("OPENAI_API_KEY not found in .env file")

    # Initialize OpenAI LLM
    llm = ChatOpenAI(
        model="gpt-4o-mini",
        api_key=OPENAI_API_KEY
    )

    # MCP server configuration
    config = {
        "weather-server": {
            "command": sys.executable,
            "args": [
                str(
                    Path(__file__).with_name(
                        "weather-mcp-server.py"
                    )
                )
            ],
            "transport": "stdio"
        }
    }

    print("Connecting to MCP Server via MultiServerMCPClient...")

    client = MultiServerMCPClient(config)

    # Discover MCP tools
    mcp_tools = await client.get_tools()

    print("\nAvailable MCP Tools:")

    for tool in mcp_tools:
        print(tool.name)

    # Bind MCP tools to OpenAI LLM
    llm_with_tools = llm.bind_tools(mcp_tools)

    user_query = (
        "What are the current weather conditions "
        "in Pune right now? Include temp    erature, "
        "humidity and wind speed."
    )

    print(f"\nUser: {user_query}")

    # Step 1: Ask LLM to select an appropriate tool
    response = await llm_with_tools.ainvoke(user_query)

    print("\nAI Tool Selection:")

    # Step 2: Execute selected MCP tools
    if response.tool_calls:

        tool_messages = []

        for tool_call in response.tool_calls:

            print(f"\nExecuting Tool: {tool_call['name']}")
            print(f"Arguments: {tool_call['args']}")

            selected_tool = next(
                tool for tool in mcp_tools
                if tool.name == tool_call["name"]
            )

            # Execute MCP tool
            tool_result = await selected_tool.ainvoke(
                tool_call["args"]
            )

            print("\nWeather Tool Result:")
            print(tool_result)

            # Prepare tool response for LLM
            tool_messages.append(
                ToolMessage(
                    content=str(tool_result),
                    tool_call_id=tool_call["id"]
                )
            )

        # Step 3: Send tool results back to LLM
        messages = [
            {"role": "user", "content": user_query},
            response,
            *tool_messages
        ]

        # Step 4: Generate final response
        final_response = await llm.ainvoke(messages)

        print("\nFinal AI Response:")
        print(final_response.content)

    else:
        print("\nAI Response:")
        print(response.content)


if __name__ == "__main__":
    asyncio.run(main())
```
