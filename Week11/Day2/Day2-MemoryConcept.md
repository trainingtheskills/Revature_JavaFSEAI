
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

# SETUP : pip install langchain langchain-openai langchain-classic python-dotenv

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

## B. ConversationBufferMemory
Purpose: Maintains the complete conversation history.

It keeps previous messages and makes them available as context for subsequent interactions.
**Example**
User: My name is Rahul.
AI: Hello Rahul!

User: I am 25 years old.
AI: That's great!

User: What is my name and age?
AI: Your name is Rahul and your age is 25.
```

from dotenv import load_dotenv

from langchain_openai import ChatOpenAI
from langchain_classic.memory import ConversationBufferMemory
from langchain_classic.chains import ConversationChain

# Load environment variables from .env file
load_dotenv()

# 1. Create the LLM
model = ChatOpenAI(
    model="gpt-4o-mini",
    temperature=0
)

# 2. Create ConversationBufferMemory
# Stores the complete conversation history
memory = ConversationBufferMemory(
    memory_key="history",
    return_messages=False
)

# 3. Create ConversationChain
# Connects the LLM with conversation memory
conversation = ConversationChain(
    llm=model,
    memory=memory,
    verbose=True
)

# 4. First interaction
response1 = conversation.predict(
    input="My name is Rahul."
)
print("AI:", response1)

# 5. Second interaction
response2 = conversation.predict(
    input="I am 25 years old."
)
print("AI:", response2)

# 6. Third interaction
# AI can use information from previous interactions
response3 = conversation.predict(
    input="What is my name and age?"
)
print("AI:", response3)

# 7. Display complete conversation history
print("\nComplete Conversation History:")
print(memory.buffer)

```

## C. ConversationBufferWindowMemory
Purpose: Maintains only a limited number of recent conversation interactions.

It is useful when the conversation becomes very long and we want to limit the amount of history passed to the LLM.
```

from dotenv import load_dotenv

from langchain_openai import ChatOpenAI
from langchain_classic.memory import ConversationBufferWindowMemory
from langchain_classic.chains import ConversationChain

# Load environment variables from .env file
load_dotenv()

# 1. Create the LLM
model = ChatOpenAI(
    model="gpt-4o-mini",
    temperature=0
)

# 2. Create ConversationBufferWindowMemory
# Stores only the latest 2 conversation turns
memory = ConversationBufferWindowMemory(
    k=2,
    memory_key="history",
    return_messages=False
)

# 3. Create ConversationChain
# Connects the LLM with conversation memory
conversation = ConversationChain(
    llm=model,
    memory=memory,
    verbose=True
)

# 4. First interaction
response1 = conversation.predict(
    input="My name is Rahul."
)
print("AI:", response1)

# 5. Second interaction
response2 = conversation.predict(
    input="I am 25 years old."
)
print("AI:", response2)

# 6. Third interaction
response3 = conversation.predict(
    input="I work as a Java developer."
)
print("AI:", response3)

# 7. Fourth interaction
response4 = conversation.predict(
    input="What is my age and profession?"
)
```
## D. ConversationSummaryMemory
Purpose: Creates a summary of previous conversations instead of retaining every individual message.

This is useful for long-running conversations.
print("AI:", response4)

# 8. Display the conversation history retained in memory
print("\nConversation History in Memory:")
print(memory.buffer)

<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/a27f598e-e5b5-4f00-a2b7-4c0fc5706b4e" />

```

from dotenv import load_dotenv

from langchain_openai import ChatOpenAI
from langchain_classic.memory import ConversationSummaryMemory
from langchain_classic.chains import ConversationChain

# Load environment variables from .env file
load_dotenv()

# 1. Create the LLM
model = ChatOpenAI(
    model="gpt-4o-mini",
    temperature=0
)

# 2. Create ConversationSummaryMemory
# Uses the LLM to summarize conversation history
memory = ConversationSummaryMemory(
    llm=model,
    memory_key="history",
    return_messages=False
)

# 3. Create ConversationChain
# Connects the LLM with conversation summary memory
conversation = ConversationChain(
    llm=model,
    memory=memory,
    verbose=True
)

# 4. First interaction
conversation.predict(
    input="My name is Rahul."
)

# 5. Second interaction
conversation.predict(
    input="I am 25 years old."
)

# 6. Third interaction
conversation.predict(
    input="I work as a Java developer."
)

# 7. Fourth interaction
conversation.predict(
    input="I have 3 years of experience."
)

# 8. Fifth interaction
conversation.predict(
    input="I want to learn Spring Boot."
)

# 9. Display summarized conversation memory
print("\nSummarized Conversation Memory:")
print(memory.buffer)

# 10. Ask a question using the summarized memory
response = conversation.predict(
    input="What do you know about me?"
)

print("\nAI Response:", response)

```

