# Lab Exercise: Understand an ML Dataset

**Duration:** 15 minutes\
**Business Case:** Employee Attrition Prediction\
**Organization:** TechNova Solutions

## 1. Business Scenario

TechNova Solutions wants to understand why employees leave the
organization. The HR team has observed employee attrition and wants to
use historical employee data to identify patterns that may help predict
whether a new employee is likely to leave.

In this exercise, you will examine a small sample dataset and identify
how its information can be used to train a Machine Learning (ML) model.

## 2. Sample Dataset

Each row represents one employee. Each column contains an attribute
describing that employee or the outcome HR wants to predict.

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

**Note:** This is a small illustrative dataset created for learning. A
real ML model would require many more representative records and careful
data preparation.

## 3. Participant Activities

Work individually or in pairs. Review the dataset and answer the
following questions.

### Activity 1: Identify the Dataset

1.  What does each row in the dataset represent?
2.  What business problem is TechNova Solutions trying to solve?
3.  What does the `Attrition` column tell us?

### Activity 2: Identify the Features

1.  Identify all the columns that could be used as input features.
2.  Which features contain numerical data?
3.  Which feature contains categorical data?
4.  Why might age, experience, salary, and satisfaction be useful when
    studying attrition?

### Activity 3: Identify the Target Variable

1.  Which column is the target variable?
2.  What are the possible values of the target variable?
3.  Is this an example of a classification or regression problem?
    Explain your answer.

### Activity 4: Understand the Need for Historical Data

1.  Why does an ML model need historical employee records?
2.  How can historical records help the model identify patterns?
3.  What could happen if the training dataset contains incorrect,
    incomplete, or biased information?

### Activity 5: Predict for a New Employee

Assume TechNova Solutions has a new employee with the following details:

  Feature        Value
  -------------- ---------
  Age            27
  Experience     3 years
  Salary         ₹35,000
  Satisfaction   Low

1.  What information would you provide to the trained model?
2.  What value would the model be asked to predict?
3.  Based on the sample dataset, what might your initial guess be?
    Explain your reasoning.
4.  Why should this initial guess not be treated as a reliable ML
    prediction?

## 4. Prompt for AI-Assisted Learning

Open ChatGPT or another available AI assistant. Use the following prompt
to explore the dataset.

> Act as a Machine Learning instructor. I am a beginner learning the
> fundamentals of ML.
>
> Analyze the following Employee Attrition dataset:
>
>   Employee ID     Age   Experience   Salary Satisfaction   Attrition
>   ------------- ----- ------------ -------- -------------- -----------
>   E101             25            2    30000 Low            Yes
>   E102             35           10    75000 High           No
>   E103             28            4    40000 Medium         Yes
>   E104             42           15    95000 High           No
>   E105             30            5    45000 Low            Yes
>
> Explain in simple language: 1. What is a dataset? 2. What are the
> features and target variable in this example? 3. Why is this a
> supervised learning problem? 4. Why is this a classification problem?
> 5. How would historical data be used to train a model? 6. How could
> the trained model make a prediction for a new employee?
>
> Give a simple step-by-step explanation and distinguish between what
> can be observed in this tiny sample and what a properly trained model
> would need to learn.

## 5. Expected Outcome

By the end of this exercise, participants should be able to:

-   Recognize a dataset as a structured collection of records.
-   Identify rows, columns, features, and the target variable.
-   Understand that historical, labelled data is used to train a
    supervised ML model.
-   Recognize employee attrition prediction as a binary classification
    use case.
-   Explain how a trained model uses new employee feature values to
    produce a prediction.

## 6. Instructor Reference: Key Answers

  -----------------------------------------------------------------------
  Question                            Expected answer
  ----------------------------------- -----------------------------------
  What is the dataset?                Employee records containing age,
                                      experience, salary, satisfaction,
                                      and attrition information.

  What does each row represent?       One employee.

  What are the features?              Age, Experience, Salary, and
                                      Satisfaction. Employee ID is an
                                      identifier and would generally not
                                      be used as a predictive feature.

  What is the target variable?        Attrition.

  What are the target classes?        Yes and No.

  What type of ML problem is this?    Supervised learning, specifically
                                      binary classification, because
                                      historical examples have known
                                      labels and the model predicts one
                                      of two classes.

  Why is historical data needed?      It provides examples from which the
                                      model can learn relationships
                                      between employee attributes and the
                                      recorded attrition outcome.

  How is a new prediction made?       Provide the new employee's feature
                                      values to the trained model; it
                                      returns a predicted attrition
                                      class, and possibly a probability
                                      if the model supports it.
  -----------------------------------------------------------------------

### Important Teaching Notes

-   The five records are only for illustrating ML concepts. They are far
    too few to train a dependable employee attrition model.
-   A model learns statistical patterns; it does not automatically
    discover the true reasons an employee leaves.
-   The sample suggests a possible relationship between low satisfaction
    and attrition, but it does not prove causation.
-   A real project should use a larger, representative dataset, validate
    data quality, handle missing values, encode categorical features,
    and evaluate the model on data not used for training.
-   Employee data is sensitive. Use appropriate access controls, privacy
    safeguards, fairness checks, and human oversight. Predictions should
    support HR analysis, not be used as the sole basis for employment
    decisions.
