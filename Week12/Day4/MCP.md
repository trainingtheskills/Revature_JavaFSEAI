# Model Context Protocol (MCP) 

 Training objective: Understand the Model Context Protocol (MCP), why it is important in Agentic AI, how MCP servers and clients communicate, and how to develop and test your own MCP server and connect it to an AI application.

I recommend using an HR Management Systehttps://github.com/trainingtheskills/Revature_JavaFSEAI/tree/main/Week12/Day4m as the running case study throughout the tutorial. This makes it easier to demonstrate how an AI assistant can interact with employee records, leave policies, salary information, and HR operations.

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

## 2.5 MCP transports
A transport defines how the Client and Server exchange messages.
Transport	Purpose	Example
STDIO	Local process communication	Desktop AI applications
Streamable HTTP	Network-based communication	Remote MCP server
SSE	Server-Sent Events transport	Compatibility with older integrations

For our tutorial:
- We will use Streamable HTTP for the learning client/server exercise.
- We will introduce STDIO for local AI application integrations.
Module exercise

Draw the architecture of an HR assistant using Host, Client, Server, and the HR database.
The SDK handles the protocol messages for us, so developers usually work with Python functions instead of manually constructing JSON-RPC requests.

# Module 3 – Build Your First MCP Server (60 minutes)

## Hands-on Coding

### 3.1 Development Scenario
We will develop an **HR Operations MCP Server**.

The server will expose the following business capabilities:

| Business Requirement | MCP Capability |
| :--- | :--- |
| Retrieve employee details | Tool |
| Check leave balance | Tool |
| Apply for leave | Tool |
| Calculate employee salary | Tool |
| Retrieve leave policy | Resource |
| Retrieve WFH policy | Resource |
| Generate leave summary instructions | Prompt |

### Why are we building an MCP server?
Imagine that your HR application already contains employee-related business logic. 

Instead of implementing the same integration separately for every AI application, we expose the functionality through an MCP server. **The server becomes a reusable integration layer.**


# 3.2 Project Setup

### Prerequisites
* Python 3.11 or above
* VS Code or PyCharm
* Basic Python programming knowledge
* Terminal access

### Directory Structure
Create the following layout in your workspace:

```text
hr-mcp-server/
│
├── server.py
├── client.py
├── requirements.txt
│
├── data/
│   └── policies/
│       ├── leave_policy.txt
│       └── wfh_policy.txt
│
└── README.md
```

### Step 1 – Create the Project
Run these commands to set up your directory and virtual environment:

```bash
mkdir hr-mcp-server
cd hr-mcp-server
python -m venv venv
```

Activate the environment based on your operating system:
* **macOS / Linux:** `source venv/bin/activate`
* **Windows:** `venv\Scripts\activate`

### Step 2 – Install Dependencies
Create your `requirements.txt` file:

```text
mcp[cli]
anyio
```

Install the dependencies:
```bash
pip install -r requirements.txt
```
*(The official Python SDK provides the underlying MCP server and client implementation.)*

---

# 3.3 Understand the MCP Server Implementation

Before writing the complete codebase, analyze this minimal server pattern:

```python
from mcp.server import MCPServer

# Create an MCP server
mcp = MCPServer("HR Operations Server")

# Expose a business function as an MCP tool
@mcp.tool()
def get_employee(employee_id: int) -> str:
    """Retrieve employee details."""
    return f"Employee ID: {employee_id}"
```

### Concept Explanation

| Code | Purpose |
| :--- | :--- |
| `MCPServer()` | Creates a new MCP server instance |
| `@mcp.tool()` | Registers a Python function as an available MCP tool |
| `get_employee()` | Implements the core backend business functionality |
| `employee_id` | Input parameter extracted from the client request |
| `return` | Sends the textual execution result back to the caller |

### Why use decorators?
Decorators make it easy to expose existing Python functions as MCP capabilities. The SDK dynamically derives the required input schema from the function's native type hints and automatically handles serialization for MCP clients.

---

# 3.4 Complete MCP Server Code

Create your `server.py` file. This implementation exposes **four tools, two resources, and one prompt**.

