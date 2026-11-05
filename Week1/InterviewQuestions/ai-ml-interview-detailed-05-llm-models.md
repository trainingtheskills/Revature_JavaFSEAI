# 5. LLM Models: GPT, BERT, Claude, Llama, Copilot and Codeium — Detailed Answers with Examples

> Model names, versions, features and pricing change quickly. Where a detail is likely to change, this guide says **check current docs**. Code snippets are illustrative; model names in them are placeholders you should replace with a current model from the provider's documentation.

---

## 5.1 GPT (Q1–Q10)

**1. What does GPT stand for?**

GPT stands for **Generative Pre-trained Transformer**. Each word carries meaning:

- **Generative:** the model produces new text (and in newer versions other outputs) rather than only labeling or classifying input.
- **Pre-trained:** before anyone uses it, it is trained on a very large collection of text to learn general language patterns. Later it can be adapted to specific tasks.
- **Transformer:** the neural network architecture it is built on, which uses attention to relate every token in the input to the others.

*Example:* If you type "The capital of France is", a GPT model has learned from its training that "Paris" is a very likely continuation, so it generates "Paris".

**Key point:** Interviewers often ask for the expansion first and then follow up with "why Transformer?". Be ready to say it uses attention instead of recurrence, so it trains in parallel.

---

**2. Who developed GPT?**

GPT was developed by **OpenAI**, an AI research company. The first GPT paper was published in 2018. OpenAI later released GPT-2, GPT-3, GPT-4 and further models, as well as ChatGPT, the chat product built on them. The underlying Transformer architecture, however, came from Google researchers in the 2017 paper "Attention Is All You Need". So OpenAI did not invent the Transformer; it showed how powerful a generative pre-trained decoder-only Transformer could become at scale.

*Example:* A common interview trap is "Did OpenAI invent the Transformer?" The correct answer: no, Google researchers did; OpenAI popularized the GPT line built on it.

**Interview tip:** Separate the *architecture* (Google, 2017), the *GPT model family* (OpenAI) and the *product* (ChatGPT).

---

**3. What type of architecture does GPT use?**

GPT uses a **decoder-only Transformer**. It reads tokens from left to right and uses **causal (masked) attention**, so each token can only look at earlier tokens, never future ones. It is trained with a simple objective: predict the next token given all previous tokens. At generation time it repeats this one token at a time, feeding each new token back in. This is called **autoregressive generation**.

*Example:* Given "I love eating", the model computes probabilities for the next token:

```
"pizza" 0.22   "food" 0.15   "fruit" 0.08   ...
```

It picks (or samples) one, say "pizza", appends it, and predicts the next token after "I love eating pizza".

**Key point:** Decoder-only means there is no separate encoder. This differs from BERT (encoder-only) and T5 (encoder–decoder).

---

**4. How did GPT evolve?**

GPT grew over several generations, mainly by increasing scale and improving training:

- **GPT-1 (2018):** showed that pre-training on unlabeled text followed by fine-tuning works well.
- **GPT-2 (2019):** a larger model that could generate surprisingly coherent text with little task-specific training.
- **GPT-3 (2020):** about 175 billion parameters; popularized **in-context learning** (few-shot prompting).
- **GPT-4 and later:** stronger reasoning, multimodal input (such as images), and alignment improvements using human feedback; newer models also include reasoning-focused variants.

The exact lineup and capabilities of the newest models change often, so **check current docs**.

*Example:* With GPT-2 you typically had to fine-tune for a task. With GPT-3 you could just show two examples in the prompt and the model followed the pattern.

**Interview tip:** Mention the trend rather than memorizing every release: bigger models, more data, better alignment, more modalities.

---

**5. What is in-context learning?**

In-context learning means the model **learns a task from instructions or examples placed inside the prompt**, without any change to its weights. The examples act as a temporary "teacher". This was a major finding with GPT-3. It works because the model has seen many patterns during pre-training and can recognize which pattern the examples follow. When the conversation ends, nothing is permanently learned.

*Example (few-shot sentiment prompt):*

```
Review: "Loved the battery life!"  -> Positive
Review: "Screen broke in a week."   -> Negative
Review: "Fast delivery and great price."  ->
```

The model completes with "Positive" even though it was never fine-tuned for sentiment.

**Key point:** In-context learning is not training. Weights stay the same, and the "knowledge" disappears when the context is gone.

---

**6. What is ChatGPT and how does it relate to GPT?**

**GPT** is the underlying family of language models. **ChatGPT** is a *product* (a chat application) built on top of GPT models. The base model is first pre-trained on text, then fine-tuned on instruction-following examples, and then aligned using human preferences, for example with RLHF. That extra training makes it respond conversationally and follow instructions rather than merely continue text. The product adds features such as conversation history, file upload and tools; the exact feature set changes, so **check current docs**.

*Example:* A raw base model given "How do I reset my password?" might continue with more questions in the same style. ChatGPT answers with step-by-step instructions because it was tuned to act as an assistant.

**Interview tip:** Say "GPT is the model, ChatGPT is the application" to show you understand the difference.

---

**7. What are GPT's strengths?**

GPT models are strong general-purpose tools. Their typical strengths are:

- **Text generation and rewriting** in many styles and languages.
- **Reasoning and problem solving** on many everyday tasks.
- **Coding help:** writing, explaining and debugging code.
- **Summarization and extraction** from long text.
- **Ecosystem:** an API, function calling, structured outputs and many third-party integrations.

How well a particular GPT model performs on a task depends on the version and the task, so test on your own data rather than relying on general claims.

*Example:* A support team can paste a long ticket thread and ask: "Summarize in three bullets and list the customer's open questions." The model returns a structured summary in seconds.

**Key point:** Strong in general, but not guaranteed right. Always evaluate on your own use case.

---

**8. What are GPT's limitations?**

Even strong GPT models have well-known limits:

- **Hallucinations:** confident but false statements.
- **Knowledge cut-off:** it does not know events after its training data unless given tools or context.
- **Context window limits:** only a finite number of tokens can be processed at once.
- **Cost and latency:** larger models cost more and respond slower.
- **Closed model:** the weights are not public, so you cannot self-host or inspect them.
- **Inconsistency:** the same prompt can yield different answers at non-zero temperature.

*Example:* Asked "What is the latest version of library X?", the model may confidently name an older version because its training data ended earlier. Connecting it to a search tool or providing the docs fixes this.

**Interview tip:** For every limitation, give a mitigation (RAG for knowledge, tools for calculation, human review for high-stakes decisions).

---

**9. How do you access GPT models in applications?**

You normally call the **OpenAI API** over HTTPS using an API key, either directly or through an SDK. Some cloud providers (for example Azure OpenAI) also host these models with their own access controls. You send a list of messages and parameters (model name, temperature, max tokens, optional tools) and receive the generated reply. Endpoint names and SDK syntax evolve, so **check current docs**.

*Example (Python, illustrative):*

```python
from openai import OpenAI

client = OpenAI()  # reads the API key from an environment variable

response = client.chat.completions.create(
    model="<current-model-name>",   # check current docs
    messages=[
        {"role": "system", "content": "You are a helpful HR assistant."},
        {"role": "user", "content": "Draft a polite email declining a job offer."},
    ],
    temperature=0.3,
)
print(response.choices[0].message.content)
```

**Key point:** Never hard-code API keys; use environment variables or a secrets manager.

---

**10. What does fine-tuning a GPT model involve?**

Fine-tuning means training an existing model further on your own examples so it behaves in a specific way (style, format or domain behaviour). The usual steps are:

1. **Collect examples:** pairs of input and ideal output.
2. **Clean and format** them in the provider's required format (often JSON Lines).
3. **Split** into training and validation sets.
4. **Upload and start** a fine-tuning job through the provider's service.
5. **Evaluate** the new model on held-out examples and compare it with the original model plus a good prompt.

Which models can be fine-tuned and how may change, so **check current docs**.

*Example training record (illustrative):*

```json
{"messages": [
  {"role": "system", "content": "You write product descriptions in a friendly tone."},
  {"role": "user", "content": "Stainless steel water bottle, 1 litre"},
  {"role": "assistant", "content": "Stay hydrated in style! This 1-litre stainless steel bottle keeps drinks cold all day."}
]}
```

**Interview tip:** Fine-tuning is best for style, format and consistent behaviour. For adding fresh *knowledge*, RAG is usually the better first choice.

---

## 5.2 BERT (Q11–Q20)

**11. What does BERT stand for?**

BERT stands for **Bidirectional Encoder Representations from Transformers**.

- **Bidirectional:** it reads context from both the left and the right of a word at the same time.
- **Encoder:** it uses only the encoder half of the Transformer.
- **Representations:** its output is a numeric representation (vector) of each token and of the whole text, useful for downstream tasks.

*Example:* For the word "bank" in "She sat on the river bank", BERT uses the words on both sides ("river") to produce a vector that differs from the vector for "bank" in "She deposited money at the bank".

**Key point:** BERT produces *contextual* representations, so the same word gets different vectors in different sentences.

