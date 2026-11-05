# AI, ML and LLM Interview Questions

**250 questions with short answers: 50 per topic, ordered from basic to advanced.**

## Contents

1. [AI and ML Introduction](#1-ai-and-ml-introduction)
2. [Supervised Learning](#2-supervised-learning)
3. [Unsupervised Learning](#3-unsupervised-learning)
4. [LLMs: Introduction, Use Cases, Transformer, Attention and Best Practices](#4-llms-introduction-use-cases-transformer-attention-and-best-practices)
5. [LLM Models: GPT, BERT, Claude, Llama, Copilot and Codeium](#5-llm-models-gpt-bert-claude-llama-copilot-and-codeium)

---

## 1. AI and ML Introduction

**1. What is Artificial Intelligence?**
Building systems that perform tasks normally needing human intelligence, such as reasoning, perception, language understanding and decision-making.

**2. What is Machine Learning?**
A subset of AI where systems learn patterns from data to make predictions or decisions without being explicitly programmed with rules.

**3. How are AI, ML and Deep Learning related?**
Deep Learning is a subset of ML (neural networks with many layers), and ML is a subset of AI. AI ⊃ ML ⊃ DL.

**4. How does ML differ from traditional programming?**
Traditional programming takes rules and data and produces answers. ML takes data and answers and produces the rules (the model).

**5. What are the main types of ML?**
Supervised, unsupervised, semi-supervised, self-supervised and reinforcement learning.

**6. What is Deep Learning?**
ML using multi-layer neural networks that learn hierarchical features automatically, especially from images, text and audio.

**7. What is a dataset, a feature and a label?**
A dataset is a collection of records. Features are the input variables. The label (target) is the output the model should predict.

**8. What is the difference between structured and unstructured data?**
Structured data fits tables with fixed columns (e.g. SQL). Unstructured data has no fixed schema (text, images, audio, video).

**9. What is the ML workflow?**
Define the problem, collect data, clean and prepare it, engineer features, train, evaluate, deploy, then monitor and retrain.

**10. What is training, validation and test data?**
Training data fits the model. Validation data tunes hyperparameters and compares models. Test data gives a final, unbiased estimate of performance.

**11. What is overfitting?**
The model memorizes training data, including noise, and performs poorly on new data (low training error, high test error).

**12. What is underfitting?**
The model is too simple to capture the patterns, so it does poorly on both training and test data.

**13. What is the bias–variance trade-off?**
High bias means oversimplified models (underfit). High variance means models too sensitive to training data (overfit). Good models balance both.

**14. How do you prevent overfitting?**
More data, simpler models, regularization (L1/L2), dropout, early stopping, cross-validation and data augmentation.

**15. What is cross-validation?**
Splitting data into k folds, training on k–1 and validating on the remaining fold in rotation, then averaging the scores for a more reliable estimate.

**16. What is feature engineering?**
Creating, transforming or selecting input variables to improve model performance, such as extracting the hour of day from a timestamp.

**17. What is feature scaling and why is it needed?**
Putting features on a similar range (normalization or standardization). It matters for distance-based and gradient-based algorithms such as KNN, SVM and neural networks.

**18. How do you handle missing values?**
Remove rows or columns, impute with mean, median, mode or model predictions, or add a "missing" indicator. The choice depends on how much is missing and why.

**19. How do you encode categorical variables?**
One-hot encoding, label or ordinal encoding, target encoding, or embeddings for high-cardinality features.

**20. What is data leakage?**
Information from outside the training set (e.g. test data or future data) leaks into training, giving unrealistically good results that fail in production.

**21. What are hyperparameters and how do they differ from parameters?**
Parameters are learned from data (e.g. weights). Hyperparameters are set before training (e.g. learning rate, tree depth).

**22. What is hyperparameter tuning?**
Searching for the best hyperparameter values using grid search, random search or Bayesian optimization, evaluated on validation data.

**23. What is a loss function?**
A function measuring how wrong the predictions are (e.g. MSE, cross-entropy). Training minimizes it.

**24. What is gradient descent?**
An optimization method that iteratively updates parameters in the direction that reduces the loss, scaled by the learning rate.

**25. What is a neural network?**
Layers of connected nodes (neurons) that apply weights, biases and activation functions to transform inputs into outputs.

**26. What is an activation function?**
A non-linear function (ReLU, sigmoid, tanh, softmax) that lets networks learn complex patterns.

**27. What is backpropagation?**
Computing the gradient of the loss with respect to each weight by applying the chain rule backward through the network.

**28. What is the difference between AI, Narrow AI and AGI?**
Narrow AI does specific tasks (today's systems). AGI would match human-level ability across many tasks and does not exist yet.

**29. What is generative AI?**
Models that create new content (text, images, code, audio) by learning the patterns of their training data.

**30. What is the difference between discriminative and generative models?**
Discriminative models learn decision boundaries, P(y|x). Generative models learn how data is produced, P(x) or P(x,y), and can generate new samples.

**31. What is reinforcement learning?**
An agent learns by interacting with an environment, receiving rewards or penalties and learning a policy that maximizes cumulative reward.

**32. What is transfer learning?**
Reusing a model trained on one task as the starting point for another, saving data and compute.

**33. What is semi-supervised learning?**
Learning from a small labeled set plus a large unlabeled set.

**34. What is self-supervised learning?**
The model creates its own labels from the data (e.g. predicting masked or next words), which is how modern LLMs are pre-trained.

**35. What is an ML model's "inference"?**
Using a trained model to make predictions on new data.

**36. What is MLOps?**
Practices for deploying, monitoring, versioning and retraining ML models reliably in production.

**37. What is model drift?**
Model performance degrades over time because the data distribution (data drift) or the input-output relationship (concept drift) changes.

**38. What are common AI use cases in business?**
Fraud detection, churn prediction, recommendations, demand forecasting, predictive maintenance, chatbots and document processing.

**39. What is explainable AI (XAI)?**
Techniques such as SHAP, LIME and feature importance that make model decisions understandable to humans.

**40. What is bias in AI?**
Systematic unfairness caused by skewed data, labeling or design. It can lead to discriminatory outcomes for certain groups.

**41. What are responsible AI principles?**
Fairness, transparency, accountability, privacy, safety and human oversight.

**42. What is the difference between a model and an algorithm?**
An algorithm is the learning procedure (e.g. Random Forest). A model is the trained result of running that algorithm on data.

**43. What is the curse of dimensionality?**
As the number of features grows, data becomes sparse and distances lose meaning, so models need far more data and may overfit.

**44. What is a baseline model and why use one?**
A simple model (e.g. predicting the majority class or the mean) used as a reference. A complex model must beat it to be worthwhile.

**45. What is the role of GPUs in AI?**
They run many parallel computations, which is ideal for matrix operations in neural network training and inference.

**46. What is the difference between batch size, epoch and iteration?**
Batch size is the number of samples per update. An epoch is one pass over the whole dataset. An iteration is one batch update.

**47. What is a confusion matrix?**
A table of true positives, false positives, true negatives and false negatives, used to evaluate classification.

**48. What is the Turing Test?**
A test where a human judge converses with a machine and a human. If the judge cannot reliably tell them apart, the machine is said to exhibit intelligent behaviour.

**49. When should you not use ML?**
When simple rules solve the problem, when there is too little or poor-quality data, when errors are unacceptable and unexplainable, or when the cost outweighs the benefit.

**50. How do you decide whether a business problem is suitable for ML?**
Check that there is a clear measurable objective, enough relevant data, patterns that can be learned, an actionable output and a way to measure business impact.

---

## 2. Supervised Learning

**1. What is supervised learning?**
Learning from labeled data, where each input has a known output, to predict the output for new inputs.

**2. What are the two main types of supervised problems?**
Classification (predict a category) and regression (predict a number).

**3. Give examples of classification and regression.**
Classification: spam detection, fraud detection, churn. Regression: house price, sales forecast, days to machine failure.

**4. What is the difference between binary, multi-class and multi-label classification?**
Binary has two classes. Multi-class has several, with one chosen per sample. Multi-label lets a sample have several labels at once.

**5. What is linear regression?**
Models the target as a linear combination of features, fitting coefficients by minimizing squared error.

**6. What are the assumptions of linear regression?**
Linearity, independence of errors, constant variance (homoscedasticity), normally distributed errors and no severe multicollinearity.

**7. What is logistic regression?**
A classification algorithm that applies the sigmoid function to a linear combination of features to output a probability.

**8. Why is logistic regression called regression if it classifies?**
It regresses the log-odds of the class on the features, then a threshold turns the probability into a class.

**9. What is a decision tree?**
A tree of if-then splits on features that leads to a prediction at the leaves.

**10. How does a decision tree choose splits?**
By maximizing information gain or minimizing impurity (Gini or entropy for classification, variance or MSE for regression).

**11. What are the drawbacks of decision trees?**
They overfit easily, are unstable to small data changes and can be biased toward features with many levels.

**12. What is Random Forest?**
An ensemble of many decision trees trained on bootstrapped samples and random feature subsets, with predictions averaged or voted.

**13. What is bagging?**
Training models on bootstrap samples and combining them to reduce variance.

**14. What is boosting?**
Training models sequentially, each correcting the errors of the previous, to reduce bias. Examples are AdaBoost, Gradient Boosting, XGBoost and LightGBM.

**15. Bagging vs boosting?**
Bagging is parallel and reduces variance. Boosting is sequential and reduces bias, but is more prone to overfitting if not regularized.

**16. What is K-Nearest Neighbors (KNN)?**
Predicts by the majority class (or average value) of the k closest training points.

**17. How do you choose k in KNN?**
Using cross-validation. Small k overfits (noisy), large k underfits (too smooth). Odd k avoids ties in binary problems.

**18. Why does KNN need feature scaling?**
It relies on distances, so large-scale features would dominate the result.

**19. What is a Support Vector Machine (SVM)?**
Finds the hyperplane that separates classes with the maximum margin.

**20. What is the kernel trick?**
Implicitly mapping data into a higher-dimensional space (e.g. RBF, polynomial kernel) so non-linear data becomes separable, without computing the mapping.

**21. What is Naive Bayes?**
A probabilistic classifier using Bayes' theorem with the "naive" assumption that features are conditionally independent given the class.

**22. When is Naive Bayes a good choice?**
Text classification and spam filtering, small datasets and cases needing a fast baseline.

**23. What is regularization?**
Adding a penalty on model complexity to the loss to reduce overfitting.

**24. L1 vs L2 regularization?**
L1 (Lasso) adds the sum of absolute weights and can zero out features (feature selection). L2 (Ridge) adds the sum of squared weights and shrinks weights smoothly.

**25. What is Elastic Net?**
A combination of L1 and L2 penalties.

**26. What metrics are used for classification?**
Accuracy, precision, recall, F1-score, ROC-AUC, PR-AUC and log loss.

**27. Define precision and recall.**
Precision = TP/(TP+FP), the share of predicted positives that are correct. Recall = TP/(TP+FN), the share of actual positives found.

**28. What is the F1-score?**
The harmonic mean of precision and recall.

**29. Why is accuracy misleading on imbalanced data?**
A model predicting only the majority class can score 99% accuracy while missing every minority case (e.g. fraud).

**30. What is ROC-AUC?**
The area under the curve of true positive rate versus false positive rate across thresholds. It measures how well the model ranks positives above negatives.

**31. When prefer PR-AUC over ROC-AUC?**
When classes are highly imbalanced and the positive class matters most.

**32. When do you prioritize recall versus precision?**
Recall when missing a positive is costly (disease, fraud). Precision when false alarms are costly (spam filter blocking real email).

**33. What metrics are used for regression?**
MAE, MSE, RMSE, R² and MAPE.

**34. MAE vs RMSE?**
MAE treats all errors equally and is robust to outliers. RMSE penalizes large errors more heavily.

**35. What does R² mean?**
The proportion of target variance explained by the model. Adjusted R² penalizes useless features.

**36. How do you handle class imbalance?**
Resampling (oversampling, SMOTE, undersampling), class weights, different thresholds, suitable metrics and anomaly detection approaches.

**37. What is SMOTE?**
Synthetic Minority Over-sampling Technique: creates synthetic minority examples by interpolating between existing neighbours.

**38. What is a decision threshold?**
The probability cut-off (default 0.5) that converts a score into a class. It can be tuned to trade precision against recall.

**39. What is multicollinearity and how do you detect it?**
Strong correlation among features, making coefficients unstable. Detect with a correlation matrix or Variance Inflation Factor (VIF).

**40. What is the difference between parametric and non-parametric models?**
Parametric models have a fixed number of parameters (linear regression). Non-parametric models grow with data (KNN, decision trees).

**41. What is gradient boosting and why is XGBoost popular?**
Gradient boosting fits each new tree to the negative gradient of the loss. XGBoost adds regularization, speed, missing-value handling and strong performance on tabular data.

**42. How do you get feature importance?**
Tree-based importance, permutation importance, coefficients of linear models, or SHAP values.

**43. What is stacking?**
Training a meta-model on the predictions of several base models.

**44. What is probability calibration?**
Adjusting predicted probabilities so they match real frequencies (e.g. Platt scaling, isotonic regression).

**45. What are residuals and why plot them?**
Residuals are actual minus predicted values. Plots reveal non-linearity, heteroscedasticity and outliers.

**46. What is stratified sampling or splitting?**
Keeping class proportions the same in each split, which matters for imbalanced data.

**47. Time-series data: how do you split for validation?**
Use chronological splits (e.g. walk-forward or TimeSeriesSplit), never random shuffling, to avoid using future data to predict the past.

**48. How do you detect and handle outliers?**
Use IQR, z-scores or visual checks. Remove, cap or transform them, or use robust models, depending on whether they are errors or real events.

**49. How would you choose between a simple and a complex model?**
Compare against a baseline using cross-validation, and consider interpretability, latency, data size and maintenance cost, not only accuracy.

**50. Walk through building a churn prediction model.**
Define churn, collect labeled history, split by time, engineer features (usage, tenure, complaints), handle imbalance, train and compare models, evaluate with recall and precision, tune the threshold, deploy, monitor drift, and link predictions to a retention action.

---

## 3. Unsupervised Learning

**1. What is unsupervised learning?**
Finding structure in unlabeled data without a target variable.

**2. What are the main types of unsupervised tasks?**
Clustering, dimensionality reduction, anomaly detection and association rule mining (plus density estimation).

**3. What is clustering?**
Grouping similar data points so that points in a cluster are more alike than points in other clusters.

**4. Give business examples of clustering.**
Customer segmentation, grouping similar servers, document grouping and grouping employees by profile.

**5. How does K-Means work?**
Choose k, place k centroids, assign each point to its nearest centroid, recompute centroids as cluster means, and repeat until stable.

**6. How do you choose k in K-Means?**
Elbow method, silhouette score, gap statistic and business knowledge.

**7. What are the limitations of K-Means?**
Needs k in advance, sensitive to initialization and outliers, assumes spherical similar-sized clusters, and needs scaled numeric features.

**8. What is K-Means++?**
A smarter initialization that spreads initial centroids apart, giving better and more stable results.

**9. What is the elbow method?**
Plot within-cluster sum of squares against k and pick the point where improvement slows sharply.

**10. What is the silhouette score?**
Measures how close a point is to its own cluster versus the nearest other cluster. It ranges from –1 to 1, and higher is better.

**11. What is hierarchical clustering?**
Builds a tree (dendrogram) of clusters either bottom-up (agglomerative) or top-down (divisive).

**12. What are linkage methods?**
Rules for distance between clusters: single, complete, average and Ward.

**13. What is a dendrogram?**
A tree diagram showing merges and the distances at which they happen. Cutting it at a level gives the clusters.

**14. What is DBSCAN?**
A density-based algorithm that groups densely packed points and marks isolated points as noise.

**15. What are DBSCAN's parameters?**
eps (neighbourhood radius) and min_samples (minimum points to form a dense region).

**16. K-Means vs DBSCAN?**
K-Means needs k and finds spherical clusters. DBSCAN needs no k, finds arbitrary shapes and detects noise, but struggles with varying densities.

**17. What is Gaussian Mixture Model (GMM)?**
A probabilistic model assuming data comes from a mix of Gaussians, giving soft (probabilistic) cluster membership, fitted with Expectation-Maximization.

**18. Hard vs soft clustering?**
Hard assigns each point to one cluster (K-Means). Soft gives membership probabilities (GMM).

**19. Why does scaling matter for clustering?**
Distance-based methods are dominated by large-range features unless scaled.

**20. Which distance metrics are used?**
Euclidean, Manhattan, cosine and Hamming (for categorical data), among others.

**21. How do you cluster categorical data?**
K-Modes, K-Prototypes (mixed data), Gower distance with hierarchical methods, or encode and embed features.

**22. How do you evaluate clustering without labels?**
Silhouette score, Davies–Bouldin index, Calinski–Harabasz index, stability checks and business usefulness.

**23. How do you interpret clusters?**
Profile each cluster by average feature values, size and behaviour, then name them (e.g. "high-income high-spenders").

**24. What is dimensionality reduction?**
Reducing the number of features while keeping as much information as possible.

**25. Why reduce dimensions?**
To fight the curse of dimensionality, reduce noise and compute, avoid overfitting and enable visualization.

**26. What is PCA?**
Principal Component Analysis projects data onto orthogonal directions (principal components) that capture the most variance.

**27. How do you choose the number of components in PCA?**
Use explained variance (e.g. keep 90–95%) or a scree plot.

**28. What are the limitations of PCA?**
Linear only, sensitive to scale, and components are hard to interpret.

**29. What is t-SNE?**
A non-linear technique for visualizing high-dimensional data in 2D or 3D by preserving local neighbourhoods.

**30. t-SNE vs PCA?**
PCA is linear, fast and preserves global structure. t-SNE is non-linear, slower and good for visual cluster exploration, but distances between clusters are not meaningful.

**31. What is UMAP?**
A non-linear reduction method, faster than t-SNE, that better preserves global structure and can be used beyond visualization.

**32. What is an autoencoder?**
A neural network that compresses input into a small latent code and reconstructs it, learning compact representations.

**33. What is anomaly detection?**
Identifying data points that differ significantly from the norm.

**34. Why is anomaly detection often unsupervised?**
Anomalies are rare and often unlabeled, and new types appear that were never seen before.

**35. What is Isolation Forest?**
Builds random trees that isolate points. Anomalies need fewer splits to isolate, so they get higher anomaly scores.

**36. What is One-Class SVM?**
Learns a boundary around normal data and flags points outside it.

**37. How do autoencoders detect anomalies?**
They reconstruct normal data well. A high reconstruction error indicates an anomaly.

**38. What is Local Outlier Factor (LOF)?**
Compares a point's local density with its neighbours' densities. Much lower density marks an outlier.

**39. What is association rule mining?**
Finding items that frequently occur together, e.g. "customers who buy bread also buy butter" (market basket analysis).

**40. Define support, confidence and lift.**
Support: how often the itemset appears. Confidence: P(B|A). Lift: confidence divided by the baseline probability of B. Lift above 1 indicates a real association.

**41. What are Apriori and FP-Growth?**
Algorithms to find frequent itemsets. Apriori generates candidates level by level. FP-Growth uses a compact tree and is usually faster.

**42. What is customer segmentation and how is it done?**
Group customers by behaviour or value, e.g. with RFM features plus K-Means, then design targeted campaigns.

**43. What is RFM analysis?**
Recency, Frequency and Monetary value, three features commonly used to segment customers.

**44. What is topic modeling?**
Discovering hidden themes in text collections, e.g. with LDA or embedding-based clustering.

**45. What is a recommendation system's link to unsupervised learning?**
Collaborative filtering and matrix factorization find latent patterns in user–item interactions without explicit labels.

**46. What is self-supervised learning and how does it relate?**
It derives labels from the data itself (e.g. predict masked words). It sits between supervised and unsupervised learning and powers LLM pre-training.

**47. What are the main challenges of unsupervised learning?**
No ground truth for evaluation, choosing the number of clusters, sensitivity to scaling, and subjective interpretation.

**48. How do you handle outliers in clustering?**
Remove or cap them, use robust algorithms such as DBSCAN, or analyse them separately as anomalies.

**49. Can you combine supervised and unsupervised learning?**
Yes: use cluster labels or PCA components as features for a supervised model, use clustering to find groups and then fit one model per group, or pre-train with self-supervision and then fine-tune.

**50. Given unlabeled customer data, how would you proceed?**
Clean and scale the data, engineer features (e.g. RFM), try K-Means and hierarchical clustering, pick k using elbow and silhouette, profile and name the clusters, validate with business teams, then act (targeted offers) and track the results.

---

## 4. LLMs: Introduction, Use Cases, Transformer, Attention and Best Practices

### 4.1 LLM Introduction (Q1–Q12)

**1. What is a Large Language Model (LLM)?**
A neural network, usually a Transformer, trained on huge amounts of text to predict the next token and, as a result, to understand and generate language.

**2. Why are they called "large"?**
They have billions of parameters and are trained on very large datasets with substantial compute.

**3. What is a token?**
A unit of text (word, sub-word or character) that the model processes. English averages roughly 3–4 characters per token.

**4. What is tokenization? Name common methods.**
Splitting text into tokens. Common methods are Byte-Pair Encoding (BPE), WordPiece and SentencePiece.

**5. What is a context window?**
The maximum number of tokens (input plus output) the model can consider at once.

**6. What are the stages of LLM training?**
Pre-training on large text (self-supervised), then supervised fine-tuning on instructions, then alignment (e.g. RLHF, DPO or other preference-based methods).

**7. What is pre-training vs fine-tuning?**
Pre-training learns general language from massive data. Fine-tuning adapts the model to a task, domain or style using smaller specific data.

**8. What is RLHF?**
Reinforcement Learning from Human Feedback: a reward model trained on human preference rankings guides the model to produce more helpful and safe responses.

**9. What is an embedding?**
A dense numeric vector representing the meaning of text, so that similar meanings are close in vector space.

**10. What is a hallucination?**
When an LLM generates fluent but false or unsupported information.

**11. What do temperature, top-p and top-k do?**
They control randomness in sampling. Low temperature gives focused, deterministic output, and high gives more varied output. Top-k and top-p limit the candidate tokens considered.

**12. What are scaling laws?**
Empirical relationships showing that model quality improves predictably with more parameters, data and compute.

### 4.2 LLM Use Cases (Q13–Q22)

**13. What are common enterprise use cases of LLMs?**
Chatbots and support, summarization, content drafting, code assistance, search and Q&A, translation, data extraction and classification.

**14. How can LLMs help in customer support?**
Answer FAQs, draft replies, summarize tickets, route requests and assist agents with relevant knowledge.

**15. How are LLMs used in software development?**
Code completion, generation, explanation, refactoring, test writing, bug fixing and documentation.

**16. How are LLMs used for document processing?**
Summarizing, extracting fields, classifying and answering questions over contracts, reports and emails.

**17. What is RAG (Retrieval-Augmented Generation)?**
Retrieving relevant documents from an external store and giving them to the LLM as context so answers are grounded in current, private data.

**18. Why use RAG rather than fine-tuning?**
RAG updates knowledge without retraining, cites sources, reduces hallucination and is cheaper. Fine-tuning suits style, format or specialized behaviour.

**19. What is a vector database?**
A database that stores embeddings and finds the nearest ones quickly, used for semantic search and RAG (e.g. FAISS, Pinecone, Weaviate, pgvector).

**20. What are AI agents?**
LLM-based systems that plan, use tools (search, APIs, code), observe results and iterate to complete multi-step tasks.

**21. What is function or tool calling?**
The model outputs structured requests to call external functions or APIs, and the application executes them and returns results.

**22. Where are LLMs a poor fit?**
Exact calculation, deterministic rules, high-stakes decisions without review, and tasks needing guaranteed factual accuracy without grounding.

### 4.3 Transformer Architecture (Q23–Q34)

**23. What is a Transformer?**
A neural architecture based on attention, introduced in "Attention Is All You Need" (2017), that processes sequences in parallel without recurrence.

**24. Why did Transformers replace RNNs and LSTMs?**
They parallelize training, handle long-range dependencies better and scale well with data and compute.

**25. What are the main components of a Transformer?**
Token embeddings, positional encoding, multi-head self-attention, feed-forward layers, residual connections and layer normalization.

**26. What is the encoder–decoder structure?**
The encoder reads and represents the input. The decoder generates output while attending to the encoder output (used in translation, T5).

**27. What are encoder-only, decoder-only and encoder–decoder models?**
Encoder-only (BERT) for understanding, decoder-only (GPT, Llama, Claude) for generation, encoder–decoder (T5, BART) for sequence-to-sequence tasks.

**28. Why is positional encoding needed?**
Attention has no built-in sense of order, so position information must be added (sinusoidal, learned, RoPE or ALiBi).

**29. What is the role of the feed-forward network?**
It applies a position-wise non-linear transformation to each token's representation, adding capacity after attention.

**30. What are residual connections and layer normalization for?**
Residuals ease gradient flow in deep networks. Layer norm stabilizes training.

**31. What is causal (masked) attention?**
Each token can attend only to earlier tokens, so the model cannot see the future when predicting the next token.

**32. What is autoregressive generation?**
Generating one token at a time, feeding each new token back as input for the next prediction.

**33. What is the KV cache?**
Storing the key and value tensors of previous tokens during generation so they are not recomputed, speeding up inference at the cost of memory.

**34. What is Mixture of Experts (MoE)?**
A design where a router sends each token to a few specialized expert networks, increasing capacity without activating all parameters.

### 4.4 Attention (Q35–Q42)

**35. What is the attention mechanism?**
A way for each token to weigh the relevance of other tokens and combine their information accordingly.

**36. What are Query, Key and Value?**
Query: what a token is looking for. Key: what a token offers. Value: the information it carries. Attention compares queries with keys to weight the values.

**37. Write the scaled dot-product attention formula.**
Attention(Q,K,V) = softmax(QKᵀ / √d_k) · V

**38. Why divide by √d_k?**
To stop dot products growing large with dimension, which would push softmax into saturated regions with tiny gradients.

**39. What is self-attention?**
Attention where queries, keys and values all come from the same sequence, so tokens attend to each other.

**40. What is multi-head attention?**
Running several attention operations in parallel with different learned projections, so the model captures different relationships at once.

**41. What is the complexity problem with attention?**
Compute and memory grow quadratically with sequence length, which limits long contexts.

**42. How are long contexts made efficient?**
FlashAttention (memory-efficient kernels), sparse or sliding-window attention, grouped-query or multi-query attention and KV-cache optimizations.

### 4.5 Best Practices (Q43–Q50)

**43. What makes a good prompt?**
Clear task, relevant context, specific format, examples where helpful and constraints. Be explicit rather than vague.

**44. What is few-shot vs zero-shot prompting?**
Zero-shot gives only instructions. Few-shot includes examples to show the desired pattern.

**45. What is chain-of-thought prompting?**
Asking the model to reason step by step, which can improve results on multi-step problems.

**46. How do you reduce hallucinations?**
Ground answers with RAG, instruct the model to say when it does not know, require citations, lower temperature, validate outputs and keep a human in the loop for important cases.

**47. How do you evaluate LLM applications?**
Build test sets with expected outputs, use automated metrics, LLM-as-judge with care, human review and track regressions when prompts or models change.

**48. What are key security risks?**
Prompt injection, data leakage, insecure output handling, excessive tool permissions and training-data poisoning.

**49. What are good practices for privacy and safety?**
Avoid sending sensitive data to external services without approval, mask PII, apply least-privilege access, add content filters, log and audit, and follow regulation.

**50. How do you control cost and latency?**
Choose the smallest model that meets quality needs, cache results, shorten prompts, batch requests, stream output and use RAG instead of long contexts.

---

## 5. LLM Models: GPT, BERT, Claude, Llama, Copilot and Codeium

### 5.1 GPT (Q1–Q10)

**1. What does GPT stand for?**
Generative Pre-trained Transformer.

**2. Who developed GPT?**
OpenAI.

**3. What type of architecture does GPT use?**
A decoder-only Transformer trained autoregressively to predict the next token.

**4. How did GPT evolve?**
From GPT-1 (2018) to GPT-2, GPT-3 (175B parameters, in-context learning), GPT-4 (multimodal, stronger reasoning) and later models and reasoning variants.

**5. What is in-context learning?**
The model learns a task from examples or instructions in the prompt, without weight updates. GPT-3 popularized this.

**6. What is ChatGPT and how does it relate to GPT?**
ChatGPT is a chat product built on GPT models fine-tuned with instructions and RLHF.

**7. What are GPT's strengths?**
Strong general text generation, reasoning, coding, summarization and a broad ecosystem (API, plugins, function calling).

**8. What are GPT's limitations?**
Hallucinations, knowledge cut-off, context limits, cost and a closed model (weights not public).

**9. How do you access GPT models in applications?**
Through the OpenAI API (and cloud providers such as Azure OpenAI), using chat or responses endpoints with tools and structured outputs.

**10. What does fine-tuning a GPT model involve?**
Preparing example prompt–response pairs, training through the provider's fine-tuning service and evaluating against a held-out set.

### 5.2 BERT (Q11–Q20)

**11. What does BERT stand for?**
Bidirectional Encoder Representations from Transformers.

**12. Who developed BERT?**
Google (2018).

**13. What architecture does BERT use?**
An encoder-only Transformer.

**14. What does "bidirectional" mean in BERT?**
It uses context from both left and right of a word at the same time, unlike left-to-right GPT.

**15. What are BERT's pre-training objectives?**
Masked Language Modeling (predict hidden tokens) and Next Sentence Prediction (later found less useful and dropped in variants).

**16. What tasks is BERT good for?**
Classification, named entity recognition, question answering, semantic search and sentence embeddings.

**17. Can BERT generate text?**
Not naturally. It is built for understanding, not autoregressive generation.

**18. What does [CLS] do in BERT?**
A special token whose final representation summarizes the sequence and is used for classification.

**19. Name BERT variants.**
RoBERTa (better training), DistilBERT (smaller, faster), ALBERT (parameter sharing) and domain models such as BioBERT and FinBERT.

**20. BERT vs GPT?**
BERT is encoder-only, bidirectional and suited to understanding tasks. GPT is decoder-only, left-to-right and suited to generation.

### 5.3 Claude (Q21–Q30)

**21. What is Claude?**
A family of LLMs built by Anthropic, available through the Claude apps, the API and cloud platforms.

**22. What is Anthropic's approach to safety?**
It emphasizes Constitutional AI, in which the model is trained to follow a set of written principles, plus red-teaming and safety research.

**23. What is Constitutional AI?**
A training method where the model critiques and revises its own outputs against a written "constitution," and AI feedback supplements human feedback.

**24. What are Claude's model tiers?**
Anthropic has released models in different size and capability tiers (for example Haiku, Sonnet and Opus) trading speed and cost against capability. Check current documentation for the latest models.

**25. What are Claude's notable strengths?**
Long-context handling, writing and analysis, coding and agentic tasks, and careful instruction following.

**26. What is Claude Code?**
Anthropic's agentic coding tool that works in the terminal, IDEs and desktop or web apps to read code, edit files and run commands.

**27. What is the Model Context Protocol (MCP)?**
An open protocol introduced by Anthropic for connecting AI applications to external tools and data sources in a standard way.

**28. How do you use Claude in an application?**
Through the Anthropic Messages API, with system prompts, tool use, streaming, prompt caching and files or images as input.

**29. What is prompt caching?**
Reusing a repeated prompt prefix across requests to reduce latency and cost.

**30. How does Claude compare with other LLMs?**
Capabilities overlap with GPT and Llama. Choice depends on task quality, cost, latency, context size, safety needs, data policy and ecosystem, so evaluate on your own tasks.

### 5.4 Llama (Q31–Q40)

**31. What is Llama?**
A family of LLMs from Meta, released with openly available weights under Meta's license.

**32. Is Llama open source?**
Its weights are openly downloadable, but the license has conditions and restrictions, so it is usually called "open-weight" rather than fully open source.

**33. What architecture does Llama use?**
A decoder-only Transformer with refinements such as RMSNorm, SwiGLU activations and rotary positional embeddings (RoPE), plus grouped-query attention in later versions.

**34. Why is Llama popular?**
It can be run locally or self-hosted, fine-tuned, kept private and customized, and it has a large community and tooling ecosystem.

**35. What sizes does Llama come in?**
Multiple sizes, from small models for edge devices to very large ones, which lets teams trade quality against cost.

**36. How can you run Llama locally?**
With tools such as Ollama, llama.cpp or LM Studio, often using quantized weights to fit consumer hardware.

**37. What is quantization?**
Storing weights in lower precision (e.g. 8-bit or 4-bit) to cut memory and speed up inference with a small quality loss.

**38. What is LoRA / QLoRA?**
Parameter-efficient fine-tuning that trains small low-rank adapter matrices instead of all weights. QLoRA does so on a quantized base model.

**39. When choose Llama over a hosted API?**
When data privacy, cost at scale, offline use, customization or control over the model matter most.

**40. What are the downsides of self-hosting Llama?**
You must manage infrastructure, GPUs, scaling, updates, safety filtering and evaluation yourself.

### 5.5 Copilot (Q41–Q46)

**41. What is GitHub Copilot?**
An AI coding assistant from GitHub (Microsoft) that gives code completions, chat, and agent-style help inside IDEs.

**42. What models power GitHub Copilot?**
It originally used OpenAI Codex and now offers multiple models from several providers. The available models change, so check current documentation.

**43. What is Microsoft Copilot?**
A family of AI assistants from Microsoft, including Microsoft 365 Copilot (inside Word, Excel, Outlook, Teams) and the Copilot app. Do not confuse it with GitHub Copilot.

**44. What are the main features of GitHub Copilot?**
Inline completions, Copilot Chat, code explanation, test generation, pull request summaries and agent modes for multi-file tasks.

**45. How should developers use Copilot safely?**
Review every suggestion, run tests, watch for insecure code and licensing issues, avoid exposing secrets and follow organization policy.

**46. How does Copilot understand the project?**
It uses context such as the open file, nearby code, other open files and (in chat modes) the repository or workspace you point it to.

### 5.6 Codeium (Q47–Q50)

**47. What is Codeium?**
An AI coding assistant offering autocomplete, chat and search across many languages and IDEs. It was known for a free individual tier.

**48. What happened to Codeium's branding?**
The company rebranded around the Windsurf editor, and the Codeium extensions were later renamed Windsurf plugins. Check current status before relying on the name.

**49. What is Windsurf?**
An AI-native IDE from the same company that combines editor, chat and an agent ("Cascade") able to work across a codebase.

**50. How do you compare coding assistants such as Copilot, Codeium and Claude Code?**
Compare suggestion quality, supported languages and IDEs, context awareness across the codebase, agent capabilities, privacy and data retention, enterprise controls, model choice and price, by trialling them on your own codebase.

---

## Tips for Using This Question Bank

- Answers are short revision notes. In an interview, add a concrete example from your own project.
- Model names, versions, features and pricing change quickly. Verify current details for GPT, Claude, Llama, Copilot and Codeium/Windsurf before an interview.
