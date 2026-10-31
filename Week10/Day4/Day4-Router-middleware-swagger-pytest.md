

# Detailed Documentation: Advanced FastAPI Concepts and REST API Development

This document covers five important topics in FastAPI and Python backend development:

1. Routers in FastAPI
2. Middleware in FastAPI
3. Swagger UI and OpenAPI Documentation
4. REST API Development
5. Unit Testing using pytest

The explanations, examples, and code samples use a common **Employee Management System** so that learners can understand how these concepts work together in a real-world application.

---

# 1. Routers in FastAPI

## 1.1 What is a Router?

A **Router** in FastAPI is used to organize API endpoints into separate modules based on their functionality.

When developing a small application, we can define all API endpoints inside a single Python file.

However, in real-world enterprise applications, we may have hundreds of API endpoints.

For example, an Employee Management System may contain:

- Employee APIs
- Department APIs
- Salary APIs
- Attendance APIs
- Authentication APIs

Maintaining all these endpoints in a single Python file makes the application difficult to manage.

FastAPI provides `APIRouter` to divide API endpoints into multiple files.

**APIRouter** allows us to group related endpoints and include them in the main FastAPI application.
<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/b7f85f75-3fda-406a-ac06-0396f1d05e0d" />

## 1.2 Why Do We Need Routers?

Consider the following application:

**Employee Management System**

| Module | API Operations |
|---|---|
| Employee | Create, Read, Update, Delete employees |
| Department | Create, Read, Update, Delete departments |
| Salary | Generate and retrieve salary details |
| Attendance | Mark and retrieve attendance |
| Authentication | Login and logout |

Without routers, all these endpoints might be defined in `main.py`.
<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/dadcbccb-8d70-4c13-8fea-56a62cfe05e1" />

Problems include:

1. Large and difficult-to-maintain files.
2. Poor code organization.
3. Difficult collaboration between developers.
4. Code duplication.
5. Difficult testing and debugging.

Routers solve these problems by separating functionality into independent modules.
<img width="206" height="286" alt="image" src="https://github.com/user-attachments/assets/2bf06e46-44ce-4558-b255-9b53315fecd2" />

## 1.3 Creating an APIRouter

FastAPI provides the `APIRouter` class.

```python
from fastapi import APIRouter

router = APIRouter()

@router.get("/employees")
def get_employees():
    return {
        "message": "List of employees"
    }
```

Explanation:

| Code | Description |
|---|---|
| `APIRouter()` | Creates a router object |
| `@router.get()` | Defines a GET endpoint |
| `@router.post()` | Defines a POST endpoint |
| `@router.put()` | Defines a PUT endpoint |
| `@router.delete()` | Defines a DELETE endpoint |

The router contains related API endpoints.

## 1.4 Creating a FastAPI Project Using Routers

Let's develop an Employee Management System.

**Project Structure**

```text
EmployeeManagement/
│
├── main.py
│
├── routers/
│   ├── __init__.py
│   ├── employee.py
│   └── department.py
│
└── models/
    ├── __init__.py
    └── employee.py
```

### Step 1: Create employee.py

Location: `routers/employee.py`

```python
from fastapi import APIRouter

router = APIRouter(
    prefix="/employees",
    tags=["Employees"]
)

employees = [
    {
        "id": 101,
        "name": "Rahul",
        "department": "IT"
    },
    {
        "id": 102,
        "name": "Priya",
        "department": "HR"
    }
]

@router.get("/")
def get_employees():
    return employees


@router.get("/{employee_id}")
def get_employee(employee_id: int):

    for employee in employees:

        if employee["id"] == employee_id:
            return employee

    return {
        "message": "Employee not found"
    }


@router.post("/")
def create_employee(employee: dict):

    employees.append(employee)

    return {
        "message": "Employee created successfully",
        "employee": employee
    }
```

### Step 2: Create department.py

Location: `routers/department.py`

```python
from fastapi import APIRouter

router = APIRouter(
    prefix="/departments",
    tags=["Departments"]
)

departments = [
    {
        "id": 1,
        "name": "IT"
    },
    {
        "id": 2,
        "name": "HR"
    }
]


@router.get("/")
def get_departments():

    return departments


@router.get("/{department_id}")
def get_department(department_id: int):

    for department in departments:

        if department["id"] == department_id:
            return department

    return {
        "message": "Department not found"
    }
```

### Step 3: Create main.py

Location: `main.py`

```python
from fastapi import FastAPI

from routers import employee
from routers import department

app = FastAPI(
    title="Employee Management API",
    description="API for managing employees and departments",
    version="1.0.0"
)

app.include_router(employee.router)

app.include_router(department.router)


@app.get("/")
def home():

    return {
        "message": "Welcome to Employee Management API"
    }
```

### Step 4: Run the application

```bash
uvicorn main:app --reload
```

Open the browser:

```text
http://127.0.0.1:8000/docs
```

You will see two groups:

- Employees
- Departments

These groups are created using the `tags` parameter of `APIRouter`.

## 1.5 Understanding prefix and tags

Consider:

```python
router = APIRouter(
    prefix="/employees",
    tags=["Employees"]
)
```

And:

```python
@router.get("/")
def get_employees():
    return []
```

The actual endpoint becomes:

```text
GET /employees/
```

| Parameter | Description |
|---|---|
| `prefix` | Adds a common URL prefix to all router endpoints |
| `tags` | Groups endpoints in Swagger UI |
| `dependencies` | Applies common dependencies to router endpoints |
| `responses` | Defines additional response documentation |

## 1.6 Router-level Dependencies

We can apply dependencies to all endpoints inside a router.

```python
from fastapi import APIRouter, Depends

def verify_token():
    print("Token verification executed")

router = APIRouter(
    prefix="/employees",
    tags=["Employees"],
    dependencies=[Depends(verify_token)]
)
```

Now, `verify_token()` will execute for requests handled by this router.

This is useful for:

- Authentication
- Authorization
- Access control
- Request validation

## 1.7 Benefits of Routers
<img width="1024" height="559" alt="image" src="https://github.com/user-attachments/assets/086ffa9e-c7dc-4aad-a10e-3f93885ad3e8" />

1. Modular application architecture.
2. Better code reusability.
3. Easier maintenance.
4. Better team collaboration.
5. Easier testing.
6. Separation of business modules.
7. Cleaner project structure.

---

# 2. Middleware in FastAPI

## 2.1 What is Middleware?

**Middleware** is a component that executes during the request-response processing cycle of an application.

Middleware sits between the client and the API endpoint.

Whenever a client sends an HTTP request, middleware can execute some operations before the request reaches the endpoint.

Similarly, middleware can execute additional operations before the response is returned to the client.

For example:

- Logging incoming requests.
- Measuring API execution time.
- Checking request headers.
- Adding response headers.
- Handling cross-origin requests.
- Implementing authentication checks.

## 2.2 Understanding the Middleware Flow

Consider a client requesting employee details.

```text
             Client
                |
                |
          HTTP Request
                |
                v
        +----------------+
        |   Middleware   |
        |                |
        | 1. Log Request |
        | 2. Check Token |
        | 3. Start Timer |
        +----------------+
                |
                v
        +----------------+
        |  API Endpoint  |
        |                |
        | Get Employee   |
        +----------------+
                |
                v
        +----------------+
        |   Middleware   |
        |                |
        | 1. Stop Timer  |
        | 2. Add Headers |
        | 3. Log Response|
        +----------------+
                |
                v
          HTTP Response
                |
                v
              Client
```

Middleware generally wraps the request-response cycle.

## 2.3 Why Do We Need Middleware?

Suppose we have 50 REST API endpoints.

We want to log the following information for every request:

- Request URL
- HTTP method
- Request processing time
- Response status code

Without middleware, we would need to write similar code inside all 50 endpoints.

This creates code duplication.

Middleware allows us to implement common functionality once and apply it across the application.

## 2.4 Creating Custom Middleware

FastAPI supports middleware using the `@app.middleware("http")` decorator.
<img width="606" height="467" alt="image" src="https://github.com/user-attachments/assets/be6accd0-1b0a-48b1-a510-3db7a48d3453" />

Example:

```python
from fastapi import FastAPI, Request
import time

app = FastAPI()


@app.middleware("http")
async def log_requests(request: Request, call_next):

    start_time = time.time()

    print("Incoming Request:", request.method, request.url)

    response = await call_next(request)

    process_time = time.time() - start_time

    print("Processing Time:", process_time)

    response.headers["X-Process-Time"] = str(process_time)

    return response


@app.get("/")
def home():

    return {
        "message": "Welcome to FastAPI"
    }
```

### Explanation

**1. Request object**

```python
request: Request
```

Provides information about the incoming HTTP request.

We can access:

```python
request.method
request.url
request.headers
request.query_params
request.client
```

**2. call_next**

```python
response = await call_next(request)
```

Passes the request to the next middleware or the API endpoint and obtains its response.

**3. Processing time**

```python
process_time = time.time() - start_time
```

Calculates how much time the request took to process.

**4. Adding a response header**

```python
response.headers["X-Process-Time"] = str(process_time)
```

Adds a custom HTTP response header.

## 2.5 Understanding Request and Response Headers

HTTP headers contain additional information about a request or response.

Example request headers:

```text
GET /employees HTTP/1.1
Host: localhost:8000
Accept: application/json
Authorization: Bearer abc123
```

Example response headers:

```text
HTTP/1.1 200 OK
Content-Type: application/json
X-Process-Time: 0.0025
```

Here:

| Header | Purpose |
|---|---|
| `Content-Type` | Specifies the response content format |
| `Authorization` | Carries authentication credentials |
| `Accept` | Specifies acceptable response formats |
| `X-Process-Time` | Custom header containing processing time |

## 2.6 CORS Middleware

CORS stands for **Cross-Origin Resource Sharing**.

It is a browser security mechanism that controls whether a frontend application can access resources from a different origin.

Consider:

Frontend application:

```text
http://localhost:3000
```

Backend application:

```text
http://localhost:8000
```

These applications have different origins because their ports are different.