```python
# ============================================================
# HR OPERATIONS MCP SERVER
# ============================================================
# This server exposes HR business capabilities through MCP.
#
# MCP capabilities:
# 1. Tools     - Execute business operations
# 2. Resources - Provide HR policy information
# 3. Prompts   - Provide reusable prompt templates
# ============================================================

from mcp.server import MCPServer

# ------------------------------------------------------------
# STEP 1: CREATE MCP SERVER
# ------------------------------------------------------------
mcp = MCPServer(
    "HR Operations Server",
    instructions="Provides employee, salary, leave and HR policy services."
)

# ------------------------------------------------------------
# STEP 2: SAMPLE HR DATABASE
# ------------------------------------------------------------
# For this tutorial, we use in-memory Python dictionaries.
# In production, connect to MySQL, PostgreSQL or an HR API.
employees = {
    101: {
        "name": "Rahul Sharma",
        "department": "IT",
        "designation": "Software Engineer",
        "annual_salary": 1200000,
        "leave_balance": 12
    },
    102: {
        "name": "Priya Verma",
        "department": "HR",
        "designation": "HR Manager",
        "annual_salary": 1500000,
        "leave_balance": 15
    },
    103: {
        "name": "Amit Kumar",
        "department": "Finance",
        "designation": "Financial Analyst",
        "annual_salary": 900000,
        "leave_balance": 8
    }
}

# ------------------------------------------------------------
# STEP 3: TOOLS (ACTIONS)
# ------------------------------------------------------------
@mcp.tool()
def get_employee_details(employee_id: int) -> str:
    """Retrieve an employee's profile, department, and designation."""
    emp = employees.get(employee_id)
    if not emp:
        return f"Error: Employee with ID {employee_id} not found."
    return f"Name: {emp['name']} | Dept: {emp['department']} | Role: {emp['designation']}"

@mcp.tool()
def get_leave_balance(employee_id: int) -> str:
    """Check the remaining leave balance for a specific employee."""
    emp = employees.get(employee_id)
    if not emp:
        return f"Error: Employee with ID {employee_id} not found."
    return f"Employee {emp['name']} has {emp['leave_balance']} days of leave remaining."

@mcp.tool()
def apply_for_leave(employee_id: int, days: int) -> str:
    """Submit a formal leave request and deduct balance if available."""
    emp = employees.get(employee_id)
    if not emp:
        return f"Error: Employee with ID {employee_id} not found."
    if emp['leave_balance'] < days:
        return f"Rejected: Insufficient balance. Requested {days}, only have {emp['leave_balance']}."
    emp['leave_balance'] -= days
    return f"Approved: {days} days of leave deducted. New balance: {emp['leave_balance']} days."

@mcp.tool()
def calculate_monthly_salary(employee_id: int) -> str:
    """Calculate the monthly gross breakdown based on annual salary."""
    emp = employees.get(employee_id)
    if not emp:
        return f"Error: Employee with ID {employee_id} not found."
    monthly = emp['annual_salary'] / 12
    return f"Employee: {emp['name']} | Monthly Gross Salary: INR {monthly:,.2_f}"

# ------------------------------------------------------------
# STEP 4: RESOURCES (CONTEXT / DATA)
# ------------------------------------------------------------
@mcp.resource("uri://policies/leave")
def leave_policy() -> str:
    """Retrieve the standard company leave policy documentation."""
    try:
        with open("data/policies/leave_policy.txt", "r") as f:
            return f.read()
    except FileNotFoundError:
        return "Standard Leave Policy: All employees receive 15 days of annual paid leave."

@mcp.resource("uri://policies/wfh")
def wfh_policy() -> str:
    """Retrieve the current hybrid/remote work-from-home guidelines."""
    try:
        with open("data/policies/wfh_policy.txt", "r") as f:
            return f.read()
    except FileNotFoundError:
        return "WFH Policy: Employees can work remotely up to 2 days per week with manager approval."

# ------------------------------------------------------------
# STEP 5: PROMPTS (TEMPLATES)
# ------------------------------------------------------------
@mcp.prompt()
def employee_leave_summary(employee_name: str) -> str:
    """Generate a structured system prompt template to review an employee's leave pattern."""
    return f"Please analyze the attendance records and prepare a formal leave summary report for {employee_name}."
```

---

# 3.5 Understand the Server Code

### Capability Mapping

| MCP Capability | Function | Responsibility |
| :--- | :--- | :--- |
| **Tool** | `get_employee_details()` | Employee profile lookup |
| **Tool** | `get_leave_balance()` | Live balance check |
| **Tool** | `apply_for_leave()` | Modifies state to process a leave request |
| **Tool** | `calculate_monthly_salary()` | Computes financial metrics |
| **Resource** | `leave_policy()` | Static or dynamic policy reference reading |
| **Resource** | `wfh_policy()` | Remote framework guidelines lookup |
| **Prompt** | `employee_leave_summary()` | Returns pre-structured instructions to the LLM |

### Core Architectural Distinctions
* **Tools** execute functional business logic and can change state (active operations).
* **Resources** serve as contextual data containers for the agent to inspect (read-only datasets).
* **Prompts** offer engineered prompt shortcuts to predictably frame interactions.

---

# 3.6 Run the MCP Server

Ensure you have `uv` installed via its official system commands. Open a terminal panel and launch the server application over a streamable transport layer:

```bash
uv run mcp run server.py --transport streamable-http
```

The underlying network engine will establish local access endpoints at:
`http://localhost:8000/mcp`

*Keep this terminal execution running continuously.*

---

# 3.7 Test Using MCP Inspector

The **MCP Inspector** provides a graphical console interface optimized for executing, reviewing, and debugging registered server components.

