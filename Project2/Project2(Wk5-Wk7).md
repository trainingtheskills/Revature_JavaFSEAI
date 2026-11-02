
# Online Shopping Management System – Project Case Study

Nov 3, 2026 · @Ashu

**Summary.** The Online Shopping Management System (ShopSmart) is a three-week project in which learners build a monolithic web application on Java 17/21 and Spring Boot, using Spring Web for the REST API, Hibernate for persistence, MySQL for storage and Maven for the build. A React SPA or Angular front end, written in HTML, CSS and JavaScript or TypeScript, calls the API. The data model has three tables: `customers`, `products` and `orders`, where each order links one customer to one product. There is no login, role or security layer, so the focus stays on core Java full-stack skills. Staff keep customer and product records, customers place orders, and orders move from placed to shipped to delivered or cancelled. The key design point is that stock is reduced and the order is saved in one transaction, so stock can never go negative. The service layer is tested with JUnit 5 and Mockito, the API with Postman, and the work runs across Weeks 5 to 7 with three checkpoints, built in IntelliJ IDEA or Eclipse and graded against a 100-mark rubric.

## 1. Project Overview

This case study asks learners to build **ShopSmart**, a small online shopping system with three entities: **Customer**, **Product** and **Order**. An order belongs to one customer and is for one product. It is delivered as one Spring Boot application with a React SPA / Angular front end, and it is the Weeks 5–7 project for the Java Full Stack course.

**Business context.** A small retailer takes orders by phone and tracks customers and stock in spreadsheets. Stock counts drift, orders get lost, and nobody can see what a given customer has ordered. ShopSmart keeps customers, products and orders in one MySQL database, so every order is tied to a known customer and a known product.

**Simplifications made on purpose**

- No login, no roles and no Spring Security: every screen and endpoint is open. A customer is a stored record (name, email, phone, address), not a user account, and has no password.
- Three tables only: `customers`, `products` and `orders`. One order is for one product and a quantity.
- Payment and delivery are not modelled; an order stores a shipping address as a plain column.

**Why a monolith.** One codebase, one build, one database and one deployment unit keep the project learnable in three weeks. A single `@Transactional` method can reduce stock and save the order together, and debugging happens in one IDE session.

**Learning outcomes.** By the end, a learner can:

- Design a layered Spring Boot application (controller, service, DAO, entity).
- Design three related MySQL tables with one-to-many relationships and map them with Hibernate (SessionFactory, Session and HQL).
- Build a validated REST API with consistent error responses.
- Build a React SPA / Angular app that consumes that API.
- Test the API in Postman and the service layer with JUnit and Mockito.

## 2. Problem Statement, Objectives and Scope

**Problem.** Orders arrive by phone and are written in a spreadsheet. Nobody can see current stock, customer details are retyped on every order, orders are lost or double-booked, and a customer's order history cannot be found.

**Objectives**

- Keep customer details once and link every order to a customer.
- Let staff browse and search products and place an order for a customer.
- Keep stock accurate: an order can never take stock below zero.
- Show all orders, and the order history of one customer.
- Let staff move an order from PLACED to SHIPPED to DELIVERED, or cancel it.
- Expose a documented REST API used by the React SPA / Angular app and by Postman.

|  | In scope | Out of scope |
| --- | --- | --- |
| Customers | Create, read, update, delete, search, order history | Passwords, login, loyalty points |
| Products | Create, read, update, delete, search, pagination | Categories, images, reviews |
| Orders | Place for a customer, list, view, cancel, update status | Multiple products per order, returns |
| Users | None; all screens are open | Registration, login, roles, JWT |
| Payment and delivery | Not modelled | Payment gateway, courier tracking |
| Platform | One Spring Boot app, MySQL, React SPA / Angular | Microservices, message brokers, Docker |

**Hint:** if a learner finishes early, the stretch goals in section 14 add order items, addresses and login in that order.

## 3. Functional Requirements

Each requirement has an acceptance check that a learner can demonstrate in Postman or in the UI.

