
# 50 Must-Know FastAPI Interview Questions and Answers

## Topics Covered

1. FastAPI Fundamentals
2. Creating REST Endpoints
3. Request and Response Models (Pydantic)
4. Path and Query Parameters
5. Dependency Injection
6. Pydantic Models
7. FastAPI Project Structure

---

# Section 1: FastAPI Fundamentals (Q1–Q7)

### Q1. What is FastAPI, and why is it used in Python development?

**Answer:**

FastAPI is a modern, high-performance Python web framework used to develop REST APIs and backend applications.

It is built on Starlette for web functionality and Pydantic for data validation.

**Major uses:**

- Developing REST APIs.
- Building microservices.
- Creating backend services for web and mobile applications.
- Integrating AI and Machine Learning models.
- Developing asynchronous applications.

**Example:**

```python
from fastapi import FastAPI

app = FastAPI()

@app.get("/")
def home():
    return {"message": "Welcome to FastAPI"}
```

### Q2. What are the major features of FastAPI?

**Answer:**

Important features of FastAPI include:

1. **High Performance:** Supports high-performance API development.
2. **Automatic Documentation:** Generates Swagger UI and ReDoc documentation.
3. **Data Validation:** Uses Pydantic for request validation.
4. **Type Hints:** Uses Python type annotations.
5. **Asynchronous Support:** Supports `async` and `await`.
6. **Dependency Injection:** Provides a built-in dependency injection system.
7. **OpenAPI Support:** Automatically generates OpenAPI specifications.

### Q3. What is the difference between FastAPI, Flask, and Django?

**Answer:**

All three are Python web frameworks, but they serve different development requirements.

| Feature | FastAPI | Flask | Django |
|---|---|---|---|
| Type | API framework | Micro web framework | Full-stack framework |
| Performance | High | Depends on deployment and implementation | Depends on deployment and implementation |
| Data Validation | Pydantic | Usually requires extensions or manual validation | Django Forms and serializers through Django REST Framework |
| Async Support | Built-in | Supported in newer versions with limitations | Supported in newer versions |
| Documentation | Automatic OpenAPI docs | Usually requires extensions | Usually requires extensions |
| ORM | No built-in ORM | No built-in ORM | Built-in ORM |
| Best Use | REST APIs and microservices | Small web applications and APIs | Full-stack web applications |

**Example use cases:**

- FastAPI: Building an Employee REST API.
- Flask: Building a small web application.
- Django: Building a complete e-commerce application.

### Q4. Why is FastAPI considered a high-performance Python web framework?

**Answer:**

FastAPI provides high performance because it uses:

- Starlette for web request handling.
- ASGI for asynchronous server communication.
- Pydantic for efficient data validation.
- Asynchronous programming using `async` and `await`.

It can handle concurrent I/O operations efficiently.

**Example:**

```python
@app.get("/products")
async def get_products():
    return {"message": "Products retrieved"}
```

### Q5. What is ASGI, and how is it different from WSGI?

**Answer:**

**ASGI (Asynchronous Server Gateway Interface)** and **WSGI (Web Server Gateway Interface)** define how Python web applications communicate with web servers.

| Feature | WSGI | ASGI |
|---|---|---|
| Full Form | Web Server Gateway Interface | Asynchronous Server Gateway Interface |
| Programming Model | Traditionally synchronous | Supports synchronous and asynchronous applications |
| WebSockets | Not natively supported | Supported |
| Async Support | Limited | Built-in |
| Common Framework | Flask | FastAPI |

**Important:**

FastAPI is an ASGI-compatible framework.

### Q6. What is Uvicorn, and why do we use it with FastAPI?

**Answer:**

Uvicorn is a lightweight ASGI web server used to run FastAPI applications.

FastAPI defines the application and its endpoints, while Uvicorn runs the application and handles incoming HTTP connections.

**Install Uvicorn:**

```bash
pip install uvicorn
```

**Start the application:**

```bash
uvicorn main:app --reload
```

### Q7. How can you start a FastAPI application using Uvicorn? Explain the command.

**Answer:**

Command:

```bash
uvicorn main:app --reload
```

Explanation:

| Command | Description |
|---|---|
| `uvicorn` | Starts the Uvicorn server |
| `main` | Refers to the main.py Python file |
| `app` | Refers to the FastAPI application object |
| `--reload` | Automatically restarts the server when code changes |

Suppose `main.py` contains:

```python
from fastapi import FastAPI

app = FastAPI()
```

After starting the server, open:

- Application: http://localhost:8000/
- Swagger UI: http://localhost:8000/docs
- ReDoc: http://localhost:8000/redoc

---

# Section 2: Creating REST Endpoints (Q8–Q15)

### Q8. What is a REST API? Explain its importance in application development.

**Answer:**

REST stands for **Representational State Transfer**.

