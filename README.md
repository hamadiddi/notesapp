# 📘 **NotesApp Backend – Spring Boot**

A modern, secure, and scalable **REST API for a Notes Management Application** built using **Spring Boot**.
This backend supports **user authentication**, **JWT-based security**, and **full CRUD operations** for notes.
Designed as a clean, production-ready project suitable for real-world use and portfolio presentation.

---

## 🚀 **Features**

### 🔐 **Authentication**

- User registration
- Secure login
- Password hashing (BCrypt)
- JWT-based authentication
- Route protection

### 📝 **Notes Management**

- Create notes
- Update notes
- Delete notes
- View a single note
- Retrieve all notes by logged-in user
- Search notes by keyword _(optional)_
- Tagging / Archiving _(optional)_

### ⚙️ **Architecture**

- Clean layered architecture (Controller → Service → Repository)
- DTOs for request/response handling
- Global exception handling
- Consistent API responses
- JPA/Hibernate for database operations

---

## 🛠 **Tech Stack**

| Component  | Technology                             |
| ---------- | -------------------------------------- |
| Language   | Java 17+                               |
| Framework  | Spring Boot 3+                         |
| Security   | Spring Security + JWT                  |
| Database   | PostgreSQL / MySQL                     |
| ORM        | Spring Data JPA (Hibernate)            |
| Build tool | Maven                                  |
| Testing    | Postman / Insomnia                     |
| Deployment | Docker / Render / Railway _(optional)_ |

---

## 📂 **Project Structure**

```
src/
 └── main/
      └── java/com.yourname.notesapp
            ├── controller/
            ├── service/
            ├── repository/
            ├── model/
            ├── dto/
            ├── exception/
            ├── security/
            └── config/
      └── resources/
            ├── application.properties
```

---

## ⚙️ **Setup & Installation**

### 1️⃣ Clone the repository

```bash
git clone https://github.com/yourname/notesapp-backend.git
cd notesapp-backend
```

### 2️⃣ Configure database in `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/notesapp
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3️⃣ Run the application

```bash
mvn spring-boot:run
```

The API will start at:
👉 **[http://localhost:8080](http://localhost:8080)**

---

## 🔑 **Authentication Endpoints**

### **Register**

```
POST /api/auth/register
```

**Body**

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "123456"
}
```

---

### **Login**

```
POST /api/auth/login
```

**Response**

```json
{
  "token": "jwt_token_here"
}
```

Use this token as:

```
Authorization: Bearer <token>
```

---

## 📝 **Notes API Endpoints**

### **Create Note**

```
POST /api/notes
```

```json
{
  "title": "Shopping List",
  "content": "Buy milk, eggs, rice"
}
```

---

### **Get All Notes**

```
GET /api/notes
```

---

### **Get Note by ID**

```
GET /api/notes/{id}
```

---

### **Update Note**

```
PUT /api/notes/{id}
```

```json
{
  "title": "Updated Title",
  "content": "Updated content"
}
```

---

### **Delete Note**

```
DELETE /api/notes/{id}
```

---

## 🧪 **Testing**

You can test the APIs using:

- Postman
- Insomnia
- Thunder Client (VS Code)

A sample Postman collection can be added later if needed.

---

## 📄 **API Response Format**

All responses follow a consistent structure:

```json
{
  "status": "success",
  "message": "Note created successfully",
  "data": {...}
}
```

Error example:

```json
{
  "status": "error",
  "message": "Note not found",
  "timestamp": "2025-12-03T12:00:00"
}
```

---

## 🛡 **Security**

The backend uses:

- Spring Security Filter Chain
- JWT Authentication
- HttpOnly bearer tokens
- Password hashing (BCrypt)
- User-specific data access rules

---

## 👤 **Author**

**Hamadi Iddi**
Backend Developer (Java | Spring Boot)
📧 mailto: dev@hamadiddi.com
🔗 GitHub: https://github.com/hamadiddi
🔗 Upwork: https://www.upwork.com/freelancers/~01725db450db626abe

---

## ⭐ **Contributions**

Contributions, issues, and feature requests are welcome!
Feel free to check the **issues page**.

---

## 📜 **License**

This project is licensed under the MIT License.
