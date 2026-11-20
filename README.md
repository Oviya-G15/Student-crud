# Student CRUD - Spring Boot

Java 17 + Spring Boot 3 + Spring Data JPA + H2 (in-memory).

## Run
Requirements: JDK 17+, Maven 3.6+ (or import into IntelliJ / Eclipse / VS Code)

    mvn spring-boot:run

Then open:
- UI:         http://localhost:8080
- API:        http://localhost:8080/api/students
- H2 console: http://localhost:8080/h2-console  (JDBC URL: jdbc:h2:mem:studentdb, user: sa, no password)

## Endpoints
| Method | URL                   | Description    |
|--------|-----------------------|----------------|
| POST   | /api/students         | Create student |
| GET    | /api/students         | List all       |
| GET    | /api/students/{id}    | Get one        |
| PUT    | /api/students/{id}    | Update         |
| DELETE | /api/students/{id}    | Delete         |

## Sample
    curl -X POST localhost:8080/api/students -H "Content-Type: application/json" \
      -d '{"name":"Arun","email":"arun@gmail.com","course":"Java"}'

## Switch to MySQL
Add mysql-connector-j dependency and change application.properties:

    spring.datasource.url=jdbc:mysql://localhost:3306/studentdb
    spring.datasource.username=root
    spring.datasource.password=yourpassword
    spring.jpa.hibernate.ddl-auto=update
