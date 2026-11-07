# Lab Exercise: Identify Labeled and Unlabeled Data

**Topic:** Supervised Learning vs. Unsupervised Learning

**Total Scenarios:** 15

**Objective:** Identify whether each dataset contains labeled or unlabeled data and understand the difference between the two.

---

## Part A: Labeled Data Scenarios

**Supervised Learning**

In each scenario, the dataset contains input information along with a known outcome or target variable.

### Scenario 1: Employee Attrition Prediction – HR

A company maintains historical employee records containing:

| Employee ID | Age | Salary | Experience | Attrition |
|---|---|---|---|---|
| E101 | 25 | 30,000 | 2 | Yes |
| E102 | 35 | 60,000 | 8 | No |
| E103 | 29 | 40,000 | 4 | Yes |

**Business Problem:** Predict whether an employee is likely to leave the organization.

### Scenario 2: Banking Fraud Detection

A bank maintains transaction records containing:

| Transaction ID | Amount | Location | Transaction Type | Fraud |
|---|---|---|---|---|
| T101 | 5,000 | Pune | Online | No |
| T102 | 95,000 | Mumbai | Online | Yes |
| T103 | 2,000 | Delhi | ATM | No |

**Business Problem:** Predict whether a new banking transaction is fraudulent.

### Scenario 3: Email Spam Detection

An organization maintains email records containing:

| Email ID | Subject | Sender | Contains Suspicious Link | Spam |
|---|---|---|---|---|
| M101 | Meeting Schedule | abc@company.com | No | No |
| M102 | Claim Your Prize | xyz@unknown.com | Yes | Yes |
| M103 | Project Update | manager@company.com | No | No |

**Business Problem:** Classify incoming emails as spam or legitimate.

### Scenario 4: House Price Prediction – Real Estate

A real estate company maintains property records containing:

| Property ID | Area (sq. ft.) | Bedrooms | Location | Price |
|---|---|---|---|---|
| H101 | 1,000 | 2 | Pune | 50,00,000 |
| H102 | 1,500 | 3 | Mumbai | 90,00,000 |
| H103 | 2,000 | 3 | Nashik | 65,00,000 |

**Business Problem:** Predict the selling price of a new property.

### Scenario 5: Customer Churn Prediction – Telecom

A telecom company maintains customer records containing:

| Customer ID | Monthly Bill | Tenure | Complaints | Churn |
|---|---|---|---|---|
| C101 | 500 | 12 | 0 | No |
| C102 | 1,200 | 3 | 4 | Yes |
| C103 | 800 | 24 | 1 | No |

**Business Problem:** Predict whether a customer is likely to discontinue the telecom service.

### Scenario 6: Medical Disease Risk Prediction – Healthcare

A hospital maintains patient records containing:

| Patient ID | Age | Blood Pressure | Glucose Level | Disease |
|---|---|---|---|---|
| P101 | 45 | 120 | 90 | No |
| P102 | 60 | 150 | 180 | Yes |
| P103 | 35 | 110 | 85 | No |

**Business Problem:** Predict whether a patient may have a particular disease based on historical patient data.

### Scenario 7: Manufacturing Quality Prediction

A manufacturing company maintains product inspection records containing:

| Product ID | Temperature | Pressure | Production Speed | Quality |
|---|---|---|---|---|
| PR101 | 80 | 20 | 100 | Good |
| PR102 | 120 | 35 | 150 | Defective |
| PR103 | 85 | 22 | 110 | Good |

**Business Problem:** Predict whether a newly manufactured product will pass quality inspection.

### Scenario 8: Student Performance Prediction – Education

An educational institution maintains student records containing:

| Student ID | Attendance | Assignment Score | Study Hours | Result |
|---|---|---|---|---|
| S101 | 90% | 85 | 4 | Pass |
| S102 | 50% | 35 | 1 | Fail |
| S103 | 80% | 75 | 3 | Pass |

**Business Problem:** Predict whether a student is likely to pass or fail an examination.

---

## Part B: Unlabeled Data Scenarios

**Unsupervised Learning**

In each scenario, the dataset contains input information but does not have a predefined outcome or target variable.

### Scenario 9: Customer Segmentation – Retail

