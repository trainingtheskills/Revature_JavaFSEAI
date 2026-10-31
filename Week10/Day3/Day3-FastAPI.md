# Learning Outcome
## By the end of this session, participants will be able to:
1. Understand the fundamentals of FastAPI and its role in developing high-performance Python-based REST APIs.
2. Create REST API endpoints to perform CRUD operations using FastAPI.
3. Design and implement request and response models using Pydantic for data validation and serialization.
4. Work with path parameters and query parameters to accept and process client requests.
5. Implement Dependency Injection to manage reusable components, services, and application dependencies.
6. Create and use Pydantic models to define structured data, enforce validation rules, and maintain data consistency.
7. Organize a FastAPI application using a modular project structure, separating routers, models, schemas, services, and application configurations.
8. Test API endpoints using Swagger UI and understand API request and response documentation.
Expected Outcome: Participants will be able to develop, validate, test, and organize a basic REST API application using Python and FastAPI.

# 1. What is an API Interface?
API (Application Programming Interface) is a mechanism that allows two different software applications to communicate and exchange data with each other.

An API acts as a bridge between two applications, allowing one application to request services or data from another application without knowing its internal implementationt.
<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/6945b4b4-37df-48ac-9d55-634f24d080bd" />

## 2. Technical Example

Suppose we have two applications:

- **Application A:** Employee Management System
- **Application B:** Payroll Management System

Application A wants to retrieve employee details from Application B.

It can call an API using an HTTP GET request:

```http
GET https://example.com/api/employees/101
```

### API Response

The API may return the following JSON response:

```json
{
    "id": 101,
    "name": "Rahul",
    "department": "IT",
    "salary": 50000
}
```
### How Does It Work?

<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/d1159194-99d8-43a3-9e98-fd06ed08f012" />

<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/2d03a7b5-c42c-4222-b51d-52aa71d3f4d9" />

1. Application A sends a GET request to Application B's API.
2. Application B receives the request.
3. Application B retrieves the employee details from its database.
4. Application B converts the data into JSON format.
5. Application B sends the JSON response to Application A.
6. Application A reads and uses the employee information.


### Key Understanding

Application A can use this response without knowing:
<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/61743ab5-6299-4250-a8bf-49656525be86" />

- How Application B stores employee information.
- Which database Application B uses.
- How Application B retrieves employee records.
- How Application B internally processes the request.


### 3. What Does an API Interface Specify?

An API Interface specifies:

- **Available Operations:** What operations can be performed.
- **Input Parameters:** What input parameters are required.
- **Data Format:** What data format should be used for communication (e.g., JSON or XML).
- **Response:** What response will be returned by the API.
- **Error Handling:** How errors and exceptions will be communicated.

###  Example: Employee Management API

Suppose we have an Employee Management System that exposes APIs to perform different employee-related operations.

The Employee API might expose the following endpoints:

| API Endpoint | HTTP Method | Operation |
|---|---|---|
| `/employees` | `GET` | Retrieve all employees |
| `/employees/101` | `GET` | Retrieve employee by ID |
| `/employees` | `POST` | Create a new employee |
| `/employees/101` | `PUT` | Update employee details |
| `/employees/101` | `DELETE` | Delete an employee |

### Understanding API Endpoints

Each API endpoint provides a specific operation:

1. **GET /employees**
   - Retrieves the list of all employees.

2. **GET /employees/101**
   - Retrieves employee details for employee ID 101.

3. **POST /employees**
   - Creates a new employee record.

4. **PUT /employees/101**
   - Updates the details of employee ID 101.

5. **DELETE /employees/101**
   - Deletes the employee record with ID 101.

###  Key Takeaway

An **API Interface defines what an application can request and how another application should respond**, without exposing its internal implementation details.


---------------------------------------------------------------------------------------------------------------------------------------------------------------

# Introduction to FastAPI

## 1. What is FastAPI?

**FastAPI** is a modern, high-performance Python web framework used to build APIs (Application Programming Interfaces).

It allows developers to create REST APIs that enable different applications to communicate and exchange data over HTTP.

FastAPI is commonly used to develop:

- RESTful Web Services.
- Backend services for web and mobile applications.
- Microservices.
- Machine Learning and AI model APIs.
- GenAI and LLM-based application APIs.

## 2. Why Do We Need FastAPI?

Suppose we have two applications:

- **Application A:** Employee Management System (Frontend)
- **Application B:** Employee Management API (Backend)

Application A wants to retrieve employee details from Application B.

FastAPI helps us develop the backend API that receives requests, processes business logic, interacts with databases, and returns responses in JSON format.

### Communication Architecture

```text
+-----------------------------+
|         Application A       |
|    Employee Management     |
|          (Frontend)         |
+-----------------------------+
              |
              | HTTP Request
              | GET /employees/101
              |
              v
+-----------------------------+
|         Application B       |
|       FastAPI Backend       |
|                             |
|  1. Receive Request         |
|  2. Process Business Logic  |
|  3. Retrieve Employee Data  |
|  4. Return JSON Response    |
+-----------------------------+
              |
              |
              v
+-----------------------------+
|          Database           |
|      Employee Records       |
+-----------------------------+
```

