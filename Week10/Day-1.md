# Week 10   -     AI EngineeringFoundations (Python &Fast API)

## DAY 1- Python Fundamentals 

## 1. Object-oriented programming concepts
### Introduction to Object-Oriented Programming (OOP)

Object-Oriented Programming (OOP) is a programming approach that helps developers design and build modular, reusable, maintainable, and scalable applications.

It organizes a program around **classes and objects**, where classes act as blueprints and objects represent real-world entities.

An object contains **attributes** that define its properties or data and **methods** that represent the actions or behaviors it can perform.

### Key Characteristics of OOP

- **Classes and Objects:** Organize code into logical structures using classes and their objects.
- **Abstraction:** is an OOP concept that hides the internal implementation details and exposes only the essential features                      of an object. It simplifies application development by allowing users to interact with an object through a                     well-defined interface without needing to understand how its internal functionality works.
- **Encapsulation:** Combines data and methods within a class while controlling access to data.
- **Inheritance:** Promotes code reusability by allowing one class to acquire the properties and behaviors of another class.
- **Polymorphism:** Enables the same method or interface to exhibit different behaviors depending on the object.


### A class is a collection of objects. Classes are blueprints for creating objects. A class defines a set of attributes and methods that the created objects (instances) can have.  

#### An Object is an instance of a Class. It represents a specific implementation of the class and holds its own data. An object consists of:

**State**: represented by the attributes and reflects the properties of an object.
**Behavior**: represented by the methods of an object and reflects the response of an object to other objects.
**Identity** : gives a unique name to an object and enables one object to interact with other objects.

##### Lab: OOPS-Class
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

##### Lab: oops- Abstration
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

####  Encapsulation
Encapsulation is the OOP principle of bundling data (attributes) and methods that work on that data into a single unit (a class), while restricting direct access to some of the object's internal components.

Real-world analogy: Think of a capsule of medicine or a bank account. When you use an ATM, you don't reach directly into the bank's vault to change your balance. Instead, you interact with a controlled interface (the ATM screen/buttons) using specific methods like deposit() or withdraw(), which protect the data from invalid or unauthorized changes.

Encapsulation in the Employee Case
In our Employee system, we might want to protect an employee's salary so that other parts of the program cannot accidentally or maliciously set it to a negative number or change it without authorization.

Instead of letting anyone write emp.salary = -50000, we hide the salary attribute and force them to use a controlled method (a setter and a getter).
##### How it Works: Encapsulation in Python

In Python, encapsulation is implemented using **access modifiers** (conventions) to indicate how "private" a member is:

| Access Level | Convention | Description |
| :--- | :--- | :--- |
| **Public** | `name` | Accessible from anywhere. |
| **Protected** | `_name` | Suggests the variable should only be accessed within the class and its subclasses. |
| **Private** | `__name` | Triggers **Name Mangling**, making it harder (but not impossible) to access from outside the class. |

> [!IMPORTANT]
> **Name Mangling:** When you prefix an attribute with two underscores (e.g., `__balance`), Python internally renames it to `_ClassName__attributeName`. This is not strictly "private" (as you could still access it via the mangled name), but it serves to prevent accidental name clashes in subclasses and discourages direct external access.

##### Lab: Encapsulation
```
class Employee:
    def __init__(self, name: str, salary: float):
        self.name = name
        self.__salary = salary  # Private attribute (hidden from direct outside access)

    # Getter method: Safely view the private salary
    def get_salary(self) -> float:
        return self.__salary

    # Setter method: Safely update the salary with validation rules
    def set_salary(self, new_salary: float) -> None:
        if new_salary >= 0:
            self.__salary = new_salary
            print(f"Salary updated successfully to ${self.__salary:,.2f}")
        else:
            print("Error: Salary cannot be negative!")


# --- Example Usage ---

emp = Employee("Alice Smith", 85000.0)

# 1. Trying to access the private attribute directly will fail:
# print(emp.__salary)  # Raises an AttributeError!

# 2. Using the getter works perfectly:
print(f"Current Salary: ${emp.get_salary():,.2f}")  
# Output: Current Salary: $85,000.00

# 3. Trying to set an invalid salary via the setter:
emp.set_salary(-1000)  
# Output: Error: Salary cannot be negative!

# 4. Updating with a valid salary:
emp.set_salary(92000)  
# Output: Salary updated successfully to $92,000.00
```
<img width="947" height="284" alt="image" src="https://github.com/user-attachments/assets/55be22fa-3ce6-4da3-9ecb-90d568671248" />

