# Solution: Real-World Business Applications of Machine Learning

**Trainer's reference answers for the 45-minute beginner lab**

> These are example solutions. The actual features and model design depend on the available data and business requirements. Any predictions shown are **illustrative**, not results from a trained model.

---

## 1. Quick Reference Answers

| Industry | Required Data | Model Output | ML Technique | Business Action |
|---|---|---|---|---|
| Banking | Amount, time, location, transaction history | Fraud probability / anomaly flag | Classification / Anomaly detection | Flag and investigate |
| HR | Age, experience, salary, satisfaction | Attrition probability | Classification | Employee engagement |
| Retail | Browsing, purchases, ratings | Recommended products | Recommendation systems | Personalized offers |
| Healthcare | Relevant clinical measurements, medical history | Disease risk estimate | Classification / Regression | Clinical review |
| Manufacturing | Temperature, vibration, maintenance records | Failure probability | Classification / Regression | Schedule maintenance |
| Telecom | Usage, billing, complaints, contract | Churn probability | Classification | Retention campaign |
| IT Operations | CPU, memory, disk, incident logs | Failure risk | Classification / Time-series forecasting | Preventive intervention |
| E-commerce | Order history, amount, frequency, address | Anomaly score | Anomaly detection | Review suspicious orders |

---

## 2. Completed Submission Templates

### Scenario 1: Banking – Fraud Detection

| Question | Sample Response |
|---|---|
| 1. Selected industry | Banking |
| 2. Business problem | Some of the thousands of daily transactions are fraudulent. The bank must spot them before money is lost. Unaddressed fraud causes financial loss, customer distrust and regulatory trouble. |
| 3. Data required | Transaction ID, customer ID, amount, time, location, transaction type (UPI, Card, NEFT), past transaction history, previous fraud status |
| 4. Important features | Amount, time of day, location, transaction type, deviation from the customer's usual behaviour |
| 5. Target variable | Fraud status (Fraudulent / Legitimate) |
| 6. ML technique | Classification (labeled data available); anomaly detection where labels are scarce |
| 7. Suggested ML algorithm | Random Forest, Logistic Regression (Isolation Forest for anomaly detection) |
| 8. Expected model output | Fraud probability or Fraudulent / Legitimate label |
| 9. Business action | Flag for investigation; trigger extra verification for suspicious cases; continue processing legitimate ones |
| 10. Expected business benefit | Lower fraud losses, faster detection, better customer trust |

### Scenario 2: HR – Employee Attrition

| Question | Sample Response |
|---|---|
| 1. Selected industry | Human Resources |
| 2. Business problem | Employees leave unexpectedly, raising hiring costs and losing skills and productivity. |
| 3. Data required | Employee ID, age, salary, years of experience, job role, satisfaction score, overtime hours, promotion history, tenure, left company (Y/N) |
| 4. Important features | Salary, satisfaction, experience, tenure, time since last promotion, overtime |
| 5. Target variable | Left company (Likely to leave / Likely to stay) |
| 6. ML technique | Classification |
| 7. Suggested ML algorithm | Logistic Regression, Random Forest |
| 8. Expected model output | Attrition probability or Likely to leave / Likely to stay |
| 9. Business action | Employee engagement, career discussions, compensation review for high-risk employees |
| 10. Expected business benefit | Lower turnover and replacement cost, better retention of key talent |

### Scenario 3: Retail – Product Recommendations

| Question | Sample Response |
|---|---|
| 1. Selected industry | Retail / online shopping |
| 2. Business problem | Customers face thousands of products and may not find what they like, so sales are missed. |
| 3. Data required | Customer ID, product ID, browsing history, past purchases, ratings, category, price, timestamp |
| 4. Important features | Past purchases, browsing behaviour, ratings, product category, similarity to other customers |
| 5. Target variable | Not required in the usual sense (the model ranks products by predicted preference) |
| 6. ML technique | Recommendation system |
| 7. Suggested ML algorithm | Collaborative Filtering, Content-Based Filtering |
| 8. Expected model output | Ranked list of recommended products |
| 9. Business action | Show personalized recommendations and offers on the site, app and email |
| 10. Expected business benefit | Higher sales, larger basket size, better customer experience |