| ID | Module | Requirement | Acceptance check |
| --- | --- | --- | --- |
| FR-01 | Customer | Add a customer with name, email, phone and address | Valid request returns 201; blank name or invalid email returns 400; duplicate email returns 409 |
| FR-02 | Customer | List customers with search by name or email and pagination | `GET /api/customers?q=asha&page=0&size=5` returns a page object |
| FR-03 | Customer | View, update and delete a customer | Unknown id returns 404; deleting a customer who has orders returns 409 |
| FR-04 | Customer | View the order history of one customer | `GET /api/customers/{id}/orders` returns only that customer's orders |
| FR-05 | Product | Add a product with name, description, price and stock quantity | Valid request returns 201; blank name or negative price returns 400 |
| FR-06 | Product | List products with search by name, sort and pagination | `GET /api/products?q=mouse&page=0&size=5` returns a page object |
| FR-07 | Product | View, update and delete a product | Unknown id returns 404; deleting a product that has orders returns 409 |
| FR-08 | Order | Place an order for an existing customer and product with quantity and shipping address | Returns 201 with status PLACED and the calculated total; unknown customer or product returns 404 |
| FR-09 | Order | Stock is reduced in the same transaction that saves the order | If stock is too low, no order is saved and stock is unchanged (409) |
| FR-10 | Order | The order stores the unit price at purchase time | Changing the product price later does not change old orders |
| FR-11 | Order | List orders with optional status filter, newest first | `GET /api/orders?status=PLACED` returns only PLACED orders |
| FR-12 | Order | View one order with its customer name and product name | Unknown id returns 404 |
| FR-13 | Order | Update status along PLACED, SHIPPED, DELIVERED | An invalid jump such as DELIVERED to PLACED returns 400 |
| FR-14 | Order | Cancel an order while it is PLACED; stock is returned | Stock increases by the order quantity; SHIPPED orders cannot be cancelled |
| FR-15 | Platform | Every error uses one JSON format | Matches the format in section 8 |

**Order status flow:** PLACED → SHIPPED → DELIVERED. CANCELLED is reachable from PLACED only and is final.

## 4. Non-Functional Requirements

| Area | Requirement |
| --- | --- |
| Performance | Product and customer lists respond in under 500 ms with 1,000 seeded rows; list endpoints are always paginated |
| Data integrity | Foreign keys from `orders` to `customers` and to `products`, a unique constraint on customer email, `CHECK` constraints on price and stock, and `@Transactional` on every write that touches more than one table |
| Concurrency | `@Version` on `Product` so two simultaneous orders cannot both take the last unit |
| Maintainability | Layered packages, DTOs separate from entities, constructor injection, no business logic in controllers |
| Testability | Service rules covered by JUnit 5 and Mockito; every endpoint covered by a Postman test |
| Usability | Responsive React SPA / Angular UI that works at phone width; clear loading and error states |
| Portability | Runs with `mvn spring-boot:run` and a local MySQL; configuration in `application.properties` |
| Documentation | README with setup steps, table diagram and API list |

## 5. Technology Stack

The stack is fixed by the course. Use **either** React **or** Angular for the front end; the same screens, routes and API calls apply to both.

| Layer | Technology | Used for |
| --- | --- | --- |
| Language | Java 17 or 21 (LTS) | Back-end code; records for DTOs, `var`, text blocks |
| Framework | Spring Boot 3.x | Auto-configuration, embedded Tomcat, dependency management |
| Web | Spring Web (Spring MVC) | `@RestController`, request mapping, `@RestControllerAdvice` |
| Validation | Bean Validation (`spring-boot-starter-validation`) | `@NotBlank`, `@Min`, `@Email` on request DTOs |
| Persistence | Hibernate ORM (native Session API and HQL) | Entity mapping, DAOs, HQL queries, transactions |
| Database | MySQL 8 | Three tables, constraints, indexes |
| Front end | React SPA (React 18, Vite) or Angular 17+ | Single-page UI, routing, forms |
| Web basics | HTML5, CSS3, JavaScript (ES6+) or TypeScript | Markup, styling, API calls |
| Build | Maven | Build and package as one runnable JAR |
| API testing | Postman | Collections, environment variables, test scripts |
| Unit testing | JUnit 5, Mockito | Service-layer tests with mocked DAOs |
| IDE | IntelliJ IDEA or Eclipse | Development, debugging, database view |

**Hint:** generate the project at start.spring.io with Spring Web, JDBC API, MySQL Driver and Validation, then add hibernate-core and spring-orm to the pom.xml. Do not add Spring Data JPA or Spring Security.

## 6. Monolithic Architecture

ShopSmart is one Spring Boot application packaged as a single JAR. The React SPA / Angular app and Postman call the same REST API; each request passes the same layers and ends in one MySQL schema.

&#91;embedded content: ShopSmart monolith · 4 layers, 1 database\]

The highlighted service layer holds the business rules and transaction boundaries; controllers and DAOs stay thin.

**Request flow: placing an order**

1. The React SPA / Angular client sends `POST /api/orders` with a JSON body that holds `customerId`, `productId`, `quantity` and `shippingAddress`.
2. `OrderController` validates the request DTO (`@Valid`) and calls `OrderService.placeOrder(...)`.
3. Inside one `@Transactional` method, the service loads the customer and the product, checks stock, reduces it, and saves the order.
4. Hibernate issues the `UPDATE products` and `INSERT orders` statements; any exception rolls both back.
5. The controller returns `201 Created` with an `OrderResponse` DTO that includes the customer and product names.

**Package structure**

