# 🧾 SYOS – Smart Yet Organized System (Billing System)

SYOS is a full-stack web-based billing system designed to replace traditional manual billing methods with an automated solution. It streamlines grocery store operations by 
enabling real-time sales, stock updates, and billing, supporting both in-store and online sales channels.

---

## 🏗 Architecture Overview

SYOS is built using a **three-tier architecture**:

- **Frontend:** React (JavaScript)
- **Backend:** Java EE (Servlets)
- **Database:** MySQL

Follows the **MVC pattern** with clear separation of concerns:
- Model: Entities like `User`, `Bill`, `Item`, `Batch`
- View: Dynamic UI using React
- Controller: Java Servlets handling HTTP requests
- DAO & Services: Business logic and database access

---

## 🧠 Features

- Unique item code-based billing
- Real-time stock reduction and bill generation
- In-store and online sales tracking
- Multiple report generations (stock, bill, reorder, reshelve)
- User authentication (Login/Register)
- Cart and checkout system
- Concurrency handled at both frontend (async/await) and backend (thread-per-request)
- JUnit + Postman test coverage

---

## 🚀 Tech Stack

| Layer        | Technology             |
|--------------|------------------------|
| Frontend     | React.js               |
| Backend      | Java Servlets (Java EE)|
| Database     | MySQL                  |
| Tools Used   | Postman, JUnit, Gson   |

---

## 🧪 Testing

- Backend Unit Tests: Written using **JUnit**
- API Testing: Performed with **Postman**
- Coverage Reports: Included under `/test` directory (if applicable)

---

## 🧵 Concurrency Handling

- **Backend:** Java EE’s built-in thread-per-request model with transaction isolation
- **Frontend:** React’s async operations and state management (non-blocking UI)

---

## ⚙️ Setup Instructions

### 🔧 Prerequisites:
- Java 11+
- Apache Tomcat 9+
- Node.js & npm
- MySQL Server

### 📁 Folder Structure:
