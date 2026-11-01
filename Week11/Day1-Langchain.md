
# What is Langchain?

Your company has a requirement to build an intelligent, AI-powered application — but if ChatGPT or other LLMs already exist, why d... ourselves?
LLMs are incredible at reasoning, writing, summarizing, and generating ideas. But they come with a few major limitations:

1. They hallucinate if asked questions beyond their training data.
2. They can't access external tools like APIs, databases, or the web on their own.
3. They can't follow conditional logic or workflows — every prompt starts from scratch.
4. They can't mask or filter sensitive responses.
5. They forget context once the chat resets.

So, a plain LLM is like a highly talented brain with no memory, no workflow manager, and no access to the outside world.

A Large Language Model (LLM) such as Claude is just a function: you send text in, you get text back. Real applications need more than that. You need to reuse prompts, plug in different models, parse the output into something your code can use, connect several LLM calls together, and add tools or your own data.

**What LangChain Actually Does?**
LangChain provides a framework to build applications around language models — think of it as the infrastructure layer that turns a raw LLM into a fully functional AI system.

<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/5e48e5dd-a638-4e38-b73b-24d1f0cb8c47" />

It allows you to chain together intelligent components like:
• 🧠 **Memory:** Retain previous conversations, user context, or results.
• 🔧 **Tools:** Let the LLM call APIs, run code, query databases, or fetch live information.
• 🧱 **Retrieval:** Bring your own knowledge base (RAG) into the conversation.
• 🛡️ **Guardrails:** Control model outputs, prevent hallucinations, and validate responses.
• ⚙️ **Control Flow:** Add conditional logic, branching, retries, fallbacks, or even switch models dynamically.



## 3. Business Requirement

The company wants to develop an **AI-Powered Employee Policy Assistant** using LangChain.

The application should allow employees to ask questions in natural language and receive answers based on official company policy documents.

Instead of manually searching through multiple PDF and Word files, employees should simply interact with an AI-powered chatbot.

### 3.1 Functional Requirements

The application should provide the following functionalities:

1. **Natural Language Interaction:** Employees should be able to ask questions using simple, natural language.

2. **Document Understanding:** The application should process HR policy documents available in PDF and Word formats.

3. **Information Retrieval:** The application should identify and retrieve relevant information from the available policy documents.

4. **AI-Powered Response Generation:** The application should use an LLM to generate meaningful and context-aware answers.

5. **Source Reference:** The application should identify the policy document from which the answer was retrieved.

6. **Context-Based Answers:** The chatbot should generate answers based on retrieved policy information rather than relying only on the LLM's pre-trained knowledge.

7. **Unknown Query Handling:** If the requested information is unavailable in the policy documents, the chatbot should inform the employee instead of generating an incorrect answer.

### 3.2 Example Employee Interactions

| Employee Question | Expected AI Assistant Behavior |
|---|---|
| How many casual leaves can I take in a year? | Retrieve the casual leave information from the Leave Management Policy. |
| Can I carry forward unused annual leaves? | Retrieve the leave carry-forward rules. |
| What is the work-from-home policy? | Retrieve the applicable work-from-home guidelines. |
| How can I claim travel expenses? | Retrieve the travel reimbursement procedure. |
| What is the company's maternity leave policy? | Retrieve the maternity leave guidelines. |
| How many days of notice period are required before resignation? | Retrieve the notice period rules. |

### 3.3 Expected Application Workflow

The application should follow this high-level workflow:

1. Employee enters a question in the chatbot.
2. The application processes the employee's question.
3. LangChain uses a retriever to search relevant information from the vector database.
4. Relevant document content is retrieved.
5. The retrieved information and employee question are passed to the LLM.
6. The LLM generates a context-aware response.
7. The chatbot displays the answer along with the source document reference.

### 3.4 Expected Outcome

The company expects an AI-powered chatbot that can:

- Answer employee policy-related questions.
- Retrieve information from multiple HR policy documents.
- Generate human-readable responses.
- Provide references to official policy documents.
- Avoid generating unsupported or misleading policy information.
- Reduce repetitive queries handled by the HR department.

**Business Goal:** Build an intelligent document-based question-answering system using LangChain, LLMs, Embeddings, Vector Databases, and Retrieval-Augmented Generation (RAG).

