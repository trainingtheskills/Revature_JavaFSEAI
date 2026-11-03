# Multi-Step Reasoning in Agentic AI: A Practical Tutorial

**Audience:** developers who have built a basic tool-calling agent (such as the Reactive and Planning agents in this folder) and want to understand how agents solve problems that need many dependent steps.

**Stack used in examples:** Python, LangChain, OpenAI (`gpt-4o-mini`). The same ideas apply to Claude or any other LLM.

---

## Table of contents

1. Overview: what, when, business scenarios, why, how
2. Worked example: what multi-step reasoning looks like
3. Why a single LLM call is not enough
4. The core techniques (each with its own What / When / Scenarios / Why / How card)
   - 4.1 Chain-of-Thought (CoT)
   - 4.2 Prompt chaining
   - 4.3 ReAct (Reason + Act)
   - 4.4 Plan-and-Execute
   - 4.5 Self-consistency
   - 4.6 Reflection / self-critique
   - 4.7 Tree-of-Thought (overview)
5. Extended thinking (reasoning models)
6. Choosing the right technique
7. Failure modes and how to fix them
8. Evaluating multi-step agents
9. Hands-on lab: refund-eligibility agent
10. Exercises
11. Cheat sheet

---

## 1. Overview: what, when, business scenarios, why, how

### What it is

**Multi-step reasoning** is an AI system's ability to break a problem into an ordered series of intermediate steps, where each step uses the result of the previous one, and reach a final answer or complete a goal. The steps can be pure thinking (working through logic or arithmetic), actions (calling tools, reading data), or checks (validating or critiquing a result). In an agent, these steps are explicit, so they can be inspected, logged, and controlled.

### When to use it

Use multi-step reasoning when **any** of these is true:

- The answer depends on **several facts** that must be fetched separately (order, policy, customer history).
- Later steps **depend on earlier results** (you cannot compute a refund before you know the order amount).
- The task needs **exact calculation, comparison, or rules** applied in sequence.
- The right next step **depends on what you find** (investigation, troubleshooting, triage).
- The outcome is **high stakes**, so you want verifiable steps, checks, or human approval along the way.
- The work is **too big for one prompt** (long documents, multi-part reports, multi-system workflows).

Do **not** use it for simple lookups, single-fact questions, or one-shot rewriting and summarizing. A single well-written prompt is cheaper, faster, and more reliable there.

Comparison of Agent Patterns

| Pattern | Question it answers |
|---|---|
| **Reactive (ReAct)** | What should I do next, based on what I just saw? |
| **Planning** | What are all the steps, before I start? |
| **Human-in-the-Loop** | Who must approve before I act? |
| **Multi-step Reasoning** | How do I carry results from one step to the next to reach an answer? |

### Business scenarios

