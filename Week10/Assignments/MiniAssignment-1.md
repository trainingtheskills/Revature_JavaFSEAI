
# Python Fundamentals – Topic-wise Mini Assignments
## Case Study: Hospital and Healthcare Management System

**Level:** Beginner  
**Objective:** Practise Python syntax, variables, data types, operators, control flow, and Object-Oriented Programming (OOP) through healthcare business scenarios.

Each assignment is designed to take approximately 5–10 minutes.

---

## 1. Python Syntax and Language Features

### Assignment 1.1: Hospital Welcome Message
Write a Python program to display a welcome message for patients visiting a hospital.

### Assignment 1.2: Hospital Information
Display the hospital name, address, contact number, and available departments.

### Assignment 1.3: Patient Details
Display patient ID, name, age, and department using print statements.

### Assignment 1.4: Comments in Python
Add single-line and multiple-line comments explaining the purpose of a patient registration program.

### Assignment 1.5: String Formatting
Display a patient's name, appointment number, and consultation fee using an f-string.

---

## 2. Variables and Data Types

### Assignment 2.1: Patient Information
Create variables to store patient ID, name, age, and registration status.

### Assignment 2.2: Doctor Information
Store doctor name, specialization, consultation fee, and availability using appropriate data types.

### Assignment 2.3: Data Type Identification
Create variables using `int`, `float`, `str`, and `bool`. Display their data types using `type()`.

### Assignment 2.4: Fee Conversion
Convert a consultation fee from string to float and calculate the final amount.

### Assignment 2.5: Department List
Store five hospital department names in a list and display them.

### Assignment 2.6: Patient Record
Create a dictionary containing patient ID, name, age, and blood group.

### Assignment 2.7: Unique Specializations
Create a set containing doctor specializations and display unique values.

### Assignment 2.8: Hospital Configuration
Store hospital name, city, and emergency contact number in a tuple.

---

## 3. Python Operators

### Assignment 3.1: Annual Salary
Calculate the annual salary of a doctor based on monthly salary.

### Assignment 3.2: Consultation Billing
Calculate the total consultation fee for multiple patients.

### Assignment 3.3: Medical Discount
Calculate a 10% discount on a patient's medical bill.

### Assignment 3.4: Age Comparison
Compare the ages of two patients using comparison operators.

### Assignment 3.5: Appointment Eligibility
Check whether a patient is at least 18 years old and has a valid appointment using logical operators.

### Assignment 3.6: Bed Availability
Increase and decrease available hospital beds using assignment operators.

### Assignment 3.7: Department Search
Check whether a department exists in the hospital department list using the `in` operator.

### Assignment 3.8: Patient Object Identity
Create two variables referring to the same patient list and check their identity using `is`.

---

## 4. Conditional Statements

### Assignment 4.1: Patient Age Category
Classify a patient as Child, Adult, or Senior Citizen based on age.

### Assignment 4.2: Consultation Charges
Calculate consultation charges based on the doctor's specialization.

### Assignment 4.3: Health Report Classification
Classify a patient's temperature as Normal, Mild Fever, or High Fever using predefined sample thresholds.

### Assignment 4.4: Appointment Confirmation
Confirm an appointment based on doctor availability and available time slots.

### Assignment 4.5: Emergency Classification
Classify an emergency case as Critical, High, Medium, or Low based on a predefined training scenario.

### Assignment 4.6: Patient Login
Validate a hospital portal username and password using conditional statements.

---

## 5. Loops and Control Flow

### Assignment 5.1: Patient List
Display five patient names using a `for` loop.

### Assignment 5.2: Hospital Revenue
Calculate the total consultation revenue from five patients using a loop.

### Assignment 5.3: Doctor Directory
Display all doctors and their specializations using a `for` loop.

### Assignment 5.4: Billing Table
Generate a billing summary for a specified number of medical tests.

### Assignment 5.5: Login Attempts
Allow a patient a maximum of three login attempts using a `while` loop.

### Assignment 5.6: Hospital Menu
Create a menu to register a patient, display doctors, book an appointment, or exit the program.

### Assignment 5.7: Skip Invalid Records
Use `continue` to skip patient records with invalid patient IDs.

### Assignment 5.8: Stop Processing
Use `break` to stop processing when an emergency case is identified in a sample patient queue.

### Assignment 5.9: Future Feature
Use `pass` to create an empty method for future implementation of medical report generation.

---

## 6. Object-Oriented Programming – Classes and Objects

### Assignment 6.1: Patient Class
Create a `Patient` class with patient ID, name, and age attributes.

### Assignment 6.2: Patient Objects
Create three `Patient` objects and display their details.

### Assignment 6.3: Doctor Class
Create a `Doctor` class with doctor name, specialization, and consultation fee.

### Assignment 6.4: Appointment Class
Create an `Appointment` class with methods to book and cancel appointments.

### Assignment 6.5: Constructor
Initialize patient details using the `__init__()` constructor.

### Assignment 6.6: Instance Method
Create a method to calculate the total consultation charges.

