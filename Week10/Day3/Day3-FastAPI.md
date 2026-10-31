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


