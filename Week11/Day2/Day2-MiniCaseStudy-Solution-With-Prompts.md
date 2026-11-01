#TechNova AI-Powered HR Assistant — Prompt Series# 
Prompt 1: Create the project structure and environment
Step 1

Objective: Set up the Python project.

I want to develop an AI-Powered HR Assistant using Python and LangChain. Help me create the initial project structure.

Requirements:

Create a project named hr-assistant.
Create the following files:
main.py
.env
requirements.txt
Use Python as the programming language.
Use LangChain and OpenAI integration.
Include the following dependencies:
langchain
langchain-core
langchain-openai
python-dotenv
Explain how to install the dependencies.
Show how to configure the OpenAI API key using the .env file.
Write a simple Python program to verify that the environment is configured correctly.

Do not implement HR functionality yet. Explain each step clearly for a beginner.

Expected outcome: A working Python project with the required dependencies and API configuration.

Prompt 2: Create mock HR data
Step 2

Objective: Prepare sample employee, policy, and ticket data.

Continue developing the TechNova AI-Powered HR Assistant. Before implementing LangChain tools, create sample HR data using Python dictionaries.

Requirements:

Create an employee dictionary with at least three employees.
Each employee should contain:
Employee ID
Employee name
Department
Designation
Leave balance
Create an HR policy dictionary containing:
Annual leave policy
Leave carry-forward policy
Work-from-home policy
Create an empty dictionary to store HR support tickets.
Initialize a ticket counter starting from 1001.

Use realistic sample data. Write the code in main.py and explain the purpose of each dictionary. Do not implement LangChain tools yet.

Expected outcome: Sample data structures ready for business operations.

Prompt 3: Implement individual LangChain tools
Step 3

Objective: Convert business functions into LangChain tools.

Continue developing the TechNova HR Assistant. Implement the following five LangChain tools using the @tool decorator.

get_employee_details(employee_id)
get_leave_balance(employee_id)
get_hr_policy(policy_type)
create_hr_ticket(employee_id, issue)
get_ticket_status(ticket_id)

Requirements:

Use the @tool decorator from langchain_core.tools.
Define appropriate Python type hints.
Add meaningful descriptions to every tool.
Use the employee, policy, and ticket dictionaries already created.
Implement proper validation for missing or invalid employee IDs.
Generate unique ticket IDs such as HR1001, HR1002, etc.
Return meaningful messages from each tool.
Register all five tools in a Python list.
Create a dictionary to look up tools by their names.
Demonstrate how to execute each tool independently using .invoke().

For each tool, explain:

Its business purpose.
Its input parameters.
Its return value.
How the @tool decorator works.

Do not integrate an LLM yet. Provide complete runnable code.

Expected outcome: Five independently working LangChain tools.

Prompt 4: Create and test ChatPromptTemplate
Step 4

Objective: Define the HR Assistant's behavior.

Continue developing the TechNova HR Assistant. Now implement a LangChain ChatPromptTemplate.

Requirements:

Import ChatPromptTemplate from langchain_core.prompts.
Create a system prompt defining the assistant as an HR Assistant for TechNova Solutions.
Include the following instructions:
Answer employee HR queries.
Use available tools when employee or ticket information is required.
Never invent employee information.
Never invent HR policies.
Ask for missing information.
Use professional and employee-friendly language.
Include a dynamic employee_id variable.
Demonstrate how to create the prompt using ChatPromptTemplate.from_messages().
Demonstrate how to pass dynamic values using invoke().
Print and explain the generated prompt messages.

Use a simple employee query as an example. Do not implement tool calling or conversation memory yet. Provide complete runnable code.

Expected outcome: A reusable prompt template that defines the assistant's role and behavior.

Prompt 5: Implement Conversation Memory
Step 5

Objective: Enable the assistant to remember previous interactions.

Continue developing the TechNova HR Assistant. Implement conversation memory using the modern LangChain message history approach.

Requirements:

