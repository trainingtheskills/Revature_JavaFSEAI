
# Mini Case Study: AI-Powered HR Assistant Using LangChain

**Level:** Beginner–Intermediate  
**Type:** Hands-on Assignment

---

## 1. Business Scenario

Consider an IT company named **TechNova Solutions** with approximately 2,000 employees.

The HR department receives numerous employee queries related to leave balance, leave policies, employee information, and HR support requests.

Currently, employees contact HR through emails or phone calls to get answers.

The company wants to develop an **AI-Powered HR Assistant using LangChain**.

Employees should be able to interact with the assistant using natural language and receive relevant information.

## 2. Business Requirements

The application should support the following interactions:

| Employee Query | Expected Functionality |
|---|---|
| What is my leave balance? | Retrieve available leave balance. |
| What is the annual leave policy? | Retrieve company leave policy. |
| How many leaves can I carry forward? | Retrieve carry-forward policy. |
| Create an HR support request for me. | Create an HR support ticket. |
| What is my HR ticket status? | Retrieve HR ticket status. |
| Do you remember my previous HR request? | Recall previous conversation. |

## 3. Technical Requirements

Implement the application using the following three LangChain concepts.

### Task 1: Implement LangChain Tools

Create the following tools using the `@tool` decorator.

| Tool Name | Description |
|---|---|
| `get_employee_details(employee_id)` | Retrieve employee name, department, and employee details. |
| `get_leave_balance(employee_id)` | Retrieve available leave balance. |
| `get_hr_policy(policy_type)` | Retrieve HR policies such as annual leave, carry-forward, and work-from-home policies. |
| `create_hr_ticket(employee_id, issue)` | Create an HR support request. |
| `get_ticket_status(ticket_id)` | Retrieve the status of an HR support ticket. |

**Requirements:**

1. Use the `@tool` decorator.
2. Define appropriate input parameters.
3. Provide meaningful tool descriptions.
4. Implement business logic inside each function.
5. Create a list containing all tools.
6. Execute each tool independently using `invoke()`.

### Task 2: Implement Prompt Template

Create a `ChatPromptTemplate` for the HR Assistant.

Your prompt should include:

| Prompt Component | Requirement |
|---|---|
| Role | Act as an HR Assistant for TechNova Solutions. |
| Task | Understand employee queries and provide HR assistance. |
| Context | Use company HR policies and employee information. |
| Constraint | Do not invent employee information or HR policies. |
| Output | Provide clear, professional, employee-friendly responses. |

### Task 3: Implement LangChain Memory

The assistant should remember previous conversations.

Use either `ChatMessageHistory` or `ConversationBufferMemory`.

The assistant should remember:

- Employee ID.
- Employee name.
- Previous HR queries.
- Previously created HR ticket ID.
- Previous conversation context.

**Example:**

```text
Employee: My employee ID is E101.

Assistant: Hello! How can I help you?

Employee: I want to know my leave balance.

Assistant: You have 12 available leaves.

Employee: Can I carry forward unused leaves?

Assistant: Yes. According to the company
policy, you can carry forward up to 5 leaves.

Employee: Do you remember my employee ID?

Assistant: Yes, your employee ID is E101.
```

## 4. Final Integration

Integrate the following components into one console-based application:

1. LangChain Tools
2. ChatPromptTemplate
3. LLM (ChatOpenAI)
4. Conversation Memory
5. Tool Calling

The application should:

- Accept employee queries through the console.
- Understand the employee's question.
- Select and execute the appropriate tool.
- Use the prompt template to guide responses.
- Maintain conversation history.
- Continue accepting questions until the employee enters `exit`.

## 5. Expected Application Interaction

```text
========================================
     TechNova HR Support Assistant
========================================

Employee: My employee ID is E101.

Assistant: Hello! Welcome to TechNova HR Support.
How can I help you today?

Employee: What is my leave balance?

Assistant: You have 12 available leaves.

Employee: What is the carry-forward policy?

Assistant: Employees can carry forward up to
5 unused leaves to the next calendar year.

Employee: Create an HR ticket for a salary
correction request.

Assistant: Your HR support ticket has been created.

Ticket ID: HR1001
Status: Open

Employee: What is my ticket status?

Assistant: Your ticket HR1001 is currently Open.

Employee: Do you remember my previous request?

Assistant: Yes. You previously raised a salary
correction request under ticket HR1001.

Employee: exit

Assistant: Thank you for contacting TechNova HR.
Have a great day!
```

## 6. Deliverables

| No. | Deliverable |
|---|---|
| 1 | Python program implementing HR tools |
| 2 | Prompt template implementation |
| 3 | Conversation memory implementation |
| 4 | Integrated HR Assistant application |
| 5 | Screenshots of application execution |

## 7. Evaluation Criteria

| Assessment Area | Marks |
|---|---:|
| Tool implementation | 30 |
| Prompt template implementation | 20 |
| Memory implementation | 20 |
| Integration of all components | 20 |
| Code organization and testing | 10 |
| **Total** | **100** |

## 8. Learning Outcome

By completing this assignment, learners will understand how LangChain Tools, Prompt Templates, and Memory work together to develop a conversational AI application for a real-world HR business scenario.

**Security consideration:** Employee salary, leave, and personal information must only be accessible to authenticated and authorized employees. Conversation memory alone must never be used to establish identity.
