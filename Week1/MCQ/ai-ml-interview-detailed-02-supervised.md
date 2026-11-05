# Supervised Learning: Detailed Interview Answers with Examples

**50 questions with in-depth explanations, worked examples and interview tips.**

> Code snippets use Python with scikit-learn and are meant as short illustrations. Numbers in examples are made up for teaching.

## Contents

| Group | Questions |
|---|---|
| [Fundamentals](#fundamentals) | 1–4 |
| [Regression and linear models](#regression-and-linear-models) | 5–8 |
| [Trees and ensembles](#trees-and-ensembles) | 9–15 |
| [Other classifiers](#other-classifiers) | 16–22 |
| [Regularization](#regularization) | 23–25 |
| [Evaluation metrics](#evaluation-metrics) | 26–35 |
| [Practical issues](#practical-issues) | 36–50 |

---

## Fundamentals

### 1. What is supervised learning?

Supervised learning trains a model on **labeled data**: every training record contains the input features and the correct answer (the label or target). The model learns the mapping from inputs to outputs by comparing its predictions with the known answers and adjusting itself to reduce the error. After training, it predicts the output for new records whose answer is unknown. The word "supervised" comes from the labels acting like a teacher who corrects the model.

**Example:** A bank has 100,000 past transactions, each marked `Fraud = Yes/No`. Features are amount, location and transaction type. The model learns which feature patterns go with fraud, then scores each new transaction.

```python
from sklearn.ensemble import RandomForestClassifier
model = RandomForestClassifier().fit(X_train, y_train)   # learn from labeled data
predictions = model.predict(X_new)                        # predict unseen data
```

**Key point:** If the dataset has a column that contains the "right answer", the problem is supervised.

### 2. What are the two main types of supervised problems?

The two types are **classification**, where the target is a category, and **regression**, where the target is a continuous number. The type depends only on what you predict, not on the algorithm. Many algorithms (trees, neural networks, SVM, KNN) come in both a classifier and a regressor version. Choosing the wrong type is a common beginner mistake, such as treating a price as categories.

**Example:** "Will this customer churn: yes or no?" is classification. "How much will this customer spend next month?" is regression.

**Interview tip:** Ask "is the output a label or a number?" before choosing a model.

### 3. Give examples of classification and regression.

Classification examples include spam detection (spam / not spam), fraud detection, churn prediction, disease diagnosis support and image recognition (cat / dog). Regression examples include house price prediction, sales forecasting, estimating delivery time and predicting days until a machine fails. A single business can use both on the same data.

| Business | Classification question | Regression question |
|---|---|---|
| Telecom | Will the customer cancel? | How many months until they cancel? |
| Real estate | Is the property overpriced? (yes/no) | What is the selling price? |
| Manufacturing | Will the machine fail this week? | How many days of life are left? |

**Key point:** Classification output can also be a probability (e.g. 0.82 chance of churn), which is then turned into a class using a threshold.

### 4. What is the difference between binary, multi-class and multi-label classification?

**Binary** classification has exactly two classes (fraud / not fraud). **Multi-class** has more than two classes, but each record gets exactly one (a handwritten digit 0–9). **Multi-label** lets a record have several labels at the same time (a news article tagged both "politics" and "economy"). Multi-class is often solved with strategies like one-vs-rest, or with softmax output in neural networks. Multi-label usually trains one binary decision per label.

**Example:** A support-ticket system can be:
- Binary: urgent or not urgent.
- Multi-class: category is exactly one of Billing, Technical, Account.
- Multi-label: a ticket can be both Billing and Technical.

**Interview tip:** Mention that metrics differ too, for example macro or micro averaged F1 for multi-class and multi-label.

---

## Regression and Linear Models

### 5. What is linear regression?

Linear regression predicts a numeric target as a weighted sum of the input features plus an intercept: `y = b0 + b1·x1 + b2·x2 + ...`. The coefficients are found by minimizing the sum of squared differences between predictions and actual values (ordinary least squares). Each coefficient says how much the target changes when that feature rises by one unit while others stay fixed. It is simple, fast and easy to explain, which makes it a strong baseline.

**Example:** Predicting house price from area in square feet:
`price = 5,00,000 + 4,000 × area`. A 1,000 sq. ft. house is predicted at 5,00,000 + 40,00,000 = 45,00,000.

```python
from sklearn.linear_model import LinearRegression
m = LinearRegression().fit(X_train, y_train)
print(m.coef_, m.intercept_)
```

**Key point:** It captures only linear relationships unless you add transformed features (e.g. area²).

### 6. What are the assumptions of linear regression?

The classic assumptions are: (1) **linearity**, so the relationship between features and target is linear; (2) **independence** of errors, so one record's error does not predict another's; (3) **homoscedasticity**, meaning the error variance is constant across predictions; (4) **normally distributed errors**, which matters mainly for confidence intervals and tests; and (5) **no severe multicollinearity** among features. Violations do not always ruin predictions, but they can make coefficients and p-values unreliable. Residual plots are the standard way to check them.

**Example:** Predicting salary from experience: if the residuals fan out (small errors for juniors, huge errors for seniors), variance is not constant. A log transform of salary often fixes this.

**Interview tip:** Time-ordered data often breaks independence (autocorrelation), so mention checking that for forecasting problems.

### 7. What is logistic regression?

Logistic regression is a classification algorithm. It computes a linear combination of the features, `z = b0 + b1·x1 + ...`, and passes it through the **sigmoid function** `p = 1 / (1 + e^(-z))`, which squeezes any number into a probability between 0 and 1. A threshold (commonly 0.5) turns the probability into a class. Coefficients are learned by minimizing log loss (cross-entropy). It is fast, interpretable and works well as a baseline.

**Example:** For churn, suppose `z = -2 + 0.5 × complaints`. With 4 complaints, z = 0 and p = 0.50. With 8 complaints, z = 2 and p = 1/(1+e⁻²) ≈ 0.88, so the customer is likely to churn.

```python
from sklearn.linear_model import LogisticRegression
clf = LogisticRegression().fit(X_train, y_train)
clf.predict_proba(X_new)[:, 1]   # probability of the positive class
```

### 8. Why is logistic regression called regression if it classifies?

It is a regression on the **log-odds** (logit) of the outcome. The odds are `p / (1 - p)`, and the log of the odds is modeled as a linear function of the features. So internally it performs a regression, producing a continuous value (the log-odds), and the sigmoid converts it into a probability. Only the final step, applying a threshold, turns it into a classification.

**Example:** If the probability of fraud is 0.8, the odds are 0.8/0.2 = 4, and the log-odds are ln(4) ≈ 1.39. The model predicts this 1.39 as a linear sum of features.

**Key point:** A coefficient of 0.5 means each unit increase in the feature multiplies the odds by e^0.5 ≈ 1.65.

---

## Trees and Ensembles

### 9. What is a decision tree?

A decision tree is a flowchart of yes/no questions on feature values. Each internal node tests a feature, each branch is an outcome of the test, and each leaf holds a prediction (a class or a number). To predict, a record travels from the root down the branches until it reaches a leaf. Trees handle numeric and categorical data, need little preprocessing, do not require scaling, and are easy to explain.

**Example (loan approval):**
```
Income > 50,000?
├─ Yes → Credit score > 700?
│        ├─ Yes → Approve
│        └─ No  → Review
└─ No  → Reject
```

```python
from sklearn.tree import DecisionTreeClassifier
tree = DecisionTreeClassifier(max_depth=3).fit(X_train, y_train)
```

### 10. How does a decision tree choose splits?

At each node, the tree tries candidate splits and picks the one that makes the child nodes as **pure** as possible. For classification, purity is measured with **Gini impurity** (`1 − Σp²`) or **entropy**; the improvement is called information gain. For regression, it picks the split that most reduces variance or MSE. The process repeats recursively until a stopping rule (depth, minimum samples, or no gain) is reached.

**Worked example (Gini):** A node has 10 customers: 6 churn, 4 stay. Gini = 1 − 0.6² − 0.4² = 0.48.
A split gives a left group (5 churn, 1 stay) and a right group (1 churn, 3 stay):
- Left Gini = 1 − (5/6)² − (1/6)² ≈ 0.278
- Right Gini = 1 − (1/4)² − (3/4)² = 0.375
- Weighted = 0.6 × 0.278 + 0.4 × 0.375 ≈ 0.317

Gain = 0.48 − 0.317 ≈ 0.163. The tree picks the split with the highest gain.

### 11. What are the drawbacks of decision trees?

A single tree **overfits** easily because it can keep splitting until it memorizes the training data. It is **unstable**: a small change in data can produce a very different tree. It creates axis-aligned splits only, so diagonal boundaries need many splits. It can also be biased toward features with many distinct values. Pruning, limiting depth, or using ensembles reduce these problems.

**Example:** A tree with no depth limit on 500 customers may give 100% training accuracy but only 70% on test data. Setting `max_depth=4` and `min_samples_leaf=20` might give 85% training and 82% test accuracy, which generalizes better.

**Interview tip:** Say that Random Forest and gradient boosting exist mainly to fix these weaknesses.

### 12. What is Random Forest?

Random Forest is an ensemble of many decision trees. Each tree is trained on a **bootstrap sample** (random sample with replacement) of the data, and at each split it considers only a **random subset of features**. For classification, the forest takes a majority vote; for regression, it averages the predictions. The randomness makes the trees different from each other, so their errors partly cancel out, which reduces variance and overfitting.

**Example:** To predict loan default, 200 trees each vote. If 150 vote "default" and 50 vote "no default", the forest predicts default with probability ≈ 0.75.

```python
from sklearn.ensemble import RandomForestClassifier
rf = RandomForestClassifier(n_estimators=200, random_state=42).fit(X_train, y_train)
```

**Key point:** Random Forest gives out-of-bag (OOB) error and feature importance almost for free.

### 13. What is bagging?

Bagging (bootstrap aggregating) trains several models independently, each on a different bootstrap sample of the training data, and then combines their predictions by voting or averaging. Because high-variance models like deep trees change a lot with the data, averaging many of them produces a smoother, more stable predictor. Bagging mainly **reduces variance**, not bias. Random Forest is bagging plus random feature selection.

**Example:** Three models predict a house price: 48, 52 and 50 lakh. The bagged prediction is the mean, 50 lakh. If one model alone had overfit and said 60, the average is much less affected.

```python
from sklearn.ensemble import BaggingClassifier
from sklearn.tree import DecisionTreeClassifier
bag = BaggingClassifier(DecisionTreeClassifier(), n_estimators=50).fit(X_train, y_train)
```

### 14. What is boosting?

Boosting builds models **sequentially**, where each new model focuses on the mistakes of the previous ones. Weak learners (usually shallow trees) are added one by one, and the final prediction is a weighted sum. AdaBoost reweights misclassified samples; gradient boosting fits each new tree to the residual errors (the gradient of the loss). Boosting mostly **reduces bias** and often gives top accuracy on tabular data, but it can overfit if too many rounds are used without regularization.

**Example (regression):** True price = 100. Tree 1 predicts 80 (residual +20). Tree 2 is trained to predict the residual and outputs 15, so the combined prediction is 95 (residual +5). Tree 3 predicts 4, giving 99. Each step shrinks the error.

```python
from sklearn.ensemble import GradientBoostingClassifier
gb = GradientBoostingClassifier(learning_rate=0.1, n_estimators=200).fit(X_train, y_train)
```

### 15. Bagging vs boosting?

| Aspect | Bagging | Boosting |
|---|---|---|
| Training | Parallel, independent models | Sequential, each corrects the last |
| Main effect | Reduces **variance** | Reduces **bias** (and some variance) |
| Base learners | Strong, deep trees | Weak, shallow trees |
| Overfitting risk | Low | Higher without tuning |
| Examples | Random Forest | AdaBoost, XGBoost, LightGBM |

**Example:** If a single deep tree overfits (high variance), try bagging/Random Forest. If a shallow model underfits (high bias), try boosting.

**Interview tip:** Add that boosting is more sensitive to noisy labels and outliers because it keeps focusing on the hard cases.

---

## Other Classifiers

### 16. What is K-Nearest Neighbors (KNN)?

KNN is a simple "lazy" algorithm: it stores the training data and, for a new point, finds the k closest training points by a distance measure (usually Euclidean). For classification it takes the majority class among those neighbours; for regression it averages their values. There is no real training step, but prediction can be slow on large data because it compares against all stored points.

**Example:** To classify a new fruit by weight and sweetness, k = 3. Its three nearest neighbours are two apples and one orange, so the prediction is apple.

```python
from sklearn.neighbors import KNeighborsClassifier
knn = KNeighborsClassifier(n_neighbors=5).fit(X_train_scaled, y_train)
```

### 17. How do you choose k in KNN?

Choose k using **cross-validation**: try several values and pick the one with the best validation score. A very small k (like 1) follows noise and overfits; a very large k over-smooths and underfits, and in the extreme predicts the majority class everywhere. For binary problems an odd k avoids tie votes. A common starting point is around the square root of the number of samples, then tune from there.

**Example:** Validation accuracy: k=1 → 78%, k=5 → 86%, k=15 → 84%, k=50 → 79%. Pick k=5.

```python
from sklearn.model_selection import GridSearchCV
GridSearchCV(KNeighborsClassifier(), {"n_neighbors": range(1, 31, 2)}, cv=5).fit(X, y)
```

### 18. Why does KNN need feature scaling?

KNN uses distances, so a feature with a large numeric range dominates the distance and the other features barely matter. Scaling (standardization or min-max) puts all features on a comparable range so each contributes fairly.

**Example:** Customers described by income (₹20,000–₹2,00,000) and age (20–60). Two customers differ by ₹10,000 in income and 30 years in age. Unscaled Euclidean distance ≈ √(10,000² + 30²) ≈ 10,000, so age is practically ignored. After scaling both to 0–1, age counts as much as income.

```python
from sklearn.pipeline import make_pipeline
from sklearn.preprocessing import StandardScaler
model = make_pipeline(StandardScaler(), KNeighborsClassifier(5)).fit(X_train, y_train)
```

**Key point:** Fit the scaler on training data only, to avoid leakage. A pipeline does this safely.

### 19. What is a Support Vector Machine (SVM)?

An SVM finds the decision boundary (a hyperplane) that separates classes with the **largest possible margin**, meaning the biggest gap between the boundary and the nearest points of each class. Those nearest points are called **support vectors**; only they define the boundary. A soft-margin version (parameter C) allows some misclassifications to handle noisy or overlapping data. SVMs work well on medium-sized, high-dimensional data such as text, but scale poorly to very large datasets.

**Example:** Separating "spam" from "not spam" emails using word-frequency features: the SVM draws the boundary in feature space that maximizes the distance to the closest spam and non-spam emails.

```python
from sklearn.svm import SVC
svm = SVC(kernel="rbf", C=1.0).fit(X_train_scaled, y_train)
```

### 20. What is the kernel trick?

When classes cannot be separated by a straight line, one idea is to map the data into a higher-dimensional space where a linear separator exists. The kernel trick lets the SVM compute the needed inner products in that space **without ever computing the mapping explicitly**, which is cheap. Common kernels are linear, polynomial and RBF (Gaussian). The kernel's parameters (such as gamma for RBF) control how flexible the boundary is.

**Example:** Points of class A form a circle around class B in 2D, so no line separates them. Adding a feature `x1² + x2²` lifts the data into 3D, where a flat plane separates the inner and outer points. An RBF kernel achieves this effect automatically.

**Interview tip:** A large gamma makes the boundary very wiggly (overfit); a small gamma makes it smoother.

### 21. What is Naive Bayes?

Naive Bayes is a probabilistic classifier based on **Bayes' theorem**: `P(class | features) ∝ P(class) × Π P(feature | class)`. It is "naive" because it assumes features are independent of each other given the class, which is rarely true but often works well anyway. Training just counts frequencies, so it is extremely fast and needs little data. Variants include Gaussian (numeric features), Multinomial (word counts) and Bernoulli (binary features).

**Example (spam):** Suppose 40% of emails are spam. The word "prize" appears in 30% of spam and 1% of non-spam emails. For an email containing "prize" only: spam score = 0.4 × 0.30 = 0.12, not-spam score = 0.6 × 0.01 = 0.006. Normalized, P(spam) = 0.12 / 0.126 ≈ 0.95.

```python
from sklearn.naive_bayes import MultinomialNB
nb = MultinomialNB().fit(X_counts, y)
```

### 22. When is Naive Bayes a good choice?

It is a good choice for **text classification** (spam filtering, sentiment, topic tagging), for **small training sets**, when you need a **very fast baseline**, and when training or prediction speed matters at large scale. It handles many features well because each is treated independently. It is a weaker choice when features are strongly correlated or when you need well-calibrated probabilities.

**Example:** A support team wants to route 10,000 incoming tickets per day by topic. A Multinomial Naive Bayes on word counts trains in seconds, predicts instantly and often reaches decent accuracy, so it is a sensible first model before trying heavier ones.

**Key point:** Its probabilities are often over-confident (close to 0 or 1), so calibrate them if you use them as real probabilities.

---

## Regularization

### 23. What is regularization?

Regularization adds a **penalty on model complexity** (usually the size of the weights) to the loss function so the model prefers simpler solutions. Large weights often mean the model is bending to fit noise, so penalizing them reduces overfitting. A hyperparameter (alpha or lambda, or C in scikit-learn's logistic regression and SVM, where smaller C means stronger regularization) controls the strength. Too much regularization causes underfitting.

**Example:** A model with 50 features and only 100 rows reaches 99% training but 70% test accuracy. After adding L2 regularization, training drops to 90% and test rises to 85%, a better generalization.

**Formula idea:** `Loss = Error + λ × Penalty(weights)`.

### 24. L1 vs L2 regularization?

| Aspect | L1 (Lasso) | L2 (Ridge) |
|---|---|---|
| Penalty | Sum of absolute weights `Σ|w|` | Sum of squared weights `Σw²` |
| Effect on weights | Can shrink some exactly to **zero** | Shrinks all weights smoothly, rarely to zero |
| Feature selection | Yes, built in | No |
| Correlated features | Tends to keep one, drop others | Spreads weight among them |
| Best when | Many irrelevant features | Many small useful effects |

**Example:** A model with 100 candidate features. Lasso might keep only 12 with non-zero coefficients, giving a sparse model that is easy to interpret. Ridge keeps all 100 but with smaller weights.

```python
from sklearn.linear_model import Lasso, Ridge
Lasso(alpha=0.1).fit(X, y); Ridge(alpha=1.0).fit(X, y)
```

### 25. What is Elastic Net?

Elastic Net combines both L1 and L2 penalties, with a mixing parameter controlling the ratio. It keeps Lasso's ability to remove features while gaining Ridge's stability when features are highly correlated. Pure Lasso can behave erratically among correlated features (picking one arbitrarily), while Elastic Net tends to keep groups of them together.

**Example:** Predicting sales from several highly correlated ad-spend channels (TV, radio, online). Lasso might arbitrarily keep only "online". Elastic Net keeps all three with moderate weights, which is more stable.

```python
from sklearn.linear_model import ElasticNet
ElasticNet(alpha=0.1, l1_ratio=0.5).fit(X, y)   # 50% L1, 50% L2
```

---

## Evaluation Metrics

### 26. What metrics are used for classification?

Common classification metrics are **accuracy**, **precision**, **recall**, **F1-score**, **ROC-AUC**, **PR-AUC** and **log loss**. Most are derived from the confusion matrix (TP, FP, TN, FN). Which one to use depends on class balance and the cost of each type of error. Always report more than accuracy on imbalanced problems.

| Metric | Answers the question |
|---|---|
| Accuracy | How often is the model right overall? |
| Precision | Of predicted positives, how many were real? |
| Recall | Of real positives, how many did we find? |
| F1 | Balance of precision and recall |
| ROC-AUC / PR-AUC | How well does the model rank positives above negatives? |
| Log loss | How good are the predicted probabilities? |

```python
from sklearn.metrics import classification_report
print(classification_report(y_test, y_pred))
```

### 27. Define precision and recall.

**Precision = TP / (TP + FP)**: among everything flagged positive, the fraction that truly is positive. **Recall = TP / (TP + FN)**: among all real positives, the fraction the model found.

**Worked example (fraud):** Of 1,000 transactions, 60 are truly fraud. The model flags 50: 40 are real fraud (TP = 40) and 10 are legitimate (FP = 10). It misses 20 frauds (FN = 20).
- Precision = 40 / (40 + 10) = **0.80**: 80% of alerts are real.
- Recall = 40 / (40 + 20) = **0.67**: it caught two-thirds of all fraud.

**Interview tip:** Memorize: precision is about the **alerts**, recall is about the **actual cases**.

### 28. What is the F1-score?

F1 is the **harmonic mean** of precision and recall: `F1 = 2 × P × R / (P + R)`. The harmonic mean punishes imbalance, so a model cannot get a high F1 by excelling at only one of the two. It is useful when you need a single number for imbalanced data and both false alarms and misses matter.

**Example (using Q27):** P = 0.80, R = 0.667. F1 = 2 × 0.80 × 0.667 / (0.80 + 0.667) ≈ **0.727**. The simple average would be 0.733, but if recall had been 0.10 with precision 1.0, the average (0.55) hides the problem while F1 = 0.18 exposes it.

**Key point:** F-beta generalizes F1; beta > 1 weights recall more, beta < 1 weights precision more.

### 29. Why is accuracy misleading on imbalanced data?

When one class dominates, a model can score high accuracy simply by always predicting the majority class, while being useless for the case you care about.

**Example:** Out of 1,000 transactions, 10 are fraud. A "model" that predicts "legitimate" for everything gets 990/1000 = **99% accuracy**, yet its recall for fraud is 0%, catching nothing. A real model with 97% accuracy that catches 8 of 10 frauds is far more valuable.

```python
from sklearn.dummy import DummyClassifier
DummyClassifier(strategy="most_frequent").fit(X_train, y_train).score(X_test, y_test)  # high accuracy, no value
```

**Interview tip:** Always compare against the majority-class baseline and report precision, recall and PR-AUC.

### 30. What is ROC-AUC?

The ROC curve plots the **true positive rate (recall)** against the **false positive rate** (FP / (FP + TN)) as the decision threshold moves from 0 to 1. The **AUC** is the area under that curve. AUC equals the probability that the model ranks a randomly chosen positive example higher than a randomly chosen negative one. An AUC of 0.5 means random guessing; 1.0 is perfect ranking. It is threshold-independent, which makes it good for comparing models.

**Example:** Model A has AUC 0.92 and Model B 0.78 on a churn dataset. A is much better at ranking likely churners above loyal customers, regardless of which cutoff you later choose.

```python
from sklearn.metrics import roc_auc_score
roc_auc_score(y_test, model.predict_proba(X_test)[:, 1])
```

**Key point:** AUC needs probabilities or scores, not hard class labels.

### 31. When prefer PR-AUC over ROC-AUC?

Prefer PR-AUC (area under the precision–recall curve) when the positive class is **rare** and you care mostly about it. ROC-AUC includes true negatives through the false positive rate, so with a huge number of negatives, a model can look excellent even if it produces many false alarms in absolute terms. Precision directly reflects those false alarms, so PR-AUC is more sensitive.

**Example:** 100 frauds among 1,000,000 transactions. A model that flags 1,000 transactions (900 false alarms) has a false positive rate of only 0.09%, so ROC-AUC looks great, but its precision is just 10%. PR-AUC reveals that weakness.

**Interview tip:** The baseline PR-AUC equals the positive-class proportion (here 0.0001), not 0.5.

### 32. When do you prioritize recall versus precision?

Prioritize **recall** when missing a real positive is costly or dangerous. Prioritize **precision** when false alarms are costly or annoying. Often you can move between them by changing the decision threshold.

| Situation | Priority | Why |
|---|---|---|
| Cancer screening | Recall | A missed case is far worse than an extra test |
| Fraud detection (first-pass alert) | Recall | Missing fraud causes loss; analysts review alerts |
| Spam filter | Precision | Blocking a real email is worse than letting spam through |
| Marketing offer with high cost per customer | Precision | Avoid wasting budget on uninterested people |

**Example:** Lowering the fraud threshold from 0.5 to 0.3 might raise recall from 67% to 85% but drop precision from 80% to 55%. The business decides which trade is acceptable.

### 33. What metrics are used for regression?

Common regression metrics are **MAE** (mean absolute error), **MSE** (mean squared error), **RMSE** (root of MSE), **R²** (variance explained) and **MAPE** (mean absolute percentage error). They measure how far predictions are from the actual numbers, in different ways. Choose by how you want to treat large errors and whether you need results in the target's units or in percent.

```python
from sklearn.metrics import mean_absolute_error, mean_squared_error, r2_score
mae = mean_absolute_error(y_test, pred)
rmse = mean_squared_error(y_test, pred) ** 0.5
r2 = r2_score(y_test, pred)
```

**Example:** House-price model: MAE = ₹3 lakh means predictions are off by ₹3 lakh on average.

**Key point:** MAPE breaks down when actual values are near or at zero.

### 34. MAE vs RMSE?

MAE averages the absolute errors, so every unit of error counts equally and it is robust to outliers. RMSE squares errors before averaging, so large errors are penalized much more. RMSE is always at least as large as MAE; a big gap between them signals a few large errors.

**Worked example:** Five predictions with errors 2, 2, 2, 2 and 20.
- MAE = (2+2+2+2+20)/5 = **5.6**
- RMSE = √((4+4+4+4+400)/5) = √83.2 ≈ **9.12**

If a single huge miss is very costly (e.g. under-forecasting stock for a big event), prefer RMSE. If all errors matter proportionally, use MAE.

### 35. What does R² mean?

R² (coefficient of determination) is the proportion of the target's variance explained by the model: `R² = 1 − SS_residual / SS_total`. A value of 1 is a perfect fit, 0 means the model is no better than always predicting the mean, and it can be negative on test data if the model is worse than the mean. Adding features never lowers training R², so **adjusted R²** penalizes useless features.

**Example:** A house-price model with R² = 0.85 explains 85% of the variation in prices; 15% remains unexplained by the features.

**Interview tip:** A high R² does not prove the model is good (it could be overfit) or causal. Always check test-set metrics and residuals.

---

## Practical Issues

### 36. How do you handle class imbalance?

Options fall into three groups: **data-level** (oversample the minority, undersample the majority, SMOTE), **algorithm-level** (class weights or cost-sensitive learning) and **evaluation/threshold-level** (use precision, recall, PR-AUC and tune the decision threshold). Start with class weights and proper metrics because they are the simplest, then try resampling if needed. Always resample only the training data, never the test data.

**Example:** With 2% churners, set `class_weight="balanced"` so each churner counts about 25 times more than a loyal customer in the loss.

```python
from sklearn.linear_model import LogisticRegression
clf = LogisticRegression(class_weight="balanced").fit(X_train, y_train)
```

**Key point:** Collecting more minority-class data is often the best fix if feasible.

### 37. What is SMOTE?

SMOTE (Synthetic Minority Over-sampling Technique) creates new, synthetic minority-class examples by picking a minority sample, finding its nearest minority neighbours, and generating a point somewhere on the line between them. Compared with plain duplication, this adds variety and reduces overfitting to repeated rows. Its weaknesses are that it can create unrealistic points, can amplify noise and works poorly with high-dimensional or purely categorical data (variants like SMOTE-NC handle mixed types).

**Example:** Fraud sample A has amount 900 and sample B has 1,100 (both fraud, neighbours). SMOTE might create a synthetic fraud at amount 1,000 on the line between them.

```python
from imblearn.over_sampling import SMOTE   # imbalanced-learn library
X_res, y_res = SMOTE(random_state=42).fit_resample(X_train, y_train)
```

**Interview tip:** Apply SMOTE only inside the training fold (use an imblearn pipeline), otherwise you leak synthetic information into validation.

### 38. What is a decision threshold?

Classifiers usually output a probability, and the **decision threshold** is the cut-off that converts it into a class. The default is 0.5, but it is a business choice, not a law. Raising the threshold makes the model stricter (higher precision, lower recall); lowering it makes it more permissive (higher recall, lower precision). The best value comes from the cost of false positives versus false negatives, often found with a precision–recall curve.

**Example:** A churn model gives a customer 0.42. With threshold 0.5 they are "stay"; with threshold 0.4 they are "churn" and receive a retention offer. If the offer is cheap and churn is costly, use 0.3–0.4.

```python
proba = model.predict_proba(X_test)[:, 1]
pred = (proba >= 0.35).astype(int)
```

### 39. What is multicollinearity and how do you detect it?

Multicollinearity means two or more features are strongly correlated with each other. In linear and logistic regression it makes coefficient estimates unstable and hard to interpret (they can swing wildly or change sign), although overall predictions may remain fine. Tree-based models are mostly unaffected. Detect it with a correlation matrix or the **Variance Inflation Factor (VIF)**; a common rule of thumb flags VIF above about 5–10. Fix by dropping or combining features, using PCA, or regularization (Ridge).

**Example:** Predicting salary using both "age" and "years of experience", which are highly correlated. The model cannot tell which one drives salary, so coefficients are unstable. Dropping one or combining them solves this.

```python
from statsmodels.stats.outliers_influence import variance_inflation_factor
vif = [variance_inflation_factor(X.values, i) for i in range(X.shape[1])]
```

### 40. What is the difference between parametric and non-parametric models?

**Parametric** models assume a fixed functional form with a fixed number of parameters, no matter how much data you have (linear regression, logistic regression, Naive Bayes). They are fast and need less data but can underfit if the assumption is wrong. **Non-parametric** models make fewer assumptions and their complexity grows with the data (KNN, decision trees, kernel SVM). They are flexible but need more data and can overfit or be slower.

| Aspect | Parametric | Non-parametric |
|---|---|---|
| Parameters | Fixed number | Grows with data |
| Assumptions | Strong | Weak |
| Data needed | Less | More |
| Examples | Linear/Logistic regression | KNN, trees |

**Example:** A linear model for house price always has one coefficient per feature. A KNN model must keep every training house in memory.

### 41. What is gradient boosting and why is XGBoost popular?

Gradient boosting builds trees one after another, where each new tree is fitted to the **negative gradient of the loss** (for squared error, simply the residuals) of the current ensemble, and is added with a small learning rate. XGBoost is a fast, optimized implementation that adds built-in regularization, parallel tree construction, handling of missing values, and efficient memory use. It has won many tabular-data competitions and is widely used in industry for structured data. LightGBM and CatBoost are similar alternatives.

**Example:** For credit-risk scoring on a table of income, debt, age and payment history, an XGBoost model typically beats logistic regression in accuracy, at the cost of some interpretability.

```python
from xgboost import XGBClassifier   # separate library
model = XGBClassifier(n_estimators=300, learning_rate=0.05, max_depth=4).fit(X_train, y_train)
```

**Interview tip:** Key tuning knobs are learning rate, number of trees, max depth and subsampling; lower learning rates need more trees.

### 42. How do you get feature importance?

Several approaches exist: **tree-based importance** (how much each feature reduces impurity across the trees), **permutation importance** (shuffle one feature on validation data and see how much the score drops), **coefficients** of linear models (on scaled features) and **SHAP values** (per-prediction contributions based on game theory). Permutation importance and SHAP are generally more reliable than built-in impurity importance, which can favour high-cardinality features.

**Example:** For a churn model, permutation importance shows shuffling `complaints` drops AUC from 0.88 to 0.75, while shuffling `customer_id` changes nothing, so complaints matter and the ID does not.

```python
from sklearn.inspection import permutation_importance
r = permutation_importance(model, X_val, y_val, n_repeats=10, random_state=0)
```

### 43. What is stacking?

Stacking (stacked generalization) combines different models by training a **meta-model** on their predictions. Base models (for example Random Forest, SVM and logistic regression) make predictions, usually out-of-fold to avoid leakage, and the meta-model (often a simple logistic or linear regression) learns how to weigh them. It can beat any single model when the base models make different kinds of errors, but it adds complexity and training time.

**Example:** Three base models give churn probabilities 0.7 (RF), 0.4 (SVM) and 0.6 (LR). The meta-learner learned that RF is most reliable and weights them, producing a final 0.62.

```python
from sklearn.ensemble import StackingClassifier
stack = StackingClassifier(
    estimators=[("rf", RandomForestClassifier()), ("svm", SVC(probability=True))],
    final_estimator=LogisticRegression(),
).fit(X_train, y_train)
```

### 44. What is probability calibration?

A model is **calibrated** when its predicted probabilities match real frequencies: among all cases predicted at 0.8, about 80% should be positive. Many models (Naive Bayes, SVM, boosted trees) produce well-ranked but poorly calibrated scores. Calibration fixes this using methods such as **Platt scaling** (a logistic fit on the scores) or **isotonic regression**, learned on held-out data. It matters when the probability itself drives decisions, such as expected loss or pricing.

**Example:** A model says 0.9 for 100 loans, but only 70 default. It is over-confident. After calibration, those same cases get about 0.7, which lets a bank compute expected loss correctly.

```python
from sklearn.calibration import CalibratedClassifierCV
cal = CalibratedClassifierCV(model, method="isotonic", cv=5).fit(X_train, y_train)
```

### 45. What are residuals and why plot them?

A residual is the **actual value minus the predicted value** for each record. Plotting residuals against predictions (or features) reveals problems that a single metric hides. Ideally they scatter randomly around zero with constant spread. A curve suggests a missing non-linear term, a funnel shape suggests non-constant variance, and isolated points suggest outliers.

**Example:** Predicting delivery time, the residual plot is U-shaped: the model under-predicts for very short and very long distances. Adding a distance² feature or using a tree model fixes it.

```python
import matplotlib.pyplot as plt
resid = y_test - pred
plt.scatter(pred, resid); plt.axhline(0)
```

### 46. What is stratified sampling or splitting?

Stratified splitting keeps the **class proportions the same** in the training and test (or each cross-validation fold) sets as in the full data. With random splitting on imbalanced data, a fold could by chance contain few or no minority examples, making metrics unreliable or training impossible. Stratification avoids this and is almost always used for classification.

**Example:** 5% of 2,000 customers churn (100 churners). A stratified 80/20 split puts about 80 churners in training and 20 in test. A plain random split might give only 12 in the test set.

```python
from sklearn.model_selection import train_test_split
X_tr, X_te, y_tr, y_te = train_test_split(X, y, test_size=0.2, stratify=y, random_state=42)
```

### 47. Time-series data: how do you split for validation?

Use **chronological splits**: train on the past and validate on the future, never random shuffling. Random splits let the model see future data while predicting the past (a form of data leakage) and give overly optimistic scores. Use walk-forward validation (expanding or rolling window), such as scikit-learn's `TimeSeriesSplit`. Also make sure features (like rolling averages) use only past information.

**Example:** Forecasting monthly sales with 36 months of data:
- Fold 1: train months 1–12, test 13–18
- Fold 2: train months 1–18, test 19–24
- Fold 3: train months 1–24, test 25–30

```python
from sklearn.model_selection import TimeSeriesSplit
for tr, te in TimeSeriesSplit(n_splits=3).split(X): ...
```

### 48. How do you detect and handle outliers?

Detect outliers with the **IQR rule** (values beyond Q1 − 1.5·IQR or Q3 + 1.5·IQR), **z-scores** (beyond about 3 standard deviations), box plots and scatter plots. First decide **why** they exist: data entry errors should be fixed or removed; genuine rare events (a huge legitimate purchase) may be exactly what you want to model. Handling options are removing, capping (winsorizing), transforming (log) or using robust methods (tree models, Huber loss, MAE).

**Example:** Salaries are mostly ₹30,000–₹90,000 with one entry of ₹9,00,00,000. With Q1 = 35,000 and Q3 = 70,000, IQR = 35,000, so the upper fence is 70,000 + 52,500 = 1,22,500. The value is flagged. If it is a typo, fix it; if it is a real CEO salary, cap it or log-transform.

**Interview tip:** Never drop outliers blindly. In fraud detection the outliers are the target.

### 49. How would you choose between a simple and a complex model?

Start with a **simple baseline** (majority class, mean, or logistic/linear regression) and add complexity only when it brings a meaningful, validated gain. Compare models using cross-validation on the metric that matches the business goal. Then weigh non-accuracy factors: interpretability and regulation, prediction latency, training and maintenance cost, amount of data and how stable the model is over time. A slightly less accurate model that is explainable and cheap is often the better business choice.

**Example:** Logistic regression gets AUC 0.86, XGBoost gets 0.88. In a regulated lending setting where each decision must be explained, the 0.02 gain may not justify losing transparency; for ad-click ranking where only accuracy matters, XGBoost wins.

| Factor | Favors simple | Favors complex |
|---|---|---|
| Explainability required | ✔ | |
| Large, rich data | | ✔ |
| Tight latency/cost | ✔ | |
| Large accuracy gain on validation | | ✔ |

### 50. Walk through building a churn prediction model.

A strong answer follows the full lifecycle:

1. **Define the problem:** what counts as churn (e.g. no activity for 60 days) and the prediction window (churn in the next 30 days). Agree on the business action.
2. **Collect labeled history:** customer records with usage, billing, complaints, tenure, contract type and the churn label.
3. **Split by time:** train on older customers or periods and test on newer ones to mimic production and avoid leakage.
4. **Prepare data and features:** handle missing values, encode categories, create features such as recent usage drop, complaint count in last 90 days and bill change.
5. **Handle imbalance:** churners may be only 5%. Use class weights or resampling in the training set.
6. **Train and compare:** baseline logistic regression, then Random Forest or gradient boosting, tuned with cross-validation.
7. **Evaluate:** use recall, precision, PR-AUC and lift; choose a threshold from the cost of a retention offer versus a lost customer.
8. **Deploy and act:** score customers regularly and feed the high-risk list to the retention team with targeted offers.
9. **Monitor and retrain:** track data and performance drift and measure whether retention campaigns actually reduce churn (A/B test).

```python
from sklearn.pipeline import make_pipeline
from sklearn.preprocessing import StandardScaler
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import classification_report

model = make_pipeline(StandardScaler(), LogisticRegression(class_weight="balanced"))
model.fit(X_train, y_train)
print(classification_report(y_test, model.predict(X_test)))
```

**Example outcome:** The model flags the top 10% riskiest customers; these contain 45% of all churners, so the retention budget is focused where it pays off.

**Interview tip:** Mention the business action and how you will measure impact. It shows you think beyond the model.
