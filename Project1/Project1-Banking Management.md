# Project 1: Banking Management System

**Technology:** Core Java 8, JDBC, MySQL,AWS ,Junit
**Level:** Beginner to Intermediate  
**Application type:** Console-based Java application  
**Architecture:** Simple Core Java package structure (no MVC, no Spring, no web layer)

## 1. Business Case Study

A bank wants to develop a simple **Banking Management System** to manage customer accounts and basic banking operations.

Currently, bank employees maintain customer information, account details, and transaction records manually. This makes it difficult to track account balances, maintain transaction history, and perform banking operations accurately.

The bank wants a Java-based console application that allows employees to create customer accounts and perform basic banking operations.

The application will be developed using **Core Java 8, JDBC, and MySQL**. The project should remain a straightforward Core Java application. Do not use MVC architecture, Spring, REST APIs, or frontend frameworks in this version.

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
- Display a confirmation message.

### Scenario 2: Create Bank Account

- Accept customer ID.
- Select account type (Savings or Current).
- Generate a unique account number.
- Set the initial account balance to zero.
- Store account information in the database.
- Display the generated account number.

### Scenario 3: Deposit Money

- Accept account number.
- Accept deposit amount.
- Validate the account number.
- Ensure the deposit amount is greater than zero.
- Add the amount to the existing balance.
- Update the account balance.
- Record the transaction.

### Scenario 4: Withdraw Money

- Accept account number.
- Accept withdrawal amount.
- Validate the account number.
- Ensure the withdrawal amount is greater than zero.
- Check whether sufficient balance is available.
- Deduct the amount from the account.
- Update the account balance.
- Record the transaction.

### Scenario 5: Fund Transfer

- Accept sender account number.
- Accept receiver account number.
- Accept transfer amount.
- Verify both accounts.
- Ensure the sender and receiver accounts are different.
- Check the sender's available balance.
- Deduct money from the sender's account.
- Add money to the receiver's account.
- Record the transfer against both accounts.
- Commit both balance updates and transaction records together.

### Scenario 6: Transaction History

- Accept account number.
- Retrieve all transactions associated with the account.
- Display transaction date, type, amount, and transaction reference where available.

## 5. Database Design

Create a MySQL database named `banking_db`.

### Table 1: `customers`

| Column | Data Type | Description |
|---|---|---|
| customer_id | INT | Primary Key |
| customer_name | VARCHAR(100) | Customer name |
| email | VARCHAR(100) | Unique email |
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
| transaction_type | VARCHAR(20) | DEPOSIT / WITHDRAWAL / TRANSFER |
| amount | DECIMAL(12,2) | Transaction amount |
| transaction_reference | VARCHAR(50) | Groups the two sides of a transfer |
| transaction_date | TIMESTAMP | Transaction date |

**Database design note:** For a transfer, insert two transaction rows with the same transaction reference: one debit entry for the sender and one credit entry for the receiver. This makes the account-wise transaction history clear.

## 6. Core Java Concepts to Apply

| Core Java 8 Concept | Application |
|---|---|
| Classes and Objects | Customer, Account, Transaction |
| Constructors | Initialize customer, account, and transaction objects |
| Encapsulation | Private fields with getters and setters |
| Inheritance | SavingsAccount and CurrentAccount, if account-specific rules are required |
| Polymorphism | Common account operations with account-specific behavior |
| Abstraction | Define common banking operations using an interface or abstract class |
| Interfaces | BankOperations |
| Method Overloading | Different deposit or account-search method signatures |
| Method Overriding | Account-specific withdrawal rules, if required |
| Collections | Store and process transaction records retrieved from the database |
| Exception Handling | Invalid account, insufficient balance, and database errors |
| Custom Exceptions | InvalidAccountException and InsufficientBalanceException |
| Java 8 Date-Time API | Transaction date and time |
| Lambda Expressions | Filter transaction records |
| Stream API | Calculate transaction summaries |

## 7. JDBC Concepts to Apply

| JDBC Concept | Application |
|---|---|
| DriverManager | Establish database connection |
| Connection | Connect to MySQL |
| Statement | Execute simple SQL statements when appropriate |
| PreparedStatement | Safely execute parameterized queries and updates |
| ResultSet | Retrieve customer, account, and transaction details |
| `executeQuery()` | SELECT operations |
| `executeUpdate()` | INSERT, UPDATE, DELETE |
| CallableStatement | Optional stored procedure execution |
| Transactions | Fund transfer |
| `commit()` | Confirm successful transfer |
| `rollback()` | Undo all transfer changes if any step fails |

