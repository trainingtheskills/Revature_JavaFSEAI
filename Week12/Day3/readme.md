# Human in Loop

# Human-in-the-Loop (HITL) in Agentic AI

**Human-in-the-Loop** means designing an agent so that a person reviews, approves, corrects, or directs it at defined points in its workflow, instead of letting it run fully autonomously. The agent does the planning and execution. The human keeps authority over decisions that are risky, ambiguous, or irreversible.

## Why it matters

Agents plan, call tools, and take real actions such as writing to databases, sending emails, or moving money. That brings risks a plain chatbot doesn't have:

- **Compounding errors:** a wrong step early in a multi-step run can corrupt everything after it.
- **Irreversible actions:** deletes, payments, and deployments can't be undone.
- **Hallucination and misread intent:** the agent may be confident and wrong.
- **Prompt injection:** untrusted content can try to steer the agent into harmful tool calls.
- **Compliance and accountability:** regulated domains such as banking and healthcare require a named human decision-maker.

## Where humans can sit in the loop

| Pattern | What the human does | Example |
|---|---|---|
| **Approval gate** | Approves or rejects a proposed action before it runs | Agent drafts a $5,000 refund; a manager clicks Approve |
| **Review and edit** | Modifies the agent's output or plan before continuing | User reorders the steps of a proposed plan |
| **Clarification** | Answers questions when the agent lacks information | "Which account should I debit?" |
| **Escalation** | Takes over when confidence is low or a rule is hit | Fraud score above threshold goes to an analyst |
| **Feedback and correction** | Rates or corrects outputs so the system improves | Thumbs-down plus corrected answer feeds evaluation data |
| **Supervision (human-on-the-loop)** | Monitors and can interrupt, but doesn't approve every step | Dashboard with a kill switch |

- **In-the-loop:** the agent cannot proceed without the human.
- **On-the-loop:** the human supervises and can intervene.
- **Out-of-the-loop:** full autonomy.

## How it works technically

1. The agent plans and reaches a **checkpoint**, triggered by a sensitive tool, a risk or confidence threshold, a policy rule, or an explicit "ask the user" tool.
2. The agent **pauses and persists its state** (conversation, plan, pending tool call) so it can wait minutes or days.
3. A **review request** goes to a human through a UI, Slack, email, or a ticket queue, with enough context to decide.
4. The human **approves, rejects, or edits**.
5. The agent **resumes** from the saved state and incorporates the decision.

```text
User request -> Agent plans -> Risky step? --no--> Execute tool --> Next step
                                   |
                                  yes
                                   v
                     Pause + save state -> Human review
                                   |
                  Approve / Edit / Reject
                                   v
                       Resume (or replan) -> Audit log
```

Framework support:

- **LangGraph:** `interrupt()` and checkpointers
- **Claude Agent SDK / Claude Code:** permission prompts and tool-approval hooks
- **AWS Bedrock Agents:** return of control

## Choosing where to put humans

Put a human where **risk x irreversibility** is high, and automate the rest:

| Risk level | Examples | Oversight |
|---|---|---|
| Low, reversible | Read-only search, drafting | Fully automatic |
| Medium | Sending an email, creating a ticket | Automatic with logging and sampled review |
| High or irreversible | Payments, production changes, customer commitments | Mandatory approval |

## Example: loan-processing agent in a bank

1. The agent gathers documents, runs credit checks, and summarizes the application (automatic).
2. It recommends approve or decline. If confidence is high and the amount is below the limit, it proceeds.
3. If the amount is large, data conflicts, or the applicant is borderline, it **pauses and escalates to a loan officer** with a summary and evidence.
4. The officer approves or overrides. The decision is **logged for audit**, and the override is stored as feedback.

## Trade-offs

**Benefits:** safety, accountability, regulatory compliance, user trust, and a feedback loop for improving the agent.

**Costs:** latency, human bottlenecks, and approval fatigue, where people rubber-stamp everything. Approval fatigue can make HITL worse than no HITL. Mitigate it with risk-tiered approvals, clear summaries, and by gradually raising autonomy as the agent's track record is measured.

## Best practices

- Show the human **what, why, and impact** (proposed action, reasoning, data used), not just "Approve?".
- Make approvals **specific**: approve this exact action with these parameters.
- Make state **resumable and auditable**: who approved what, and when.
- Set **timeouts and fallbacks**: a safe default or escalation if nobody responds.
- Treat HITL as a **dial, not a switch**. Start with more oversight and loosen it as evaluation data supports it.

## Summary

HITL keeps the agent doing the work while humans keep the authority over the decisions that matter.