### Assignment 6.7: Class Attribute
Maintain a common hospital name using a class variable.

---

## 7. Encapsulation

### Assignment 7.1: Private Patient ID
Create a `Patient` class with a private patient ID attribute.

### Assignment 7.2: Getter Method
Create a method to retrieve a patient's registered contact number.

### Assignment 7.3: Setter Method
Create a method to update a patient's contact number after validation.

### Assignment 7.4: Medical Billing
Prevent direct modification of a patient's outstanding bill using encapsulation.

### Assignment 7.5: Property Decorator
Implement getter and setter methods using `@property` to manage consultation fees.

---

## 8. Inheritance

### Assignment 8.1: Hospital Staff Hierarchy
Create a `HospitalStaff` parent class and a `Doctor` child class.

### Assignment 8.2: Nurse Class
Create a `Nurse` class inheriting from `HospitalStaff`.

### Assignment 8.3: Super Function
Use `super()` to initialize common staff attributes.

### Assignment 8.4: Patient Categories
Create `InPatient` and `OutPatient` classes inheriting from `Patient`.

### Assignment 8.5: Method Overriding
Override a method in `InPatient` and `OutPatient` classes to calculate different billing charges.

---

## 9. Polymorphism

### Assignment 9.1: Staff Salary
Implement the same `calculate_salary()` method in `Doctor` and `Nurse` classes.

### Assignment 9.2: Payment Processing
Create `CashPayment`, `CardPayment`, and `UPIPayment` classes with a common `process_payment()` method.

### Assignment 9.3: Medical Tests
Create `BloodTest` and `XRayTest` classes with a common `calculate_cost()` method.

### Assignment 9.4: Patient Billing
Implement `calculate_bill()` differently in `InPatient` and `OutPatient` classes.

---

## 10. Abstraction

### Assignment 10.1: Abstract Hospital Staff
Create an abstract `HospitalStaff` class with an abstract `calculate_salary()` method.

### Assignment 10.2: Payment Interface
Create an abstract `Payment` class with a `process_payment()` method.

### Assignment 10.3: Patient Management
Create an abstract `Patient` class and implement it in `InPatient` and `OutPatient` classes.

### Assignment 10.4: Notification System
Create an abstract `Notification` class and implement `EmailNotification` and `SMSNotification`.

---

## 11. Dunder (Magic) Methods

### Assignment 11.1: Constructor
Use `__init__()` to initialize a `Doctor` object.

### Assignment 11.2: String Representation
Implement `__str__()` to display patient details.

### Assignment 11.3: Object Comparison
Implement `__eq__()` to compare two `Doctor` objects based on doctor ID.

### Assignment 11.4: Addition Operator
Implement `__add__()` to add the consultation fees of two doctors.

### Assignment 11.5: Object Length
Implement `__len__()` to return the number of patients registered in a hospital.

---

## 12. Integrated Mini Projects

### Assignment 12.1: Patient Management System
Develop a program to:
- Register patients.
- Update patient details.
- Display patient records.

### Assignment 12.2: Doctor Appointment System
Develop a program to:
- Maintain doctor availability.
- Book appointments.
- Cancel appointments.

### Assignment 12.3: Hospital Billing System
Develop a program to:
- Calculate consultation charges.
- Calculate medical test costs.
- Apply discounts.
- Generate final bills.

### Assignment 12.4: Hospital Staff Management
Develop a program to:
- Register doctors and nurses.
- Calculate salaries.
- Display staff details.

### Assignment 12.5: Hospital Resource Management
Develop a program to:
- Track available beds.
- Track medical equipment.
- Display hospital resource availability.

---

## 13. Suggested Classroom Practice Schedule

| Topic | Assignments | Suggested Time |
|---|---|---:|
| Syntax and language features | 1.1–1.5 | 10 min |
| Variables and data types | 2.1–2.8 | 15 min |
| Operators | 3.1–3.8 | 15 min |
| Conditional statements | 4.1–4.6 | 15 min |
| Loops | 5.1–5.9 | 20 min |
| Classes and objects | 6.1–6.7 | 20 min |
| Encapsulation | 7.1–7.5 | 10 min |
| Inheritance | 8.1–8.5 | 10 min |
| Polymorphism | 9.1–9.4 | 10 min |
| Abstraction | 10.1–10.4 | 10 min |
| Dunder methods | 11.1–11.5 | 10 min |

**Note:** These are independent exercises, not a requirement to complete every assignment during a six-hour session. Use the remaining exercises as practice or homework.

---

## 14. Guidelines for Participants

1. Read and understand the business requirement before writing code.
2. Identify the variables, data types, operators, and control flow required.
3. Use classes and objects where appropriate.
4. Write the program independently before requesting AI assistance.
5. Execute the code and test different inputs.
6. Handle invalid inputs and boundary conditions.
7. Use AI to obtain hints, review code, and identify errors.
8. Be prepared to explain the logic and predict the program output.

**Important:** Use fictional patient data and sample scenarios only. Do not use real patient records.