##### Run 
<img width="652" height="59" alt="image" src="https://github.com/user-attachments/assets/b8d51cd0-a444-4eee-bf95-7147d636f4d9" />

#### Inheritance
Inheritance is the OOP principle that allows a new class (subclass or child class) to adopt the attributes and methods of an existing class (superclass or parent class). This promotes code reuse so you don't have to rewrite the same logic repeatedly.

Real-world analogy: A Manager is a specialized type of Employee. They share general employee traits (like a name and salary), but they also have unique traits (like a department or team size) and specific behaviors.
**Key Benefits of Inheritance:**

Code Reusability: Common attributes and methods (name, salary) only need to be written once in the parent class.

Method Overriding: Subclasses can customize or extend parent behaviors to fit their specific needs.

Hierarchical Organization: Reflects real-world relationships clearly in your code.
#### Lab: Inheritance
```
class Employee:
    def __init__(self, name: str, salary: float):
        self.name = name
        self.salary = salary

    def get_details(self) -> str:
        """Returns basic employee details."""
        return f"Name: {self.name} | Salary: ${self.salary:,.2f}"


# 1. Manager inherits from Employee
class Manager(Employee):
    def __init__(self, name: str, salary: float, department: str):
        # super() calls the constructor of the parent (Employee) class
        super().__init__(name, salary)
        self.department = department

    # Method Overriding: Customizing the parent's method for a Manager
    def get_details(self) -> str:
        return f"Manager: {self.name} | Department: {self.department} | Salary: ${self.salary:,.2f}"


# 2. Developer inherits from Employee
class Developer(Employee):
    def __init__(self, name: str, salary: float, programming_language: str):
        super().__init__(name, salary)
        self.programming_language = programming_language

    def write_code(self) -> str:
        """A unique method specific to developers."""
        return f"{self.name} is writing code in {self.programming_language}."


# --- Example Usage ---

# Creating instances of child classes
mgr = Manager("Alice Smith", 110000.0, "Engineering")
dev = Developer("Bob Jones", 95000.0, "Python")

# 1. Manager uses the overridden get_details method
print(mgr.get_details())  
# Output: Manager: Alice Smith | Department: Engineering | Salary: $110,000.00

# 2. Developer inherits the base get_details method
print(dev.get_details())  
# Output: Name: Bob Jones | Salary: $95,000.00

# 3. Developer uses its own unique method
print(dev.write_code())   
# Output: Bob Jones is writing code in Python.
```

#### Run :
<img width="606" height="57" alt="image" src="https://github.com/user-attachments/assets/479ef4a7-4bb3-4cee-aee8-09ba4c494466" />

#### Polymorphism
Polymorphism comes from Greek meaning "many forms." In object-oriented programming, it refers to the ability of different classes to respond to the same method call in their own unique way.

Real-world analogy: Imagine a button labeled "Press". If you press the button on a coffee machine, it brews coffee. If you press it on a microwave, it starts cooking. The action (press) is the same, but the behavior depends entirely on what object is receiving it.
**Polymorphism in the Employee Case**

Suppose every employee in our company has a method called perform_duties(). While a general employee might do standard office work, a Developer writes code, and a Manager leads meetings.

Because of polymorphism, we can loop through a list of mixed employees and call .perform_duties() on each one without needing to check what specific type of employee they are!