```text
com.annotatetech.shopsmart
├── ShopSmartApplication.java
├── config/        CorsConfig, HibernateConfig
├── controller/    CustomerController, ProductController, OrderController
├── service/       CustomerService, ProductService, OrderService
├── dao/           CustomerDao, ProductDao, OrderDao
├── entity/        Customer, Product, Order, OrderStatus (enum)
├── dto/           CustomerRequest, ProductRequest, PlaceOrderRequest, OrderResponse, PageResponse
└── exception/     ResourceNotFoundException, OutOfStockException, GlobalExceptionHandler
```

**Design rules the reviewer checks**

- Controllers never call DAOs; entities are never returned as JSON.
- DTOs are Java records.
- Any write that changes more than one table sits in one `@Transactional` service method.
- Dependencies arrive through constructor injection.

**Hint:** build Customer and Product end to end (entity, DAO, service, controller, Postman test) before touching Order. The Order work then reuses a pattern the learner already knows and only adds the two foreign keys.

## 7. Database Design

The schema `shopsmart_db` has three tables. `orders` links the other two: one customer can place many orders, and one product can appear in many orders.

**Table: `customers`**

| Column | Type | Constraints | Notes |
| --- | --- | --- | --- |
| `id` | BIGINT | PK, AUTO\_INCREMENT |  |
| `name` | VARCHAR(100) | NOT NULL |  |
| `email` | VARCHAR(120) | NOT NULL, UNIQUE | Used to find a customer |
| `phone` | VARCHAR(15) | NOT NULL | 10 digits |
| `address` | VARCHAR(300) | NOT NULL | Default shipping address |
| `created_at` | TIMESTAMP | NOT NULL, DEFAULT CURRENT\_TIMESTAMP |  |

**Table: `products`**

| Column | Type | Constraints | Notes |
| --- | --- | --- | --- |
| `id` | BIGINT | PK, AUTO\_INCREMENT |  |
| `name` | VARCHAR(150) | NOT NULL | Searched with `LIKE` |
| `description` | VARCHAR(500) | NULL |  |
| `price` | DECIMAL(10,2) | NOT NULL, CHECK (price > 0) | Use `BigDecimal` in Java |
| `stock_qty` | INT | NOT NULL, CHECK (stock\_qty >= 0) | Reduced by orders |
| `version` | BIGINT | NOT NULL, DEFAULT 0 | For `@Version` optimistic locking |
| `created_at` | TIMESTAMP | NOT NULL, DEFAULT CURRENT\_TIMESTAMP |  |

**Table: `orders`**

| Column | Type | Constraints | Notes |
| --- | --- | --- | --- |
| `id` | BIGINT | PK, AUTO\_INCREMENT |  |
| `customer_id` | BIGINT | NOT NULL, FK to `customers(id)` | Who placed the order |
| `product_id` | BIGINT | NOT NULL, FK to `products(id)` | What was ordered |
| `quantity` | INT | NOT NULL, CHECK (quantity > 0) |  |
| `unit_price` | DECIMAL(10,2) | NOT NULL | Copy of product price at purchase |
| `total_amount` | DECIMAL(10,2) | NOT NULL | `unit_price` × `quantity` |
| `shipping_address` | VARCHAR(300) | NOT NULL | Defaults to the customer's address, can differ |
| `status` | VARCHAR(20) | NOT NULL, DEFAULT 'PLACED' | PLACED, SHIPPED, DELIVERED, CANCELLED |
| `created_at` | TIMESTAMP | NOT NULL, DEFAULT CURRENT\_TIMESTAMP |  |

**Relationships:** `customers` 1 — N `orders` through `orders.customer_id`, and `products` 1 — N `orders` through `orders.product_id`. Each order therefore has exactly one customer and one product.

```sql
CREATE DATABASE shopsmart_db;
USE shopsmart_db;

CREATE TABLE customers (
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  name       VARCHAR(100) NOT NULL,
  email      VARCHAR(120) NOT NULL UNIQUE,
  phone      VARCHAR(15)  NOT NULL,
  address    VARCHAR(300) NOT NULL,
  created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
  id          BIGINT AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR(150)  NOT NULL,
  description VARCHAR(500),
  price       DECIMAL(10,2) NOT NULL CHECK (price > 0),
  stock_qty   INT           NOT NULL CHECK (stock_qty >= 0),
  version     BIGINT        NOT NULL DEFAULT 0,
  created_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (
  id               BIGINT AUTO_INCREMENT PRIMARY KEY,
  customer_id      BIGINT        NOT NULL,
  product_id       BIGINT        NOT NULL,
  quantity         INT           NOT NULL CHECK (quantity > 0),
  unit_price       DECIMAL(10,2) NOT NULL,
  total_amount     DECIMAL(10,2) NOT NULL,
  shipping_address VARCHAR(300)  NOT NULL,
  status           VARCHAR(20)   NOT NULL DEFAULT 'PLACED',
  created_at       TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id) REFERENCES customers(id),
  CONSTRAINT fk_orders_product  FOREIGN KEY (product_id)  REFERENCES products(id)
);

CREATE INDEX idx_customers_name ON customers(name);
CREATE INDEX idx_products_name ON products(name);
CREATE INDEX idx_orders_status ON orders(status);
```

