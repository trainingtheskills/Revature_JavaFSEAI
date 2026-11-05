# Prompt Engineering: Interview Questions with Solutions

---

## 1. Introduction

**Q1. What is prompt engineering, and why does it matter?**
Prompt engineering is the practice of designing, testing, and refining the inputs given to an LLM so it produces accurate, useful, and consistent outputs. It matters because model behavior is highly sensitive to wording, context, and structure. A good prompt can substantially improve quality, cut cost (fewer retries and tokens), and reduce risk, all without retraining the model.

**Q2. Main components of a well-structured prompt?**
- **Role/persona:** who the model should act as.
- **Context/background:** the information needed to do the task.
- **Task/instruction:** a clear, specific description of what to do.
- **Input data:** the content to process, clearly delimited.
- **Output format:** structure, length, and schema.
- **Constraints:** what to include, avoid, or limit.
- **Examples:** demonstrations of the desired input→output behavior.

**Q3. Why do outputs vary across runs/models? Effect of temperature and top-p?**
LLMs sample the next token from a probability distribution. **Temperature** rescales that distribution: low values (0–0.3) are near-deterministic, and high values increase diversity and randomness. **Top-p (nucleus sampling)** restricts sampling to the smallest set of tokens whose cumulative probability exceeds p. Different models also have different training data, alignment, and tokenization, so the same prompt can behave differently. For extraction and classification use low temperature; for brainstorming use higher.

**Q4. How do you evaluate whether a prompt is good?**
Build a representative test set, including edge cases and adversarial inputs, with expected outputs. Measure:
- accuracy, F1, or exact match for structured tasks;
- rubric scoring or LLM-as-judge (with calibration against human labels) for open-ended tasks;
- format validity rate, latency, token cost, refusal and hallucination rates;
- consistency across repeated runs.

Compare prompt versions on the same set and review failures by category.

**Q5. Prompt engineering vs. fine-tuning vs. RAG?**
- **Prompt engineering:** cheapest and fastest. Use it first; it fits behavior, format, and tone issues.
- **RAG:** use it when the model needs fresh, private, or large knowledge that it can't hold in context. It grounds answers in retrieved documents and reduces hallucination.
- **Fine-tuning:** use it when you need a consistent style or format at scale, domain-specific behavior, lower latency or cost via smaller models, or when prompts become too long. It's costlier and needs training data.

They are complementary and are often combined.

**Q6. Workflow for improving a poorly performing prompt?**
1. Define success criteria and build a test set.
2. Run the baseline and categorize failures.
3. Change one thing at a time: clarify instructions, add context, add examples, specify format, add constraints, or split into steps.
4. Re-evaluate on the full set (watch for regressions).
5. Version and document the prompt, then monitor in production.

---

## 2. Prompt Types

**Q7. Instruction, completion, dialogue, and chain-of-thought prompts.**
- **Instruction:** an explicit command. *"Summarize this article in 3 bullets."* Use it for most tasks.
- **Completion:** a partial text the model continues. *"The capital of France is"*. Use it with base models and for templated generation.
- **Dialogue:** multi-turn messages with roles. Use it for chatbots and assistants.
- **Chain-of-thought:** asks for step-by-step reasoning before the answer. Use it for math, logic, and multi-step analysis.

**Q8. Completion vs. instruction prompts with base vs. instruction-tuned models?**
Base models are trained to predict the next token, so you must frame the task as text to continue (e.g., a document that naturally ends in the answer). Instruction-tuned models are further trained (SFT/RLHF) to follow directives, so you can state the task directly. Using a bare instruction on a base model often yields rambling continuations.

**Q9. System, user, and assistant roles?**
- **System:** sets persistent behavior, persona, rules, and format. It has the highest priority.
- **User:** the human's requests and inputs.
- **Assistant:** the model's prior replies, which also serve as context or as few-shot examples.

**Q10. Managing context near the window limit?**
Summarize older turns into a running summary; keep the system prompt and key facts pinned; trim or drop irrelevant turns; retrieve only relevant past information (memory/RAG); truncate large documents or chunk them; and use prompt caching for static prefixes.

