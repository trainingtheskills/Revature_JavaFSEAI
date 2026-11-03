
<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/f4803bd1-e7ef-4e31-bc7a-eb24a0b37586" />



<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/74dbe21b-06ad-46e6-9861-b986693b5f02" />


```
# --------------------------------------------------
# Example: Creating an AI Agent using LangChain
# Topic: Agent + Tool Calling
# --------------------------------------------------

# Step 1: Import required libraries
import os
from dotenv import load_dotenv
from langchain.agents import create_agent

# Step 2: Load environment variables from .env
load_dotenv()

# Step 3: Verify OpenAI API key
if not os.getenv("OPENAI_API_KEY"):
    raise ValueError("OPENAI_API_KEY not found in .env file")

print("OpenAI API Key loaded successfully!")


# --------------------------------------------------
# Step 4: Define a Tool (Python Function)
# --------------------------------------------------

def get_weather(city: str) -> str:
    """
    Get the weather information for a given city.

    Args:
        city: Name of the city.

    Returns:
        Weather information as a string.
    """

    print(f"\n[TOOL EXECUTION] Getting weather for {city}...")

    # Mock weather data
    weather_data = {
        "pune": "Sunny, 32°C",
        "mumbai": "Cloudy, 29°C",
        "delhi": "Rainy, 25°C",
        "bangalore": "Partly cloudy, 27°C"
    }

    # Retrieve weather information
    weather = weather_data.get(
        city.lower(),
        "Weather information is not available for this city."
    )

    return f"Weather in {city}: {weather}"


# --------------------------------------------------
# Step 5: Create the AI Agent
# --------------------------------------------------

agent = create_agent(

    # LLM model used as the brain of the agent
    model="openai:gpt-5",

    # Register available tools
    tools=[get_weather],

    # Define the agent's role and instructions
    system_prompt="""
    You are a helpful weather assistant.

    Your responsibilities:
    1. Understand the user's weather-related questions.
    2. Identify the city mentioned in the question.
    3. Use the get_weather tool to retrieve weather information.
    4. Do not guess weather information.
    5. Present the result in a simple and user-friendly format.
    """
)


# --------------------------------------------------
# Step 6: Send a user request to the Agent
# --------------------------------------------------

print("\nUSER REQUEST")
print("-" * 50)

user_input = "What is the weather like in Pune?"

print(user_input)


# --------------------------------------------------
# Step 7: Invoke the Agent
# --------------------------------------------------

result = agent.invoke(
    {
        "messages": [
            {
                "role": "user",
                "content": user_input
            }
        ]
    }
)


# --------------------------------------------------
# Step 8: Display the Agent's response
# --------------------------------------------------

print("\nAGENT RESPONSE")
print("-" * 50)

print(result["messages"][-1].content)


# --------------------------------------------------
# Step 9: Display the execution messages
# --------------------------------------------------

print("\nAGENT EXECUTION DETAILS")
print("-" * 50)

for message in result["messages"]:

    print("\nMessage Type:", type(message).__name__)

    # Display tool calls made by the LLM
    if getattr(message, "tool_calls", None):
        print("Tool Calls:", message.tool_calls)

    # Display tool execution result
    if getattr(message, "name", None):
        print("Tool Name:", message.name)
        print("Tool Result:", message.content)

```
