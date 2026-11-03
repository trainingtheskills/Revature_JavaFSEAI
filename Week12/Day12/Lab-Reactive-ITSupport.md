# Problem scenario

Imagine an IT company has an AI-powered Helpdesk Assistant.

Employees can ask questions such as:
- "Check the status of my support ticket INC1025."
- "My laptop is not working. Create a support ticket."
- "What is the status of ticket INC1026?"

![Uploading image.png…]()

```

# --------------------------------------------------
# Example 2: IT Helpdesk Support Agent
# Framework: LangChain
# Agent Type: Reactive Agent (ReAct-style)
# --------------------------------------------------

#---------------------------------------------------
#  Problem scenario
# Imagine an IT company has an AI-powered Helpdesk Assistant.

# Employees can ask questions such as:
    #- "Check the status of my support ticket INC1025."
    #- "My laptop is not working. Create a support ticket."
    #- "What is the status of ticket INC1026?"
# The AI Agent should understand the request and select the appropriate tool.
#
# Step 1: Import required libraries

import os
from dotenv import load_dotenv
from langchain.agents import create_agent


# Step 2: Load environment variables

load_dotenv()

if not os.getenv("OPENAI_API_KEY"):
    raise ValueError("OpenAI API key not found!")


# --------------------------------------------------
# Step 3: Define Tool 1 - Check Ticket Status
# --------------------------------------------------

def get_ticket_status(ticket_id: str) -> str:
    """
    Retrieve the status of an IT support ticket.

    Args:
        ticket_id: Unique support ticket ID.

    Returns:
        Ticket status information.
    """

    print(f"\n[TOOL EXECUTED] Checking ticket: {ticket_id}")

    # Mock ticket database
    tickets = {
        "INC1025": "Open - Assigned to Network Team",
        "INC1026": "In Progress - Assigned to Hardware Team",
        "INC1027": "Resolved - Issue fixed"
    }

    status = tickets.get(
        ticket_id.upper(),
        "Ticket ID not found."
    )

    return f"Ticket {ticket_id}: {status}"


# --------------------------------------------------
# Step 4: Define Tool 2 - Create Support Ticket
# --------------------------------------------------

def create_support_ticket(
    employee_name: str,
    issue: str,
    priority: str
) -> str:
    """
    Create a new IT support ticket.

    Args:
        employee_name: Name of the employee.
        issue: Description of the IT issue.
        priority: Issue priority (Low, Medium, High).

    Returns:
        Newly created ticket information.
    """

    print("\n[TOOL EXECUTED] Creating support ticket...")

    # Mock ticket creation
    ticket_id = "INC1028"

    return (
        f"Ticket created successfully! "
        f"Ticket ID: {ticket_id}, "
        f"Employee: {employee_name}, "
        f"Issue: {issue}, "
        f"Priority: {priority}"
    )

# --------------------------------------------------
# Step 5: Create the Reactive AI Agent
# --------------------------------------------------

agent = create_agent(

    # LLM model
    model="openai:gpt-5",

    # Register available tools
    tools=[
        get_ticket_status,
        create_support_ticket
    ],

    # Agent instructions
    system_prompt="""
    You are an intelligent IT Helpdesk Assistant.

    Your responsibilities:

    1. Understand employee IT support requests.
    2. Identify whether the employee wants to
       check a ticket or create a new ticket.
    3. Use get_ticket_status to check ticket status.
    4. Use create_support_ticket to create tickets.
    5. Ask for missing information before calling
       a tool.
    6. Use High, Medium or Low priority.
    7. Never invent ticket information.
    8. Present the final result clearly.
    """
)


# --------------------------------------------------
# Step 6: Interact with the Agent
# --------------------------------------------------

print("\n===================================")
print("     IT HELPDESK AI ASSISTANT")
print("===================================")

user_input = input("\nEnter your IT support request: ")


# --------------------------------------------------
# Step 7: Invoke the Agent
# --------------------------------------------------

result = agent.invoke(
    {
        "messages": [
            {
                "role": "user",
                "content": user_input
            }
        ]
    }
)


# --------------------------------------------------
# Step 8: Display Final Response
# --------------------------------------------------

print("\n===================================")
print("          AGENT RESPONSE")
print("===================================")

print(result["messages"][-1].content)


# --------------------------------------------------
# Step 9: Display Agent Execution Details
# --------------------------------------------------

print("\n===================================")
print("       EXECUTION DETAILS")
print("===================================")

for message in result["messages"]:

    print("\nMessage Type:", type(message).__name__)

    if getattr(message, "tool_calls", None):
        print("Tool Calls:", message.tool_calls)

    if getattr(message, "name", None):
        print("Tool Name:", message.name)
        print("Tool Result:", message.content)

```
