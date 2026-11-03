# Multi-Step Reasoning Agent using LangChain

# Business scenario: An employee asks:

"Am I eligible for 5 days of annual leave?"

The application performs multiple steps and carries the output of each step forward to reach the final answer.

## Execution Flow 

<img width="196" height="489" alt="image" src="https://github.com/user-attachments/assets/8f4838e5-30c9-4f71-af49-0727743bbb2c" />



<img width="389" height="492" alt="image" src="https://github.com/user-attachments/assets/5f53a72a-cd4d-42e4-a010-de2e8f3fd138" />

```

"""
Project: Multi-Step HR Leave Eligibility Assistant
Framework: LangChain + LCEL
Model: OpenAI

Business scenario:
An employee wants to know whether they are eligible
for a requested number of annual leave days.

Workflow:
1. Retrieve employee details.
2. Calculate completed service years.
3. Retrieve leave policy.
4. Evaluate leave eligibility.
5. Generate the final answer using an LLM.

<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/9d4f9574-2926-4f03-958a-c56ecbf8e167" />


"""

import os
from datetime import date
from dotenv import load_dotenv

from langchain_openai import ChatOpenAI
from langchain_core.prompts import ChatPromptTemplate
from langchain_core.output_parsers import StrOutputParser
from langchain_core.runnables import RunnableLambda


# ==========================================================
# STEP 1: LOAD ENVIRONMENT VARIABLES
# ==========================================================

load_dotenv()

OPENAI_API_KEY = os.getenv("OPENAI_API_KEY")

if not OPENAI_API_KEY:
    raise ValueError(
        "OPENAI_API_KEY is missing. "
        "Please configure it in your .env file."
    )


# ==========================================================
# STEP 2: INITIALIZE OPENAI MODEL
# ==========================================================

MODEL_NAME = os.getenv("OPENAI_MODEL", "gpt-4o-mini")

llm = ChatOpenAI(
    model=MODEL_NAME,
    temperature=0,
    api_key=OPENAI_API_KEY
)


# ==========================================================
# STEP 3: MOCK EMPLOYEE DATABASE
# ==========================================================

EMPLOYEES = {
    "EMP101": {
        "employee_id": "EMP101",
        "name": "Rahul",
        "department": "IT",
        "joining_date": "2022-06-15",
        "leave_balance": 12
    },

    "EMP102": {
        "employee_id": "EMP102",
        "name": "Priya",
        "department": "HR",
        "joining_date": "2026-05-10",
        "leave_balance": 2
    },

    "EMP103": {
        "employee_id": "EMP103",
        "name": "Amit",
        "department": "Finance",
        "joining_date": "2024-01-10",
        "leave_balance": 20
    }
}


# ==========================================================
# STEP 4: MOCK HR LEAVE POLICY DATABASE
# ==========================================================

LEAVE_POLICIES = {
    "ANNUAL": {
        "leave_type": "Annual Leave",
        "minimum_service_years": 1,
        "annual_entitlement": 18,
        "description": "Annual paid leave for eligible employees."
    }
}


# ==========================================================
# STEP 5: RETRIEVE EMPLOYEE DETAILS
# ==========================================================

def retrieve_employee(data):

    print("\n" + "=" * 55)
    print("STEP 1: RETRIEVING EMPLOYEE DETAILS")
    print("=" * 55)

    employee_id = data["employee_id"]

    employee = EMPLOYEES.get(employee_id)

    if employee is None:
        raise ValueError(
            f"Employee ID {employee_id} does not exist."
        )

    print("Employee ID:", employee["employee_id"])
    print("Employee Name:", employee["name"])
    print("Department:", employee["department"])
    print("Joining Date:", employee["joining_date"])
    print("Leave Balance:", employee["leave_balance"])

    # Preserve original input and add employee information.
    return {
        **data,
        "employee": employee
    }


# ==========================================================
# STEP 6: CALCULATE COMPLETED SERVICE YEARS
# ==========================================================

def calculate_service(data):

    print("\n" + "=" * 55)
    print("STEP 2: CALCULATING EMPLOYEE SERVICE")
    print("=" * 55)

    joining_date = date.fromisoformat(
        data["employee"]["joining_date"]
    )

    today = date.today()

    completed_years = today.year - joining_date.year

    # Check whether the work anniversary has occurred.
    if (today.month, today.day) < (
        joining_date.month, joining_date.day
    ):
        completed_years -= 1

    if completed_years < 0:
        raise ValueError("Joining date cannot be in the future.")

    print("Joining Date:", joining_date)
    print("Current Date:", today)
    print("Completed Service Years:", completed_years)

    # Pass previous data and add calculated service years.
    return {
        **data,
        "completed_years": completed_years
    }


# ==========================================================
# STEP 7: RETRIEVE LEAVE POLICY
# ==========================================================

def retrieve_leave_policy(data):

    print("\n" + "=" * 55)
    print("STEP 3: RETRIEVING LEAVE POLICY")
    print("=" * 55)

    policy = LEAVE_POLICIES.get("ANNUAL")

    if policy is None:
        raise ValueError("Annual leave policy not found.")

    print("Leave Type:", policy["leave_type"])
    print(
        "Minimum Service Required:",
        policy["minimum_service_years"],
        "years"
    )
    print(
        "Annual Leave Entitlement:",
        policy["annual_entitlement"],
        "days"
    )

    return {
        **data,
        "policy": policy
    }


# ==========================================================
# STEP 8: EVALUATE LEAVE ELIGIBILITY
# ==========================================================

def evaluate_eligibility(data):

    print("\n" + "=" * 55)
    print("STEP 4: EVALUATING LEAVE ELIGIBILITY")
    print("=" * 55)

    completed_years = data["completed_years"]

    requested_days = data["requested_days"]

    leave_balance = data["employee"]["leave_balance"]

    minimum_service = (
        data["policy"]["minimum_service_years"]
    )

    # Business rule 1: Check minimum service requirement.
    service_eligible = (
        completed_years >= minimum_service
    )

    # Business rule 2: Check available leave balance.
    balance_eligible = (
        leave_balance >= requested_days
    )

    # Both conditions must be satisfied.
    eligible = service_eligible and balance_eligible

    # Identify the reason for the result.
    if not service_eligible:

        reason = (
            "Employee has not completed the minimum "
            "required service period."
        )

    elif not balance_eligible:

        reason = (
            "Employee does not have sufficient "
            "annual leave balance."
        )

    else:

        reason = (
            "Employee meets the service and leave "
            "balance requirements."
        )

    print("Completed Service:", completed_years, "years")
    print("Requested Leave:", requested_days, "days")
    print("Available Balance:", leave_balance, "days")
    print("Service Eligible:", service_eligible)
    print("Balance Eligible:", balance_eligible)
    print("Final Eligibility:", eligible)
    print("Reason:", reason)

    return {
        **data,
        "eligible": eligible,
        "reason": reason
    }


# ==========================================================
# STEP 9: CREATE LLM PROMPT
# ==========================================================

hr_prompt = ChatPromptTemplate.from_template(
    """
    You are a professional HR Assistant.

    An employee has asked about annual leave eligibility.

    Use the following verified HR information to
    generate a clear and professional response.

    Employee Details:
    Employee ID: {employee_id}
    Employee Name: {employee_name}
    Department: {department}

    Leave Details:
    Leave Type: {leave_type}
    Requested Leave Days: {requested_days}
    Available Leave Balance: {leave_balance}

    Eligibility Information:
    Completed Service Years: {completed_years}
    Minimum Required Service Years: {minimum_service}
    Eligibility Status: {eligibility}
    Reason: {reason}

    Instructions:
    1. Explain whether the employee is eligible.
    2. Mention the requested leave days.
    3. Mention the available leave balance.
    4. Explain the reason for the decision.
    5. Use a professional and friendly tone.
    6. Do not invent HR policies or employee information.
    7. Do not approve or submit a leave application.

    Generate the final response.
    """
)


# ==========================================================
# STEP 10: CREATE LLM RESPONSE CHAIN
# ==========================================================

hr_response_chain = (
    hr_prompt
    | llm
    | StrOutputParser()
)


# ==========================================================
# STEP 11: GENERATE FINAL HR RESPONSE
# ==========================================================

def generate_final_answer(data):

    print("\n" + "=" * 55)
    print("STEP 5: GENERATING FINAL HR RESPONSE")
    print("=" * 55)

    # Prepare the information for the LLM.
    prompt_data = {

        "employee_id": data["employee"]["employee_id"],

        "employee_name": data["employee"]["name"],

        "department": data["employee"]["department"],

        "leave_type": data["policy"]["leave_type"],

        "requested_days": data["requested_days"],

        "leave_balance": data["employee"]["leave_balance"],

        "completed_years": data["completed_years"],

        "minimum_service": (
            data["policy"]["minimum_service_years"]
        ),

        "eligibility": (
            "Eligible" if data["eligible"]
            else "Not Eligible"
        ),

        "reason": data["reason"]
    }

    # Invoke the LLM chain.
    final_answer = hr_response_chain.invoke(prompt_data)

    return {
        "employee_id": data["employee"]["employee_id"],
        "employee_name": data["employee"]["name"],
        "eligible": data["eligible"],
        "final_answer": final_answer
    }


# ==========================================================
# STEP 12: BUILD MULTI-STEP WORKFLOW USING LCEL
# ==========================================================

multi_step_workflow = (

    RunnableLambda(retrieve_employee)

    | RunnableLambda(calculate_service)

    | RunnableLambda(retrieve_leave_policy)

    | RunnableLambda(evaluate_eligibility)

    | RunnableLambda(generate_final_answer)

)


# ==========================================================
# STEP 13: EXECUTE THE MULTI-STEP WORKFLOW
# ==========================================================

def run_hr_agent(employee_id, requested_days):

    print("\n")
    print("*" * 60)
    print("       MULTI-STEP HR LEAVE ELIGIBILITY ASSISTANT")
    print("*" * 60)

    # Validate input.
    if not isinstance(requested_days, int) or isinstance(
        requested_days, bool
    ) or requested_days <= 0:
        raise ValueError(
            "Requested leave days must be a positive integer."
        )

    # Initial input to the workflow.
    employee_request = {
        "employee_id": employee_id,
        "requested_days": requested_days
    }

    # Execute all steps sequentially.
    result = multi_step_workflow.invoke(employee_request)

    # Display final output.
    print("\n")
    print("*" * 60)
    print("                 FINAL HR RESPONSE")
    print("*" * 60)

    print("\nEmployee ID:", result["employee_id"])
    print("Employee Name:", result["employee_name"])

    print("\nEligibility:", result["eligible"])

    print("\nHR Assistant Response:")
    print(result["final_answer"])

    print("\n" + "*" * 60)

    return result


# ==========================================================
# STEP 14: MAIN PROGRAM
# ==========================================================

if __name__ == "__main__":

    try:

        # TEST CASE 1
        run_hr_agent(
            employee_id="EMP101",
            requested_days=5
        )

        # TEST CASE 2
        run_hr_agent(
            employee_id="EMP102",
            requested_days=5
        )

    except Exception as error:

        print("\nAPPLICATION ERROR:", str(error))

```