A browser may block frontend JavaScript from accessing the backend response unless the backend allows the origin through appropriate CORS headers.

FastAPI provides `CORSMiddleware`.

Example:

```python
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()

origins = [
    "http://localhost:3000",
    "http://127.0.0.1:3000"
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"]
)


@app.get("/")
def home():

    return {
        "message": "CORS configured successfully"
    }
```

### Understanding CORS Parameters

| Parameter | Description |
|---|---|
| `allow_origins` | Specifies which origins can access the API |
| `allow_credentials` | Allows credentials such as cookies |
| `allow_methods` | Specifies allowed HTTP methods |
| `allow_headers` | Specifies allowed request headers |

**Important:** Avoid using unrestricted origins in production when credentials are enabled. Configure only trusted frontend origins.

## 2.7 Middleware Use Cases

| Middleware | Purpose |
|---|---|
| Logging Middleware | Records incoming requests |
| Authentication Middleware | Checks authentication credentials |
| Timing Middleware | Measures API processing time |
| CORS Middleware | Controls cross-origin browser access |
| Security Middleware | Adds security-related response headers |
| Error Handling Middleware | Handles or standardizes certain application errors |

Note: Authentication and authorization are often better implemented using FastAPI dependencies or security utilities rather than generic middleware, particularly when access control varies by endpoint.

## 2.8 Benefits of Middleware

- Centralized request processing.
- Code reusability.
- Consistent logging.
- Better monitoring.
- Improved security controls.
- Reduced code duplication.

---

# 3. Swagger UI and OpenAPI

## 3.1 What is API Documentation?

API documentation describes how developers can communicate with a REST API.

It explains:

- Available API endpoints.
- Supported HTTP methods.
- Required request parameters.
- Request body formats.
- Response formats.
- HTTP status codes.
- Error responses.
- Authentication requirements.

API documentation is important when frontend and backend developers work independently.

For example:

A React developer needs to understand how to call an Employee API developed using FastAPI.

API documentation provides the required information.

## 3.2 What is OpenAPI?

**OpenAPI Specification (OAS)** is a standard format for describing REST APIs.

It provides a machine-readable description of an API.

An OpenAPI document describes:

- API paths.
- HTTP methods.
- Request parameters.
- Request body schemas.
- Response schemas.
- Authentication schemes.
- API metadata.

FastAPI automatically generates an OpenAPI schema based on the application code.

By default, the OpenAPI document is available at:

```text
http://127.0.0.1:8000/openapi.json
```

The document is generally represented in JSON format.

## 3.3 What is Swagger UI?

**Swagger UI** is a browser-based interface that displays API documentation and allows developers to interactively execute API requests.

FastAPI automatically provides Swagger UI.

Default URL:

```text
http://127.0.0.1:8000/docs
```

Swagger UI allows developers to:

1. View available API endpoints.
2. Understand request parameters.
3. View request and response schemas.
4. Enter request data.
5. Execute API requests.
6. View actual API responses.
7. Test different HTTP methods.

## 3.4 Swagger UI vs OpenAPI

| Feature | Swagger UI | OpenAPI |
|---|---|---|
| Definition | Interactive documentation interface | API description specification |
| Purpose | Explore and test APIs | Describe API structure |
| Format | Browser-based interface | JSON or YAML |
| Usage | Developers and testers | Developers and API tools |
| Execution | Supports interactive API calls | Does not execute API requests itself |
| FastAPI URL | `/docs` | `/openapi.json` |

**Important:** OpenAPI is the specification. Swagger UI is one tool that displays an OpenAPI document.

## 3.5 Creating API Documentation in FastAPI

Example:

```python
from fastapi import FastAPI

app = FastAPI(
    title="Employee Management API",
    description="REST API for managing employee records",
    version="1.0.0"
)


@app.get(
    "/employees",
    summary="Get all employees",
    description="Returns a list of all employees"
)
def get_employees():

    return [
        {
            "id": 101,
            "name": "Rahul"
        },
        {
            "id": 102,
            "name": "Priya"
        }
    ]
```

Now open:

```text
http://127.0.0.1:8000/docs
```

Swagger UI displays the API title, description, version, endpoint summary, and response information.

## 3.6 Adding Request and Response Models

Pydantic models help FastAPI generate accurate API documentation.

Example:

```python
from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()


class Employee(BaseModel):

    id: int
    name: str
    department: str
    salary: float


@app.post("/employees")
def create_employee(employee: Employee):

    return {
        "message": "Employee created successfully",
        "data": employee
    }
```

Swagger UI will automatically show the request body schema.

Example request:

```json
{
    "id": 101,
    "name": "Rahul",
    "department": "IT",
    "salary": 50000
}
```

FastAPI uses the Pydantic model to validate the incoming JSON request.

## 3.7 Customizing Swagger Documentation

FastAPI supports additional documentation properties.

```python
from fastapi import FastAPI

app = FastAPI(
    title="Banking REST API",
    description="""
    API for managing customer accounts,
    transactions and banking operations.
    """,
    version="2.0.0",
    contact={
        "name": "API Support",
        "email": "support@example.com"
    },
    license_info={
        "name": "MIT"
    }
)
```

