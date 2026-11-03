 # Langgraph

## 1. What is LangGraph?
LangGraph is an open-source framework (from the LangChain team) for building stateful, 
multi-step LLM applications and agents as a graph: 
nodes do the work, edges decide what runs next, and a shared state flows through everything.

<img width="307" height="185" alt="image" src="https://github.com/user-attachments/assets/ff2d2e03-14c7-4035-9c67-1cc993d1635f" />

Langgraph orchestrate the workflow as graph choosing right path at every state until goal is achieved.

## How Langgraph works?

<img width="547" height="265" alt="image" src="https://github.com/user-attachments/assets/67e80239-1877-4653-ba39-faa11983ecbc" />

## Why it exist?

A simple **LangChain** chain is a straight line: **prompt → model → parser**.

Real agents need more: loops (retry until the answer is good), branches (route by intent), pauses (wait for a human), and memory (resume tomorrow). A linear chain cannot express these cleanly. 

**LangGraph** models the workflow explicitly as a graph so control flow is code you can see, test and debug, not something hidden inside a prompt.


