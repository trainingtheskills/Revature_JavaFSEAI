# Exception Handling

Exception handling in Python is a mechanism used to handle runtime errors so that a program does not terminate unexpectedly.

It allows developers to detect errors, handle them appropriately, and continue executing the program.

For example, consider a program that divides two numbers. If the user enters 0 as the denominator, Python raises a ZeroDivisionError. Exception handling allows us to handle this situation gracefully.

## 1. What is an Exception?
An exception is an error that occurs during the execution of a program and interrupts its normal flow.
```
num1 = 10
num2 = 0

result = num1 / num2

print("Result:", result)
print("Program completed")
```
## 2. Types of Common Exceptions in Python

# Common Exceptions in Python

An exception is an error that occurs during the execution of a Python program and interrupts its normal flow.

## Common Python Exceptions

| Exception | Description | Example |
|---|---|---|
| `ZeroDivisionError` | Dividing a number by zero | `10 / 0` |
| `ValueError` | Invalid value conversion | `int("hello")` |
| `TypeError` | Operation on incompatible data types | `"10" + 5` |
| `NameError` | Using an undefined variable | `print(total)` |
| `IndexError` | Accessing an invalid list index | `list[10]` |
| `KeyError` | Accessing a dictionary key that doesn't exist | `student["age"]` |
| `FileNotFoundError` | Trying to open a file that doesn't exist | `open("abc.txt")` |
| `AttributeError` | Accessing an unavailable object attribute | `"hello".append()` |


## 3. Exception Handling Keywords

Python provides five important keywords for exception handling.

| Keyword | Description |
|---|---|
| `try` | Contains code that might generate an exception. |
| `except` | Handles the exception if it occurs. |
| `else` | Executes when no exception occurs in the try block. |
| `finally` | Executes whether an exception occurs or not. |
| `raise` | Explicitly generates an exception. |