Use InMemoryChatMessageHistory from langchain_core.chat_history.
Create an object to store conversation messages.
Store employee messages using HumanMessage.
Store assistant responses using AIMessage.
Retrieve previous messages from memory.
Demonstrate how to maintain conversation history across multiple queries.
Use MessagesPlaceholder in ChatPromptTemplate to insert conversation history dynamically.

Demonstrate the following conversation:

Employee: My employee ID is E101.
Assistant: Hello Rahul! How can I help you?
Employee: What is my leave balance?
Assistant: You have 12 available leaves.
Employee: What is the annual leave policy?
Assistant: Employees are eligible for 18 annual leaves.
Employee: Do you remember my employee ID?
Assistant: Your employee ID is E101.

Explain how the conversation history is stored and reused. Do not integrate tools yet. Provide complete runnable code. Use modern LangChain APIs rather than legacy ConversationBufferMemory.

Expected outcome: Conversation history that can be retrieved and inserted into subsequent prompts.

Prompt 6: Integrate ChatOpenAI with tools
Step 6

Objective: Allow the LLM to select and call tools.

Continue developing the TechNova HR Assistant. Now integrate ChatOpenAI with the five LangChain tools created earlier.

Requirements:

Create a ChatOpenAI object using gpt-4o-mini.
Set temperature to 0.
Use model.bind_tools(tools) to bind the available tools.
Pass an employee query to the model.
Inspect the response's tool_calls property.
Extract the tool name, arguments, and tool call ID.
Find the corresponding tool using the tool dictionary.
Execute the tool using .invoke().
Send the result back to the LLM using ToolMessage.
Invoke the LLM again to generate a natural-language response.

Demonstrate these queries:

What is my leave balance?
What is the annual leave policy?
Create an HR ticket for a salary correction request.
What is the status of ticket HR1001?

Explain the complete tool-calling lifecycle. Do not integrate conversation memory yet. Provide complete runnable code.

Expected outcome: An LLM that can select and execute HR tools based on employee queries.

Prompt 7: Integrate prompt template, tools, and memory
Step 7

Objective: Combine the three main LangChain building blocks.

Continue developing the TechNova AI-Powered HR Assistant. Now integrate the following components:

ChatOpenAI
ChatPromptTemplate
MessagesPlaceholder
LangChain Tools
InMemoryChatMessageHistory
Tool Calling

Requirements:

Maintain conversation history between employee queries.
Insert previous messages into the prompt using MessagesPlaceholder.
Bind all five HR tools to ChatOpenAI.
Send employee queries to the model.
Detect tool calls using response.tool_calls.
Execute requested tools.
Send tool results back using ToolMessage.
Invoke the model again to generate the final response.
Save the employee query and final assistant response in memory.
Support multiple tool calls if the model requests them.

Create a reusable function: process_query(user_input)

This function should:

Accept an employee query.
Retrieve conversation history.
Invoke the prompt and model.
Execute tools when requested.
Return the final assistant response.
Save the completed conversation turn.

Provide complete runnable code. Explain how the components interact with one another.

Expected outcome: A functional conversational HR assistant with tools and memory.

Prompt 8: Build the console application
Step 8

Objective: Create the final user interface.

Continue developing the TechNova AI-Powered HR Assistant. Now build a console-based application around the integrated LangChain implementation.

Requirements:

Display the application title: TechNova HR Support Assistant
Display a welcome message.
Accept employee queries using Python input().
Process each query using process_query().
Display the assistant's response.
Continue accepting queries in a while loop.
Stop when the employee enters "exit".
Handle empty inputs.
Add exception handling.
Keep conversation history during the application session.

Expected interaction:

Employee: My employee ID is E101.
Assistant: Welcome Rahul! How can I help you?
Employee: What is my leave balance?
Assistant: You have 12 available leaves.
Employee: Create an HR ticket for salary correction.
Assistant: Your ticket HR1001 has been created.
Employee: What is my ticket status?
Assistant: Your ticket HR1001 is currently Open.
Employee: Do you remember my previous request?
Assistant: Yes, you previously raised a salary correction request.
Employee: exit
Assistant: Thank you for contacting TechNova HR.

Provide the complete updated main.py file.

Expected outcome: A working console-based HR chatbot.

