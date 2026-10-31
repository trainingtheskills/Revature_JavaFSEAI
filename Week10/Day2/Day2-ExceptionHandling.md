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

<img width="1024" height="718" alt="image" src="https://github.com/user-attachments/assets/da48136d-ecf1-413d-bef7-7d17738c26f6" />


## Program 1: Accept two number and handle exception
```
try:
    num1 = int(input("Enter first number: "))
    num2 = int(input("Enter second number: "))

    result = num1 / num2

except ZeroDivisionError:
    print("Cannot divide by zero.")

except ValueError:
    print("Please enter valid numbers.")

else:
    print("Division successful!")
    print("Result:", result)
```
## Program 2: Raise an exception
```
try:
    num1 = int(input("Enter first number: "))
    num2 = int(input("Enter second number: "))

    result = num1 / num2

except ZeroDivisionError:
    print("Cannot divide by zero.")

except ValueError:
    print("Please enter valid numbers.")

else:
    print("Division successful!")
    print("Result:", result)
```
# Program 3: Use Finally
```
try:
    num1 = int(input("Enter first number: "))
    num2 = int(input("Enter second number: "))

    result = num1 / num2

    print("Result:", result)

except ZeroDivisionError:
    print("Cannot divide by zero.")

except ValueError:
    print("Invalid input.")

finally:
    print("Thank you for using the application.")
```
# Program 4: Banking App 
```
balance = 10000

try:
    amount = float(input("Enter withdrawal amount: "))

    if amount <= 0:
        raise ValueError("Withdrawal amount must be positive.")

    if amount > balance:
        raise ValueError("Insufficient balance.")

    balance = balance - amount

except ValueError as e:
    print("Transaction failed:", e)

else:
    print("Withdrawal successful!")
    print("Remaining balance:", balance)

finally:
    print("Thank you for banking with us.")
```


# Hands-on Assignments for Participants
Try implementing the following programs.
Assignment	Requirements	Exception to handle
1. Calculator	Accept two numbers and perform division                     -  ZeroDivisionError
2. Age Validator	Accept age and validate numeric input.	                -  ValueError
3. Student Marks	Accept marks and validate that they are between 0 and 100. -ValueError
4. List Access	Accept an index and display the corresponding list element.- IndexError
5. File Reader	Open and read a text file.	                               - FileNotFoundError
6. Bank Withdrawal	Validate withdrawal amount and account balance.	       -      ValueError

