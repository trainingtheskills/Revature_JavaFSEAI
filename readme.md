#Project – P1 
##Project Title: Banking Management System

##Project Description:
Banking Management System – Case Study
A bank wants to develop a console-based Banking Management System using Core Java 8, JDBC, and MySQL to automate basic banking operations. The application should allow bank employees to create customers and bank accounts, deposit and withdraw money, check account balances, transfer funds between accounts, and view transaction history. Customer, account, and transaction details must be stored in a MySQL database and accessed through JDBC. The application should use OOP concepts, Collections, Exception Handling, Java 8 features (Lambda expressions and Streams), and JDBC transactions to ensure reliable banking operations.

##Objective :
To develop a console-based Banking Management System that automates basic banking operations such as customer registration, account creation, deposits, withdrawals, balance enquiries, fund transfers, and transaction history while ensuring secure and reliable data management and test cases.

Tools Used: Core Java 8, JDBC, MySQL, VSCode/Eclipse/IntelliJ IDEA, Maven,AWS (Optional), Junit
Weeks [during training]: Wk2-Wk4
Project Type: Individual Project 
Outcome: A fully functional console-based Banking Management System that performs basic banking operations, stores customer and transaction details in a MySQL database, and demonstrates Core Java 8, JDBC, and OOP concepts.

——————————————————————————————————

#Project – P2 
##Project Title:Online Shopping Management System -Monolithic
##Project Description:
ShopSmart is a  Java full-stack project: a Spring Boot monolith with Hibernate and MySQL and a React SPA or Angular front end. It has three linked tables, customers, products and orders. Staff manage customers and products, and each order ties one customer to one product. The core rule is that stock is reduced and the order saved in one transaction, so stock never goes negative. It is tested with JUnit 5, Mockito and Postman, and delivered across Weeks 5 to 7.

##Objective / Tools Used:
##Weeks [during training]: Wk5-Wk-7
##Project Type: Group Project 
##Outcome: By the end of this project, learners will be able to:
1. Build a layered Spring Boot monolith (controller, service, DAO, entity).
2. Design customers, products and orders tables with one-to-many relationships and map them with Hibernate and HQL.
3. Create a validated REST API with pagination and consistent errors.
4. Use @Transactional so stock and order updates succeed or fail together.
5. Build a React SPA or Angular front end that calls the API.
6. Test with JUnit 5, Mockito and Postman.
7. Explain the trade-offs of a monolith versus services.

——————————————————————————————————
#Project –  P3 
##Project Title: OnlineShoppingManagement-Micorservice

##Project Description:
 Project 2 is the ShopSmart monolith, one Spring Boot application with one database for customers, products and orders. In this project the team splits it into small services that can be built, tested and started independently, then secures it and runs it in Docker containers.
Objective / Tools Used: Microservices, JPA, Security and Docker, Spring Boot Data JPA, 
Convert the ShopSmart monolith (Project 2) into a secure microservicis system. The team splits the single Spring Boot application into independent services, moves the data layer to Spring Data JPA, adds login and role-based access with Spring Security, and packages everything in Docker containers that start with one command.
##Weeks [during training]: Wk8-Wk10
##Project Type: Group Project
##Outcome: 
* A working ShopSmart system split into separate services (auth, customer, product and order) behind an API gateway.
* Each service stores its data in its own database through Spring Data JPA.
* Login with JWT tokens and role-based access (admin, staff and customer), so each user sees only what they are allowed to.
* The whole system, including the database and the web front end, runs in Docker containers and starts with one command.


#Project –  P4
##Project Title: ShopSmart AI Assistant
##Project Description:
Build a small ShopSmart AI Assistant in Python over three weeks. It starts as a FastAPI service for products and orders. It 
Objective / Tools Used: Build a working ShopSmart AI Assistant in small steps with GitHub Copilot. Learners create a FastAPI service for products and orders, add a RAG feature that answers policy questions from documents with sources, and finish with an agent that remembers the conversation and asks staff for approval before cancelling an order. Each week adds one layer, and every task ends with something the learner can show.
#Weeks [during training]: Wk11-12
##Project Type: Group Project
##Outcome: The outcome of the project is a working ShopSmart AI Assistant that a learner can run and demo. It consists of:
* A FastAPI service for products and orders, with tests, a sales report and a consistent error format.
* A RAG feature that answers shop-policy questions from five documents, shows its sources, and says “I don’t know” when the documents don’t help.
* An agent that checks order status, answers policy questions, remembers the conversation, and cancels an order only after staff approve.