**Key Benefits of Polymorphism:**
Flexibility & Extensibility: You can add new employee types (e.g., Designer, Salesperson) later without changing the code that processes them.

Simplified Code: Functions or loops can interact with objects uniformly through a shared interface, ignoring internal implementation differences.

##### Lab: Polymorphism 
```
class Employee:
    def __init__(self, name: str):
        self.name = name

    def perform_duties(self) -> str:
        return f"{self.name} is performing general office tasks."


class Developer(Employee):
    def perform_duties(self) -> str:
        # Overriding the method with developer-specific behavior
        return f"{self.name} is writing and debugging code."


class Manager(Employee):
    def perform_duties(self) -> str:
        # Overriding the method with manager-specific behavior
        return f"{self.name} is leading team syncs and planning projects."


# --- Example Usage ---

# 1. Creating a list of different employee objects
employees = [
    Employee("Alice"),
    Developer("Bob"),
    Manager("Charlie")
]

# 2. Demonstrating Polymorphism: 
# We call the exact same method (.perform_duties()) on every object, 
# and each object responds according to its own class definition.
for emp in employees:
    print(emp.perform_duties())

# Output:
# Alice is performing general office tasks.
# Bob is writing and debugging code.
# Charlie is leading team syncs and planning projects.
```

## Dunder Methods 
What are Dunder Methods?
Dunder stands for Double Underscore. These are special methods in Python that start and end with two underscores (like __init__, __str__, or __add__).

They are also commonly known as magic methods or special methods. They aren't meant to be called directly by your code (e.g., you rarely write emp.__str__()); instead, Python calls them automatically behind the scenes when you use certain built-in functions, operators, or syntax.

```
# # Dunder stands for Double Underscore.
# These are special methods in Python that start and end with
# two underscores (like __init__, __str__, or __add__).
#
# They are also commonly known as magic methods or special methods.
#
# They are not generally called directly by your code.
# For example, you rarely write emp.__str__().
#
# Instead, Python calls them automatically behind the scenes
# when you use certain built-in functions, operators, or syntax.
class Employee:
    def __init__(self, name: str, salary: float):
        self.name = name
        self.salary = salary

    # 1. __str__: Controls how the object looks when printed or converted to a string
    def __str__(self) -> str:
        return f"{self.name} (Salary: ${self.salary:,.2f})"

    # 2. __repr__: Official string representation (great for debugging/logging)
    def __repr__(self) -> str:
        return f"Employee(name='{self.name}', salary={self.salary})"

    # 3. __eq__: Defines how two objects are compared using the '==' operator
    def __eq__(self, other) -> bool:
        if isinstance(other, Employee):
            return self.name == other.name and self.salary == other.salary
        return False

    # 4. __add__: Allows you to use the '+' operator to combine two objects
    def __add__(self, other) -> float:
        if isinstance(other, Employee):
            return self.salary + other.salary
        raise TypeError("Can only add salaries of two Employee objects")


# --- Example Usage ---

emp1 = Employee("Alice Smith", 85000.0)
emp2 = Employee("Bob Jones", 95000.0)
emp3 = Employee("Alice Smith", 85000.0)

# 1. Using __str__ (triggered automatically by print() or str())
print(emp1)  
# Output: Alice Smith (Salary: $85,000.00)

# 2. Using __eq__ (triggered by '==')
print(emp1 == emp3)  
# Output: True (because name and salary match)

# 3. Using __add__ (triggered by '+')
total_payroll = emp1 + emp2
print(f"Total Combined Salary: ${total_payroll:,.2f}")  
# Output: Total Combined Salary: $180,000.00
```
## Run : <img width="587" height="59" alt="image" src="https://github.com/user-attachments/assets/36bca202-198b-4066-a522-b38dd613dbf3" />

### 2. Python syntax andlanguage features

### 3. Variables, datatypes, operators, andcontrol flow

