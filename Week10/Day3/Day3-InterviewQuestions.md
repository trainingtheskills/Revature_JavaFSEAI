
# 50 Must-Know FastAPI Interview Questions

## Topics Covered

- FastAPI Fundamentals
- Creating REST Endpoints
- Request and Response Models (Pydantic)
- Path and Query Parameters
- Dependency Injection
- Pydantic Models
- FastAPI Project Structure

These questions are suitable for Python beginners, junior Python developers, and candidates preparing for Python Backend Developer interviews.

---

# Section 1: FastAPI Fundamentals (Q1–Q7)

**Q1.** What is FastAPI, and why is it used in Python development?

**Q2.** What are the major features of FastAPI?

**Q3.** What is the difference between FastAPI, Flask, and Django?

**Q4.** Why is FastAPI considered a high-performance Python web framework?

**Q5.** What is ASGI, and how is it different from WSGI?

**Q6.** What is Uvicorn, and why do we use it with FastAPI?

**Q7.** How can you start a FastAPI application using Uvicorn? Explain the command:

```bash
uvicorn main:app --reload
```

---

# Section 2: Creating REST Endpoints (Q8–Q15)

**Q8.** What is a REST API? Explain its importance in application development.

**Q9.** What is an API endpoint in FastAPI?

**Q10.** What is the purpose of the `@app.get()` decorator?

**Q11.** Explain the difference between GET, POST, PUT, PATCH, and DELETE HTTP methods.

**Q12.** How do you create a GET endpoint in FastAPI? Write a simple example.

**Q13.** How do you create a POST endpoint that accepts JSON data?

**Q14.** What is the purpose of the `HTTPException` class in FastAPI? How do you return a 404 error?

**Q15.** What is the difference between HTTP status codes 200, 201, 400, 401, 403, 404, and 500?

---

# Section 3: Request and Response Models using Pydantic (Q16–Q23)

**Q16.** What is Pydantic, and why is it used with FastAPI?

**Q17.** What is the purpose of `BaseModel` in Pydantic?

**Q18.** How do you create a Pydantic model for an Employee entity?

**Q19.** How does FastAPI validate incoming JSON request data using a Pydantic model?

**Q20.** What happens when a client sends an incorrect data type in a request body?

**Q21.** What is the difference between a request model and a response model?

**Q22.** What is the purpose of `response_model` in FastAPI? Explain with an example.

**Q23.** What is the difference between `model_dump()` and `model_validate()` in Pydantic V2?

---

# Section 4: Path and Query Parameters (Q24–Q30)

**Q24.** What are path parameters in FastAPI? Explain with an example.

**Q25.** What are query parameters in FastAPI? Explain with an example.

**Q26.** What is the difference between path parameters and query parameters?

**Q27.** How does FastAPI automatically convert path and query parameters into Python data types?

**Q28.** How can you define optional query parameters in FastAPI?

**Q29.** How can you accept multiple query parameters in a single endpoint?

**Q30.** What happens if a path parameter declared as an integer receives a string value?

---

# Section 5: Dependency Injection (Q31–Q36)

**Q31.** What is Dependency Injection (DI), and why is it important in application development?

**Q32.** What is the purpose of `Depends()` in FastAPI?

**Q33.** How do you create a simple dependency function in FastAPI?

**Q34.** Can multiple endpoints share the same dependency function? Explain with an example.

**Q35.** How can dependencies be used for authentication and authorization?

**Q36.** What is the difference between dependency injection and directly calling a function inside an endpoint?

---

# Section 6: Pydantic Models (Q37–Q43)

**Q37.** What is the difference between a Python dictionary and a Pydantic model?

**Q38.** How do you define required and optional fields in a Pydantic model?

**Q39.** What is the difference between `str`, `int`, `float`, and `bool` type annotations in Pydantic models?

**Q40.** How do you define default values for fields in a Pydantic model?

**Q41.** What is field validation in Pydantic? Explain the purpose of `Field()`.

**Q42.** What is the difference between `model_dump()` and converting a Pydantic model using `dict()`?

**Q43.** How can you create nested Pydantic models? Explain with an example.

---

# Section 7: FastAPI Project Structure (Q44–Q50)

**Q44.** What is a recommended folder structure for a FastAPI project?

**Q45.** Why should we separate API routes from business logic?

**Q46.** What is the purpose of `APIRouter` in FastAPI?

**Q47.** How do you create separate route files for Employee and Product modules?

**Q48.** What is the purpose of `__init__.py` in a Python package?

**Q49.** What is the role of `requirements.txt` in a FastAPI project?

**Q50.** How do you organize database configuration, models, schemas, routes, and services in a scalable FastAPI application?

---

# Recommended Practical Interview Preparation

Practice these five tasks alongside the theoretical questions.

| No. | Practical Task | Concepts Tested |
|---|---|---|
| 1 | Create an Employee REST API with GET and POST | REST endpoints |
| 2 | Add PUT and DELETE operations | CRUD |
| 3 | Add search by department and salary | Query parameters |
| 4 | Create EmployeeIn and EmployeeOut models | Pydantic |
| 5 | Create a shared dependency for validating an API key | Dependency Injection |

---

# Important Concepts to Prioritize

## 1. REST Endpoints and HTTP Methods

- GET
- POST
- PUT
- PATCH
- DELETE

## 2. Pydantic and BaseModel

- Data validation
- Request models
- Response models
- `model_dump()`
- `model_validate()`

## 3. Path and Query Parameters

- Required parameters
- Optional parameters
- Type conversion
- Multiple query parameters

## 4. Dependency Injection

- `Depends()`
- Shared dependencies
- Authentication
- Authorization

## 5. FastAPI Project Structure

- `APIRouter`
- Schemas
- Services
- Database configuration
- Route separation

---

## Preparation Tip

Be ready to:

1. Explain each concept in your own words.
2. Write a small Python code example.
3. Explain what happens when a client sends an API request.
4. Demonstrate the concept using FastAPI Swagger UI.
5. Discuss practical use cases from real-world backend applications.
