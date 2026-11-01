# 1. What is a Chain in LangChain?
A Chain in LangChain is a sequence of components connected together to perform a specific task.

**Instead of writing separate code to:**
  -  Accept user input.
  - Prepare a prompt.
  - Send the prompt to an LLM.
  - Process the LLM response.
  - Return the final result.
We can connect these components into a single workflow called a Chain.

A chain allows us to combine multiple LangChain building blocks to create an end-to-end application.


# Simple Real-World Example: Employee Policy Assistant Using LangChain

## 1. Business Scenario

Consider an AI-powered **Employee Policy Assistant** used by an organization.

An employee wants to know how many casual leaves they can take in a year. Instead of searching through policy documents or contacting the HR team, the employee can ask the question in natural language.

**Employee question:**

> How many casual leaves can I take in a year?

## 2. What Does the Application Need to Do?

The application needs to perform the following steps:

1. **Accept the employee's question** — receive the question from the employee.
2. **Prepare a prompt** — insert the employee's question into a predefined prompt template.
3. **Send the prompt to an LLM** — pass the prepared prompt to a language model.
4. **Generate an answer** — let the LLM generate a response.
5. **Return the answer** — display the generated response to the employee.

<img width="648" height="461" alt="image" src="https://github.com/user-attachments/assets/8d765f14-4d9b-450d-92ad-0c20adedd596" />

## 3. How Does LangChain Help?

Without LangChain, developers may need to write and manage each step independently.

LangChain allows developers to connect these steps into a **chain**. The output of one component becomes the input to the next component.

For example:

```text
Employee Question
       |
       v
   Prompt Template
       |
       v
       LLM
       |
       v
   Generated Answer
       |
       v
  Employee Response
```

## 4. Understanding the Chain

| Component | Responsibility |
|---|---|
| Employee Question | Supplies the question as input |
| Prompt Template | Creates a structured prompt using the question |
| LLM | Processes the prompt and generates a response |
| Output | Returns the response to the employee |

## 5. Example Interaction

**Employee:**

> How many casual leaves can I take in a year?

**AI Employee Policy Assistant:**

> According to the company's leave policy, employees are entitled to 12 casual leaves per calendar year.

*Note: The number of leaves is illustrative. In a real application, the assistant should retrieve the applicable rule from the organization's approved policy documents.*

## 6. Key Takeaway

A **LangChain chain** connects multiple components into a single workflow.

Instead of manually coordinating prompt creation, LLM invocation, and output handling as separate steps, developers can connect these components and execute them as one sequence.

**In simple terms:**

> LangChain helps connect the steps required to build an LLM-powered application into a reusable workflow.

