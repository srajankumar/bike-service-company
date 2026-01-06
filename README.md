## Bike Service Company

This is a Spring Boot API designed to manage bike servicing efficiently. It allows customers to register their bikes, track service statuses, and interact securely using JWT authentication.

## Features

- User Authentication (JWT)
- Role-Based Access Control (Admin & Customer)
- Bike Management (CRUD Operations)
- Exception Handling
- Unit Testing (JUnit & Mockito)
- API Documentation with Swagger

## Technology Stacks

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-%236DB33F.svg?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-%236DB33F.svg?style=for-the-badge&logo=spring-security&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-%23F6C915.svg?style=for-the-badge&logo=json-web-tokens&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-%2300758F.svg?style=for-the-badge&logo=mysql&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-%23FF7200.svg?style=for-the-badge&logo=hibernate&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-%239B0E27.svg?style=for-the-badge&logo=hibernate&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-%2325A162.svg?style=for-the-badge&logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/Mockito-%2300A7E0.svg?style=for-the-badge&logo=mockito&logoColor=white)
![MockMvc](https://img.shields.io/badge/MockMvc-%23404D59.svg?style=for-the-badge&logo=spring&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-%2388C100.svg?style=for-the-badge&logo=swagger&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-%23FF6C37.svg?style=for-the-badge&logo=postman&logoColor=white)

## API Endpoints

### Authentication

| Method | Endpoint          | Description                            |
| ------ | ----------------- | -------------------------------------- |
| `POST` | `/api/auth/login` | Logs in a user and generates JWT token |

### Bike Operations

| Method   | Endpoint          | Description              |
| -------- | ----------------- | ------------------------ |
| `POST`   | `/api/bikes/save` | Adds a new bike          |
| `GET`    | `/api/bikes`      | Retrieves all bikes      |
| `GET`    | `/api/bikes/{id}` | Gets bike details by ID  |
| `PUT`    | `/api/bikes/{id}` | Updates an existing bike |
| `DELETE` | `/api/bikes/{id}` | Deletes a bike           |

### Example Request Body for Adding/Updating a Bike:

**Full Payload (for adding a new bike or full update):**

```json
{
  "bikeMake": "Bajaj",
  "modelName": "Pulsar NS200",
  "bikeRegistrationNumber": "UP16CD7890",
  "bikeChassisNumber": "98765432109876543",
  "knownIssues": "Starter motor noise",
  "cost": 160000,
  "givenDate": "2025-07-01T14:00:00",
  "expectedDeliveryDate": "2025-07-12",
  "customer": {
    "customerName": "Alok Kumar",
    "phoneNumber": "9012345678",
    "houseNo": "7C",
    "street": "Civil Lines",
    "landmark": "Near Railway Station",
    "city": "Lucknow",
    "state": "Uttar Pradesh",
    "pin": "226001"
  }
}
```

**Partial Payload (for partial update):**
This API supports partial updates. For example, to update only the `bikeMake`, `bikeRegistrationNumber`, `cost`, and `customerName`:

```json
{
  "bikeMake": "Honda Civic",
  "bikeRegistrationNumber": "KA19MA0000",
  "cost": 150000,
  "customer": {
    "customerName": "Tejas"
  }
}
```

## Prerequisites

- Java 17+
- Maven
- MySQL
- Postman (for testing APIs)

> Note: If you are using Eclipse IDE, you must install the Lombok plugin for the project to compile correctly.

## Database Setup

1. **Create the database:**

   ```sql
   CREATE DATABASE bikeservice;
   ```

2. **Tables:**

   - The following tables will be created automatically by the application:
     - `bikes`
     - `customers`
     - `roles`
     - `users`
     - `users_roles`

3. **Insert roles:**

   ```sql
   INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');
   ```

4. **Insert users:**

   - Use the `PasswordGenerator` class from the `com.cts.util` package (in the backend folder) to generate a hashed password.
   - Insert users with the following columns: `id`, `email`, `name`, `password`, `username`.
   - Example:
     ```sql
     INSERT INTO users (id, email, name, password, username) VALUES
     (1, 'admin@example.com', 'Admin User', '<hashed_password>', 'admin'),
     (2, 'user@example.com', 'Regular User', '<hashed_password>', 'user');
     ```
     Replace `<hashed_password>` with the output from the `PasswordGenerator`.

5. **Map users to roles:**
   - Use the `users_roles` table to assign roles to users.
   - Example:
     ```sql
     -- Assign admin role to admin user
     INSERT INTO users_roles (user_id, roles_id) VALUES (1, 1);
     -- Assign user role to regular user
     INSERT INTO users_roles (user_id, roles_id) VALUES (2, 2);
     ```

## Steps to Run

1.  Clone this repository:
    ```sh
    git clone https://github.com/srajankumar/bike-service-company.git
    cd bike-service-company
    ```
2.  Configure `application.properties`.
    Update the database credentials and security settings.

    > **Note:** If you need to generate a secure random **JWT secret key**, you can quickly do so using Node.js with the following command:

    > ```sh
    > node -e "console.log(require('crypto').randomBytes(32).toString('hex'))"
    > ```

3.  Install dependencies & build:
    ```sh
    mvn clean install
    ```
4.  Run the application:
    ```sh
    mvn spring-boot:run
    ```
5.  Access APIs via Postman or Swagger UI
    ```sh
    http://localhost:8080/swagger-ui/index.html
    ```
