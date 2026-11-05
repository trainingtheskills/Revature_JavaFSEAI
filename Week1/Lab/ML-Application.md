# Lab Exercise: Real-World Business Applications of Machine Learning

**Duration:** 45-minute hands-on lab | **Level:** Beginner

**Theme:** Identifying Business Problems and Mapping Them to Machine Learning Solutions

**Business Context:** Multiple industries are adopting Machine Learning to improve decision-making, automate analysis, reduce risks, and enhance customer experiences.

---

## 1. Learning Objectives

By the end of this exercise, participants should be able to:

1. Identify real-world business problems across different industries.
2. Understand how Machine Learning can solve business problems.
3. Identify the data required to train an ML model.
4. Determine what the model should predict.
5. Understand the difference between classification, regression, clustering, and anomaly detection.
6. Use ChatGPT to explore possible ML solutions to business problems.

---

## 2. Lab Structure

| Activity | Duration |
|---|---|
| Introduction to business applications | 5 min |
| Industry scenario analysis | 10 min |
| Group-based problem-solving | 15 min |
| AI-assisted solution exploration | 10 min |
| Presentation and discussion | 5 min |
| **Total** | **45 min** |

---

## 3. Real-World Business Scenarios

Introduce the following scenarios to participants.

| Industry | Business Problem | ML Application | Possible ML Technique |
|---|---|---|---|
| Banking | Fraudulent transactions | Fraud detection | Classification / Anomaly detection |
| HR | Employees leaving | Attrition prediction | Classification |
| Retail | Product preferences | Recommendation systems | Recommendation algorithms |
| Healthcare | Disease risk | Predictive diagnosis support | Classification / Regression |
| Manufacturing | Equipment failure | Predictive maintenance | Classification / Regression |
| Telecom | Customer cancellation | Churn prediction | Classification |
| IT Operations | Server failures | Failure prediction | Classification / Time-series forecasting |
| E-commerce | Unusual transactions | Anomaly detection | Anomaly detection |

> **Trainer explanation:** Machine Learning is not restricted to one industry. The same ML concepts can be applied to different business domains, provided relevant data is available.

---

## 4. Participant Activities

Participants will work in **groups of 2–3**.

Each group will select one business scenario from the table above and analyze it using the following four tasks.

### Task 1: Identify the Business Problem

Understand the actual business challenge.

**Example: Banking – Fraudulent Transactions**

A bank processes thousands of transactions every day. Some transactions may be fraudulent. The bank wants to identify suspicious transactions before financial losses occur.

**Questions:**

- What is the business problem?
- Why is it important to solve?
- What happens if the problem is not addressed?

### Task 2: Identify the Required Data

Determine what information the organization needs to collect.

**Example: Banking – Fraud Detection**

| Data Attribute | Description |
|---|---|
| Transaction ID | Unique transaction reference |
| Customer ID | Customer identification |
| Transaction Amount | Amount transferred |
| Transaction Time | Time of transaction |
| Location | Transaction location |
| Transaction Type | UPI, Card, NEFT, etc. |
| Previous Fraud Status | Whether a transaction was previously identified as fraudulent |

**Questions:**

- What data should the organization collect?
- Which attributes are important?
- Is historical data available?
- Do we have examples of fraudulent and legitimate transactions?

### Task 3: Identify What the ML Model Should Predict

Determine the expected output of the model.

**Example: Fraud Detection**

The model should classify a transaction as:

- Fraudulent
- Legitimate

This is a **classification** problem.

### Task 4: Identify the Business Action

Determine what the organization should do after receiving the ML prediction.

**Example:**

| Model Prediction | Possible Business Action |
|---|---|
| Fraudulent | Flag transaction for investigation |
| Suspicious | Trigger additional verification |
| Legitimate | Continue transaction processing |

> The model supports the bank's fraud investigation process. It should **not** automatically treat every flagged transaction as confirmed fraud.

---

## 5. Detailed Industry Scenarios for Group Activities

Participants can use these scenarios to understand the business context.

### Scenario 1: Banking – Fraud Detection

A bank wants to identify fraudulent transactions using customer transaction history, transaction amount, time, and location.

- **Model output:** Fraudulent / Legitimate
- **Technique:** Classification / Anomaly detection

### Scenario 2: HR – Employee Attrition

An organization wants to identify employees who may leave based on salary, experience, satisfaction, and other relevant information.

- **Model output:** Likely to leave / Likely to stay
- **Technique:** Classification

### Scenario 3: Retail – Product Recommendations

An online retailer wants to recommend products based on customer browsing history, previous purchases, and product preferences.

