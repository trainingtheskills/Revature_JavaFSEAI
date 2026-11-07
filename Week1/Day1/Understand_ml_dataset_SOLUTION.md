# Solution: Lab Exercise -- Understand an ML Dataset

**Business Case:** Employee Attrition Prediction -- TechNova Solutions\
**Duration:** 15 minutes

## 1. Understand the Dataset

The dataset contains historical employee information collected by
TechNova Solutions.

Each row represents one employee, and each column represents an
attribute or characteristic of that employee.

  -------------------------------------------------------------------------------
  Employee             Age    Experience    Salary (₹) Satisfaction   Attrition
  ID                             (Years)                              
  ---------- ------------- ------------- ------------- -------------- -----------
  E101                  25             2        30,000 Low            Yes

  E102                  35            10        75,000 High           No

  E103                  28             4        40,000 Medium         Yes

  E104                  42            15        95,000 High           No

  E105                  30             5        45,000 Low            Yes
  -------------------------------------------------------------------------------

### Explanation

-   There are **5 employee records**.
-   Each employee represents one observation or data point.
-   There are **6 columns (variables)**.
-   The dataset contains employee demographic, professional,
    compensation, and satisfaction information.

## 2. Identify the Dataset

**Answer:** Employee Attrition Dataset.

It is a structured, tabular dataset because information is organized
into rows and columns.

  -----------------------------------------------------------------------
  Dataset characteristic              Answer
  ----------------------------------- -----------------------------------
  Dataset name                        Employee Attrition Dataset

  Number of records                   5

  Number of columns                   6

  Data format                         Structured / Tabular

  Business domain                     Human Resource Management

  ML problem                          Classification

  Business objective                  Predict whether an employee will
                                      leave the organization
  -----------------------------------------------------------------------

## 3. Identify the Features

Features are the input variables that an ML model uses to learn patterns
and make predictions.

  Feature        Description                        Data type
  -------------- ---------------------------------- -------------
  Age            Employee's age                     Numerical
  Experience     Years of professional experience   Numerical
  Salary         Employee's salary                  Numerical
  Satisfaction   Employee satisfaction level        Categorical

The four predictive features are:

1.  Age
2.  Experience
3.  Salary
4.  Satisfaction

**Important:** Employee ID is an identifier. It is generally excluded
from predictive features because it does not represent a meaningful
employee characteristic. In a real project, identifiers should also be
checked for accidental data leakage or other unintended effects.

## 4. Identify the Target Variable

The target variable is the value that the ML model needs to predict.

**Answer: `Attrition`**

Possible values:

-   **Yes:** The employee left the organization.
-   **No:** The employee stayed with the organization.

This is a **binary classification problem** because the target variable
has two possible classes.

### Model input and output

**Input features:** - Age - Experience - Salary - Satisfaction

**Historical training data:** Employee records with known attrition
outcomes.

**Predicted output:** - `Yes` -- predicted to leave. - `No` -- predicted
to stay.

The output is a prediction, not a certainty.

## 5. Why Is Historical Data Required?

Historical data is required because machine learning algorithms learn
patterns from previously collected examples.

Consider these observations:

  Employee ID   Satisfaction     Salary (₹) Attrition
  ------------- -------------- ------------ -----------
  E101          Low                  30,000 Yes
  E102          High                 75,000 No
  E103          Medium               40,000 Yes
  E104          High                 95,000 No
  E105          Low                  45,000 Yes

The ML algorithm examines the relationship between employee
characteristics and recorded attrition outcomes.

It may identify patterns such as:

-   Employees with low satisfaction have higher attrition in this small
    sample.
-   Employees with high satisfaction have remained in the organization
    in this sample.
-   Salary and experience may also provide useful information.

### Why historical data matters

1.  Provides examples for the ML algorithm to learn from.
2.  Helps identify relationships between features and outcomes.
3.  Enables the model to learn patterns associated with employee
    attrition.
4.  Allows us to evaluate model performance using unseen historical
    records.

**Important:** Five records are sufficient for understanding the
concept, but not for building a reliable real-world prediction model. A
production model needs a much larger, representative dataset.

Also, these patterns indicate association, not necessarily causation. A
model cannot establish the true reasons employees leave simply by
finding correlations.

## 6. How Can a Trained Model Predict Attrition for a New Employee?

Suppose TechNova hires a new employee with the following information:

  Feature        Value
  -------------- ---------
  Age            27
  Experience     3 years
  Salary         ₹35,000
  Satisfaction   Low

