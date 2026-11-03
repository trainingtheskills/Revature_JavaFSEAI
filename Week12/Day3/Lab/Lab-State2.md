## Objective
 Orchestrate a multi-stage Content Generation Pipeline using a graph-based architecture via LangGraph. 
 
 <img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/7e0757bc-6306-4708-9d66-a3af310159f2" />

##Core Execution Steps

- State Structuring: Establishes a centralized TypedDict schema to share data across separate runtime contexts.
- Sequential Node Isolation: Modularizes execution boundaries into distinct functional components (topic_generator \(\rightarrow \) article_writer \(\rightarrow \) summarizer).
- Dependency Flow Routing: Configures edge trajectories to dynamically pass accumulated context from one Large Language Model (LLM) execution layer to the next.

```
import os
from typing import TypedDict
from dotenv import load_dotenv
from langchain_openai import ChatOpenAI
from langgraph.graph import StateGraph, START, END

# 1. Load Environment & Initialize OpenAI LLM
load_dotenv()

if not os.environ.get("OPENAI_API_KEY"):
    raise RuntimeError("OPENAI_API_KEY is missing. Add it to your .env file.")

llm = ChatOpenAI(
    model="gpt-4o-mini",
    temperature=0
)

# 2. Define the Shared State Schema
class State(TypedDict):
    topic: str
    article: str
    summary: str

# 3. Define the Node Functions
def topic_generator(state: State):
    # Generates the initial topic (overrides default if empty)
    print("--- Running Node: Topic Generator ---")
    topic = "Machine Learning"
    return {"topic": topic}

def article_writer(state: State):
    # Reads the topic from state and generates an article
    print("--- Running Node: Article Writer ---")
    topic = state["topic"]
    article = llm.invoke(f"Write about {topic}").content
    return {"article": article}

def summarizer(state: State):
    # Reads the generated article from state and creates a summary
    print("--- Running Node: Summarizer ---")
    article = state["article"]
    summary = llm.invoke(f"Summarize this\n{article}").content
    return {"summary": summary}

# 4. Build the Graph Workflow
builder = StateGraph(State)

# Add all execution nodes
builder.add_node("topic_generator", topic_generator)
builder.add_node("article_writer", article_writer)
builder.add_node("summarizer", summarizer)

# Set up the sequential edge pathways
builder.add_edge(START, "topic_generator")
builder.add_edge("topic_generator", "article_writer")
builder.add_edge("article_writer", "summarizer")
builder.add_edge("summarizer", END)

# Compile the final graph execution engine
graph = builder.compile()

# 5. Execute the Graph Workflow
print("Starting LangGraph Execution...\n")
result = graph.invoke({"topic": ""})

# Print individual properties from the final state payload
print("\n=== FINAL STATE OUTPUT ===")
print(f"Topic:   {result['topic']}")
print("-" * 40)
print(f"Summary:\n{result['summary']}")
```