In a secondary, independent terminal panel, run:
```bash
npx -y @modelcontextprotocol/inspector
```
<img width="827" height="429" alt="image" src="https://github.com/user-attachments/assets/497d587a-6dcd-46b2-9d8d-ab083b79a5be" />
Open the URL displayed in the terminal and connect to the server.
Launch the administrative portal link presented in the stdout log output to view your tools, run tests, and check payload schemas.

### 3.8 Test Scenarios in MCP Inspector

Use the following test inputs inside the MCP Inspector graphical interface to validate that your server handlers are returning data correctly.

| Capability | Test Input Parameter(s) | Expected Output Format |
| :--- | :--- | :--- |
| **Tool:** `get_employee_details` | `employee_id = 101` | `Name: Rahul Sharma \| Dept: IT \| Role: Software Engineer` |
| **Tool:** `get_leave_balance` | `employee_id = 101` | `Employee Rahul Sharma has 12 days of leave remaining.` |
| **Tool:** `apply_for_leave` | `employee_id = 101`, `days = 3` | `Approved: 3 days of leave deducted. New balance: 9 days.` |
| **Tool:** `calculate_monthly_salary` | `employee_id = 101` | `Employee: Rahul Sharma \| Monthly Gross Salary: INR 100,000.00` |
| **Resource:** `leave_policy` | *None (Select URI)* | `Standard Leave Policy: All employees receive 15 days...` |
| **Resource:** `wfh_policy` | *None (Select URI)* | `WFH Policy: Employees can work remotely up to 2 days...` |
| **Prompt:** `employee_leave_summary` | `employee_name = "Rahul Sharma"` | `Please analyze the attendance records and prepare...` |

> [!NOTE]
> **The Inspector allows you to test the server independently of any AI model.** This isolates your infrastructure code from LLM variability, making it easy to confirm your types, routing, and data access logic function exactly as designed before plugging the server into an agent network.

markdown
# Module 4 – MCP Resources and Prompts (45 minutes)

## 4.1 Understanding MCP Resources
* **What?** Resources are structured, read-only data sources exposed by an MCP server.
* **Why?** AI applications need exact contextual information to answer questions accurately without bloating the core model application logic.
* **How?** The server registers data endpoints using unique Uniform Resource Identifiers (URIs).

### Static vs. Dynamic Resources

| Resource Type | Description | Use Case Example | URI Pattern |
| :--- | :--- | :--- | :--- |
| **Static Resource** | Fixed information that rarely changes. | Company leave policy document | `hr://policies/leave` |
| **Dynamic Resource** | Live data generated on-the-fly using input parameters. | Specific employee ledger records | `hr://employees/{employee_id}` |

### Dynamic Resource Implementation Example
```python
@mcp.resource("hr://employees/{employee_id}")
def employee_resource(employee_id: int) -> str:
    employee = employees.get(employee_id)
    if employee is None:
        return "Employee not found."
    return str(employee)
```
An MCP client can query `hr://employees/101` to instantly pull live runtime parameters into the LLM context window.

---

## 4.2 Understanding MCP Prompts
* **What?** MCP prompts are reusable, server-controlled prompt engineering templates.
* **Why?** They allow organizations to standardize and centralize how an AI assistant handles specialized business tasks, ensuring compliance and stylistic consistency.
* **How?** The client fetches the template from the server, populates runtime arguments, and passes the formatted text instructions to the LLM.

### Prompt Template Implementation Example
```python
@mcp.prompt()
def explain_leave_policy(employee_name: str) -> str:
    """Generate standardized instructions for explaining leave guidelines."""
    return f"""
    Explain the leave policy to {employee_name}.
    Include:
    - Annual leave entitlement.
    - Approval requirements.
    - Leave application procedure.
    """
```

---

## 4.3 Tools vs. Resources vs. Prompts

| Feature | Tools | Resources | Prompts |
| :--- | :--- | :--- | :--- |
| **Primary Purpose** | **Action** (State changes / execution) | **Information** (Context injection) | **Instructions** (Task framing) |
| **Executable Logic** | Yes | Usually no (Read-only data) | No (Text template text) |
| **Context Loading** | Can return execution logs | Yes | Can reference or bundle context |
| **LLM Involvement** | Model dynamically decides to select/call | Host / Client decides when to read | Host / Application decides when to apply |
| **HR System Example** | `apply_for_leave()` | `leave_policy.txt` text | Leave summary analysis template |

### Classroom Discussion Exercise
Present these real-world scenarios to participants and ask them to categorize the appropriate MCP primitive:

1. *Find an employee's salary.* &rarr; **Tool**
2. *Read the company's leave policy.* &rarr; **Resource**
3. *Generate instructions for a performance review.* &rarr; **Prompt**
4. *Submit an employee leave request.* &rarr; **Tool**
5. *Read the WFH policy.* &rarr; **Resource**

---

# Module 5 – Develop an MCP Client (60 minutes)

