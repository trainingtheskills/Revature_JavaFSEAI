# Week 10   -     AI EngineeringFoundations (Python &Fast API)

## DAY 1- Python Fundamentals 

### 1. Object-oriented programming concepts
## Introduction to Object-Oriented Programming (OOP)

Object-Oriented Programming (OOP) is a programming approach that helps developers design and build modular, reusable, maintainable, and scalable applications.

It organizes a program around **classes and objects**, where classes act as blueprints and objects represent real-world entities.

An object contains **attributes** that define its properties or data and **methods** that represent the actions or behaviors it can perform.

### Key Characteristics of OOP

- **Classes and Objects:** Organize code into logical structures using classes and their objects.
- **Abstraction:** is an OOP concept that hides the internal implementation details and exposes only the essential features                      of an object. It simplifies application development by allowing users to interact with an object through a                     well-defined interface without needing to understand how its internal functionality works.
- **Encapsulation:** Combines data and methods within a class while controlling access to data.
- **Inheritance:** Promotes code reusability by allowing one class to acquire the properties and behaviors of another class.
- **Polymorphism:** Enables the same method or interface to exhibit different behaviors depending on the object.
<img width="674" height="342" alt="image" src="https://github.com/user-attachments/assets/6fc4c1af-023c-496b-9005-ae0e6c5d6e68" />

##### A class is a collection of objects. Classes are blueprints for creating objects. A class defines a set of attributes and methods that the created objects (instances) can have.  

##### An Object is an instance of a Class. It represents a specific implementation of the class and holds its own data. An object consists of:

**State**: represented by the attributes and reflects the properties of an object.
**Behavior**: represented by the methods of an object and reflects the response of an object to other objects.
**Identity** : gives a unique name to an object and enables one object to interact with other objects.

### Lab: OOPS-Class
```
class Employee:
    # Class attribute shared by all employees
    company_name = "Tech Solutions Inc."

    def __init__(self, emp_id: int, name: str, position: str, salary: float):
        # Instance attributes
        self.emp_id = emp_id
        self.name = name
        self.position = position
        self.salary = salary

    def get_details(self) -> str:
        """Returns a formatted string with employee details."""
        return f"ID: {self.emp_id} | Name: {self.name} | Position: {self.position} | Salary: ${self.salary:,.2f}"

    def give_raise(self, amount: float) -> None:
        """Increases the employee's salary by a given amount."""
        if amount > 0:
            self.salary += amount
            print(f"New salary for {self.name}: ${self.salary:,.2f}")
        else:
            print("Raise amount must be positive.")

    def __str__(self) -> str:
        """Provides a user-friendly string representation of the object."""
        return f"{self.name} ({self.position})"


# --- Example Usage ---

# 1. Creating objects (instances of the Employee class)
emp1 = Employee(emp_id=101, name="Alice Smith", position="Software Engineer", salary=85000.0)
emp2 = Employee(emp_id=102, name="Bob Jones", position="Project Manager", salary=95000.0)

# 2. Accessing attributes and methods
print(emp1.get_details())  # Output: ID: 101 | Name: Alice Smith | Position: Software Engineer | Salary: $85,000.00
print(emp2)                # Output: Bob Jones (Project Manager)

# 3. Giving a raise
emp1.give_raise(5000)      # Output: New salary for Alice Smith: $90,000.00
```
##### Run : python oops-class.py
<img width="589" height="75" alt="image" src="https://github.com/user-attachments/assets/cf20d112-b461-43e2-a10b-84e97e6cc18f" />

#### Abstraction
Abstraction is the OOP principle of hiding complex implementation details and exposing only the essential features to the user.

Real-world analogy: When you drive a car, you press the gas pedal to move forward. You don't need to understand fuel injection, spark plugs, or thermodynamics to make the car go. The complexity is abstracted away behind a simple interface.

### Lab: oops- Abstration
```
from abc import ABC, abstractmethod

# 1. Abstract Base Class (Blueprint)
class Employee(ABC):
    def __init__(self, name: str, base_salary: float):
        self.name = name
        self.base_salary = base_salary

    # Abstract method: Forces subclasses to implement their own pay logic,
    # while hiding the complex calculation details from the user.
    @abstractmethod
    def calculate_pay(self) -> float:
        pass

    # Concrete method: Shared interface
    def get_payslip(self) -> str:
        return f"Payslip for {self.name}: ${self.calculate_pay():,.2f}"


# 2. Subclass implementing the hidden details for Full-Time Employees
class FullTimeEmployee(Employee):
    def calculate_pay(self) -> float:
        # Complex hidden logic: taxes, benefits, standard deductions
        tax_deduction = self.base_salary * 0.20
        health_insurance = 300.0
        return self.base_salary - tax_deduction - health_insurance


# 3. Subclass implementing hidden details for Interns
class Intern(Employee):
    def calculate_pay(self) -> float:
        # Interns have a different tax structure/no health deduction
        return self.base_salary - (self.base_salary * 0.05)


# --- Example Usage ---

# emp = Employee("Alice", 50000)  # Error! Cannot instantiate abstract class directly.

ft_emp = FullTimeEmployee("Alice Smith", 80000.0)
intern = Intern("Bob Jones", 20000.0)

# The user just calls .get_payslip() without worrying about how taxes/deductions are computed!
print(ft_emp.get_payslip())  
# Output: Payslip for Alice Smith: $63,700.00

print(intern.get_payslip())  
# Output: Payslip for Bob Jones: $19,000.00
```

##### Run - 
<img width="624" height="46" alt="image" src="https://github.com/user-attachments/assets/c62ae277-eaef-4fb7-845a-f47647a37f51" />

#####  Encapsulation
Encapsulation is the OOP principle of bundling data (attributes) and methods that work on that data into a single unit (a class), while restricting direct access to some of the object's internal components.

Real-world analogy: Think of a capsule of medicine or a bank account. When you use an ATM, you don't reach directly into the bank's vault to change your balance. Instead, you interact with a controlled interface (the ATM screen/buttons) using specific methods like deposit() or withdraw(), which protect the data from invalid or unauthorized changes.

Encapsulation in the Employee Case
In our Employee system, we might want to protect an employee's salary so that other parts of the program cannot accidentally or maliciously set it to a negative number or change it without authorization.

Instead of letting anyone write emp.salary = -50000, we hide the salary attribute and force them to use a controlled method (a setter and a getter).
<img width="947" height="284" alt="image" src="https://github.com/user-attachments/assets/55be22fa-3ce6-4da3-9ecb-90d568671248" />


### Lab: Encapsulation
<img width="652" height="59" alt="image" src="https://github.com/user-attachments/assets/b8d51cd0-a444-4eee-bf95-7147d636f4d9" />

### 2. Python syntax andlanguage features

### 3. Variables, datatypes, operators, andcontrol flow

