
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


## 4. Proposed Technical Solution

The company decides to develop the application using the following technologies:

| Technology | Responsibility |
|---|---|
| Python | Application programming |
| FastAPI | Develop REST APIs |
| LangChain | Connect and coordinate LLM, documents, and retrieval components |
| OpenAI GPT | Understand questions and generate responses |
| Embeddings | Convert policy documents into numerical representations |
| Vector Database | Store and search document embeddings |
| Pydantic | Validate request and response data |



## 5. Understanding LangChain Components

This is one of the most important concepts when introducing **LangChain** to students.

LangChain provides different components that help developers build applications using Large Language Models (LLMs).

Instead of writing everything from scratch, developers can use ready-made LangChain components to perform specific tasks.

The following table explains the relationship between business requirements and LangChain components.

| Requirement | LangChain Component |
|---|---|
| Read PDF documents | Document Loaders |
| Divide documents into chunks | Text Splitters |
| Generate vector representations | Embeddings Integration |
| Store and search vectors | Vector Store Integrations |
| Retrieve relevant information | Retrievers |
| Prepare instructions for LLM | Prompt Templates |
| Connect with GPT | Chat Models |
| Generate structured responses | Output Parsers |

### 5.1 Understanding Each Component
<img width="1024" height="512" alt="image" src="https://github.com/user-attachments/assets/c24bf1cf-0259-4fa5-a826-c043a4eb7167" />

The application will work in two major stages.

Stage 1: Document Processing (One-time or whenever policies change)
1. HR uploads policy documents.
2. LangChain Document Loaders read PDF and Word files.
3. Text Splitters divide large documents into smaller chunks.
4. Embedding models convert chunks into vectors.
5. Vector Database stores the vectors with document metadata.
Stage 2: Employee Question Answering (Whenever an employee asks a question)
1. Employee enters a question.
2. LangChain sends the question for semantic retrieval.
3. Retriever searches the Vector Database.
4. Relevant policy information is retrieved.
5. LangChain sends the question and retrieved context to the LLM.
6. LLM generates a natural-language answer.
7. The application returns the answer with source references.
"

### 5.3 Important Note

LangChain acts as an orchestration framework that connects different components required to build an LLM-powered application.

It does not independently create an LLM or a vector database. Instead, it provides integrations that allow developers to work with different models, document sources, embedding models, and vector stores through a common programming framework.



## Important LangChain Components

This is the important part when introducing **LangChain** to students.

LangChain provides different components that help developers integrate LLMs with documents, embeddings, vector databases, and other services.




## 7. Expected Business Benefits

The AI-Powered Employee Policy Assistant will provide the following business benefits to TechNova Solutions:

1. **24/7 Employee Self-Service Support**
   - Employees can ask HR policy-related questions anytime without waiting for HR executives.

2. **Reduced Repetitive HR Queries**
   - Automates responses to frequently asked employee questions.
   - Allows HR teams to focus on more complex employee-related activities.

3. **Faster Access to Company Information**
   - Employees can quickly find relevant information from company policy documents.

4. **Consistent Policy-Based Responses**
   - Provides answers based on official HR policy documents.
   - Reduces the possibility of inconsistent information.

5. **Improved Employee Experience**
   - Enables employees to interact with an AI-powered chatbot using natural language.
   - Makes accessing HR information easier and more convenient.

6. **Reduced Manual Document Searching**
   - Eliminates the need for employees and HR executives to manually search through multiple PDF and Word documents.


## 8. Step-by-Step Practical Implementation Roadmap

We will develop the AI-Powered Employee Policy Assistant incrementally.

Each step introduces a new technical concept and gradually builds towards the complete application.

| Step | Practical Implementation | Concept Introduced |
|---|---|---|
| 1 | Ask GPT a question | LLM Integration |
| 2 | Manage reusable prompts | Prompt Templates |
| 3 | Load an HR PDF | Document Loaders |
| 4 | Split the PDF content | Text Splitters |
| 5 | Generate embeddings | Embeddings |
| 6 | Store document vectors | Vector Store |
| 7 | Search relevant information | Retriever |
| 8 | Generate answers using retrieved content | RAG (Retrieval-Augmented Generation) |
| 9 | Expose the chatbot through REST APIs | FastAPI |

### 8.1 Implementation Approach

**Step 1: Ask GPT a Question**
- Connect Python application with OpenAI GPT.
- Send a question to GPT.
- Display the generated response.
- Concept: LLM Integration.

