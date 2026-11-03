
"""
============================================================
PROJECT: HR EMPLOYEE LEAVE REQUEST PROCESSING
============================================================

OBJECTIVE:
Develop an HR assistant using LangChain that processes
employee leave requests, protects sensitive information,
and generates structured JSON responses.

WHAT THIS PROGRAM DOES:

1. Accepts an employee leave request.
2. Identifies and masks Personally Identifiable Information
   (PII), such as email addresses and phone numbers.
3. Uses RunnableLambda to integrate custom preprocessing.
4. Uses ChatPromptTemplate for HR-specific instructions.
5. Uses ChatOpenAI (gpt-4o-mini) to extract leave details.
6. Uses JsonOutputParser to convert the LLM response
   into a Python dictionary.
7. Displays the final structured result.

LANGCHAIN CONCEPTS DEMONSTRATED:

- PII Masking and Text Preprocessing
- RunnableLambda
- ChatPromptTemplate
- ChatOpenAI
- LCEL (LangChain Expression Language)
- JsonOutputParser
- Chain Execution using invoke()

============================================================
"""

# ---------------------------------------------------------
# STEP 0: IMPORT REQUIRED LIBRARIES
# ---------------------------------------------------------

import re
import os

# Path helps locate the .env file relative to this script.
from pathlib import Path

# Load environment variables from the .env file.
from dotenv import load_dotenv

# Import LangChain prompt template.
from langchain_core.prompts import ChatPromptTemplate

# Import JSON output parser.
from langchain_core.output_parsers import JsonOutputParser

# Import RunnableLambda.
from langchain_core.runnables import RunnableLambda

# Import OpenAI Chat Model.
from langchain_openai import ChatOpenAI


# ---------------------------------------------------------
# STEP 1: LOAD OPENAI API KEY
# ---------------------------------------------------------

# Find the directory containing this Python file.
BASE_DIR = Path(__file__).resolve().parent

# Construct the absolute path of the .env file.
ENV_FILE = BASE_DIR / ".env"

# Load environment variables from the specified file.
# override=True gives the .env value precedence over
# an existing environment variable.
load_dotenv(dotenv_path=ENV_FILE, override=True)

# Retrieve the OpenAI API key.
api_key = os.getenv("OPENAI_API_KEY")

# Validate whether the API key exists.
if not api_key or not api_key.strip():

    raise ValueError(
        "\nOPENAI_API_KEY is missing!\n"
        f"Please create a .env file at: {ENV_FILE}\n"
        "Add the following configuration:\n"
        "OPENAI_API_KEY=your_actual_api_key\n"
    )

print("OpenAI API Key loaded successfully!")


# ---------------------------------------------------------
# STEP 2: PREPROCESSING AND PII MASKING
# ---------------------------------------------------------

# Define a function to clean employee messages
# and mask personally identifiable information (PII).
def clean_and_mask(text: str) -> dict:

    # Remove leading and trailing spaces.
    text = text.strip()

    # Identify email addresses and replace with [EMAIL].
    text = re.sub(
        r"\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}\b",
        "[EMAIL]",
        text
    )

    # Identify 10-digit phone numbers and replace with [PHONE].
    text = re.sub(
        r"\b\d{10}\b",
        "[PHONE]",
        text
    )

    # Limit employee message to 2000 characters.
    # Return a dictionary containing the message variable.
    return {
        "message": text[:2000]
    }


# Convert the Python function into a LangChain Runnable.
# RunnableLambda allows custom Python logic inside LCEL.
clean = RunnableLambda(clean_and_mask)


# ---------------------------------------------------------
# STEP 3: CREATE THE HR PROMPT TEMPLATE
# ---------------------------------------------------------

# ChatPromptTemplate creates structured system and human
# messages for the language model.
prompt = ChatPromptTemplate.from_messages([

    # SYSTEM MESSAGE:
    # Defines the HR assistant's role and instructions.
    (
        "system",
        """
        You are an HR assistant at Acme Bank.

        Your task is to analyze employee leave requests
        and extract the relevant leave information.

        Extract the following fields:

        1. leave_type
        2. start_date
        3. end_date
        4. reply

        INSTRUCTIONS:

        - Return ONLY valid JSON.
        - Do not include Markdown code blocks.
        - Use null if the employee did not provide information.
        - Do not assume or invent a leave type.
        - Preserve the dates as stated by the employee.
        - Write a short and professional HR response.
        - Do not attempt to reconstruct masked PII.

        EXPECTED JSON STRUCTURE:

        {{
            "leave_type": null,
            "start_date": null,
            "end_date": null,
            "reply": "Your response here"
        }}
        """
    ),

    # HUMAN MESSAGE:
    # Inserts the cleaned employee message dynamically.
    # {message} is populated from clean_and_mask().
    (
        "human",
        "Employee message: {message}"
    )

])


# ---------------------------------------------------------
# STEP 4: INITIALIZE THE OPENAI LANGUAGE MODEL
# ---------------------------------------------------------

# Create an instance of the OpenAI Chat Model.
llm = ChatOpenAI(

    # Specify the OpenAI model.
    model="gpt-4o-mini",

    # Temperature 0 provides more consistent responses.
    temperature=0,

    # Explicitly pass the API key.
    api_key=api_key
)


# ---------------------------------------------------------
# STEP 5: BUILD THE LANGCHAIN LCEL PIPELINE
# ---------------------------------------------------------

# Connect all Runnable components using the pipe operator.
#
# Execution flow:
#
# Employee Message
#       |
#       v
# RunnableLambda (PII Masking)
#       |
#       v
# ChatPromptTemplate
#       |
#       v
# ChatOpenAI
#       |
#       v
# JsonOutputParser
#       |
#       v
# Python Dictionary
#

chain = clean | prompt | llm | JsonOutputParser()


# ---------------------------------------------------------
# STEP 6: EXECUTE THE PIPELINE
# ---------------------------------------------------------

# Sample employee leave request.
employee_message = (
    "Need leave 22nd to 26th Dec, "
    "sister's wedding. raj@acme.com"
)

print("\nORIGINAL EMPLOYEE MESSAGE")
print("-" * 50)
print(employee_message)

# Execute the preprocessing function separately
# to demonstrate how PII masking works.
cleaned_message = clean_and_mask(employee_message)

print("\nAFTER PII MASKING")
print("-" * 50)
print(cleaned_message["message"])

# Execute the complete LangChain pipeline.
try:

    result = chain.invoke(employee_message)

    # -----------------------------------------------------
    # STEP 7: DISPLAY THE STRUCTURED RESULT
    # -----------------------------------------------------

    print("\nHR ASSISTANT RESPONSE")
    print("=" * 50)

    # Display the complete Python dictionary.
    print(result)

    print("\nEXTRACTED LEAVE DETAILS")
    print("-" * 50)

    # Display individual JSON fields.
    print("Leave Type :", result.get("leave_type"))
    print("Start Date :", result.get("start_date"))
    print("End Date   :", result.get("end_date"))
    print("HR Reply   :", result.get("reply"))

    print("=" * 50)

except Exception as e:

    # Display an error if the API call or processing fails.
    print("\nError while executing HR Assistant:")
    print(type(e).__name__, ":", str(e))