A REST API allows different software applications to communicate over HTTP using standard methods.

For example:

An Employee Management System wants to retrieve employee information from a Payroll Management System.

It can call:

```http
GET /api/employees/101
```

The API may return:

```json
{
    "id": 101,
    "name": "Ashu",
    "department": "IT",
    "salary": 50000
}
```

**Benefits of REST APIs:**

- Communication between independent applications.
- Platform-independent integration.
- Standard HTTP methods.
- Support for JSON data exchange.
- Separation of frontend and backend applications.

### Q9. What is an API endpoint in FastAPI?

**Answer:**

An API endpoint is a specific URL through which a client can interact with an application.

An endpoint generally consists of:

- HTTP method.
- URL path.
- Request parameters or body.
- Response data.

**Example:**

```python
@app.get("/employees")
def get_employees():
    return [
        {"id": 1, "name": "John"},
        {"id": 2, "name": "David"}
    ]
```

Here:

- HTTP Method: GET
- Endpoint: `/employees`
- Function: `get_employees()`

### Q10. What is the purpose of the @app.get() decorator?

**Answer:**

The `@app.get()` decorator maps an HTTP GET request to a Python function.

It is commonly used to retrieve information from an application.

**Example:**

```python
@app.get("/employees")
def get_employees():
    return {"message": "Employee list"}
```

When a client sends a GET request to:

```text
http://localhost:8000/employees
```

FastAPI executes the `get_employees()` function.

Other commonly used decorators are:

| Decorator | HTTP Method | Purpose |
|---|---|---|
| `@app.get()` | GET | Retrieve data |
| `@app.post()` | POST | Create data |
| `@app.put()` | PUT | Replace or update data |
| `@app.patch()` | PATCH | Partially update data |
| `@app.delete()` | DELETE | Delete data |

### Q11. Explain the difference between GET, POST, PUT, PATCH, and DELETE HTTP methods.

**Answer:**

These are standard HTTP methods used to perform CRUD operations.

| HTTP Method | Purpose | Example |
|---|---|---|
| GET | Retrieve records | Get all employees |
| POST | Create a new record | Add an employee |
| PUT | Replace an existing record | Update employee details |
| PATCH | Partially update a record | Update employee salary |
| DELETE | Remove a record | Delete an employee |

**CRUD Mapping:**

| CRUD Operation | HTTP Method |
|---|---|
| Create | POST |
| Read | GET |
| Update | PUT / PATCH |
| Delete | DELETE |

### Q12. How do you create a GET endpoint in FastAPI? Write a simple example.

**Answer:**

Use the `@app.get()` decorator.

**Example:**

```python
from fastapi import FastAPI

app = FastAPI()

@app.get("/products")
def get_products():

    products = [
        {"id": 1, "name": "Laptop"},
        {"id": 2, "name": "Mobile"}
    ]

    return products
```

**URL:**

```text
http://localhost:8000/products
```

**Output:**

```json
[
    {
        "id": 1,
        "name": "Laptop"
    },
    {
        "id": 2,
        "name": "Mobile"
    }
]
```

### Q13. How do you create a POST endpoint that accepts JSON data?

**Answer:**

A POST endpoint can accept JSON data through a Pydantic model.

**Step 1: Create a Pydantic model.**

```python
from pydantic import BaseModel

class Employee(BaseModel):
    name: str
    department: str
    salary: float
```

**Step 2: Create the POST endpoint.**

```python
@app.post("/employees")
def add_employee(employee: Employee):

    return {
        "message": "Employee added successfully",
        "employee": employee.model_dump()
    }
```

**Request URL:**

```text
http://localhost:8000/employees
```

**Request Body:**

```json
{
    "name": "John",
    "department": "IT",
    "salary": 50000
}
```

FastAPI receives the JSON request body and Pydantic validates it using the `Employee` model.

### Q14. What is the purpose of the HTTPException class in FastAPI? How do you return a 404 error?

**Answer:**

`HTTPException` is used to return HTTP error responses from FastAPI endpoints.

It allows developers to specify:

- HTTP status code.
- Error details.
- Optional response headers.

**Example:**

```python
from fastapi import FastAPI, HTTPException

app = FastAPI()

@app.get("/employees/{employee_id}")
def get_employee(employee_id: int):

    if employee_id != 101:
        raise HTTPException(
            status_code=404,
            detail="Employee not found"
        )

    return {
        "id": 101,
        "name": "John"
    }
```

**Output when employee is not found:**

```json
{
    "detail": "Employee not found"
}
```

### Q15. What is the difference between HTTP status codes 200, 201, 400, 401, 403, 404, and 500?

**Answer:**

HTTP status codes indicate the result of an HTTP request.

| Status Code | Name | Description |
|---|---|---|
| 200 | OK | Request completed successfully |
| 201 | Created | New resource created successfully |
| 400 | Bad Request | Invalid request |
| 401 | Unauthorized | Authentication is required or invalid |
| 403 | Forbidden | Client is not permitted to access the resource |
| 404 | Not Found | Requested resource does not exist |
| 500 | Internal Server Error | Unexpected server-side error |

**Example:**

```python
@app.post("/employees", status_code=201)
def create_employee():
    return {"message": "Employee created"}
```

---

# Section 3: Request and Response Models using Pydantic (Q16–Q23)

### Q16. What is Pydantic, and why is it used with FastAPI?

**Answer:**

Pydantic is a Python library used for data validation, parsing, and serialization.

FastAPI integrates Pydantic to validate incoming request data and define response structures.

**Major benefits:**

- Automatic data validation.
- Type checking and conversion where supported.
- Required and optional fields.
- Nested data models.
- Clear error messages.
- JSON Schema generation.

**Example:**

```python
from pydantic import BaseModel

class Employee(BaseModel):
    name: str
    age: int
    salary: float
```

### Q17. What is the purpose of BaseModel in Pydantic?

**Answer:**

`BaseModel` is the base class provided by Pydantic.

Developers inherit from `BaseModel` to create structured data models.

**Example:**

```python
from pydantic import BaseModel

class Employee(BaseModel):
    name: str
    age: int
    department: str
```

When an object is created, Pydantic validates the supplied values against the declared field types.

```python
employee = Employee(
    name="John",
    age=25,
    department="IT"
)

print(employee.name)
print(employee.age)
```

### Q18. How do you create a Pydantic model for an Employee entity?

**Answer:**

Create a class that inherits from `BaseModel`.

```python
from pydantic import BaseModel, Field

class Employee(BaseModel):

    id: int
    name: str
    department: str
    salary: float = Field(gt=0)
```

**Explanation:**

- `id`: Employee identification number.
- `name`: Employee name.
- `department`: Employee department.
- `salary`: Employee salary.
- `Field(gt=0)`: Salary must be greater than zero.

### Q19. How does FastAPI validate incoming JSON request data using a Pydantic model?

**Answer:**

FastAPI automatically uses Pydantic when a Pydantic model is declared as an endpoint parameter.

**Example:**

```python
from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()

class Employee(BaseModel):
    name: str
    age: int

@app.post("/employees")
def add_employee(employee: Employee):

    return employee.model_dump()
```

**Request Body:**

```json
{
    "name": "John",
    "age": 25
}
```

**Validation Process:**

1. Client sends JSON data.
2. FastAPI receives the request body.
3. Pydantic validates the data.
4. Pydantic creates an Employee object.
5. Endpoint function receives the validated object.
6. The endpoint returns a response.

### Q20. What happens when a client sends an incorrect data type in a request body?

**Answer:**

If the incoming data cannot be validated against the Pydantic model, FastAPI returns a validation error.

The default HTTP status code is **422 Unprocessable Entity**.

**Example model:**

```python
class Employee(BaseModel):
    name: str
    age: int
```

**Incorrect request:**

```json
{
    "name": "John",
    "age": "Twenty Five"
}
```

**Possible error response:**

```json
{
    "detail": [
        {
            "type": "int_parsing",
            "loc": ["body", "age"],
            "msg": "Input should be a valid integer",
            "input": "Twenty Five"
        }
    ]
}
```

Pydantic may automatically convert compatible values, but it rejects values that cannot be validated.

### Q21. What is the difference between a request model and a response model?

**Answer:**

A request model defines the structure of incoming data, while a response model defines the structure of outgoing data.

**Example:**

```python
from pydantic import BaseModel

class EmployeeIn(BaseModel):
    name: str
    department: str
    salary: float

class EmployeeOut(BaseModel):
    id: int
    name: str
    department: str
```

**Usage:**

```python
@app.post("/employees", response_model=EmployeeOut)
def add_employee(employee: EmployeeIn):

    return {
        "id": 101,
        "name": employee.name,
        "department": employee.department,
        "salary": employee.salary
    }
```

Here:

- `EmployeeIn` validates incoming data.
- `EmployeeOut` controls the response structure.
- The salary field is not included in the response.

### Q22. What is the purpose of response_model in FastAPI? Explain with an example.

**Answer:**

`response_model` defines the expected structure of an API response.

It is used for:

- Response validation.
- Response serialization.
- Filtering unwanted fields.
- Generating API documentation.

**Example:**

```python
from pydantic import BaseModel

class EmployeeOut(BaseModel):
    id: int
    name: str
    department: str

@app.get(
    "/employees/{employee_id}",
    response_model=EmployeeOut
)
def get_employee(employee_id: int):

    return {
        "id": employee_id,
        "name": "John",
        "department": "IT",
        "password": "secret123"
    }
```

**Actual response:**

```json
{
    "id": 101,
    "name": "John",
    "department": "IT"
}
```

The `password` field is excluded because it is not defined in `EmployeeOut`.

### Q23. What is the difference between model_dump() and model_validate() in Pydantic V2?

**Answer:**

Both methods are used in Pydantic V2 but perform different operations.

| Feature | model_dump() | model_validate() |
|---|---|---|
| Purpose | Converts model into Python data | Validates input and creates a model |
| Input | Pydantic model instance | Python object or dictionary |
| Output | Python dictionary by default | Pydantic model instance |
| Usage | Serialization | Validation |

**Example of model_dump():**

```python
employee = Employee(
    name="John",
    age=25
)

data = employee.model_dump()

print(data)
```

Output:

```python
{
    "name": "John",
    "age": 25
}
```

**Example of model_validate():**

```python
data = {
    "name": "David",
    "age": 30
}

employee = Employee.model_validate(data)

print(employee.name)
```

Output:

```text
David
```

---

# Section 4: Path and Query Parameters (Q24–Q30)

### Q24. What are path parameters in FastAPI? Explain with an example.

**Answer:**

Path parameters are variables included directly in the URL path.

They are commonly used to identify a specific resource.

**Example:**

```python
@app.get("/employees/{employee_id}")
def get_employee(employee_id: int):

    return {
        "employee_id": employee_id
    }
```

**URL:**

```text
http://localhost:8000/employees/101
```

**Output:**

```json
{
    "employee_id": 101
}
```

Here, `101` is the path parameter.

### Q25. What are query parameters in FastAPI? Explain with an example.

**Answer:**

Query parameters are values passed after the `?` symbol in a URL.

They are commonly used for filtering, searching, sorting, and pagination.

**Example:**

```python
@app.get("/employees")
def get_employees(department: str):

    return {
        "department": department
    }
```

**URL:**

```text
http://localhost:8000/employees?department=IT
```

**Output:**

```json
{
    "department": "IT"
}
```

Here, `department=IT` is a query parameter.

### Q26. What is the difference between path parameters and query parameters?

**Answer:**

| Feature | Path Parameter | Query Parameter |
|---|---|---|
| Location | Inside URL path | After `?` |
| Purpose | Identify a resource | Filter or search resources |
| Syntax | `{employee_id}` | `department=IT` |
| Example | `/employees/101` | `/employees?department=IT` |
| Required | Normally required | Can be optional |

**Path parameter example:**

```python
@app.get("/employees/{employee_id}")
def get_employee(employee_id: int):
    return {"id": employee_id}
```

**Query parameter example:**

```python
@app.get("/employees")
def search_employee(name: str):
    return {"name": name}
```

### Q27. How does FastAPI automatically convert path and query parameters into Python data types?

**Answer:**

FastAPI uses Python type annotations to validate and convert incoming parameter values.

**Example:**

```python
@app.get("/products/{product_id}")
def get_product(product_id: int):

    return {
        "product_id": product_id,
        "type": str(type(product_id))
    }
```

URL:

```text
http://localhost:8000/products/101
```

FastAPI converts the string representation `"101"` from the URL into the Python integer `101`.

If conversion is not possible, FastAPI returns a validation error.

### Q28. How can you define optional query parameters in FastAPI?

**Answer:**

Optional query parameters can be defined using `None` as the default value.

In Python 3.10+, use `str | None`.

**Example:**

```python
@app.get("/employees")
def get_employees(
    department: str | None = None
):

    if department:
        return {
            "message": f"Employees in {department}"
        }

    return {
        "message": "All employees"
    }
```

**URL with parameter:**

```text
http://localhost:8000/employees?department=IT
```

**URL without parameter:**

```text
http://localhost:8000/employees
```

Both requests are valid.

### Q29. How can you accept multiple query parameters in a single endpoint?

**Answer:**

Declare multiple function parameters in the endpoint.

FastAPI automatically treats simple scalar parameters as query parameters when they are not part of the path.

**Example:**

```python
@app.get("/employees/search")
def search_employees(
    department: str,
    min_salary: float,
    city: str
):

    return {
        "department": department,
        "min_salary": min_salary,
        "city": city
    }
```

**URL:**

```text
http://localhost:8000/employees/search?department=IT&min_salary=40000&city=Pune
```

**Output:**

```json
{
    "department": "IT",
    "min_salary": 40000,
    "city": "Pune"
}
```

### Q30. What happens if a path parameter declared as an integer receives a string value?

**Answer:**

FastAPI attempts to validate and convert the path parameter into an integer.

If the supplied value cannot be converted, FastAPI returns a validation error with HTTP status code **422**.

**Example:**

```python
@app.get("/employees/{employee_id}")
def get_employee(employee_id: int):

    return {
        "id": employee_id
    }
```

**Valid URL:**

