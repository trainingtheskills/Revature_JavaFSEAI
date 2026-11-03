# Model Context Protocol (MCP) – 

## Training objective: Understand the Model Context Protocol (MCP), why it is important in Agentic AI, how MCP servers and clients communicate, and how to develop and test your own MCP server and connect it to an AI application.

I recommend using an HR Management System as the running case study throughout the tutorial. This makes it easier to demonstrate how an AI assistant can interact with employee records, leave policies, salary information, and HR operations.

The examples use Python and the official MCP Python SDK v2, with a focus on understanding the protocol rather than hiding everything behind an agent framework. The current SDK supports tools, resources, prompts, and standard transports. 
MCP Python

## 1. Learning outcomes
By the end of six hours, participants should be able to:
1. Explain MCP architecture and its role in Agentic AI.
2. Understand the difference between MCP, APIs, function calling, and LangChain tools.
3. Explain MCP Host, Client, and Server.
4. Understand MCP tools, resources, and prompts.
5. Understand JSON-RPC and MCP communication.
6. Develop an MCP server using Python.
7. Create an MCP client to discover and invoke tools.
8. Expose business data using MCP resources.
9. Create reusable prompts using MCP prompts.
10. Connect an MCP server to an AI application.
11. Test and troubleshoot MCP implementations.
2. Six-hour training schedule
3. 


## Module 1 – Introduction to MCP (45 minutes)
1.1 What is MCP?
Concept

MCP stands for Model Context Protocol.

It is an open protocol that standardizes how AI applications connect to external tools, systems, and data sources.

Think of MCP as a common communication language between AI applications and external systems.

For example, imagine that you are developing an HR AI assistant.

A user asks:
What is my remaining leave balance, and what is the company's work-from-home policy?

To answer this question, an AI application may need to:
- Retrieve employee information from an HR database.
- Retrieve the leave balance.
- Read the work-from-home policy.
- Generate a natural-language response.
Without a standardized integration mechanism, developers may need to build separate integrations for each AI application.

MCP provides a standardized way to expose these capabilities.
1.2 Why do we need MCP?
Consider the following scenario.

You have three AI applications:
- Claude Desktop
- A custom LangChain-based HR assistant
- An AI assistant developed using another LLM provider
You have three business systems:
- Employee Management System
- Payroll Management System
- Leave Management System
Without MCP, every AI application may need its own integration with each business system.

<img width="524" height="296" alt="image" src="https://github.com/user-attachments/assets/6d9edcd0-dc0f-4502-8783-eb884e4866be" />

With MCP, business capabilities can be exposed through standardized MCP servers.

<img width="1017" height="622" alt="image" src="https://github.com/user-attachments/assets/29ac03dc-166a-4344-bfba-63cc476c56d5" />

## Integration Architecture Comparison: Without MCP vs. With MCP

| Feature / Aspect | Without MCP | With MCP |
| :--- | :--- | :--- |
| **Integration Type** | Custom integration for each application | Standardized integration |
| **Code Patterns** | Repeated integration code | Reusable MCP servers |
| **Tool Communication** | Different tool interfaces | Standardized discovery and invocation |
| **Maintenance Overhead** | More maintenance | Easier integration management |
| **System Architecture** | Tighter coupling | Better separation of concerns |


## Why is this important in Agentic AI?

An AI agent needs more than the ability to generate text. It needs access to external capabilities. **MCP standardizes how those capabilities are discovered and accessed.**

## 1.3 MCP vs API vs Function Calling

These concepts are related, but they are not interchangeable.

| Concept | Purpose |
| :--- | :--- |
| **REST API** | Allows applications to communicate with backend services |
| **Function Calling** | Allows an LLM to request execution of a function |
| **MCP** | Standardizes communication between AI applications and external capabilities |
| **LangChain Tool** | Represents an executable capability that an agent can invoke |

### Example

Suppose the HR system has this REST endpoint:
```http
GET /api/employees/101/leave-balance
```

An MCP server can expose that backend functionality as:
```python
get_leave_balance(employee_id=101)
```