---

**12. Who developed BERT?**

BERT was developed by researchers at **Google** and published in **2018**. It was quickly adopted across the industry and open-sourced, and Google used BERT-based models to improve how its search understands queries. It changed natural language processing because one pre-trained model could be fine-tuned for many tasks with small labeled datasets.

*Example:* Before BERT, a team building a sentiment classifier often trained a model from scratch. After BERT, they could download a pre-trained BERT and fine-tune it on a few thousand labeled reviews.

**Interview tip:** If asked "GPT vs BERT origin", say OpenAI built GPT and Google built BERT, both in 2018, both based on the Transformer.

---

**13. What architecture does BERT use?**

BERT uses an **encoder-only Transformer**. The input text is turned into tokens, embeddings are added (token, position and segment), and the stack of encoder layers applies **self-attention without a causal mask**, so every token can attend to every other token in both directions. The output is one vector per token. A small task-specific layer is added on top for tasks such as classification.

*Example:* Input: `[CLS] The movie was great [SEP]`. BERT outputs a vector for each of the six pieces. For sentiment classification, a small layer takes the `[CLS]` vector and predicts Positive or Negative.

**Key point:** No decoder means no natural text generation, but excellent understanding.

---

**14. What does "bidirectional" mean in BERT?**

Bidirectional means BERT considers the **words before and after** a given word when building its representation. A left-to-right model like GPT only sees the words before. Bidirectional context helps with ambiguity, because the meaning of a word often depends on what follows it as well.

*Example:*

- "He went to the **bank** to deposit cash."
- "He sat by the **bank** and watched the river."

The words after "bank" ("to deposit cash" or "and watched the river") help BERT decide which meaning is correct. A purely left-to-right model has only "He went to the" or "He sat by the" at that moment.

**Interview tip:** Mention that bidirectionality is what makes BERT great at *understanding* but is also why it cannot simply generate the next word.

---

**15. What are BERT's pre-training objectives?**

BERT was pre-trained on large unlabeled text with two objectives:

- **Masked Language Modeling (MLM):** about 15% of tokens are hidden (replaced with `[MASK]` in most cases) and the model predicts them using both-side context.
- **Next Sentence Prediction (NSP):** the model sees two sentences and predicts whether the second really follows the first in the source text.

Later research (for example RoBERTa) found NSP added little and dropped it, keeping MLM.

*Example (MLM):*

```
Input : The capital of France is [MASK].
Output: Paris   (highest probability)
```

**Key point:** MLM is self-supervised: the "labels" are the original words, so no human labeling is needed.

---

**16. What tasks is BERT good for?**

BERT is best at **understanding and analysing** text, usually after fine-tuning. Typical tasks:

- **Text classification** (sentiment, spam, topic, intent).
- **Named Entity Recognition (NER):** finding people, places, organizations.
- **Extractive question answering:** finding the answer span inside a passage.
- **Semantic search and sentence similarity** (usually with a sentence-embedding variant).

*Example (using the Hugging Face library, illustrative):*

```python
from transformers import pipeline

classifier = pipeline("sentiment-analysis")  # uses a BERT-family model by default
print(classifier("The delivery was late and the box was damaged."))
# [{'label': 'NEGATIVE', 'score': 0.99}]
```

**Interview tip:** If a task is "label or extract from text", BERT-style models are often cheaper and faster than a large generative model.

---

**17. Can BERT generate text?**

Not naturally. BERT is trained to fill in masked words and to understand text, not to continue a sequence token by token. You can force it to fill blanks, and researchers have built tricks to make it generate, but the results are poor compared with decoder models such as GPT, Llama or Claude. For generation tasks like chat, writing or code completion, use a decoder-only or encoder–decoder model.

*Example:* BERT can fill `"I want to eat [MASK]."` with "pizza", but it cannot reliably write a full paragraph about pizza.

**Key point:** Understanding tasks → BERT-style. Generation tasks → GPT-style.

---

**18. What does [CLS] do in BERT?**

`[CLS]` is a special token added at the **start** of every input. Because of self-attention, the final vector at the `[CLS]` position gathers information from the whole sequence, so it works as a summary representation. For sentence-level tasks such as classification, a small classifier layer is trained on that vector. A related special token, `[SEP]`, marks the boundary between sentences, and `[MASK]` marks hidden tokens during pre-training.

*Example:*

```
Tokens : [CLS] I   love  this  phone [SEP]
Vectors:  v0   v1  v2    v3    v4    v5
Classifier uses v0  ->  "Positive"
```

