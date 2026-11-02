
# Online Shopping Management System – Microservices Case Study

Nov 3, 2026 · @Ashu

**Summary.** This is the second ShopSmart project. The Weeks 5–7 monolith is rebuilt as six small Spring Boot services behind one API gateway: `auth-service`, `customer-service`, `product-service` and `order-service`, plus a `discovery-server` (Eureka) and an `api-gateway` (Spring Cloud Gateway). Each business service owns its own MySQL schema and uses **Spring Data JPA** instead of the hand-written Hibernate `SessionFactory` and HQL. **Spring Security** adds login, BCrypt passwords, JWT access tokens, and three roles (ADMIN, STAFF, CUSTOMER). The one-transaction order from the monolith becomes an **orchestrated saga**: the order is saved as PENDING, stock is reserved in `product-service`, and the order is confirmed, or rejected with the stock released. Every service, the React SPA and MySQL are **Dockerized** and start with one `docker compose up`. The work runs over four weeks with four checkpoints and is graded against a 100-mark rubric.

## 1. Project Overview

This case study continues **ShopSmart**, the Customer / Product / Order system that learners built as a monolith in the previous project. The business stays the same. The architecture changes in four ways:

| Change | From (monolith) | To (this project) |
| --- | --- | --- |
| Architecture | One Spring Boot JAR, one schema | Independent services, one schema each, one gateway |
| Persistence | Hibernate `SessionFactory`, DAOs, HQL | Spring Data JPA repositories, derived queries, JPQL |
| Security | None; every endpoint open | Login, BCrypt, JWT, roles, ownership rules |
| Delivery | `mvn spring-boot:run` and a local MySQL | A Docker image per service and Docker Compose |

**Business context.** The retailer from the first project has grown. Phone and web orders now arrive together, the warehouse team and the sales team want to release changes on different days, and customers want to log in and see their own orders. One shared application and one shared database have become the bottleneck. Staff must not be able to see what only an administrator should see, and a customer must never see another customer's data.

**What stays the same on purpose**

- The three business entities (`Customer`, `Product`, `Order`) and the order status flow.
- The REST API paths under `/api`, the JSON error format and the `PageResponse` shape, so the React SPA needs only small changes.
- One order is for one product and a quantity. Payment and delivery are still not modelled.

**Learning outcomes.** By the end, a learner can:

- Split a monolith into services along business boundaries and explain each boundary.
- Replace a hand-written Hibernate data layer with Spring Data JPA (repositories, `Pageable`, JPQL, `@Version`, Flyway migrations).
- Route traffic through an API gateway with service discovery.
- Keep data consistent without a shared transaction, using a saga with compensation and idempotent calls.
- Secure the system with JWT, role-based access and ownership checks, enforced at the gateway and again in each service.
- Package every component in a Docker image and run the whole system with Docker Compose.
- Test at four levels: unit, repository (Testcontainers), web-security slice, and end-to-end with Postman.

## 2. Problem Statement, Objectives and Scope

**Problem.** The monolith works, but it cannot be changed or scaled in parts. A bug in the order code can stop product browsing. Every endpoint is open, so anyone who can reach the server can edit prices or read customer phone numbers. Setting up a developer machine means installing Java, MySQL and Node by hand.

**Objectives**

- Split the system into services that can be built, tested, started and replaced independently.
- Let each service own its data; no service reads another service's tables.
- Keep stock accurate across services: an order can never take stock below zero, and a failed order never keeps stock reserved.
- Require login for everything except registration and login, and give each role only the access it needs.
- Start the full system, database included, with one command on any machine that has Docker.

|  | In scope | Out of scope |
| --- | --- | --- |
| Services | Auth, Customer, Product, Order, Gateway, Discovery | Config server, service mesh |
| Data | One MySQL schema per service, Flyway migrations, Spring Data JPA | Sharding, read replicas, NoSQL |
| Communication | Synchronous REST with OpenFeign, timeouts, circuit breaker | Message brokers (Kafka, RabbitMQ), events |
| Consistency | Orchestrated saga with compensation, idempotent reserve and release | Two-phase commit, distributed transactions |
| Security | Register, login, BCrypt, JWT (HS256), roles, method security | OAuth2 login with an external provider, refresh tokens, MFA |
| Delivery | Dockerfiles, Docker Compose, health checks, `.env` secrets | Kubernetes, cloud deployment, CI/CD pipeline |
| Platform | Spring Boot 3.x, Java 21, MySQL 8, React SPA behind Nginx | Distributed tracing (stretch goal) |

**Hint:** build the monolith's tests first as a safety net. If a learner kept the Postman collection from the first project, most of it still runs, with one new login step.

## 3. Functional Requirements

Requirements FR-01 to FR-15 keep their meaning from the monolith. Their acceptance checks now also include the role that is allowed to call them. FR-16 onward are new.

| ID | Module | Requirement | Acceptance check |
| --- | --- | --- | --- |
| FR-01 | Customer | Add a customer with name, email, phone and address | STAFF or ADMIN: 201; blank name or invalid email: 400; duplicate email: 409 |
| FR-02 | Customer | List customers with search by name or email and pagination | `GET /api/customers?q=asha&page=0&size=5` returns a page object (STAFF, ADMIN) |
| FR-03 | Customer | View, update and delete a customer | Unknown id: 404; delete a customer who has orders: 409; delete is ADMIN only |
| FR-04 | Customer | View the order history of one customer | `GET /api/customers/{id}/orders` returns only that customer's orders; a CUSTOMER can read only their own |
| FR-05 | Product | Add a product with name, description, price and stock | STAFF or ADMIN: 201; blank name or negative price: 400 |
| FR-06 | Product | List products with search, sort and pagination | Any logged-in user can read; `GET /api/products?q=mouse&page=0&size=5` |
| FR-07 | Product | View, update and delete a product | Unknown id: 404; delete a product that has orders: 409 |
| FR-08 | Order | Place an order for a customer and product | 201 with status PLACED and the calculated total; unknown customer or product: 404 |
| FR-09 | Order | Stock is reserved before the order is confirmed | If stock is too low the order is REJECTED, stock is unchanged, and the call returns 409 |
| FR-10 | Order | The order stores the unit price at purchase time | Changing the product price later does not change old orders |
| FR-11 | Order | List orders with optional status filter, newest first | STAFF or ADMIN; REJECTED orders are hidden unless `status=REJECTED` is requested |
| FR-12 | Order | View one order with customer name and product name | The names are stored on the order, so no cross-service call is needed to read it |
| FR-13 | Order | Update status along PLACED, SHIPPED, DELIVERED | STAFF or ADMIN; an invalid jump returns 400 |
| FR-14 | Order | Cancel an order while it is PLACED; stock is returned | Stock increases by the order quantity; SHIPPED orders cannot be cancelled |
| FR-15 | Platform | Every error uses one JSON format | Matches the format in section 8, including 401, 403 and 503 |
| FR-16 | Auth | Register a CUSTOMER account | `POST /api/auth/register` returns 201; duplicate email: 409; password shorter than 8 characters: 400 |
| FR-17 | Auth | Log in and receive a JWT | `POST /api/auth/login` returns 200 and `accessToken`; a wrong password returns 401 with a message that does not say which part was wrong |
| FR-18 | Security | Every endpoint except register and login needs a valid token | No token or an expired token: 401 |
| FR-19 | Security | Role rules are enforced | A CUSTOMER calling a STAFF endpoint gets 403 |
| FR-20 | Security | A customer sees only their own data | A CUSTOMER requesting another customer's profile or orders gets 404 |
| FR-21 | Customer | A CUSTOMER creates and reads their own profile | `POST /api/customers/me` links the profile to the user id in the token |
| FR-22 | Order | A saga compensates on failure | If the order cannot be confirmed after stock was reserved, the stock is released |
| FR-23 | Order | Reserve and release are idempotent | Calling release twice for the same order returns stock once; a retried reserve does not take stock twice |
| FR-24 | Platform | Internal endpoints are not public | `/internal/**` is not reachable through the gateway (403) |
| FR-25 | Platform | One command starts everything | `docker compose up --build` starts all containers; the SPA works at `http://localhost:3000` |
| FR-26 | Platform | Every service reports health | `GET /actuator/health` returns `UP` inside the Docker network |

