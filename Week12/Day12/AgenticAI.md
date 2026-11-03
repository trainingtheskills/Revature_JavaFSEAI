# What is Agentic AI?

An AI agent is a system where an LLM doesn’t just answer a prompt. It pursues a goal by deciding what to do next, taking actions through tools, observing the results,
and repeating until the task is done.

## The core loop

Perceive: take in the goal plus the current context (user request, memory, tool results).
Reason/plan: the LLM decides the next step.
Act: call a tool (API, database, code execution, search, file system).
Observe: read the result and feed it back into context.
Repeat or stop: continue until the goal is met or a stop condition is hit.

## Main components

Model (the brain): the LLM doing the reasoning.
Tools: functions or APIs the agent can call, often exposed via MCP.
Memory: short-term (the context window) and long-term (vector store, files, databases).
Instructions/guardrails: system prompt, permissions, validation, human-in-the-loop checkpoints.


## Agent vs. chatbot vs. workflow

	Who decides the steps?	Example
Chatbot / single LLM call	None. One prompt in, one response out	“Summarize this text”
Workflow	Developer, with a fixed code path (LLMs may fill in steps)	Prompt chaining, routing, parallelization
Agent	The LLM, dynamically at runtime	“Investigate why order #123 failed and fix it”


Exmaple:
**Banking example:** a loan-review agent receives an application, calls a credit-score API, queries the customer’s DB2 records, checks policy documents via RAG, finds a missing document, drafts a request to the customer, and escalates to a human underwriter when confidence is low. No one scripted that exact sequence.

**When to use one:** open-ended problems where the steps can’t be predicted. Otherwise, prefer a simpler workflow. Agents cost more, run slower, and can compound errors, so start with the simplest thing that works and add autonomy only when needed.

**Common patterns:** prompt chaining, routing, parallelization, orchestrator-workers, evaluator-optimizer, and multi-agent systems.

**Banking example:** a loan-review agent receives an application, calls a credit-score API, queries the customer’s DB2 records, checks policy documents via RAG, finds a missing document, drafts a request to the customer, and escalates to a human underwriter when confidence is low. No one scripted that exact sequence.

**When to use one:** open-ended problems where the steps can’t be predicted. Otherwise, prefer a simpler workflow. Agents cost more, run slower, and can compound errors, so start with the simplest thing that works and add autonomy only when needed.

**Common patterns:** prompt chaining, routing, parallelization, orchestrator-workers, evaluator-optimizer, and multi-agent systems.