**Interview tip:** Say "CLS stands for classification; its output vector represents the whole sentence for classification tasks."

---

**19. Name BERT variants.**

Many models improve or adapt BERT:

- **RoBERTa:** trained longer on more data, without NSP, for better performance.
- **DistilBERT:** a smaller, faster model made by knowledge distillation, keeping much of BERT's quality.
- **ALBERT:** shares parameters across layers to reduce size.
- **Domain models:** BioBERT (biomedical text), FinBERT (finance), SciBERT (scientific papers), and multilingual BERT variants.

*Example:* A mobile app that classifies messages on-device may choose DistilBERT because it is smaller and faster. A hospital project on clinical notes might start from a biomedical BERT.

**Key point:** Pick a variant based on the trade-off between accuracy, speed, size and domain.

---

**20. BERT vs GPT?**

| Aspect | BERT | GPT |
|---|---|---|
| Architecture | Encoder-only | Decoder-only |
| Direction | Bidirectional | Left-to-right (causal) |
| Pre-training | Masked language modeling | Next-token prediction |
| Strength | Understanding, classification, extraction, embeddings | Generation, chat, reasoning, coding |
| Typical usage | Fine-tune for a specific task | Prompt, or fine-tune, for many tasks |

*Example:* To tag emails as "billing", "technical" or "sales", a fine-tuned BERT classifier is efficient. To write the reply to that email, you use a GPT-style model.

**Interview tip:** Add that in practice they are often combined: BERT-style embeddings retrieve relevant documents, and a GPT-style model writes the answer.

---

## 5.3 Claude (Q21–Q30)

**21. What is Claude?**

Claude is a family of large language models built by **Anthropic**. People use it through the Claude apps (web, desktop, mobile), the Anthropic API, coding tools such as Claude Code, and cloud platforms that offer it. It handles chat, writing, analysis, summarization, coding and agent-style tasks with tools. Available models and features change, so **check current docs**.

*Example:* A finance analyst uploads a long report and asks, "List the five biggest risks mentioned and quote the sentence for each." Claude reads the document and returns the list with quotations.

**Interview tip:** Describe it neutrally: a general-purpose LLM family from Anthropic, with a stated focus on safety and helpfulness.

---

**22. What is Anthropic's approach to safety?**

Anthropic describes its mission as developing AI that is safe and beneficial. Its published approach includes **Constitutional AI**, extensive testing and red-teaming (deliberately trying to make the model misbehave), research into interpretability and alignment, and usage policies. The goal is for the model to be helpful while avoiding harmful outputs. How these practices are applied evolves, so **check current docs** and Anthropic's published materials.

*Example:* If a user asks for help with something clearly harmful, a safety-trained model should decline, while still helping with legitimate, similar-sounding requests such as safety education.

**Key point:** Safety is about balance: avoiding harm without being uselessly restrictive.

---

**23. What is Constitutional AI?**

Constitutional AI is a training approach from Anthropic in which the model is guided by a **written set of principles** (a "constitution"). In one stage, the model critiques and revises its own answers according to those principles. In another, AI-generated preference feedback (based on the principles) is used to train the model, reducing the need for human labelers to review every harmful example. The principles can draw on sources such as human-rights style documents and safety guidelines.

*Example (simplified flow):*

1. Prompt: a request that could produce a harmful answer.
2. Model drafts an answer.
3. Model is asked: "Does this answer violate the principle of avoiding harm? Rewrite it if so."
4. The improved answer becomes training data.

**Interview tip:** Contrast with plain RLHF: Constitutional AI uses written rules plus AI feedback, instead of relying only on human ratings.

---

**24. What are Claude's model tiers?**

Anthropic has released Claude models in **tiers that trade capability against speed and cost**. Historically these have been named Haiku (smaller, fast, lower cost), Sonnet (balanced) and Opus (most capable). Names, versions and the exact lineup change over time, so **check current docs** for the latest models and their recommended uses.

*Example:* An application might use a small fast tier to classify thousands of incoming support tickets cheaply, and a more capable tier to draft complex responses or do deep analysis on a few important cases.

**Key point:** The general idea to remember is "choose the smallest model that meets your quality bar".

---

**25. What are Claude's notable strengths?**

Commonly cited strengths of Claude models include:

- **Handling long inputs** such as long documents or codebases (context sizes vary by model; check current docs).
- **Writing and analysis** with careful, nuanced language.
- **Coding and agentic workflows**, where the model uses tools over many steps.
- **Following detailed instructions** and formatting requirements.

Strengths vary by version, and benchmark claims change, so evaluate on your own tasks.