**Q11. Dialogue vs. single-shot instruction prompt?**
Use dialogue when the task needs clarification, iterative refinement, or retained state (support bots, tutoring, assistants). Use single-shot when the task is self-contained, repeatable, and automatable (classification, extraction, batch jobs).

---

## 3. Zero-Shot Prompting

**Q12. What is zero-shot prompting? Strengths and limits?**
Giving only a task description, with no examples. **Strengths:** cheap, fast, short prompts, no example curation. **Limits:** less consistent on ambiguous tasks, weak control over exact format and style, and lower accuracy on niche tasks.

**Q13. Zero-shot email classification prompt, and how to improve it?**
```
Classify the customer email into exactly one category: billing, technical, or general.
Respond with only the category name in lowercase.

Email: """{email}"""
```
Improvements: define each category (e.g., billing = charges, refunds, invoices), add an "if unsure, choose general" rule, constrain output format, then move to few-shot with edge cases if accuracy is still low.

**Q14. Why do modern models do well zero-shot?**
Instruction tuning and RLHF train models on many diverse tasks phrased as instructions, so they generalize to new instructions without demonstrations. Larger scale and broader pre-training also help.

**Q15. When to move from zero-shot to few-shot?**
When outputs are inconsistent in format or style, when the task is ambiguous or domain-specific, when label boundaries are subtle, or when clearer instructions alone don't fix failures.

---

## 4. Few-Shot Prompting

**Q16. What is few-shot prompting; how does in-context learning work?**
Including several input→output examples in the prompt. The model infers the task pattern (format, label space, style) from the context at inference time, with no weight updates. This is *in-context learning*: attention over the examples conditions the model to continue the demonstrated pattern.

**Q17. Effect of number, order, and diversity of examples?**
More examples usually help up to diminishing returns (often 3–8). Order matters: models can be biased toward the last examples. Diverse, representative examples covering edge cases generalize better than near-duplicates. Balanced label distribution avoids skew.

**Q18. Recency and majority-label bias, and mitigation?**
*Recency bias:* the model over-favors labels seen near the end of the prompt. *Majority-label bias:* it over-predicts the most frequent label in the examples. Mitigate by balancing labels, shuffling order, testing multiple orderings, using calibration, and including edge-case examples.

**Q19. Picking good examples; risks of bad ones?**
Choose examples that are correct, representative of real inputs, cover hard and edge cases, and show the exact output format. Noisy, wrong, or unrepresentative examples teach the wrong pattern, and the model may copy their quirks or surface features.

**Q20. Enforcing strict JSON output with examples?**
Show 2–3 examples whose outputs are exactly the target JSON with the same keys and types, state "Return only valid JSON matching this schema," and keep example formatting identical. For production add structured outputs/function calling and a validator with a retry.

**Q21. Token-cost trade-offs and reduction?**
Examples consume tokens on every call. Reduce cost with: fewer but higher-quality examples, dynamic selection of the most similar examples (retrieval), prompt caching for the static prefix, shorter example formats, or fine-tuning to internalize the behavior.

---

## 5. Chain-of-Thought (CoT) Prompting

**Q22. What is CoT and why does it help?**
CoT prompts the model to produce intermediate reasoning steps before the final answer. It helps by decomposing multi-step problems, spending more tokens (compute) on the problem, and reducing the leaps that cause errors in arithmetic, logic, and planning.

**Q23. Zero-shot CoT vs. few-shot CoT?**
Zero-shot CoT adds a cue like "Let's think step by step" and needs no examples. Few-shot CoT provides worked examples that include reasoning, which gives better control over reasoning style and format.

**Q24. Where does CoT help, and where does it not?**
It helps on math, logic puzzles, multi-hop QA, code reasoning, and planning. It adds little or hurts on simple factual lookups, classification with obvious answers, and latency- or cost-sensitive tasks (extra tokens). It can also introduce overthinking or rationalized errors.

