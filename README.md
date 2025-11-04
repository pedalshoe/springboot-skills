Springboot Skills

# Spring Boot User Management API - Day 3

This implementation demonstrates enterprise-level Spring Boot features including **DTO mapping with MapStruct**, **Bean Validation**, and **RFC 7807 Problem+JSON error handling**.

## 🎯 Features Implemented

### 1. DTO Mapping with MapStruct
- **CreateUserRequest**: DTO for creating users
- **UpdateUserRequest**: DTO for updating users  
- **UserResponse**: DTO for API responses
- **UserMapper**: MapStruct mapper interface for conversions

### 2. Bean Validation (JSR-380)
- `@NotBlank` - ensures required fields
- `@Email` - validates email format
- `@Size` - validates string length
- Automatic validation on `@Valid` annotated request bodies

### 3. Problem+JSON Error Handling (RFC 7807)
- Standardized error responses following RFC 7807
- `GlobalExceptionHandler` catches all exceptions
- Returns structured error details with:
  - `type`: URI reference for the error type
  - `title`: Short, human-readable summary
  - `status`: HTTP status code
  - `detail`: Detailed explanation
  - `instance`: URI reference to specific occurrence
  - `timestamp`: When the error occurred
  - `errors`: Field-level validation errors (for validation failures)

### 4. Service Layer
- Business logic separated from controller
- `@Transactional` for database operations
- Clean separation of concerns

## 📁 File Structure

```
src/main/java/com/cml/springbootskills/day3/
├── controllers/
│   └── UserController.java          # REST endpoints
├── dto/
│   ├── CreateUserRequest.java       # Create DTO with validation
│   ├── UpdateUserRequest.java       # Update DTO with validation
│   └── UserResponse.java            # Response DTO
├── entities/
│   └── User.java                    # JPA entity
├── error/
│   ├── GlobalExceptionHandler.java  # Problem+JSON error handler
│   ├── ProblemDetail.java           # RFC 7807 problem detail
│   ├── UserNotFoundException.java   # Custom exception
│   └── DuplicateEmailException.java # Custom exception
├── mappers/
│   └── UserMapper.java              # MapStruct mapper
├── repositories/
│   └── UserRepository.java          # JPA repository
└── services/
    └── UserService.java             # Business logic layer
```

## 🔧 Setup Instructions

### 1. Update pom.xml
Add the following dependencies:
```xml
<!-- Validation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- MapStruct -->
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.5.5.Final</version>
</dependency>
```

Add compiler plugin configuration for MapStruct:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.11.0</version>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>org.mapstruct</groupId>
                <artifactId>mapstruct-processor</artifactId>
                <version>1.5.5.Final</version>
            </path>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>${lombok.version}</version>
            </path>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok-mapstruct-binding</artifactId>
                <version>0.2.0</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

### 2. Rebuild the Project
```bash
mvn clean install
```

This will generate the MapStruct implementation classes in `target/generated-sources/annotations/`.

### 3. Remove DataSource Exclusion
In `SpringbootSkillsApplication.java`:
```java
@SpringBootApplication  // Remove the exclude parameter
@EnableJpaRepositories(basePackages = "com.cml.springbootskills.day3.repositories")
public class SpringbootSkillsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootSkillsApplication.class, args);
    }
}
```

## 🚀 API Endpoints

### Create User
```bash
POST /day3/users
Content-Type: application/json

{
  "email": "john@example.com",
  "displayName": "John Doe"
}
```

**Success Response (201 Created):**
```json
{
  "id": 1,
  "email": "john@example.com",
  "displayName": "John Doe",
  "createdAt": "2025-11-04T10:30:00Z"
}
```

**Validation Error (400 Bad Request):**
```json
{
  "type": "about:blank",
  "title": "Validation Failed",
  "status": 400,
  "detail": "One or more fields have validation errors",
  "instance": "/day3/users",
  "timestamp": "2025-11-04T10:30:00Z",
  "errors": {
    "email": "Email must be valid",
    "displayName": "Display name must be between 2 and 100 characters"
  }
}
```

### Get All Users
```bash
GET /day3/users
```

### Get User by ID
```bash
GET /day3/users/1
```

**Not Found (404):**
```json
{
  "type": "about:blank",
  "title": "User Not Found",
  "status": 404,
  "detail": "User with id 999 not found",
  "instance": "/day3/users/999",
  "timestamp": "2025-11-04T10:30:00Z"
}
```

### Search by Email
```bash
GET /day3/users/search?email=john@example.com
```

### Update User
```bash
PUT /day3/users/1
Content-Type: application/json

{
  "email": "newemail@example.com",
  "displayName": "John Updated"
}
```

### Partial Update
```bash
PATCH /day3/users/1
Content-Type: application/json

{
  "displayName": "New Name Only"
}
```

### Delete User
```bash
DELETE /day3/users/1
```

## 🧪 Testing

Run the tests:
```bash
mvn test
```

The test file demonstrates:
- ✅ Valid data creation
- ❌ Invalid email validation
- ❌ Missing required fields
- ❌ String length validation
- ❌ Duplicate email handling
- ❌ 404 error responses

## 📝 Validation Rules

### CreateUserRequest
- `email`: Required, must be valid email format, max 255 characters
- `displayName`: Required, 2-100 characters

### UpdateUserRequest
- `email`: Optional, must be valid email format if provided, max 255 characters
- `displayName`: Optional, 2-100 characters if provided

## 🎨 Key Design Patterns

1. **DTO Pattern**: Separate request/response objects from entities
2. **Mapper Pattern**: MapStruct for object conversions
3. **Repository Pattern**: Spring Data JPA
4. **Service Layer**: Business logic separation
5. **Exception Handler**: Centralized error handling
6. **Problem+JSON**: Standardized error format (RFC 7807)

## 📚 Additional Resources

- [MapStruct Documentation](https://mapstruct.org/)
- [Bean Validation Specification](https://beanvalidation.org/)
- [RFC 7807 - Problem Details for HTTP APIs](https://datatracker.ietf.org/doc/html/rfc7807)
- [Spring Validation Guide](https://spring.io/guides/gs/validating-form-input/)

## 🔍 What's Next?

Consider adding:
- Pagination and sorting
- Advanced search with specifications
- Audit logging (@CreatedDate, @LastModifiedDate)
- API versioning
- OpenAPI/Swagger documentation
- Rate limiting
- Caching with Redis