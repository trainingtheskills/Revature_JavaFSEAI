
# Memory Concept in LangChain

## 1. What is Memory in LangChain?
Memory in LangChain is a mechanism that allows an AI application to remember information from previous interactions and use that information in future conversations.

Normally, an LLM does not automatically remember previous conversations.

## How memory works?
<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/5a8c4057-ef0a-4b8e-9e01-fc6e5513ea51" />

## 2. Why do we need Memory?
A customer interacts with the chatbot:
**Conversation	Message**
1	My name is Rahul.
2	I purchased a laptop yesterday.
3	My order ID is ORD101.
4	I want to return my laptop.

Without memory, the chatbot may ask the customer to provide the order ID again.
AI: Hello Rahul! I see that you purchased a laptop with order ID ORD101. I can help you initiate the return process.

With memory, the chatbot can use previously shared information:

## Benefits of Memory

1. Maintains conversational context.
   
2. Avoids asking repetitive questions.
   
3. Supports multi-turn conversations.
   
4. Improves personalized responses.
   
5. Helps build conversational AI applications.

## 3. How does Memory work in LangChain?
<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/6061d7a5-b7b7-4747-aaf2-d6ea8b7abe34" />

## 4. Types of Memory in LangChain
LangChain has provided different memory approaches depending on how much conversation information an application needs to retain.


## Types of Memory in LangChain

LangChain provides different memory mechanisms to maintain conversation context in AI applications.

| Memory Type | Purpose | Example |
|---|---|---|
| `ChatMessageHistory` | Stores conversation messages | Stores user and AI messages |
| `ConversationBufferMemory` | Stores the complete conversation | Remembers all previous messages |
| `ConversationBufferWindowMemory` | Stores only the latest K interactions | Remembers the last 5 interactions |
| `ConversationSummaryMemory` | Stores a summarized version of the conversation | Summarizes a long discussion |

### A. ChatMessageHistory
**Purpose:** Stores messages exchanged between the user and AI.

It maintains messages in chronological order.
```
from langchain_community.chat_message_histories import ChatMessageHistory

# Create conversation history
history = ChatMessageHistory()

# Add user message
history.add_user_message("My name is Rahul")

# Add AI response
history.add_ai_message("Hello Rahul! Nice to meet you.")

# Add another user message
history.add_user_message("I am learning LangChain")

# Display conversation history
for message in history.messages:
    print(message.type, ":", message.content)
```
