# Development roadmap
## 1. Project Setup
   ### Prompt 1 — Environment and dependencies
   **Tasks to perform:**
   - Create a Python project named hr-assistant.
   - Create a virtual environment.
   - Install LangChain, LangChain Core, LangChain OpenAI, and Python dotenv.
   - Configure the OpenAI API key using a .env file.
   - Create the initial main.py file.
   - Verify that the Python environment and OpenAI connection work.
   Deliverable: A configured Python project ready for development.
## 2. Prepare Business Data
   ### Prompt 2 — Employees, policies and tickets
    **Tasks to perform: **
   - Create mock employee records using Python dictionaries.
   - Store employee ID, name, department, designation, and leave balance.
   - Create sample HR policies for annual leave, carry-forward, and work from home.
   - Create an empty dictionary to store HR support tickets.
   - Initialize a ticket counter starting from 1001.
   - Test the data retrieval operations.
   Deliverable: Sample HR data structures ready for business operations.
## 3. Develop LangChain Tools
   ### Prompt 3 — Five business functions
    **Tasks to perform:**
   - Implement get_employee_details().
   - Implement get_leave_balance().
   - Implement get_hr_policy().
   - Implement create_hr_ticket().
   - Implement get_ticket_status().
   - Convert each Python function into a LangChain tool using @tool.
   - Add meaningful tool descriptions and type hints.
   - Register all tools in a list.
   - Test each tool independently using .invoke().
   Deliverable: Five independently working LangChain tools.
## 4. Develop Prompt Template
   ### Prompt 4 — Assistant instructions
    **Tasks to perform: **
   - Create a ChatPromptTemplate.
   - Define the assistant's role as an HR Assistant for TechNova Solutions.
   - Specify its responsibilities and business context.
   - Define rules for handling employee queries.
   - Add constraints to prevent invented employee details and HR policies.
   - Introduce dynamic variables such as employee_id.
   - Test the prompt using sample employee queries.
   Deliverable: A reusable prompt template defining the assistant's behavior.
## 5. Implement Conversation Memory
   ### Prompt 5 — Conversation history
    **Tasks to perform:**
   - Implement conversation history using a supported LangChain message-history approach.
   - Store employee messages and assistant responses.
   - Retrieve previous messages.
   - Integrate MessagesPlaceholder into the prompt template.
   - Demonstrate how previous conversation messages are inserted into new prompts.
   - Test whether the assistant can recall the employee ID and previous HR requests.
   Deliverable: A working conversation memory mechanism.
   
   Note: Check the installed LangChain version and use a supported memory API. For persistent production conversations, consider LangGraph checkpointers.
## 6. Integrate LLM and Tools
   ### Prompt 6 — Tool calling
    **Tasks to perform:**
   - Initialize ChatOpenAI using gpt-4o-mini.
   - Bind the five tools using model.bind_tools(tools).
   - Send employee queries to the LLM.
   - Inspect the tool_calls property in the model response.
   - Extract the tool name, arguments, and tool call ID.
   - Execute the selected tool using .invoke().
   - Send the tool result back to the LLM using ToolMessage.
   - Generate the final natural-language response.
   Deliverable: An LLM capable of selecting and executing HR tools.
## 7. Integrate All Components
   ### Prompt 7 — Prompt + Tools + Memory
   T **asks to perform:**
   - Combine ChatOpenAI, ChatPromptTemplate, and LangChain tools.
   - Integrate conversation history into the prompt.
   - Maintain employee context across multiple queries.
   - Execute tools dynamically based on employee questions.
   - Handle multiple tool calls when required.
   - Save the employee query and final assistant response in memory.
   - Create a reusable process_query() function.
   - Test the complete interaction flow.
   Deliverable: An integrated conversational HR Assistant.
## 8. Build Console Application
   ### Prompt 8 — User interaction
    **Tasks to perform:**
   - Create a welcome screen for TechNova HR Support.
   - Accept employee queries using Python input().
   - Process queries through process_query().
   - Display the assistant's responses.
   - Implement a continuous while loop.
   - Handle empty input and invalid requests.
   - Add an exit option to terminate the application.
   - Maintain conversation history throughout the session.
   Deliverable: A complete console-based HR chatbot.
## 9. Test and Debug
   ### Prompt 9 — Quality assurance
   **Tasks to perform:**
   - Test employee ID recognition and validation.
   - Verify employee details against the mock database.
   - Verify leave balances against stored records.
   - Verify HR policy responses against the policy dictionary.
   - Test HR ticket creation and status retrieval.
   - Verify conversation recall.
   - Test missing information and invalid inputs.
   - Inspect tool calls and their returned results.
   - Fix incorrect answers, hallucinations, and memory issues.
   - Resolve deprecated API warnings.
   Deliverable: A tested and corrected HR Assistant application.
## 10. Refactor and Document
    ### Prompt 10 — Final project
    **Tasks to perform:**
    - Separate the application into logical Python modules.
    - Move employee and policy data into data.py.
    - Move LangChain tools into tools.py.
    - Move prompt definitions into prompts.py.
    - Move memory management into memory.py.
    - Keep application orchestration in main.py.
    - Create requirements.txt and .env.example.
    - Write project documentation in README.md.
    - Document installation, configuration, execution, and sample interactions.
    - Identify possible production enhancements such as MySQL, authentication, and persistent memory.
    Deliverable: A modular, documented, maintainable HR Assistant project.