*Example:* You give Claude a 40-page policy document and a system prompt: "Answer only from this document. If the answer is not present, say so." It tends to follow that constraint and quote relevant sections.

**Interview tip:** Avoid absolute statements such as "best model"; say what you measured on your own tasks.

---

**26. What is Claude Code?**

Claude Code is Anthropic's **agentic coding tool**. It runs in a terminal and also in IDEs and desktop or web apps. Unlike a chat window, it can read the files in your project, propose and make edits, run commands such as tests, and iterate on the results, with permission controls that let you approve actions. Features and availability change, so **check current docs**.

*Example (typical session):*

```
You: Add input validation to the signup form and update the tests.
Claude Code: reads the form and test files, edits them,
             runs the test suite, fixes a failing test,
             and shows you the diff for review.
```

**Key point:** Always review the diff and run tests yourself; the tool is an assistant, not a replacement for code review.

---

**27. What is the Model Context Protocol (MCP)?**

MCP is an **open protocol, introduced by Anthropic, for connecting AI applications to external tools and data sources in a standard way**. Instead of writing a custom integration for every tool and every AI app, a tool provider writes an **MCP server** once, and any MCP-compatible **client** (an AI app) can use it. Servers can expose tools (actions), resources (data) and prompts. It has been adopted beyond Anthropic's own products; check current docs for the latest specification.

*Example:* A company builds an MCP server for its ticketing system exposing "search_tickets" and "create_ticket". An MCP-capable assistant can then answer "Show my open high-priority tickets" by calling that tool.

**Interview tip:** Describe MCP as "USB-C for AI tools": one standard connector instead of many custom ones.

---

**28. How do you use Claude in an application?**

You call the **Anthropic Messages API** (or use it through a supported cloud platform). You send a model name, optional system prompt, a list of user and assistant messages and parameters like max tokens; the API returns the model's reply. It also supports features such as tool use, streaming, images and files, and prompt caching. Exact parameters and model names change, so **check current docs**.

*Example (Python, illustrative):*

```python
import anthropic

client = anthropic.Anthropic()  # reads the API key from an environment variable

message = client.messages.create(
    model="<current-model-name>",   # check current docs
    max_tokens=500,
    system="You are a concise assistant for an HR helpdesk.",
    messages=[
        {"role": "user", "content": "Explain our leave policy in 3 bullet points."}
    ],
)
print(message.content[0].text)
```

**Key point:** Note that `max_tokens` is required and the system prompt is a separate field in this API.

---

**29. What is prompt caching?**

Prompt caching lets the provider **reuse the already-processed beginning (prefix) of a prompt** across multiple requests instead of reprocessing it every time. When many requests share a long identical prefix, such as a large system prompt, a document or a set of examples, caching can reduce latency and cost. The cached part must stay identical, and details such as how long entries last and how they are billed vary, so **check current docs**.

*Example:* A chatbot always starts with a 20-page product manual (the fixed prefix) followed by each user's question (the changing part). With caching, the manual is processed once and reused, so each new question is faster and cheaper.

**Interview tip:** Design prompts with static content first and dynamic content last so the prefix can be cached.

---

**30. How does Claude compare with other LLMs?**

Claude, GPT, Llama and others have overlapping capabilities, and rankings change with each release. The practical way to compare is to test on **your own tasks** using criteria such as:

- Output quality for your use case.
- Cost per request and latency.
- Context window size.
- Safety and refusal behaviour.
- Data privacy and retention terms.
- Tooling and ecosystem fit.
- Whether you need an open-weight model you can self-host.

*Example evaluation plan:* Collect 50 real customer questions with approved answers. Run each candidate model with the same prompt. Score accuracy, tone and format compliance, and record cost and response time. Choose the model with the best quality-per-cost for your needs.

**Key point:** "It depends, and here is how I would measure" is a stronger interview answer than naming a winner.

---

## 5.4 Llama (Q31–Q40)

**31. What is Llama?**

Llama is a family of large language models created by **Meta**. Meta releases the model **weights** so that developers and organizations can download and run them on their own hardware, subject to the license terms. It comes in multiple sizes, including smaller ones suited to limited hardware, and has a large community producing fine-tunes, tools and applications. Specific versions and sizes change, so **check current docs**.

*Example:* A company with strict data-privacy rules downloads a Llama model and runs it on its own servers, so customer text never leaves its network.

**Interview tip:** Emphasize control: you choose where it runs, how it is customized and what data it sees.

---

**32. Is Llama open source?**

