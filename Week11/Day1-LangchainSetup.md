# LangChain Setup – Python Environment

LangChain is a Python framework for developing applications powered by Large Language Models (LLMs). To start developing LangChain applications, we need to install Python, create a virtual environment, install LangChain packages, and configure an LLM provider such as OpenAI.

## 1. Prerequisites

<img width="482" height="227" alt="image" src="https://github.com/user-attachments/assets/349b5548-1814-4617-ab9f-bf1930bebc59" />

## Steps:
  1. ashujauhari@Ashus-MacBook-Air Week11-CTS-AI-RAG % > python3 --version
     
  3. ashujauhari@Ashus-MacBook-Air Week11-CTS-AI-RAG % > pip3 --version
     
  5. ashujauhari@Ashus-MacBook-Air Week11-CTS-AI-RAG % > python3 -m venv venv
       For Mac:
  4.(venv) ashujauhari@Ashus-MacBook-Air Week11-CTS-AI-RAG >source venv/bin/activate
      For Windows:
    (venv) ashujauhari@Ashus-MacBook-Air Week11-CTS-AI-RAG >venv\Scripts\activate
 6. (venv) ashujauhari@Ashus-MacBook-Air Week11-CTS-AI-RAG % pip install langchain langchain-openai python-dotenv
    
 7. (venv) ashujauhari@Ashus-MacBook-Air Day1-Langchain % touch .env
 8. (venv) ashujauhari@Ashus-MacBook-Air Day1-Langchain % pip freeze > requirements.txt