- **Model output:** Recommended products
- **Technique:** Recommendation systems

### Scenario 4: Healthcare – Disease Risk Prediction

A healthcare organization wants to estimate disease risk using relevant medical measurements and historical clinical data.

- **Model output:** Estimated risk category or probability
- **Technique:** Classification / Regression

### Scenario 5: Manufacturing – Predictive Maintenance

A factory wants to predict equipment failure using temperature, vibration, operating hours, and maintenance history.

- **Model output:** Failure risk or estimated time to failure
- **Technique:** Classification / Regression

### Scenario 6: Telecom – Customer Churn

A telecom provider wants to identify customers who may cancel their subscriptions based on usage, billing, complaints, and service history.

- **Model output:** Likely to churn / Likely to stay
- **Technique:** Classification

### Scenario 7: IT Operations – Server Failure Prediction

An IT team wants to predict server failures using CPU usage, memory consumption, disk utilization, response times, and historical incidents.

- **Model output:** Failure risk or predicted resource demand
- **Technique:** Classification / Time-series forecasting

### Scenario 8: E-commerce – Anomaly Detection

An e-commerce company wants to identify unusual orders based on transaction amount, order frequency, delivery address, and purchasing patterns.

- **Model output:** Normal / Unusual activity
- **Technique:** Anomaly detection

---

## 6. AI-Assisted Learning Activity

Participants can use ChatGPT to analyze their selected business scenario.

Copy and use the following prompt:

````text
Act as an experienced Machine Learning consultant and instructor.

I am learning how Machine Learning is applied to real-world business problems.

My selected industry is: [Enter Industry]

My business problem is: [Enter Business Problem]

Help me design a Machine Learning solution by answering the following questions:
1. Explain the business problem using a practical real-world example.
2. Explain why Machine Learning is suitable for this problem.
3. Identify at least 8 data attributes required to solve this problem.
4. Classify each attribute as numerical, categorical, date/time, or identifier.
5. Identify which attributes can be used as input features.
6. Identify the target variable, if the problem requires one.
7. Determine whether the problem is Classification, Regression, Clustering, Recommendation, Time-series Forecasting, or Anomaly Detection.
8. Suggest two suitable Machine Learning algorithms and explain why they are appropriate.
9. Explain how historical data can be used for model training.
10. Provide three sample input records.
11. Show an illustrative example of the expected model output.
12. Explain what business action the organization can take based on the prediction.
13. Explain the possible risks and limitations of this ML solution.
Present the solution in simple language suitable for beginners.

Do not assume that a model has actually been trained. Clearly distinguish illustrative predictions from actual ML model results.
````

---

## 7. Participant Submission Template

Each group should complete the following table and present its solution.

| Question | Participant Response |
|---|---|
| 1. Selected industry | |
| 2. Business problem | |
| 3. Data required | |
| 4. Important features | |
| 5. Target variable (if applicable) | |
| 6. ML technique | |
| 7. Suggested ML algorithm | |
| 8. Expected model output | |
| 9. Business action | |
| 10. Expected business benefit | |

---

## 8. Knowledge Check

**Five MCQs**

**1. A bank wants to identify fraudulent transactions using historical labeled transaction data. Which technique is suitable?**

- [ ] Classification
- [ ] Clustering
- [ ] Recommendation
- [ ] Regression

**2. A retailer wants to group customers based on purchasing behaviour without predefined groups.**

- [ ] Classification
- [ ] Clustering
- [ ] Regression
- [ ] Forecasting

**3. Predicting the number of days before a machine failure is primarily:**

- [ ] Classification
- [ ] Clustering
- [ ] Regression
- [ ] Recommendation

**4. A telecom company wants to predict whether a customer will cancel a subscription.**

- [ ] Classification
- [ ] Clustering
- [ ] Dimensionality reduction
- [ ] Recommendation

**5. Which is the most appropriate business action after an ML model flags a suspicious transaction?**

- [ ] Automatically declare the customer guilty
- [ ] Ignore the prediction
- [ ] Trigger verification or investigation
- [ ] Delete the transaction history

---

## 9. Expected Learning Outcome

By completing this lab, participants should understand that Machine Learning is not just about algorithms or writing Python code.

It starts with understanding a business problem, identifying relevant data, defining the expected output, selecting an appropriate ML approach, and deciding how the business will use the result.

### The Real-World ML Problem-Solving Framework

```text
Business Problem
       ↓
Data Collection
       ↓
Feature and Target Identification
       ↓
ML Technique and Algorithm
       ↓
Model Prediction
       ↓
Business Decision and Action
```