**Order status flow:** PENDING → PLACED → SHIPPED → DELIVERED. PENDING becomes REJECTED if the reservation fails. CANCELLED is reachable from PLACED only. REJECTED, CANCELLED and DELIVERED are final.

## 4. Non-Functional Requirements

| Area | Requirement |
| --- | --- |
| Data ownership | Each service has its own schema and database user. No cross-schema foreign keys, joins or shared entities |
| Data integrity | Constraints and `@Transactional` stay inside one service. Across services, use the saga in section 6. Schema changes go through Flyway; `ddl-auto=validate` |
| Concurrency | Stock is reduced with one atomic `UPDATE ... WHERE stock_qty >= :qty`, plus `@Version` on `Product` for edits |
| Resilience | Feign connect and read timeouts of 2 seconds; a circuit breaker on the product client; retry only on idempotent calls (release) |
| Security | BCrypt passwords; JWT signed with a secret read from the environment; tokens valid for 30 minutes; stateless sessions; secrets never committed to Git |
| Defence in depth | The gateway validates the token, and each service validates it again. A service does not trust a request just because it came from inside the network |
| Performance | Product and customer lists respond in under 500 ms with 1,000 seeded rows; list endpoints are always paginated |
| Observability | Actuator health and info on every service; a request id passes from the gateway through all log lines |
| Maintainability | Layered packages per service, DTOs separate from entities, constructor injection, shared code only in the `common` module |
| Testability | Unit, repository, web-security and end-to-end tests as listed in section 13 |
| Portability | Runs the same on any machine with Docker and Docker Compose; configuration through environment variables |
| Documentation | README with architecture diagram, run steps, role matrix and API list |

## 5. Technology Stack

| Layer | Technology | Used for |
| --- | --- | --- |
| Language | Java 21 (LTS) | Records for DTOs, `var`, text blocks |
| Framework | Spring Boot 3.x | Auto-configuration, Actuator, embedded Tomcat |
| Cloud | Spring Cloud (release train that matches the Boot version) | Gateway, Eureka, OpenFeign |
| Gateway | Spring Cloud Gateway | Single entry point, routing, token check |
| Discovery | Spring Cloud Netflix Eureka | Services find each other by name |
| Service calls | Spring Cloud OpenFeign, Resilience4j | Declarative REST clients, timeouts, circuit breaker |
| Persistence | Spring Data JPA (Hibernate 6 underneath) | Entities, `JpaRepository`, derived queries, JPQL, paging |
| Migrations | Flyway | Versioned SQL schema per service |
| Database | MySQL 8 | One schema and one user per service |
| Security | Spring Security 6, OAuth2 Resource Server (JWT), BCrypt | Login, token issue and validation, `@PreAuthorize` |
| Validation | Bean Validation | `@NotBlank`, `@Email`, `@Min`, `@Size` on request DTOs |
| Front end | React 18 (Vite), Axios, React Router | SPA with login, protected routes, role-aware screens |
| Web server | Nginx | Serves the built SPA and proxies `/api` to the gateway |
| Build | Maven multi-module | Parent POM, `common` library, one module per service |
| Containers | Docker (multi-stage builds), Docker Compose | Images, networks, volumes, health checks |
| Testing | JUnit 5, Mockito, Testcontainers, Spring Security Test, Postman | Unit, repository, web-security and API tests |
| IDE | IntelliJ IDEA or Eclipse, Docker Desktop | Development and running containers |

**Hint:** generate each service at start.spring.io. Typical dependencies: Spring Web, Validation, Spring Data JPA, MySQL Driver, Flyway, Actuator, OAuth2 Resource Server, Eureka Discovery Client, OpenFeign (order and customer services). The gateway uses Gateway, Eureka Discovery Client and OAuth2 Resource Server (reactive). The discovery server uses Eureka Server.

## 6. Microservices Architecture

ShopSmart is now six Spring Boot applications and one SPA. Only the SPA and the gateway are reachable from outside Docker. Everything else lives on a private Docker network.

```text
 Browser ──► Nginx + React SPA (:3000)
                  │  /api/*
                  ▼
           ┌──────────────┐        ┌────────────────────┐
           │ api-gateway  │◄──────►│ discovery-server   │
           │   (:8080)    │        │ Eureka (:8761)     │
           │ JWT check    │        └────────────────────┘
           └──┬───┬───┬───┬──────────────  (private Docker network)
              │   │   │   │
   ┌──────────┘   │   │   └───────────┐
   ▼              ▼   ▼               ▼
 auth-service  customer-   product-   order-service ──► customer-service (Feign)
   (:8081)     service     service      (:8084)    └──► product-service  (Feign)
      │        (:8082)     (:8083)         │
      ▼           ▼           ▼            ▼
   auth_db    customer_db  product_db   order_db      (one MySQL container, four schemas)
```

**Why these boundaries**

| Service | Owns | Reason it is separate |
| --- | --- | --- |
| `auth-service` | Users, passwords, roles, token issue | Security data changes for different reasons and is the most sensitive; only this service sees password hashes |
| `customer-service` | Customer profiles | Contact details, linked to a user id; changed by sales staff |
| `product-service` | Products, stock, stock reservations | Catalogue and stock are changed by the warehouse and read heavily |
| `order-service` | Orders and their status | The workflow that ties the others together; the only service that calls the others |
| `api-gateway` | Routing, token check, CORS for dev | One public address; keeps `/internal/**` private |
| `discovery-server` | Service registry | Services call `lb://product-service`, not a hard-coded host |

**What changes from the monolith**

| Monolith | Microservices |
| --- | --- |
| `HibernateConfig`, `SessionFactory`, `HibernateTransactionManager` | Removed; Spring Boot auto-configures JPA and the transaction manager |
| `CustomerDao`, `ProductDao`, `OrderDao` | `CustomerRepository`, `ProductRepository`, `OrderRepository` (interfaces only) |
| HQL through `session.createQuery` | Derived query methods and `@Query` (JPQL) |
| `PageResponse` built by hand with `setFirstResult` | `Pageable` in, `Page<T>` out, mapped to the same `PageResponse` record |
| `orders.customer_id` and `orders.product_id` foreign keys | Plain ids; `customerName` and `productName` copied onto the order |
| One `@Transactional` `placeOrder` | A saga across two services (below) |
| `CorsConfig` | Removed from services; Nginx proxy in Docker, CORS only in the gateway for Vite dev |
| `mvn spring-boot:run` | `docker compose up --build` |

**Gateway routes.** Order matters: the more specific route comes first.

| Order | Path | Goes to | Note |
| --- | --- | --- | --- |
| 1 | `/api/customers/*/orders` | `order-service` | Order history is owned by the order service |
| 2 | `/api/auth/**` | `auth-service` | Register and login are the only public paths |
| 3 | `/api/customers/**` | `customer-service` | |
| 4 | `/api/products/**` | `product-service` | |
| 5 | `/api/orders/**` | `order-service` | |
| – | `/internal/**` | nowhere | Denied at the gateway |

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: customer-orders
          uri: lb://order-service
          predicates: [ "Path=/api/customers/*/orders" ]
        - id: auth
          uri: lb://auth-service
          predicates: [ "Path=/api/auth/**" ]
        - id: customers
          uri: lb://customer-service
          predicates: [ "Path=/api/customers/**" ]
        - id: products
          uri: lb://product-service
          predicates: [ "Path=/api/products/**" ]
        - id: orders
          uri: lb://order-service
          predicates: [ "Path=/api/orders/**" ]