### Scenario 4: Healthcare – Disease Risk Prediction

| Question | Sample Response |
|---|---|
| 1. Selected industry | Healthcare |
| 2. Business problem | Patients at high risk of a disease need to be identified early so preventive care can start. |
| 3. Data required | Patient ID, age, gender, blood pressure, glucose, BMI, cholesterol, family history, lifestyle factors, diagnosis history |
| 4. Important features | Age, blood pressure, glucose, BMI, cholesterol, family history |
| 5. Target variable | Disease present / risk category (classification) or risk score (regression) |
| 6. ML technique | Classification / Regression |
| 7. Suggested ML algorithm | Logistic Regression, Decision Tree / Random Forest |
| 8. Expected model output | Risk category (Low / Medium / High) or risk probability |
| 9. Business action | Send high-risk cases for clinical review. The model supports clinicians and does not replace them. |
| 10. Expected business benefit | Earlier intervention, better outcomes, more efficient use of clinical resources |

### Scenario 5: Manufacturing – Predictive Maintenance

| Question | Sample Response |
|---|---|
| 1. Selected industry | Manufacturing |
| 2. Business problem | Unexpected machine breakdowns stop production and are costly to repair. |
| 3. Data required | Machine ID, temperature, vibration, operating hours, pressure, maintenance history, past failure records, timestamp |
| 4. Important features | Temperature, vibration, operating hours, time since last maintenance |
| 5. Target variable | Failure (Yes / No) or remaining days to failure |
| 6. ML technique | Classification (will it fail?) / Regression (how long until failure?) |
| 7. Suggested ML algorithm | Random Forest, Gradient Boosting |
| 8. Expected model output | Failure probability or estimated days to failure |
| 9. Business action | Schedule maintenance before the predicted failure |
| 10. Expected business benefit | Less downtime, lower repair cost, longer equipment life |

### Scenario 6: Telecom – Customer Churn

| Question | Sample Response |
|---|---|
| 1. Selected industry | Telecom |
| 2. Business problem | Customers cancel subscriptions, and winning new customers costs more than keeping existing ones. |
| 3. Data required | Customer ID, monthly usage, monthly bill, contract type, tenure, number of complaints, service issues, payment delays, churned (Y/N) |
| 4. Important features | Usage, billing, complaints, contract type, tenure |
| 5. Target variable | Churn (Likely to churn / Likely to stay) |
| 6. ML technique | Classification |
| 7. Suggested ML algorithm | Logistic Regression, Random Forest |
| 8. Expected model output | Churn probability or Likely to churn / Likely to stay |
| 9. Business action | Run a retention campaign: targeted offers, better plans, proactive support |
| 10. Expected business benefit | Higher retention, protected revenue, lower acquisition cost |

### Scenario 7: IT Operations – Server Failure Prediction

| Question | Sample Response |
|---|---|
| 1. Selected industry | IT Operations |
| 2. Business problem | Servers fail without warning, causing outages, lost revenue and unhappy users. |
| 3. Data required | Server ID, CPU usage, memory consumption, disk utilization, response time, error logs, past incidents, timestamp |
| 4. Important features | CPU, memory, disk utilization, response time, recent error rate |
| 5. Target variable | Failure within the next period (Yes / No), or future resource demand |
| 6. ML technique | Classification / Time-series forecasting |
| 7. Suggested ML algorithm | Random Forest or Gradient Boosting (classification); ARIMA or LSTM (forecasting) |
| 8. Expected model output | Failure risk score or forecasted resource demand |
| 9. Business action | Preventive intervention: add capacity, restart services, replace hardware |
| 10. Expected business benefit | Less downtime, better service reliability, planned maintenance |

### Scenario 8: E-commerce – Anomaly Detection

| Question | Sample Response |
|---|---|
| 1. Selected industry | E-commerce |
| 2. Business problem | Unusual orders (possible fraud, abuse or errors) must be found without blocking genuine customers. |
| 3. Data required | Order ID, customer ID, order amount, order frequency, delivery address, payment method, order time, purchase history |
| 4. Important features | Order amount, frequency, delivery address change, deviation from the customer's usual pattern |
| 5. Target variable | Usually none (unsupervised), as labeled examples of unusual orders are rare |
| 6. ML technique | Anomaly detection |
| 7. Suggested ML algorithm | Isolation Forest, One-Class SVM |
| 8. Expected model output | Anomaly score or Normal / Unusual label |
| 9. Business action | Review suspicious orders before shipping |
| 10. Expected business benefit | Fewer fraudulent orders and chargebacks, safer operations |