**Entity-to-table mapping**

| Entity | Table | Mapping notes |
| --- | --- | --- |
| `Customer` | `customers` | `@Id` with `IDENTITY`; `email` mapped with `unique = true`; no list of orders is needed on the entity |
| `Product` | `products` | `@Id` with `IDENTITY`; `BigDecimal` for `price`; `@Version` on `version`; `stockQty` maps to `stock_qty` |
| `Order` | `orders` | `@Table(name = "orders")`; two `@ManyToOne(fetch = LAZY)` fields, `customer` with `@JoinColumn(name = "customer_id")` and `product` with `@JoinColumn(name = "product_id")`; `@Enumerated(EnumType.STRING)` on `status` |
| `OrderStatus` | none | Java enum with PLACED, SHIPPED, DELIVERED, CANCELLED; stored as text in `orders.status` |

**Hints**

- Name the table `orders`, not `order`; the second one breaks SQL unless quoted.
- Put `@ManyToOne` only on `Order`. A customer's orders are found with an HQL query, which avoids a two-way mapping.
- Use `@Enumerated(EnumType.STRING)`; the default stores a number and breaks if the enum is reordered.
- Set `hibernate.hbm2ddl.auto=validate` once the script above is in place; `update` is fine while experimenting.
- Insert 5 customers and 10 to 20 products with a `data.sql` or a one-off script so the UI is never empty.

## 8. REST API Design

All endpoints live under `/api`, return JSON and use standard HTTP status codes. Lists are paginated with `page` and `size`.

| Method | Endpoint | Purpose | Success |
| --- | --- | --- | --- |
| GET | `/api/customers` | List with `q` (name or email search), `page`, `size` | 200 |
| GET | `/api/customers/{id}` | Customer detail | 200 |
| POST | `/api/customers` | Create customer | 201 |
| PUT | `/api/customers/{id}` | Update customer | 200 |
| DELETE | `/api/customers/{id}` | Delete customer (409 if the customer has orders) | 204 |
| GET | `/api/customers/{id}/orders` | Order history of one customer | 200 |
| GET | `/api/products` | List with `q` (name search), `page`, `size`, `sort` | 200 |
| GET | `/api/products/{id}` | Product detail | 200 |
| POST | `/api/products` | Create product | 201 |
| PUT | `/api/products/{id}` | Update product, including stock | 200 |
| DELETE | `/api/products/{id}` | Delete product (409 if it has orders) | 204 |
| POST | `/api/orders` | Place an order for a customer and reduce stock | 201 |
| GET | `/api/orders` | List orders, optional `status`, `page`, `size` | 200 |
| GET | `/api/orders/{id}` | Order detail with customer and product names | 200 |
| PUT | `/api/orders/{id}/status` | Move to SHIPPED or DELIVERED | 200 |
| PUT | `/api/orders/{id}/cancel` | Cancel a PLACED order and return stock | 200 |

**Sample: place an order**

```json
POST /api/orders

{
  "customerId": 3,
  "productId": 7,
  "quantity": 2,
  "shippingAddress": "12 MG Road, Pune 411001"
}
```

```json
201 Created

{
  "orderId": 1021,
  "customerId": 3,
  "customerName": "Asha Rao",
  "productId": 7,
  "productName": "Wireless Mouse",
  "quantity": 2,
  "unitPrice": 799.00,
  "totalAmount": 1598.00,
  "shippingAddress": "12 MG Road, Pune 411001",
  "status": "PLACED",
  "createdAt": "2026-11-03T10:15:30"
}
```

**Standard error format** (returned by `GlobalExceptionHandler`)

```json
{
  "timestamp": "2026-11-03T10:15:30",
  "status": 409,
  "error": "Conflict",
  "message": "Only 1 unit of 'Wireless Mouse' is in stock",
  "path": "/api/orders"
}
```

| Status | When |
| --- | --- |
| 400 | Validation failure or invalid status change |
| 404 | Customer, product or order not found |
| 409 | Insufficient stock, duplicate customer email, or deleting a customer or product that has orders |

**Hint:** build a `PageResponse<T>` DTO in the list endpoints (Hibernate has no built-in Page type), so the JSON carries `content`, `totalElements` and `totalPages` for the React SPA / Angular pagination bar.

## 9. Frontend Design

The React SPA (Vite) or Angular app talks only to the REST API. In development it runs on port 5173 (React) or 4200 (Angular) and the API on 8080. There is no login, so there are no protected routes.

