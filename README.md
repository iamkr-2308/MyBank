# Bank Management System — Spring Boot REST API

A production-style banking REST API built with Spring Boot, converted from an earlier console-based JDBC application into a fully layered, secured, documented, and tested backend service.

## Features

- Full CRUD for bank accounts (create, view, balance check)
- Deposit and withdraw with atomic transaction handling (`@Transactional`)
- Transaction history with pagination
- JWT-based authentication (register/login), passwords hashed with BCrypt
- Global exception handling with proper HTTP status codes (400, 404, 500)
- Input validation on all request bodies
- Interactive API documentation via Swagger/OpenAPI
- Unit tests for service-layer business logic (JUnit 5 + Mockito)

## Tech Stack


|
Layer
|
Technology
|
|
---
|
---
|
|
Language
|
Java 17
|
|
Framework
|
Spring Boot 3.5
|
|
Data Access
|
Spring Data JPA / Hibernate
|
|
Database
|
MySQL 8
|
|
Security
|
Spring Security + JWT (JJWT library)
|
|
Documentation
|
Springdoc OpenAPI (Swagger UI)
|
|
Testing
|
JUnit 5, Mockito
|
|
Build Tool
|
Maven
|
|
Boilerplate Reduction
|
Lombok
|

## Architecture

Standard layered architecture:

Controller → Service → Repository → Entity
↑
DTOs (request/response) Global Exception Handler
↑
JWT Auth Filter → Security Config


Security sits as a filter layer in front of the existing REST API — the controller/service/repository code for accounts and transactions is completely independent of the authentication layer.

## Getting Started

### Prerequisites

- Java 17 (Temurin recommended)
- Maven (or use the included `mvnw` wrapper)
- MySQL 8.x running locally

### 1. Clone the repository

```bash
git clone https://github.com/iamkr-2308/MyBank.git
```

### 2. Create the database

```sql
CREATE DATABASE IF NOT EXISTS bank_app;
```

### 3. Set your database password as an environment variable

Never hardcode credentials. Set `DB_PASSWORD` in your environment or IDE run configuration:

```bash
export DB_PASSWORD=your_mysql_root_password
```

### 4. Run the application

```bash
./mvnw spring-boot:run
```

The app starts on `http://localhost:8080`. Hibernate will auto-create the `accounts`, `transactions`, and `users` tables on first run.

## API Documentation

Once running, explore and test every endpoint interactively at:

http://localhost:8080/swagger-ui.html


## Authentication Flow

All `/api/**` endpoints require a JWT. `/auth/**` and Swagger are open.

**1. Register**

POST /auth/register
{ "username": "kunal", "password": "securepass123" }


**2. Login** — returns a JWT valid for 1 hour

POST /auth/login
{ "username": "kunal", "password": "securepass123" }


**3. Use the token on any protected endpoint**

GET /api/accounts
Authorization: Bearer <token>


## Sample Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Log in, receive a JWT |
| POST | `/api/accounts` | Create a bank account |
| GET | `/api/accounts` | List all accounts |
| GET | `/api/accounts/{accNo}/balance` | Check balance |
| POST | `/api/accounts/{accNo}/deposit` | Deposit money |
| POST | `/api/accounts/{accNo}/withdraw` | Withdraw money |
| GET | `/api/accounts/{accNo}/transactions?page=0&size=10` | Paginated transaction history |

## Running Tests

```bash
./mvnw test
```

## Project Background

This project began as a console-based Java application using raw JDBC and the DAO pattern. It was rebuilt into a Spring Boot REST API to reflect real-world backend practices: layered architecture, DTOs, exception handling, pagination, authentication, and automated testing.

## Author

Kunal — MSc IT student, building toward a Java backend developer role.
