# 📝 Blog Backend API (Spring Boot)

A **RESTful Blog Backend API** built using **Spring Boot**.  
This project provides secure APIs for managing **users, blog posts, comments, and categories** with **JWT authentication and role-based authorization**.

The project follows a **layered architecture** including Controllers, Services, Repositories, DTOs, and Security configurations.

---

# 🚀 Features

- User Registration & Login
- JWT Authentication & Authorization
- Role Based Access (Admin / User)
- Create, Update, Delete Blog Posts
- Category Management
- Comment on Posts
- Pagination & Sorting
- File Upload Support
- Global Exception Handling
- Swagger API Documentation
- Clean Layered Architecture

---

# 🛠 Tech Stack

| Technology | Purpose |
|------------|--------|
| Spring Boot | Backend Framework |
| Spring Security | Authentication & Authorization |
| JWT (JSON Web Token) | Secure API Authentication |
| Spring Data JPA | Database Operations |
| Hibernate | ORM Framework |
| Swagger | API Documentation |
| Maven | Dependency Management |
| Java | Programming Language |

---

# 🔐 Authentication (JWT)

This project uses **JWT (JSON Web Token)** for securing APIs.

### Authentication Flow

1. User registers or logs in.
2. Server validates credentials.
3. Server generates a **JWT Token**.
4. Client sends the token in request headers.
5. Spring Security validates the token before accessing protected APIs.

Example Header:

```
Authorization: Bearer <JWT_TOKEN>
```

### Security Classes

- `JwtAuthenticationFilter` → Intercepts requests and validates token  
- `JwtTokenHelper` → Generates and validates JWT token  
- `JwtAuthenticationEntryPoint` → Handles unauthorized access  
- `CustomUserDetailService` → Loads user from database  

---

# ⚙ Project Structure

```
src/main/java
│
├── config
│   ├── AppConstants.java
│   ├── SecurityConfig.java
│   └── SwaggerConfig.java
│
├── controllers
│   ├── AuthController.java
│   ├── CategoryController.java
│   ├── CommentController.java
│   ├── PostController.java
│   └── UserController.java
│
├── entities
│   ├── User.java
│   ├── Role.java
│   ├── Post.java
│   ├── Comment.java
│   └── Category.java
│
├── exception
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
│
├── payloads
│   ├── UserDto.java
│   ├── PostDto.java
│   ├── CommentDto.java
│   ├── CategoryDto.java
│   └── ApiResponse.java
│
├── repositories
│   ├── UserRepo.java
│   ├── PostRepo.java
│   ├── CommentRepo.java
│   ├── CategoryRepo.java
│   └── RoleRepo.java
│
├── services
│   ├── UserService.java
│   ├── PostService.java
│   ├── CommentService.java
│   ├── CategoryService.java
│   └── FileService.java
│
├── services/impl
│   ├── UserImpl.java
│   ├── PostImpl.java
│   ├── CommentImpl.java
│   ├── CategoryImpl.java
│   └── FileImpl.java
│
└── security
    ├── JwtAuthenticationFilter.java
    ├── JwtAuthenticationEntryPoint.java
    ├── JwtTokenHelper.java
    └── CustomUserDetailService.java
```

---

# 🌐 API Modules

### Authentication
- Register User
- Login User
- Generate JWT Token

### Users
- Create User
- Update User
- Delete User
- Get All Users

### Posts
- Create Post
- Update Post
- Delete Post
- Get Posts
- Get Post by ID

### Categories
- Create Category
- Update Category
- Delete Category
- Get All Categories

### Comments
- Add Comment
- Delete Comment

---

# ⚠ Global Exception Handling

The project includes a **Global Exception Handler** to manage application errors in a centralized way.

### Handled Exceptions

- Resource Not Found
- Invalid Requests
- Authentication Errors
- Validation Errors

Class Used:

```
GlobalExceptionHandler.java
```

Benefits:
- Cleaner controllers
- Consistent API responses
- Centralized error management

---

# 📄 API Documentation

Swagger is integrated for API testing.

After running the project, open:

```
http://localhost:8080/swagger-ui/
```

---

# ▶ How to Run the Project

### 1 Clone the repository

```bash
git clone https://github.com/your-username/blog-backend.git
```

### 2 Navigate to project folder

```bash
cd blog-backend
```

### 3 Run the project

```bash
mvn spring-boot:run
```

or run from IDE.

---

# 🔑 Environment Configuration

Update database configuration in:

```
application.properties
```

Example:

```
spring.datasource.url=jdbc:mysql://localhost:3306/blog
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

---

# 📌 Future Improvements

- Email Verification
- Image Storage (Cloudinary / AWS S3)
- Like / Dislike Posts
- Post Search Feature
- Frontend Integration (React)

---

# 👨‍💻 Author

**Aman Yadav**

Backend Developer  
Java | Spring Boot | REST API | Security

---

# ⭐ Support

If you like this project, please ⭐ the repository.
