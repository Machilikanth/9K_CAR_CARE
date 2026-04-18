# 9K CAR CARE

A Spring Boot application for car care services.

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL

## Dependencies

- Spring Boot Web
- Spring Data JPA
- MySQL Connector/J
- Lombok
- Spring Security
- SpringDoc OpenAPI

## Running the Application

To run the application, use the Maven wrapper:

```bash
./mvnw spring-boot:run
```

The application will start on port 8080.

## Configuration

Update `src/main/resources/application.properties` for database configuration.

Example:
```
spring.datasource.url=jdbc:mysql://localhost:3306/carcare
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```

## APIs

### User API
- GET /api/cars/{carNumber} - Get car details by car number

### Admin APIs (requires authentication: admin/password)
- POST /api/admin/cars - Create new car
- POST /api/admin/cars/{carId}/services - Add services to car
- PUT /api/admin/cars/{carId}/status - Update car status
- GET /api/admin/cars - Get all cars

## Swagger UI
Access API documentation at http://localhost:8080/swagger-ui.html

## Building

```bash
./mvnw clean compile
```

## Configuration

Update `src/main/resources/application.properties` for database configuration.

Example:
```
spring.datasource.url=jdbc:mysql://localhost:3306/carcare
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
```