Prompt 9: Test and debug the application
Step 9

Objective: Validate the application and fix errors.

Review and test the complete TechNova AI-Powered HR Assistant developed using Python and LangChain. Perform a detailed code review.

Check the following areas:

Correct imports and package compatibility.
Correct use of ChatOpenAI.
Correct use of ChatPromptTemplate.
Correct use of MessagesPlaceholder.
Correct use of InMemoryChatMessageHistory.
Correct tool registration.
Correct tool calling using bind_tools().
Correct handling of tool_calls.
Correct ToolMessage creation and tool_call_id matching.
Correct conversation history management.
Correct employee ID handling.
Correct ticket creation and ticket status retrieval.
Correct handling of invalid employee IDs and ticket IDs.
Correct handling of missing information.
Correct application exit behavior.

Test these scenarios:

Valid employee ID.
Invalid employee ID.
Leave balance retrieval.
HR policy retrieval.
Ticket creation.
Ticket status retrieval.
Previous conversation recall.
Missing employee ID.
Empty user input.
Multiple consecutive tool calls.

Identify bugs and explain why they occur. Provide corrected complete code only after explaining the issues. Do not silently remove any existing functionality.

Expected outcome: A tested and corrected application.

Prompt 10: Refactor and document the final application
Step 10 — Final

Objective: Prepare the project for demonstration and future enhancement.

Refactor the completed TechNova AI-Powered HR Assistant into a clean, maintainable Python project.

Requirements:

Separate the application into logical modules:
tools.py
data.py
prompts.py
memory.py
main.py
Keep responsibilities separate:
data.py: Employee, policy, and ticket data.
tools.py: All LangChain tools.
prompts.py: ChatPromptTemplate.
memory.py: Conversation history management.
main.py: LLM integration and console application.
Avoid unnecessary code duplication.
Use meaningful function and variable names.
Add comments to important sections.
Add exception handling.
Keep the application compatible with modern LangChain APIs.
Include requirements.txt.
Include .env.example without an actual API key.
Create a README.md with:
Project overview.
Features.
Project structure.
Installation steps.
Environment configuration.
Execution instructions.
Sample application interaction.
Explain how the application can later be extended to use:
MySQL database.
Persistent conversation history.
Employee authentication.
HR management system APIs.

Provide the complete source code for every file. Ensure all modules work together and the final application can be executed using:

bash
python main.py

Expected outcome: A modular, documented, maintainable HR Assistant project.

Development Roadmap

Here is the recommended order for teaching and implementation.

HR Assistant development journey
Project setup — Prompt 1 — Environment and dependencies
Prepare business data — Prompt 2 — Employees, policies and tickets
Develop tools — Prompt 3 — Five business functions
Develop prompt template — Prompt 4 — Assistant instructions
Implement memory — Prompt 5 — Conversation history
Integrate LLM and tools — Prompt 6 — Tool calling
Integrate all components — Prompt 7 — Prompt + Tools + Memory
Build console application — Prompt 8 — User interaction
Test and debug — Prompt 9 — Quality assurance
Refactor and document — Prompt 10 — Final project
Additional Prompt: How to Work Effectively with AI

Use this instruction at the beginning of your development session.

Act as a Senior Python Developer and LangChain Architect.

I am developing an AI-Powered HR Assistant using Python, LangChain, and OpenAI.

Follow these development rules:

1. Develop the application incrementally, one step at a time.
2. Do not generate the entire application in one response.
3. Before writing code, explain the purpose of the current step.
4. Provide complete runnable code for the current step.
5. Explain important imports, classes, functions, and methods.
6. Use modern LangChain APIs.
7. Do not use deprecated APIs unless explicitly requested.
8. Preserve previously implemented functionality.
9. Do not modify unrelated files or code.
10. After each step, provide test cases and expected output.
11. Wait for my confirmation before proceeding to the next step.
12. If an error occurs, first explain the root cause and then provide the corrected code.

The final goal is to build a working console-based HR Assistant using LangChain Tools, ChatPromptTemplate, ChatOpenAI, Tool Calling, and Conversation Memory.