```text
http://localhost:8000/employees/101
```

**Invalid URL:**

```text
http://localhost:8000/employees/abc
```

The second URL generates a validation error because `"abc"` cannot be converted into an integer.

**Important: Route Ordering**

Static routes should be declared before conflicting dynamic routes.

Incorrect order:

```python
@app.get("/books/{book_id}")
def get_book(book_id: int):
    return {"id": book_id}

@app.get("/books/query")
def search_books():
    return {"message": "Search books"}
```

Correct order:

```python
@app.get("/books/query")
def search_books():
    return {"message": "Search books"}

@app.get("/books/{book_id}")
def get_book(book_id: int):
    return {"id": book_id}
```

---

# Section 5: Dependency Injection (Q31–Q36)

### Q31. What is Dependency Injection (DI), and why is it important in application development?

**Answer:**

Dependency Injection is a design technique in which an object or function receives the services or resources it needs from an external source instead of creating them internally.

**Benefits:**

- Code reusability.
- Loose coupling.
- Easier testing.
- Centralized resource management.
- Better maintainability.

**Real-world example:**

Suppose multiple API endpoints require database access.

Instead of creating a database connection separately in every endpoint, a shared dependency can provide the connection.

### Q32. What is the purpose of Depends() in FastAPI?

**Answer:**

`Depends()` is a function provided by FastAPI to declare dependencies.

FastAPI executes the dependency and provides its result to the endpoint.

**Example:**

```python
from fastapi import FastAPI, Depends

app = FastAPI()

def get_message():
    return "Welcome to FastAPI"

@app.get("/")
def home(message: str = Depends(get_message)):

    return {
        "message": message
    }
```

**Output:**

```json
{
    "message": "Welcome to FastAPI"
}
```

Here, FastAPI executes `get_message()` and injects its return value into the endpoint.

### Q33. How do you create a simple dependency function in FastAPI?

**Answer:**

Create a normal Python function and use `Depends()` to inject it into an endpoint.

**Example:**

```python
from fastapi import FastAPI, Depends

app = FastAPI()

def get_current_user():

    return {
        "id": 101,
        "username": "John"
    }

@app.get("/profile")
def get_profile(
    user: dict = Depends(get_current_user)
):

    return user
```

The dependency function provides the user information to the endpoint.

### Q34. Can multiple endpoints share the same dependency function? Explain with an example.

**Answer:**

Yes. FastAPI allows multiple endpoints to reuse the same dependency function.

**Example:**

```python
from fastapi import FastAPI, Depends

app = FastAPI()

def get_current_user():

    return {
        "username": "John"
    }

@app.get("/profile")
def profile(
    user: dict = Depends(get_current_user)
):
    return user

@app.get("/dashboard")
def dashboard(
    user: dict = Depends(get_current_user)
):
    return {
        "message": "Dashboard",
        "user": user
    }
```

Both endpoints use the same `get_current_user()` dependency.

By default, FastAPI caches the result of a dependency within a single request when the same dependency is used multiple times.

### Q35. How can dependencies be used for authentication and authorization?

**Answer:**

Dependencies can be used to check user credentials, validate API keys, and verify access permissions before executing an endpoint.

**Example: API Key Validation**

```python
from fastapi import FastAPI, Depends, Header, HTTPException

app = FastAPI()

def verify_api_key(
    api_key: str = Header()
):

    if api_key != "my-secret-key":
        raise HTTPException(
            status_code=401,
            detail="Invalid API Key"
        )

    return api_key

@app.get("/secure-data")
def secure_data(
    key: str = Depends(verify_api_key)
):

    return {
        "message": "Access granted"
    }
```

The client must send the correct `api-key` HTTP header.

**Important:**

This is a simplified demonstration. Real applications should use securely managed secrets and appropriate authentication mechanisms.

### Q36. What is the difference between dependency injection and directly calling a function inside an endpoint?

**Answer:**

| Feature | Dependency Injection | Direct Function Call |
|---|---|---|
| Execution | Managed by FastAPI | Called manually |
| Reusability | Easily shared across endpoints | Requires manual calls |
| Testing | Supports dependency overrides | Usually requires manual mocking |
| Resource Management | Supports dependency lifecycle and cleanup | Must be handled manually |
| Integration | Uses `Depends()` | Uses normal function calls |

**Dependency Injection example:**

```python
def get_user():
    return "John"

@app.get("/profile")
def profile(user: str = Depends(get_user)):
    return {"user": user}
```

**Direct function call:**

```python
def get_user():
    return "John"

@app.get("/profile")
def profile():
    user = get_user()
    return {"user": user}
```

Both can work, but dependency injection provides better integration with FastAPI.

---

# Section 6: Pydantic Models (Q37–Q43)

### Q37. What is the difference between a Python dictionary and a Pydantic model?

**Answer:**

