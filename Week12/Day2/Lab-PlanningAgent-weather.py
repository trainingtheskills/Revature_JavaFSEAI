# Write a weather comparision agent using planning Agent

# How Does it works ?
User goal
   │
   ▼
PHASE 1  PLANNER     LLM writes the whole plan first (no tools)
   │                 1. Retrieve Pune weather
   │                 2. Retrieve Mumbai weather
   │                 3. Extract both temperatures
   │                 4. Compare  5. Conclude
   ▼
PHASE 2  EXECUTOR    An agent runs ONE step at a time,
   │                 calling get_weather when needed,
   │                 and carrying earlier results forward
   ▼
PHASE 3  SUMMARIZER  LLM writes the final answer from the step results

The main pieces

Plan (Pydantic model): forces the planner to return a clean list of steps instead of free text.
create_plan(): the planner call, which sees the goal and the available tool but runs nothing.
execute_step(): gives the executor agent the goal, the current step and the earlier results, so step 4 can compare the temperatures found in steps 1-2.
run_planning_agent(): the plan → execute → conclude pipeline, printing each phase so students can see it.
```
# --------------------------------------------------
# Example: Planning Agent using LangChain
# Topic: Plan first, then execute the plan
# --------------------------------------------------
#
# Flow:   User goal -> PLANNER (writes the plan)
#                   -> EXECUTOR (runs each step, using tools)
#                   -> SUMMARIZER (writes the final conclusion)
#
# Setup:
#   pip install -U langchain langchain-openai python-dotenv
#   .env file:  OPENAI_API_KEY=sk-...

# Step 1: Import required libraries
import os
from typing import List

from dotenv import load_dotenv
from pydantic import BaseModel, Field
from langchain.agents import create_agent
from langchain.chat_models import init_chat_model

# Step 2: Load environment variables from .env
load_dotenv()

if not os.getenv("OPENAI_API_KEY"):
    raise ValueError("OPENAI_API_KEY not found in .env file")

print("OpenAI API Key loaded successfully!")

# Change this if your account has access to another model (e.g. "openai:gpt-5")
MODEL = "openai:gpt-4o-mini"


# --------------------------------------------------
# Step 3: Define a Tool (Python Function)
# --------------------------------------------------

def get_weather(city: str) -> str:
    """
    Get the weather information for a given city.

    Args:
        city: Name of the city.

    Returns:
        Weather information as a string, including the temperature.
    """

    print(f"    [TOOL EXECUTION] Getting weather for {city}...")

    # Mock weather data
    weather_data = {
        "pune": "Sunny, 32°C",
        "mumbai": "Cloudy, 29°C",
        "delhi": "Rainy, 25°C",
        "bangalore": "Partly cloudy, 27°C",
    }

    weather = weather_data.get(
        city.lower(),
        "Weather information is not available for this city.",
    )

    return f"Weather in {city}: {weather}"


# --------------------------------------------------
# Step 4: Define the shape of a Plan
# --------------------------------------------------
# The planner must return a list of short, ordered steps.

class Plan(BaseModel):
    steps: List[str] = Field(
        description="Ordered list of short steps needed to achieve the goal"
    )


# --------------------------------------------------
# Step 5: PLANNER - the LLM writes the plan (no tools used here)
# --------------------------------------------------

planner_llm = init_chat_model(MODEL).with_structured_output(Plan)


def create_plan(goal: str) -> List[str]:
    prompt = f"""
You are a planning assistant.

Goal: {goal}

Available tool: get_weather(city) -> returns the weather and temperature of one city.

Write a short, numbered-style plan (3 to 6 steps) to achieve the goal.
Each step must be one clear action. Do not execute anything yet.
"""
    plan = planner_llm.invoke(prompt)
    return plan.steps


# --------------------------------------------------
# Step 6: EXECUTOR - an agent that carries out ONE step at a time
# --------------------------------------------------

executor = create_agent(
    model=MODEL,
    tools=[get_weather],
    system_prompt="""
    You are a task executor.

    You will be given ONE step of a larger plan, plus the results of earlier steps.
    1. Do only the current step.
    2. Use the get_weather tool whenever weather data is needed. Do not guess.
    3. Reply with a short result for this step only.
    """,
)


def execute_step(goal: str, step: str, previous_results: List[str]) -> str:
    context = "\n".join(previous_results) if previous_results else "None yet"

    message = f"""
Overall goal: {goal}

Results so far:
{context}

Current step to execute: {step}
"""
    result = executor.invoke({"messages": [{"role": "user", "content": message}]})
    return result["messages"][-1].content


# --------------------------------------------------
# Step 7: SUMMARIZER - the LLM writes the final conclusion
# --------------------------------------------------

summarizer_llm = init_chat_model(MODEL)


def summarize(goal: str, results: List[str]) -> str:
    prompt = f"""
Goal: {goal}

Results of every executed step:
{chr(10).join(results)}

Using only these results, give the user a clear final answer.
"""
    return summarizer_llm.invoke(prompt).content


# --------------------------------------------------
# Step 8: The Planning Agent = plan, then execute, then summarize
# --------------------------------------------------

def run_planning_agent(goal: str) -> str:

    # PHASE 1: PLAN
    print("\nPHASE 1: PLANNING")
    print("-" * 50)
    steps = create_plan(goal)
    for number, step in enumerate(steps, start=1):
        print(f"  {number}. {step}")

    # PHASE 2: EXECUTE
    print("\nPHASE 2: EXECUTION")
    print("-" * 50)
    results = []
    for number, step in enumerate(steps, start=1):
        print(f"\n  Executing step {number}: {step}")
        output = execute_step(goal, step, results)
        print(f"  Result: {output}")
        results.append(f"Step {number} ({step}): {output}")

    # PHASE 3: CONCLUDE
    print("\nPHASE 3: FINAL ANSWER")
    print("-" * 50)
    return summarize(goal, results)


# --------------------------------------------------
# Step 9: Send a user request to the Planning Agent
# --------------------------------------------------

if __name__ == "__main__":
    user_input = "Compare the weather in Pune and Mumbai and tell me which city is hotter."

    print("\nUSER REQUEST")
    print("-" * 50)
    print(user_input)

    answer = run_planning_agent(user_input)
    print(answer)
  ```

## Points

You can review the plan before anything runs, so it’s easy to add an approval step between Phase 1 and 2.
It uses gpt-4o-mini by default. Change MODEL if you want gpt-5.
This simple version doesn’t replan when a step fails. A good extension exercise is to add a check after each step that calls the planner again on failure.