## Output

************************************************************
       MULTI-STEP HR LEAVE ELIGIBILITY ASSISTANT
************************************************************

=======================================================
**STEP 1: RETRIEVING EMPLOYEE DETAILS**
=======================================================
Employee ID: EMP102
Employee Name: Priya
Department: HR
Joining Date: 2026-05-10
Leave Balance: 2

=======================================================
**STEP 2: CALCULATING EMPLOYEE SERVICE**
=======================================================
Joining Date: 2026-05-10
Current Date: 2026-11-03
Completed Service Years: 0

=======================================================
**STEP 3: RETRIEVING LEAVE POLICY**
=======================================================
Leave Type: Annual Leave
Minimum Service Required: 1 years
Annual Leave Entitlement: 18 days

=======================================================
**STEP 4: EVALUATING LEAVE ELIGIBILITY**
=======================================================
Completed Service: 0 years
Requested Leave: 5 days
Available Balance: 2 days
Service Eligible: False
Balance Eligible: False
Final Eligibility: False
Reason: Employee has not completed the minimum required service period.

=======================================================
**STEP 5: GENERATING FINAL HR RESPONSE**
=======================================================


************************************************************
                 **FINAL HR RESPONSE**
************************************************************

Employee ID: EMP102
Employee Name: Priya

Eligibility: False

HR Assistant Response:
Subject: Annual Leave Eligibility Inquiry

Dear Priya,

Thank you for reaching out regarding your annual leave eligibility. 

After reviewing your request for 5 days of annual leave, I would like to inform you that, unfortunately, you are not eligible for this leave at this time. Currently, you have an available leave balance of 2 days, which is below the requested amount. Additionally, you have completed 0 years of service, while the minimum required service period for annual leave eligibility is 1 year.

If you have any further questions or need assistance with anything else, please feel free to reach out.

Best regards,

[Your Name]  
HR Assistant  
[Your Company]  
[Your Contact Information]  

************************************************************