A Python dictionary stores key-value pairs, whereas a Pydantic model defines a structured data format with validation rules.

| Feature | Python Dictionary | Pydantic Model |
|---|---|---|
| Structure | Key-value pairs | Defined fields |
| Type Validation | Not automatic | Automatic validation |
| Required Fields | Not enforced | Enforced by default |
| Data Conversion | Manual | Supports automatic conversion |
| Serialization | Dictionary already | `model_dump()` |
| Validation Errors | Manual checks required | Structured validation errors |

**Dictionary example:**

```python
employee = {
    "name": "John",
    "age": 25
}
```

**Pydantic model example:**

```python
from pydantic import BaseModel

class Employee(BaseModel):
    name: str
    age: int

employee = Employee(
    name="John",
    age=25
)
```

### Q38. How do you define required and optional fields in a Pydantic model?

**Answer:**

In Pydantic V2:

- A field without a default value is required.
- A field with a default value is optional to provide.
- `str | None` allows a field to contain either a string or `None`.

**Example:**

```python
from pydantic import BaseModel

class Employee(BaseModel):

    name: str
    age: int

    city: str | None = None
```

Here:

- `name` is required.
- `age` is required.
- `city` is optional and defaults to `None`.

**Valid input:**

```python
employee = Employee(
    name="John",
    age=25
)
```

### Q39. What is the difference between str, int, float, and bool type annotations in Pydantic models?

**Answer:**

These are Python data types used to define the expected type of a field.

| Data Type | Description | Example |
|---|---|---|
| `str` | String value | `"John"` |
| `int` | Integer value | `25` |
| `float` | Decimal or floating-point value | `45000.50` |
| `bool` | Boolean value | `True` or `False` |

**Example:**

```python
from pydantic import BaseModel

class Employee(BaseModel):

    name: str
    age: int
    salary: float
    is_active: bool
```

### Q40. How do you define default values for fields in a Pydantic model?

**Answer:**

Default values can be assigned directly in the model definition.

**Example:**

```python
from pydantic import BaseModel

class Employee(BaseModel):

    name: str
    department: str = "IT"
    is_active: bool = True
```

**Creating an object:**

```python
employee = Employee(
    name="John"
)

print(employee.department)
print(employee.is_active)
```

**Output:**

```text
IT
True
```

Pydantic uses the default values when the client does not provide those fields.

### Q41. What is field validation in Pydantic? Explain the purpose of Field().

**Answer:**

Field validation is the process of checking whether incoming data satisfies the defined validation rules.

Pydantic provides `Field()` to define additional constraints and metadata.

**Example:**

```python
from pydantic import BaseModel, Field

class Employee(BaseModel):

    name: str = Field(min_length=3)

    age: int = Field(
        gt=18,
        lt=60
    )

    salary: float = Field(gt=0)
```

**Explanation:**

| Validation | Meaning |
|---|---|
| `min_length=3` | Name must contain at least 3 characters |
| `gt=18` | Age must be greater than 18 |
| `lt=60` | Age must be less than 60 |
| `gt=0` | Salary must be greater than zero |

**Invalid input:**

```python
employee = Employee(
    name="Jo",
    age=15,
    salary=-100
)
```

Pydantic raises a validation error.

### Q42. What is the difference between model_dump() and converting a Pydantic model using dict()?

**Answer:**

In Pydantic V2, `model_dump()` is the recommended method for converting a model into a Python dictionary.

| Feature | model_dump() | dict() |
|---|---|---|
| Pydantic V2 | Recommended | Deprecated compatibility method |
| Purpose | Converts model into dictionary | Converts model into dictionary |
| Recommendation | Use this method | Avoid in new code |

**Example:**

```python
from pydantic import BaseModel

class Employee(BaseModel):
    name: str
    age: int

employee = Employee(
    name="John",
    age=25
)

data = employee.model_dump()

print(data)
```

Output:

```python
{
    "name": "John",
    "age": 25
}
```

### Q43. How can you create nested Pydantic models? Explain with an example.

**Answer:**

A nested Pydantic model is a model that contains another Pydantic model as one of its fields.

It is useful when working with complex JSON structures.

**Example:**

```python
from pydantic import BaseModel

class Address(BaseModel):

    city: str
    state: str
    pincode: int

class Employee(BaseModel):

    name: str
    department: str
    address: Address
```

**Creating an object:**

```python
employee = Employee(
    name="John",
    department="IT",
    address={
        "city": "Pune",
        "state": "Maharashtra",
        "pincode": 411001
    }
)

print(employee.address.city)
```

**Output:**

```text
Pune
```

Pydantic automatically validates the nested address data.

---

# Section 7: FastAPI Project Structure (Q44–Q50)

### Q44. What is a recommended folder structure for a FastAPI project?

**Answer:**