**Step 2: Manage Reusable Prompts**
- Create reusable prompt templates.
- Pass dynamic values into prompts.
- Concept: Prompt Templates.

**Step 3: Load an HR PDF**
- Load the company's HR policy PDF document.
- Extract text from the document.
- Concept: Document Loaders.

**Step 4: Split the PDF Content**
- Divide large documents into smaller chunks.
- Configure chunk size and chunk overlap.
- Concept: Text Splitters.

**Step 5: Generate Embeddings**
- Convert document chunks into numerical vectors.
- Understand semantic representations.
- Concept: Embeddings.

**Step 6: Store Document Vectors**
- Store generated embeddings in a vector database.
- Use ChromaDB or FAISS.
- Concept: Vector Store.

**Step 7: Search Relevant Information**
- Accept an employee question.
- Search the vector database for relevant document chunks.
- Concept: Retriever.

**Step 8: Generate Answers Using Retrieved Content**
- Retrieve relevant HR policy information.
- Pass the retrieved content and employee question to GPT.
- Generate a context-aware answer.
- Concept: RAG (Retrieval-Augmented Generation).

**Step 9: Expose the Chatbot Through REST APIs**
- Develop REST endpoints using FastAPI.
- Accept employee questions through API requests.
- Return AI-generated answers as JSON responses.
- Concept: FastAPI Integration.

### 8.2 Final Learning Outcome

After completing all nine steps, students will be able to develop an AI-powered document question-answering application using:

- Python
- OpenAI GPT
- LangChain
- Prompt Templates
- Document Loaders
- Text Splitters
- Embeddings
- Vector Databases
- Retrievers
- RAG
- FastAPI

# LangChain Architecture
LangChain is built on a Modular Architecture. Rather than being a monolithic library, it is a collection of specialized components that can be composed into complex sequences. This modularity allows developers to swap out different LLM providers (e.g., OpenAI, Anthropic, HuggingFace) or different retrieval strategies without rewriting the entire application.


## The Core Components Hierarchy of LangChain

LangChain provides different component categories that work together to build intelligent LLM-powered applications.

The following table explains the core component hierarchy and the responsibility of each layer.

| **Layer** | **Component Category** | **Function** |
|---|---|---|
| **Foundation** | **Model I/O** | Handles interaction with the LLM through Prompts, Models, and Output Parsers. |
| **Orchestration** | **Chains** | The logic layer that sequences multiple components together to execute a workflow. |
| **Intelligence** | **Memory** | Provides the ability to store and retrieve conversational context across interactions. |
| **Agency** | **Tools & Agents** | Enables the LLM to interact with external systems and perform actions using APIs, search, and code execution. |
| **Data** | **Retrieval (RAG)** | Connects the LLM to private or external data sources through document retrieval and vector stores. |

### Understanding Each Layer

#### 1. Foundation – Model I/O

- Responsible for communication between the application and LLM.
- Includes Prompt Templates, Chat Models, and Output Parsers.
- Example: Send an employee's question to OpenAI GPT and receive an answer.

#### 2. Orchestration – Chains

- Combines multiple LangChain components into a workflow.
- Defines the sequence in which components execute.
- Example: Combine a Prompt Template, Chat Model, and Output Parser to generate an answer.

#### 3. Intelligence – Memory

- Maintains conversational context.
- Allows applications to use information from previous interactions.
- Example: Remember an employee's previous question during a conversation.

#### 4. Agency – Tools & Agents

- Allows LLM-powered applications to interact with external systems.
- Tools perform specific operations, while agents can decide which tools to use.
- Example: Use an HR API to retrieve an employee's leave balance.

#### 5. Data – Retrieval (RAG)

- Connects LLMs with external knowledge sources.
- Retrieves relevant information from documents and vector databases.
- Example: Retrieve leave policy information from the company's HR PDF documents.

### Summary

| Layer | Key Components |
|---|---|
| Foundation | Prompts, Models, Output Parsers |
| Orchestration | Chains |
| Intelligence | Memory |
| Agency | Tools, Agents |
| Data | Document Loaders, Embeddings, Vector Stores, Retrievers |

**Note:** This is a conceptual hierarchy for understanding LangChain's building blocks. In modern LangChain development, LCEL, Runnable components, LangGraph, and other abstractions provide additional ways to compose and orchestrate these capabilities.


# Building Blocks of Langchain 
<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/326ee6c1-574e-4873-b684-1974c3585cad" />





