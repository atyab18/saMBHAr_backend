# Mess Management System — Backend

A Spring Boot backend for managing mess orders with OTP-based delivery verification and role-based access control.

---

## Tech Stack

| Layer    | Technology                          |
|----------|-------------------------------------|
| Backend  | Spring Boot, Java 17, JPA/Hibernate |
| Database | MySQL 8                             |
| Build    | Maven                               |

---

## Project Structure

```
back/
└── src/main/java/com/mess/
    ├── controller/    (Auth, Mess, Menu, Order)
    ├── service/
    ├── entity/        (User, Mess, Menu, Order, OrderHistory)
    ├── repository/
    └── dto/
```

Runs on → `http://localhost:8082`

---

## Features

### Authentication & Authorization
- Role-based access control: `USER` vs `ADMIN`
- Mobile number + password login

### User Features
- Register / Login with mobile number and password
- Place orders (auto-calculated total)
- Receive a **4-digit OTP** on successful order placement
- View active and completed order history
- Edit profile (name, mobile, password, address)

### Mess Admin Features
- Register / Login (automatically creates a Mess entity on registration)
- Manage menu combos — Add / Edit / Delete
- View active incoming orders
- Enter OTP provided by customer to confirm delivery → marks order as `COMPLETED`
- View full order history

### OTP-Based Delivery Confirmation
- A random 4-digit OTP is generated when a user places an order
- User shares the OTP with the mess admin upon delivery
- Admin enters the OTP in the system to complete the order
- Completion is logged in the `order_history` table

---

## Database Entities

| Entity         | Key Fields                                                                  |
|----------------|-----------------------------------------------------------------------------|
| `User`         | id, name, mobileNo (unique), password, address, role                       |
| `Mess`         | id, name, address, ownerId (→ User)                                         |
| `Menu`         | id, messId, comboName, items, price                                         |
| `Order`        | id, userId, messId, comboId, quantity, totalPrice, status, otp, createdAt  |
| `OrderHistory` | id, orderId, status, completedAt                                            |

**Order Status Values:** `ACTIVE` → `COMPLETED`

---

## REST API Endpoints

**Base URL:** `http://localhost:8082`

### Auth (`/api/auth`)
| Method | Endpoint                     | Description               |
|--------|------------------------------|---------------------------|
| POST   | `/api/auth/register`         | Register user or admin    |
| POST   | `/api/auth/login`            | Login with mobile + password |
| PUT    | `/api/auth/profile/{userId}` | Update user profile       |

### Mess (`/api/mess`)
| Method | Endpoint          | Description     |
|--------|-------------------|-----------------|
| GET    | `/api/mess/`      | Get all messes  |
| GET    | `/api/mess/{id}`  | Get mess by ID  |

### Menu (`/api/menu`)
| Method | Endpoint                  | Description               |
|--------|---------------------------|---------------------------|
| GET    | `/api/menu/mess/{messId}` | Get all combos for a mess |
| POST   | `/api/menu`               | Add new combo (admin)     |
| PUT    | `/api/menu/{id}`          | Update combo (admin)      |
| DELETE | `/api/menu/{id}`          | Delete combo (admin)      |

### Orders (`/api/orders`)
| Method | Endpoint                           | Description                         |
|--------|------------------------------------|-------------------------------------|
| POST   | `/api/orders`                      | Place order (generates 4-digit OTP) |
| POST   | `/api/orders/verify-otp`           | Verify OTP → mark order COMPLETED   |
| GET    | `/api/orders/user/{id}/active`     | User's active orders                |
| GET    | `/api/orders/user/{id}/history`    | User's completed orders             |
| GET    | `/api/orders/mess/{id}/active`     | Admin's active orders               |
| GET    | `/api/orders/mess/{id}/history`    | Admin's order history               |

---

## Prerequisites

- Java 17+
- Maven 3.8+
- MySQL 8+

---

## Setup & Run

### 1. MySQL — Create Database
```sql
CREATE DATABASE mess_management;
```
> JPA will auto-create all tables on first run.

### 2. Configure `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/mess_management
spring.datasource.username=root
spring.datasource.password=
server.port=8082
```

### 3. Run
```bash
cd back
mvn spring-boot:run
```
Runs at → `http://localhost:8082`

---

## Order Flow

```
User places order
      ↓
4-digit OTP generated & stored in Order record
      ↓
User receives meal & shares OTP with Admin
      ↓
Admin submits OTP via /api/orders/verify-otp
      ↓
Order marked COMPLETED → logged in Order History
```