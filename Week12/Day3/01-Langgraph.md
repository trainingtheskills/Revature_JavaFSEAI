 # Langgraph

## 1. What is LangGraph?
LangGraph is an open-source framework (from the LangChain team) for building stateful, 
multi-step LLM applications and agents as a graph: 
nodes do the work, edges decide what runs next, and a shared state flows through everything.

<img width="307" height="185" alt="image" src="https://github.com/user-attachments/assets/ff2d2e03-14c7-4035-9c67-1cc993d1635f" />

Langgraph orchestrate the workflow as graph choosing right path at every state until goal is achieved.
**One-line mental model.** LangGraph = a state machine for LLM workflows. Define a State, write Nodes (Python functions) that read and update it, connect them with Edges (fixed or conditional), compile, and run.

| Term | Plain meaning | Analogy |
| --- | --- | --- |
| State | Shared data passed between steps | A case file moving through a bank department |
| Node | A function that does one step (call LLM, call tool, validate) | A desk where one clerk works |
| Edge | Which node runs next | The arrow on the process flowchart |
| Conditional edge | A function that picks the next node at runtime | A manager deciding: approve, reject or escalate |
| Checkpointer | Saves state after every step | Case file saved so work resumes later |

## How Langgraph works?

<img width="547" height="265" alt="image" src="https://github.com/user-attachments/assets/67e80239-1877-4653-ba39-faa11983ecbc" />

## Why it exist?

A simple **LangChain** chain is a straight line: **prompt → model → parser**.

Real agents need more: loops (retry until the answer is good), branches (route by intent), pauses (wait for a human), and memory (resume tomorrow). A linear chain cannot express these cleanly. 

**LangGraph** models the workflow explicitly as a graph so control flow is code you can see, test and debug, not something hidden inside a prompt.

## 2. Core concepts

**State.** A typed schema (TypedDict, dataclass or Pydantic model) that every node reads from and writes to. Nodes return only the keys they want to change; LangGraph merges the update into the state.

**Reducers.** Rules for merging an update into a key. By default a new value overwrites the old one. With a reducer such as `add_messages` or `operator.add`, values are appended instead. This is how a conversation history grows and how parallel branches combine results safely.

**Nodes.** Plain functions `f(state) -> partial_state_update`. A node may call an LLM, a tool, a database or ordinary code.

**Edges.**

| Edge type | Use | API |
| --- | --- | --- |
| Normal | Always go from A to B | `add_edge("A", "B")` |
| Conditional | Routing function returns the next node name | `add_conditional_edges("A", router)` |
| Entry / exit | Where the graph starts and ends | `START`, `END` |
| Dynamic | Node returns a `Command` to update state and choose the next node at once | `Command(goto=..., update=...)` |

**Compile.** `graph.compile()` validates the graph and returns a runnable. Options include a `checkpointer` (persistence) and `interrupt_before` / `interrupt_after` (pause points).

**Checkpointer and thread.** After each step, the state is saved under a `thread_id`. Invoking again with the same thread\_id continues that conversation or workflow. This enables memory, crash recovery, human approval and time travel. Use `InMemorySaver` for development and a database-backed saver (Postgres, SQLite) for production.

**Super-step.** Nodes that can run at the same time execute in one step; the state is updated and checkpointed at the end of each step.



## Langchain Vs LangGraph

<img width="716" height="389" alt="image" src="https://github.com/user-attachments/assets/aa35b80e-4bdc-45a4-8b97-0db72524669f" />


## 3. How it works: build a graph in six steps

Install: `pip install langgraph langchain-openai` (any chat model provider works).



