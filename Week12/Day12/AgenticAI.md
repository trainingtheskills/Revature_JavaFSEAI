# What is Agentic AI?

An AI agent is a system where an LLM doesn’t just answer a prompt. It pursues a goal by deciding what to do next, taking actions through tools, observing the results,
and repeating until the task is done.


#--------------------------------------Problem scenario-----------------------------------------------------------

# Introduction to Agentic AI
## Classroom Problem Scenario: The Intelligent HR Assistant

**Target Audience:** Beginners / IT Professionals  
**Duration:** 15–20 Minutes  
**Objective:** Understand why Agentic AI is needed and how it differs from Traditional Programming and Generative AI.

---

# 1. Introduce the Problem Scenario

## The Business Problem: Managing Employee Requests

Imagine you are working in the HR department of a large IT company with 2,000 employees.

Every day, the HR department receives hundreds of employee requests through emails and chat messages.

### Sample Employee Requests

1. "I want to apply for leave from December 22 to December 26."
2. "How many casual leaves do I have remaining?"
3. "Can I work from home next Monday?"
4. "What will be my salary after deducting 2 days of leave?"
5. "I submitted a leave request last week. What is its status?"

Currently, HR executives manually perform the following activities:

1. Read employee requests.
2. Identify what the employee needs.
3. Check employee details in the HR database.
4. Retrieve relevant company policies from PDF documents.
5. Calculate leave balance or salary deductions.
6. Check whether manager approval is required.
7. Send an appropriate response.
8. Update the HR system.

### The Challenge

Can we build an AI system that can independently:

- Understand employee requests?
- Decide what actions are required?
- Access different business systems?
- Retrieve relevant information?
- Execute appropriate actions?
- Verify the results?
- Provide the final response?

**Ask Students:**

"Can ChatGPT alone solve this problem, or do we need something more intelligent?"

Allow students to discuss.

---

# 2. Understand the Evolution of AI

Use the same HR problem to demonstrate three different approaches.

## Scenario 1: Traditional Programming

**Employee Request:**

"Calculate my remaining leaves."

**System Execution:**

The application executes predefined business logic.

```python
total_leaves = 20
used_leaves = 8

remaining_leaves = total_leaves - used_leaves

print("Remaining Leaves:", remaining_leaves)
```

**Output:**

```text
Remaining Leaves: 12
```

### Characteristics

- Follows predefined instructions.
- Executes fixed business rules.
- Requires explicit programming.
- Does not independently decide what to do next.
- Works well for predictable tasks.

**Limitation:** The system cannot independently understand complex employee requests or decide which additional systems to access.

---

## Scenario 2: Generative AI

**Employee Request:**

"How can I apply for leave?"

**AI Response:**

"You can apply for leave through the HR portal. Select the leave type, enter the dates and submit your request."

### Characteristics

- Understands natural language.
- Generates human-like responses.
- Answers questions.
- Summarizes information.
- Can explain company policies.

**Limitation:** Generating instructions does not mean the AI has actually submitted the leave application or updated the HR system.

---

## Scenario 3: Agentic AI

**Employee Request:**

"Apply for leave from December 22 to December 26."

**AI Agent Execution:**

The AI Agent performs the following activities:

1. Understands the employee's request.
2. Identifies the employee.
3. Checks the available leave balance.
4. Retrieves the relevant leave policy.
5. Validates the requested dates.
6. Determines whether manager approval is required.
7. Submits the leave request if authorized.
8. Verifies the submission result.
9. Provides the request ID and status.

### Characteristics

- Understands the desired goal.
- Breaks the goal into smaller tasks.
- Plans the execution steps.
- Selects appropriate tools.
- Interacts with external systems.
- Observes the results.
- Adapts its next action when necessary.

**Key Point:** The AI Agent does not just answer questions. It works toward achieving a business goal.

---

# 3. Comparison: Traditional Programming vs Generative AI vs Agentic AI