| # | Scenario | Typical steps | Good-fit technique |
|---|---|---|---|
| 1 | **Refund / return eligibility** (retail, e-commerce) | Fetch order, check delivery date, check policy window, apply restocking fee, decide, get approval | ReAct + approval gate |
| 2 | **Loan application pre-screening** (banking) | Collect documents, verify identity, pull credit data, apply policy rules, compute exposure, recommend, escalate borderline cases | Plan-and-Execute + human review |
| 3 | **Card transaction dispute** (banking) | Retrieve transaction, check merchant and location, compare to customer pattern, check fraud rules, decide provisional credit | ReAct |
| 4 | **KYC / customer onboarding** (banking, fintech) | Extract data from documents, validate fields, screen against watchlists, score risk, route to analyst | Prompt chaining + escalation |
| 5 | **Fraud and AML alert investigation** | Pull alert, gather related transactions, check counterparties, build timeline, write case summary | ReAct + reflection |
| 6 | **IT incident triage and resolution** | Read ticket, check logs and service status, identify probable cause, recommend fix, create incident ticket | Plan-and-Execute + approval |
| 7 | **Insurance claim processing** | Verify policy coverage, check claim documents, estimate damage, detect inconsistencies, compute payout | Prompt chaining + ReAct |
| 8 | **Regulatory or compliance report drafting** | Collect data from systems, compute metrics, draft sections, check against a checklist, revise | Plan-and-Execute + reflection |
| 9 | **Customer support resolution** | Understand issue, look up account, search knowledge base, try fix, escalate if unresolved | ReAct |
| 10 | **Contract or policy review** | Extract clauses, compare with standard terms, flag deviations, summarize risk | Prompt chaining + self-consistency |
| 11 | **Procurement and purchase approval** | Check budget, compare vendor quotes, verify approval limits, route for sign-off | Prompt chaining + approval gate |
| 12 | **HR policy and leave queries** | Identify employee, fetch leave balance, apply policy rules, compute entitlement, answer | ReAct |
| 13 | **Data analysis and reporting** | Query data, clean it, compute metrics, interpret, produce a narrative with charts | Plan-and-Execute |
| 14 | **Travel disruption rebooking** | Check flight status, find alternatives, check fare rules, price options, rebook with approval | ReAct + approval gate |
| 15 | **Code review and bug fixing** | Read the failing test, locate code, propose a fix, run tests, iterate | ReAct + reflection |

### Why it matters

- **Accuracy:** writing out and checking steps cuts the errors that come from "jumping" to an answer.
- **Grounding:** tools supply real data, so the agent does not guess facts.
- **Transparency and auditability:** every step can be logged, which regulated industries such as banking require.
- **Control:** you can add validation, limits, and human approval at specific steps.
- **Capability:** it lets AI handle work that no single prompt can, such as multi-system workflows.
- **Debuggability:** when something goes wrong, you can see which step failed instead of guessing.

The trade-off is cost, latency, and more things that can fail. Add steps only where they improve measured results.

### How it works (in short)

1. **Decompose** the goal into steps (the model decides, or you define a fixed chain).
2. **Execute** a step: reason, call a tool, or both.
3. **Observe** the result and update the working state (what is known so far).
4. **Decide** the next step: continue, change the plan, ask a human, or stop.
5. **Verify** the result against rules or a checklist (optionally with self-consistency or reflection).
6. **Answer** with the final result and the reasoning behind it.

```text
Goal -> Plan/Decompose -> [ Act -> Observe -> Update state ]  (repeat)
                                          |
                                 Verify -> Human approval (if risky) -> Final answer
```

Sections 4.1 to 4.7 below give you the concrete techniques for step 1 to 5, each with its own What / When / Scenarios / Why / How card and working code.

---

## 2. Worked example: what multi-step reasoning looks like

**Multi-step reasoning** is the ability of an AI system to break a problem into a sequence of intermediate steps, where each step builds on the result of the previous one, to reach a final answer or complete a goal.

Compare two questions:

| Question | Steps needed |
|---|---|
| "What is the capital of France?" | 1 (recall) |
| "Customer asks for a refund on order 1003. Is it eligible, and how much should they get?" | 4+ (look up order, check delivery date, check policy window, compute amount, decide) |

The second question has **dependencies**: you cannot compute the amount before you know the order total, and you cannot decide eligibility before you know the delivery date and the policy.

In agentic systems, multi-step reasoning appears in two forms:

- **Internal reasoning:** the model "thinks through" intermediate steps in text before answering.
- **External reasoning:** the model alternates between thinking and **acting** (calling tools, reading results), so each step can use fresh information from the outside world.

Most real agents combine both.

---

## 3. Why a single LLM call is not enough

A single prompt-and-answer call struggles with multi-step problems for four reasons:

1. **No working memory.** Without room to write out intermediate results, the model must "jump" to an answer. Arithmetic, logic, and multi-hop questions suffer most.
2. **No access to fresh data.** The model cannot know the order status, the current policy, or today's price. It needs tools.
3. **Error concentration.** One mistaken leap produces a wrong final answer with no chance to notice.
4. **No control points.** You cannot validate, log, or get human approval on a step that never happens visibly.

