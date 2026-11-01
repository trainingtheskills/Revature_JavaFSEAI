# What Are LangChain Tools?


In LangChain, Tools are functions that allow an AI application to interact with external systems, services, APIs, databases, and perform specific operations.

An LLM (Large Language Model) can understand questions, generate text, summarize information, and answer questions. However, it cannot independently perform external operations such as:
- Performing mathematical calculations using a Python function.
- Searching for information on the internet.
- Retrieving customer details from a database.
- Checking weather information using a weather API.
- Sending emails.
- Fetching order details from an e-commerce application.
Tools solve this limitation by allowing an LLM to invoke predefined functions when required.

<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/ba4763cd-816c-4d20-a5f9-f734d0e14e4d" />

## Business use case 
Consider an e-commerce application called TechNova Shopping Assistant.

A customer asks:
**Where is my order ORD101?**

The AI model understands the question, but it does not have direct access to the order database.

We can create a tool called get_order_status() that retrieves order information.

The AI model can then decide to call this tool and use its result to answer the customer.

## Types of LangChain Tools
**1. Search Tools** — Allow models to fetch real-time information from sources like Google Search or Bing.

**2. Database Query Tools** — Help AI interact with structured data sources like SQL databases or vector stores.

**3. API Call Tools** — Enable interaction with external APIs (e.g., weather data, stock prices, etc.).

**4. Mathematical Tools** — Allow AI to perform complex calculations and data processing.

**5. File Processing Tools**— Help analyze documents, PDFs, or CSV files.

**6. Code Execution Tools** — Provide AI the capability to write, execute, and debug code in Python or other languages.