## 8. Suggested Core Java Project Structure

Keep the project organized into simple packages based on Java responsibilities. This is **not an MVC structure**. The application starts from `Main.java`, accepts console input, invokes ordinary Java classes, and uses JDBC classes to communicate with MySQL.

```text
BankingManagementSystem/
│
├── src/
│   └── com/banking/
│       │
│       ├── Main.java
│       ├── BankOperations.java
│       │
│       ├── model/
│       │   ├── Customer.java
│       │   ├── Account.java
│       │   ├── SavingsAccount.java
│       │   ├── CurrentAccount.java
│       │   └── Transaction.java
│       │
│       ├── operations/
│       │   └── BankingOperations.java
│       │
│       ├── database/
│       │   └── DBConnection.java
│       │
│       ├── exception/
│       │   ├── InvalidAccountException.java
│       │   └── InsufficientBalanceException.java
│       │
│       └── utility/
│           ├── InputUtility.java
│           └── AccountNumberGenerator.java
│
├── resources/
│   └── db.properties
│
└── pom.xml
```

### Responsibility of each package

| Package / Class | Responsibility |
|---|---|
| `Main.java` | Application entry point; displays the menu and controls the console workflow |
| `BankOperations.java` | Declares banking operations that the application supports |
| `model` | Contains simple Java classes representing customers, accounts, and transactions |
| `operations` | Implements banking operations and coordinates validation and JDBC calls |
| `database` | Creates and provides MySQL database connections |
| `exception` | Contains application-specific custom exceptions |
| `utility` | Contains reusable console-input and account-number helper methods |
| `resources` | Stores database configuration properties |

**Structure guidance:**
- Do not create `controller`, `service`, `repository`, or `view` packages.
- Do not introduce MVC layers or Spring components.
- Keep the application console-driven through `Main.java`.
- Use JDBC directly from the banking operations implementation for this learning project.
- Keep model classes focused on data and basic object behavior.

## 9. Development Milestones

| Milestone | Development Activity | Concepts |
|---|---|---|
| 1 | Create Customer, Account, and Transaction classes | Core Java, classes, objects, constructors |
| 2 | Implement console menu and input handling | Scanner, methods, switch, loops |
| 3 | Implement customer and account creation in memory | OOP, collections |
| 4 | Connect Java application to MySQL | JDBC, DriverManager, Connection |
| 5 | Implement customer and account database operations | PreparedStatement, ResultSet |
| 6 | Implement deposit and withdrawal | Business rules, validation, exceptions |
| 7 | Implement fund transfer | JDBC transactions, commit, rollback |
| 8 | Implement transaction history | SQL JOIN, Collections |
| 9 | Add transaction summaries | Java 8 Streams and Lambda expressions |
| 10 | Test invalid inputs and failure scenarios | Exception handling and debugging |

## 10. Important Business Validation Rules

Students must implement these validations:

1. Customer email should be unique.
2. Account number should be unique.
3. An account can only be created for an existing customer.
4. Deposit amount must be greater than zero.
5. Withdrawal amount must be greater than zero.
6. Withdrawal should not exceed the available balance.
7. Fund transfer should not allow the same sender and receiver account.
8. Both accounts must exist before transferring money.
9. Fund transfer must be atomic: either both account balance updates and transaction records succeed, or none of them do.
10. Transaction history should be maintained for every successful banking operation.
11. Invalid menu choices and malformed console input should be handled without terminating the application unexpectedly.

> **Important:** Use a single JDBC connection and transaction for the complete fund-transfer operation. Disable auto-commit, perform both balance updates and both transaction inserts, then commit. If any operation fails, roll back the entire transaction. For a real banking system, additional controls such as concurrency handling, auditability, authorization, and idempotency would also be required.
>
> NOTE: Write test cases

## 11. Expected Project Outcome

By completing this project, students should be able to:

- Develop a Java 8 console-based application.
- Apply object-oriented programming principles.
- Organize a Core Java project using packages and classes.
- Connect a Java application to MySQL using JDBC.
- Perform database CRUD operations.
- Implement banking business logic.
- Handle exceptions and validations.
- Implement database transactions.
- Use Java 8 Lambda expressions and Streams.

## 12. Future Enhancement (After This Core Java Project)

Once the Core Java and JDBC version is complete, the project can be extended in a separate phase:

1. Convert the application into a Spring Boot REST API.
2. Expose banking operations through HTTP endpoints.
3. Add authentication and authorization.
4. Build an Angular or React user interface.
5. Connect the frontend to the backend APIs.

These are future enhancements only. They are **not part of the current Core Java project scope**.