| Route | Page | API calls |
| --- | --- | --- |
| `/` | Product list with search box, pagination, Order button | `GET /api/products` |
| `/products/new`, `/products/:id/edit` | Add and edit product forms | `POST`, `GET`, `PUT /api/products` |
| `/customers` | Customer list with search and pagination | `GET /api/customers` |
| `/customers/new`, `/customers/:id/edit` | Add and edit customer forms | `POST`, `GET`, `PUT /api/customers` |
| `/customers/:id` | Customer detail with order history | `GET /api/customers/{id}`, `GET /api/customers/{id}/orders` |
| `/products/:id/order` | Order form: pick a customer, quantity, address, live total | `GET /api/products/{id}`, `GET /api/customers`, `POST /api/orders` |
| `/orders` | Orders table with status filter and status actions | `GET /api/orders`, `PUT .../status`, `PUT .../cancel` |
| `/orders/:id` | Order detail | `GET /api/orders/{id}` |

**Components:** `Navbar`, `ProductCard`, `ProductForm`, `CustomerForm`, `CustomerSelect`, `OrderForm`, `OrdersTable`, `StatusBadge`, `Pagination`, `Loader`, `ErrorBanner`.

**State:** `useState` and `useEffect` in each page; no global store is needed.

**API client**

```javascript
import axios from "axios";

const api = axios.create({ baseURL: "http://localhost:8080/api" });

export const getProducts = (q = "", page = 0, size = 8) =>
  api.get("/products", { params: { q, page, size } });

export const getCustomers = (q = "", page = 0, size = 8) =>
  api.get("/customers", { params: { q, page, size } });

export const createCustomer = (customer) => api.post("/customers", customer);

export const getCustomerOrders = (id, page = 0, size = 8) =>
  api.get(`/customers/${id}/orders`, { params: { page, size } });

export const placeOrder = (order) => api.post("/orders", order);

export const updateStatus = (id, status) =>
  api.put(`/orders/${id}/status`, { status });

export const cancelOrder = (id) => api.put(`/orders/${id}/cancel`);

export default api;
```

**Showing server errors in the UI**

```javascript
try {
  await placeOrder(form);
  navigate("/orders");
} catch (err) {
  setError(err.response?.data?.message ?? "Something went wrong");
}
```

**Hints**

- In the order form, load customers into a searchable dropdown (`CustomerSelect`) and fill the shipping address from the chosen customer, which the user can edit.
- Compute the total as `price * quantity` on screen; the server recalculates it anyway.
- Disable the Order button when stock is 0 and while a request is running.
- For Angular, use one service per entity (`CustomerService`, `ProductService`, `OrderService`) over `HttpClient`, and the same routes.

## 10. Validation, CORS and Error Handling

There is no authentication in this version, so input validation and consistent errors are the safety net. Every request DTO is validated and every failure becomes the JSON error shown in section 8.

**Validation rules on request DTOs**

| Request | Field | Rule |
| --- | --- | --- |
| `CustomerRequest` | `name` | Required, at most 100 characters |
| `CustomerRequest` | `email` | Required, valid email, unique |
| `CustomerRequest` | `phone` | Required, exactly 10 digits |
| `CustomerRequest` | `address` | Required, at most 300 characters |
| `ProductRequest` | `name` | Required, at most 150 characters |
| `ProductRequest` | `description` | Optional, at most 500 characters |
| `ProductRequest` | `price` | Required, at least 0.01 |
| `ProductRequest` | `stockQty` | Required, 0 or more |
| `PlaceOrderRequest` | `customerId` | Required |
| `PlaceOrderRequest` | `productId` | Required |
| `PlaceOrderRequest` | `quantity` | Required, at least 1 |
| `PlaceOrderRequest` | `shippingAddress` | Required, at most 300 characters |
| `StatusUpdateRequest` | `status` | Required, one of the enum values |

**Exception to HTTP status.** One `@RestControllerAdvice` class maps each exception to the error format in section 8.

| Exception | Status | Example message |
| --- | --- | --- |
| `ResourceNotFoundException` | 404 | Customer 3 not found |
| `MethodArgumentNotValidException` | 400 | `phone: must be 10 digits` |
| `InvalidStatusException` | 400 | Cannot move from DELIVERED to PLACED |
| `OutOfStockException` | 409 | Only 1 unit of 'Wireless Mouse' is in stock |
| `DataIntegrityViolationException` | 409 | Duplicate customer email, or deleting a customer or product that has orders |
| `ObjectOptimisticLockingFailureException` | 409 | The request conflicts with existing data. Please retry. |

CORS: add one `WebMvcConfigurer` that allows the React SPA or Angular dev server (port 5173 or 4200) to call `/api/**` on port 8080 with GET, POST, PUT and DELETE.

