# Solution: Identify Labeled and Unlabeled Data

**Trainer's reference answers | Topic: Supervised vs. Unsupervised Learning**

> Labeled data has a known outcome (target variable) for each record, so it is used for **supervised learning**. Unlabeled data has no predefined outcome, so it is used for **unsupervised learning**, which discovers structure in the data.

---

## 1. Submission Template: Completed

| Scenario | Labeled / Unlabeled | Supervised / Unsupervised | Target Variable Available? |
|---|---|---|---|
| 1 | Labeled | Supervised | Yes (Attrition) |
| 2 | Labeled | Supervised | Yes (Fraud) |
| 3 | Labeled | Supervised | Yes (Spam) |
| 4 | Labeled | Supervised | Yes (Price) |
| 5 | Labeled | Supervised | Yes (Churn) |
| 6 | Labeled | Supervised | Yes (Disease) |
| 7 | Labeled | Supervised | Yes (Quality) |
| 8 | Labeled | Supervised | Yes (Result) |
| 9 | Unlabeled | Unsupervised | No |
| 10 | Unlabeled | Unsupervised | No |
| 11 | Unlabeled | Unsupervised | No |
| 12 | Unlabeled | Unsupervised | No |
| 13 | Unlabeled | Unsupervised | No |
| 14 | Unlabeled | Unsupervised | No |
| 15 | Unlabeled | Unsupervised | No |

---

## 2. Detailed Answers: Part A (Labeled Data)

| # | Scenario | Input Features | Target | Business Objective | ML Technique | How the Business Uses the Results |
|---|---|---|---|---|---|---|
| 1 | HR – Employee Attrition | Age, Salary, Experience | Attrition (Yes/No) | Predict who may leave | Classification (Logistic Regression, Random Forest) | Engage and retain at-risk employees; review pay and career paths |
| 2 | Banking – Fraud Detection | Amount, Location, Transaction Type | Fraud (Yes/No) | Detect fraudulent transactions | Classification (Random Forest, Logistic Regression) | Flag for investigation or extra verification |
| 3 | Email Spam Detection | Subject, Sender, Contains Suspicious Link | Spam (Yes/No) | Filter spam | Classification (Naive Bayes, Logistic Regression) | Move spam to the junk folder; protect users from phishing |
| 4 | Real Estate – House Price | Area, Bedrooms, Location | Price (number) | Predict selling price | **Regression** (Linear Regression, Random Forest Regressor) | Set listing prices, support buyers and sellers, valuation |
| 5 | Telecom – Churn | Monthly Bill, Tenure, Complaints | Churn (Yes/No) | Predict who will leave | Classification (Logistic Regression, Random Forest) | Run retention offers; fix service issues |
| 6 | Healthcare – Disease Risk | Age, Blood Pressure, Glucose Level | Disease (Yes/No) | Predict disease risk | Classification (Logistic Regression, Decision Tree) | Send high-risk patients for clinical review and early screening |
| 7 | Manufacturing – Quality | Temperature, Pressure, Production Speed | Quality (Good/Defective) | Predict inspection outcome | Classification (Decision Tree, Random Forest) | Adjust process settings; target inspection; reduce defects |
| 8 | Education – Student Performance | Attendance, Assignment Score, Study Hours | Result (Pass/Fail) | Predict pass or fail | Classification (Logistic Regression, Decision Tree) | Give early support and counselling to at-risk students |

**Note:** Scenario 4 is the only Part A scenario whose target is a *number* (Price), so it is a regression problem. The other seven have a *category* target, so they are classification problems.

**Identifiers are not features:** Employee ID, Transaction ID, Email ID, Property ID, Customer ID, Patient ID, Product ID and Student ID only identify records and are excluded from the inputs.

---

## 3. Detailed Answers: Part B (Unlabeled Data)

