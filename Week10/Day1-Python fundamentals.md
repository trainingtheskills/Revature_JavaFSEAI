# Week 10   -     AI EngineeringFoundations (Python &Fast API)

## DAY 1- Python Fundamentals 

Python is a high-level, interpreted programming language known for its clear syntax and readability. It is a versatile, general-purpose language that supports multiple programming paradigms, including object-oriented, procedural, and functional programming.
## 1. Python Language features
<img width="1024" height="541" alt="image" src="https://github.com/user-attachments/assets/a8b4e46a-fe10-4a02-94ee-3b5d74055d2f" />

## Language Design

- **Simple, readable syntax**: indentation-based blocks and English-like keywords make code short and easy to learn.
- **Interpreted**: code runs line by line without a separate compile step, which speeds up testing and debugging.
- **Dynamically typed**: you don't declare variable types; they're determined at runtime (optional type hints are available).
- **High-level**: memory management, garbage collection, and low-level details are handled for you.

## Programming Paradigms

- **Object-oriented**: classes, inheritance, polymorphism, and encapsulation.
- **Procedural and functional**: functions, lambdas, `map`/`filter`, comprehensions, and generators.
- **Multi-paradigm**: you can mix styles in the same program.

## Ecosystem

- **Extensive standard library**: modules for file I/O, networking, regex, JSON, threading, and more ("batteries included").
- **Huge third-party ecosystem** via PyPI: NumPy, pandas, Django, Flask, FastAPI, TensorFlow, PyTorch, and others.
- **Strong community** and extensive documentation.

## Practical Advantages

- **Cross-platform**: runs on Windows, macOS, and Linux with little or no change.
- **Open source and free**, including for commercial use.
- **Extensible and embeddable**: can call C/C++ code and be embedded in other applications.
- **Great for integration**: works well as glue between systems, APIs, and databases.
- **Built-in data structures**: lists, dictionaries, sets, and tuples are first-class and powerful.

<img width="1024" height="519" alt="image" src="https://github.com/user-attachments/assets/2e982335-8e4b-41d5-8ba5-3cb88b2e81c8" />

## 1. Python Syntax 
# 1. Python Syntax

Python's syntax is intentionally designed to be clean, elegant, and highly readable. Its creator, Guido van Rossum, aimed to build a language where reading code feels almost as natural as reading English sentences.

---

## 🏛️ 1. Core Syntax Fundamentals

### Whitespace and Indentation
Unlike languages like Java, C++, or JavaScript that use curly braces `{}` to define blocks of code, Python relies entirely on **whitespace indentation**. 

* **The Rule:** Every line of code inside a control structure (like a loop, function, or conditional statement) must be indented by the exact same number of spaces.
* **The Standard:** The official Python style guide (PEP 8) recommends using **4 spaces** per indentation level. Tabs should not be mixed with spaces.
* **Enforcement:** Incorrect or inconsistent spacing will cause the interpreter to stop executing and throw an `IndentationError`.

```python
# Correct Indentation
if True:
    print("This line belongs to the if-statement.")
    if True:
        print("This line belongs to a nested block.")

# Incorrect Indentation (Fails immediately)
if True:
print("This will trigger an IndentationError")
```

### End-of-Statement Rules
* **Newlines:** Python uses a newline character (pressing Enter) to mark the end of a statement. You do not need to end lines with semicolons (`;`). While semicolons are syntactically legal to put multiple statements on one line, doing so is considered unpythonic.
* **Implicit Line Continuation:** If code is enclosed inside parentheses `()`, brackets `[]`, or braces `{}`, you can split the statement across multiple lines without any errors.
* **Explicit Line Continuation:** If you have a long line of code outside of brackets, you can break it up manually by placing a backslash (`\`) at the end of the line.

```python
# Implicit continuation (Clean and preferred)
total_sum = (
    value_one +
    value_two +
    value_three
)

# Explicit continuation (Using a backslash)
total_product = 10 * 20 * \
                30 * 40
```

---

## 🏷️ 2. Identifiers, Keywords, and Variables

### Identifiers (Naming Rules)
Identifiers are the names you give to variables, functions, classes, and modules. They must follow these strict structural guidelines:
* Can contain alphanumeric characters (`A-Z`, `a-z`, `0-9`) and underscores (`_`).
* Must **never start with a digit** (e.g., `1variable` is invalid).
* Are **case-sensitive** (e.g., `UserAge`, `userage`, and `userAge` are treated as three completely separate variables).

### Keywords
Python reserves a specific set of words for its internal functional logic. You cannot use these keywords as identifiers or variable names:
* Control words: `if`, `elif`, `else`, `for`, `while`, `break`, `continue`, `return`
* Logic words: `and`, `or`, `not`, `True`, `False`, `None`, `in`, `is`
* Structural words: `def`, `class`, `import`, `from`, `as`, `try`, `except`, `pass`

### Dynamic Typing Syntax
Variables in Python are just pointers to objects in memory. You do not declare a variable's data type before using it. The type is assigned dynamically based on the value given, and it can safely change later in the script.

```python
data_point = 42        # Automatically treated as an integer (int)
data_point = "Active"  # Reassigned seamlessly to a string (str)
```

---

## 💬 3. Comments and Documentation

### Inline and Block Comments
Any text following a hash symbol (`#`) is completely ignored by the Python interpreter. These are used to explain the *why* behind your code logic.

```python
# Calculate the final price after applying a standard 10% discount
discounted_price = total_cost * 0.9
```

### Docstrings (Documentation Strings)
When triple quotes (`"""` or `'''`) are placed at the absolute beginning of a module, class, or function, they act as formal documentation. Python parses these so that developers can read the documentation interactively.

```python
def calculate_area(radius):
    """
    Computes the area of a circle.
    
    Parameters:
    radius (float): The radius of the circle.
    
    Returns:
    float: The calculated area.
    """
    return 3.14159 * (radius ** 2)
```

---

## 🔀 4. Structural Logic Syntax

### Conditionals (`if`, `elif`, `else`)
Conditional structures evaluate expressions to boolean values (`True`/`False`). Every conditional expression must end with a colon (`:`), which signals the start of the indented block below it.

```python
temperature = 25

if temperature > 30:
    print("It is hot outside.")
elif temperature >= 18:
    print("The weather is pleasant.")  # This block will execute
else:
    print("It is cold outside.")
```

### Code Suites and the `pass` Statement
Because Python relies on indentation to group code blocks together, you cannot leave an `if` statement or a function completely empty without causing a syntax error. If you need a structural placeholder for code you intend to write later, use the `pass` keyword.

```python
if user_has_logged_out:
    pass  # Placeholder: item will be coded later without breaking execution
```

## 2. Python 
a. Variables
b. datatypes 
c. operators
d. control flow
