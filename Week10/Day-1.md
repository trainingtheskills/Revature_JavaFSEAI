# Week 10   -     AI EngineeringFoundations (Python &Fast API)

## Python Fundamentals 

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

## Lab: OOPS-Class
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
#### Run : python oops-class.py
<img width="589" height="75" alt="image" src="https://github.com/user-attachments/assets/cf20d112-b461-43e2-a10b-84e97e6cc18f" />

### 2. Python syntax andlanguage features

### 3. Variables, datatypes, operators, andcontrol flow