**Hint:** because every endpoint is open, run this only on a local machine. Adding Spring Security with a login and roles is a stretch goal.

## 11. Key Implementation Notes

**Hibernate setup.** The data layer uses Hibernate ORM directly through its `SessionFactory` and `Session`. Spring Data JPA, `JpaRepository` and `EntityManager` are not used.

| Item | What to do |
| --- | --- |
| Dependencies in `pom.xml` | `hibernate-core`, `spring-orm`, `spring-boot-starter-jdbc`, `mysql-connector-j`; do not add `spring-boot-starter-data-jpa` |
| SessionFactory | One `LocalSessionFactoryBean` in `HibernateConfig`: the DataSource, the `entity` package to scan, and the Hibernate properties |
| Transactions | A `HibernateTransactionManager` bean and `@EnableTransactionManagement`; services use `@Transactional` |
| Session access | Each DAO injects `SessionFactory` and calls `getCurrentSession()` inside the service's transaction |
| Mapping | Entities use Hibernate's mapping annotations (`@Entity`, `@Table`, `@Id`, `@ManyToOne`, `@Version`); in Hibernate 6 these live in the `jakarta.persistence` package |
| Queries | HQL through `session.createQuery(...)`; there are no derived query methods |

Build each layer in this order. The rules below are the behaviour the Postman tests check.

| Layer | Build | Rule |
| --- | --- | --- |
| DAO | `CustomerDao` with `search(q, page, size)` and `findByEmail(email)`; `ProductDao` with `search(name, page, size)`; `OrderDao` with `findByStatus(status, page, size)` and `findByCustomer(customerId, page, size)` | Queries are HQL; the DAO uses the current Hibernate session |
| Enum | `OrderStatus` with a `canMoveTo(next)` method | Allowed moves are in the table below |
| Controller | `CustomerController`, `ProductController` and `OrderController` | Validate with `@Valid`, call one service method, return the right status code, nothing else |
| Service | `CustomerService`, `ProductService` and `OrderService` | All business rules and `@Transactional` live here |

| From status | Allowed next status |
| --- | --- |
| PLACED | SHIPPED, CANCELLED |
| SHIPPED | DELIVERED |
| DELIVERED | None |
| CANCELLED | None |

**HQL queries the DAOs need**

| Purpose | HQL |
| --- | --- |
| Search customers | `from Customer c where lower(c.name) like lower(:q) or lower(c.email) like lower(:q) order by c.id`, with `setFirstResult` and `setMaxResults` |
| Search products | `from Product p where lower(p.name) like lower(:q) order by p.id`, with `setFirstResult` and `setMaxResults` |
| Count for paging | `select count(p) from Product p where lower(p.name) like lower(:q)` |
| Orders by status | `from Order o join fetch o.customer join fetch o.product where o.status = :status order by o.createdAt desc` |
| Orders of one customer | `from Order o join fetch o.product where o.customer.id = :customerId order by o.createdAt desc` |
| Does the customer have orders? | `select count(o) from Order o where o.customer.id = :id` |

**Hint:** Hibernate has no `Page` type, so return a small `PageResponse` DTO with `content`, `page`, `size`, `totalElements` and `totalPages`. If HQL complains about the name `Order`, declare the entity as `@Entity(name = "CustomerOrder")` and use that name in queries.

**`placeOrder` steps** (one `@Transactional` method)

1. Find the customer by id; if missing, throw `ResourceNotFoundException`.
2. Find the product by id; if missing, throw `ResourceNotFoundException`.
3. If stock is less than the requested quantity, throw `OutOfStockException`.
4. Reduce the product's stock by the quantity.
5. Create the order: link the customer and the product, copy the product price into `unitPrice`, set `totalAmount` to `unitPrice` × `quantity`, set the shipping address, set status PLACED.
6. Save the order and return an `OrderResponse` DTO that includes the customer and product names.

**`updateStatus` steps**

1. Find the order, else 404.
2. If the new status is CANCELLED or `canMoveTo` is false, throw `InvalidStatusException`.
3. Set the new status.

**`cancel` steps**

1. Find the order, else 404.
2. If the status is not PLACED, throw `InvalidStatusException`.
3. Add the order quantity back to the product's stock.
4. Set the status to CANCELLED.

**`deleteCustomer` steps**

1. Find the customer, else 404.
2. Count the customer's orders; if more than zero, throw a conflict (409) with a clear message.
3. Delete the customer.

