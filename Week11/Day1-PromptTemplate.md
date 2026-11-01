#PromptTemplate

# LangChain Prompt Template

A **LangChain Prompt Template** is a reusable way to build prompts for language models. It provides a clear structure to follow while making it easy to customize prompts and save time.

LangChain's templates help keep prompts consistent and make the model's answers more predictable and reliable.

Over time, **they also improve results by reducing randomness in how prompts are written**.

## Components of a Prompt Template

A prompt template usually consists of two things:

### 1. Text Prompt

A text prompt is a chunk of natural language that provides instructions or information to the language model.

It can be:

- Plain text.
- Text containing placeholders such as `{variable}`.

The placeholders are replaced with actual values when the prompt template is executed.

**Example:**

```text
Explain {topic} in simple words.
```

Here, `{topic}` is a placeholder that can be replaced with values such as:

- Artificial Intelligence
- Machine Learning
- LangChain

### 2. Optional Formatting Rules

Formatting rules allow you to control how the final prompt should look.

For example:

- Bold text.
- Uppercase text.
- Specific text structures.
- Output formatting instructions.

These rules help maintain consistency in the prompts sent to language models.

## Example

Suppose we want to create a reusable prompt for explaining technical concepts.

**Prompt Template:**

```text
You are a technical trainer.

Explain {topic} in simple words.

Provide:
1. Definition
2. Key Features
3. Real-world Example
4. Advantages
```

**Input:**

```python
topic = "LangChain"
```

**Final Prompt:**

```text
You are a technical trainer.

Explain LangChain in simple words.

Provide:
1. Definition
2. Key Features
3. Real-world Example
4. Advantages
```

## Advantages of Prompt Templates

1. **Reusability:** Use the same prompt structure for different inputs.
2. **Consistency:** Maintain a consistent prompting approach.
3. **Customization:** Dynamically replace placeholders with actual values.
4. **Maintainability:** Easily modify prompts in one place.
5. **Time Saving:** Avoid writing similar prompts repeatedly.
6. **Predictability:** Use structured instructions to make model responses more consistent.


# 3 Types of LangChain Prompt Templates
LangChain offers different template classes:

**1. String PromptTemplate** for creating basic prompts.

**2.ChatPromptTemplate** for chat-based prompts with multiple messages.

**3.MessagesPlaceholder** for injecting a dynamic list of messages (such as a conversation history).


## 3.1 String Prompt Template
This generates prompts by filling in blanks in a string, and is perfect for completion-style models that expect a single text input (text-davinci-003 or similar).

You define a prompt with placeholders using standard Python format syntax. At runtime, these get resolved to generate the final prompt string:

```
# Form the Prompt 
# String Template - This generates prompts by filling in blanks in a string, and is perfect for completion-style models that expect a single text input 
from langchain_core.prompts import PromptTemplate

example_prompt = PromptTemplate.from_template("Share an interesting fact about {animal}.")  # infers 'animal' as input variable

# Format the template with a specific animal
filled_prompt = example_prompt.format(animal="octopus")
print(filled_prompt)

#> Share an interesting fact about octopus.
```

```
# Form a Prompt Template
from langchain_core.prompts import PromptTemplate

# Create a String Prompt Template
prompt = PromptTemplate.from_template(
    "You are an expert {subject} trainer. Explain {topic} with a simple example."
)

# Provide input values
formatted_prompt = prompt.format(
    subject="Python",
    topic="OOP Concepts"
)

print(formatted_prompt)
```


```
# Mulitple Parameter
from langchain_core.prompts import PromptTemplate

prompt = PromptTemplate(
    input_variables=["role", "topic", "level"],
    template="""
    Act as a {role}.

    Explain {topic} for {level} level students.

    Provide:
    1. Definition
    2. Explanation
    3. Real-world example
    4. Python code example
    """
)

result = prompt.format(
    role="Python Trainer",
    topic="Inheritance",
    level="Beginner"
)

print(result)
```


