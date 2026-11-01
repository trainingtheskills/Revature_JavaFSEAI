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
# String Template - This generates prompts by filling in blanks in a string, and is perfect for completion-style models that expect a single text input 
from langchain_core.prompts import PromptTemplate

example_prompt = PromptTemplate.from_template("Share an interesting fact about {animal}.")  # infers 'animal' as input variable

# Format the template with a specific animal
filled_prompt = example_prompt.format(animal="octopus")
print(filled_prompt)

#> Share an interesting fact about octopus.
```

```
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

