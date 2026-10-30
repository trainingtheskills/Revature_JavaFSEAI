# Python Fundamentals – Hands-on Assignment

**Assignment Title:** Online Shopping and Order Management System  
**Level:** Beginner  
**Objective:** Apply Python syntax, variables, data types, operators, control flow, and object-oriented programming concepts to develop a simple shopping application.

## 1. Problem Statement

Develop a menu-driven Online Shopping and Order Management System using Python.

The application should allow customers to browse products, add products to their shopping cart, calculate the order amount, apply discounts, and generate an order summary.

The application should demonstrate the use of Python programming fundamentals and object-oriented programming concepts.

## 2. Assignment Requirements

### Task 1: Python Syntax and Language Features

1. Display the application name and welcome message.
2. Accept customer details using the `input()` function.
3. Use appropriate comments and documentation strings.
4. Demonstrate string formatting using f-strings.
5. Convert user input into appropriate data types.
6. Use meaningful variable and function names.

### Task 2: Variables, Data Types and Operators

Create variables to store:

- Customer ID
- Customer Name
- Product ID
- Product Name
- Product Price
- Product Quantity
- Discount Percentage
- Delivery Charges

Requirements:

1. Use appropriate Python data types.
2. Calculate the total price of products.
3. Calculate the discount amount.
4. Calculate the delivery charges.
5. Calculate the final payable amount.
6. Use arithmetic operators for calculations.
7. Use comparison operators to validate product price and quantity.
8. Use logical operators to check discount eligibility.
9. Demonstrate assignment operators.

**Business Rules:**

- Orders above ₹2,000 receive a 10% discount.
- Orders above ₹5,000 receive a 15% discount.
- Orders below ₹500 have delivery charges of ₹50.
- Orders of ₹500 or more have free delivery.

### Task 3: Conditional Statements and Control Flow

Implement the following requirements:

**A. Product Availability**

- Check whether the requested product is available.
- Display an appropriate message if the product is out of stock.

**B. Discount Calculation**

- Apply a 15% discount for orders above ₹5,000.
- Apply a 10% discount for orders above ₹2,000 and up to ₹5,000.
- No discount for other orders.

**C. Delivery Calculation**

- Apply ₹50 delivery charges for orders below ₹500.
- Provide free delivery for orders of ₹500 or more.

**D. Menu-Driven Application**

Display the following menu:

1. View Products
2. Add Product to Cart
3. View Shopping Cart
4. Calculate Order Amount
5. Generate Order Summary
6. Exit

Use appropriate conditional statements and loops to implement the menu.

### Task 4: Object-Oriented Programming Concepts

Implement the application using classes and objects.

**A. Class and Object**

Create the following classes:

- `Product`
- `Customer`
- `ShoppingCart`
- `Order`

Create multiple product and customer objects.

**B. Constructor**

- Implement the `__init__()` method in each class.
- Initialize object attributes using constructor parameters.

**C. Encapsulation**

- Use private attributes for product price and available stock.
- Implement getter and setter methods.
- Validate price and stock values before updating them.

**D. Inheritance**

- Create a class named `PremiumCustomer` that inherits from `Customer`.
- Provide additional benefits to premium customers, such as an extra discount.

**E. Polymorphism**

- Implement a method named `calculate_discount()` in the customer-related classes.
- Override the method in `PremiumCustomer` to provide a different discount calculation.

**F. Abstraction**

- Create an abstract class named `Payment`.
- Define an abstract method named `process_payment()`.
- Create concrete payment classes such as `UPIPayment` and `CardPayment` that implement this method.

## 3. Sample Product Catalogue

Use the following products for testing:

| Product ID | Product Name | Price | Available Stock |
|---|---|---:|---:|
| P101 | Laptop | ₹55,000 | 10 |
| P102 | Wireless Mouse | ₹800 | 25 |
| P103 | Keyboard | ₹1,500 | 15 |
| P104 | Monitor | ₹12,000 | 8 |
| P105 | Headphones | ₹2,500 | 20 |

## 4. Expected Application Behaviour

Your application should:

1. Accept customer information.
2. Display available products.
3. Allow customers to add products to their shopping cart.
4. Validate product availability and quantity.
5. Calculate the cart total.
6. Apply applicable discounts.
7. Calculate delivery charges.
8. Generate an order summary.
9. Allow customers to select a payment method.
10. Display payment status.

## 5. Technical Guidelines

- Use Python 3.x.
- Use meaningful class, method, and variable names.
- Follow Python naming conventions and indentation rules.
- Divide the application into logical methods.
- Use loops and conditional statements appropriately.
- Implement all required OOP concepts.
- Handle invalid input and exceptions wherever required.
- Do not use external libraries.

## 6. Submission Requirements

1. Complete Python source code (`.py` files).
2. Screenshot of application execution.
3. Sample input and output.
4. Short explanation of the classes and OOP concepts implemented.

## 7. Evaluation Criteria

| Evaluation Area | Weightage |
|---|---:|
| Python syntax and language features | 15% |
| Variables, data types and operators | 15% |
| Conditional statements and loops | 20% |
| Classes, objects and constructors | 15% |
| Encapsulation, inheritance and polymorphism | 20% |
| Abstraction, validation and code organization | 15% |
| **Total** | **100%** |

**Important:** Write clean, readable, modular, and reusable Python code. Be prepared to explain your implementation and demonstrate the complete shopping workflow during the review session.
