
# First State

## Problem " Building a Single-Node Autonomous AI Topic Generator Using LangGrap "

<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/557232d8-e291-44c8-8ded-456da65dc338" />


```
import os
from typing import TypedDict
from dotenv import load_dotenv
from langchain_openai import ChatOpenAI
from langgraph.graph import StateGraph, START, END

# 1. Load Environment & Initialize LLM
load_dotenv()

if not os.environ.get("OPENAI_API_KEY"):
    raise RuntimeError("OPENAI_API_KEY is missing. Add it to your .env file.")

llm = ChatOpenAI(
    model="gpt-4o-mini",
    temperature=0
)

# 2. Define the LangGraph State
class State(TypedDict):
    topic: str

# 3. Define the Node Function
def topic_generator(state):
    topic = llm.invoke("Generate an AI topic").content
    return {"topic": topic}

# 4. Build the Workflow Graph
builder = StateGraph(State)

# Add Node
builder.add_node("topic_generator", topic_generator)

# Add Edges to control flow
builder.add_edge(START, "topic_generator")
builder.add_edge("topic_generator", END)

# Compile Graph
graph = builder.compile()

# 5. Run the Graph
result = graph.invoke({})
print(result)
```