## 3.8 Swagger UI and ReDoc

FastAPI provides two documentation interfaces by default.

| Feature | Swagger UI | ReDoc |
|---|---|---|
| URL | `/docs` | `/redoc` |
| Interface | Interactive | Documentation-oriented |
| Execute APIs | Yes | Generally no interactive execution |
| Purpose | API exploration and testing | API reference documentation |

Default URLs:

```text
http://127.0.0.1:8000/docs
```

```text
http://127.0.0.1:8000/redoc
```

## 3.9 Benefits of Swagger and OpenAPI

1. Automatically generated API documentation.
2. Easier frontend-backend integration.
3. Faster API testing.
4. Standardized API descriptions.
5. Better team collaboration.
6. Easier API integration with external systems.
7. Improved API maintainability.

---

# 4. REST API Development Using FastAPI

## 4.1 What is REST?

REST stands for **Representational State Transfer**.

REST is an architectural style used to design network-based applications.

REST APIs allow different applications to communicate using HTTP.

For example:

- React frontend communicates with FastAPI backend.
- Mobile application communicates with a banking server.
- Employee Management System communicates with Payroll Management System.

REST APIs generally exchange data using JSON.

## 4.2 REST API Architecture

Consider an Employee Management System.

```text
       React Frontend
              |
              |
          HTTP Request
              |
              v
       FastAPI Backend
              |
              |
       Business Logic
              |
              v
        Database Layer
              |
              |
          MySQL DB
              |
              v
        JSON Response
              |
              v
       React Frontend
```

## 4.3 Important HTTP Methods

REST APIs use different HTTP methods to perform operations on resources.

| HTTP Method | Operation | Description |
|---|---|---|
| GET | Read | Retrieve employee information |
| POST | Create | Create a new employee |
| PUT | Update | Replace an existing employee |
| PATCH | Partial Update | Update selected employee fields |
| DELETE | Delete | Delete an employee |

Example endpoints:

| Endpoint | HTTP Method | Description |
|---|---|---|
| `/employees` | GET | Retrieve all employees |
| `/employees/101` | GET | Retrieve employee 101 |
| `/employees` | POST | Create an employee |
| `/employees/101` | PUT | Replace employee 101 |
| `/employees/101` | PATCH | Partially update employee 101 |
| `/employees/101` | DELETE | Delete an employee |

## 4.4 What is a Resource?

In REST architecture, a resource represents an entity or object managed by the API.

Examples:

- Employee
- Customer
- Product
- Account
- Order
- Department

Each resource is identified through a URL.

For example:

```text
/employees
```

Represents the employee collection.

```text
/employees/101
```

Represents a particular employee.

## 4.5 Understanding HTTP Status Codes

HTTP status codes communicate the result of an API request.

| Status Code | Meaning | Description |
|---|---|---|
| 200 | OK | Request successful |
| 201 | Created | Resource successfully created |
| 204 | No Content | Successful operation without response body |
| 400 | Bad Request | Invalid client request |
| 401 | Unauthorized | Authentication required or invalid |
| 403 | Forbidden | Access not permitted |
| 404 | Not Found | Resource does not exist |
| 409 | Conflict | Request conflicts with existing resource state |
| 422 | Unprocessable Content | Request validation failed |
| 500 | Internal Server Error | Unexpected server-side error |

## 4.6 Building a Complete REST API

Let's develop a simple Employee Management REST API using FastAPI and Pydantic.

### Step 1: Install Dependencies

```bash
pip install fastapi uvicorn pydantic
```

For testing:

```bash
pip install pytest httpx
```

### Step 2: Create the Project Structure

```text
EmployeeManagement/
│
├── main.py
│
├── models/
│   ├── __init__.py
│   └── employee.py
│
├── routers/
│   ├── __init__.py
│   └── employee.py
│
└── tests/
    ├── __init__.py
    └── test_employee.py
```

### Step 3: Create Pydantic Models

File: `models/employee.py`

```python
from pydantic import BaseModel, Field


class Employee(BaseModel):

    id: int

    name: str = Field(
        min_length=2,
        max_length=50
    )

    department: str

    salary: float = Field(
        gt=0
    )
```

Explanation:

| Attribute | Description |
|---|---|
| `BaseModel` | Base class for Pydantic models |
| `id: int` | Employee ID must be an integer |
| `name: str` | Employee name must be a string |
| `min_length` | Minimum name length |
| `max_length` | Maximum name length |
| `gt=0` | Salary must be greater than zero |

### Step 4: Create Employee Router

File: `routers/employee.py`