| # | Scenario | Input Features | Target? | Business Objective | ML Technique | How the Business Uses the Results |
|---|---|---|---|---|---|---|
| 9 | Retail – Customer Segmentation | Annual Income, Spending Score | None | Discover customer groups | Clustering (K-Means) | Tailor marketing, e.g. target high-income high-spenders with premium offers |
| 10 | E-commerce – Purchase Behaviour | Total Orders, Avg Order Value, Purchase Frequency | None | Find customers with similar buying patterns | Clustering (K-Means, Hierarchical) | Loyalty programs, personalised offers, cross-selling |
| 11 | HR – Employee Grouping | Experience, Salary, Training Hours | None | Group similar employee profiles | Clustering (K-Means) | Plan training, career paths and compensation by group |
| 12 | Banking – Customer Behaviour | Monthly Transactions, Avg Balance, ATM Usage | None | Group customers by account usage | Clustering (K-Means) | Design products and offers per segment; target digital adoption |
| 13 | IT – Infrastructure Monitoring | CPU, Memory, Network Traffic | None | Find groups of servers with similar load | Clustering (K-Means); anomaly detection for outliers | Balance load, plan capacity, spot overloaded servers |
| 14 | Streaming – Viewing Preferences | Action, Comedy, Drama movies watched | None | Group users by taste | Clustering (K-Means) | Genre-based recommendations and content planning |
| 15 | Manufacturing – Equipment Monitoring | Temperature, Vibration, Operating Hours | None | Find machines with similar behaviour | Clustering (K-Means); anomaly detection for outliers | Spot machines behaving abnormally; plan maintenance |

### Expected groups (illustrative, from inspecting the sample data)

| # | Likely Group 1 | Likely Group 2 |
|---|---|---|
| 9 | C101, C103: low income, low spending | C102, C104: high income, high spending |
| 10 | C101, C103: low-volume, low-value buyers | C102, C104: high-volume, high-value buyers |
| 11 | E101, E103: junior, lower salary, less training | E102, E104: senior, higher salary, more training |
| 12 | B101, B103: low balance, more ATM use | B102, B104: high balance, high transactions, little ATM use |
| 13 | SV101, SV103: lightly loaded | SV102, SV104: heavily loaded |
| 14 | U101, U103: action lovers | U102 (comedy), U104 (drama): different tastes; with more data these would form separate groups |
| 15 | M101, M103: normal operation | M102, M104: hot, high vibration, long hours; maintenance candidates |

> These groups come from reading the sample records, not from a trained model. Real clustering needs many more records, and the number of clusters is a choice that must be checked.

---

## 4. Supervised vs. Unsupervised: Key Differences

| Aspect | Supervised (Labeled) | Unsupervised (Unlabeled) |
|---|---|---|
| Data | Inputs **and** known outcome | Inputs only |
| Goal | Predict a known target | Discover hidden patterns or groups |
| Typical tasks | Classification, Regression | Clustering, Anomaly detection |
| Examples here | Scenarios 1–8 | Scenarios 9–15 |
| Evaluation | Compare predictions with true labels | No true labels; judged by usefulness and cluster quality |
| Typical algorithms | Logistic Regression, Decision Tree, Random Forest | K-Means, Hierarchical Clustering, Isolation Forest |

**Quick test:** ask *"Is there a column that says what the right answer is?"* If yes, it is labeled. If no, it is unlabeled.

---

## 5. Discussion Question: Sample Answer

**Can the same business problem be approached using both supervised and unsupervised learning?**

**Yes.** The choice depends on whether labeled data exists and what the business wants to know.

**Example: Banking fraud**

| Approach | Data needed | What it does |
|---|---|---|
| **Supervised** | Past transactions labeled Fraud / Not Fraud | A classifier learns known fraud patterns and predicts Fraud / Legitimate for new transactions |
| **Unsupervised** | Transactions with no fraud labels | Clustering or anomaly detection finds transactions that look unusual compared with normal behaviour |

- Supervised learning is very good at catching *known* fraud types, but needs enough labeled fraud examples and can miss new tactics.
- Unsupervised learning can flag *new or unknown* suspicious behaviour without labels, but flagged items are only unusual, not proven fraud, so humans must review them.
- In practice, banks often **combine both**: a supervised model for known fraud plus anomaly detection for new patterns.

**Another example: Customers**

- **Supervised:** predict churn using labeled Churn = Yes/No records.
- **Unsupervised:** segment customers into groups by behaviour. The segments can then be used as an extra input to the churn model.

---

## 6. Key Takeaways

- **Labeled data → supervised learning → predict a known target** (category = classification, number = regression).
- **Unlabeled data → unsupervised learning → discover groups or unusual patterns** (clustering, anomaly detection).
- The same business area can use either approach, or both, depending on the available data and the question being asked.
