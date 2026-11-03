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