**`application.properties`**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/shopsmart_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}
hibernate.dialect=org.hibernate.dialect.MySQLDialect
hibernate.hbm2ddl.auto=validate
hibernate.show_sql=true
hibernate.format_sql=true
server.port=8080
```

**Hints**

- You do not call `session.update(product)` in `placeOrder`: the product is a persistent entity, so, through dirty checking, Hibernate writes the stock change when the transaction commits.
- Throwing a `RuntimeException` inside a `@Transactional` method rolls everything back. Ask learners to prove it: place an order for more than the stock and check that no row was added.
- `join fetch` loads the customer and product with the order, so the mapper can read their names without a lazy-loading error.

## 12. Testing Strategy

Three levels keep the application safe to change: Postman for the API contract, JUnit 5 with Mockito for the business rules, and a manual walkthrough of the React SPA / Angular screens.

**Postman collection** with folders `Customers`, `Products` and `Orders`, and an environment holding `baseUrl` (`http://localhost:8080/api`), `customerId`, `productId` and `orderId`. Every request has at least one test script, and the collection runs end to end in Collection Runner.

| ID | Scenario | Request | Expected result |
| --- | --- | --- | --- |
| T-01 | Create customer | `POST /api/customers` | 201, response has `id` |
| T-02 | Create customer with a duplicate email | `POST /api/customers` | 409 |
| T-03 | Create customer with an invalid phone | `POST /api/customers` | 400 with field message |
| T-04 | Search customers | `GET /api/customers?q=asha&page=0&size=5` | 200, `content` array, `totalElements` present |
| T-05 | Create product | `POST /api/products` | 201, response has `id` |
| T-06 | Create product with blank name | `POST /api/products` | 400 with field message |
| T-07 | Search products | `GET /api/products?q=mouse&page=0&size=5` | 200, `content` array |
| T-08 | Place valid order | `POST /api/orders` | 201, PLACED, total = price × quantity, customer name present |
| T-09 | Place order for an unknown customer | `POST /api/orders` | 404 |
| T-10 | Check stock after order | `GET /api/products/{id}` | `stockQty` reduced by the order quantity |
| T-11 | Order more than stock | `POST /api/orders` | 409, stock unchanged |
| T-12 | Customer order history | `GET /api/customers/{id}/orders` | 200, contains the new order |
| T-13 | Ship then deliver | `PUT /api/orders/{id}/status` twice | 200 each time |
| T-14 | Invalid status jump | `PUT /api/orders/{id}/status` to PLACED | 400 |
| T-15 | Cancel a PLACED order | `PUT /api/orders/{id}/cancel` | 200, stock restored |
| T-16 | Delete a customer or product that has orders | `DELETE /api/customers/{id}` | 409 |

**Post-response script** on the create-customer request saves the id for later requests:

```javascript
pm.test("Status is 201", () => pm.response.to.have.status(201));

const body = pm.response.json();
pm.test("Id returned", () => pm.expect(body.id).to.be.a("number"));

pm.environment.set("customerId", body.id);
```

**Unit tests: the business rules** (JUnit 5 with Mockito, DAOs mocked)

| Test | Given | Expect |
| --- | --- | --- |
| Place order with enough stock | Customer and product found, stock 5, quantity 2 | Stock becomes 3; order saved as PLACED with the correct total and the customer linked |
| Place order with too little stock | Product stock 1, quantity 3 | `OutOfStockException`; stock still 1; order never saved |
| Place order for unknown customer | Customer `findById` returns empty | `ResourceNotFoundException`; product stock unchanged |
| Place order for unknown product | Product `findById` returns empty | `ResourceNotFoundException` |
| Cancel a PLACED order | Order of 2 units, product stock 3 | Stock becomes 5; status CANCELLED |
| Cancel a SHIPPED order | Order with status SHIPPED | `InvalidStatusException`; stock unchanged |
| Move DELIVERED to PLACED | Order with status DELIVERED | `InvalidStatusException` |
| Delete a customer who has orders | Order count for the customer is 1 | Conflict exception; customer never deleted |

**How Mockito is used.** Service tests are plain unit tests: no Spring context, no database. The DAOs are mocks, so each test checks only the service's business rule.

| Mockito feature | Use in these tests |
| --- | --- |
| `spring-boot-starter-test` | Brings in JUnit 5 and Mockito; no extra dependency needed |
| `@ExtendWith(MockitoExtension.class)` | Enables Mockito annotations on the test class |
| `@Mock` | Creates mock `CustomerDao`, `ProductDao` and `OrderDao` |
| `@InjectMocks` | Builds `OrderService` with the mock DAOs passed to its constructor |
| `when(...).thenReturn(...)` | Sets what a DAO returns, for example `customerDao.findById(3L)` returns a customer |
| `verify(..., never())` and `verify(..., times(1))` | Proves that an order is not saved when stock is too low, and is saved once when it is valid |
| `ArgumentCaptor` | Captures the saved order to check its customer, `unitPrice`, `totalAmount` and status PLACED |
| `assertThrows` (JUnit 5) | Checks that `OutOfStockException`, `ResourceNotFoundException` or `InvalidStatusException` is thrown |