## Hands-on Coding

### 5.1 What is an MCP Client?
An **MCP Client** is the system component responsible for discovering, initializing, and communicating with an MCP Server.

```text
+-------------------+             +---------------------+             +---------------------+

|   AI Application  | <=========> |     MCP Client      | <=========> |     MCP Server      |
|    (LLM / Host)   |             | (client.py Session) |             | (server.py Process) |
+-------------------+             +---------------------+             +---------------------+
```

* **The Core Problem:** `server.py` successfully exposes capabilities, but an agent network needs an explicit communication gateway to query them.
* **The Solution:** The client opens a secure transport lifecycle (like stdio or HTTP streams) to programmatically discover tools, read resources, fetch prompt configurations, and pipe execution payloads.

### 5.2 Client Development Workflow
Use code with caution.

<FollowUp>
Let's build out the client program! I can help you generate:
* The next text blocks for **5.2 Client Development Workflow** details
* The **complete, copy-pasteable implementation for `client.py`** using `mcp.client.session`
* A **sample agent routine** showing how to parse the server responses and pass them to an LLM provider (like OpenAI or Anthropic)

What would you like to add next?
</FollowUp>
<img width="872" height="523" alt="image" src="https://github.com/user-attachments/assets/f2e8ad17-a615-460a-848d-51d71ff6823d" />

# Module 5 & 6: Model Context Protocol (MCP) Client & Agentic Integration

We will implement five core operations in this module:
1. **Connect to the MCP server.**
2. **Discover available tools.**
3. **Invoke an MCP tool.**
4. **Read MCP resources.**
5. **Retrieve MCP prompts.**

---

## 5.3 Complete MCP Client Code
Create a file named `client.py` and implement the following production-ready asynchronous structure.

```python
import os
import anyio
from dotenv import load_dotenv
from mcp import ClientSession, StdioServerParameters
from mcp.client.stdio import stdio_client
from mcp.types import TextResourceContents

# Load environment variables from a local .env file
load_dotenv()

async def main():
    # ----------------------------------------------------
    # STEP 1: CONFIGURE MCP SERVER CONNECTION PARAMETERS
    # ----------------------------------------------------
    server_params = StdioServerParameters(
        command="python",
        args=["server.py"], 
        env=os.environ.copy()
    )
    
    print("Connecting to server...")
    
    # Establish connection channel via stdio transport layers
    async with stdio_client(server_params) as (read_stream, write_stream):
        async with ClientSession(read_stream, write_stream) as client:
            
            # Initialize connection session with the server
            await client.initialize()
            print("\n================================")
            print("CONNECTED TO HR MCP SERVER")
            print("================================\n")
            
            # ----------------------------------------------------
            # STEP 2: DISCOVER MCP TOOLS
            # ----------------------------------------------------
            print("1. DISCOVERING MCP TOOLS")
            tools_result = await client.list_tools()
            for tool in tools_result.tools:
                print(f"Tool Name: {tool.name}")
                
            # ----------------------------------------------------
            # STEP 3: INVOKE GET EMPLOYEE DETAILS
            # ----------------------------------------------------
            print("\n2. GET EMPLOYEE DETAILS")
            emp_result = await client.call_tool("get_employee_details", {"employee_id": 101})
            if not emp_result.is_error:
                for content in emp_result.content:
                    if isinstance(content, TextResourceContents):
                        print(content.text)
            else:
                print("Error fetching employee details.")

            # ----------------------------------------------------
            # STEP 4: CHECK LEAVE BALANCE
            # ----------------------------------------------------
            print("\n3. CHECK LEAVE BALANCE")
            leave_result = await client.call_tool("get_leave_balance", {"employee_id": 101})
            if not leave_result.is_error:
                for content in leave_result.content:
                    if isinstance(content, TextResourceContents):
                        print(content.text)

            # ----------------------------------------------------
            # STEP 5: CALCULATE MONTHLY SALARY
            # ----------------------------------------------------
            print("\n4. CALCULATE MONTHLY SALARY")
            salary_result = await client.call_tool("calculate_monthly_salary", {"employee_id": 101})
            if not salary_result.is_error:
                for content in salary_result.content:
                    if isinstance(content, TextResourceContents):
                        print(content.text)

            # ----------------------------------------------------
            # STEP 6: DISCOVER AVAILABLE MCP RESOURCES
            # ----------------------------------------------------
            print("\n5. DISCOVERING MCP RESOURCES")
            resources_result = await client.list_resources()
            for resource in resources_result.resources:
                print(f"Resource URI: {resource.uri} | Name: {resource.name}")

            # ----------------------------------------------------
            # STEP 7: READ MCP RESOURCES (Static Policies)
            # ----------------------------------------------------
            print("\n6. READING MCP RESOURCES")
            try:
                res_content = await client.read_resource("policy://leave-policy")
                for content in res_content.contents:
                    if isinstance(content, TextResourceContents):
                        print(content.text)
            except Exception as e:
                print(f"Resource read failed: {e}")

            # ----------------------------------------------------
            # STEP 8: DISCOVER MCP PROMPTS
            # ----------------------------------------------------
            print("\n7. DISCOVER MCP PROMPTS")
            prompts_result = await client.list_prompts()
            for prompt in prompts_result.prompts:
                print("Prompt:", prompt.name)
                
            # ----------------------------------------------------
            # STEP 9: RETRIEVE LEAVE SUMMARY PROMPT
            # ----------------------------------------------------
            print("\n8. RETRIEVE LEAVE SUMMARY PROMPT")
            prompt_result = await client.get_prompt(
                "employee_leave_summary",
                {"employee_name": "Rahul Sharma"}
            )
            for message in prompt_result.messages:
                print("Role:", message.role)
                print("Content:", message.content)
                
            # ----------------------------------------------------
            # STEP 10: COMPLETE
            # ----------------------------------------------------
            print("\n================================")
            print("MCP CLIENT EXECUTION COMPLETED")
            print("================================")

if __name__ == "__main__":
    anyio.run(main)
```