Llama is usually called **open-weight**, not fully open source. The model weights can be downloaded and used, but Meta releases them under its own **license with conditions and restrictions** (for example around acceptable use and certain large-scale commercial uses). It also does not necessarily publish all the training data. Under the common definition of open source, a license must meet specific freedoms, so many people say "open-weight" for accuracy. Read the current license before commercial use.

*Example:* A startup planning to ship a product built on Llama should have someone read the license terms for its specific model version first.

**Key point:** Open-weight ≠ unrestricted. Always check the license.

---

**33. What architecture does Llama use?**

Llama is a **decoder-only Transformer**, like GPT, with several modern refinements:

- **RMSNorm** for normalization.
- **SwiGLU** activation in the feed-forward layers.
- **Rotary Positional Embeddings (RoPE)** to encode token position.
- **Grouped-query attention** in some versions to speed up inference and reduce memory.

*Example:* You do not need to code these, but you may be asked to explain them at a high level: RoPE helps the model understand token order; grouped-query attention reduces the memory used by the KV cache during generation.

**Interview tip:** If you do not remember the details, say "decoder-only Transformer with efficiency improvements" rather than guessing.

---

**34. Why is Llama popular?**

Llama is popular because it gives teams capabilities that hosted-only models cannot:

- **Self-hosting and privacy:** data stays in your environment.
- **Customization:** you can fine-tune it on your own data.
- **Cost control:** at high volume, running your own model can be cheaper than paying per token (depends on hardware and usage).
- **Offline or edge use** with smaller models.
- **Community and tooling:** many fine-tunes, runtimes and tutorials.

*Example:* A hospital that cannot send patient notes to an external API might run a fine-tuned Llama model inside its own secure network.

**Key point:** The trade-off is that you take on infrastructure and maintenance.

---

**35. What sizes does Llama come in?**

Llama has been released in **multiple sizes** (measured by number of parameters), ranging from small models that fit on a laptop or phone-class hardware to very large models that need many GPUs. Larger models are generally more capable but slower and costlier to run. The available sizes differ by version, so **check current docs**.

*Example:*

| Need | Sensible choice |
|---|---|
| Quick local experiments on a laptop | Small model, quantized |
| A production chatbot on a single GPU server | Medium model |
| Highest quality, with a large GPU cluster | Largest model |

**Interview tip:** The rule of thumb: start small, measure quality, and move up only if needed.

---

**36. How can you run Llama locally?**

Several tools make this easy, usually using **quantized** weights so the model fits in memory:

- **Ollama:** simple command-line tool and local server.
- **llama.cpp:** efficient C/C++ runtime for CPUs and GPUs.
- **LM Studio:** a desktop app with a graphical interface.

*Example (Ollama; model names change, check the Ollama library):*

```bash
# download and chat with a Llama model
ollama run llama3.2

# or call the local server over HTTP
curl http://localhost:11434/api/generate -d '{
  "model": "llama3.2",
  "prompt": "Explain overfitting in one paragraph."
}'
```

**Key point:** Memory (RAM/VRAM) is the main limit; bigger models need more of it.

---

**37. What is quantization?**

Quantization **stores a model's numbers (weights) with fewer bits**, for example 8-bit or 4-bit instead of 16-bit. This shrinks the file, reduces memory use and often speeds up inference, usually with a small loss in quality. It is what lets large models run on consumer hardware.

*Example (rough arithmetic):* A model with 8 billion parameters stored in 16-bit uses about 16 GB for weights (8 billion × 2 bytes). In 4-bit it uses about 4 GB (8 billion × 0.5 bytes), plus some overhead. That is why a 4-bit version can fit on a typical gaming GPU or laptop.

**Interview tip:** Mention the trade-off clearly: smaller and faster, but slightly lower quality, and very aggressive quantization can hurt noticeably.

---

**38. What is LoRA / QLoRA?**

**LoRA (Low-Rank Adaptation)** is a parameter-efficient fine-tuning method. Instead of updating all of a model's weights, it freezes them and trains **small extra "adapter" matrices** added to certain layers. This needs far less memory and compute, and the adapter is a small file you can swap in and out. **QLoRA** does the same on top of a **quantized** base model, making it possible to fine-tune fairly large models on modest GPUs.

*Example:* A company fine-tunes a Llama model to write replies in its brand voice. With LoRA they train an adapter of a few hundred megabytes or less, instead of retraining billions of weights, and can keep separate adapters for different brands.

**Key point:** LoRA = cheap, modular fine-tuning. QLoRA = LoRA + quantization to save even more memory.

---

**39. When choose Llama over a hosted API?**

Choose Llama (or another open-weight model) when:

- **Privacy or compliance** requires that data stays in your environment.
- **Cost at large scale** makes self-hosting cheaper than per-token billing.
- **Deep customization** or fine-tuning is needed.
- **Offline or on-device** operation is required.
- **Vendor independence** matters.

Choose a hosted API when you want the quickest start, the strongest available models, no infrastructure and automatic upgrades.

*Example:* A prototype built in two days should use a hosted API. A mature product processing millions of internal documents daily under strict data rules may justify a self-hosted Llama deployment.

**Interview tip:** Frame it as a trade-off between convenience (API) and control (self-hosted).

---

**40. What are the downsides of self-hosting Llama?**

Self-hosting gives control but adds responsibility:

- **Infrastructure cost:** GPUs, servers and idle capacity.
- **Operations:** scaling, monitoring, uptime and upgrades are your job.
- **Quality gap:** the best hosted models may outperform what you can run cheaply (depends on the task and time).
- **Safety and moderation:** you must add your own filters and guardrails.
- **Evaluation and maintenance:** you must test new versions and retrain adapters.
- **License compliance:** you must follow the license terms.

*Example:* A team saves on API fees but then spends engineer time handling GPU outages and capacity planning during peak load. The true cost comparison must include people and infrastructure, not just token prices.

**Key point:** Compare total cost of ownership, not just per-token price.

---

## 5.5 Copilot (Q41–Q46)

**41. What is GitHub Copilot?**

GitHub Copilot is an **AI coding assistant from GitHub (owned by Microsoft)**. It works inside editors such as VS Code and JetBrains IDEs and on GitHub itself. It gives inline code suggestions as you type, a chat interface for questions and edits, and newer agent-style features that can work across multiple files. Plans and features change, so **check current docs**.

*Example:* You type a comment in Python:

```python
# function that returns the n-th Fibonacci number using iteration
```

and Copilot suggests the full function body, which you can accept with the Tab key.

**Interview tip:** Say what it does well (boilerplate, tests, explanations) and that you always review its output.

---

**42. What models power GitHub Copilot?**

Copilot began with **OpenAI Codex**, a model derived from GPT trained on code and text. Since then, GitHub has expanded Copilot to use newer models, and it now offers a **choice of models from several providers** in some features. The exact list changes frequently and depends on plan and feature, so **check current docs** rather than relying on a fixed list.

*Example:* In Copilot Chat you may be able to pick a model from a dropdown, using a faster model for quick questions and a more capable one for complex refactoring, subject to what your plan allows.

**Key point:** Do not memorize model names for Copilot; remember that it is multi-model and changing.

---

**43. What is Microsoft Copilot?**

**Microsoft Copilot** is a family of AI assistants from Microsoft. Examples include **Microsoft 365 Copilot**, which works inside Word, Excel, PowerPoint, Outlook and Teams using your organization's data under its access controls, and the general **Copilot** app and Windows/Edge integrations. It is a **different product from GitHub Copilot**, although both use the "Copilot" name and are Microsoft-related. Product names and features change, so **check current docs**.

*Example:* In Outlook, Microsoft 365 Copilot might summarize a long email thread and draft a reply; in Excel it might help analyze a table. GitHub Copilot would instead help you write code in an IDE.

**Interview tip:** Interviewers use this to test whether you confuse the two. State clearly: GitHub Copilot = coding, Microsoft 365 Copilot = office productivity.

---

**44. What are the main features of GitHub Copilot?**

Commonly available features include:

- **Inline completions:** suggestions as you type.
- **Copilot Chat:** ask questions about code, explain errors, generate snippets.
- **Code explanation and refactoring help.**
- **Test generation:** draft unit tests for a function.
- **Pull request assistance:** summaries and descriptions.
- **Agent modes:** multi-step, multi-file tasks with your approval.

Exact availability depends on plan and version, so **check current docs**.

*Example (test generation):* Select a function `calculate_discount(price, pct)` and ask Chat: "Write pytest tests including edge cases." Copilot returns tests for 0%, 100%, negative values and so on, which you then review and run.

**Key point:** Treat generated tests as a starting point; verify they actually check the right behaviour.

---

**45. How should developers use Copilot safely?**

Treat Copilot's output like code from a junior colleague: useful, but it must be reviewed.

- **Review every suggestion** and understand it before accepting.
- **Run tests and linters;** add tests for new behaviour.
- **Watch for security issues,** such as SQL injection, hard-coded secrets or weak cryptography.
- **Check licensing and policy settings** your organization provides for public-code matching.
- **Do not paste secrets or sensitive data** into prompts.
- **Follow your company's AI-usage policy.**

