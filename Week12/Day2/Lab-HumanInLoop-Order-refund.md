# HumanInLoop

# ------------------------------------------------------------
**Example: Simple Human-in-the-Loop (HITL) Agent using LangChain**
**Topic: Agent pauses for human approval before a risky action**
** Business Scenario:**
**A customer-support agent can look up orders (safe) and**
**issue refunds (risky - money moves!).**

  - check_order_status  -> runs automatically
  - issue_refund        -> PAUSES and asks a human: approve / reject

<img width="572" height="603" alt="image" src="https://github.com/user-attachments/assets/c0bd7a1b-d6af-4cf4-aa9f-36fb16fbfe9f" />

```
mport os

from dotenv import load_dotenv
from langchain.agents import create_agent
from langchain.tools import tool

# Step 2: Load environment variables from .env
load_dotenv()

if not os.getenv("OPENAI_API_KEY"):
    raise ValueError("OPENAI_API_KEY not found in .env file")

print("OpenAI API Key loaded successfully!")

MODEL = "openai:gpt-4o-mini"

# Mock order database
ORDERS = {
    "1001": {"item": "Wireless Mouse", "amount": 1200, "status": "Delivered"},
    "1002": {"item": "Laptop Stand", "amount": 2500, "status": "Shipped"},
    "1003": {"item": "Mechanical Keyboard", "amount": 7500, "status": "Delivered"},
}


# --------------------------------------------------
# Step 3: Human approval helper (the "human in the loop")
# --------------------------------------------------

def ask_human_approval(action: str) -> bool:
    """Pause the agent and ask a human to approve or reject an action."""
    print("\n" + "=" * 60)
    print("  HUMAN APPROVAL REQUIRED")
    print("=" * 60)
    print(f"  Agent wants to: {action}")
    answer = input("  Approve? (y/n): ").strip().lower()
    print("=" * 60 + "\n")
    return answer in ("y", "yes")


# --------------------------------------------------
# Step 4: Define Tools
# --------------------------------------------------

@tool
def check_order_status(order_id: str) -> str:
    """Look up an order by its ID and return item, amount and status."""
    print(f"    [TOOL] Checking order {order_id} (auto-approved, read-only)")
    order = ORDERS.get(order_id)
    if not order:
        return f"Order {order_id} not found."
    return (f"Order {order_id}: {order['item']}, "
            f"Rs.{order['amount']}, status: {order['status']}")


@tool
def issue_refund(order_id: str, reason: str) -> str:
    """Issue a refund for an order. This moves money, so it needs human approval."""
    order = ORDERS.get(order_id)
    if not order:
        return f"Order {order_id} not found. No refund issued."

    # ---- APPROVAL GATE: the agent stops here until a human decides ----
    action = f"refund Rs.{order['amount']} for order {order_id} ({order['item']}) - reason: {reason}"
    if not ask_human_approval(action):
        return "REJECTED by human reviewer. Refund was NOT issued."

    print(f"    [TOOL] Refund of Rs.{order['amount']} issued for order {order_id}")
    return f"Refund of Rs.{order['amount']} issued for order {order_id}."


# --------------------------------------------------
# Step 5: Create the agent
# --------------------------------------------------

agent = create_agent(
    model=MODEL,
    tools=[check_order_status, issue_refund],
    system_prompt=(
        "You are a customer support agent. "
        "Use check_order_status to look up orders. "
        "Use issue_refund only when the customer asks for a refund. "
        "If a refund is rejected by the human reviewer, politely explain "
        "that it could not be processed and suggest contacting a supervisor."
    ),
)


# --------------------------------------------------
# Step 6: Chat loop
# --------------------------------------------------

if __name__ == "__main__":
    print("\nSupport Agent ready. Try: 'Please refund order 1003, the keyboard is faulty'")
    print("Type 'exit' to quit.\n")

    while True:
        user_input = input("You: ").strip()
        if user_input.lower() in ("exit", "quit"):
            break

        result = agent.invoke({"messages": [{"role": "user", "content": user_input}]})
        print(f"\nAgent: {result['messages'][-1].content}\n")

```


**Output**
```
(venv) ashujauhari@Ashus-MacBook-Air HumanInLoopAgent % python refund_hitl_agent.py 
OpenAI API Key loaded successfully!

Support Agent ready. Try: 'Please refund order 1003, the keyboard is faulty'
Type 'exit' to quit.

You: 1003
    [TOOL] Checking order 1003 (auto-approved, read-only)

Agent: Your order 1003 for a Mechanical Keyboard has been delivered. If you need further assistance, feel free to ask!

You: Please refund order 1003, the keyboard is faulty

============================================================
  HUMAN APPROVAL REQUIRED
============================================================
  Agent wants to: refund Rs.7500 for order 1003 (Mechanical Keyboard) - reason: faulty keyboard
  Approve? (y/n): y
============================================================

    [TOOL] Refund of Rs.7500 issued for order 1003

Agent: The refund of Rs.7500 for order 1003 has been successfully issued. If you have any further questions or need assistance, feel free to ask!

You: exit
```
