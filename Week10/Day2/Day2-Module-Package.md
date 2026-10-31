# What are Modules and Packages in Python, and Why Do We Use Them?
When developing a Python application, we may need to write hundreds or thousands of lines of code. Writing all the code in a single Python file makes the application difficult to understand, maintain, debug, and extend.

Python provides Modules and Packages to organize code into smaller, reusable components.

Let's understand both concepts with a real-world example.
## 1. What is a Module in Python?
A Module is a Python file (.py) that contains reusable code, such as variables, functions, classes, and statements.

In simple words, a module is a file in which we write specific functionality that can be reused in other Python programs.

Real-world example:

Suppose we are developing an Employee Management System. We need to perform different operations:
- Register employees.
- Calculate employee salaries.
- Calculate attendance.
- Generate employee reports.

  ## Program
  ### Create salary module
  ```
  # salary.py

  def calculate_salary(basic_salary, allowance):
    total_salary = basic_salary + allowance
    return total_salary
  ```

  ``` #main.py
  # main.py

import salary
result = salary.calculate_salary(30000, 5000)
print("Employee Salary:", result)
  ```