## 3. How Does FastAPI Work?

Consider the following communication flow:

1. A client (browser, mobile application, or another system) sends an HTTP request.
2. FastAPI receives the request through an API endpoint.
3. FastAPI processes the request using Python code.
4. It may communicate with a database or another service.
5. FastAPI returns the response, usually in JSON format.
6. The client receives and uses the response.

## 4. Key Features of FastAPI

| Feature | Description |
|---|---|
| High Performance | Provides high performance using ASGI and asynchronous programming support. |
| REST API Development | Makes it easy to create RESTful API endpoints. |
| Automatic Documentation | Generates interactive API documentation automatically. |
| Pydantic Integration | Validates request data and defines response structures. |
| Type Hints | Uses Python type annotations for better code readability and validation. |
| Async Support | Supports asynchronous programming using `async` and `await`. |
| Dependency Injection | Helps manage reusable components and dependencies. |
| Data Validation | Validates incoming request data automatically. |

## 5. Simple FastAPI Example

Let's create a simple API that returns employee details.

### Step 1: Install FastAPI

Open the terminal and execute:

```bash
pip install "fastapi[standard]"
```

### Step 2: Create a Python File

Create a file named `main.py`.

```python
from fastapi import FastAPI

# Create FastAPI application
app = FastAPI()

# Define API endpoint
@app.get("/employees/101")
def get_employee():

    return {
        "id": 101,
        "name": "Rahul",
        "department": "IT",
        "salary": 50000
    }
```

### Step 3: Run the Application

Execute the following command in the terminal:

```bash
fastapi dev main.py
```

The application will start on:

```text
http://127.0.0.1:8000
```

### Step 4: Access the API

Open the following URL in your browser:

```text
http://127.0.0.1:8000/employees/101
```

### Expected JSON Response

```json
{
    "id": 101,
    "name": "Rahul",
    "department": "IT",
    "salary": 50000
}
```

### Understanding the Code

| Code | Description |
|---|---|
| `from fastapi import FastAPI` | Imports the FastAPI class. |
| `app = FastAPI()` | Creates a FastAPI application instance. |
| `@app.get("/employees/101")` | Defines a GET API endpoint. |
| `def get_employee()` | Defines a Python function to handle the request. |
| `return { ... }` | Returns employee information as JSON. |

## 6. Automatic API Documentation

One of the most useful features of FastAPI is automatic API documentation.

After starting the application, FastAPI automatically generates interactive API documentation.

### 6.1 Swagger UI

Access the following URL:

```text
http://127.0.0.1:8000/docs
```

Swagger UI allows developers to:

- View available API endpoints.
- Understand request parameters.
- Execute API requests directly from the browser.
- View API responses.
- Test APIs without requiring separate API testing tools.

### 6.2 ReDoc

Access the following URL:

```text
http://127.0.0.1:8000/redoc
```

ReDoc provides an alternative documentation interface for exploring API specifications.

## 7. FastAPI vs Flask vs Django

| Feature | FastAPI | Flask | Django |
|---|---|---|---|
| Type | Modern API framework | Lightweight web framework | Full-stack web framework |
| Performance | High | Depends on deployment and implementation | Depends on deployment and implementation |
| API Development | Excellent | Supported | Supported through Django REST Framework |
| Data Validation | Pydantic | Additional libraries | Django Forms/Serializers |
| Async Support | Native | Supported | Supported |
| Automatic API Docs | Built-in | Additional libraries | Additional libraries |
| Learning Curve | Easy to Moderate | Easy | Moderate to High |

## 8. Important FastAPI Concepts

The following concepts are important when developing real-world FastAPI applications.

### 8.1 Creating REST Endpoints

Defining API endpoints using HTTP methods such as:

- GET
- POST
- PUT
- DELETE

### 8.2 Request and Response Models (Pydantic)

Defining the structure and validation rules for incoming requests and outgoing responses.

### 8.3 Path Parameters

Passing values through the URL path.

Example:

```text
GET /employees/101
```

Here, `101` represents the employee ID.

### 8.4 Query Parameters

Passing optional or filtering values through the URL.

Example:

```text
GET /employees?department=IT
```

Here, `department` is a query parameter.

### 8.5 Dependency Injection

Reusing common components such as:

- Database connections.
- Authentication services.
- Configuration settings.
- Common business services.

### 8.6 Pydantic Models

Defining structured data models with automatic validation.

### 8.7 FastAPI Project Structure

Organizing an application into modules such as:

- Routers
- Services
- Models
- Schemas
- Database
- Configuration

## 9. Summary

**FastAPI is a Python framework that simplifies REST API development.**

It provides:

- Automatic data validation.
- Interactive API documentation.
- Asynchronous programming support.
- High performance.
- Easy integration with databases and external services.

It is particularly useful for building Python backend services, microservices, and APIs that expose AI and Machine Learning models to other applications.

## 10. Official Documentation

For additional learning, refer to the official documentation:

https://fastapi.tiangolo.com/