---

## 3. Worked Example: Prompt Output for Banking Fraud Detection

An illustrative answer to the ChatGPT prompt, shown for the Banking scenario.

### Data attributes and their types

| Attribute | Type | Input feature? |
|---|---|---|
| Transaction ID | Identifier | No |
| Customer ID | Identifier | No |
| Transaction Amount | Numerical | Yes |
| Transaction Time | Date/time | Yes (e.g., hour of day) |
| Location | Categorical | Yes |
| Transaction Type (UPI, Card, NEFT) | Categorical | Yes |
| Average Customer Spend (30 days) | Numerical | Yes |
| Transactions in Last 24 Hours | Numerical | Yes |
| Previous Fraud Status | Categorical | Used to build the target label |

- **Target variable:** `is_fraud` (Fraudulent / Legitimate)
- **Problem type:** Classification (with anomaly detection as a complement)
- **Algorithms:** Random Forest (handles mixed data and non-linear patterns); Logistic Regression (simple, fast, easy to explain)

### How historical data is used

1. Collect past transactions labeled as fraudulent or legitimate.
2. Split into training and test sets (e.g., 80/20).
3. Train the model on the training set to learn fraud patterns.
4. Evaluate on the test set, using precision and recall since fraud is rare.
5. Use the model on new transactions.

### Three sample input records (illustrative)

| Transaction ID | Amount | Time | Location | Type | Avg Spend (30d) | Txns (24h) |
|---|---|---|---|---|---|---|
| T1001 | ₹850 | 14:20 | Mumbai | UPI | ₹900 | 2 |
| T1002 | ₹95,000 | 03:10 | Unknown | Card | ₹1,200 | 7 |
| T1003 | ₹12,000 | 22:45 | Delhi | NEFT | ₹5,000 | 3 |

### Illustrative model output (not from a trained model)

| Transaction ID | Fraud probability | Label | Business action |
|---|---|---|---|
| T1001 | 0.03 | Legitimate | Continue processing |
| T1002 | 0.94 | Fraudulent | Flag for investigation |
| T1003 | 0.45 | Suspicious | Trigger additional verification |

### Risks and limitations

- **Imbalanced data:** fraud is rare, so the model may miss it.
- **False positives:** genuine customers may be blocked, which hurts trust.
- **Evolving fraud:** fraudsters change tactics, so the model needs regular retraining.
- **Data quality and bias:** poor or biased data leads to poor predictions.
- **Privacy:** transaction data is sensitive and must be protected.
- **Human oversight:** flagged transactions are not confirmed fraud and need review.

---

## 4. Knowledge Check: Answer Key

| # | Answer | Explanation |
|---|---|---|
| 1 | **Classification** | Historical *labeled* data (fraud / legitimate) points to supervised classification. |
| 2 | **Clustering** | Grouping customers without predefined groups is unsupervised learning. |
| 3 | **Regression** | Days until failure is a continuous numeric value. |
| 4 | **Classification** | The outcome is a category: will cancel / will not cancel. |
| 5 | **Trigger verification or investigation** | ML predictions support human decisions. They are not proof of guilt, and the data must not be ignored or deleted. |

---

## 5. Technique Summary

| Technique | What it does | Example in this lab |
|---|---|---|
| Classification | Predicts a category | Fraud / Legitimate, Churn / Stay |
| Regression | Predicts a number | Days to machine failure |
| Clustering | Groups similar items without labels | Customer segmentation |
| Anomaly detection | Finds unusual patterns | Unusual e-commerce orders |
| Recommendation | Suggests relevant items | Product recommendations |
| Time-series forecasting | Predicts future values over time | Server resource demand |

---

## 6. Key Takeaway

Machine Learning is not just algorithms or Python code. It starts with a business problem, then data, a defined output, the right ML approach, and finally a business decision.

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