An MCP client can discover the tool and invoke it without needing to understand the internal REST implementation.

> [!IMPORTANT]
> MCP does not replace REST APIs. An MCP server can use REST APIs, databases, or existing business logic internally.

## 1.4 Where is MCP used?

* AI-powered enterprise applications
* AI coding assistants
* Database connectivity
* Document retrieval
* File system access
* CRM and ERP integrations
* HR and payroll automation
* Agentic AI workflows

---

## Discussion Exercise

**Ask participants:**
> *"If you were building an AI banking assistant, what external systems would it need to access?"*

**Expected answers:**
* Customer database
* Account management
* Transaction history
* Loan management
* KYC system
* Fraud monitoring system

# Module 2 – MCP Architecture and Communication (45 minutes)
2.1 Understand the three main components
MCP follows a Host–Client–Server architecture.

<img width="501" height="329" alt="image" src="https://github.com/user-attachments/assets/9a268437-31ac-41c2-a795-dc1b2b761d92" />
# 2.1 Core Components of MCP

## A. MCP Host
* **What?** The Host is the AI application that provides the user interface and coordinates AI interactions.
* **Why?** The Host manages the interaction between the user, the LLM, and MCP clients.
* **How?** When a user asks a question, the Host can use an LLM to determine whether an MCP capability is needed.
* **Examples:**
  * Claude Desktop
  * An IDE with MCP support
  * A custom AI application

## B. MCP Client
* **What?** The Client is the component responsible for communicating with an MCP server.
* **Why?** It provides a standardized way to discover available capabilities and invoke them.
* **How?** The Client can discover available tools, read resources, retrieve prompts, and invoke tools.

## C. MCP Server
* **What?** The Server exposes business capabilities to MCP clients.
* **Why?** It separates the business implementation from the AI application.
* **How?** A server can expose tools (executable functions), resources (contextual data), and prompts (reusable prompt templates).

---

## 2.2 Understand MCP Primitives

### 1. Tools – Actions
Functions that an AI application can discover and invoke.

* **HR Examples:**
  * Calculate employee salary
  * Check leave balance
  * Apply for leave
  * Retrieve employee details

### 2. Resources – Context/Data
Data that an AI application can read and use as context.

* **HR Examples:**
  * Employee directory
  * Leave policy
  * Work-from-home policy
  * Salary structure documentation

### 3. Prompts – Reusable Instructions
Predefined prompt templates that help structure interactions.

* **HR Examples:**
  * Generate an employee leave summary
  * Explain salary components
  * Review an employee's leave request

---

## 2.3 How Does MCP Communication Work?

Consider this interaction:

> **User:** *"Check employee 101's leave balance."*

### Execution Flow
1. **User** sends a request to the AI Host.
2. The **Host** uses the MCP Client.
3. The **Client** discovers available tools.
4. The **LLM** determines that `get_leave_balance` is appropriate.
5. The **Client** sends a tool invocation to the MCP Server.
6. The **Server** executes the business logic.
7. The **Result** is returned to the Client.
8. The **Host** uses the result to generate a final response.

<img width="789" height="525" alt="image" src="https://github.com/user-attachments/assets/1dd48c7c-ee0e-4621-b1b6-e882a70b2a2f" />

## 2.4 What is JSON-RPC?
MCP uses JSON-RPC as its underlying message protocol.

JSON-RPC allows one application to request that another application execute a method.

Example request:
```
{
  "jsonrpc": "2.0",
  "id": 1,
  "method": "tools/call",
  "params": {
    "name": "get_leave_balance",
    "arguments": {
      "employee_id": 101
    }
  }
}
```
```
Example response:
{
  "jsonrpc": "2.0",
  "id": 1,
  "result": {
    "content": [
      {
        "type": "text",
        "text": "Employee 101 has 12 leave days remaining."
      }
    ]
  }
}
```


The SDK handles the protocol messages for us, so developers usually work with Python functions instead of manually constructing JSON-RPC requests.

