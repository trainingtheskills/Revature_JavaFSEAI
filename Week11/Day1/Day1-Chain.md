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

## . Important Components Used in a Chain

| Component | Purpose |
|---|---|
| `PromptTemplate` | Creates a reusable text prompt |
| `ChatPromptTemplate` | Creates structured chat messages |
| `LLM` | Generates text responses |
| `OutputParser` | Converts model output into the required format |
| `Runnable` | Represents a component that can execute as part of a chain |
| `Chain` | Connects multiple components into a workflow |


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



## Legacy chains are still in the curriculum though, so:

### LLMChain - The most basic and general-use chain - 

it connects an LLM with a prompt template. It’s a single-step chain, which means it takes in one input and returns one output.
LLMChain is a fundamental building block in more complex chains. These days, we don’t use it directly too often. Usually we’ll just do something like this for a generic chain:

An LLMChain is implied here, no need for explicit invocation.

**SimpleSequentialChain** - The output of chain A becomes the only input to Chain B, and so on. It’s a simple sequence.
Since there’s only one input and output for each chain, it’s good for simple tasks that benefit from multiple steps. For instance, Chain A takes in raw data to create an outline, then passes it to Chain B to write and return an article. 

*Note the use of LLMChain here!... also since direct use of LLMChain is deprecated,  SequentialChains aren’t really directly used either! We’ll see an easier way in the demo
chain =  chain1 | chain2 | … | chain500

**SequentialChain** - This chain is like SimpleSequentialChain, but with more flexibility and power. 
Outputs are stored globally instead of becoming the input for the next chain. You can also have multiple inputs/outputs in a chain, and pass variables between chains.
[pic]
*Note how each chain defines its own output_key that can be called on after invocation!

**LLMMathChain** - The aptly named LLMMathChain is good to use when working with numbers and calculations. The LLM decides what kind of mathematical problem it’s solving, but offloads the actual computation to a calculator under the hood.
Math Chains can be helpful in more complex scenarios like word problems, business-related forecasting, or general quantitative decision making, but if you want mathematical responses, you can use a general chain or a tool (week 5 content).

Good use of LLMMathChain:
“If an evil scientist sells 75 Doomsday Devices at $120 each, what is the total revenue?”
Word problem format, no guesswork required from the LLM. It has all the info it needs.
Bad use of LLMMathChain:
“How long would it take me to get to the moon?”
More of a generic query. The LLM has to assume things like distance and speed, so it might be prone to making stuff up.

**LLMRouterChain** - This chain’s main use case is to route user requests to the appropriate sub-chain. This helps us create versatile apps that are accurate across a wide array of capabilities.
Imagine we have different chains for summarizing, translating, and general chatting. A properly configured LLMRouterChain knows the appropriate chain to invoke if the user sends the prompt “Take this English text and return it in Spanish.”.
[pic]

**TransformChain** - This chain doesn’t actually call the LLM at all. It’s just a regular python function that we can fit in our overall chain.
This is useful for input preprocessing, LLM response postprocessing, data cleaning, really anything you might need to do with plain python (which tends to be data transformation, hence the name).

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

