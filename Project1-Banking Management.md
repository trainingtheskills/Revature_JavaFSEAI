# Project 1: Banking Management System

**Technology:** Core Java 8, JDBC, MySQL  
**Level:** Beginner to Intermediate  
**Application type:** Console-based Java application

## 1. Business Case Study

A bank wants to develop a simple **Banking Management System** to manage customer accounts and basic banking operations.

Currently, bank employees maintain customer information, account details, and transaction records manually. This makes it difficult to track account balances, maintain transaction history, and perform banking operations accurately.

The bank wants a Java-based application that allows employees to create customer accounts and perform basic banking operations.

The application will be developed using **Java 8, JDBC, and MySQL**.

## 2. Business Requirements

The application should support the following functionalities:

| Module | Requirements |
|---|---|
| Customer Management | Add customer and view customer details |
| Account Management | Create bank account and view account details |
| Deposit | Deposit money into an account |
| Withdrawal | Withdraw money from an account |
| Balance Enquiry | Check current account balance |
| Fund Transfer | Transfer money between two accounts |
| Transaction History | View deposit, withdrawal, and transfer records |

## 3. Sample Application Workflow

The application should display the following menu:

```text
====================================
       BANKING MANAGEMENT SYSTEM
====================================

1. Create Customer
2. Create Bank Account
3. View Customer Details
4. Deposit Money
5. Withdraw Money
6. Check Account Balance
7. Transfer Money
8. View Transaction History
9. Exit

Enter your choice:
```

## 4. Sample Business Scenarios

### Scenario 1: Create Customer

- Accept customer name, email, and phone number.
- Generate a unique customer ID.
- Store customer details in the database.

### Scenario 2: Create Bank Account

- Accept customer ID.
- Select account type (Savings or Current).
- Generate a unique account number.
- Set the initial account balance to zero.
- Store account information in the database.

### Scenario 3: Deposit Money

- Accept account number.
- Accept deposit amount.
- Validate the account number.
- Add the amount to the existing balance.
- Update the account balance.
- Record the transaction.

### Scenario 4: Withdraw Money

- Accept account number.
- Accept withdrawal amount.
- Check whether sufficient balance is available.
- Deduct the amount from the account.
- Update the account balance.
- Record the transaction.

### Scenario 5: Fund Transfer

- Accept sender account number.
- Accept receiver account number.
- Accept transfer amount.
- Verify both accounts.
- Check the sender's available balance.
- Deduct money from the sender's account.
- Add money to the receiver's account.
- Record both sides of the transfer.

### Scenario 6: Transaction History

- Accept account number.
- Retrieve all transactions associated with the account.
- Display transaction date, type, and amount.

## 5. Database Design

Create a MySQL database named `banking_db`.

### Table 1: `customers`

| Column | Data Type | Description |
|---|---|---|
| customer_id | INT | Primary Key |
| customer_name | VARCHAR(100) | Customer name |
| email | VARCHAR(100) | Email |
| phone | VARCHAR(15) | Phone number |

### Table 2: `accounts`

| Column | Data Type | Description |
|---|---|---|
| account_number | BIGINT | Primary Key |
| customer_id | INT | Foreign Key |
| account_type | VARCHAR(20) | Savings / Current |
| balance | DECIMAL(12,2) | Account balance |
| created_date | DATE | Account creation date |

### Table 3: `transactions`

| Column | Data Type | Description |
|---|---|---|
| transaction_id | INT | Primary Key |
| account_number | BIGINT | Account number |
| transaction_type | VARCHAR(20) | Deposit / Withdrawal / Transfer |
| amount | DECIMAL(12,2) | Transaction amount |
| transaction_date | TIMESTAMP | Transaction date |

## 6. Java Concepts to Apply

| Java 8 Concept | Application |
|---|---|
| Classes and Objects | Customer, Account, Transaction |
| Constructors | Initialize customer and account objects |
| Encapsulation | Private fields with getters and setters |
| Inheritance | SavingsAccount and CurrentAccount |
| Polymorphism | Account-specific operations |
| Abstraction | Banking operations interface |
| Interfaces | BankService |
| Method Overloading | Different deposit methods |
| Method Overriding | Account-specific withdrawal rules |
| Collections | Store and process transaction records |
| Exception Handling | Invalid account and insufficient balance |
| Java 8 Date-Time API | Transaction date and time |
| Lambda Expressions | Filter transactions |
| Stream API | Calculate transaction summaries |

## 7. JDBC Concepts to Apply

| JDBC Concept | Application |
|---|---|
| DriverManager | Establish database connection |
| Connection | Connect to MySQL |
| Statement | Execute basic SQL statements |
| PreparedStatement | Insert and update records |
| ResultSet | Retrieve customer and account details |
| `executeQuery()` | SELECT operations |
| `executeUpdate()` | INSERT, UPDATE, DELETE |
| CallableStatement | Optional stored procedure execution |
| Transactions | Fund transfer |
| `commit()` | Confirm successful transfer |
| `rollback()` | Reverse unsuccessful transfer |

## 8. Suggested Java Project Structure

```text
BankingManagementSystem/
│
├── src/
│   └── com/banking/
│       │
│       ├── model/
│       │   ├── Customer.java
│       │   ├── Account.java
│       │   └── Transaction.java
│       │
│       ├── service/
│       │   ├── BankService.java
│       │   └── BankServiceImpl.java
│       │
│       ├── dao/
│       │   ├── CustomerDAO.java
│       │   ├── AccountDAO.java
│       │   └── TransactionDAO.java
│       │
│       ├── util/
│       │   └── DBConnection.java
│       │
│       ├── exception/
│       │   ├── InvalidAccountException.java
│       │   └── InsufficientBalanceException.java
│       │
│       └── Main.java
│
└── pom.xml
```

## 9. Development Milestones

| Milestone | Development Activity | Concepts |
|---|---|---|
| 1 | Create Customer and Account classes | Core Java, OOP |
| 2 | Implement customer and account creation | Constructors, Methods |
| 3 | Connect Java application to MySQL | JDBC |
| 4 | Implement customer and account CRUD | PreparedStatement, ResultSet |
| 5 | Implement deposit and withdrawal | Business logic, Exceptions |
| 6 | Implement fund transfer | JDBC Transactions |
| 7 | Implement transaction history | SQL JOIN, Collections |
| 8 | Add transaction summaries | Java 8 Streams and Lambda |

## 10. Important Business Validation Rules

Students must implement these validations:

1. Customer email should be unique.
2. Account number should be unique.
3. Deposit amount must be greater than zero.
4. Withdrawal amount must be greater than zero.
5. Withdrawal should not exceed the available balance.
6. Fund transfer should not allow the same sender and receiver account.
7. Both accounts must exist before transferring money.
8. Fund transfer must be atomic: either both account updates succeed or neither does.
9. Transaction history should be maintained for every successful banking operation.

> **Important:** Use JDBC transactions for fund transfers. Do not update the sender and receiver balances as independent commits. For a real banking system, additional controls such as concurrency handling, auditability, authorization, and idempotency would also be required.

## 11. Expected Project Outcome

By completing this project, students should be able to:

- Develop a Java 8 console-based application.
- Apply object-oriented programming principles.
- Connect Java applications to MySQL using JDBC.
- Perform database CRUD operations.
- Implement banking business logic.
- Handle exceptions and validations.
- Implement database transactions.
- Use Java 8 Lambda expressions and Streams.

## 12. Future Enhancement

Convert this console-based application into a Spring Boot REST API and connect it to an Angular or React banking dashboard.

This gives students a single project that starts with Core Java 8 and JDBC and can eventually evolve into a full-stack banking application.
