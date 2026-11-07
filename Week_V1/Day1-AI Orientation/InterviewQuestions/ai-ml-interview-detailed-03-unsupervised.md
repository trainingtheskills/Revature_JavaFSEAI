# Unsupervised Learning: Detailed Interview Answers with Examples

**50 questions, each with an explanation, a concrete example and an interview tip where useful.**

## Contents

- [A. Fundamentals (Q1–Q4)](#a-fundamentals-q1q4)
- [B. K-Means (Q5–Q10)](#b-k-means-q5q10)
- [C. Hierarchical, DBSCAN and GMM (Q11–Q18)](#c-hierarchical-dbscan-and-gmm-q11q18)
- [D. Practical Clustering (Q19–Q23)](#d-practical-clustering-q19q23)
- [E. Dimensionality Reduction (Q24–Q32)](#e-dimensionality-reduction-q24q32)
- [F. Anomaly Detection (Q33–Q38)](#f-anomaly-detection-q33q38)
- [G. Association Rules and Other Applications (Q39–Q46)](#g-association-rules-and-other-applications-q39q46)
- [H. Challenges and Case Questions (Q47–Q50)](#h-challenges-and-case-questions-q47q50)

All Python snippets use `scikit-learn` and assume `import numpy as np` and the relevant imports shown. Numbers in examples are small and made up for illustration.

---

## A. Fundamentals (Q1–Q4)

### 1. What is unsupervised learning?

**Explanation:** Unsupervised learning is a type of machine learning that works on data with **no labels**, meaning there is no "right answer" column for the model to predict. Instead of learning a mapping from input to output, the algorithm looks for hidden structure on its own: groups of similar records, unusual records, simpler representations or items that occur together. Because there is no ground truth, the results are judged by how useful and coherent they are rather than by a simple accuracy number. It is common in exploration, where the business does not yet know what patterns exist.

**Example:** A retailer has a table of customers with `annual_income` and `spending_score` but no column saying which "type" of customer each one is. An unsupervised algorithm can discover, for instance, that there are a low-income low-spender group and a high-income high-spender group.

**Key point:** Ask "is there a column giving the correct answer?" If not, it is unsupervised.

### 2. What are the main types of unsupervised tasks?

**Explanation:** The main types are:

- **Clustering:** group similar items (customer segments).
- **Dimensionality reduction:** compress many features into fewer ones (PCA, t-SNE, UMAP).
- **Anomaly detection:** find items that do not fit the normal pattern (fraud, faulty machines).
- **Association rule mining:** find items that frequently occur together (market basket analysis).
- **Density estimation:** model the probability distribution of the data (e.g. Gaussian mixtures).

Each answers a different business question: "Who is similar?", "How can I simplify or visualize this?", "What is unusual?" and "What goes together?".

**Example:** A supermarket uses clustering to segment shoppers, PCA to visualize them, anomaly detection to spot suspicious returns, and association rules to place bread near butter.

**Interview tip:** Name all four main types, and give one business example for each.

### 3. What is clustering?

**Explanation:** Clustering assigns data points to groups (clusters) so that points within a group are more similar to each other than to points in other groups. "Similar" is defined by a distance or similarity measure, such as Euclidean distance over scaled features. There is no predefined list of groups, so the algorithm decides the grouping from the data alone. After clustering, analysts inspect each group and give it a business meaning.

**Example:** Customers described by (income, spending score): (30k, 20), (35k, 25), (80k, 90), (90k, 85). Clustering puts the first two together and the last two together.

```python
from sklearn.cluster import KMeans
import numpy as np
X = np.array([[30,20],[35,25],[80,90],[90,85]])
print(KMeans(n_clusters=2, n_init=10, random_state=0).fit_predict(X))  # e.g. [0 0 1 1]
```

**Key point:** Cluster numbers (0, 1) are arbitrary. They have no meaning until you profile them.

### 4. Give business examples of clustering.

**Explanation:** Clustering is used wherever a business wants to treat different groups differently without knowing the groups in advance. Typical uses are customer segmentation for marketing, grouping similar servers for capacity planning, organizing documents or support tickets by topic, grouping employees by profile for training plans, and grouping products by sales behaviour. The value comes from acting differently on each cluster.

**Example:**

| Domain | Clustered items | Action per cluster |
|---|---|---|
| Retail | Customers by income and spend | Premium offers for high spenders, discounts for low spenders |
| IT | Servers by CPU, memory, traffic | Move load from heavily loaded to lightly loaded servers |
| HR | Employees by experience, salary, training | Tailor training programmes |
| Support | Tickets by text content | Route to the right team |

**Interview tip:** Always connect the cluster to a business action. Clusters without an action are just charts.

---

## B. K-Means (Q5–Q10)

### 5. How does K-Means work?

**Explanation:** K-Means splits the data into *k* clusters in four repeated steps:

1. Choose *k* and place *k* initial centroids (cluster centres).
2. Assign each point to its nearest centroid.
3. Recompute each centroid as the mean of the points assigned to it.
4. Repeat steps 2–3 until assignments stop changing or a maximum number of iterations is reached.

It minimizes the **within-cluster sum of squared distances** (inertia). It always converges, but possibly to a local optimum, which is why it is run several times from different starts.

**Example:** Points on one axis: 1, 2, 3 and 10, 11, 12 with k = 2. Start centroids at 1 and 12. Points 1, 2, 3 go to centroid 1 and 10, 11, 12 to centroid 12. New centroids: 2 and 11. Assignments no longer change, so it stops with clusters {1,2,3} and {10,11,12}.

```python
from sklearn.cluster import KMeans
km = KMeans(n_clusters=2, n_init=10, random_state=42).fit(X)
print(km.labels_, km.cluster_centers_, km.inertia_)
```

### 6. How do you choose k in K-Means?

**Explanation:** The number of clusters is a decision, not something K-Means finds itself. Common aids are the **elbow method** (plot inertia against k and look for the bend), the **silhouette score** (pick the k with the highest average silhouette), the **gap statistic**, and **business knowledge** (e.g. the marketing team can only run four campaigns, so k = 4). In practice, combine statistical evidence with what the business can act on.

**Example:** Silhouette scores for k = 2, 3, 4, 5 are 0.62, 0.55, 0.41, 0.38. The best statistical choice is k = 2, but if the business needs three tiers (Bronze, Silver, Gold), k = 3 (0.55) is still acceptable.

```python
from sklearn.metrics import silhouette_score
for k in range(2, 7):
    labels = KMeans(n_clusters=k, n_init=10, random_state=0).fit_predict(X)
    print(k, silhouette_score(X, labels))
```

### 7. What are the limitations of K-Means?

**Explanation:** K-Means has several weaknesses:

- You must choose k in advance.
- Results depend on the initial centroids (local optima).
- It is sensitive to outliers because the mean is pulled by extreme values.
- It assumes clusters are roughly spherical and similar in size, so it fails on elongated or ring-shaped groups.
- It needs numeric features on a comparable scale and does not handle categorical data directly.
- It forces every point into a cluster, even noise.

**Example:** Two concentric rings of points are obviously two groups to the eye, but K-Means will cut them into left and right halves. DBSCAN handles this correctly.

**Interview tip:** Mention a limitation and the alternative that fixes it (outliers → DBSCAN, soft membership → GMM).

### 8. What is K-Means++?

**Explanation:** K-Means++ is a smarter way to choose the initial centroids. The first centroid is chosen at random, and each next centroid is chosen with probability proportional to its squared distance from the nearest centroid already chosen. This spreads the starting centroids apart. It usually gives better final clusters, fewer iterations and more stable results than purely random starts. In scikit-learn it is the default (`init="k-means++"`).

**Example:** With random initialization, two starting centroids might both land inside the same dense group, leaving another group with no centroid and giving a poor result. K-Means++ makes that unlikely because a point near an existing centroid has a low chance of being picked.

### 9. What is the elbow method?

**Explanation:** The elbow method runs K-Means for several values of k and plots **inertia** (within-cluster sum of squares) on the y-axis against k. Inertia always falls as k grows, because more clusters fit the data more tightly. The "elbow" is the point after which adding clusters gives only small improvements. That bend suggests a reasonable k. The elbow can be vague, so it is usually confirmed with silhouette scores.

**Example:**

| k | Inertia |
|---|---|
| 1 | 1000 |
| 2 | 420 |
| 3 | 180 |
| 4 | 160 |
| 5 | 148 |

The big drops end at k = 3, so the elbow is at 3.

```python
inertias = [KMeans(n_clusters=k, n_init=10, random_state=0).fit(X).inertia_ for k in range(1, 8)]
```

### 10. What is the silhouette score?

**Explanation:** For each point, the silhouette compares *a*, the average distance to other points in its own cluster, with *b*, the average distance to points in the nearest other cluster. It is `(b − a) / max(a, b)`, so it ranges from −1 to 1. Near 1 means the point is well inside its cluster. Near 0 means it is on a boundary. Negative means it may be in the wrong cluster. The average over all points gives a score for the whole clustering, and higher is better.

**Example:** A point has a = 2 and b = 8. Silhouette = (8 − 2) / 8 = 0.75, so it sits well in its cluster. Another has a = 5 and b = 5.5: (5.5 − 5) / 5.5 ≈ 0.09, so it is on the border.

```python
from sklearn.metrics import silhouette_score
silhouette_score(X, labels)
```

**Interview tip:** Silhouette needs no labels, so it works for unsupervised evaluation.

---

## C. Hierarchical, DBSCAN and GMM (Q11–Q18)

### 11. What is hierarchical clustering?

**Explanation:** Hierarchical clustering builds a nested tree of clusters. **Agglomerative** (bottom-up) starts with every point as its own cluster and repeatedly merges the two closest clusters until one remains. **Divisive** (top-down) starts with one cluster and keeps splitting. You do not need to fix k in advance: build the tree, then cut it at the level that gives the number of clusters you want. It is easy to interpret but becomes slow on very large datasets.

**Example:** Customers A, B, C, D where A and B are very close and C and D are very close. Step 1 merges A+B, step 2 merges C+D, and step 3 merges both groups. Cutting after step 2 gives two clusters.

```python
from sklearn.cluster import AgglomerativeClustering
AgglomerativeClustering(n_clusters=2, linkage="ward").fit_predict(X)
```

### 12. What are linkage methods?

**Explanation:** Linkage defines how distance between two clusters (not two points) is measured when deciding what to merge:

- **Single:** distance between the closest pair of points. It can chain clusters together.
- **Complete:** distance between the farthest pair. It gives compact clusters.
- **Average:** average distance over all pairs.
- **Ward:** merges the pair that least increases total within-cluster variance. It is a popular default and tends to give similar-sized clusters.

**Example:** Two clusters with nearest points 2 units apart but far-side points 10 apart: single linkage says 2 (looks close, may merge), complete linkage says 10 (looks far, stays separate).

**Interview tip:** If asked for a default, say Ward linkage with Euclidean distance on scaled data.

### 13. What is a dendrogram?

**Explanation:** A dendrogram is a tree diagram of a hierarchical clustering. The leaves are individual points, and each joining of branches shows a merge. The height of a join is the distance (or dissimilarity) at which the merge happened. To pick clusters, draw a horizontal line across the tree at a chosen height: the branches it cuts are the clusters. Long vertical gaps before a merge suggest a natural cut.

**Example:** If merges happen at heights 1, 1.2, 1.4 and then a big jump to 8, cutting just below 8 leaves two well-separated clusters.

```python
from scipy.cluster.hierarchy import linkage, dendrogram
import matplotlib.pyplot as plt
dendrogram(linkage(X, method="ward")); plt.show()
```

### 14. What is DBSCAN?

**Explanation:** DBSCAN (Density-Based Spatial Clustering of Applications with Noise) forms clusters from areas where points are densely packed and labels points in sparse areas as **noise**. It classifies points as *core* (many neighbours within a radius), *border* (near a core point) or *noise*. Clusters grow by connecting core points that are neighbours. It can find clusters of any shape and does not need k, which is why it is popular for spatial and irregular data.

**Example:** GPS pings from delivery vans: dense areas become "hotspot" clusters (depots, popular streets), while stray pings from signal errors are labeled noise (−1).

```python
from sklearn.cluster import DBSCAN
labels = DBSCAN(eps=0.5, min_samples=5).fit_predict(X)  # -1 means noise
```

### 15. What are DBSCAN's parameters?

**Explanation:** There are two main parameters:

- **eps:** the radius of the neighbourhood around a point.
- **min_samples:** the minimum number of points within eps for a point to count as a core point.

If eps is too small, most points become noise. If it is too large, separate clusters merge into one. A common way to choose eps is a k-distance plot: sort each point's distance to its k-th neighbour and look for the bend. Features must be scaled first because eps is a single distance.

**Example:** With eps = 0.3 and min_samples = 4, a point with 6 other points within 0.3 is a core point. A point with only 1 neighbour and not near any core point is noise.

**Interview tip:** A rough rule for min_samples is at least the number of features + 1, often 2 × features.

### 16. K-Means vs DBSCAN?

**Explanation:**

| Aspect | K-Means | DBSCAN |
|---|---|---|
| Need k in advance | Yes | No |
| Cluster shape | Roughly spherical | Arbitrary |
| Outliers | Forced into clusters, distort centroids | Labeled as noise |
| Varying density | Handles it moderately | Struggles |
| Main parameters | k | eps, min_samples |
| Speed on large data | Fast | Slower, depends on indexing |

K-Means suits well-separated, round clusters when you know roughly how many groups you want. DBSCAN suits irregular shapes and noisy data.

**Example:** Customer segments in a table of income and spend are often K-Means friendly. Geographic hotspots of crime on a map are better handled by DBSCAN.

### 17. What is a Gaussian Mixture Model (GMM)?

**Explanation:** A GMM assumes the data was generated by a mixture of several Gaussian (bell-shaped) distributions, each with its own mean and covariance. It learns these using the **Expectation-Maximization (EM)** algorithm: the E-step estimates how likely each point belongs to each Gaussian, and the M-step updates the Gaussians from those probabilities. Because covariances can differ, it can model elliptical and differently sized clusters, unlike K-Means. It outputs probabilities of membership, not just a label.

**Example:** A customer with a 70% chance of belonging to "occasional buyers" and 30% to "frequent buyers" can receive a blended marketing approach.

```python
from sklearn.mixture import GaussianMixture
gm = GaussianMixture(n_components=2, random_state=0).fit(X)
gm.predict(X); gm.predict_proba(X)
```

### 18. Hard vs soft clustering?

**Explanation:** In **hard clustering** each point belongs to exactly one cluster (K-Means, DBSCAN, hierarchical cutting). In **soft clustering** each point has a degree or probability of belonging to every cluster (GMM, fuzzy c-means). Soft assignments capture ambiguity: points near a border are not forced into one side. They are useful when groups overlap, such as customers who behave like two segments.

**Example:**

| Customer | Hard (K-Means) | Soft (GMM) |
|---|---|---|
| A | Cluster 1 | 0.95 C1, 0.05 C2 |
| B | Cluster 2 | 0.55 C1, 0.45 C2 (borderline) |

---

## D. Practical Clustering (Q19–Q23)

### 19. Why does scaling matter for clustering?

**Explanation:** Distance-based algorithms treat each feature's raw numeric range as its importance. A feature measured in thousands (salary) will overwhelm a feature measured in single digits (years of experience), so the clusters reflect only salary. Scaling (standardization or min-max) puts features on comparable ranges so each contributes fairly. It matters for K-Means, hierarchical, DBSCAN, KNN and PCA.

**Example:** Employee A: salary 50,000, experience 2. Employee B: salary 51,000, experience 20. The Euclidean distance is about 1,000.2, dominated by salary even though experience differs hugely. After standardizing, both differences count.

```python
from sklearn.preprocessing import StandardScaler
Xs = StandardScaler().fit_transform(X)
```

### 20. Which distance metrics are used?

**Explanation:** Common metrics are:

- **Euclidean:** straight-line distance (default for K-Means).
- **Manhattan:** sum of absolute differences, less sensitive to outliers, like walking city blocks.
- **Cosine:** angle between vectors, ignoring magnitude (text and embeddings).
- **Hamming:** share of positions that differ (binary or categorical data).
- **Gower:** handles mixed numeric and categorical features.

The choice should match the data type and what "similar" means for the business.

**Example:** Two documents with the same word proportions but one twice as long have a cosine similarity of 1 (same topic), although their Euclidean distance is large.

### 21. How do you cluster categorical data?

**Explanation:** Standard K-Means needs numbers and means, which do not exist for categories. Options include **K-Modes** (uses modes and mismatch counts), **K-Prototypes** (mixed numeric and categorical), **hierarchical clustering with Gower distance**, or converting categories to numbers by one-hot encoding or learned embeddings and then clustering. One-hot encoding inflates dimensions and can distort distances, so check the result carefully.

**Example:** Customers with `plan` (Basic/Premium), `payment` (Card/UPI) and `region`. K-Modes might find a cluster "Premium, UPI, West" as the typical profile.

**Interview tip:** Say "K-Modes or K-Prototypes, or Gower distance with hierarchical clustering".

### 22. How do you evaluate clustering without labels?

**Explanation:** With no ground truth, you use **internal metrics** and practical checks:

- **Silhouette score** (higher is better).
- **Davies–Bouldin index** (lower is better): measures cluster overlap.
- **Calinski–Harabasz index** (higher is better): ratio of between-cluster to within-cluster spread.
- **Stability:** do clusters stay similar on resampled data or different random seeds?
- **Business usefulness:** can the teams act on the clusters and do they get different outcomes?

If labels do exist for a validation sample, external metrics such as the Adjusted Rand Index can also be used.

**Example:** K-Means with k = 3 gives silhouette 0.58, and re-running on 80% subsets gives the same groupings. Marketing confirms the three groups respond differently to offers. That is strong evidence.

### 23. How do you interpret clusters?

**Explanation:** Interpretation turns numeric labels into business meaning. Steps: add the cluster label to the original (unscaled) data, compute the average or median of each feature per cluster, check cluster sizes, compare clusters with each other and with the overall average, and then name each cluster. Visualizations such as bar charts or radar charts of cluster profiles help. Check with domain experts that the names make sense.

**Example:**

| Cluster | Avg income | Avg spend score | Size | Name |
|---|---|---|---|---|
| 0 | 32k | 22 | 40% | Budget-conscious |
| 1 | 85k | 88 | 25% | Premium spenders |
| 2 | 85k | 20 | 15% | Wealthy savers |

```python
df["cluster"] = labels
df.groupby("cluster")[["income","spend_score"]].mean()
```

---

## E. Dimensionality Reduction (Q24–Q32)

### 24. What is dimensionality reduction?

**Explanation:** Dimensionality reduction converts data with many features into a version with fewer features while keeping as much useful information as possible. It can work by **selecting** a subset of features or by **transforming** features into new ones (PCA, autoencoders). It makes data easier to visualize, faster to process and often less noisy. The new features may be harder to interpret than the originals.

**Example:** A dataset with 100 correlated survey questions reduced to 5 components such as "satisfaction", "price sensitivity" and "service quality".

### 25. Why reduce dimensions?

**Explanation:** Reasons include:

- Fighting the **curse of dimensionality**: in high dimensions, data becomes sparse and distances lose meaning.
- Reducing **noise** and redundant (highly correlated) features.
- Lowering **computation and memory** cost.
- Reducing **overfitting** in supervised models.
- Enabling **visualization** in 2D or 3D.

**Example:** A model trained on 10,000 pixel features of small images may run 20 times faster after PCA to 100 components, with almost the same accuracy.

### 26. What is PCA?

**Explanation:** Principal Component Analysis finds new axes (principal components) that are **orthogonal** (uncorrelated) directions along which the data varies most. The first component captures the largest share of variance, the second the next largest, and so on. You keep only the first few and project the data onto them. It is based on the eigenvectors of the covariance matrix (or SVD) and requires scaled data.

**Example:** Height and weight of people are strongly correlated. PCA finds one direction ("overall body size") that holds most of the variation, so two features can be summarized by one component with little loss.

```python
from sklearn.decomposition import PCA
pca = PCA(n_components=2)
Z = pca.fit_transform(Xs)
print(pca.explained_variance_ratio_)
```

### 27. How do you choose the number of components in PCA?

**Explanation:** Look at **explained variance ratio**. Plot the cumulative explained variance and keep enough components to reach a target such as 90–95%. A **scree plot** (variance per component) shows where the curve flattens. For visualization, use 2 or 3. If PCA feeds a supervised model, choose the number by cross-validated performance.

**Example:** Explained variance by component: 55%, 25%, 10%, 6%, 4%. Cumulative: 55, 80, 90, 96, 100. For a 90% target, keep 3 components.

```python
PCA(n_components=0.95).fit(Xs)   # keeps enough components for 95% variance
```

### 28. What are the limitations of PCA?

**Explanation:** PCA only finds **linear** structure, so it can miss curved patterns. It is **sensitive to scale**, so features must be standardized. The components are mixtures of original features and are **hard to interpret**. It is affected by outliers, because variance is outlier-sensitive. It also assumes that high variance means important, which is not always true: a low-variance feature can be the one that separates classes.

**Example:** Data shaped like a spiral cannot be unrolled by PCA. A non-linear method such as UMAP or t-SNE does better for visualization.

### 29. What is t-SNE?

**Explanation:** t-SNE (t-distributed Stochastic Neighbour Embedding) is a non-linear method for visualizing high-dimensional data in 2D or 3D. It converts distances between points into probabilities of being neighbours and arranges points in low dimensions so that neighbouring points stay close. It is very good at revealing local clusters. Results depend on the **perplexity** parameter and random seed.

**Example:** Visualizing embeddings of handwritten digits: t-SNE shows ten clear islands, one per digit, even though the original data has hundreds of dimensions.

```python
from sklearn.manifold import TSNE
Z = TSNE(n_components=2, perplexity=30, random_state=0).fit_transform(Xs)
```

### 30. t-SNE vs PCA?

**Explanation:**

| Aspect | PCA | t-SNE |
|---|---|---|
| Type | Linear | Non-linear |
| Preserves | Global variance structure | Local neighbourhoods |
| Speed | Fast | Slow on big data |
| Deterministic | Yes | No (random start) |
| Can transform new data | Yes | Not directly |
| Distances between clusters meaningful? | Yes | No |
| Best use | Preprocessing, compression | Visual exploration |

**Interview tip:** A common practice is to apply PCA first (e.g. to 50 components) and then t-SNE for visualization.

### 31. What is UMAP?

**Explanation:** UMAP (Uniform Manifold Approximation and Projection) is a non-linear dimensionality reduction technique. Like t-SNE, it preserves local neighbourhoods, but it is usually **faster**, tends to keep more **global structure**, and can be used to transform new data and as a preprocessing step, not only for plots. Its main parameters are `n_neighbors` and `min_dist`.

**Example:** Reducing 1 million document embeddings to 2D for a map of topics takes far less time with UMAP than with t-SNE.

```python
import umap   # from the umap-learn package
Z = umap.UMAP(n_neighbors=15, min_dist=0.1, random_state=0).fit_transform(Xs)
```

### 32. What is an autoencoder?

**Explanation:** An autoencoder is a neural network with an **encoder** that compresses the input into a small **latent code** (bottleneck) and a **decoder** that reconstructs the original input from it. It is trained to minimize reconstruction error, so the latent code must retain the essential information. Unlike PCA, it can learn non-linear compressions. Uses include dimensionality reduction, denoising and anomaly detection.

**Example:** A 784-pixel image is squeezed to a 32-number code and rebuilt. The 32 numbers can be used as compact features for clustering or search.

---

## F. Anomaly Detection (Q33–Q38)

### 33. What is anomaly detection?

**Explanation:** Anomaly (outlier) detection identifies data points that differ significantly from the normal pattern. Anomalies may be errors (a faulty sensor reading) or important events (fraud, intrusions, equipment about to fail). Methods range from simple statistics (z-score, IQR) to machine learning (Isolation Forest, autoencoders). The output is usually an **anomaly score** and a threshold for flagging.

**Example:** A customer normally spends ₹500–₹2,000 per order and suddenly places a ₹95,000 order from a new country. This is flagged for review.

### 34. Why is anomaly detection often unsupervised?

**Explanation:** Anomalies are rare, so there are few labeled examples. They are also diverse and new kinds appear, so a supervised model trained on past fraud types may miss new ones. Labeling is expensive and slow. Unsupervised methods learn what "normal" looks like and flag anything that deviates, with no labels needed. When some labels exist, hybrid or semi-supervised approaches are used.

**Example:** A factory has 5 years of sensor data and only 3 recorded failures, too few to train a classifier. An autoencoder trained on normal operation flags unusual readings instead.

### 35. What is Isolation Forest?

**Explanation:** Isolation Forest builds many random trees. Each split picks a random feature and a random split value. Anomalies are few and different, so they are **isolated in fewer splits** (shorter path from the root), while normal points need more splits. The average path length across trees converts into an anomaly score. It is fast, scales well and needs no distance calculations.

**Example:** In transaction amounts mostly between 100 and 5,000, a single 90,000 transaction is separated after one or two random splits, so it gets a high anomaly score.

```python
from sklearn.ensemble import IsolationForest
iso = IsolationForest(contamination=0.01, random_state=0).fit(X)
iso.predict(X)         # -1 = anomaly, 1 = normal
iso.decision_function(X)
```

### 36. What is One-Class SVM?

**Explanation:** One-Class SVM is trained mostly on **normal** data. It learns a boundary (in a kernel-transformed space) that encloses most normal points. New points outside the boundary are flagged as anomalies. The parameter `nu` roughly controls the share of points allowed outside. It needs scaled data and can be slow on very large datasets, and kernel and parameter choices matter.

**Example:** Train on normal network traffic features. A burst of packets with unusual sizes and ports falls outside the learned region and is flagged as a possible intrusion.

```python
from sklearn.svm import OneClassSVM
oc = OneClassSVM(kernel="rbf", nu=0.05, gamma="scale").fit(X_normal)
oc.predict(X_new)
```

### 37. How do autoencoders detect anomalies?

**Explanation:** Train an autoencoder only on normal data. It learns to reconstruct normal patterns well, so reconstruction error is low. When an anomalous input arrives, which the network has never learned to rebuild, the reconstruction error is **high**. Set a threshold on the error (e.g. the 99th percentile on normal validation data) and flag anything above it. This works well for high-dimensional data such as sensor signals or images.

**Example:** A machine vibration autoencoder has typical error 0.02. A bearing about to fail gives a pattern that rebuilds with error 0.30, which exceeds the 0.08 threshold, so maintenance is alerted.

### 38. What is Local Outlier Factor (LOF)?

**Explanation:** LOF compares the **local density** of a point with the densities of its k nearest neighbours. A normal point lives in a region as dense as its neighbours' (LOF ≈ 1). A point in a much sparser area than its neighbours has LOF well above 1 and is an outlier. Because it is local, it can detect outliers in datasets with clusters of different densities, where a global distance threshold would fail.

**Example:** In a city, a house priced far above its own neighbourhood is flagged, even if its price is normal for a richer district elsewhere.

```python
from sklearn.neighbors import LocalOutlierFactor
lof = LocalOutlierFactor(n_neighbors=20, contamination=0.02)
labels = lof.fit_predict(X)   # -1 = outlier
```

---

## G. Association Rules and Other Applications (Q39–Q46)

### 39. What is association rule mining?

**Explanation:** Association rule mining discovers rules of the form "if A, then B" from transaction data, where A and B are sets of items that frequently occur together. The classic use is **market basket analysis**. Rules are discovered without labels and are judged by support, confidence and lift. They show co-occurrence, not causation.

**Example:** From 1,000 baskets, a rule `{bread} → {butter}` shows that people who buy bread often buy butter, so the store places them close together or bundles them.

### 40. Define support, confidence and lift.

**Explanation:**

- **Support(A→B)** = share of all transactions containing both A and B.
- **Confidence(A→B)** = P(B | A) = transactions with A and B ÷ transactions with A.
- **Lift(A→B)** = confidence ÷ support(B). Lift above 1 means A and B occur together more than by chance, 1 means independent, below 1 means they avoid each other.

**Example:** 1,000 transactions. Bread in 200, butter in 100, both in 80.

- Support = 80/1000 = 0.08
- Confidence(bread→butter) = 80/200 = 0.40
- Support(butter) = 0.10, so Lift = 0.40 / 0.10 = **4.0** (a strong association)

**Interview tip:** High confidence alone can be misleading if B is just very common. Always check lift.

### 41. What are Apriori and FP-Growth?

**Explanation:** Both find **frequent itemsets**. **Apriori** works level by level: it finds frequent single items, builds candidate pairs only from frequent items, then triples, and so on. It relies on the fact that any subset of a frequent itemset must also be frequent, which prunes many candidates. It can be slow because it scans the data repeatedly. **FP-Growth** compresses the transactions into an FP-tree and mines it without generating candidates, which is usually much faster on large data.

**Example:** If {milk} is infrequent, Apriori never considers {milk, bread} or {milk, eggs}, saving work.

```python
from mlxtend.frequent_patterns import apriori, association_rules
freq = apriori(basket_df, min_support=0.05, use_colnames=True)
rules = association_rules(freq, metric="lift", min_threshold=1.2)
```

### 42. What is customer segmentation and how is it done?

**Explanation:** Customer segmentation divides customers into groups that behave or need similarly so the business can treat each group appropriately. The usual process: gather data (purchases, demographics, engagement), engineer features (e.g. RFM), scale them, run a clustering algorithm such as K-Means, choose k, profile and name the segments, and design actions for each. Segments should be reviewed regularly because behaviour changes.

**Example:** Segments might be "Loyal high spenders" (VIP service), "At-risk previously active customers" (win-back offers) and "New customers" (onboarding emails).

### 43. What is RFM analysis?

**Explanation:** RFM scores customers by **Recency** (how recently they purchased), **Frequency** (how often) and **Monetary value** (how much they spend). Together they summarize customer value in three simple features, which are then scored (e.g. quintiles 1–5) or clustered. Customers who bought recently, often and with high spend are the best.

**Example:** Customer X: last bought 5 days ago, 24 orders, ₹60,000 total → R=5, F=5, M=5 (champion). Customer Y: last bought 300 days ago, 2 orders, ₹1,500 → R=1, F=1, M=1 (lost).

```python
rfm = df.groupby("customer_id").agg(
    recency=("days_since_order","min"),
    frequency=("order_id","count"),
    monetary=("amount","sum"))
```

### 44. What is topic modeling?

**Explanation:** Topic modeling discovers hidden themes in a collection of documents without labels. **LDA** (Latent Dirichlet Allocation) assumes each document is a mix of topics and each topic is a distribution over words. Newer approaches cluster document embeddings (for example BERTopic-style pipelines). Each topic is described by its top words, and a human names it.

**Example:** On 10,000 support tickets, a topic with top words "refund, payment, charge, card" is named "Billing issues", and another with "login, password, reset" is named "Account access".

```python
from sklearn.decomposition import LatentDirichletAllocation
from sklearn.feature_extraction.text import CountVectorizer
X = CountVectorizer(stop_words="english").fit_transform(docs)
lda = LatentDirichletAllocation(n_components=5, random_state=0).fit(X)
```

### 45. What is a recommendation system's link to unsupervised learning?

**Explanation:** Many recommenders learn from user–item interactions (clicks, ratings, purchases) with no explicit "correct recommendation" label. **Collaborative filtering** finds users or items with similar behaviour (a neighbourhood or clustering idea), and **matrix factorization** discovers latent factors (hidden tastes) behind the interactions, closely related to dimensionality reduction. Embeddings of users and items are often learned this way.

**Example:** A user–movie matrix is factorized into factors like "likes action" and "likes romance". A new user who rated three action films is placed near other action fans and gets their favourites recommended.

**Key point:** Modern recommenders mix unsupervised, supervised and self-supervised techniques.

### 46. What is self-supervised learning and how does it relate?

**Explanation:** Self-supervised learning creates labels **from the data itself**, such as hiding part of the input and asking the model to predict it. It needs no human labels, so it uses large unlabeled data, but the training is like supervised learning. Examples: predicting the next word in a sentence, filling masked words, or predicting a missing image patch. Models trained this way learn rich representations that can be fine-tuned for specific tasks, which is how large language models are pre-trained.

**Example:** From "The bank approved the ___", the model predicts "loan". The sentence supplies both the input and the answer, so no manual labeling is needed.

---

## H. Challenges and Case Questions (Q47–Q50)

### 47. What are the main challenges of unsupervised learning?

**Explanation:** The main challenges are:

- **No ground truth:** it is hard to say whether a result is "right".
- **Choosing parameters** such as the number of clusters or DBSCAN's eps.
- **Sensitivity to scaling**, outliers and feature selection.
- **Subjective interpretation:** different analysts may name clusters differently.
- **Stability:** results can change with random seeds or small data changes.
- **Curse of dimensionality** in high-dimensional data.

**Example:** Two analysts run K-Means with k = 3 and k = 5 on the same customers and both can defend their results. The deciding factor is which segmentation the business can use and which shows different outcomes.

**Interview tip:** Show that you validate with multiple metrics plus business review.

### 48. How do you handle outliers in clustering?

**Explanation:** First decide whether the outliers are **errors** or **real, important cases**. Options are: remove clear errors, cap extreme values (winsorize) or apply a log transform, use robust algorithms (DBSCAN labels them as noise; K-Medoids uses actual points as centres), or detect outliers separately with Isolation Forest or LOF and analyse them as their own group. Always check how results change with and without them.

**Example:** One customer with ₹10 crore annual spend pulls a K-Means centroid far from the other high spenders. Treating that customer separately (or using DBSCAN) gives cleaner segments for everyone else.

### 49. Can you combine supervised and unsupervised learning?

**Explanation:** Yes, and it is common:

- Use **cluster labels or distances to centroids** as extra features in a supervised model.
- Use **PCA or autoencoder** outputs to reduce features before a classifier.
- **Cluster first, then train one model per cluster** when groups behave differently.
- Use **self-supervised pre-training** on unlabeled data, then fine-tune on a small labeled set.
- **Semi-supervised learning**: use clustering to propagate labels to unlabeled points.

**Example:** For churn prediction, first segment customers with K-Means, then add `segment_id` as a feature in a gradient boosting churn model. The model can then learn segment-specific churn behaviour.

```python
df["segment"] = KMeans(n_clusters=4, n_init=10, random_state=0).fit_predict(Xs)
# then train a classifier using df[features + ["segment"]]
```

### 50. Given unlabeled customer data, how would you proceed?

**Explanation:** A sensible step-by-step plan:

1. **Clarify the goal**: what decision will the segments support?
2. **Explore and clean** the data: missing values, duplicates, outliers.
3. **Engineer features**, e.g. RFM, tenure, product mix.
4. **Scale** numeric features and encode categoricals sensibly.
5. **Try algorithms**: K-Means first, then hierarchical or GMM for comparison.
6. **Choose k** using elbow and silhouette, and check stability.
7. **Profile and name** the clusters from average feature values.
8. **Validate** with business stakeholders.
9. **Act** (targeted offers) and **measure** the results with A/B tests.
10. **Monitor and refresh** the segments periodically.

**Example (end-to-end sketch):**

```python
from sklearn.preprocessing import StandardScaler
from sklearn.cluster import KMeans
from sklearn.metrics import silhouette_score

Xs = StandardScaler().fit_transform(rfm[["recency","frequency","monetary"]])
best_k = max(range(2, 8),
             key=lambda k: silhouette_score(
                 Xs, KMeans(n_clusters=k, n_init=10, random_state=0).fit_predict(Xs)))
rfm["segment"] = KMeans(n_clusters=best_k, n_init=10, random_state=0).fit_predict(Xs)
print(rfm.groupby("segment").mean())
```

**Interview tip:** Finish by explaining the business action and how you would measure success, because that is what interviewers listen for.