A FastAPI application should be organized into separate modules for routes, schemas, services, and database operations.

**Example project structure:**

```text
FastAPIProject/
│
├── main.py
│
├── app/
│   ├── __init__.py
│   │
│   ├── routers/
│   │   ├── __init__.py
│   │   ├── employee.py
│   │   └── product.py
│   │
│   ├── schemas/
│   │   ├── __init__.py
│   │   ├── employee.py
│   │   └── product.py
│   │
│   ├── services/
│   │   ├── __init__.py
│   │   ├── employee_service.py
│   │   └── product_service.py
│   │
│   ├── models/
│   │   ├── __init__.py
│   │   └── employee.py
│   │
│   └── database.py
│
├── requirements.txt
└── README.md
```

**Purpose of important folders:**

| Folder/File | Responsibility |
|---|---|
| `main.py` | Application entry point |
| `routers/` | API endpoint definitions |
| `schemas/` | Pydantic request and response models |
| `services/` | Business logic |
| `models/` | Database ORM models |
| `database.py` | Database connection and configuration |
| `requirements.txt` | Python dependencies |

### Q45. Why should we separate API routes from business logic?

**Answer:**

Separating routes from business logic improves application organization and maintainability.

**Without separation:**

API endpoints contain request handling, business rules, and database operations together.

**With separation:**

- Router handles HTTP requests and responses.
- Service handles business logic.
- Repository or database layer handles data access.

**Example:**

`services/employee_service.py`

```python
def get_employee_by_id(employee_id: int):

    return {
        "id": employee_id,
        "name": "John",
        "department": "IT"
    }
```

`routers/employee.py`

```python
from fastapi import APIRouter
from app.services.employee_service import get_employee_by_id

router = APIRouter()

@router.get("/{employee_id}")
def get_employee(employee_id: int):

    return get_employee_by_id(employee_id)
```

This separation makes code easier to test, reuse, and maintain.

### Q46. What is the purpose of APIRouter in FastAPI?

**Answer:**

`APIRouter` is used to organize API endpoints into separate modules.

It allows developers to divide a large application into smaller functional modules.

**Example:**

```python
from fastapi import APIRouter

router = APIRouter()

@router.get("/employees")
def get_employees():

    return {
        "message": "Employee list"
    }
```

Register the router in `main.py`:

```python
from fastapi import FastAPI
from app.routers.employee import router as employee_router

app = FastAPI()

app.include_router(employee_router)
```

Now the employee endpoint is available at:

```text
http://localhost:8000/employees
```

### Q47. How do you create separate route files for Employee and Product modules?

**Answer:**

Create separate Python files for each module and define their endpoints using `APIRouter`.

**Step 1: Create employee.py**

```python
from fastapi import APIRouter

router = APIRouter(
    prefix="/employees",
    tags=["Employees"]
)

@router.get("/")
def get_employees():

    return [
        {"id": 1, "name": "John"},
        {"id": 2, "name": "David"}
    ]
```

**Step 2: Create product.py**

```python
from fastapi import APIRouter

router = APIRouter(
    prefix="/products",
    tags=["Products"]
)

@router.get("/")
def get_products():

    return [
        {"id": 101, "name": "Laptop"},
        {"id": 102, "name": "Mobile"}
    ]
```

**Step 3: Register both routers in main.py**

```python
from fastapi import FastAPI

from app.routers.employee import router as employee_router
from app.routers.product import router as product_router

app = FastAPI()

app.include_router(employee_router)
app.include_router(product_router)
```

**Available endpoints:**

| Endpoint | Description |
|---|---|
| `/employees/` | Get employees |
| `/products/` | Get products |

### Q48. What is the purpose of __init__.py in a Python package?

**Answer:**

`__init__.py` is a Python file commonly used to mark a directory as a regular Python package.

It can also be used to:

- Initialize package-level code.
- Expose selected classes and functions.
- Simplify imports.
- Define package-level variables.

**Example structure:**

```text
EmployeeManagement/
│
├── main.py
│
└── employee/
    ├── __init__.py
    ├── employee.py
    └── service.py
```

**Example import:**

```python
from employee.employee import Employee
```

**Important:**

Python 3.3 and later also support namespace packages without `__init__.py`, but using it is still common in application projects.

### Q49. What is the role of requirements.txt in a FastAPI project?

**Answer:**

`requirements.txt` contains the external Python packages and their version constraints required by an application.

It helps developers install the required dependencies consistently.

**Example requirements.txt:**

```text
fastapi
uvicorn
pydantic
```

**Install all dependencies:**

```bash
pip install -r requirements.txt
```

**Generate requirements.txt from the current environment:**

```bash
pip freeze > requirements.txt
```

**Benefits:**

- Dependency management.
- Project setup.
- Easier deployment.
- Sharing development environments.

### Q50. How do you organize database configuration, models, schemas, routes, and services in a scalable FastAPI application?