**Hint:** name tests `method_condition_expectedResult`, for example `placeOrder_unknownCustomer_throwsNotFound`, and keep one rule per test.

**Also cover:** a DAO integration test for `ProductDao.search` and `CustomerDao.search`, and a manual run in the browser: add a customer and a product, place an order, check stock, view the customer's history, cancel, check stock again.

**Hint:** write the Postman tests as each endpoint is finished, not at the end; the collection is also the demo script.

## 13. Week 5 to Week 7 Implementation Plan

The project runs across course Weeks 5, 6 and 7, about 15 working days. The back end comes first so the React SPA / Angular app always has a stable API to call.

&#91;embedded content: Week 5 to 7 roadmap · 3 phases, 3 gates\]

A gate is a short demo that a trainer or peer reviews before the next week starts; a learner who misses a gate finishes it before moving on.

| Day | Focus | Output to show |
| --- | --- | --- |
| 1 | Generate project (Web, JDBC, MySQL, Validation) and add hibernate-core and spring-orm; create Git repo and `shopsmart_db` | App starts and connects to MySQL |
| 2 | `customers` table, `Customer` entity, `CustomerDao`, `HibernateConfig` | Table created; sample customers inserted |
| 3 | `CustomerService` and `CustomerController` with DTOs: create, get, list, search, update, delete | Customer API works in Postman |
| 4 | `products` table, `Product` entity, `ProductDao`, `ProductService`, `ProductController` | Product CRUD and search work |
| 5 | Bean Validation and `GlobalExceptionHandler` for customers and products | **Gate 1:** Customer and Product APIs pass T-01 to T-07 |
| 6 | `orders` table, `Order` entity with two foreign keys, `OrderStatus` enum, `OrderDao` | Table created with both foreign keys |
| 7 | `placeOrder` with customer lookup, stock check and `@Transactional` | Order saved, stock reduced |
| 8 | Order list with status filter, order detail, customer order history | History shows only that customer's orders |
| 9 | Status update, cancel with stock return, delete-customer rule; `@Version` on Product | Invalid jumps return 400; delete with orders returns 409 |
| 10 | Mockito unit tests for the services and Postman tests T-08 to T-16 | **Gate 2:** Orders API reduces stock, tests pass |
| 11 | React SPA (Vite) or Angular setup, routing, API client, product list with search | Products show in the browser |
| 12 | Customer list, add and edit forms, customer detail with order history | Customers managed from the UI |
| 13 | Order form with customer dropdown and live total; orders table | Place an order from the UI |
| 14 | Status buttons, cancel, error banners, responsive CSS | Full flow works on a phone-width screen |
| 15 | Bug fixes, README, table diagram, demo rehearsal | **Gate 3:** demo ready |

## 14. Deliverables, Evaluation and Stretch Goals

**Deliverables**

- Git repository with the Spring Boot project and the React SPA / Angular app in separate folders, and weekly commits.
- `schema.sql` (and optional `data.sql`) that creates `customers`, `products` and `orders`.
- Postman collection and environment exported as JSON.
- README with setup steps, the three-table diagram, the API list and screenshots.
- A 10-minute demo: add a customer and a product, place an order, show stock drop, show the customer's order history, cancel, show stock return, and show one error response.

**Evaluation rubric (100 marks)**

| Criterion | Marks | What the reviewer looks for |
| --- | --- | --- |
| Database design | 10 | Three tables, correct types, primary keys, both foreign keys, unique email, `CHECK` constraints, sensible indexes |
| Customer and Product APIs | 15 | Full CRUD, search, pagination, DTOs, correct status codes |
| Order API and transaction | 20 | Order linked to a customer, stock reduced and restored correctly, price snapshot, rollback on failure |
| Validation and errors | 10 | Bean Validation, one JSON error format, no stack traces in responses |
| React SPA / Angular UI | 20 | Working screens for customers, products and orders, loading and error states, responsive layout |
| Testing | 15 | Postman tests T-01 to T-16 and at least four Mockito service unit tests |
| Code quality and README | 10 | Layered packages, constructor injection, readable names, clear README |

**Stretch goals, in suggested order**

1. Add an `order_items` table so one order can hold several products.
2. Add an `addresses` table so a customer can have several saved addresses.
3. Add a `categories` table and a category filter on the product list.
4. Keep a cart in the React SPA / Angular app (`localStorage`) and check out in one request.
5. Add Spring Security with login and roles, and link each customer to a user account.
6. Add Swagger/OpenAPI documentation with `springdoc-openapi`.
7. Run MySQL and the app with Docker Compose.

**From monolith to services (discussion).** Customers, Products and Orders are already separate packages, so they are the natural split. The costs a learner should name: the foreign keys from `orders` to `customers` and `products` become plain ids and network calls, and the single transaction that reduces stock and saves the order must become a coordinated sequence with a way to undo it. That is why the course starts with the monolith.