---

## 5.4 Execute the Application
Make sure the MCP server is already running in **Terminal 1**.

Open **Terminal 2** and execute the client script:
```bash
python client.py
```

### Expected Output
The console window will display log outputs structurally formatted as follows:
```text
================================
CONNECTED TO HR MCP SERVER
================================

1. DISCOVERING MCP TOOLS
Tool Name: get_employee_details
Tool Name: get_leave_balance
Tool Name: apply_for_leave
Tool Name: calculate_monthly_salary

2. GET EMPLOYEE DETAILS
Employee Name: Rahul Sharma
Department: IT
Designation: Software Engineer
Annual Salary: 1200000

3. CHECK LEAVE BALANCE
Rahul Sharma has 12 leave days remaining.

4. CALCULATE MONTHLY SALARY
Employee: Rahul Sharma
Annual Salary: 1200000
Monthly Gross Salary: 100000.00
```
*(The remaining output down your console trace will display the static leave policy details, WFH guidelines, and structured prompt descriptions).*

---

## 5.5 Understand Important Client Methods

| Method | Purpose |
| :--- | :--- |
| `Client()` | Establishes an explicit underlying connection interface to an MCP server configuration. |
| `list_tools()` | Discovers all available functional tools exposed by the targeted server instance. |
| `call_tool()` | Invokes a specific functional tool entry point with structured argument variables. |
| `list_resources()` | Discovers contextual database paths, schema layouts, or file resources exposed by the server. |
| `read_resource()` | Reads specific file contents, documents, or live data corresponding to a known resource URI. |
| `list_prompts()` | Discovers available declarative prompt templates structured on the server. |
| `get_prompt()` | Retrieves a highly contextualized prompt template injected with specific runtime input strings. |

> **Production Guidance:** The client SDK always packages typed execution metadata alongside error contexts, specifically via the `is_error` flag on tool-call outputs. In enterprise environments, always validate this property before evaluating content payloads.

---

## 5.6 Understand the Difference: Direct Function Call vs. MCP Tool Call

Consider these two paradigms when executing business operations in an environment:

### Approach 1: Direct Python Function Call
```python
result = get_leave_balance(101)
```
* **Characteristics:** The execution logic must reside natively inside the memory environment of your application workspace, or explicitly imported as an internal static file dependency module.

### Approach 2: MCP Tool Invocation
```python
result = await client.call_tool(
    "get_leave_balance",
    {"employee_id": 101}
)
```
* **Characteristics:** The function payload is securely decoupled and evaluated by completely isolated, decentralized context provider engines over standard transport envelopes. This provides massive decoupled execution scaling capabilities for distributed AI platforms.

---

## 5.7 Hands-On Exercises

Extend the implementation details inside your `client.py` file to systematically demonstrate the following core competencies:

1. **Discover All Available MCP Resources:** Append loops mapping over server-provided items via `client.list_resources()`.
2. **Invoke `apply_for_leave` for Employee 101:** Validate standard operations by parsing a balanced transaction parameters bundle (e.g., applying for `5` days).
3. **Try Applying for 20 Days of Leave:** Intentionally trigger an application rule validation fault by testing an out-of-bounds parameters bundle.
4. **Retrieve the Employee Leave Summary Prompt:** Request the template explicitly via `client.get_prompt()` passing required query fields.
5. **Read the Dynamic Employee Resource:** Retrieve isolated employee objects dynamically from paths matching parameters strings (e.g., `resource://employees/101`).
6. **Implement Error Handling for an Invalid Employee ID:** Wrap call executions in isolated safety try/except wrappers and intercept structural flags returned by processing instances when evaluating bad lookups (e.g., matching missing keys like `999`).

---

## Module 6: Connect MCP with AI Agents (75 Minutes)
### Agentic AI Integration