```python
from fastapi import APIRouter, HTTPException
from models.employee import Employee

router = APIRouter(
    prefix="/employees",
    tags=["Employees"]
)

employees = [
    Employee(
        id=101,
        name="Rahul",
        department="IT",
        salary=50000
    ),
    Employee(
        id=102,
        name="Priya",
        department="HR",
        salary=60000
    )
]


# GET All Employees

@router.get("/", response_model=list[Employee])
def get_employees():

    return employees


# GET Employee By ID

@router.get("/{employee_id}", response_model=Employee)
def get_employee(employee_id: int):

    for employee in employees:

        if employee.id == employee_id:
            return employee

    raise HTTPException(
        status_code=404,
        detail="Employee not found"
    )


# CREATE Employee

@router.post("/", response_model=Employee, status_code=201)
def create_employee(employee: Employee):

    for existing in employees:

        if existing.id == employee.id:

            raise HTTPException(
                status_code=409,
                detail="Employee already exists"
            )

    employees.append(employee)

    return employee


# UPDATE Employee

@router.put("/{employee_id}", response_model=Employee)
def update_employee(employee_id: int, updated_employee: Employee):

    for index, employee in enumerate(employees):

        if employee.id == employee_id:

            if updated_employee.id != employee_id:

                raise HTTPException(
                    status_code=400,
                    detail="Employee ID cannot be changed"
                )

            employees[index] = updated_employee

            return updated_employee

    raise HTTPException(
        status_code=404,
        detail="Employee not found"
    )


# DELETE Employee

@router.delete("/{employee_id}", status_code=204)
def delete_employee(employee_id: int):

    for index, employee in enumerate(employees):

        if employee.id == employee_id:

            employees.pop(index)

            return None

    raise HTTPException(
        status_code=404,
        detail="Employee not found"
    )
```

### Step 5: Create Main Application

File: `main.py`

```python
from fastapi import FastAPI

from routers.employee import router as employee_router

app = FastAPI(
    title="Employee Management API",
    description="REST API for Employee Management",
    version="1.0.0"
)

app.include_router(employee_router)


@app.get("/")
def home():

    return {
        "message": "Welcome to Employee Management API"
    }
```

### Step 6: Run the Application

```bash
uvicorn main:app --reload
```

Open Swagger UI:

```text
http://127.0.0.1:8000/docs
```

## 4.7 Testing the REST API

We can test our API using Swagger UI.

**Test 1: GET All Employees**

```http
GET /employees/
```

Expected response:

```json
[
    {
        "id": 101,
        "name": "Rahul",
        "department": "IT",
        "salary": 50000
    },
    {
        "id": 102,
        "name": "Priya",
        "department": "HR",
        "salary": 60000
    }
]
```

**Test 2: GET Employee By ID**

```http
GET /employees/101
```

Expected response:

```json
{
    "id": 101,
    "name": "Rahul",
    "department": "IT",
    "salary": 50000
}
```

**Test 3: POST Employee**

```http
POST /employees/
```

Request body:

```json
{
    "id": 103,
    "name": "Amit",
    "department": "Finance",
    "salary": 55000
}
```

Expected status code:

```text
201 Created
```

**Test 4: PUT Employee**

```http
PUT /employees/103
```

Request body:

```json
{
    "id": 103,
    "name": "Amit Kumar",
    "department": "Finance",
    "salary": 65000
}
```

**Test 5: DELETE Employee**

```http
DELETE /employees/103
```

Expected status code:

```text
204 No Content
```

**Note:** This example uses an in-memory Python list. Data will be lost when the application restarts. Production applications should use a persistent database such as MySQL or PostgreSQL.

---

# 5. Unit Testing Using pytest

## 5.1 What is Software Testing?

Software testing is the process of verifying that an application behaves as expected.

Testing helps identify:

- Programming errors.
- Incorrect business logic.
- Unexpected API responses.
- Invalid input handling.
- Integration problems.
- Regression issues.

For example, consider an Employee API.

We want to verify:

1. Whether GET returns all employees.
2. Whether POST creates an employee.
3. Whether PUT updates an employee.
4. Whether DELETE removes an employee.
5. Whether invalid employee IDs return appropriate errors.

Instead of manually testing every API endpoint repeatedly, we can automate the testing process.

## 5.2 What is pytest?

**pytest** is a popular Python testing framework used to write and execute automated tests.

It is widely used for:

- Unit testing.
- Functional testing.
- Integration testing.
- API testing.
- Regression testing.

pytest provides a simple syntax for writing test cases.

Example:

```python
def add(a, b):

    return a + b


def test_add():

    assert add(10, 20) == 30
```

Here:

- `test_add()` is a test function.
- `assert` verifies the expected result.
- If the condition is true, the test passes.
- If the condition is false, the test fails.

## 5.3 Why Do We Need pytest?

Consider an application containing 100 API endpoints.

Whenever a developer modifies the application, some existing functionality may stop working.

Manually testing all endpoints takes time.

pytest allows us to automate these tests.

Benefits:

1. Faster testing.
2. Early error detection.
3. Automated regression testing.
4. Better code quality.
5. Easy integration with CI/CD pipelines.
6. Reusable test cases.
7. Easy debugging.

## 5.4 Installing pytest

Install pytest:

```bash
pip install pytest
```

For FastAPI API testing, install HTTPX:

```bash
pip install httpx
```

## 5.5 Writing Your First pytest Program

Create a file:

```text
test_calculator.py
```

Code:

```python
def add(a, b):

    return a + b


def subtract(a, b):

    return a - b


def multiply(a, b):

    return a * b


def test_add():

    assert add(10, 20) == 30


def test_subtract():

    assert subtract(20, 10) == 10


def test_multiply():

    assert multiply(5, 4) == 20
```

Run the test:

```bash
pytest
```

Expected output:

```text
3 passed
```

pytest automatically discovers files named:

- `test_*.py`
- `*_test.py`

It also discovers test functions whose names start with `test_`.

## 5.6 Understanding assert

The `assert` statement checks whether a condition is true.

Syntax:

```python
assert actual_value == expected_value
```

Example:

```python
def test_salary():

    salary = 50000

    assert salary == 50000
```

Other examples:

```python
assert 10 > 5

assert "FastAPI" == "FastAPI"

assert 100 != 200

assert 50 >= 50
```

If an assertion fails, pytest reports the failure and shows the difference between expected and actual values.

## 5.7 Testing FastAPI Applications Using TestClient

FastAPI provides `TestClient` through its testing utilities.

It allows us to test API endpoints without starting an external Uvicorn server.

Example:

```python
from fastapi.testclient import TestClient
from main import app

client = TestClient(app)


def test_home():

    response = client.get("/")

    assert response.status_code == 200

    assert response.json() == {
        "message": "Welcome to Employee Management API"
    }
```

Explanation:

| Code | Description |
|---|---|
| `TestClient` | Creates a test client for the FastAPI application |
| `client.get()` | Sends an HTTP GET request |
| `response.status_code` | Returns HTTP status code |
| `response.json()` | Converts the JSON response into a Python object |
| `assert` | Verifies expected results |

## 5.8 Testing Employee REST API

Let's write test cases for the Employee Management API developed earlier.

File:

```text
tests/test_employee.py
```
** If test file is seperate folder **
<img width="211" height="263" alt="image" src="https://github.com/user-attachments/assets/a6c64048-7964-4a8c-a657-ea0e2d53beb8" />

1. Create a pytest.ini
   ```
        [pytest]
        pythonpath = .
        testpaths = testing
   ```
2. Run pytest now
   
   ``` EmployeeManangement % pytest ```
Code:

```python
from fastapi.testclient import TestClient

from main import app

client = TestClient(app)


def test_get_employees():

    response = client.get("/employees/")

    assert response.status_code == 200

    assert len(response.json()) >= 0


def test_get_employee_by_id():

    response = client.get("/employees/101")

    assert response.status_code == 200

    assert response.json()["name"] == "Rahul"


def test_get_employee_not_found():

    response = client.get("/employees/999")

    assert response.status_code == 404


def test_create_employee():

    employee = {
        "id": 103,
        "name": "Amit",
        "department": "Finance",
        "salary": 55000
    }

    response = client.post(
        "/employees/",
        json=employee
    )

    assert response.status_code == 201

    assert response.json()["name"] == "Amit"


def test_update_employee():

    employee = {
        "id": 101,
        "name": "Rahul Sharma",
        "department": "IT",
        "salary": 70000
    }

    response = client.put(
        "/employees/101",
        json=employee
    )

    assert response.status_code == 200

    assert response.json()["salary"] == 70000


def test_delete_employee():

    response = client.delete("/employees/102")

    assert response.status_code == 204
```

**Important testing consideration:** These tests modify the shared in-memory employee list. Their results can depend on execution order and previous tests. For reliable tests, use fixtures to reset test data or override the application's database dependency with an isolated test database.

## 5.9 Understanding pytest Fixtures

A **fixture** is a function that provides reusable setup or test data to test functions.

Fixtures help avoid repeating initialization code.

Example: conftest.py  

```python
@pytest.fixture
def employee():
    return {
        "id": 109,
        "name": "Amit",
        "department": "IT109",
        "salary": 50000
    }
```

Here:

```python
@pytest.fixture
```

Defines a fixture.

The test functions receive the fixture by using its function name as a parameter.

## 5.10 Fixture with FastAPI TestClient

Example:

```python
import pytest

from fastapi.testclient import TestClient

from main import app


@pytest.fixture
def client():

    return TestClient(app)


def test_home(client):

    response = client.get("/")

    assert response.status_code == 200
```

Benefits:

- Reusable test setup.
- Cleaner test functions.
- Easier test maintenance.
- Centralized initialization.

## 5.11 Understanding pytest Parametrize

Sometimes we need to test the same function with multiple input values.

Instead of writing multiple test functions, we can use `@pytest.mark.parametrize`.

Example:

```python
import pytest


def add(a, b):

    return a + b


@pytest.mark.parametrize(
    "a, b, expected",
    [
        (10, 20, 30),
        (5, 5, 10),
        (100, 200, 300),
        (1, 2, 3)
    ]
)
def test_add(a, b, expected):

    assert add(a, b) == expected
```

Here, pytest executes the same test function with four different sets of inputs.

Benefits:

1. Less code duplication.
2. Multiple test scenarios.
3. Better test coverage.
4. Easier maintenance.

## 5.12 Understanding pytest Markers

Markers are used to categorize test cases.

Example:

```python
import pytest


@pytest.mark.smoke
def test_login():

    assert True


@pytest.mark.regression
def test_employee():

    assert True
```

To execute tests with a particular marker:

```bash
pytest -m smoke
```

Before using custom markers, register them in `pytest.ini`:

```ini
[pytest]
markers =
    smoke: Smoke test cases
    regression: Regression test cases
```

## 5.13 Common pytest Commands

| Command | Description |
|---|---|
| `pytest` | Execute all discovered tests |
| `pytest -v` | Display detailed test results |
| `pytest -q` | Display compact results |
| `pytest test_employee.py` | Execute a specific test file |
| `pytest -k employee` | Execute tests matching a name expression |
| `pytest -x` | Stop after the first failure |
| `pytest --tb=short` | Display short traceback information |
| `pytest -m smoke` | Execute tests with the smoke marker |
| `pytest --collect-only` | Display discovered tests without executing them |

## 5.14 Understanding Testing Types

| Testing Type | Description | Example |
|---|---|---|
| Unit Testing | Tests individual functions or components | Testing salary calculation |
| Integration Testing | Tests interaction between components | API interacting with database |
| Functional Testing | Tests application functionality | Employee creation |
| API Testing | Tests REST API endpoints | GET, POST, PUT, DELETE |
| Regression Testing | Checks existing functionality after changes | Rechecking employee APIs |
| Smoke Testing | Checks critical application functionality | Checking application health endpoint |

---

# 6. Putting Everything Together: FastAPI Project Architecture

Now let's understand how all five concepts work together in a real-world application.

Consider an Employee Management System.

## 6.1 Overall Architecture

```text
                 FRONTEND
              React / Angular
                     |
                     |
                  HTTP
                     |
                     v
             FASTAPI APPLICATION
                     |
                     v
                MIDDLEWARE
                     |
          Logging / CORS / Security
                     |
                     v
                  ROUTERS
                     |
           +---------+---------+
           |         |         |
           v         v         v
        Employee  Department  Salary
         Router    Router     Router
           |         |         |
           +---------+---------+
                     |
                     v
               SERVICE LAYER
                     |
                     v
             REPOSITORY LAYER
                     |
                     v
                  DATABASE
                  MySQL
```

## 6.2 Role of Each Component

| Component | Responsibility |
|---|---|
| FastAPI | Creates the backend application |
| Routers | Organize API endpoints |
| Middleware | Processes incoming requests and outgoing responses |
| Pydantic | Validates request and response data |
| Service Layer | Contains business logic |
| Repository Layer | Handles database operations |
| MySQL | Stores application data |
| OpenAPI | Defines API documentation |
| Swagger UI | Displays and tests API endpoints |
| pytest | Automates application testing |

## 6.3 Recommended Enterprise Project Structure

```text
EmployeeManagement/
│
├── main.py
│
├── routers/
│   ├── employee.py
│   ├── department.py
│   └── salary.py
│
├── models/
│   ├── employee.py
│   ├── department.py
│   └── salary.py
│
├── schemas/
│   ├── employee.py
│   ├── department.py
│   └── salary.py
│
├── services/
│   ├── employee_service.py
│   ├── department_service.py
│   └── salary_service.py
│
├── repositories/
│   ├── employee_repository.py
│   └── department_repository.py
│
├── middleware/
│   ├── logging.py
│   └── security.py
│
├── database/
│   ├── connection.py
│   └── session.py
│
├── tests/
│   ├── test_employee.py
│   ├── test_department.py
│   └── test_salary.py
│
├── requirements.txt
│
└── README.md
```

This structure separates different application responsibilities and supports easier maintenance as the project grows.

---

# 7. Important Interview Questions

The following questions are useful for technical interviews and practical assessments.

## 7.1 Routers

1. What is APIRouter in FastAPI?
2. Why do we need routers?
3. What is the difference between `FastAPI()` and `APIRouter()`?
4. What is the purpose of the `prefix` parameter?
5. What is the purpose of the `tags` parameter?
6. How do you include a router in the main application?
7. Can we define multiple routers in a single application?
8. How can we apply dependencies to all endpoints of a router?

## 7.2 Middleware

9. What is middleware?
10. How does middleware work in FastAPI?
11. What is the purpose of `call_next()`?
12. What is the difference between middleware and dependencies?
13. What is CORS?
14. Why do we need CORSMiddleware?
15. How can we measure API execution time using middleware?
16. Can we create custom middleware in FastAPI?

## 7.3 Swagger and OpenAPI

17. What is Swagger UI?
18. What is OpenAPI?
19. What is the difference between Swagger UI and OpenAPI?
20. What is the default Swagger UI URL in FastAPI?
21. What is the default OpenAPI JSON URL?
22. What is ReDoc?
23. How can we customize API documentation?
24. How do Pydantic models help generate Swagger documentation?

## 7.4 REST API Development

25. What is REST?
26. What are REST architectural principles?
27. What is a resource in REST?
28. What is the difference between GET and POST?
29. What is the difference between PUT and PATCH?
30. What is the difference between 200 and 201 status codes?
31. What is the purpose of HTTP status code 404?
32. What is the difference between path parameters and query parameters?
33. Why do REST APIs commonly use JSON?
34. What is the purpose of Pydantic in FastAPI?

