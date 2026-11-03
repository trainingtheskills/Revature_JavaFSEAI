# Simple Case Study: The Business Trip Request

## Situation

An employee tells the company's AI assistant:

> "Book me a trip to Toronto for the client meeting next Tuesday at 10 AM. Keep it within policy."

**Tools the agent has:** `search_flights()`, `search_hotels()`, `check_policy()`, `get_calendar()`, `book()`, `send_itinerary()`.

**Company policy:** economy flights only, hotels up to $200/night, manager approval required if the total exceeds $1,500.

---

## Approach 1: Reactive Agent (one step at a time)

The agent decides each step only after seeing the last result.

1. *Thought:* I need flights. → `search_flights(Toronto, Monday)`
2. *Observation:* Only one flight arrives before the meeting, and it costs $900.
3. *Thought:* That's expensive. Check policy. → `check_policy()`
4. *Observation:* Economy is allowed, and approval is needed above $1,500.
5. *Thought:* Now a hotel. → `search_hotels()` → cheapest is $210/night, over the limit.
6. *Thought:* Try another area. → `search_hotels(downtown-east)` → $190, fine.
7. *Thought:* Total is $1,280. No approval needed. → `book()` → `send_itinerary()`

**What happened:** the agent improvised. When the hotel was over budget, it simply tried something else.

---

## Approach 2: Planning Agent (plan first, then act)

The agent writes the whole plan before touching any tool:

```
Plan:
1. Check the calendar and confirm the dates
2. Search flights arriving before 10 AM Tuesday
3. Search hotels within the $200 limit
4. Calculate total cost and check it against policy
5. If total > $1,500, request manager approval
6. Book flight and hotel
7. Send the itinerary
```

The employee or manager can **review the plan**, and the agent then executes it step by step. If step 3 finds no hotel under $200, the agent **replans**, for example by widening the search area or asking for an exception.

---

## Explanation

| | Reactive | Planning |
|---|---|---|
| **How it worked** | Looked, acted, looked again | Wrote the whole route, then drove it |
| **Analogy** | Walking through a new city using only your eyes | Following a map made before you left |
| **Strength shown** | Recovered smoothly when the hotel was too expensive | Everyone could see and approve what would happen *before* any money was spent |
| **Weakness shown** | Nobody knew in advance what it would book | If the world changed mid-trip (the flight sells out), the old plan breaks and needs replanning |

### Which is better here?

A trip is a **known, structured task** with money involved, so a **planning agent** is the better fit. The plan can be approved, the steps follow a sensible order, and the cost is controlled.

A **reactive agent** would be better if the request were vague and exploratory, such as: *"Find out why my last expense claim was rejected."* There is no way to plan that in advance, because each clue decides where to look next.

### The takeaway

- **Reactive = think while doing.** It is flexible, but you don't know the path in advance.
- **Planning = think, then do.** It is predictable and reviewable, but needs replanning when things change.
- Most real agents **combine both**: they plan the big steps and react within each one.


#--------------------------------------------------------------------------------------------------

# 2. Problem Scenario: The Failed Overnight Payment Batch

A retail bank runs its **overnight payment settlement** on IBM i, with IBM MQ carrying messages between systems and DB2 as the ledger. At 6:00 AM, operations finds that **12,000 payments are unsettled**. Salary credits are due to customers by 9:00 AM.

Two very different kinds of work are needed.

## Part A: Find out what went wrong (unknown territory)

Nobody knows the cause. It might be:

- a stuck MQ queue,
- a locked DB2 table,
- a failed upstream file,
- a bad message blocking the rest.

The engineer works like this:

1. Check the queue depth. It is high, so look at the oldest message.
2. That message is malformed, so check what produced it.
3. The producer job failed at 2:14 AM, so check its logs.
4. A schema change was deployed last night, so check which fields changed.

**Each step is chosen only after seeing the previous result.** No one could write this sequence down in advance.

## Part B: Fix it safely (known territory, high stakes)

Once the cause is known, the fix must follow a controlled order:

1. Quarantine the bad message.
2. Pause the settlement job.
3. Restore the queue.
4. Reprocess the 12,000 payments.
5. Reconcile totals against DB2.
6. Get risk and operations approval at the key points.
7. Notify stakeholders and write the audit log.

**The order matters, mistakes are costly, and a manager wants to approve the plan before anything is touched.**

## The Business Question

*Can one AI agent handle both the open-ended diagnosis and the controlled remediation, and should it work the same way in both phases?*

---

# Introducing the Two Agent Styles

## Reactive Agent: "think while doing"

A reactive agent chooses **one step at a time** from the current situation. It acts, observes the result, and then decides what to do next. There is no upfront roadmap.

```
Observe → Reason → Act → Observe → Reason → Act → ...
```

This is the ReAct loop. It handles surprises well, because it adapts at every step.

**Fits Part A:** diagnosis is exploratory, and the path is unknown until evidence appears.

## Planning Agent: "think, then do"

A planning agent first **writes a complete plan**, then executes it step by step. If a step fails or reality differs from the plan, it **replans**.

```
Goal → Plan (step 1..N) → Execute step → Check → (Replan if needed) → Done
```

The plan can be reviewed, approved and logged **before** anything runs.

**Fits Part B:** remediation is structured, order-sensitive, and needs sign-off.

## Side by Side

| | Reactive | Planning |
|---|---|---|
| Decides | Next step only | Whole sequence first |
| Strength | Adapts to the unexpected | Predictable, reviewable, auditable |
| Weakness | Can wander or loop with no overall view | Plan can go stale or be followed blindly |
| Approval | Hard to gate in advance | Approve the plan upfront |
| Banking fit | Incident diagnosis, investigation | Settlement fixes, loan processing, compliance workflows |

## The Design Answer: A Hybrid

In the scenario, the best system is **both**:

1. A **reactive diagnosis agent** investigates until it names a root cause.
2. It hands that finding to a **planning agent**, which drafts the remediation plan.
3. A **human approves the plan**.
4. The plan executes with checks after each step. A failure triggers **replanning**, and risky steps pause for approval.

The plan also serves as the audit trail, which regulated banking environments require.

> **Reactive agents discover. Planning agents deliver. Real systems need both.**