```

**Internal endpoints.** Calls between services use paths that start with `/internal`. They are not routed by the gateway and the containers' ports are not published, so only other containers can reach them. They still require a valid JWT, which the order service forwards from the caller.

| Service | Internal endpoint | Purpose |
| --- | --- | --- |
| `customer-service` | `GET /internal/customers/{id}` | Name and default address of a customer |
| `customer-service` | `GET /internal/customers/by-user/{userId}` | Find the customer profile of a logged-in user |
| `product-service` | `POST /internal/products/{id}/reserve` | Reserve stock for an order; returns name and unit price |
| `product-service` | `POST /internal/reservations/{orderId}/release` | Give reserved stock back (idempotent) |
| `order-service` | `GET /internal/orders/exists?customerId=` or `?productId=` | Used by the delete rules in FR-03 and FR-07 |

**The saga: placing an order.** There is no shared database transaction any more, so the order service coordinates the steps and undoes them on failure.

1. The SPA sends `POST /api/orders` with a JWT. The gateway checks the token and routes to `order-service`.
2. `OrderService` works out the customer id (from the token for a CUSTOMER, from the body for STAFF) and calls `customer-service` for the customer name and address. Unknown customer: 404.
3. A short local transaction saves the order as **PENDING**. The order now has an id.
4. `order-service` calls `product-service` `reserve` with `orderId` and `quantity`. The product service reduces stock with one atomic update and records a row in `stock_reservations`. No stock: 409.
5. **Success:** a second local transaction stores the unit price, the product name and the total, and sets the status to **PLACED**. The response is 201.
6. **Stock refused:** the order is set to **REJECTED** with a reason, and the caller gets 409. No stock was taken.
7. **Unknown outcome** (timeout, circuit open): the order service calls `release` for this order id, marks the order REJECTED, and returns 503. Because `release` is idempotent, it is safe even if the reservation never happened.

Cancelling follows the same idea in reverse: call `release` first (retries are safe), then set CANCELLED. If the database update fails afterwards, the learner retries the cancel and `release` does nothing the second time.

```java
@Service
public class OrderService {

    private final OrderTxService tx;               // separate bean so @Transactional is applied
    private final CustomerClient customers;
    private final ProductClient products;

