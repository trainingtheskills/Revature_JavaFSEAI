# Python Programming Assignment – Conditional Statements and Decision Making

**Assignment Title:** Implementing Python Conditional Statements Using Real-World Scenarios

**Level:** Beginner

**Objective:** Demonstrate your understanding of Python conditional statements by implementing different decision-making scenarios using `if`, `if-else`, `if-elif-else`, nested conditions, logical operators, membership operators and conditional expressions.

---

## 1. Problem Statement

Develop a Python program named `python_if_assignment.py` that demonstrates the practical implementation of 11 different forms of conditional statements.

For each form, create a real-world scenario, accept or define appropriate input values, apply the required condition and display the result.

## 2. Assignment Requirements

Implement the following 11 tasks.

### Task 1: Simple if Statement – Age Eligibility

Write a Python program to check whether a person is eligible to vote.

**Requirements:**
- Accept the person's age.
- Check whether the age is 18 or above.
- Display an appropriate message if the person is eligible.
- Do not display an eligibility message when the condition is false.

### Task 2: if-else Statement – Temperature Classification

Write a program to classify the temperature.

**Requirements:**
- Accept the temperature in Celsius.
- If the temperature is above 30, display `Hot Weather`.
- Otherwise, display `Normal Weather`.

### Task 3: if-elif-else Statement – Student Grading System

Write a program to calculate a student's grade based on marks.

**Requirements:**

| Marks | Grade |
|---|---|
| 90–100 | A |
| 80–89 | B |
| 70–79 | C |
| 60–69 | D |
| 40–59 | E |
| Below 40 | Fail |

- Accept marks from the user.
- Validate that marks are between 0 and 100.
- Use `if-elif-else` to determine the grade.
- Display the result.

### Task 4: Nested if Statement – Movie Ticket Eligibility

Write a program to determine whether a customer can book a movie ticket.

**Requirements:**
- Accept the customer's age.
- Check whether the customer is 18 or above.
- If eligible, ask whether the customer has a valid ID.
- Allow ticket booking only if both conditions are satisfied.
- Display an appropriate message.

### Task 5: Logical Operators – Employee Bonus Eligibility

Write a program to determine whether an employee is eligible for a performance bonus.

**Requirements:**
- Accept employee experience in years.
- Accept the performance rating.
- An employee qualifies if experience is at least 3 years AND performance rating is at least 4.
- Also demonstrate the use of `or` and `not` in separate conditions.
- Display the eligibility result.

### Task 6: Membership Operators – Product Availability

Write a program to check product availability in an online shopping application.

Use the following product list:

```python
products = ["Laptop", "Mouse", "Keyboard", "Monitor", "Headphones"]
```

**Requirements:**
- Accept a product name from the user.
- Use the `in` operator to check whether the product exists.
- Use the `not in` operator to identify unavailable products.
- Display the appropriate message.

### Task 7: Truthy and Falsy Values – Shopping Cart Validation

Write a program to check whether a shopping cart contains products.

**Requirements:**
- Create an empty shopping cart using a list.
- Check whether the cart contains products.
- Display `Cart is Empty` when no products are available.
- Add at least two products to the cart.
- Check the cart again and display its contents.
- Demonstrate how empty and non-empty collections behave in conditional statements.

### Task 8: Checking None – Customer Information Validation

Write a program to check whether customer information has been provided.

**Requirements:**
- Create a variable named `customer_name` and initially assign `None`.
- Use `is None` to check whether the customer name is missing.
- Display an appropriate message.
- Assign a valid customer name.
- Check again using `is not None`.

### Task 9: Chained Comparison – Examination Score Validation

Write a program to validate examination scores.

**Requirements:**
- Accept a student's examination score.
- Use chained comparison to check whether the score is between 0 and 100.
- Use the expression `0 <= score <= 100`.
- Display whether the score is valid or invalid.

### Task 10: Ternary Conditional Expression – Pass or Fail

Write a program to determine whether a student has passed an examination.

**Requirements:**
- Accept marks from the user.
- Use a ternary conditional expression.
- If marks are 40 or above, assign `Pass`.
- Otherwise, assign `Fail`.
- Display the result.

Use the following syntax as a reference:

```python
result = "Pass" if marks >= 40 else "Fail"
```

### Task 11: Common Mistake – Assignment vs Comparison

Write a program demonstrating the difference between assignment and comparison operators.

**Requirements:**
- Create a variable named `available_stock`.
- Assign a value to the variable using `=`.
- Use `==` to compare the stock against zero.
- Display whether the product is out of stock.
- Demonstrate why using `=` instead of `==` inside a condition is incorrect.

## 3. Technical Requirements

1. Use Python 3.x.
2. Create a file named `python_if_assignment.py`.
3. Use meaningful variable names.
4. Use appropriate input and output statements.
5. Convert user input into suitable data types.
6. Use comments to identify each task.
7. Implement all 11 conditional statement forms.
8. Handle invalid inputs wherever required.
9. Do not use external libraries.
10. Do not use classes or objects for this assignment.

## 4. Expected Project Structure

```text
Python_Conditional_Assignment/
│
└── python_if_assignment.py
```

## 5. Submission Requirements

1. Submit the complete Python source file.
2. Submit screenshots showing successful execution of all 11 tasks.
3. Provide sample input and output for each task.
4. Write a short explanation of each conditional statement.
5. Be prepared to explain the difference between the different conditional forms during the review session.

## 6. Evaluation Criteria

| Evaluation Area | Weightage |
|---|---:|
| Simple if and if-else | 10% |
| if-elif-else | 10% |
| Nested if | 10% |
| Logical operators | 10% |
| Membership operators | 10% |
| Truthy and falsy values | 10% |
| None checking | 10% |
| Chained comparison | 10% |
| Ternary expression | 10% |
| Assignment vs comparison | 10% |
| **Total** | **100%** |

**Important:** Write and execute the program independently. Ensure that all 11 tasks work correctly and be prepared to explain why each conditional statement is appropriate for its respective scenario.


# AI-Solution
## Task 1: Simple if Statement – Age Eligibility
```
Act as an experienced Python developer.

Write a beginner-friendly Python program named age_eligibility.py to check voting eligibility using a simple if statement.

Requirements:

Accept the person's age using input().

Convert the age into an integer.

Check whether age >= 18 using a simple if statement.

Display You are eligible to vote. only when the condition is true.

Do not use else, elif, loops, functions, or classes.

Add comments to explain the code.

Generate the complete Python code and show sample outputs for ages 25 and 16. Briefly explain how the if statement works.
```
