# Authentication & Authorization with Spring Security
This project demonstrates the implementation of authentication and authorization mechanisms using Spring Security and JWT (JSON Web Token). The application provides secure access to APIs based on user roles (admin or user), ensuring that only authorized users can access certain endpoints. Additionally, it handles error responses with appropriate error models.

## Features

- **JWT Authentication**: Secure authentication using JSON Web Tokens.
- **Role-Based Authorization**: Access control based on user roles (admin, user).
- **Error Handling**: Proper error models returned for different error scenarios.
- **User Management**: APIs for user registration and login.
- **Admin APIs**: Special endpoints accessible only to admin users.

## Technologies Used

- **Spring Boot**: Framework for building the backend application.
- **Spring Security**: For authentication and authorization.
- **JWT**: For token-based authentication.
- **Maven**: Build and dependency management tool.
- **PostgreSQL**: Database for development and testing.

## Getting Started

### Prerequisites

- Java 21
- Maven 3.6 or higher

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/erayayaz/spring-security.git
    cd spring-security
    ```
   
2. Build the project:
    ```sh
    mvn clean install
    ```
3. Run the docker (if you do not have docker desktop please install and pull postgres):
    ```sh
    docker-compose up -d
    ```

4. Run the application:
    ```sh
    mvn spring-boot:run
    ```

### API Endpoints

#### Authentication

- **POST /api/auth/register**: Register a new user.
- **POST /api/auth/login**: Authenticate a user and return a JWT token.

#### User APIs

- **GET /api/auth/test**: Get the profile of the authenticated user.

#### Admin APIs

- **GET /api/auth/admin**: Get a list of all users (accessible only to admin).

### Error Handling

The application returns appropriate error responses with detailed messages in case of various error scenarios, such as authentication failure, access denied, etc.

## Usage

Once the application is running, you can use tools like Insomnia, Postman or cURL to interact with the APIs. Make sure to include the JWT token in the `Authorization` header for endpoints that require authentication.

## Contributing

Contributions are welcome! Please fork the repository and create a pull request with your changes.