A retail company maintains customer records containing:

| Customer ID | Annual Income | Spending Score |
|---|---|---|
| C101 | 30,000 | 20 |
| C102 | 80,000 | 90 |
| C103 | 35,000 | 25 |
| C104 | 90,000 | 85 |

**Business Problem:** Discover groups of customers based on their income and spending behaviour.

### Scenario 10: Product Purchase Behaviour – E-commerce

An e-commerce company maintains customer purchase records containing:

| Customer ID | Total Orders | Average Order Value | Purchase Frequency |
|---|---|---|---|
| C101 | 5 | 500 | 2 per month |
| C102 | 25 | 2,000 | 10 per month |
| C103 | 8 | 700 | 3 per month |
| C104 | 30 | 3,500 | 12 per month |

**Business Problem:** Identify customers with similar purchasing patterns.

### Scenario 11: Employee Grouping – HR

An organization maintains employee records containing:

| Employee ID | Experience | Salary | Training Hours |
|---|---|---|---|
| E101 | 2 | 30,000 | 10 |
| E102 | 10 | 90,000 | 40 |
| E103 | 3 | 35,000 | 15 |
| E104 | 15 | 1,20,000 | 50 |

**Business Problem:** Discover groups of employees with similar experience, salary, and training profiles.

### Scenario 12: Banking Customer Behaviour

A bank maintains customer account information containing:

| Customer ID | Monthly Transactions | Average Balance | ATM Usage |
|---|---|---|---|
| B101 | 10 | 20,000 | 5 |
| B102 | 50 | 2,00,000 | 2 |
| B103 | 15 | 30,000 | 10 |
| B104 | 60 | 3,00,000 | 1 |

**Business Problem:** Identify groups of banking customers with similar account usage patterns.

### Scenario 13: IT Infrastructure Monitoring

An IT organization collects server performance information containing:

| Server ID | CPU Usage | Memory Usage | Network Traffic |
|---|---|---|---|
| SV101 | 20% | 30% | 100 Mbps |
| SV102 | 95% | 90% | 900 Mbps |
| SV103 | 25% | 35% | 120 Mbps |
| SV104 | 90% | 85% | 850 Mbps |

**Business Problem:** Discover patterns in server usage and identify groups of servers with similar performance characteristics.

### Scenario 14: Movie Recommendation Behaviour

A streaming platform maintains user activity records containing:

| User ID | Action Movies Watched | Comedy Movies Watched | Drama Movies Watched |
|---|---|---|---|
| U101 | 20 | 2 | 5 |
| U102 | 3 | 25 | 10 |
| U103 | 18 | 3 | 4 |
| U104 | 2 | 5 | 30 |

**Business Problem:** Identify groups of users with similar movie-watching preferences.

### Scenario 15: Manufacturing Equipment Monitoring

A manufacturing company collects machine sensor information containing:

| Machine ID | Temperature | Vibration | Operating Hours |
|---|---|---|---|
| M101 | 60 | 2.5 | 500 |
| M102 | 95 | 8.5 | 2,000 |
| M103 | 65 | 3.0 | 600 |
| M104 | 100 | 9.0 | 2,500 |

**Business Problem:** Discover patterns in machine operating behaviour and identify machines with similar operating characteristics.

---

## Part C: Participant Activity

For each of the 15 scenarios, complete the following tasks.

1. Identify whether the dataset is labeled or unlabeled.
2. Identify whether it belongs to supervised or unsupervised learning.
3. Identify the input features.
4. Determine whether a target variable is available.
5. Identify the business objective.
6. Suggest an appropriate ML technique.
7. Explain how the business could use the results.

### Participant Submission Template

| Scenario | Labeled / Unlabeled | Supervised / Unsupervised | Target Variable Available? |
|---|---|---|---|
| 1 | | | |
| 2 | | | |
| 3 | | | |
| 4 | | | |
| 5 | | | |
| 6 | | | |
| 7 | | | |
| 8 | | | |
| 9 | | | |
| 10 | | | |
| 11 | | | |
| 12 | | | |
| 13 | | | |
| 14 | | | |
| 15 | | | |

**Discussion question:** Can the same business problem be approached using both supervised and unsupervised learning? Explain with an example of your choice.