Multi-step reasoning fixes these by making intermediate steps **explicit, inspectable, and tool-backed**.

> **Rule of thumb:** if a competent human would need to write things down, look something up, or check their work, the agent needs multi-step reasoning.

---

## 4. The core techniques

### 4.1 Chain-of-Thought (CoT)

> **At a glance**
>
> - **What:** the model writes out its reasoning steps before giving the final answer.
> - **When:** the problem needs logic or arithmetic and all the data is already in the prompt.
> - **Business scenarios:** pricing and discount calculations (#13), policy-rule interpretation (#12), contract clause comparison (#10), simple eligibility checks with data supplied.
> - **Why:** the written steps act as working memory, which improves accuracy at almost no extra cost.
> - **How:** add "think step by step" and a fixed answer format, for example a final line `ANSWER:`.

**Idea:** ask the model to reason step by step before giving the final answer. The written steps act as working memory.

**Direct prompt (weak):**

```text
A customer bought 3 items at Rs.1,200 each with 10% discount and 18% GST on the
discounted amount. What is the final bill?
```

**CoT prompt (stronger):**

```text
A customer bought 3 items at Rs.1,200 each with 10% discount and 18% GST on the
discounted amount. What is the final bill?

Think step by step. Show each calculation, then give the final answer on a line
starting with "ANSWER:".
```

**Expected reasoning:**

```text
Subtotal = 3 x 1200 = 3600
Discount = 10% of 3600 = 360  -> discounted = 3240
GST = 18% of 3240 = 583.20
Final = 3240 + 583.20 = 3823.20
ANSWER: Rs.3,823.20
```

**Code:**

```python
from langchain.chat_models import init_chat_model

model = init_chat_model("openai:gpt-4o-mini")

question = (
    "A customer bought 3 items at Rs.1200 each with 10% discount and 18% GST "
    "on the discounted amount. What is the final bill?"
)

cot_prompt = (
    f"{question}\n\n"
    "Think step by step. Show each calculation, then give the final answer "
    "on a line starting with 'ANSWER:'."
)

print(model.invoke(cot_prompt).content)
```

**When to use:** reasoning that needs no outside data (math, logic, analysis of text already in the prompt).

**Limits:** the model can still make arithmetic mistakes, and it cannot fetch information. For exact calculations, give it a calculator tool instead.

---

### 4.2 Prompt chaining

> **At a glance**
>
> - **What:** a fixed sequence of separate LLM calls, where each output feeds the next, with code checks in between.
> - **When:** the stages are known in advance and identical every time.
> - **Business scenarios:** KYC document processing (#4), insurance claim intake (#7), contract review (#10), procurement approvals (#11), support email triage.
> - **Why:** each call has one narrow job, so it is easy to test, validate, and debug, and the path is predictable and cheap.
> - **How:** write one prompt per stage, validate the output (types, allowed values) in code, then pass it on.

**Idea:** split the task into a fixed sequence of separate LLM calls. The output of call N becomes the input of call N+1. Between calls you can validate, transform, or gate the data in ordinary code.

```text
Input -> [Step 1: Extract] -> [Step 2: Classify] -> [Step 3: Draft reply] -> Output
                 |                    |
              validate             validate
```

**Example: support-email handling**

```python
from langchain.chat_models import init_chat_model

model = init_chat_model("openai:gpt-4o-mini")

def ask(prompt: str) -> str:
    return model.invoke(prompt).content.strip()

email = """Hi, I ordered a mechanical keyboard (order 1003) two weeks ago.
Several keys stopped working. I want my money back. - Ravi"""

# Step 1: extract structured facts
facts = ask(
    "Extract the order id, product, and problem from this email. "
    "Reply as three lines: order_id, product, problem.\n\n" + email
)

# Step 2: classify (programmatic gate between steps)
category = ask(
    f"Facts:\n{facts}\n\nClassify the request as exactly one of: "
    "REFUND, REPLACEMENT, QUESTION. Reply with the word only."
)
if category not in {"REFUND", "REPLACEMENT", "QUESTION"}:
    raise ValueError(f"Unexpected category: {category}")

# Step 3: draft a reply using earlier outputs
reply = ask(
    f"Facts:\n{facts}\nCategory: {category}\n\n"
    "Write a polite 3-sentence reply that acknowledges the request and says "
    "we will check eligibility."
)
print(reply)
```

**Why it works:** each call has one narrow job, so it is easier to prompt, test, and debug. You control the path.

**When to use:** the steps are known in advance and the same every time (pipelines, document processing, content workflows).

**Limits:** the path is fixed. If the right next step depends on what was found, you need ReAct or planning.

---

### 4.3 ReAct (Reason + Act)

> **At a glance**
>
> - **What:** the model loops through Thought, Action (tool call), Observation until it can answer.
> - **When:** the next step depends on what the last tool returned, and the problem needs live data.
> - **Business scenarios:** refund eligibility (#1), card dispute handling (#3), customer support (#9), HR queries (#12), travel rebooking (#14), AML alert investigation (#5), bug fixing (#15).
> - **Why:** it is adaptive and grounded in real data, and it needs no fixed workflow.
> - **How:** give the agent well-described tools, a system prompt that says "verify with tools, never guess", and a step limit. In LangChain, use `create_agent`.

**Idea:** the model loops through **Thought -> Action -> Observation** until it can answer.

```text
Thought:      I need the order details first.
Action:       get_order("1003")
Observation:  Delivered 2025-10-20, Rs.7500
Thought:      Now I need the return policy window.
Action:       get_policy("electronics")
Observation:  Refund allowed within 30 days of delivery
Thought:      Today is 2025-11-03, that is 14 days. Eligible. Compute refund.
Action:       calculate("7500 * 1.0")
Observation:  7500
Final answer: Eligible for a full refund of Rs.7500.
```

The next action depends on the previous observation, which is what makes this **adaptive** multi-step reasoning.

**Code (same style as the other agents in this folder):**

```python
import os
from datetime import date
from dotenv import load_dotenv
from langchain.agents import create_agent
from langchain.tools import tool

load_dotenv()
assert os.getenv("OPENAI_API_KEY"), "OPENAI_API_KEY missing in .env"

ORDERS = {
    "1001": {"item": "Wireless Mouse", "amount": 1200, "delivered": "2025-10-10"},
    "1003": {"item": "Mechanical Keyboard", "amount": 7500, "delivered": "2025-10-20"},
}
POLICY = {"electronics": 30, "accessories": 15}   # refund window in days

@tool
def get_order(order_id: str) -> str:
    """Return item, amount and delivery date for an order id."""
    o = ORDERS.get(order_id)
    return str(o) if o else "Order not found"

@tool
def get_policy(category: str) -> str:
    """Return refund window in days for a product category (electronics, accessories)."""
    days = POLICY.get(category.lower())
    return f"{days} days" if days else "Unknown category"

@tool
def days_between(start_date: str, end_date: str) -> str:
    """Number of days between two ISO dates (YYYY-MM-DD)."""
    d = (date.fromisoformat(end_date) - date.fromisoformat(start_date)).days
    return str(d)

agent = create_agent(
    model="openai:gpt-4o-mini",
    tools=[get_order, get_policy, days_between],
    system_prompt=(
        "You are a refund-eligibility assistant. Today is 2025-11-03. "
        "Always look up facts with tools, never guess. Work step by step, "
        "and state your final decision with the reason."
    ),
)

result = agent.invoke({"messages": [
    {"role": "user", "content": "Is order 1003 (a keyboard) eligible for a refund?"}
]})
print(result["messages"][-1].content)
```

To **see** the multi-step reasoning, print every message:

```python
for m in result["messages"]:
    print(f"[{m.type}]", getattr(m, "content", ""), getattr(m, "tool_calls", ""))
```

You should see: model asks for `get_order` -> tool result -> model asks for `get_policy` and `days_between` -> tool results -> final answer.

**When to use:** the next step depends on what the last step returned; tools are involved; the problem is open-ended.

**Limits:** can wander, repeat calls, or stop too early; the "plan" is implicit and hard to audit. Hence planning.

---

### 4.4 Plan-and-Execute

> **At a glance**
>
> - **What:** a planner writes a numbered plan first, then an executor carries out each step, with optional re-planning.
> - **When:** the goal is long or multi-part, or you want a plan that a person can approve.
> - **Business scenarios:** loan pre-screening (#2), IT incident resolution (#6), compliance report drafting (#8), data analysis and reporting (#13).
> - **Why:** the plan is visible and auditable, keeps long tasks on track, and lets you use a strong model to plan and a cheaper one to execute.
> - **How:** get a structured plan from the model, show or approve it, run each step with tools while passing earlier results forward, and re-plan if a step fails.

**Idea:** separate **planning** from **doing**.

```text
Goal -> PLANNER (writes numbered steps) -> EXECUTOR (runs each step with tools)
     -> (optional) RE-PLANNER (updates plan from results) -> SUMMARIZER (final answer)
```

**Benefits**

- The plan is a visible artifact: you can show it to a human for approval (a natural human-in-the-loop checkpoint).
- Long tasks stay on track because the executor follows a list.
- You can use a stronger model to plan and a cheaper one to execute.

**Minimal sketch:**

```python
from typing import List
from pydantic import BaseModel, Field
from langchain.chat_models import init_chat_model

class Plan(BaseModel):
    steps: List[str] = Field(description="Ordered, concrete steps to reach the goal")

planner = init_chat_model("openai:gpt-4o-mini").with_structured_output(Plan)

goal = "Decide whether order 1003 is eligible for a refund and compute the amount."
plan = planner.invoke(
    "Create a short numbered plan (max 5 steps) for this goal. "
    "Available tools: get_order, get_policy, days_between.\n\nGoal: " + goal
)

for i, s in enumerate(plan.steps, 1):
    print(f"{i}. {s}")

# Executor: run each step with the ReAct agent from section 4.3,
# passing along results of earlier steps as context.
context = ""
for step in plan.steps:
    out = agent.invoke({"messages": [
        {"role": "user", "content": f"Context so far:\n{context}\n\nDo this step: {step}"}
    ]})
    context += f"\nStep: {step}\nResult: {out['messages'][-1].content}\n"

print(context)
```

See `PlanningAgent/weather-Planning.py` and `PlanningAgent/itSupport.py` in this folder for full versions.

**When to use:** long, multi-part goals; when you want an approvable plan; when cost matters.

**Limits:** plans can be wrong or go stale once real data comes in. Add a **re-planning** step after each result, or after failures.

---

### 4.5 Self-consistency

> **At a glance**
>
> - **What:** sample the reasoning several times and take the majority answer.
> - **When:** a single answer is high stakes and you can afford several calls.
> - **Business scenarios:** classifying a transaction as fraud or not (#3, #5), extracting key terms from contracts (#10), risk-rating a customer (#4).
> - **Why:** independent reasoning paths that agree are more likely to be correct, and disagreement is a useful warning signal.
> - **How:** call the model 3 to 7 times with temperature above zero, parse each final answer, vote, and escalate to a human if there is no clear majority.

**Idea:** sample the reasoning several times (with temperature > 0) and take the **majority answer**. Different reasoning paths that agree are more likely correct.

```python
from collections import Counter
from langchain.chat_models import init_chat_model

model = init_chat_model("openai:gpt-4o-mini", temperature=0.8)

prompt = (
    "Think step by step, then end with a line 'ANSWER: <number>'.\n\n"
    "A train travels 60 km in 45 minutes. At the same speed, how far does it go in 2 hours?"
)

answers = []
for _ in range(5):
    text = model.invoke(prompt).content
    for line in text.splitlines():
        if line.strip().startswith("ANSWER:"):
            answers.append(line.split(":", 1)[1].strip())

print(Counter(answers).most_common())    # e.g. [('160 km', 5)]
```

**When to use:** high-stakes single answers (math, classification, extraction) where you can afford N calls.

**Cost:** N times the tokens. Use selectively, for example only when a first pass has low confidence.

---

### 4.6 Reflection / self-critique

> **At a glance**
>
> - **What:** the model drafts, critiques the draft against a checklist, and revises.
> - **When:** output quality can be stated as clear criteria (tone, completeness, rule compliance).
> - **Business scenarios:** compliance reports (#8), customer-facing replies (#9), AML case summaries (#5), generated code (#15).
> - **Why:** it catches missed requirements and rule violations before the output reaches a customer or regulator.
> - **How:** write a concrete checklist, have a critic call return OK or a list of problems, revise, and cap the loop at 1 to 2 rounds.

**Idea:** after producing a draft, have the model (or a second model) **critique** it against a checklist, then revise. Repeat 1-2 times.

```text
Draft -> Critique ("what is wrong or missing?") -> Revise -> (stop when critique says OK)
```

```python
from langchain.chat_models import init_chat_model

model = init_chat_model("openai:gpt-4o-mini")
ask = lambda p: model.invoke(p).content.strip()

task = "Write a 3-sentence refund denial message for an order outside the 30-day window."

draft = ask(task)

for round_no in range(2):                       # cap the loop!
    critique = ask(
        "Critique this message against the checklist:\n"
        "1) polite  2) states the policy reason  3) offers an alternative  "
        "4) no promises we cannot keep.\n"
        "If everything passes, reply exactly 'OK'. Otherwise list problems.\n\n" + draft
    )
    if critique == "OK":
        break
    draft = ask(f"Revise the message to fix these problems:\n{critique}\n\nMessage:\n{draft}")

print(draft)
```

**When to use:** writing, code generation, analysis where quality criteria can be stated clearly.

**Caution:** always cap iterations, and give the critic concrete criteria. Vague "make it better" loops waste tokens and can make output worse.

---

### 4.7 Tree-of-Thought (overview)

> **At a glance**
>
> - **What:** explore several candidate reasoning paths, score them, prune weak ones, and backtrack.
> - **When:** early choices strongly affect the outcome, as in search-like puzzles and optimization.
> - **Business scenarios:** staff or shift scheduling, route and itinerary planning, portfolio or resource allocation options.
> - **Why:** it can find solutions that a single linear chain misses.
> - **How:** propose several next steps, have the model score each, expand only the best, and stop at a budget. It is expensive, so use it only when simpler methods fail.

**Idea:** instead of one chain of reasoning, explore **several candidate steps at each stage**, score them, keep the best, and backtrack from dead ends. Think of it as search over reasoning paths.

```text
            Problem
           /   |   \
         A     B     C        <- propose several next steps
        / \    |
      A1  A2   B1             <- expand the promising ones
       X       OK             <- prune bad paths, continue from good ones
```

It helps on puzzles and planning problems where early choices matter (for example scheduling or game-like tasks). It is expensive and rarely needed in business agents, so treat it as an advanced option after CoT, ReAct and reflection.

---

## 5. Extended thinking (reasoning models)

Modern models can be given a **thinking budget**: the model reasons internally for a number of tokens before producing the visible answer. You do not have to prompt "think step by step" yourself; the model plans, checks and backtracks internally.

With Claude, extended thinking is enabled through the API's `thinking` parameter. Check the current Anthropic documentation for the exact parameters and the models that support it, since options evolve.

```python
import anthropic

client = anthropic.Anthropic()          # uses ANTHROPIC_API_KEY

response = client.messages.create(
    model="claude-sonnet-5-5",          # confirm the current model id in the docs
    max_tokens=4000,
    thinking={"type": "enabled", "budget_tokens": 2000},
    messages=[{"role": "user", "content":
        "Order total Rs.7500, delivered 14 days ago, policy is 30 days for "
        "electronics with a 10% restocking fee on opened items. "
        "The item was opened. What refund does the customer get?"}],
)

for block in response.content:
    if block.type == "thinking":
        print("THINKING:", block.thinking)
    elif block.type == "text":
        print("ANSWER:", block.text)
```

**Practical guidance**

- Use extended thinking for hard reasoning inside a step (analysis, tricky math, planning). Use **tools** for facts and exact calculation.
- A larger thinking budget costs more and adds latency. Start small and raise it only if quality needs it.
- Thinking improves each step, but it does **not** replace the agent loop: tool use, validation and human approval still matter.

---

## 6. Choosing the right technique

| Situation | Best fit |
|---|---|
| Needs reasoning only, no tools | Chain-of-Thought or extended thinking |
| Fixed, known sequence of stages | Prompt chaining |
| Next step depends on tool results | ReAct |
| Long goal, needs approvable plan, cost-sensitive | Plan-and-Execute (+ re-planning) |
| One high-stakes answer, can afford repeats | Self-consistency |
| Output quality has clear criteria | Reflection loop |
| Hard search-like puzzle | Tree-of-Thought |

**Start simple.** A single well-prompted call, then CoT, then prompt chain, then ReAct. Add planning and reflection only when you can show they fix a failure you measured. More steps means more cost, latency and places to fail.

---

## 7. Failure modes and how to fix them

| Failure | Symptom | Fix |
|---|---|---|
| **Error propagation** | An early mistake poisons all later steps | Validate step outputs in code; use tools for facts and math; add a verification step |
| **Infinite or long loops** | Agent repeats the same tool call | Set a max-steps limit; detect repeated calls; give a clear stop condition |
| **Premature stopping** | Answers before gathering needed facts | System prompt: "Always verify with tools before answering"; require specific fields in the answer |
| **Wrong tool or arguments** | Bad tool call, then confused reasoning | Clear tool descriptions with examples; typed arguments; return helpful error messages |
| **Hallucinated observations** | Model "remembers" data it never fetched | Forbid guessing in the prompt; require citing which tool result supports each claim |
| **Context overflow** | Long runs forget early steps | Summarize earlier steps; keep a compact scratchpad or state object |
| **Plan drift** | Executor ignores or rewrites the plan | Pass the plan every step; add a re-plan checkpoint |
| **Overthinking** | Simple task takes many costly steps | Route easy requests to a single call; cap the thinking budget |
| **Irreversible mistakes** | Wrong action taken (refund, delete) | Add human approval gates for risky tools (see `HumanInLoopAgent/`) |

**Three defences that work almost everywhere**

1. **Step limit** (for example `recursion_limit` or your own counter).
2. **Tools for anything exact** (dates, math, lookups).
3. **Logging every step** so you can replay and debug.

---

## 8. Evaluating multi-step agents

A correct final answer can hide a bad process, and a wrong answer can come from one bad step. Evaluate both.

**Outcome metrics**

- Task success rate on a test set (for example 30 refund scenarios with known correct decisions).
- Answer correctness against ground truth.

**Process metrics**

- Number of steps and tool calls per task (cost and efficiency).
- Tool-call accuracy: right tool, right arguments.
- Step-level correctness: was each intermediate result right?
- Recovery: did the agent handle a tool error or missing data sensibly?

**Simple test harness idea**

```python
tests = [
    {"q": "Is order 1003 eligible for a refund?", "expect": "eligible"},
    {"q": "Is order 1001 eligible for a refund?", "expect": "not eligible"},  # accessory, past window
]

passed = 0
for t in tests:
    out = agent.invoke({"messages": [{"role": "user", "content": t["q"]}]})
    answer = out["messages"][-1].content.lower()
    tool_calls = sum(1 for m in out["messages"] if getattr(m, "tool_calls", None))
    ok = t["expect"] in answer
    passed += ok
    print(f"{'PASS' if ok else 'FAIL'} | tool-call turns: {tool_calls} | {t['q']}")

print(f"{passed}/{len(tests)} passed")
```

Run the same tests after every prompt or tool change to catch regressions.

---

## 9. Hands-on lab: refund-eligibility agent

**Goal:** build an agent that decides eligibility and refund amount using multiple dependent steps, then see where each technique helps.

**Business rules**

- Refund window: 30 days for electronics, 15 days for accessories.
- Opened items incur a 10% restocking fee.
- Refund amount = order amount minus restocking fee (if opened).
- Today's date is 2025-11-03.

**Tasks**

1. **Baseline:** ask one direct question to the model with no tools. Note any wrong or invented facts.
2. **CoT:** add "think step by step". What improves? What is still wrong (hint: it has no order data)?
3. **ReAct:** build the agent from section 4.3. Add a `calculate_refund(amount, opened)` tool. Print all messages and mark each Thought/Action/Observation.
4. **Plan-and-Execute:** add a planner that outputs the steps first. Print the plan before executing.
5. **Reflection:** add a final critic step that checks the decision against the rules and returns OK or a list of problems.
6. **Guardrail:** add a max-steps limit and test with an order id that does not exist. Does the agent recover gracefully?
7. **Human in the loop:** wrap the final "issue refund" action in an approval gate using the pattern from `HumanInLoopAgent/refund_hitl_agent.py`.

**Expected lesson:** each layer fixes a specific problem. Tools fix missing facts, planning fixes drift, reflection fixes rule violations, and approval gates fix irreversible risk.

---

## 10. Exercises

1. **Conceptual:** explain in two sentences why a ReAct agent can solve "Is order 1003 eligible?" but a single CoT prompt cannot.
2. **Design:** a bank wants an agent to answer "Why was my card transaction declined?" List the steps and tools needed, and mark which steps depend on earlier results.
3. **Debugging:** an agent calls `get_order("1003")` five times in a row. List three possible causes and a fix for each.
4. **Choose a technique:** for each task pick the best fit from section 6 and justify: (a) summarize 50 PDFs the same way, (b) investigate a production incident, (c) classify a support ticket with high accuracy, (d) draft a regulatory report with a quality checklist.
5. **Coding:** extend the self-consistency example to stop early when 3 answers in a row agree.
6. **Evaluation:** write 10 test cases for the refund agent, including 3 edge cases (exactly 30 days, unknown order, accessory opened).

---

## 11. Cheat sheet

| Technique | One-line summary | Strength | Weakness |
|---|---|---|---|
| Chain-of-Thought | Write reasoning before the answer | Simple, cheap | No tools, can err on exact math |
| Prompt chaining | Fixed sequence of calls | Predictable, testable | Rigid path |
| ReAct | Think, act, observe, repeat | Adaptive, tool-aware | Can wander or loop |
| Plan-and-Execute | Plan first, then run steps | Auditable, approvable | Plan can go stale |
| Self-consistency | Vote across several samples | Higher accuracy | N times the cost |
| Reflection | Draft, critique, revise | Better quality | Needs clear criteria, cap loops |
| Tree-of-Thought | Search over reasoning paths | Hard puzzles | Expensive, rarely needed |
| Extended thinking | Model reasons internally | Strong per-step reasoning | Cost and latency |

**Remember**

- Make steps explicit, inspectable and tool-backed.
- Use code for exact things and the LLM for judgment.
- Start simple, add structure only when measurement shows a need.
- Cap loops, log everything, and put humans on irreversible actions.

---

*Related material in this folder:* `ReativeAgent/` (tool calling basics), `PlanningAgent/` (plan-then-execute), `HumanInLoopAgent/` (approval gates), `Human-in-the-Loop-Agentic-AI.md` (concept notes).