### 6.1 Why integrate MCP with an AI Agent?
Up to this juncture, our manual MCP client implementation requires programmers to predictively script explicit function calls ahead of execution timelines:

```python
await client.call_tool(
    "get_leave_balance",
    {"employee_id": 101}
)
```

However, imagine a human conversational user presents an ambiguous, multi-layered natural language request:
> *"Check Rahul's leave balance, calculate his monthly salary, and explain whether he can apply for five days of leave."*

Writing rigid conditional pipelines to parse every permutation of multi-part conversational questions becomes incredibly complex. 

Integrating an **AI Agent** addresses this complexity by delegating orchestration logic to an active reasoning core. 

The AI agent seamlessly performs the following continuous loop:
1. **Understand** the nuanced intent behind the natural language instruction.
2. **Identify** required application goals and underlying capabilities.
3. **Select** the most appropriate combination of individual server endpoints.
4. **Invoke** MCP tools autonomously via the transport pipeline without hardcoded sequences.
5. **Interpret** structural JSON payloads or error contexts returning from the server.
6. **Generate** a clean, natural-language response directly answering the user's initial objective.
   
## 6.2 Understand the architecture
<img width="673" height="501" alt="image" src="https://github.com/user-attachments/assets/584c17fa-326e-47d4-b43f-2f124592ab6a" />

# Module 6 – Agentic AI and LangChain Integration

## 6.1 Architectural Breakdown

The architecture consists of the following components:

| Component | Responsibility |
| :--- | :--- |
| **User** | Submits a natural-language request |
| **LLM** | Understands the request and selects tools |
| **Agent** | Coordinates the workflow |
| **MCP Client** | Communicates with the MCP server |
| **MCP Server** | Exposes business capabilities |
| **HR System** | Stores actual business data |

---

## 6.3 MCP vs. LangChain Tools

This is an important architectural distinction for Agentic AI developers:

| LangChain Tool | MCP Tool |
| :--- | :--- |
| Defined locally within an application or specific framework | Exposed globally through an independent MCP server |
| Primarily framework-oriented | Protocol-oriented |
| Application-specific integration | Reusable across any compatible MCP host (Claude, IDEs, etc.) |
| Invoked through a native agent framework | Invoked through a standardized MCP client |

> [!TIP]
> **Key Learning:** An MCP tool can be converted into a LangChain-compatible tool using adapters. This allows any standard framework agent to seamlessly use capabilities exposed by external MCP servers.

---

## 6.4 Install LangChain MCP Integration

Create or extend your `requirements.txt` file to support the framework bridge:

```text
mcp[cli]
anyio
langchain
langchain-openai
langchain-mcp-adapters
python-dotenv
```

Install the requirements:
```bash
pip install -r requirements.txt
```

Configure your secure credentials inside a local `.env` environment file:
```env
OPENAI_API_KEY=your_api_key_here
```

> [!WARNING]
> Do not commit your `.env` file to public GitHub or source control repositories.

---

## 6.5 Develop the AI Agent

Create an `agent.py` script. The following implementation uses the LangChain MCP adapter to dynamically parse remote server capabilities and pass them as tools to an agent executor.

```python
import asyncio
from dotenv import load_dotenv
from langchain_core.messages import HumanMessage
from langchain_openai import ChatOpenAI
from langchain_mcp_adapters.tools import load_mcp_tools
from langgraph.prebuilt import create_react_agent

load_dotenv()

async def main():
    # --------------------------------------------------------
    # STEP 1: INITIALIZE LLM & DISCOVER MCP TOOLS
    # --------------------------------------------------------
    model = ChatOpenAI(model="gpt-4o-mini", temperature=0)
    
    # Connects to the running server via its streamable HTTP endpoint
    mcp_tools = await load_mcp_tools(
        "http://localhost:8000/mcp"
    )

    # --------------------------------------------------------
    # STEP 2: CONSTRUCT FRAMEWORK AGENT
    # --------------------------------------------------------
    agent = create_react_agent(model, tools=mcp_tools)

    # --------------------------------------------------------
    # STEP 3: SUBMIT USER REQUEST
    # --------------------------------------------------------
    user_query = """
    Check employee 101's leave balance.
    Calculate the monthly gross salary.
    Also determine whether the employee can request
    five days of leave.
    """
    
    response = await agent.ainvoke(
        {
            "messages": [
                {
                    "role": "user",
                    "content": user_query
                }
            ]
        }
    )
    
    # --------------------------------------------------------
    # STEP 4: DISPLAY AGENT RESPONSE
    # --------------------------------------------------------
    print("\n=== HR AI ASSISTANT RESPONSE ===\n")
    print(response["messages"][-1].content)

# ------------------------------------------------------------
# STEP 5: RUN APPLICATION
# ------------------------------------------------------------
if __name__ == "__main__":
    asyncio.run(main())
```

