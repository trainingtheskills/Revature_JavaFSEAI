# AI and ML Introduction: Detailed Interview Answers

**50 questions with detailed explanations and examples. Beginner-friendly.**

## Contents

- [Part 1: Foundations (Q1–Q10)](#part-1-foundations-q1q10)
- [Part 2: Model Quality and Data Preparation (Q11–Q22)](#part-2-model-quality-and-data-preparation-q11q22)
- [Part 3: How Models Learn (Q23–Q27)](#part-3-how-models-learn-q23q27)
- [Part 4: Types and Families of AI (Q28–Q35)](#part-4-types-and-families-of-ai-q28q35)
- [Part 5: Production, Ethics and Practice (Q36–Q50)](#part-5-production-ethics-and-practice-q36q50)

Code snippets use Python with scikit-learn and are meant to illustrate ideas, not to be production-ready.

---

## Part 1: Foundations (Q1–Q10)

### 1. What is Artificial Intelligence?

**Explanation:** Artificial Intelligence (AI) is the field of building computer systems that perform tasks that normally require human intelligence. These tasks include understanding language, recognizing images, reasoning, planning, learning from experience and making decisions. AI is a broad umbrella: it includes rule-based expert systems, search and planning algorithms, and modern machine learning. A system does not need to think like a human to count as AI; it only needs to show intelligent behaviour on a task. Most AI you meet today is "narrow": it is very good at one job, such as translating text or detecting fraud.

**Example:** A bank's fraud system that blocks a suspicious card payment at 3 a.m. in a foreign country is AI. So is a voice assistant that understands "set an alarm for 6" and a navigation app that picks the fastest route using live traffic.

**Key point:** AI is the goal (intelligent behaviour). ML is one popular way to reach it.

---

### 2. What is Machine Learning?

**Explanation:** Machine Learning (ML) is a subset of AI in which computers learn patterns from data instead of following hand-written rules. You give an algorithm many examples, and it adjusts an internal model so that it can make predictions on examples it has not seen. The more relevant, good-quality data it learns from, the better it usually becomes. ML is useful when the rules are too complex to write by hand, such as recognizing a face or predicting who will cancel a subscription.

**Example:** Instead of writing rules like "if the email contains 'prize' then spam," you show the algorithm 100,000 emails labeled spam or not spam. It learns which words, senders and links signal spam, including patterns no human wrote down.

```python
from sklearn.linear_model import LogisticRegression

X = [[0], [1], [0], [1], [1], [0]]   # 1 = contains suspicious link
y = [0, 1, 0, 1, 1, 0]               # 1 = spam
model = LogisticRegression().fit(X, y)
print(model.predict([[1]]))          # predicts spam for a suspicious link
```

**Interview tip:** Mention Arthur Samuel's idea of "giving computers the ability to learn without being explicitly programmed" if asked for a classic definition.

---

### 3. How are AI, ML and Deep Learning related?

**Explanation:** They are nested sets. AI is the broadest field. Machine Learning is a subset of AI that learns from data. Deep Learning is a subset of ML that uses neural networks with many layers. Not every AI system uses ML (a chess program using hand-coded search rules is AI but not ML), and not every ML model is deep learning (linear regression and decision trees are ML but not deep learning). Deep learning became dominant for images, speech and text because it learns useful features automatically from raw data.

**Example:**

| Level | Example |
|---|---|
| AI only (no learning) | A rule-based chatbot with fixed if-then replies |
| ML (not deep) | Random Forest predicting customer churn from a table |
| Deep Learning | A convolutional network identifying tumours in X-rays |

```
AI
 └── Machine Learning
       └── Deep Learning
```

**Key point:** AI ⊃ ML ⊃ DL. Large Language Models are deep learning models.

---

### 4. How does ML differ from traditional programming?

**Explanation:** In traditional programming, a developer writes explicit rules, and the program applies those rules to data to produce answers. In ML, you provide data and the known answers, and the algorithm works out the rules, which are stored in the model. This is powerful when rules are hard to express, change over time, or are hidden in large datasets. The trade-off is that ML needs good data, and its behaviour is statistical, so it can be wrong in ways rules would not be.

**Example:** Detecting a loan default.

- **Traditional:** "If income < 20,000 and missed payments > 2, reject." The developer must discover and maintain every rule.
- **ML:** Feed in 50,000 past loans with outcomes (repaid or defaulted). The model learns that a combination of income, debt ratio, age of credit history and payment behaviour predicts default.

| Approach | Input | Output |
|---|---|---|
| Traditional programming | Rules + Data | Answers |
| Machine learning | Data + Answers | Rules (the model) |

---

### 5. What are the main types of ML?

**Explanation:** There are five commonly named types.

1. **Supervised learning:** learns from labeled data (input and correct output) to predict outputs for new inputs.
2. **Unsupervised learning:** finds structure in unlabeled data, such as groups or unusual points.
3. **Semi-supervised learning:** uses a small labeled set plus a large unlabeled set.
4. **Self-supervised learning:** creates its own labels from the data, for example by hiding a word and predicting it.
5. **Reinforcement learning:** an agent learns by trial and error using rewards.

**Example:**

| Type | Business example |
|---|---|
| Supervised | Predict loan default from past labeled loans |
| Unsupervised | Group customers into segments |
| Semi-supervised | Classify medical images when only a few are labeled by doctors |
| Self-supervised | Pre-train a language model by predicting the next word |
| Reinforcement | A warehouse robot learning efficient routes |

---

### 6. What is Deep Learning?

**Explanation:** Deep Learning uses neural networks with many layers (hence "deep"). Each layer transforms its input into a slightly more abstract representation. For images, early layers may detect edges, middle layers detect shapes, and later layers detect objects such as faces or cars. The model learns these features by itself, so you do not have to design them by hand as in classical ML. Deep learning usually needs lots of data and computing power (often GPUs), but achieves excellent results on images, audio, video and text.

**Example:** A deep network for recognizing handwritten digits takes the raw pixels of a 28×28 image and outputs a digit from 0 to 9. A classical ML pipeline would need hand-built features (stroke counts, curvature) first.

**Interview tip:** Say when you would *not* use it: small tabular datasets, where gradient-boosted trees often match or beat deep learning with far less cost.

---

### 7. What is a dataset, a feature and a label?

**Explanation:** A **dataset** is a collection of records, usually a table. A **feature** is an input column the model uses to make its prediction (also called an independent variable or attribute). A **label** (or target) is the output the model should learn to predict (also called the dependent variable). Supervised learning needs both features and labels. Unsupervised learning has features only.

**Example:** Employee attrition dataset.

| Employee ID | Age | Salary | Experience | Attrition |
|---|---|---|---|---|
| E101 | 25 | 30,000 | 2 | Yes |
| E102 | 35 | 60,000 | 8 | No |

- Features: Age, Salary, Experience
- Label: Attrition
- Employee ID is an identifier, not a feature (it carries no predictive meaning).

```python
X = df[["Age", "Salary", "Experience"]]  # features
y = df["Attrition"]                      # label
```

---

### 8. What is the difference between structured and unstructured data?

**Explanation:** Structured data has a fixed format of rows and columns with defined types, such as a SQL table or a spreadsheet. It is easy to query and feed to classic ML algorithms. Unstructured data has no predefined schema: free text, images, audio, video, PDFs. It makes up most of the world's data, and it usually needs special processing, such as embeddings or deep learning, to turn it into numbers. There is also semi-structured data such as JSON or XML.

**Example:**

| Type | Example |
|---|---|
| Structured | A bank's transactions table: amount, date, account ID |
| Semi-structured | A JSON API response with nested fields |
| Unstructured | Customer support emails, call recordings, scanned invoices |

**Key point:** LLMs and deep learning made unstructured data much more usable for business.

---

### 9. What is the ML workflow?

**Explanation:** An ML project follows a repeating cycle.

1. **Define the problem** and the success metric (e.g. reduce churn by 10%).
2. **Collect data** from databases, logs or APIs.
3. **Clean and prepare** it: handle missing values, duplicates and errors.
4. **Engineer features** that capture useful signals.
5. **Train** one or more models.
6. **Evaluate** on unseen data using the right metrics.
7. **Deploy** it where the business can use predictions.
8. **Monitor and retrain** as data changes.

**Example:** A telecom company wants to reduce churn. It defines churn as "no activity for 60 days," pulls billing, usage and complaint data, builds features like "complaints in last 3 months," trains a classifier, checks recall on a test set, deploys scores to the CRM, and tracks monthly whether the model's accuracy is slipping.

**Interview tip:** Emphasize that most of the time goes to data preparation and deployment, not model training.

---

### 10. What is training, validation and test data?

**Explanation:** Data is split so you can measure how the model performs on examples it has never seen.

- **Training set:** the model learns from this.
- **Validation set:** used during development to compare models and tune hyperparameters.
- **Test set:** used once at the end for an unbiased final score.

A common split is 70/15/15 or 80/10/10. If you tune on the test set, it stops being a fair check, because your decisions have been influenced by it.

**Example:** With 10,000 customer records: 7,000 train, 1,500 validate, 1,500 test. You try three models, pick the best on the validation set, then report its score on the test set.

```python
from sklearn.model_selection import train_test_split

X_train, X_temp, y_train, y_temp = train_test_split(X, y, test_size=0.3, random_state=42)
X_val, X_test, y_val, y_test = train_test_split(X_temp, y_temp, test_size=0.5, random_state=42)
```

---

## Part 2: Model Quality and Data Preparation (Q11–Q22)

### 11. What is overfitting?

**Explanation:** Overfitting happens when a model learns the training data too well, including its noise and random quirks, so it fails to generalize to new data. The tell-tale sign is a big gap: very high accuracy on training data but much lower accuracy on validation or test data. It is common with very complex models, small datasets and too many features.

**Example:** A student who memorizes last year's exam answers scores 100% on that exam but fails a new exam with different questions. Similarly, a decision tree with no depth limit can reach 100% training accuracy but only 70% on test data.

```python
from sklearn.tree import DecisionTreeClassifier

tree = DecisionTreeClassifier().fit(X_train, y_train)      # unlimited depth
print(tree.score(X_train, y_train), tree.score(X_test, y_test))
# e.g. 1.00 vs 0.72 -> overfitting
```

---

### 12. What is underfitting?

**Explanation:** Underfitting means the model is too simple to capture the real pattern, so it performs poorly on both training and test data. Causes include a too-simple model, too few or poor features, too much regularization or too little training. Unlike overfitting, there is no large gap; both scores are just low.

**Example:** Using a straight line to predict house prices when the relationship with area is clearly curved, or using only "city" to predict price while ignoring size and bedrooms. Training accuracy is 55% and test accuracy is 54%.

**Fix:** Use a more flexible model, add informative features, reduce regularization or train longer.

---

### 13. What is the bias–variance trade-off?

**Explanation:** Model error comes from two main sources. **Bias** is error from overly simple assumptions (underfitting). **Variance** is error from being too sensitive to the particular training sample (overfitting). Making a model more complex lowers bias but raises variance, and the reverse is also true. The goal is the sweet spot where total error on new data is lowest.

**Example:** Think of darts.

| | Low variance | High variance |
|---|---|---|
| **Low bias** | Tight cluster on the bullseye (ideal) | Scattered around the bullseye |
| **High bias** | Tight cluster far from the bullseye | Scattered and off-target |

A linear model on a curved relationship has high bias. A deep unpruned tree has high variance.

**Interview tip:** Link it to solutions: regularization and more data lower variance; richer models and features lower bias.

---

### 14. How do you prevent overfitting?

**Explanation:** Several techniques work, often combined.

1. **More training data** (or data augmentation).
2. **Simpler model** (limit tree depth, fewer layers).
3. **Regularization** (L1/L2 penalties).
4. **Dropout** in neural networks.
5. **Early stopping** when validation loss stops improving.
6. **Cross-validation** to detect overfitting reliably.
7. **Feature selection** to remove noisy inputs.

**Example:**

```python
from sklearn.ensemble import RandomForestClassifier

# Limiting depth and requiring more samples per leaf reduces overfitting
model = RandomForestClassifier(max_depth=6, min_samples_leaf=10, random_state=42)
model.fit(X_train, y_train)
```

With a neural network, you would stop training at the epoch where validation loss is lowest.

---

### 15. What is cross-validation?

**Explanation:** Cross-validation gives a more reliable estimate of performance than a single split. In k-fold cross-validation, the data is split into k equal folds. The model is trained k times, each time using a different fold as the validation set and the other k–1 folds for training. The k scores are averaged. This uses all data for both training and validation and reduces the luck of one particular split.

**Example:** With 5-fold cross-validation on 1,000 rows, each fold has 200 rows. Scores might be 0.82, 0.85, 0.80, 0.84, 0.83, giving a mean of 0.828.

```python
from sklearn.model_selection import cross_val_score

scores = cross_val_score(model, X, y, cv=5)
print(scores.mean(), scores.std())
```

**Interview tip:** For time-series data use time-based splits, and for imbalanced classes use stratified folds.

---

### 16. What is feature engineering?

**Explanation:** Feature engineering is creating, transforming or selecting input variables so the model can learn better. Raw data is rarely in the best form. Good features often improve results more than switching algorithms. Common techniques include extracting parts of dates, combining columns, aggregating history, binning, log transforms and creating ratios.

**Example:** From a transaction timestamp and history, you might create:

- `hour_of_day` (night-time transactions may be riskier)
- `is_weekend`
- `amount_vs_avg_30d` = amount divided by the customer's 30-day average
- `txns_last_24h`

```python
df["hour"] = pd.to_datetime(df["time"]).dt.hour
df["amount_ratio"] = df["amount"] / df["avg_spend_30d"]
```

**Key point:** Domain knowledge is often the biggest advantage in feature engineering.

---

### 17. What is feature scaling and why is it needed?

**Explanation:** Feature scaling puts numeric features on comparable ranges. **Normalization** (min-max) rescales to 0–1. **Standardization** rescales to mean 0 and standard deviation 1. Algorithms that rely on distances (KNN, K-Means, SVM) or gradients (neural networks, logistic regression) are sensitive to scale, because a feature measured in large numbers can dominate. Tree-based models (decision trees, Random Forest) generally do not need scaling.

**Example:** Customers described by salary (30,000 to 120,000) and age (20 to 60). Without scaling, a KNN distance is almost entirely decided by salary, because a salary difference of 10,000 swamps an age difference of 10.

```python
from sklearn.preprocessing import StandardScaler

scaler = StandardScaler().fit(X_train)       # fit on training data only
X_train_s = scaler.transform(X_train)
X_test_s = scaler.transform(X_test)
```

**Interview tip:** Fit the scaler on training data only, to avoid data leakage.

---

### 18. How do you handle missing values?

**Explanation:** First understand *why* values are missing: random, systematic or meaningful. Then choose an approach.

- **Drop** rows or columns if few are missing or the column is mostly empty.
- **Impute** with mean or median (numeric) or mode (categorical).
- **Model-based imputation** (KNN or regression).
- **Add a missing indicator** column when "being missing" carries information.
- Some algorithms (e.g. gradient boosting libraries) handle missing values natively.

**Example:** In a loan dataset, `income` is missing for 5% of applicants. Fill with the median income (robust to outliers) and add `income_missing = 1`, since applicants who skip the field may behave differently.

```python
from sklearn.impute import SimpleImputer

imputer = SimpleImputer(strategy="median")
X_train_filled = imputer.fit_transform(X_train)
```

---

### 19. How do you encode categorical variables?

**Explanation:** Models need numbers, so categories must be converted.

- **One-hot encoding:** one binary column per category; best for nominal categories with few values.
- **Label/ordinal encoding:** integers; suitable when there is a natural order (Low < Medium < High).
- **Target encoding:** replace a category with the average target value (risk of leakage if not done carefully).
- **Embeddings:** learned dense vectors for high-cardinality features such as product IDs.

**Example:** `Transaction Type` with values UPI, Card, NEFT.

| Type | is_UPI | is_Card | is_NEFT |
|---|---|---|---|
| UPI | 1 | 0 | 0 |
| Card | 0 | 1 | 0 |
| NEFT | 0 | 0 | 1 |

```python
pd.get_dummies(df, columns=["transaction_type"])
```

**Key point:** Do not label-encode unordered categories for linear models, because it creates a fake order (UPI < Card < NEFT).

---

### 20. What is data leakage?

**Explanation:** Data leakage happens when information that would not be available at prediction time ends up in the training data. The model then looks excellent in testing but fails in production. Leakage can come from using future data, including the target in the features, or fitting preprocessing (scalers, imputers) on the full dataset before splitting.

**Example:** Predicting loan default and including the feature `collection_calls_made`. Collection calls only happen *after* a customer misses payments, so the feature almost reveals the answer. The model scores 99% in testing but is useless for deciding at application time.

**Another example:** Scaling the whole dataset, then splitting. Test-set statistics leaked into the training process.

**Interview tip:** Ask, "Would I have this feature at the moment I need to make the prediction?"

---

### 21. What are hyperparameters and how do they differ from parameters?

**Explanation:** **Parameters** are values the model learns from data during training, such as the weights in a linear model or the split thresholds in a tree. **Hyperparameters** are settings you choose *before* training that control the learning process or model structure, such as learning rate, number of trees, maximum depth and number of clusters. Parameters are learned automatically; hyperparameters are tuned by you.

**Example:**

| Model | Parameters (learned) | Hyperparameters (set by you) |
|---|---|---|
| Linear regression | Coefficients, intercept | Regularization strength |
| Random Forest | Splits and leaf values | n_estimators, max_depth |
| Neural network | Weights and biases | Learning rate, layers, batch size |
| K-Means | Centroid positions | k (number of clusters) |

---

### 22. What is hyperparameter tuning?

**Explanation:** Hyperparameter tuning means searching for the hyperparameter values that give the best validation performance. Methods include **grid search** (try every combination), **random search** (try random combinations, often more efficient) and **Bayesian optimization** (use earlier results to choose the next trial). Always evaluate with cross-validation or a validation set, never the test set.

**Example:**

```python
from sklearn.model_selection import GridSearchCV
from sklearn.ensemble import RandomForestClassifier

params = {"n_estimators": [100, 300], "max_depth": [4, 8, None]}
search = GridSearchCV(RandomForestClassifier(random_state=42), params, cv=5, scoring="f1")
search.fit(X_train, y_train)
print(search.best_params_, search.best_score_)
```

Here 2 × 3 = 6 combinations, each evaluated with 5 folds, so 30 model fits.

---

## Part 3: How Models Learn (Q23–Q27)

### 23. What is a loss function?

**Explanation:** A loss function measures how far a model's predictions are from the true values. Training tries to find parameters that make the loss as small as possible. Different tasks use different losses: **Mean Squared Error (MSE)** for regression and **cross-entropy (log loss)** for classification. The loss must be something the optimizer can differentiate, which can differ from the business metric you report (e.g. accuracy).

**Example:** Predicting house prices (in lakhs). True: 50, predicted: 45.

- Error = 5, squared error = 25
- Another house: true 90, predicted 100 → squared error = 100

MSE = (25 + 100) / 2 = 62.5. The model adjusts weights to reduce this number.

```python
import numpy as np
y_true = np.array([50, 90]); y_pred = np.array([45, 100])
mse = np.mean((y_true - y_pred) ** 2)   # 62.5
```

---

### 24. What is gradient descent?

**Explanation:** Gradient descent is the standard method for minimizing a loss function. It calculates the gradient (slope) of the loss with respect to each parameter, then moves the parameters a small step in the opposite direction, downhill. The step size is the **learning rate**. Too large a learning rate overshoots and may diverge; too small makes training slow. Variants include batch, stochastic (SGD), mini-batch and adaptive optimizers such as Adam.

**Example:** Minimize loss L(w) = (w – 3)². The gradient is 2(w – 3). Start at w = 0 with learning rate 0.1.

| Step | w | Gradient | New w |
|---|---|---|---|
| 1 | 0.00 | –6.00 | 0.60 |
| 2 | 0.60 | –4.80 | 1.08 |
| 3 | 1.08 | –3.84 | 1.46 |

It moves steadily toward w = 3, the minimum.

```python
w, lr = 0.0, 0.1
for _ in range(50):
    w -= lr * 2 * (w - 3)
print(round(w, 3))   # close to 3
```

---

### 25. What is a neural network?

**Explanation:** A neural network is a model made of layers of connected units called neurons. Each neuron multiplies its inputs by weights, adds a bias, and passes the result through an activation function. Layers are stacked: an input layer, one or more hidden layers and an output layer. By adjusting the weights during training, the network can learn very complex, non-linear relationships. It is loosely inspired by the brain, but it is just math on arrays of numbers.

**Example:** Predicting loan default from 3 inputs (income, debt ratio, age).

```
Inputs (3) → Hidden layer (8 neurons, ReLU) → Output (1 neuron, sigmoid → probability)
```

```python
from sklearn.neural_network import MLPClassifier
net = MLPClassifier(hidden_layer_sizes=(8,), max_iter=500).fit(X_train_s, y_train)
```

---

### 26. What is an activation function?

**Explanation:** An activation function is applied to a neuron's output to add non-linearity. Without it, stacking many layers would still be equivalent to one linear function, so the network could not learn curves or complex patterns. Common choices:

| Function | Output range | Typical use |
|---|---|---|
| **ReLU** | 0 to ∞ | Hidden layers (simple, fast, widely used) |
| **Sigmoid** | 0 to 1 | Binary classification output |
| **Tanh** | –1 to 1 | Hidden layers in some models |
| **Softmax** | probabilities summing to 1 | Multi-class output |

**Example:** ReLU(x) = max(0, x). ReLU(–3) = 0 and ReLU(2.5) = 2.5. Softmax on scores [2.0, 1.0, 0.1] gives roughly [0.66, 0.24, 0.10], a probability for each of three classes.

---

### 27. What is backpropagation?

**Explanation:** Backpropagation is the algorithm used to compute how much each weight in a neural network contributed to the loss. It first does a **forward pass** to compute predictions and loss. Then it applies the chain rule from calculus backward through the layers to compute gradients for every weight. An optimizer such as gradient descent then uses these gradients to update the weights. Modern libraries (PyTorch, TensorFlow) calculate this automatically.

**Example (tiny network):** y = w × x, loss = (y – t)². With x = 2, w = 1, target t = 6:

- Forward: y = 2, loss = (2 – 6)² = 16
- Backward: dLoss/dw = 2(y – t) × x = 2(–4)(2) = –16
- Update with learning rate 0.1: w = 1 – 0.1 × (–16) = 2.6

The new w makes y = 5.2, closer to 6.

```python
# PyTorch computes this automatically
# loss.backward()   # backpropagation
# optimizer.step()  # weight update
```

---

## Part 4: Types and Families of AI (Q28–Q35)

### 28. What is the difference between AI, Narrow AI and AGI?

**Explanation:** **Narrow AI** (weak AI) performs specific tasks and cannot transfer its skill to unrelated tasks. All AI in use today is narrow, even impressive systems such as chatbots and image generators. **Artificial General Intelligence (AGI)** refers to a hypothetical system with human-level ability across a wide range of intellectual tasks, able to learn new tasks without being built for them. AGI does not exist today, and experts disagree about when, or whether, it will. Some also discuss "superintelligence," meaning intelligence far beyond human level, which is also hypothetical.

**Example:** A chess engine that beats world champions cannot book a flight. A spam filter cannot diagnose a disease. Each is narrow. An AGI would be able to learn both without being redesigned.

**Interview tip:** Be careful with claims. Large language models are general-purpose in many text tasks, but are still generally considered narrow AI in the AGI sense.

---

### 29. What is generative AI?

**Explanation:** Generative AI models create new content, such as text, images, code, music and video, by learning the patterns and structure of their training data. Instead of only predicting a label, they produce new samples that resemble the data. Major families include Large Language Models, diffusion models for images, and GANs. Business uses include drafting emails, writing code, creating marketing images and summarizing documents.

**Example:** Prompt: "Write a polite reminder email for an unpaid invoice." A generative model produces a new, fluent email. A traditional classifier could only label an email as "invoice-related" or not.

**Key point:** Outputs are plausible, not guaranteed correct, so review is needed for important uses.

---

### 30. What is the difference between discriminative and generative models?

**Explanation:** A **discriminative model** learns the boundary between classes and predicts the label given the input, P(y | x). Examples: logistic regression, SVM, most neural network classifiers. A **generative model** learns how the data itself is produced, P(x) or P(x, y), so it can create new samples and also classify. Examples: Naive Bayes, Gaussian Mixture Models, GANs, and LLMs. Discriminative models are often more accurate for pure classification, while generative models are more flexible.

**Example:** Distinguishing cats from dogs.

- Discriminative: learns "what separates cat images from dog images" and outputs "cat" or "dog."
- Generative: learns what cats and dogs look like and can draw a new cat image, or compare which class explains an image better.

---

### 31. What is reinforcement learning?

**Explanation:** In reinforcement learning (RL), an **agent** interacts with an **environment**, takes **actions**, observes the new **state** and receives a **reward** (positive or negative). Over many trials, it learns a **policy**, a strategy that maximizes total reward over time. There are no labeled right answers; the agent learns from consequences. RL needs a safe way to explore, often a simulator, and a well-designed reward.

**Example:** A warehouse robot gets +1 for each package delivered, –0.1 per second of delay and –5 for a collision. Over thousands of simulated trips it learns efficient, safe routes. Other uses: game-playing agents, ad bidding strategies and tuning chatbots with human feedback (RLHF).

---

### 32. What is transfer learning?

**Explanation:** Transfer learning reuses a model trained on a large dataset for one task as the starting point for a different but related task. The early layers have already learned general features (edges and textures for images, grammar and meaning for text), so you only need to adapt the final layers or fine-tune with a small dataset. This saves data, time and compute and often improves accuracy.

**Example:** A model pre-trained on millions of general photos is fine-tuned on 2,000 photos of your factory's defective and good parts. It reaches strong accuracy that would be impossible training from scratch on only 2,000 images.

```python
# Sketch (PyTorch / torchvision style)
# model = torchvision.models.resnet18(weights="DEFAULT")
# for p in model.parameters(): p.requires_grad = False      # freeze base
# model.fc = torch.nn.Linear(model.fc.in_features, 2)       # new output layer
```

---

### 33. What is semi-supervised learning?

**Explanation:** Semi-supervised learning uses a small amount of labeled data together with a large amount of unlabeled data. Labels are often expensive because they need experts, so this approach gets more value from cheap unlabeled data. A common method is **pseudo-labeling**: train on the labeled data, predict labels for unlabeled data, keep confident predictions as extra training examples, and retrain.

**Example:** A hospital has 100,000 chest X-rays but radiologists have labeled only 2,000. A model is trained on the 2,000, labels the confident cases among the rest, and retrains on the enlarged set.

---

### 34. What is self-supervised learning?

**Explanation:** In self-supervised learning, the labels come from the data itself, so no human labeling is needed. The task is made up by hiding or transforming part of the input and asking the model to predict it. Because raw data (text, images, audio) is plentiful, this scales to huge datasets. It is how most modern language models are pre-trained: predict the next token or fill in a masked token.

**Example:** Take the sentence "The bank approved the loan." Hide a word: "The bank approved the ___." The correct answer ("loan") is the label, taken from the sentence itself. Repeat over billions of sentences and the model learns grammar, facts and meaning.

**Key point:** It is sometimes grouped under unsupervised learning because no manual labels are used.

---

### 35. What is an ML model's "inference"?

**Explanation:** Inference is using a trained model to make predictions on new, unseen data. Training is the learning phase (slow, compute-heavy, done occasionally). Inference is the usage phase (often needs to be fast, can run millions of times a day). Inference can be **batch** (score many records on a schedule) or **real-time** (respond within milliseconds to a request). Latency, cost and throughput matter most at inference time.

**Example:** A bank trains a fraud model weekly (training). Every time a card is swiped, the app sends the transaction details to the model and gets a fraud probability back in under 100 ms (inference).

```python
model.fit(X_train, y_train)          # training
proba = model.predict_proba(new_x)   # inference
```

---

## Part 5: Production, Ethics and Practice (Q36–Q50)

### 36. What is MLOps?

**Explanation:** MLOps (Machine Learning Operations) applies DevOps ideas to ML so that models can be deployed, monitored and updated reliably. It covers version control for code, data and models, automated training pipelines, testing, deployment, monitoring, and retraining triggers. Without it, many models stay stuck as notebooks and never deliver business value, or silently degrade after launch.

**Example:** A churn model pipeline:

1. New data lands daily and is validated.
2. A pipeline retrains the model weekly and records the experiment.
3. A candidate model is compared against the live model.
4. If better, it is deployed automatically.
5. Dashboards watch accuracy and data drift and alert the team.

**Common tools:** MLflow, Kubeflow, Airflow and cloud ML platforms.

---

### 37. What is model drift?

**Explanation:** Model drift means a deployed model's performance gets worse over time because the world changes. **Data drift** is a change in the distribution of the input features. **Concept drift** is a change in the relationship between inputs and the target. Drift is detected by monitoring input statistics and, when labels arrive, prediction quality. The usual fix is retraining on recent data.

**Example:**

- **Data drift:** A fraud model trained on mostly in-store purchases starts receiving many more online transactions.
- **Concept drift:** Before an economic downturn, a particular income level meant low default risk. Afterwards the same income no longer does, so the learned relationship is wrong.

**Interview tip:** Mention monitoring metrics such as the Population Stability Index or statistical tests like Kolmogorov–Smirnov.

---

### 38. What are common AI use cases in business?

**Explanation:** AI is applied across functions where there is data and a decision to improve.

| Area | Use case | Typical ML task |
|---|---|---|
| Banking | Fraud detection | Classification / anomaly detection |
| Telecom | Churn prediction | Classification |
| Retail | Product recommendations | Recommendation systems |
| Retail | Demand forecasting | Time-series forecasting / regression |
| Manufacturing | Predictive maintenance | Classification / regression |
| HR | Attrition prediction | Classification |
| Customer service | Chatbots, ticket routing | NLP / LLMs |
| Operations | Document processing | OCR, NLP, LLMs |

**Example:** An e-commerce company recommends products using purchase history, which can increase average order value, and uses forecasting to stock the right items before a festival season.

---

### 39. What is explainable AI (XAI)?

**Explanation:** Explainable AI covers methods that make model predictions understandable to humans. This matters for trust, debugging, regulation (e.g. explaining credit decisions) and finding bias. Some models are interpretable by design (linear models, small decision trees). For complex models, post-hoc tools help: **feature importance**, **SHAP** (how much each feature pushed a prediction up or down) and **LIME** (a local simple model that explains one prediction).

**Example:** A loan is rejected. A SHAP explanation shows: high debt-to-income ratio (+0.30 toward rejection), recent missed payment (+0.20), long credit history (–0.10). The loan officer can explain the decision to the applicant.

```python
# import shap
# explainer = shap.TreeExplainer(model)
# shap_values = explainer.shap_values(X_test)
```

---

### 40. What is bias in AI?

**Explanation:** Bias in AI means a system produces systematically unfair or skewed results for certain people or groups. It usually comes from data (unrepresentative samples, historical discrimination in labels), from design choices (poor proxy variables, wrong objective) or from how the system is used. Bias can be introduced silently, so it must be tested for, for example by comparing error rates across groups.

**Example:** A hiring model trained on ten years of past hiring decisions, where mostly one group was hired, may learn to prefer that group, even if gender is removed, by picking up proxies such as certain colleges or hobbies. Another example: a face recognition system trained mostly on light-skinned faces performs worse on darker skin tones.

**Mitigation:** Representative data, fairness metrics, bias audits, removing problematic proxies and human review.

---

### 41. What are responsible AI principles?

**Explanation:** Responsible AI means building and using AI in a way that is trustworthy and beneficial. Organizations often list these principles:

- **Fairness:** avoid unjust discrimination.
- **Transparency and explainability:** people understand how and when AI is used.
- **Accountability:** a human or team owns the outcomes.
- **Privacy and security:** protect personal data and systems.
- **Safety and reliability:** the system behaves as intended and fails safely.
- **Human oversight:** people can review, override and appeal decisions.

**Example:** A bank's fraud model flags transactions but does not automatically accuse customers. A trained analyst reviews flagged cases, customers can dispute decisions, data is access-controlled and fairness across customer groups is reviewed regularly.

---

### 42. What is the difference between a model and an algorithm?

**Explanation:** An **algorithm** is the procedure or recipe used to learn from data. A **model** is the result: the learned artifact (parameters plus structure) that is saved and used to make predictions. The same algorithm trained on different data produces different models.

**Example:** "Random Forest" is an algorithm. After you run it on your customer churn data, you get a specific trained model with particular trees and splits that can predict churn for your customers. If you train it on housing data, you get a different model from the same algorithm.

```python
algorithm = RandomForestClassifier()      # algorithm / untrained estimator
model = algorithm.fit(X_train, y_train)   # trained model
```

---

### 43. What is the curse of dimensionality?

**Explanation:** As the number of features (dimensions) increases, the space grows exponentially, so data becomes sparse. Points become far apart and distances between them become less meaningful, which hurts distance-based methods such as KNN and clustering. Models also have more ways to overfit and need much more data to cover the space. Solutions include feature selection, dimensionality reduction (PCA), regularization and collecting more data.

**Example:** With 1 feature split into 10 bins, you need about 10 samples to cover the space. With 6 features, covering every bin needs 10⁶ = 1,000,000 combinations. With 1,000 customers and 200 features, the data is extremely sparse.

**Interview tip:** Say that having many features is not bad per se, but useless ones add noise and need more data.

---

### 44. What is a baseline model and why use one?

**Explanation:** A baseline is a simple model or rule that sets the minimum performance a real model must beat. It tells you whether a complex model is actually adding value and gives you a sanity check. Common baselines are predicting the majority class, predicting the mean or median, using last period's value (for forecasts), or a basic logistic regression.

**Example:** For churn, only 8% of customers churn. A baseline that predicts "no churn" for everyone has 92% accuracy but zero recall. A new model with 93% accuracy is barely better, and you would judge it on recall and precision instead.

```python
from sklearn.dummy import DummyClassifier

baseline = DummyClassifier(strategy="most_frequent").fit(X_train, y_train)
print(baseline.score(X_test, y_test))
```

---

### 45. What is the role of GPUs in AI?

**Explanation:** Training and running neural networks consists mostly of huge matrix multiplications. A CPU has a few powerful cores that run tasks one after another, while a GPU has thousands of smaller cores that run many calculations in parallel. This makes GPUs far faster for deep learning. Specialized chips such as TPUs and other AI accelerators serve the same purpose. Memory (VRAM) is often the limiting factor for model size and batch size.

**Example:** Training an image classifier that takes many hours on a CPU may take a fraction of that on a GPU. Large language models are trained on clusters with thousands of accelerators.

**Key point:** Classic ML on tables (Random Forest, linear models) usually runs fine on CPUs.

---

### 46. What is the difference between batch size, epoch and iteration?

**Explanation:**

- **Epoch:** one full pass through the entire training dataset.
- **Batch size:** the number of samples processed before one weight update.
- **Iteration:** one weight update, i.e. one batch processed.

Number of iterations per epoch = dataset size ÷ batch size.

**Example:** 2,000 training samples, batch size 100.

- Iterations per epoch = 2,000 ÷ 100 = 20
- Training for 10 epochs = 200 iterations (weight updates)

Smaller batches give noisier but more frequent updates; larger batches are more stable and use hardware efficiently but need more memory.

---

### 47. What is a confusion matrix?

**Explanation:** A confusion matrix is a table comparing actual classes with predicted classes for a classification model. For a binary problem it has four cells: **True Positives (TP)**, **False Positives (FP)**, **True Negatives (TN)** and **False Negatives (FN)**. Many metrics derive from it: precision = TP/(TP+FP), recall = TP/(TP+FN), accuracy = (TP+TN)/total.

**Example:** A fraud model tested on 1,000 transactions (50 truly fraud).

| | Predicted Fraud | Predicted Legit |
|---|---|---|
| **Actual Fraud** | TP = 40 | FN = 10 |
| **Actual Legit** | FP = 30 | TN = 920 |

- Accuracy = (40 + 920) / 1000 = 96%
- Precision = 40 / 70 ≈ 57%
- Recall = 40 / 50 = 80%

```python
from sklearn.metrics import confusion_matrix
print(confusion_matrix(y_test, y_pred))
```

---

### 48. What is the Turing Test?

**Explanation:** Proposed by Alan Turing in 1950, the Turing Test (the "imitation game") asks whether a machine can converse so well that a human judge cannot reliably tell it from a human. A judge chats through text with a hidden human and a hidden machine. If the judge cannot consistently pick which is which, the machine is said to show intelligent behaviour. The test is historically important, but is criticized because it measures imitation of human conversation rather than understanding, and a system can pass by being convincing without being truly intelligent.

**Example:** A judge asks both hidden participants about their weekend and a poem. If the judge guesses correctly only about half the time, the machine is considered to have passed that run.

**Interview tip:** Mention that modern benchmarks for AI are task-specific, rather than relying on the Turing Test.

---

### 49. When should you not use ML?

**Explanation:** ML is not always the right tool. Avoid it when:

1. **Simple rules solve it:** a clear, stable rule is cheaper and more transparent.
2. **There is not enough data,** or the data is poor, biased or unlabeled with no way to label it.
3. **Errors are unacceptable** and cannot be reviewed (some safety-critical decisions).
4. **Full explainability is legally required** and cannot be achieved by the model.
5. **Cost outweighs benefit:** building, deploying and maintaining a model is more expensive than the value created.
6. **The pattern is not learnable,** or the data has no signal for the target.

**Example:** Calculating GST on an invoice is a deterministic formula. Using ML would be slower, less accurate and harder to audit. By contrast, predicting which customers will churn has no simple rule, so ML is appropriate.

---

### 50. How do you decide whether a business problem is suitable for ML?

**Explanation:** Use a short checklist.

1. **Clear objective:** what decision will improve, and how will you measure it?
2. **Data availability:** is there enough relevant, accessible, legal-to-use data, with labels if supervised?
3. **Learnable pattern:** is there a plausible link between inputs and the outcome?
4. **Actionable output:** can the business act on the prediction in time?
5. **Value vs cost:** is the expected benefit larger than the cost to build and run it?
6. **Risk and ethics:** are fairness, privacy and regulation manageable?
7. **Baseline comparison:** does it beat a simple rule?

**Example:** *Telecom churn.*

- Objective: cut monthly churn from 3% to 2.5%.
- Data: 3 years of usage, billing, complaints, with churn labels. Yes.
- Pattern: low usage plus complaints plausibly predicts leaving. Yes.
- Action: retention team calls the top 5% highest-risk customers weekly. Yes.
- Value: each retained customer is worth far more than the cost of an offer. Yes.

The problem is a good ML candidate. Compare with "decide the office seating plan," which has little data and no measurable outcome, so ML is not needed.

---

## Quick Revision Summary

| Topic | One-line recall |
|---|---|
| AI vs ML vs DL | AI ⊃ ML ⊃ DL |
| ML vs programming | Data + answers → rules (model) |
| Overfitting | Great on train, poor on new data |
| Underfitting | Poor on both |
| Bias vs variance | Too simple vs too sensitive |
| Cross-validation | Rotate folds for a reliable score |
| Data leakage | Using information unavailable at prediction time |
| Hyperparameters | Set by you before training |
| Gradient descent | Step downhill on the loss |
| Backpropagation | Chain rule to get gradients |
| Model drift | Performance decays as the world changes |
| Baseline | The simple bar a model must beat |