| Feature | Traditional Programming | Generative AI | Agentic AI |
|---|---|---|---|
| Input | Structured input | Natural language | Goal or objective |
| Processing | Predefined logic | LLM reasoning and generation | Reasoning, planning and execution |
| Decision-making | Rule-based | Primarily response generation | Goal-oriented decisions |
| Tool usage | Explicitly programmed | Can use tools when integrated | Selects and uses tools based on task |
| Actions | Predefined operations | Usually generates responses | Can execute authorized actions |
| Adaptability | Limited | Contextual responses | Can adapt its execution plan |
| Example | Calculate leave balance | Explain leave policy | Check balance and submit leave request |

### Remember

- Traditional Programming: Follow instructions.
- Generative AI: Generate responses.
- Agentic AI: Work toward achieving goals.

---

# 4. How Does an AI Agent Work?

Introduce the basic Agentic AI execution cycle.

## Agentic AI Workflow

An AI Agent generally follows this process:

### Step 1: Goal Identification

Understand what the user wants to achieve.

Example:

"Apply for five days of leave."

### Step 2: Perception

Understand the request and extract relevant information.

Example:

- Employee ID
- Leave duration
- Start date
- End date
- Leave type

### Step 3: Planning

Break the main objective into smaller tasks.

Example:

1. Retrieve employee details.
2. Check leave balance.
3. Retrieve leave policy.
4. Validate eligibility.
5. Submit the request if authorized.

### Step 4: Reasoning and Decision-Making

Evaluate available information and determine the next action.

Example:

- Does the employee have sufficient leave balance?
- Are the requested dates valid?
- Is manager approval required?

### Step 5: Tool Selection

Select appropriate tools to perform the required operations.

Example:

- HR Database Tool
- Policy Retrieval Tool
- Calculator Tool
- Email Tool

### Step 6: Action Execution

Execute the required operations using the selected tools.

Example:

Submit the leave request through the HR application API.

### Step 7: Observation

Check the result of the executed action.

Example:

Did the HR application successfully create the leave request?

### Step 8: Memory and Context

Retain relevant conversation information for subsequent interactions, subject to privacy and data-retention policies.

Example:

Remember the employee's ID and the leave request discussed in the conversation.

### Step 9: Goal Completion

Provide the final response.

Example:

"Your leave request has been submitted successfully. Your request ID is LR1025. It is awaiting manager approval."

**Important:** An agent may repeat the planning, action and observation cycle until the goal is achieved or human intervention is required.

---

# 5. Classroom Activity: Build an Intelligent HR Agent

## Problem Statement

An employee sends the following request:

"I need leave for five days next month. Please check my leave balance and apply for leave if I am eligible."

Ask students to think about how an AI Agent should solve this problem.

### Question 1: What information should the AI Agent collect?

Possible answers:

- Employee ID
- Employee name
- Leave balance
- Leave policy
- Leave type
- Requested dates
- Manager details

### Question 2: Which tools might the AI Agent need?

| Business Requirement | Required Tool |
|---|---|
| Retrieve employee details | HR Database Tool |
| Check leave balance | HR Database Tool |
| Retrieve leave policy | PDF / RAG Retrieval Tool |
| Calculate available leave | Calculator Tool |
| Submit leave request | HR Application API |
| Notify employee | Email Tool |

### Question 3: What should happen if the employee does not have sufficient leave balance?

Discuss the possible approaches:

1. Inform the employee that the balance is insufficient.
2. Ask whether the employee wants to apply for unpaid leave.
3. Offer alternative dates.
4. Do not submit an invalid leave request.

### Question 4: What should happen if manager approval is required?

Possible approach:

1. Create a pending leave request.
2. Send an approval notification to the manager.
3. Wait for the manager's decision.
4. Update the leave request status.
5. Notify the employee.

**Discussion Point:** Should an AI Agent approve its own leave request? Why should certain actions require human approval?

---

# 6. Architecture of the Intelligent HR Agent

The following components can be used to develop the application.