> [!NOTE]
> This example uses the LangChain MCP adapter's HTTP transport. **Keep your MCP server running separately in its own terminal window.** The adapter automatically handles network serialization, manages connection handshakes, and dynamically surfaces server-side tools into LangChain format. For the latest API versions and model capabilities, consult the official integration docs.

---

## 6.6 Understand What Happens Behind the Scenes

When a user submits a multi-step prompt:

> **User Request:** *"Check employee 101's leave balance and calculate the monthly salary."*

The agent breaks this down into an iterative sequence loop:

```text
[User Prompt] ──> (LLM reasons) ──> [Call get_leave_balance(101)]
                                              │
(Return breakdown text) <── (LLM reasons) <──┘ (Deducts/Checks state)
         │
         └──> [Call calculate_monthly_salary(101)]
                        │
[Final Answer Context] <┘
```
<img width="544" height="458" alt="image" src="https://github.com/user-attachments/assets/b72d3540-c231-4fa6-ad1e-16c5808969ea" />

## 6.7 Important Distinction: MCP is not the Agent

MCP does not independently understand natural-language requests or decide which tool should be invoked. It acts purely as a structured transport and integration bridge between your business logic and an intelligent client application.

| Feature / Aspect | MCP | AI Agent |
| :--- | :--- | :--- |
| **Connectivity** | Provides standardized connectivity | Understands user requests |
| **Tool Management** | Exposes available tools | Selects which tools to use |
| **Context & Data** | Provides resources | Reasons over context |
| **Prompt Delivery** | Provides template shortcuts | Coordinates actual tasks |
| **Execution** | Executes requested capabilities | Determines the workflow |

> [!IMPORTANT]
> **This distinction is essential when designing Agentic AI architectures.** MCP functions as the sensory organs and limbs (giving access to tools and data paths), while the AI Agent acts as the brain (handling runtime orchestration, reasoning loops, and strategic decision-making).


## 6.8 Updated LangChain Integration

For a modern LangChain implementation, the first-party integration uses `MCPAdapter` directly out of the `langchain[mcp]` namespace. This eliminates the need to maintain an outdated, separate adapter package.

### Installation
Update your environment using the unified extra:

```bash
pip install "langchain[mcp]" langchain-openai python-dotenv
```

### Updated Agent Implementation
Create or replace your `agent.py` script with the following implementation using the modern `MCPAdapter` workflow:

```python
import asyncio
from dotenv import load_dotenv
from langchain.agents import create_agent
from langchain.mcp import MCPAdapter

# Load API key from secure local environment
load_dotenv()

async def main():
    # --------------------------------------------------
    # 1. CONNECT TO HR MCP SERVER
    # --------------------------------------------------
    # Connect to the remote server using the native protocol context adapter
    async with MCPAdapter("http://localhost:8000/mcp") as adapter:
        
        # --------------------------------------------------
        # 2. DISCOVER MCP TOOLS
        # --------------------------------------------------
        tools = await adapter.list_tools()
        print("\nAvailable MCP tools:")
        for tool in tools:
            print(f"- {tool.name}")
            
        # --------------------------------------------------
        # 3. CONSTRUCT AND RUN THE AGENT ARCHITECTURE
        # --------------------------------------------------
        # Pass the dynamically adapted tools directly into the core agent logic
        # agent = create_agent("anthropic:claude-sonnet-5", tools)
        # response = await agent.ainvoke({"messages": [{"role": "user", "content": "..."}]})

# ------------------------------------------------------------
# RUN APPLICATION
# ------------------------------------------------------------
if __name__ == "__main__":
    asyncio.run(main())
```

### Execution
Keep your `server.py` file active in its initial terminal view, then execute the new client workflow script:

```bash
python agent.py
```

The running client agent automatically connects, pulls the remote method signatures, converts them into framework-ready tools on-the-fly, and integrates them seamlessly into the processing routine.

> [!TIP]
> **Core Point:** Notice that your backend business logic in `server.py` remains completely untouched. Because MCP decouples implementation from consumption, you can swap, upgrade, or change your outer agent framework code completely without rewriting or breaking your primary enterprise HR systems.


# Module 7 – End-to-End Project Lab (30 minutes)

## Build an Intelligent HR Assistant using MCP
### Final Practical Assessment

### Business Scenario
An organization wants to develop an HR AI assistant to help employees obtain information and perform basic operations seamlessly. Employees must be able to ask natural-language questions instead of navigating across disparate legacy HR applications.

### Business Requirements

| ID | Requirement | Expected MCP Capability |
| :--- | :--- | :--- |
| **BR01** | Retrieve employee details | Tool |
| **BR02** | Check leave balance | Tool |
| **BR03** | Apply for leave | Tool |
| **BR04** | Calculate monthly salary | Tool |
| **BR05** | Read leave policy | Resource |
| **BR06** | Read WFH policy | Resource |
| **BR07** | Generate leave summary | Prompt |
| **BR08** | Answer employee questions | AI Agent |

---

