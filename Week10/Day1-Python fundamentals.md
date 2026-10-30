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
### Comments
use either the hash symbol (#) for standard inline/block comments or triple quotes (""") for multi-line summaries and documentation.

### DocString 
A docstring (short for documentation string) is a special type of literal string in Python used to explain and document what a specific module, class, method, or function does.

A docstring is created by placing a triple-quoted string (""" or ''') as the absolute first statement inside a function, class, method, or module.
```
# 1. This is a single-line block comment
# It is placed on its own line above the code.
total_score = 100

final_price = total_score * 0.85  # 2. This is an inline comment (2 spaces after code)

"""
3. This is a multi-line string used as a comment.
Since it isn't assigned to a variable, Python ignores it.
Ideal for writing long explanations.
"""

def greet():
    """4. This is a Docstring. Placed inside a function/class to document it."""
    print("Hello!")
```
## 2. Python 
a. Variables and  datatypes 
# 1. Variables and Data Types in Python

In Python, programs store, manipulate, and manage information using **variables**. Understanding how variables point to values and how Python categorizes those values into distinct data types is essential for writing efficient code.

---

## 🏗️ 1. Introducing Variables

A variable is a reserved memory location used to store values. Unlike low-level languages where a variable is a static bucket holding data, a **Python variable acts like a label or pointer** attached to a specific object in memory.

### Variable Assignment Syntax
Python does not require command options or structural wrappers to declare a variable. A variable is created the moment you first assign a value to it using the assignment operator (`=`).

```python
# The label 'age' points to an integer object containing 25
age = 25 

# The label 'user_name' points to a text string object
user_name = "Alice"
```

### Core Variable Rules
* **No Declaration Required:** You never need to declare the data type ahead of time.
* **Dynamic Reassignment:** A single variable can reference different data types at different points during runtime execution.
* **Identifier Constraints:** Names must start with a letter or an underscore (`_`), can only contain alphanumeric characters and underscores (`A-Z`, `a-z`, `0-9`, `_`), and are strictly case-sensitive (`Total` and `total` are different variables).

```python
# Dynamic typing example
status = "Pending"  # Initially a string (str)
status = 200        # Changed dynamically to an integer (int)
```

---

## 📊 2. The Built-in Data Types

Python classifies data into structured families. Every object has an intrinsic data type that defines what actions can be safely performed on it.

### 🔢 Numeric Types
Used to represent quantitative mathematical values.

* **`int` (Integer):** Positive or negative whole numbers without decimals. Python integers have arbitrary precision (they can grow as large as your system's memory allows).
* **`float` (Floating-Point):** Numbers containing a decimal point or written in scientific notation (`e` or `E`).
* **`complex` (Complex Numbers):** Numbers with a real part and an imaginary part, written with a `j` suffix.

```python
count = 142          # int
price = 19.99        # float
impedance = 4 + 3j   # complex
```

### 🔤 Text Sequence Type
Used to manage character configurations and readable data blocks.

* **`str` (String):** Text wrapped inside either single quotes (`'...'`), double quotes (`"..."`), or triple quotes (`"""..."""` for multi-line blocks). Strings are **immutable** (they cannot be modified in place after creation).

```python
greeting = "Hello, World!"
multiline_notice = """This text spans across
multiple distinct lines."""
```

### ⚖️ Boolean Type
Used for logical evaluations and conditional control pathways.

* **`bool` (Boolean):** Can only hold one of two literal values: **`True`** or **`False`**. Booleans evaluate expressions internally during conditional processing.

```python
is_active = True
has_errors = False
```

### 📦 Sequence Types (Collections)
Used to organize multiple data objects sequentially inside a single variable container.

* **`list`:** An ordered, modifiable (**mutable**) sequence that allows duplicate items. Defined using square brackets (`[]`).
* **`tuple`:** An ordered, unchangeable (**immutable**) sequence that allows duplicate items. Defined using parentheses (`()`). Tuples are structurally faster and safer than lists.

```python
# List configuration (can be updated dynamically)
shopping_cart = ["item_a", "item_b", "item_c"]

# Tuple configuration (fixed permanently once built)
gps_coordinates = (40.7128, -74.0060)
```

### 🗺️ Mapping Type
Used to represent structured associations.

* **`dict` (Dictionary):** An unordered, mutable collection of **key-value pairs**. Keys must be unique and immutable (like strings or integers), while values can be any valid object. Defined using curly braces (`{}`) with colon separators.

```python
user_profile = {
    "id": 1042,
    "role": "Administrator",
    "verified": True
}
```

### 🧬 Set Types
Used for mathematical set tracking and group operations.

* **`set`:** An unordered collection of unique elements. It does not allow duplicates and cannot be indexed. Defined using curly braces (`{}`) or the `set()` builder.

```python
unique_tags = {"python", "data", "coding", "python"}  # The duplicate "python" is dropped automatically
```

### 🚫 Null/Special Type
Used to mark missing values.

* **`NoneType` (`None`):** A unique data type containing exactly one value: `None`. It signifies the absence of a value, empty status fields, or default unreturned function states.

```python
database_connection = None
```

```
# =====================================================================
# 1. DEMONSTRATING VARIABLE SCOPES (Global, Local, Instance, & Class)
# =====================================================================

# GLOBAL VARIABLE: Defined outside functions/classes. Accessible anywhere.
global_message = "I am a global variable!"

class Employee:
    # CLASS VARIABLE: Shared by all instances of this class.
    company_name = "TechCorp Solutions"
    
    def __init__(self, name, salary):
        # INSTANCE VARIABLES: Unique to each object created from this class.
        self.name = name          
        self.salary = salary      

    def calculate_bonus(self):
        # LOCAL VARIABLE: Defined inside a method. Only exists inside this block.
        bonus_rate = 0.10          
        return self.salary * bonus_rate

# =====================================================================
# 2. DEMONSTRATING VARIABLE DATA TYPES (Built-in Types)
# =====================================================================

# Numeric Types
age = 29                          # Integer (int)
hourly_rate = 45.50               # Floating-point number (float)
coordinates = 3 + 4j              # Complex number (complex)

# Text Sequence Type
employee_role = "Senior Engineer" # String (str)

# Boolean Type
is_full_time = True               # Boolean (bool) - True or False

# Sequence Types (Collections)
skills_list = ["Python", "SQL"]   # List: Ordered, changeable, allows duplicates.
shift_hours = (9, 17)             # Tuple: Ordered, unchangeable, allows duplicates.

# Mapping Type
project_details = {               # Dictionary (dict): Key-value pairs.
    "title": "Cloud Migration",
    "team_size": 5
}

# Set Types
unique_tools = {"Git", "Docker"}  # Set: Unordered, unindexed, no duplicates.

# Binary & None Types
empty_value = None                # NoneType: Represents the absence of a value.


# =====================================================================
# 3. RUNNING AND PRINTING THE DEMONSTRATION
# =====================================================================
if __name__ == "__main__":
    print("--- 1. Variable Scope Demo ---")
    print(f"Global Variable: {global_message}")
    print(f"Class Variable:  {Employee.company_name}")
    
    # Instantiating objects to show instance variables
    emp1 = Employee("Alice", 80000)
    print(f"Instance Vars:   Name: {emp1.name}, Salary: ${emp1.salary}")
    print(f"Local Var Result (Bonus Calculated): ${emp1.calculate_bonus()}")
    
    print("\n--- 2. Variable Data Types Demo ---")
    print(f"Integer:         {age} (Type: {type(age).__name__})")
    print(f"Float:           {hourly_rate} (Type: {type(hourly_rate).__name__})")
    print(f"Complex:         {coordinates} (Type: {type(coordinates).__name__})")
    print(f"String:          '{employee_role}' (Type: {type(employee_role).__name__})")
    print(f"Boolean:         {is_full_time} (Type: {type(is_full_time).__name__})")
    print(f"List:            {skills_list} (Type: {type(skills_list).__name__})")
    print(f"Tuple:           {shift_hours} (Type: {type(shift_hours).__name__})")
    print(f"Dictionary:      {project_details} (Type: {type(project_details).__name__})")
    print(f"Set:             {unique_tools} (Type: {type(unique_tools).__name__})")
    print(f"NoneType:        {empty_value} (Type: {type(empty_value).__name__})")
```

##  Data Type Comparison Matrix
# Python Collections Comparison

| Data Type | Ordered? | Mutable (Changeable)? | Unique Items Only? | Syntax |
| :--- | :--- | :--- | :--- | :--- |
| **list** | Yes | Yes | No | `[1, 2, 3]` |
| **tuple** | Yes | No | No | `(1, 2, 3)` |
| **dict** | Yes (Insertion order) | Yes | Yes (Keys must be unique) | `{"key": "value"}` |
| **set** | No | Yes | Yes | `{1, 2, 3}` |


# Selection Guide: When to Use Which Python Collection
<img width="1024" height="519" alt="image" src="https://github.com/user-attachments/assets/3eb68c65-4f63-409c-bf24-fdf7b3124536" />


| Data Type | Ideal Use Case Scenarios | Key Requirements Met |
| :--- | :--- | :--- |
| **`list`** | • Managing a simple sequence of similar items (e.g., unread email titles). | • The specific **order** of elements matters.<br>• Items must be **added, removed, or modified** frequently over time. |
| **`tuple`** | • Storing read-only operational configurations.<br>• Setting up coordinate pairs or constant data records. | • Data must remain **fixed and unchangeable** during execution.<br>• Protection against accidental program modifications.<br>• Need for minor **performance optimizations** or hashable dictionary keys. |
| **`dict`** | • Mapping specific entity attributes.<br>• Implementing search indexes or lookups. | • Managing complex relational data via **key-value pairs**.<br>• Querying, matching, or updating information instantly using a **custom name or identifier label** instead of an integer sequence position. |
| **`set`** | • Deduplicating data streams.<br>• Checking membership inclusion status. | • Automatically **filtering out duplicate entries**.<br>• Performing mathematical group calculations (e.g., intersections, unions, differences).<br>• Focus is strictly on whether an item **exists**, disregarding its exact storage index. |


| Data Type | Ideal Use Case Scenarios | Key Requirements Met |
| :--- | :--- | :--- |
| **`list`** | • Managing a simple sequence of similar items (e.g., unread email titles). | • The specific **order** of elements matters.<br>• Items must be **added, removed, or modified** frequently over time. |
| **`tuple`** | • Storing read-only operational configurations.<br>• Setting up coordinate pairs or constant data records. | • Data must remain **fixed and unchangeable** during execution.<br>• Protection against accidental program modifications.<br>• Need for minor **performance optimizations** or hashable dictionary keys. |
| **`dict`** | • Mapping specific entity attributes.<br>• Implementing search indexes or lookups. | • Managing complex relational data via **key-value pairs**.<br>• Querying, matching, or updating information instantly using a **custom name or identifier label** instead of an integer sequence position. |
| **`set`** | • Deduplicating data streams.<br>• Checking membership inclusion status. | • Automatically **filtering out duplicate entries**.<br>• Performing mathematical group calculations (e.g., intersections, unions, differences).<br>• Focus is strictly on whether an item **exists**, disregarding its exact storage index. |


| Type | Syntax | Characteristics | Use Case |
|------|--------|-----------------|----------|
| List | `[a, b]` | Mutable (changeable), Ordered. | A collection of items you need to modify often. |
| Tuple | `(a, b)` | Immutable (unchangeable), Ordered. | A fixed collection of items (e.g., coordinates). |
| Set | `{a, b}` | Mutable, Unordered, Unique items only. | Removing duplicates or mathematical sets. |
| Dictionary | `{"k": "v"}` | Mutable, Key-Value pairs. | Mapping unique keys to specific values. |
c. operators
d. control flow