## 7.5 pytest

35. What is pytest?
36. Why do we use automated testing?
37. What is the purpose of the `assert` statement?
38. What are pytest fixtures?
39. What is `@pytest.mark.parametrize`?
40. What are pytest markers?
41. What is TestClient in FastAPI?
42. How do you test a GET endpoint?
43. How do you test a POST endpoint?
44. What is the difference between unit testing and integration testing?
45. How do you execute a specific test file using pytest?

---

# 8. Practical Assignments for Students

These assignments are designed to help learners develop hands-on experience with FastAPI.

## Assignment 1: Router Development

**Scenario: Product Management System**

Create a FastAPI application using routers.

Requirements:

1. Create a `product.py` router.
2. Create a `category.py` router.
3. Create a `main.py` application.
4. Define separate endpoints for products and categories.
5. Use appropriate router prefixes.
6. Group endpoints using Swagger tags.

Expected endpoints:

| Method | Endpoint | Operation |
|---|---|---|
| GET | `/products` | Retrieve all products |
| GET | `/products/{id}` | Retrieve a product |
| POST | `/products` | Create a product |
| GET | `/categories` | Retrieve all categories |

## Assignment 2: Middleware Development

**Scenario: API Request Monitoring**

Develop custom middleware to monitor incoming API requests.

Requirements:

1. Log the HTTP method.
2. Log the request URL.
3. Calculate request processing time.
4. Add a custom response header.
5. Display the response status code.

Expected custom header:

```text
X-Process-Time
```

## Assignment 3: Swagger and OpenAPI

**Scenario: Banking Application**

Develop a Banking REST API with proper API documentation.

Requirements:

1. Set the API title.
2. Add the API description.
3. Specify the API version.
4. Create Customer and Account Pydantic models.
5. Add summaries and descriptions to endpoints.
6. Test all endpoints using Swagger UI.
7. Examine the generated OpenAPI JSON.

## Assignment 4: REST API Development

**Scenario: Student Management System**

Develop a complete CRUD REST API.

Student attributes:

| Attribute | Data Type |
|---|---|
| ID | Integer |
| Name | String |
| Course | String |
| Marks | Float |

Requirements:

1. Create a student.
2. Retrieve all students.
3. Retrieve a student by ID.
4. Update student information.
5. Delete a student.
6. Implement Pydantic validation.
7. Use appropriate HTTP status codes.
8. Handle missing student records using HTTPException.

## Assignment 5: pytest

**Scenario: Employee API Testing**

Write automated tests for the Employee Management API.

Requirements:

1. Test the home endpoint.
2. Test GET all employees.
3. Test GET employee by ID.
4. Test employee creation.
5. Test employee update.
6. Test employee deletion.
7. Test invalid employee IDs.
8. Test invalid request data.
9. Create reusable pytest fixtures.
10. Execute all tests using pytest.

---

# 9. Important Commands Cheat Sheet

| Purpose | Command |
|---|---|
| Create virtual environment | `python -m venv venv` |
| Activate on macOS/Linux | `source venv/bin/activate` |
| Install FastAPI | `pip install fastapi` |
| Install Uvicorn | `pip install uvicorn` |
| Install pytest | `pip install pytest` |
| Install HTTPX | `pip install httpx` |
| Run FastAPI application | `uvicorn main:app --reload` |
| Open Swagger UI | `http://127.0.0.1:8000/docs` |
| Open ReDoc | `http://127.0.0.1:8000/redoc` |
| View OpenAPI JSON | `http://127.0.0.1:8000/openapi.json` |
| Execute all tests | `pytest` |
| Execute tests in verbose mode | `pytest -v` |
| Execute a specific test file | `pytest tests/test_employee.py` |

---

# 10. Final Summary

| Topic | Key Concept | FastAPI Component |
|---|---|---|
| Routers | Organize API endpoints into modules | `APIRouter` |
| Middleware | Process requests and responses | `@app.middleware("http")` |
| Swagger UI | Interactive API documentation | `/docs` |
| OpenAPI | Standard API specification | `/openapi.json` |
| REST API | Communication between applications using HTTP | GET, POST, PUT, PATCH, DELETE |
| pytest | Automated Python testing | `pytest` |
| API Testing | Test REST endpoints | `TestClient` |
| Fixtures | Reusable test setup and data | `@pytest.fixture` |
| Parameterization | Run tests with multiple input sets | `@pytest.mark.parametrize` |

**Recommended learning sequence:**

1. Start with REST API fundamentals and HTTP methods.
2. Build CRUD APIs using FastAPI and Pydantic.
3. Organize endpoints using APIRouter.
4. Implement middleware for logging and CORS.
5. Explore Swagger UI and OpenAPI documentation.
6. Write automated tests using pytest and TestClient.
7. Integrate a database such as MySQL or PostgreSQL.
8. Develop a complete Employee Management REST API.

This learning sequence takes students from basic FastAPI development to building and testing a modular backend application using industry-relevant practices.