## Lab Implementation Blueprint

### Task 1 – MCP Server Implementation
* Build a consolidated `server.py` using `MCPServer`.
* Expose transactional endpoints for profile lookups, real-time balances, data mutation (submitting leaves), and breakdown calculators.

### Task 2 – MCP Resources Configuration
* Bind text paths (`data/policies/`) to static URIs like `hr://policies/leave` and `hr://policies/wfh`.
* Implement a parameterized dynamic resource route matching `hr://employees/{employee_id}`.

### Task 3 – MCP Prompts Templates
* Construct an `@mcp.prompt()` helper for standardizing `employee_leave_summary` outputs.
* Build an additional prompt template optimized for explaining specific compensation/salary mechanics.

### Task 4 – MCP Client Logic
* Write a clean `client.py` scripting context that securely initiates a session.
* Discovers and parses capabilities before executing live operations over the stream.

### Task 5 – AI Agent Integration
* Inject discovered server components into LangChain using the modern `MCPAdapter`.
* Validate that an agent can parse text prompts and string together multi-tool pipelines.

---

## 7.1 Validation & Test Scenarios

Execute the following test queries to verify system behaviors:

| Test Case | Target System Prompt / Intent |
| :--- | :--- |
| **TC01** | *"Find the employee details of employee 101."* |
| **TC02** | *"What is employee 101's remaining leave balance?"* |
| **TC03** | *"Calculate employee 101's monthly gross salary."* |
| **TC04** | *"Can employee 101 request five days of leave?"* |
| **TC05** | *"Explain the company's leave policy."* |
| **TC06** | *"What is the WFH policy?"* |
| **TC07** | *"Apply for 20 days of leave for employee 101."* *(Expected boundary error rejection)* |
| **TC08** | *"Give me a summary of employee 102's leave information."* |

### 7.2 Expected Final Interaction

> **Employee:** *"Can I take five days of leave? Also, tell me my remaining leave balance."*

> **HR AI Assistant:** 
> *"I checked your HR records.*
> * *Available leave balance: 12 days.*
> * *Requested leave: 5 days.*
> * *Remaining balance after an approved request: 7 days.*
> 
> *You have a sufficient leave balance to submit the request. However, manager approval is required before the leave is formally confirmed."*

---

## 7.3 Evaluation Rubric

| Assessment Area | Max Score |
| :--- | :--- |
| **MCP Server Core Architecture** | 20 Marks |
| **MCP Functional Tools Engine** | 15 Marks |
| **Resources & Structural Prompts** | 15 Marks |
| **Programmatic MCP Client Flow** | 15 Marks |
| **First-Party AI Agent Integration** | 20 Marks |
| **Boundary Testing & Error Isolation** | 10 Marks |
| **Code Formatting & Clean Architecture** | 5 Marks |
| **Total Practical Score** | **100 Marks** |

---

# 8. Advanced Architectural Concepts

| Topic | Key Learning Objectives |
| :--- | :--- |
| **MCP Security** | Implementation of strict token authorization, user scoping, and least-privilege paradigms. |
| **STDIO Transport** | Understanding single-host, process-isolated input/output communications. |
| **Streamable HTTP** | Orchestrating network-isolated microservices using server-sent events (SSE). |
| **Multi-Server Meshes** | Connecting a standalone agent to concurrent independent backend systems. |
| **Production Scaling** | Migrating away from mock states to persistent pools (PostgreSQL/REST routers). |

### 8.1 Security Guidelines
* **Zero Trust Descriptions:** Never treat schema summaries or context documents as authorization markers. 
* **Human-in-the-Loop (HITL):** Enforce manual workflow confirmation triggers prior to running tools that change financial schemas or resource balances.
* **Input Sanitization:** Guard runtime types strictly; malicious parameter injections should be bounded inside the transport level.

### 8.2 Common Troubleshooting Matrix

| Problem | Root Cause | Actionable Solution |
| :--- | :--- | :--- |
| `ModuleNotFoundError` | Active execution out of context environment bounds. | Run `source venv/bin/activate` and install the package definitions. |
| `Connection Refused` | Client initiated runtime hook prior to target system routing. | Confirm the base server terminal block is alive and listening. |
| `Tool Not Found` | Signature mismatch or failure to instantiate decorator layer. | Ensure `@mcp.tool()` is explicitly loaded during runtime bootstrap. |
| `Invalid Argument` | LLM payload keys don't align with Python parameter hints. | Review precise typing definitions in the implementation. |

---

# 9. Final Project Execution Sequence

To perform a successful class demonstration, execute the files exactly in this sequence:

```bash
# 1. Spin up the underlying microservice
uv run mcp run server.py --transport streamable-http

# 2. Verify endpoints (In a separate shell workspace)
npx -y @modelcontextprotocol/inspector

# 3. Test programmatic client pipelines
python client.py

# 4. Fire up the orchestration intelligence loop
python agent.py
```