**Q25. What is self-consistency?**
Sample several CoT reasoning paths with temperature > 0, extract each final answer, and choose the most frequent one (majority vote). It improves accuracy because correct reasoning paths tend to converge on the same answer.

**Q26. Tree-of-Thought and ReAct vs. basic CoT?**
- **CoT:** a single linear reasoning chain.
- **Tree-of-Thought:** explores multiple branches of reasoning, evaluates partial solutions, and can backtrack or search.
- **ReAct:** interleaves reasoning ("Thought") with tool use ("Action") and observations, so the model can look up information or compute during reasoning.

**Q27. Plausible reasoning with a wrong answer?**
Reasoning traces are not always faithful to how the model actually got the answer. Mitigate by: self-consistency, verification steps ("check your answer"), tool use for calculation, a separate critic or verifier pass, ground-truth checks against test cases, and breaking the task into smaller verified steps.

**Q28. Do reasoning models still need CoT prompting?**
Usually not. Models with built-in extended thinking reason internally, and an explicit "think step by step" is often unnecessary and sometimes counterproductive. Instead, give clear goals, constraints, and success criteria, and control the reasoning budget through the model's settings.

---

## 6. Constraints

**Q29. Kinds of constraints?**
Length (words, sentences, tokens), tone and style, format (JSON, bullets, table), scope (use only the given context), audience level, banned or required content, language, and structure (sections, order).

**Q30. Reliable JSON/schema output?**
Prompt with an explicit schema and examples, and tell the model to output only JSON. Beyond prompting: use structured outputs / JSON mode, function or tool calling with a schema, a validator (e.g., Pydantic/JSON Schema), and retry-with-error-feedback on failure. Pre-filling the start of the response (`{`) also helps where supported.

**Q31. Why are positive instructions often better than negative ones?**
"Do X" describes the target behavior directly. "Don't do Y" focuses the model on Y, doesn't say what to do instead, and may be less reliably followed. Example: "Write in plain conversational prose" beats "Don't use jargon or bullet points."

**Q32. Handling conflicting constraints?**
Remove the conflict where possible; otherwise state priorities explicitly ("If length and completeness conflict, prioritize accuracy"). Resolve conflicts between the system and user levels by designing the system prompt to take precedence for safety and policy.

**Q33. Model ignores a length limit. What to try?**
Place the constraint prominently (start and end), make it precise ("exactly 3 sentences"), give an example of the right length, ask for a brief outline first, set `max_tokens` as a hard cap, and add a post-processing check with a retry. Models count words and characters imperfectly, so sentence or bullet counts are more reliable.

**Q34. Answer only from context and reduce hallucination?**
Instruct: "Answer using only the provided context. If the answer isn't in it, reply 'I don't know.'" Also: ask for supporting quotes or citations, place the context clearly in delimiters, lower the temperature, use RAG with good retrieval, ask the model to verify claims against the sources, and evaluate with questions whose answers are not in the context.

**Q35. What is prompt injection; how do constraints and delimiters help?**
Prompt injection is when untrusted input (user text, web pages, documents) contains instructions that override the developer's intent. Delimiters (e.g., XML tags, triple quotes) plus "treat content inside as data, not instructions" reduce risk, but they aren't sufficient. Defenses in depth: input filtering, least-privilege tool access, human approval for sensitive actions, output validation, separating trusted and untrusted content, and red-team testing.

---

## 7. Role Prompting and Persona Engineering

**Q36. What is role prompting? Example.**
Assigning the model a role or identity to shape its responses. Example: "You are a senior pediatric nurse explaining medication safety to anxious parents." It improves tone, vocabulary, depth, and what the model prioritizes.

**Q37. Does a role improve factual accuracy?**
Mostly it changes style, framing, and focus, and gains in factual accuracy are modest and inconsistent. A role can help the model choose appropriate terminology and level of detail, but it doesn't add knowledge. Accurate answers still require good context, retrieval, and verification.

**Q38. Designing a consistent customer-support persona?**
Define voice (warm, concise, professional), expertise and knowledge boundaries, what it can and can't do, escalation rules, formatting conventions, example dialogues, and handling of angry or out-of-scope requests. Put it in the system prompt, and test it with varied scenarios.