**Answer:**

A scalable FastAPI application should separate responsibilities into different layers.

A common layered architecture is:

1. **Routes Layer:** Handles HTTP requests and responses.
2. **Schemas Layer:** Defines Pydantic request and response models.
3. **Services Layer:** Contains business logic.
4. **Repository/Database Layer:** Handles database operations.
5. **Models Layer:** Defines database ORM entities.
6. **Configuration Layer:** Manages environment variables and application settings.

**Example architecture:**

```text
FastAPIProject/
│
├── main.py
│
├── app/
│   ├── routers/
│   │   └── employee.py
│   │
│   ├── schemas/
│   │   └── employee.py
│   │
│   ├── services/
│   │   └── employee_service.py
│   │
│   ├── repositories/
│   │   └── employee_repository.py
│   │
│   ├── models/
│   │   └── employee.py
│   │
│   ├── config/
│   │   └── settings.py
│   │
│   └── database.py
│
├── requirements.txt
└── README.md
```

**Request Flow:**

```text
Client
  |
  v
API Router
  |
  v
Pydantic Schema Validation
  |
  v
Service Layer
  |
  v
Repository Layer
  |
  v
Database
  |
  v
Service Layer
  |
  v
API Response
```

**Benefits:**

- Separation of concerns.
- Code reusability.
- Easier unit testing.
- Easier maintenance.
- Better scalability.
- Easier team collaboration.

---

# Section 8: Practical Interview Assignments

Practice the following assignments to demonstrate your understanding of FastAPI.

## Assignment 1: Employee REST API

Create an Employee Management REST API with the following operations:

- GET all employees.
- GET employee by ID.
- POST a new employee.
- PUT employee details.
- DELETE an employee.

**Concepts:** REST endpoints, HTTP methods, CRUD.

## Assignment 2: Employee Search API

Create an endpoint to search employees using:

- Department.
- City.
- Minimum salary.
- Maximum salary.

**Concepts:** Path parameters and query parameters.

## Assignment 3: Pydantic Validation

Create an Employee Pydantic model with:

- Employee ID.
- Employee name.
- Email.
- Age.
- Salary.
- Department.

Add validation rules:

- Name must contain at least 3 characters.
- Age must be greater than 18.
- Salary must be greater than zero.

**Concepts:** BaseModel, Field, validation.

## Assignment 4: Request and Response Models

Create two Pydantic models:

- `EmployeeIn`
- `EmployeeOut`

The request model should accept employee information, while the response model should exclude confidential information.

**Concepts:** Request models, response models, response_model.

## Assignment 5: Dependency Injection

Create a shared dependency function that validates an API key.

Use this dependency in the following endpoints:

- `/employees`
- `/products`
- `/dashboard`

**Concepts:** Depends(), dependency reuse, authentication.

## Assignment 6: FastAPI Project Structure

Create a modular FastAPI project with:

- `main.py`
- `routers/`
- `schemas/`
- `services/`
- `models/`
- `database.py`

Create separate Employee and Product modules.

**Concepts:** APIRouter, modular development, project organization.

---

# Section 9: Quick Revision – Important FastAPI Concepts

| Concept | Description |
|---|---|
| FastAPI | Python framework for building APIs |
| Uvicorn | ASGI server |
| ASGI | Asynchronous Server Gateway Interface |
| Pydantic | Data validation library |
| BaseModel | Base class for Pydantic models |
| Depends() | Dependency injection function |
| APIRouter | Organizes API endpoints |
| HTTPException | Raises HTTP errors |
| response_model | Defines response structure |
| model_dump() | Converts Pydantic model into a dictionary |
| model_validate() | Validates input and creates a model |
| Path Parameter | Value included in the URL path |
| Query Parameter | Value passed after the `?` symbol |
| GET | Retrieves resources |
| POST | Creates resources |
| PUT | Replaces or updates a resource |
| PATCH | Partially updates a resource |
| DELETE | Deletes a resource |
| Swagger UI | Interactive API documentation |
| ReDoc | Alternative API documentation |

---

# Interview Preparation Tips

1. Understand the difference between FastAPI, Flask, and Django.
2. Practice creating REST endpoints using GET, POST, PUT, PATCH, and DELETE.
3. Understand how Pydantic validates JSON request bodies.
4. Practice writing request and response models.
5. Understand path parameters and query parameters.
6. Learn how `Depends()` works.
7. Practice organizing APIs using `APIRouter`.
8. Be comfortable explaining a complete CRUD application.
9. Understand how FastAPI handles validation errors and HTTP exceptions.
10. Practice explaining the complete request-response lifecycle.

**Final Preparation Goal:**

Be able to independently develop an Employee Management REST API using FastAPI, Pydantic, CRUD operations, dependency injection, and a modular project structure.