```text
                  EMPLOYEE
                      |
                      |
              Employee Request
                      |
                      v
              +---------------+
              |      LLM      |
              |               |
              | Understand    |
              | Reason        |
              | Plan          |
              +---------------+
                      |
                      v
              +---------------+
              |   AI AGENT    |
              |               |
              | Decision      |
              | Orchestration |
              | Tool Selection|
              +---------------+
                      |
          +-----------+-----------+
          |           |           |
          v           v           v
      +--------+  +--------+  +----------+
      | HR DB  |  |  RAG   |  |Calculator|
      | Tool   |  | Tool   |  |  Tool    |
      +--------+  +--------+  +----------+
          |           |           |
          +-----------+-----------+
                      |
                      v
              +---------------+
              |   HR SYSTEM   |
              |               |
              | Submit Leave  |
              | Update Status |
              +---------------+
                      |
                      v
              +---------------+
              | Final Response|
              |               |
              | Request ID    |
              | Leave Status  |
              +---------------+
                      |
                      v
                   EMPLOYEE
```

## Component Responsibilities

| Component | Responsibility |
|---|---|
| LLM | Understands requests and supports reasoning |
| AI Agent | Coordinates the workflow and selects actions |
| HR Database Tool | Retrieves employee information |
| RAG Tool | Retrieves information from HR policy documents |
| Calculator Tool | Performs calculations |
| HR Application API | Submits and updates leave requests |
| Email Tool | Sends notifications |
| Memory | Maintains relevant conversation context |

**Security Note:** The AI Agent must operate with appropriate access permissions. Sensitive operations, such as leave approvals, should require authorized human approval where applicable.

---

# 7. Important Concepts to Introduce

After explaining the problem scenario, introduce the following concepts.

| Concept | Explanation | HR Example |
|---|---|---|
| LLM | Language model used for understanding and reasoning | Understand leave requests |
| Prompt Engineering | Providing instructions to an LLM | HR Assistant instructions |
| Function Calling | Allowing an LLM to request external functions | Call leave balance function |
| Tools | External functions available to an agent | Database, calculator, email |
| AI Agent | System that uses an LLM and tools to achieve goals | Intelligent HR Assistant |
| Planning | Breaking a goal into smaller tasks | Check balance, retrieve policy |
| Memory | Maintaining relevant context | Remember employee details |
| RAG | Retrieving relevant information from documents | Search HR policy PDFs |
| Agentic Workflow | Repeated reasoning, action and observation | Submit and verify leave request |
| Multi-Agent System | Multiple agents collaborating | HR Agent and Payroll Agent |

---

# 8. Suggested Teaching Sequence

After introducing the problem, follow this learning sequence.

| Sequence | Topic | Practical Demonstration |
|---|---|---|
| 1 | Introduction to LLMs | Ask questions using an LLM |
| 2 | Prompt Engineering | Design an HR Assistant prompt |
| 3 | Function Calling | Calculate salary using a tool |
| 4 | AI Agents | Let an agent select appropriate tools |
| 5 | Agentic Workflow | Demonstrate Plan → Act → Observe |
| 6 | Memory | Remember employee context |
| 7 | RAG | Retrieve HR policies from PDFs |
| 8 | Multiple Tools | Database + Calculator + Email |
| 9 | Multi-Agent Systems | HR Agent + Payroll Agent |
| 10 | End-to-End Project | Develop an Intelligent HR Assistant |

---

# 9. Conclude the Session

Use the following explanation to summarize Agentic AI.

> Traditional software follows instructions.
>
> Generative AI generates responses.
>
> Agentic AI takes a goal, determines how to achieve it, selects the required tools, performs actions, observes the results and adapts its next steps to complete the task.

## The Key Difference

**Generative AI:** What should I say?

**Agentic AI:** What should I do to achieve this goal?

### Final Question 

"Imagine you have an AI Agent connected to your email, calendar, database and web browser. What real-world task would you automate using this agent?"

Encourage students to suggest examples from:

- HR Management
- Banking
- Healthcare Administration
- Travel Management
- Customer Support
- Education
- E-commerce
---
# --------------------------------------------------------


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