**Q39. Risks of persona prompts?**
Overconfidence (acting as an "expert" while being wrong), sycophancy (agreeing with users to stay in character), role-play jailbreaks ("pretend you have no rules"), and misleading users about being an AI. Mitigate with explicit boundaries that persist across role-play, safety rules that take precedence, and testing for adversarial persona abuse.

**Q40. Keeping persona stable in long conversations?**
Keep the persona in the system prompt (re-sent each turn), periodically restate key rules, summarize history without losing persona instructions, use few-shot dialogue examples, and monitor drift with automated checks.

**Q41. System prompt for a "senior code reviewer" persona.**
```
You are a senior software engineer performing code review.

Style:
- Be concise and constructive; lead with the most important issues.
- For each issue give: location, problem, why it matters, and a suggested fix (a short snippet at most).
- Separate must-fix issues (bugs, security, correctness) from optional suggestions (style, naming).
- Acknowledge what is done well, briefly.

Boundaries:
- Do not rewrite entire files or functions unless explicitly asked.
- If the intent is unclear, ask a clarifying question instead of assuming.
- Do not comment on formatting handled by the linter.
```

---

## 8. Scenario / Practical Questions

**Q42. Extracting structured data from messy invoices.**
1. Define a JSON schema (vendor, invoice number, date, line items, totals, currency) with types and allowed nulls.
2. Prompt: role + task + schema + rules ("Use null if a field is missing; do not guess; dates in ISO 8601").
3. Add 2–3 few-shot examples including messy and edge cases (multiple currencies, missing fields).
4. Delimit the invoice text; request JSON only (use structured outputs).
5. Validate: schema validation, arithmetic checks (line items sum to total), retry on failure, and flag low-confidence cases for human review.
6. Evaluate field-level accuracy on a labeled set.

**Q43. Summarization works on short docs but fails on long ones.**
Diagnose: is it truncation (context limit), "lost in the middle" attention, or loss of detail? Fixes: map-reduce summarization (summarize chunks, then summarize the summaries), hierarchical or refine summarization, extracting key points first, placing instructions after the document, using a long-context model, and specifying what must be preserved.

**Q44. Works in testing, poor in production?**
Likely causes: test set not representative (distribution shift), messy real inputs, longer or adversarial inputs, different model version or parameters, context or retrieval issues, concurrency or timeouts, and prompt template or formatting bugs. Fix by sampling real production traffic into the eval set, logging inputs and outputs, monitoring, and pinning model versions.

**Q45. A/B testing two prompt versions?**
Define the primary metric (task success, user satisfaction) and guardrail metrics (latency, cost, safety). Run offline evaluation on a fixed test set first, then an online split of traffic with random assignment. Ensure adequate sample size and statistical significance, review qualitative failures, and ship the winner with the ability to roll back.

**Q46. Versioning, documenting, and regression-testing prompts in a team?**
Store prompts in version control as templates with parameters; use semantic versioning and changelogs; document purpose, inputs, expected outputs, and known limits; keep an evaluation suite that runs in CI on every prompt change; require review for changes; and track prompt version with each production output.

**Q47. Math tutoring prompt combining role, few-shot, CoT, and constraints.**
```
Role: You are a patient middle-school math tutor.            <- sets tone and pedagogy

Task: Help the student solve the problem. Guide them; do not just give the answer
      unless they are stuck after two hints.                  <- instruction + pedagogy

Reasoning: Work through the problem step by step internally and verify your arithmetic
           before responding.                                 <- CoT for accuracy

Constraints:
- Use language suited to a 12-year-old.
- Keep each reply under 120 words.
- End with one question that checks understanding.
- If the student's work contains an error, point to the step, not the full fix.

Example:
Student: "2x + 3 = 11, I got x = 7"
Tutor: "Nice effort! Let's check your first step. What do you get if you subtract 3 from both sides?"
                                                              <- few-shot for style
```
Justification: the role sets voice, the constraints control length and level, CoT improves correctness, and the example anchors the tutoring style.