```
# Prompt with LLM
from langchain_core.prompts import PromptTemplate

prompt = PromptTemplate(
    input_variables=["role", "topic", "level"],
    template="""
    Act as a {role}.

    Explain {topic} for {level} level students.

    Provide:
    1. Definition
    2. Explanation
    3. Real-world example
    4. Python code example
    """
)

result = prompt.format(
    role="Python Trainer",
    topic="Inheritance",
    level="Beginner"
)

print(result)


### PromptTemplate Method

from langchain_core.prompts import PromptTemplate

# ==========================================================
# 1. from_template()
# PURPOSE:
# Creates a reusable prompt template from a normal string.
# Variables inside {} are treated as dynamic input variables.
# ==========================================================

prompt = PromptTemplate.from_template(
    """
    You are an expert {role}.

    Explain the topic: {topic}

    Target Audience: {level}

    Provide:
    1. Definition
    2. Explanation
    3. Real-world example
    """
)


# ==========================================================
# 2. input_variables
# PURPOSE:
# Returns a list of all dynamic variables used in the
# prompt template.
# Helps identify what input values are required.
# ==========================================================

print("\n1. INPUT VARIABLES")
print("-----------------------------")

print(prompt.input_variables)


# ==========================================================
# 3. format()
# PURPOSE:
# Replaces the dynamic variables in the template with
# actual values supplied by the application.
# Returns the final formatted prompt as a Python string.
# ==========================================================

print("\n2. FORMAT METHOD")
print("-----------------------------")

formatted_prompt = prompt.format(
    role="Python Trainer",
    topic="Object Oriented Programming",
    level="Beginner"
)

print(formatted_prompt)


# ==========================================================
# 4. format_prompt()
# PURPOSE:
# Formats the template by replacing dynamic variables.
# Unlike format(), it returns a PromptValue object
# (StringPromptValue) instead of a normal string.
# Useful when passing formatted prompts to an LLM.
# ==========================================================

print("\n3. FORMAT_PROMPT METHOD")
print("-----------------------------")

prompt_value = prompt.format_prompt(
    role="Java Trainer",
    topic="Inheritance",
    level="Intermediate"
)

print(prompt_value)

# PURPOSE:
# to_string() converts the PromptValue object into
# a normal Python string for display or inspection.

print("\nConverted into String:")
print(prompt_value.to_string())


# ==========================================================
# 5. invoke()
# PURPOSE:
# Executes the prompt template with the supplied input
# dictionary and returns a formatted PromptValue object.
# It is commonly used when building LangChain chains.
# Note: It does NOT call an LLM by itself.
# ==========================================================

print("\n4. INVOKE METHOD")
print("-----------------------------")

result = prompt.invoke({
    "role": "AI Trainer",
    "topic": "Generative AI",
    "level": "Beginner"
})

print(result)

# Convert PromptValue into a normal Python string.

print("\nConverted into String:")
print(result.to_string())


# ==========================================================
# 6. LLM INTEGRATION (NEXT STEP)
# PURPOSE:
# Connects the prompt template with an LLM.
# The | operator creates a LangChain chain using LCEL.
# invoke() executes the complete chain and generates
# an AI response.
# ==========================================================

# from langchain_openai import ChatOpenAI
#
# llm = ChatOpenAI(model="gpt-4o-mini")
#
# chain = prompt | llm
#
# response = chain.invoke({
#     "role": "AI Trainer",
#     "topic": "LangChain",
#     "level": "Beginner"
# })
#
# print(response.content)





-----------------------------------------------------------------------------------------
## ChatPromptTemplate: Chat-Style Prompt with Roles

ChatPromptTemplate is a class provided by LangChain that helps developers create structured, reusable prompts for chat-based Large Language Models (LLMs).

It allows us to define a conversation using different message roles, such as:
- System: Defines the behaviour, personality, and instructions for the AI.
- Human: Represents the user's input or question.
- AI: Represents the assistant's response or an example response.
It also supports dynamic placeholders, allowing us to pass values at runtime instead of hardcoding information into prompts.

###. Why do we need ChatPromptTemplate?
Consider a business application where employees ask questions about company policies.

For example:
- Employee 1: What is the annual leave policy?
- Employee 2: How many sick leaves are allowed?
- Employee 3: What is the maternity leave policy?
We want the AI to behave consistently while answering all these questions.

Instead of writing a new prompt for every employee, we can create a reusable template.

```
System:
You are an HR assistant of TechNova Solutions.
Answer employee questions politely and professionally.

Human:
{question}
```