The prediction process works as follows.

### Step 1: Collect employee information

HR provides the employee's age, experience, salary, and satisfaction
level.

### Step 2: Prepare input data

The information is converted into a format that the ML model
understands. For example, satisfaction levels may be converted into
numerical values using an encoding technique.

### Step 3: Pass data to the trained model

The trained model analyzes the input features using patterns learned
from historical employee records.

### Step 4: Generate a prediction

`<badge color="info">`{=html}Illustrative prediction
only`</badge>`{=html}

  Prediction field    Example output
  ------------------- -------------------------------
  Business use case   Employee Attrition Prediction
  Predicted class     **Yes**
  Interpretation      Likely to leave

This is an illustration of the prediction process. It is not an actual
model result or a validated risk assessment.

### Step 5: HR takes appropriate action

HR might:

-   Conduct an employee engagement discussion.
-   Review compensation and benefits.
-   Understand employee concerns.
-   Explore opportunities for training or career development.

The prediction should support HR decision-making, not automatically
determine employment decisions.

## 7. Prompt for AI-Assisted Learning

Participants can use ChatGPT or another LLM to understand the dataset.

### Sample prompt

``` text
Act as a Machine Learning instructor.

I am a beginner learning Machine Learning.

Consider the following business case:

TechNova Solutions wants to predict employee attrition
using historical employee data.

Dataset:

| Employee | Age | Experience | Salary | Satisfaction | Attrition |
|----------|-----|------------|--------|--------------|-----------|
| E101     | 25  | 2          | 30000  | Low          | Yes       |
| E102     | 35  | 10         | 75000  | High         | No        |
| E103     | 28  | 4          | 40000  | Medium       | Yes       |
| E104     | 42  | 15         | 95000  | High         | No        |
| E105     | 30  | 5          | 45000  | Low          | Yes       |

Explain the following in simple language with practical examples:

1. What is a dataset?
2. What is a feature?
3. What is a target variable?
4. Why is Attrition a target variable?
5. Why is this a classification problem?
6. How does an ML algorithm learn from historical data?
7. How can the trained model predict attrition for a new employee?
8. What are the limitations of using only five employee records?

Finally, create three new employee records and ask me to
identify their features and predict the possible target variable.

Do not reveal the answers until I attempt the exercise.
```

## 8. Additional Knowledge: How Business Data Becomes ML Input

This is one of the most important concepts participants should
understand.

  Business concept                                  Machine Learning terminology
  ------------------------------------------------- ------------------------------
  Employee information                              Dataset
  Age, salary, experience, satisfaction             Features
  Employee attrition status                         Target variable
  Historical employee records with known outcomes   Training data
  ML algorithm                                      Learning algorithm
  Trained ML algorithm                              ML model
  New employee information                          New input data
  Predicted attrition                               Model prediction

### End-to-end conceptual flow

``` text
Business Problem
      |
      v
Employee Attrition Prediction
      |
      v
Historical Employee Dataset
      |
      v
Identify Features and Target
      |
      +----------------------+
      |                      |
      v                      v
 Input Features         Target Variable
 Age                    Attrition
 Experience             Yes / No
 Salary
 Satisfaction
      |                      |
      +----------+-----------+
                 |
                 v
          Training Data
                 |
                 v
           ML Algorithm
                 |
                 v
            ML Training
                 |
                 v
             ML Model
                 |
                 v
       New Employee Information
                 |
                 v
        Model Prediction
                 |
                 v
          Predicted Attrition
              Yes / No
```

## 9. Expected Learning Outcomes

By the end of this exercise, participants should be able to explain:

-   What a dataset is and how it is structured.
-   The difference between features and target variables.
-   Why historical data is important in supervised learning.
-   Why employee attrition prediction is a classification problem.
-   How an ML model uses input features to predict an outcome.

## Trainer's Key Takeaway

A business problem becomes a Machine Learning problem when we identify:

1.  **What information is available?** --- Features.
2.  **What outcome do we want to predict?** --- Target.
3.  **What historical examples can teach the algorithm?** --- Training
    Data.

**Business Problem → Dataset → Features + Target → ML Training →
Prediction**

### Instructor Note

Reinforce the distinction between an algorithm and a model:

-   An **ML algorithm** is the learning procedure used to find patterns
    in training data.
-   An **ML model** is the learned result of applying that algorithm to
    the training data.

In this example, a classification algorithm learns from employee records
with known attrition labels. The resulting model can then predict an
attrition class for new employee data.