    // Deliberately NOT @Transactional: no database transaction stays open during a remote call.
    public OrderResponse placeOrder(PlaceOrderRequest req, CallerContext caller) {
        CustomerSummary customer = customers.get(resolveCustomerId(req, caller));
        CustomerOrder order = tx.createPending(customer, req);

        try {
            ReservationResponse r =
                products.reserve(req.productId(), new ReserveRequest(order.getId(), req.quantity()));
            return OrderResponse.from(tx.confirm(order.getId(), r));
        } catch (OutOfStockException | ResourceNotFoundException e) {
            tx.reject(order.getId(), e.getMessage());
            throw e;                                // handler returns 409 or 404
        } catch (RuntimeException e) {              // timeout, circuit open, 5xx
            safeRelease(order.getId());             // compensation
            tx.reject(order.getId(), "Product service unavailable");
            throw new ServiceUnavailableException("Could not place the order. Please retry.");
        }
    }
}
```

**Design rules the reviewer checks**

- A service never reads another service's database. Data comes through its REST API only.
- No `@Transactional` method makes a remote call.
- Controllers call services, services call repositories and Feign clients; entities are never returned as JSON.
- DTOs are Java records; dependencies arrive through constructor injection.
- `reserve` and `release` are idempotent and keyed by `orderId`.
- Shared code lives only in `common` (error format, exceptions, security configuration). It contains no business logic.

**Repository layout**

```text
shopsmart/
├── pom.xml                       parent POM (modules, dependency management)
├── common/                       ErrorResponse, exceptions, GlobalExceptionHandler, SecurityConfig, JWT converter
├── discovery-server/
├── api-gateway/
├── auth-service/
├── customer-service/
├── product-service/
├── order-service/
├── frontend/                     React SPA, Dockerfile, nginx.conf
├── docker/
│   ├── Dockerfile.service        one multi-stage Dockerfile for every Java service
│   └── mysql-init/01-schemas.sql creates the four schemas and four users
├── docker-compose.yml
├── .env.example                  names of all secrets, no real values
└── postman/
```

**Package structure of one service** (`order-service`)

```text
com.annotatetech.shopsmart.order
├── OrderServiceApplication.java
├── config/       FeignConfig (token relay, error decoder), SecurityBeans
├── controller/   OrderController, InternalOrderController
├── service/      OrderService, OrderTxService
├── client/       CustomerClient, ProductClient (@FeignClient), CustomerSummary, ReservationResponse
├── repository/   OrderRepository
├── entity/       CustomerOrder, OrderStatus
└── dto/          PlaceOrderRequest, OrderResponse, StatusUpdateRequest, PageResponse
```

**Hint:** build `customer-service` and `product-service` first as stand-alone services with their own MySQL schema. Add the gateway next. Build `order-service` last, because it needs the other two.

## 7. Database Design (one schema per service)

One MySQL container hosts four schemas for convenience. Each schema has its own user that can reach only that schema. Treat them as four separate databases; moving one to its own server later must not require a code change.

| Schema | Tables | Database user |
| --- | --- | --- |
| `auth_db` | `users` | `auth_user` |
| `customer_db` | `customers` | `customer_user` |
| `product_db` | `products`, `stock_reservations` | `product_user` |
| `order_db` | `orders` | `order_user` |

**`auth_db.users`**

| Column | Type | Constraints | Notes |
| --- | --- | --- | --- |
| `id` | BIGINT | PK, AUTO\_INCREMENT |  |
| `email` | VARCHAR(120) | NOT NULL, UNIQUE | Login name |
| `password_hash` | VARCHAR(100) | NOT NULL | BCrypt hash, never the password |
| `role` | VARCHAR(20) | NOT NULL | ADMIN, STAFF or CUSTOMER |
| `enabled` | BOOLEAN | NOT NULL, DEFAULT TRUE |  |
| `created_at` | TIMESTAMP | NOT NULL, DEFAULT CURRENT\_TIMESTAMP |  |

**`customer_db.customers`**: the monolith's table plus one column.

| Column | Type | Constraints | Notes |
| --- | --- | --- | --- |
| `user_id` | BIGINT | NULL, UNIQUE | Id of the user in `auth-service`. A plain number, not a foreign key. Null for customers that staff typed in |

The other columns (`id`, `name`, `email`, `phone`, `address`, `created_at`) are unchanged.

**`product_db.products`** is unchanged from the monolith, including `version` for `@Version`.

**`product_db.stock_reservations`** (new)

| Column | Type | Constraints | Notes |
| --- | --- | --- | --- |
| `id` | BIGINT | PK, AUTO\_INCREMENT |  |
| `order_id` | BIGINT | NOT NULL, UNIQUE | The key that makes reserve and release idempotent |
| `product_id` | BIGINT | NOT NULL, FK to `products(id)` | Same schema, so a real foreign key is fine |
| `quantity` | INT | NOT NULL, CHECK (quantity > 0) |  |
| `status` | VARCHAR(10) | NOT NULL | RESERVED or RELEASED |
| `created_at` | TIMESTAMP | NOT NULL, DEFAULT CURRENT\_TIMESTAMP |  |

**`order_db.orders`**: no foreign keys to other schemas, and names copied onto the row.

| Column | Type | Constraints | Notes |
| --- | --- | --- | --- |
| `id` | BIGINT | PK, AUTO\_INCREMENT |  |
| `customer_id` | BIGINT | NOT NULL | Id in `customer-service`; no FK |
| `customer_name` | VARCHAR(100) | NOT NULL | Copied when the order is created |
| `product_id` | BIGINT | NOT NULL | Id in `product-service`; no FK |
| `product_name` | VARCHAR(150) | NULL | Filled when the reservation succeeds |
| `quantity` | INT | NOT NULL, CHECK (quantity > 0) |  |
| `unit_price` | DECIMAL(10,2) | NULL | Filled when the order is confirmed |
| `total_amount` | DECIMAL(10,2) | NULL | `unit_price` × `quantity` |
| `shipping_address` | VARCHAR(300) | NOT NULL |  |
| `status` | VARCHAR(20) | NOT NULL, DEFAULT 'PENDING' | PENDING, PLACED, SHIPPED, DELIVERED, CANCELLED, REJECTED |
| `failure_reason` | VARCHAR(200) | NULL | Set when REJECTED |
| `version` | BIGINT | NOT NULL, DEFAULT 0 | `@Version`, stops two staff members changing status at once |
| `created_at` | TIMESTAMP | NOT NULL, DEFAULT CURRENT\_TIMESTAMP |  |
| `updated_at` | TIMESTAMP | NOT NULL, DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP |  |

**Flyway.** Each service keeps its scripts in `src/main/resources/db/migration`. The first script of `order-service` is:

```sql
-- V1__create_orders.sql
CREATE TABLE orders (
  id               BIGINT AUTO_INCREMENT PRIMARY KEY,
  customer_id      BIGINT        NOT NULL,
  customer_name    VARCHAR(100)  NOT NULL,
  product_id       BIGINT        NOT NULL,
  product_name     VARCHAR(150),
  quantity         INT           NOT NULL CHECK (quantity > 0),
  unit_price       DECIMAL(10,2),
  total_amount     DECIMAL(10,2),
  shipping_address VARCHAR(300)  NOT NULL,
  status           VARCHAR(20)   NOT NULL DEFAULT 'PENDING',
  failure_reason   VARCHAR(200),
  version          BIGINT        NOT NULL DEFAULT 0,
  created_at       TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at       TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE INDEX idx_orders_status   ON orders(status);
CREATE INDEX idx_orders_customer ON orders(customer_id);
CREATE INDEX idx_orders_product  ON orders(product_id);
```

The Docker init script creates the schemas and users once, when the MySQL volume is first created:

```sql
-- docker/mysql-init/01-schemas.sql
CREATE DATABASE auth_db;     CREATE USER 'auth_user'@'%'     IDENTIFIED BY 'change-me-auth';
CREATE DATABASE customer_db; CREATE USER 'customer_user'@'%' IDENTIFIED BY 'change-me-customer';
CREATE DATABASE product_db;  CREATE USER 'product_user'@'%'  IDENTIFIED BY 'change-me-product';
CREATE DATABASE order_db;    CREATE USER 'order_user'@'%'    IDENTIFIED BY 'change-me-order';
GRANT ALL ON auth_db.*     TO 'auth_user'@'%';
GRANT ALL ON customer_db.* TO 'customer_user'@'%';
GRANT ALL ON product_db.*  TO 'product_user'@'%';
GRANT ALL ON order_db.*    TO 'order_user'@'%';
```

**Hint:** the passwords above are placeholders for a local lab. For anything shared, generate the init script from the `.env` values, or use a `.sh` init script that reads environment variables, and never commit real passwords.

## 8. REST API Design

All public endpoints stay under `/api` and are reached through the gateway on port 8080 (or through Nginx on port 3000). Every endpoint except register and login needs `Authorization: Bearer <token>`.

| Method | Endpoint | Purpose | Roles | Success |
| --- | --- | --- | --- | --- |
| POST | `/api/auth/register` | Create a CUSTOMER account | public | 201 |
| POST | `/api/auth/login` | Log in, receive a JWT | public | 200 |
| GET | `/api/auth/me` | The caller's id, email and role | any | 200 |
| POST | `/api/customers/me` | Create my customer profile | CUSTOMER | 201 |
| GET | `/api/customers/me` | Read my profile | CUSTOMER | 200 |
| GET | `/api/customers` | List with `q`, `page`, `size` | STAFF, ADMIN | 200 |
| GET | `/api/customers/{id}` | Customer detail | STAFF, ADMIN | 200 |
| POST | `/api/customers` | Create customer | STAFF, ADMIN | 201 |
| PUT | `/api/customers/{id}` | Update customer | STAFF, ADMIN | 200 |
| DELETE | `/api/customers/{id}` | Delete (409 if the customer has orders) | ADMIN | 204 |
| GET | `/api/customers/{id}/orders` | Order history (served by `order-service`) | STAFF, ADMIN, or the owner | 200 |
| GET | `/api/products` | List with `q`, `page`, `size`, `sort` | any | 200 |
| GET | `/api/products/{id}` | Product detail | any | 200 |
| POST | `/api/products` | Create product | STAFF, ADMIN | 201 |
| PUT | `/api/products/{id}` | Update product, including stock | STAFF, ADMIN | 200 |
| DELETE | `/api/products/{id}` | Delete (409 if it has orders) | ADMIN | 204 |
| POST | `/api/orders` | Place an order (saga) | CUSTOMER, STAFF, ADMIN | 201 |
| GET | `/api/orders` | List orders, optional `status` | STAFF, ADMIN | 200 |
| GET | `/api/orders/mine` | My orders | CUSTOMER | 200 |
| GET | `/api/orders/{id}` | Order detail | STAFF, ADMIN, or the owner | 200 |
| PUT | `/api/orders/{id}/status` | Move to SHIPPED or DELIVERED | STAFF, ADMIN | 200 |
| PUT | `/api/orders/{id}/cancel` | Cancel a PLACED order | STAFF, ADMIN, or the owner | 200 |

**Sample: log in**

```json
POST /api/auth/login

{ "email": "asha@example.com", "password": "Str0ng-Pass!" }
```

```json
200 OK

{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "expiresInSeconds": 1800,
  "role": "CUSTOMER"
}
```

The token's claims are `sub` (user id), `email`, `roles` (for example `["CUSTOMER"]`), `iss` and `exp`. A password or any other secret is never placed in a token.

**Sample: place an order** (a CUSTOMER leaves `customerId` out; the service takes it from the token)

```json
POST /api/orders
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

{
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

**Standard error format** (the same record, from the `common` module, in every service and in the gateway)

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
| 401 | Missing, invalid or expired token; wrong login |
| 403 | Valid token, but the role is not allowed |
| 404 | Customer, product or order not found; also another customer's data (FR-20) |
| 409 | Insufficient stock, duplicate email, deleting a customer or product that has orders, optimistic-lock conflict |
| 503 | A downstream service is slow or down and the circuit is open |

**Hint:** return 404, not 403, when a CUSTOMER asks for someone else's order. A 403 would confirm that the order exists.

## 9. Spring Data JPA

Spring Data JPA replaces the DAO classes, `HibernateConfig` and the HQL strings. Hibernate is still the JPA provider underneath, so entity annotations barely change; they come from `jakarta.persistence` as before.

| Monolith (Hibernate) | Spring Data JPA |
| --- | --- |
| `class ProductDao` with `SessionFactory` | `interface ProductRepository extends JpaRepository<Product, Long>` |
| `session.get`, `session.persist` | `findById`, `save` |
| HQL `from Product p where lower(p.name) like ...` | `Page<Product> findByNameContainingIgnoreCase(String name, Pageable p)` |
| `setFirstResult`, `setMaxResults`, a separate count query | `PageRequest.of(page, size, sort)`; the count query is automatic |
| Manual `LocalSessionFactoryBean`, `hibernate.*` properties | `spring.jpa.*` properties and auto-configuration |
| `hbm2ddl.auto=validate` plus a hand-run SQL script | `ddl-auto=validate` plus Flyway migrations at startup |

**Repositories**

```java
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // One atomic statement: two simultaneous orders cannot both take the last unit.
    @Modifying(clearAutomatically = true)
    @Query("""
           update Product p
              set p.stockQty = p.stockQty - :qty, p.version = p.version + 1
            where p.id = :id and p.stockQty >= :qty
           """)
    int decreaseStock(@Param("id") Long id, @Param("qty") int qty);

    @Modifying(clearAutomatically = true)
    @Query("update Product p set p.stockQty = p.stockQty + :qty, p.version = p.version + 1 where p.id = :id")
    int increaseStock(@Param("id") Long id, @Param("qty") int qty);
}

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("""
           select c from Customer c
            where lower(c.name)  like lower(concat('%', :q, '%'))
               or lower(c.email) like lower(concat('%', :q, '%'))
           """)
    Page<Customer> search(@Param("q") String q, Pageable pageable);

    boolean existsByEmail(String email);
    Optional<Customer> findByUserId(Long userId);
}

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {

    Page<CustomerOrder> findByStatus(OrderStatus status, Pageable pageable);
    Page<CustomerOrder> findByStatusNot(OrderStatus status, Pageable pageable);   // hides REJECTED
    Page<CustomerOrder> findByCustomerId(Long customerId, Pageable pageable);
    boolean existsByCustomerId(Long customerId);
    boolean existsByProductId(Long productId);
}
```

**Entity notes**

| Entity | Mapping notes |
| --- | --- |
| `Customer` | `@Id` with `IDENTITY`; `email` unique; new nullable `userId` column |
| `Product` | `BigDecimal price`, `@Version Long version`; `stockQty` maps to `stock_qty` |
| `StockReservation` | `orderId` unique; `@Enumerated(EnumType.STRING)` on `status` |
| `CustomerOrder` | `@Table(name = "orders")`; plain `Long customerId` and `Long productId`, **no** `@ManyToOne`; `@Version`; `@Enumerated(EnumType.STRING)` on `status` |

**Properties common to every JPA service**

```yaml
spring:
  jpa:
    open-in-view: false
    hibernate:
      ddl-auto: validate
    properties:
      hibernate.format_sql: true
  flyway:
    enabled: true
```

**Hints**

- Name the class `CustomerOrder` and the table `orders`. `Order` is a reserved word in JPQL and SQL.
- `@Modifying` queries skip the persistence context, so use `clearAutomatically = true` and increment `version` yourself.
- `Page<T>` is not a stable JSON shape. Map it to the `PageResponse` record (`content`, `page`, `size`, `totalElements`, `totalPages`) in the service, so the SPA contract does not change.
- Turn off `open-in-view`. Lazy loading in the web layer hides slow queries and is not needed once entities do not leave the service.
- Check the queries with `spring.jpa.show-sql=true` once, then turn it off.

**Reservation service (product-service)**

```java
@Transactional
public ReservationResponse reserve(Long productId, ReserveRequest req) {
    Product product = products.findById(productId)
        .orElseThrow(() -> new ResourceNotFoundException("Product " + productId + " not found"));

    Optional<StockReservation> existing = reservations.findByOrderId(req.orderId());
    if (existing.isPresent()) {                          // a retry: do not take stock twice
        return ReservationResponse.of(product, existing.get());
    }
    if (products.decreaseStock(productId, req.quantity()) == 0) {
        throw new OutOfStockException(
            "Only " + product.getStockQty() + " unit of '" + product.getName() + "' is in stock");
    }
    StockReservation saved = reservations.save(
        new StockReservation(req.orderId(), productId, req.quantity(), ReservationStatus.RESERVED));
    return ReservationResponse.of(product, saved);
}

@Transactional
public void release(Long orderId) {                      // safe to call any number of times
    reservations.findByOrderId(orderId)
        .filter(r -> r.getStatus() == ReservationStatus.RESERVED)
        .ifPresent(r -> {
            products.increaseStock(r.getProductId(), r.getQuantity());
            r.setStatus(ReservationStatus.RELEASED);
        });
}
```

## 10. Security Design

Security is enforced in two places. The **gateway** rejects requests without a valid token before they reach a service. **Each service** validates the token again and applies role and ownership rules, so a bug or a misrouted request in one layer does not open the system.

**Roles**

| Role | Who | Can do |
| --- | --- | --- |
| `CUSTOMER` | A shopper who registered | Browse products; manage own profile; place orders for self; see and cancel own orders |
| `STAFF` | Shop employee | Everything a CUSTOMER can; manage customers and products; place orders for any customer; list, ship and cancel orders |
| `ADMIN` | Owner | Everything STAFF can; delete customers and products; create STAFF accounts |

**Role matrix**

| Action | CUSTOMER | STAFF | ADMIN |
| --- | --- | --- | --- |
| Register, log in | yes | yes | yes |
| Read products | yes | yes | yes |
| Create or edit products | no | yes | yes |
| Delete products or customers | no | no | yes |
| List or edit all customers | no | yes | yes |
| Own profile | yes | n/a | n/a |
| Place an order | own only | any customer | any customer |
| Read an order or history | own only | any | any |
| Ship or deliver an order | no | yes | yes |
| Cancel an order | own, while PLACED | any, while PLACED | any, while PLACED |

**Authentication flow**

1. `POST /api/auth/login`. `auth-service` loads the user, checks the password with `BCryptPasswordEncoder.matches`, and signs a JWT.
2. The SPA keeps the token and sends it as `Authorization: Bearer ...` on every call.
3. The gateway decodes and verifies the signature and expiry. Failure returns 401. `/internal/**` is denied here.
4. The target service verifies the token again and turns the `roles` claim into Spring authorities (`ROLE_CUSTOMER` and so on).
5. `@PreAuthorize` rules check the role; service code checks ownership.

**Issuing the token (auth-service)**

```java
public String issue(User user) {
    Instant now = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("shopsmart-auth")
        .subject(user.getId().toString())
        .issuedAt(now)
        .expiresAt(now.plus(30, ChronoUnit.MINUTES))
        .claim("email", user.getEmail())
        .claim("roles", List.of(user.getRole().name()))
        .build();
    JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
    return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
}

@Bean
JwtEncoder jwtEncoder(@Value("${app.jwt.secret}") String secret) {
    SecretKey key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    return new NimbusJwtEncoder(new ImmutableSecret<>(key));
}
```

**Validating the token in each service (shared in `common`)**

```java
@Configuration
@EnableMethodSecurity
public class ResourceServerConfig {

    @Bean
    SecurityFilterChain chain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())                       // no cookies, so no CSRF risk
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(a -> a
                .requestMatchers("/actuator/health/**").permitAll()
                .anyRequest().authenticated())
            .oauth2ResourceServer(o -> o.jwt(j -> j.jwtAuthenticationConverter(converter())));
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder(@Value("${app.jwt.secret}") String secret) {
        SecretKey key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(key).build();
    }

    private JwtAuthenticationConverter converter() {
        JwtGrantedAuthoritiesConverter roles = new JwtGrantedAuthoritiesConverter();
        roles.setAuthoritiesClaimName("roles");
        roles.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter c = new JwtAuthenticationConverter();
        c.setJwtGrantedAuthoritiesConverter(roles);
        return c;
    }
}
```

In `auth-service` the same chain additionally calls `.requestMatchers("/api/auth/login", "/api/auth/register").permitAll()`. The gateway uses the reactive equivalent (`SecurityWebFilterChain`, `NimbusReactiveJwtDecoder.withSecretKey`) with the same public paths and `.pathMatchers("/internal/**").denyAll()`.

**Role and ownership rules in the services**

```java
@PreAuthorize("hasAnyRole('STAFF','ADMIN')")
@PostMapping("/api/products")
public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest req) { ... }

@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/api/products/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) { ... }

// Ownership is checked in the service, because it needs data, not just a role.
public OrderResponse getOrder(Long id, CallerContext caller) {
    CustomerOrder o = orders.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Order " + id + " not found"));
    if (caller.isCustomer() && !o.getCustomerId().equals(caller.customerId())) {
        throw new ResourceNotFoundException("Order " + id + " not found");   // 404, not 403
    }
    return OrderResponse.from(o);
}
```

**Passing the token between services.** `order-service` forwards the caller's token on Feign calls, so the downstream service can still see who asked.

```java
@Bean
RequestInterceptor tokenRelay() {
    return template -> {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a instanceof JwtAuthenticationToken jwt) {
            template.header("Authorization", "Bearer " + jwt.getToken().getTokenValue());
        }
    };
}
```

**Security rules the reviewer checks**

- Passwords are stored only as BCrypt hashes; the login error never says whether the email or the password was wrong.
- The JWT secret is at least 32 bytes, comes from an environment variable, and is the same in `.env` only; it is not in Git, in the images, or in `application.yml`.
- 401 and 403 come back in the standard JSON error format (custom `AuthenticationEntryPoint` and `AccessDeniedHandler`), never an HTML page or a stack trace.
- The first ADMIN is created at startup from `ADMIN_EMAIL` and `ADMIN_PASSWORD` environment variables, only if no ADMIN exists. `register` can only create CUSTOMER accounts.
- UI buttons are hidden by role for usability, but the server rules are what protect the data.

**Known limits, discussed in class**

- `/internal/**` relies on network isolation plus a forwarded user token. A stronger design uses service-to-service credentials (OAuth2 client credentials); that is a stretch goal.
- HS256 uses one shared secret in every service. RS256 with a public key (JWKS) removes the need to share the signing key.
- Tokens cannot be revoked before they expire; refresh tokens and a short expiry are the usual remedy.
- The SPA keeps the token in `sessionStorage`, which a cross-site scripting bug could read. An HttpOnly cookie is the alternative, with its own CSRF trade-off.

## 11. Docker and Docker Compose

Every Java service uses the **same** multi-stage Dockerfile. A build argument names the Maven module. Stage one compiles with Maven and the JDK; stage two copies only the JAR onto a small JRE image and runs as a non-root user.

```dockerfile
# docker/Dockerfile.service
FROM maven:3.9-eclipse-temurin-21 AS build
ARG SERVICE
WORKDIR /workspace
COPY . .
RUN --mount=type=cache,target=/root/.m2 \
    mvn -q -pl ${SERVICE} -am -DskipTests package

FROM eclipse-temurin:21-jre-alpine
ARG SERVICE
RUN addgroup -S app && adduser -S app -G app
USER app
WORKDIR /app
COPY --from=build /workspace/${SERVICE}/target/*.jar app.jar
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"
ENTRYPOINT ["java", "-jar", "app.jar"]
```

A `.dockerignore` at the repository root keeps `target/`, `node_modules/`, `.git/`, `.env` and IDE folders out of the build context.

**The SPA image** builds with Node, then serves static files from Nginx. Nginx also proxies `/api` to the gateway, so the browser talks to one origin and no CORS setup is needed in Docker.

```dockerfile
# frontend/Dockerfile
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

FROM nginx:1.27-alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
```

```nginx
# frontend/nginx.conf
server {
  listen 80;
  root /usr/share/nginx/html;

  location /api/ {
    proxy_pass http://api-gateway:8080;
    proxy_set_header Host $host;
    proxy_set_header X-Request-Id $request_id;
  }
  location / {
    try_files $uri /index.html;        # React Router paths
  }
  add_header X-Content-Type-Options nosniff;
  add_header X-Frame-Options DENY;
}
```

**Docker Compose.** Only `frontend` and `api-gateway` publish ports. MySQL and the services are reachable on the private `backend` network only.

```yaml
# docker-compose.yml (abridged: customer-service and order-service follow the product-service pattern)
name: shopsmart

services:
  mysql:
    image: mysql:8.4
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
    volumes:
      - mysql-data:/var/lib/mysql
      - ./docker/mysql-init:/docker-entrypoint-initdb.d:ro
    healthcheck:
      test: ["CMD-SHELL", "mysqladmin ping -h localhost -p$$MYSQL_ROOT_PASSWORD --silent"]
      interval: 10s
      timeout: 5s
      retries: 10
    networks: [backend]

  discovery-server:
    build: { context: ., dockerfile: docker/Dockerfile.service, args: { SERVICE: discovery-server } }
    environment:
      SERVER_PORT: 8761
    healthcheck:
      test: ["CMD-SHELL", "wget -qO- http://localhost:8761/actuator/health | grep -q UP"]
      interval: 10s
      retries: 12
    networks: [backend]

  product-service:
    build: { context: ., dockerfile: docker/Dockerfile.service, args: { SERVICE: product-service } }
    environment:
      SERVER_PORT: 8083
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/product_db
      SPRING_DATASOURCE_USERNAME: product_user
      SPRING_DATASOURCE_PASSWORD: ${PRODUCT_DB_PASSWORD}
      EUREKA_CLIENT_SERVICEURL_DEFAULTZONE: http://discovery-server:8761/eureka
      EUREKA_INSTANCE_PREFERIPADDRESS: "true"
      APP_JWT_SECRET: ${JWT_SECRET}
    depends_on:
      mysql:            { condition: service_healthy }
      discovery-server: { condition: service_healthy }
    healthcheck:
      test: ["CMD-SHELL", "wget -qO- http://localhost:8083/actuator/health | grep -q UP"]
      interval: 10s
      retries: 12
    networks: [backend]

  api-gateway:
    build: { context: ., dockerfile: docker/Dockerfile.service, args: { SERVICE: api-gateway } }
    ports: ["8080:8080"]
    environment:
      SERVER_PORT: 8080
      EUREKA_CLIENT_SERVICEURL_DEFAULTZONE: http://discovery-server:8761/eureka
      EUREKA_INSTANCE_PREFERIPADDRESS: "true"
      APP_JWT_SECRET: ${JWT_SECRET}
    depends_on:
      discovery-server: { condition: service_healthy }
    networks: [backend]

  frontend:
    build: ./frontend
    ports: ["3000:80"]
    depends_on: [api-gateway]
    networks: [backend]

volumes:
  mysql-data:

networks:
  backend:
```

```properties
# .env.example  (copy to .env; .env is in .gitignore)
MYSQL_ROOT_PASSWORD=
AUTH_DB_PASSWORD=
CUSTOMER_DB_PASSWORD=
PRODUCT_DB_PASSWORD=
ORDER_DB_PASSWORD=
JWT_SECRET=            # at least 32 random characters
ADMIN_EMAIL=
ADMIN_PASSWORD=
```

**Commands the learner must know**

| Command | Purpose |
| --- | --- |
| `docker compose up --build` | Build images and start everything |
| `docker compose ps` | Show containers and their health |
| `docker compose logs -f order-service` | Follow one service's logs |
| `docker compose up -d --build product-service` | Rebuild and restart one service |
| `docker compose down` | Stop and remove containers, keep the database volume |
| `docker compose down -v` | Also delete the database volume (start from scratch) |

**Hints**

- Start order is handled by `depends_on` with `service_healthy`. Even so, Spring Boot services should retry the database at startup (HikariCP does this for a short time); do not rely on start order alone.
- Containers have random hostnames, so set `EUREKA_INSTANCE_PREFERIPADDRESS=true`, or services register under names the others cannot resolve.
- `localhost` inside a container means that container. Use service names (`mysql`, `discovery-server`).
- The MySQL init scripts run only when the volume is empty. If you change them, run `docker compose down -v`.
- For debugging, add a `docker-compose.override.yml` that publishes MySQL on port 3307. Do not publish it in the main file.
- Check image size with `docker images`; the final images should be a few hundred MB, not over a gigabyte.

## 12. Key Implementation Notes

**Service build order.** Each row ends with something a learner can show.

| Step | Build | Rule |
| --- | --- | --- |
| 1 | Parent POM and `common` module | Error record, exception classes, `GlobalExceptionHandler`, `ResourceServerConfig` |
| 2 | `customer-service` | Entity, repository, service, controller, Flyway script, validation |
| 3 | `product-service` | Same pattern, plus `@Version`, `decreaseStock`, `stock_reservations`, internal endpoints |
| 4 | `discovery-server` and `api-gateway` | Routes from section 6; services register by name |
| 5 | `order-service` | Entity with plain ids, Feign clients, the saga, status rules |
| 6 | `auth-service` | Users, BCrypt, login, JWT issue, admin bootstrap |
| 7 | Security rules | Token check at gateway and in services, `@PreAuthorize`, ownership checks |
| 8 | Docker | One Dockerfile, then Compose, then the SPA image |

**`OrderStatus.canMoveTo(next)`**

| From status | Allowed next status |
| --- | --- |
| PENDING | PLACED, REJECTED (set only by the saga, never by the API) |
| PLACED | SHIPPED, CANCELLED |
| SHIPPED | DELIVERED |
| DELIVERED, CANCELLED, REJECTED | None |

**Feign clients and errors**

```java
@FeignClient(name = "product-service")
public interface ProductClient {

    @PostMapping("/internal/products/{id}/reserve")
    ReservationResponse reserve(@PathVariable Long id, @RequestBody ReserveRequest body);

    @PostMapping("/internal/reservations/{orderId}/release")
    void release(@PathVariable Long orderId);
}
```

An `ErrorDecoder` turns downstream 404 into `ResourceNotFoundException` and 409 into `OutOfStockException`, so the saga code reads like normal Java. Everything else becomes a runtime exception that the saga treats as an unknown outcome.

```yaml
spring:
  cloud:
    openfeign:
      client:
        config:
          default:
            connectTimeout: 2000
            readTimeout: 2000
      circuitbreaker:
        enabled: true
resilience4j:
  circuitbreaker:
    instances:
      product-service:
        slidingWindowSize: 10
        failureRateThreshold: 50
        waitDurationInOpenState: 10s
```

**Rules for other steps** (kept as small as the monolith's)

- `cancel`: find the order (404 if missing or not owned); status must be PLACED, else 400; call `release(orderId)` (retry up to 3 times, it is idempotent); set CANCELLED.
- `updateStatus`: find the order; reject CANCELLED and any move `canMoveTo` refuses (400); set the new status. `@Version` makes a simultaneous update return 409.
- `deleteCustomer` (customer-service): find the customer; call `GET /internal/orders/exists?customerId=`; if true, throw a 409 with a clear message; else delete. If `order-service` is down, return 503; never delete on a guess.
- `deleteProduct`: the same rule with `?productId=`.
- `POST /api/customers/me`: take `sub` from the token as `userId`; if a profile already exists for that user, return 409.
- Admin bootstrap: a `CommandLineRunner` in `auth-service` that creates the ADMIN from the environment if none exists.

**Request id.** A small gateway filter adds an `X-Request-Id` header if missing. Each service copies it into the logging MDC and the Feign interceptor forwards it, so one order can be followed across all log files.

**Hints**

- Put the `@Transactional` steps (`createPending`, `confirm`, `reject`) in `OrderTxService`, a separate bean. A method called from another method of the same class skips the Spring proxy and the transaction annotation does nothing.
- Prove the saga: stop `product-service`, place an order, and check that the order is REJECTED, the caller got 503, and no stock changed. Then start it again and place the same order successfully.
- Prove idempotency: call `release` twice for one order id and check that stock increased once.
- A circuit breaker's fallback should not pretend success. Throw `ServiceUnavailableException`.

## 13. Testing Strategy

Four levels keep the system safe to change.

| Level | Tools | What it proves |
| --- | --- | --- |
| Unit | JUnit 5, Mockito | Business rules in services with mocked repositories and Feign clients |
| Repository | `@DataJpaTest`, Testcontainers (MySQL 8) | Queries, constraints, `@Version`, and the stock update against a real MySQL |
| Web and security slice | `@WebMvcTest`, Spring Security Test (`jwt()` post-processor) | 401, 403, role rules and ownership on each controller |
| End to end | Postman, Docker Compose | The whole stack: login, gateway routing, saga, roles |

**Postman collection** with folders `Auth`, `Customers`, `Products`, `Orders` and `Security`. A collection-level pre-request script logs in three users (admin, staff, customer) and stores `adminToken`, `staffToken` and `customerToken` in the environment. Environment variables: `baseUrl` (`http://localhost:8080/api`), `customerId`, `productId`, `orderId`.

| ID | Scenario | Request | Expected result |
| --- | --- | --- | --- |
| T-01 | Register a customer account | `POST /api/auth/register` | 201 |
| T-02 | Register with a duplicate email | `POST /api/auth/register` | 409 |
| T-03 | Register with a 5-character password | `POST /api/auth/register` | 400 with field message |
| T-04 | Log in | `POST /api/auth/login` | 200, `accessToken` present |
| T-05 | Log in with a wrong password | `POST /api/auth/login` | 401, generic message |
| T-06 | Call a protected endpoint with no token | `GET /api/products` | 401, standard JSON error |
| T-07 | Call with an expired or tampered token | `GET /api/products` | 401 |
| T-08 | Customer creates a product | `POST /api/products` with `customerToken` | 403 |
| T-09 | Staff creates a product | `POST /api/products` with `staffToken` | 201, response has `id` |
| T-10 | Staff creates a product with a blank name | `POST /api/products` | 400 with field message |
| T-11 | Search products | `GET /api/products?q=mouse&page=0&size=5` | 200, `content` array |
| T-12 | Customer creates own profile | `POST /api/customers/me` | 201 |
| T-13 | Staff creates a customer with a duplicate email | `POST /api/customers` | 409 |
| T-14 | Customer lists all customers | `GET /api/customers` with `customerToken` | 403 |
| T-15 | Customer places a valid order | `POST /api/orders` | 201, PLACED, total = price × quantity |
| T-16 | Check stock after the order | `GET /api/products/{id}` | `stockQty` reduced by the order quantity |
| T-17 | Order more than stock | `POST /api/orders` | 409, stock unchanged, order is REJECTED |
| T-18 | Order for an unknown product | `POST /api/orders` | 404 |
| T-19 | Customer reads own order history | `GET /api/customers/{id}/orders` | 200, contains the new order |
| T-20 | Customer reads another customer's order | `GET /api/orders/{id}` | 404 |
| T-21 | Staff ships then delivers | `PUT /api/orders/{id}/status` twice | 200 each time |
| T-22 | Invalid status jump | `PUT /api/orders/{id}/status` to PLACED | 400 |
| T-23 | Customer cancels own PLACED order | `PUT /api/orders/{id}/cancel` | 200, stock restored |
| T-24 | Cancel the same order again | `PUT /api/orders/{id}/cancel` | 400, stock not increased again |
| T-25 | Staff deletes a customer | `DELETE /api/customers/{id}` with `staffToken` | 403 |
| T-26 | Admin deletes a customer who has orders | `DELETE /api/customers/{id}` with `adminToken` | 409 |
| T-27 | Reach an internal endpoint through the gateway | `POST /internal/products/1/reserve` | 403 or 404 |
| T-28 | Product service stopped, then an order | `POST /api/orders` | 503, order REJECTED, stock unchanged |

**Pre-request script** for the collection:

```javascript
const login = (email, password, key) =>
  pm.sendRequest({
    url: pm.environment.get("baseUrl") + "/auth/login",
    method: "POST",
    header: { "Content-Type": "application/json" },
    body: { mode: "raw", raw: JSON.stringify({ email, password }) }
  }, (err, res) => pm.environment.set(key, res.json().accessToken));

login(pm.environment.get("adminEmail"), pm.environment.get("adminPassword"), "adminToken");
```

**Unit tests: the business rules** (JUnit 5 with Mockito; repositories and Feign clients mocked)

| Test | Given | Expect |
| --- | --- | --- |
| Place order, stock available | Customer found; `reserve` returns name and price | Order saved PENDING then PLACED with correct total; `release` never called |
| Place order, out of stock | `reserve` throws `OutOfStockException` | Order REJECTED; exception rethrown; `release` not called |
| Place order, unknown customer | Customer client throws not found | Nothing saved; `reserve` never called |
| Place order, timeout | `reserve` throws a runtime exception | `release(orderId)` called once; order REJECTED; `ServiceUnavailableException` |
| Customer orders for someone else | Caller is CUSTOMER with id 3, body says customer 9 | The service uses id 3 |
| Cancel a PLACED order | Order PLACED | `release` called; status CANCELLED |
| Cancel a SHIPPED order | Order SHIPPED | `InvalidStatusException`; `release` never called |
| Read another customer's order | Caller customer 3, order of customer 9 | `ResourceNotFoundException` |
| Reserve twice, same order id | Reservation already exists | Stock reduced once; same response returned |
| Release twice | Reservation already RELEASED | Stock increased once |

**Repository tests** (`@DataJpaTest` with a Testcontainers MySQL, `@AutoConfigureTestDatabase(replace = NONE)`): `findByNameContainingIgnoreCase` paging, `decreaseStock` returns 0 when stock is too low, `CHECK` and `UNIQUE` constraints reject bad rows, Flyway migrations apply on an empty schema.

**Web-security tests** (`@WebMvcTest` with `SecurityMockMvcRequestPostProcessors.jwt()`): each endpoint gets one test per role and one without a token. Use `jwt().authorities(new SimpleGrantedAuthority("ROLE_CUSTOMER"))`.

**Compose smoke test:** a short script that runs `docker compose up -d --build`, waits until all containers are healthy, runs the Postman collection with Newman, and runs `docker compose down -v`.

**Hint:** write the Postman tests as each endpoint is finished. The collection is also the demo script.

## 14. Week-by-Week Implementation Plan

The project runs across four weeks, about 20 working days. The order is deliberate: data layer first, services and gateway next, then security, then Docker and the UI. Each week ends with a gate, a short demo that a trainer or peer reviews before the next week starts.

| Day | Focus | Output to show |
| --- | --- | --- |
| 1 | Parent POM, `common` module, MySQL in Compose with the init script | MySQL container healthy; four schemas exist |
| 2 | `customer-service` with Spring Data JPA, Flyway, validation | Customer CRUD, search and paging in Postman |
| 3 | `product-service`: entity, repository, `@Version`, search | Product CRUD and search work |
| 4 | `decreaseStock`, `stock_reservations`, reserve and release endpoints | Reserve twice for one order id changes stock once |
| 5 | Shared `GlobalExceptionHandler`; repository tests with Testcontainers | **Gate 1:** two services on JPA, their tests pass |
| 6 | `discovery-server` and `api-gateway` with the route table | Calls through port 8080 reach both services |
| 7 | `order-service`: `CustomerOrder`, repository, status enum, history route | Orders table created; history route works |
| 8 | Feign clients, error decoder, token relay stub, timeouts | `order-service` reads a customer and reserves stock |
| 9 | The saga: PENDING, reserve, confirm, reject, compensation | Valid order PLACED; low stock REJECTED |
| 10 | Cancel, status update, delete rules, circuit breaker, Mockito tests | **Gate 2:** order flow works across services, T-15 to T-24 pass with security off |
| 11 | `auth-service`: users, BCrypt, register, login, JWT, admin bootstrap | Token returned; wrong password 401 |
| 12 | Resource-server config in `common`; gateway token check | No token: 401 at the gateway and at each service |
| 13 | `@PreAuthorize` roles; ownership rules; `customers/me` | Customer sees only own data |
| 14 | 401 and 403 JSON handlers; web-security slice tests | **Gate 3:** T-01 to T-28 pass (T-28 after Docker) |
| 15 | `Dockerfile.service`; run each service from its image | Every image builds and starts |
| 16 | Full `docker-compose.yml`, health checks, `.env`, no published DB port | `docker compose up --build` gives a healthy system |
| 17 | React: login page, token storage, Axios interceptor, protected routes | Log in from the SPA; 401 sends the user back to login |
| 18 | Role-aware screens; frontend Dockerfile and Nginx proxy | Staff and customer see different menus; SPA on port 3000 |
| 19 | Smoke test with Newman; saga failure test (stop `product-service`); bug fixes | T-28 passes; the stack recovers when the service restarts |
| 20 | README, architecture diagram, role matrix, demo rehearsal | **Gate 4:** demo ready |

## 15. Frontend Changes

The SPA keeps its screens. It gains a login page, a token, and role awareness. In Docker it calls `/api` on its own origin, and Nginx forwards to the gateway.

| Route | Page | Who | New or changed |
| --- | --- | --- | --- |
| `/login`, `/register` | Login and register forms | public | New |
| `/` | Product list, Order button | any user | Order button needs login |
| `/products/new`, `/products/:id/edit` | Product forms | STAFF, ADMIN | Guarded by role |
| `/customers`, `/customers/new`, `/customers/:id/edit` | Customer management | STAFF, ADMIN | Guarded by role |
| `/profile` | My profile (create or edit) | CUSTOMER | New |
| `/customers/:id` | Customer detail with order history | STAFF, ADMIN | Guarded by role |
| `/products/:id/order` | Order form | any user | For a CUSTOMER the customer dropdown is hidden |
| `/orders` | All orders with status actions | STAFF, ADMIN | Guarded by role |
| `/my-orders` | My orders with a Cancel button | CUSTOMER | New |
| `/orders/:id` | Order detail | owner, STAFF, ADMIN | Shows 404 page for others |

New pieces: `AuthContext` (token, user, role, `login`, `logout`), `ProtectedRoute` (takes `roles`), `LoginPage`, `RegisterPage`, `ProfilePage`, `MyOrdersPage`.

```javascript
import axios from "axios";

const api = axios.create({ baseURL: import.meta.env.VITE_API_URL ?? "/api" });

api.interceptors.request.use((config) => {
  const token = sessionStorage.getItem("token");
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

api.interceptors.response.use(
  (res) => res,
  (err) => {
    if (err.response?.status === 401) {
      sessionStorage.removeItem("token");
      window.location.assign("/login");
    }
    return Promise.reject(err);
  }
);

export const login = (email, password) => api.post("/auth/login", { email, password });
export default api;
```

**Hints**

- In Vite development, set `VITE_API_URL=http://localhost:8080/api` and enable CORS for `http://localhost:5173` in the gateway only.
- Handle the new errors: 401 goes to login, 403 shows "You do not have access", 503 shows "Please try again in a moment".
- Decode the token's `roles` claim to show or hide menus, but never treat the browser as the security boundary.

## 16. Deliverables, Evaluation and Stretch Goals

**Deliverables**

- One Git repository with the parent POM, all services, the `frontend` folder and weekly commits.
- Flyway scripts in each service.
- `Dockerfile.service`, the frontend Dockerfile, `docker-compose.yml`, `.env.example` and `.dockerignore`. No real secrets in Git.
- Postman collection and environment exported as JSON, plus the Newman smoke-test script.
- README with the architecture diagram, run steps, the role matrix, the saga steps, the API list and screenshots.
- A 12-minute demo: `docker compose up`; register and log in as a customer; show a 403 on a staff endpoint; place an order and show stock drop; stop `product-service` and show the 503 and REJECTED order; restart it and place the order again; cancel and show stock return; log in as staff and ship an order.

**Evaluation rubric (100 marks)**

| Criterion | Marks | What the reviewer looks for |
| --- | --- | --- |
| Service decomposition, gateway and discovery | 15 | Clear boundaries, routes in the right order, `/internal/**` blocked, no cross-service database access |
| Spring Data JPA and migrations | 15 | Repositories, paging, JPQL, `@Version`, Flyway, `validate`, one schema and user per service |
| Order workflow and consistency | 15 | Saga with PENDING, PLACED and REJECTED; compensation; idempotent reserve and release; no remote call in a transaction |
| Security | 20 | BCrypt, JWT, checks at gateway and service, roles, ownership returns 404, JSON 401 and 403, no secrets in Git |
| Docker and Compose | 15 | Multi-stage images, non-root user, health checks, private network, only gateway and SPA published, one-command start |
| Front end | 5 | Login, guarded routes, role-aware menus, 401 handling |
| Testing | 10 | Postman T-01 to T-28, at least six Mockito tests, at least two Testcontainers repository tests, one web-security test per role |
| Code quality and README | 5 | Layered packages, constructor injection, clean `common` module, clear README |

**Stretch goals, in suggested order**

1. Replace the synchronous saga with events: `order-service` publishes `OrderCreated`, `product-service` answers with `StockReserved` or `StockRejected` (Kafka or RabbitMQ).
2. Add a Spring Cloud Config Server, or keep configuration in Compose files per environment.
3. Add distributed tracing with Micrometer Tracing and Zipkin.
4. Replace the shared JWT secret with RS256 and a JWKS endpoint; add refresh tokens.
5. Use OAuth2 client credentials for service-to-service calls instead of forwarding the user's token.
6. Add rate limiting at the gateway (Redis) and a login-attempt lockout.
7. Aggregate Swagger/OpenAPI from every service at the gateway.
8. Add `order_items` so one order holds several products; the saga then reserves several products and must release all of them on failure.
9. Write Kubernetes manifests (Deployments, Services, Secrets) for the same images.
10. Run the Postman collection in a GitHub Actions workflow that builds the images and runs Compose.

**Discussion: what did we pay for the split?** Learners should leave with a written answer. A good one names:

- **Consistency:** the single transaction is gone. We replaced it with a saga, idempotent calls and a REJECTED status, and we accept that stock and orders can be briefly out of step.
- **Latency and failure:** a call inside one JVM became a network call that can be slow or fail. Timeouts, a circuit breaker and 503 responses are the cost.
- **Coupling that remains:** `customer-service` and `product-service` ask `order-service` whether a record has orders, and `order-service` calls both. The services are separate deployments but not fully independent.
- **Operations:** one build became six, plus a registry, a gateway and a network. Docker Compose hides most of that locally, but production would need monitoring, tracing and a deployment platform.
- **When the monolith was the better choice:** a small team, one release cycle and no need to scale parts separately. The monolith is not a mistake to fix; it is the right start, and this project shows what a split costs.