*Example:* Copilot suggests building a SQL query by concatenating user input. A careful developer replaces it with a parameterized query:

```python
# risky suggestion
cursor.execute("SELECT * FROM users WHERE name = '" + name + "'")

# safer
cursor.execute("SELECT * FROM users WHERE name = %s", (name,))
```

**Interview tip:** Giving a concrete security example like this shows real-world judgment.

---

**46. How does Copilot understand the project?**

Copilot builds context from what is available to it, which commonly includes the **current file, code near the cursor, other open files or tabs, and, in chat or agent modes, files or the repository you reference**. Some features let you point to specific files, folders or the workspace, or add custom instructions describing your project's conventions. Better context usually produces better suggestions, but it cannot see what you do not give it. How context is gathered varies by feature, so **check current docs**.

*Example:* If you open `models.py` and `utils.py` together and then start writing a function in `views.py`, Copilot can often suggest code that uses the classes and helpers defined in those files.

**Key point:** To improve results, open relevant files, write clear comments or prompts, and reference specific files in chat.

---

## 5.6 Codeium (Q47–Q50)

**47. What is Codeium?**

Codeium was an **AI coding assistant** offering code autocomplete, chat and code search across many programming languages and IDEs. It became well known for offering a **free tier for individual developers**. The company has since rebranded its products under the **Windsurf** name, so its details and availability may have changed. **Check current docs** for what is offered today.

*Example:* A student using a free plugin in VS Code gets autocomplete suggestions while writing JavaScript functions, similar in spirit to GitHub Copilot.

**Interview tip:** Mention the rebrand to Windsurf to show you are up to date.

---

**48. What happened to Codeium's branding?**

The company behind Codeium launched an AI-native editor called **Windsurf** and later moved its products under that brand. The Codeium extensions for editors were renamed or replaced by **Windsurf plugins**, and the company itself became known as Windsurf. Because corporate and product changes happen quickly in this space, verify the current names, ownership and plans in **current docs or news** before relying on any statement here.

*Example:* If an older tutorial tells you to "install the Codeium extension", you may need to search for the Windsurf plugin instead, or check whether the older name still works.

**Key point:** In interviews, say "Codeium, now branded Windsurf" and note that you would verify the latest status.

---

**49. What is Windsurf?**

Windsurf is an **AI-native IDE** (an editor built around AI) from the company formerly known for Codeium. It combines a code editor, chat and an **agent** (named Cascade in its materials) that can understand the codebase, edit multiple files and run commands, with the developer reviewing the changes. Features and availability evolve, so **check current docs**.

*Example:* You ask: "Rename the `User` class to `Customer` across the project and update imports." An agent-style assistant searches the project, edits the relevant files, and presents the changes for you to review and test.

**Interview tip:** The key idea is the shift from "autocomplete in an existing editor" to "AI agent embedded in the editor".

---

**50. How do you compare coding assistants such as Copilot, Codeium and Claude Code?**

Because products change quickly, a good answer gives a **method** rather than a fixed ranking. Compare:

| Criterion | What to check |
|---|---|
| Suggestion quality | Accuracy on your languages and frameworks |
| Context awareness | Can it use the whole repository, not just one file? |
| Agent capabilities | Can it plan, edit multiple files and run tests? |
| IDE and language support | Works in your editor and stack |
| Privacy and data retention | Is your code stored or used for training? |
| Enterprise controls | Admin policies, audit logs, license filters |
| Model choice and cost | Which models, and what pricing for your team size |

*Example trial:* Pick three real tasks (add an endpoint, fix a bug, write tests). Have each tool attempt them on the same codebase. Record correctness, time saved, number of corrections needed and developer satisfaction. Decide based on the results and your security requirements.

**Key point:** The best tool is the one that performs well on your codebase and satisfies your organization's security rules.

---

## Quick Revision Table

| Model | Developer | Architecture | Best for |
|---|---|---|---|
| GPT | OpenAI | Decoder-only Transformer | General generation, chat, coding, reasoning |
| BERT | Google | Encoder-only Transformer | Classification, NER, QA, embeddings |
| Claude | Anthropic | LLM family (check docs for details) | Long documents, writing, coding, agents |
| Llama | Meta | Decoder-only Transformer (open-weight) | Self-hosting, privacy, customization |
| GitHub Copilot | GitHub / Microsoft | Multi-model coding assistant | Code completion, chat, agent tasks in IDEs |
| Codeium / Windsurf | Codeium → Windsurf | Coding assistant / AI IDE | Autocomplete, agentic editing |
