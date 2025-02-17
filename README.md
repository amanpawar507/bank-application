# Bank API - A Simple Financial Institution Backend

## Objective:
This project is an API for a fake financial institution. The goal is to build a backend system that enables basic banking operations. This API is designed for bank employees and can be used by multiple frontends.

## Tech Stack
Language: Java 21
Framework: Spring Boot
Database: MySQL
Build Tool: Maven

## API routes allow:
1. Create a new bank account for a customer, with an initial deposit amount. A single customer may have multiple bank accounts.
2. Transfer amounts between any two accounts, including those owned by different customers.
3. Retrieve balances for a given account.
4. Retrieve transfer history for a given account.

## Setup & Installation:
1. Clone the repository:
```bash
git clone https://github.com/yourusername/bank-api.git
```

2. Set up MySQL database
CREATE DATABASE bank_db;

3. Configure application properties (src/main/resources/application.properties)
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/bank_db
spring.datasource.username=username
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```

5. Build and run the project
```bash
mvn clean install
mvn spring-boot:run
```
## API Endpoints
1. /bankapp/create-bank -> Create a new bank
2. /bankapp/create-customer -> Create a new customer
3. /bankapp/bank-accounts/create-account -> Create a new bank account
4. /bankapp/bank-accounts/balance/{bankAccountId} -> Get balance details for a specific bank account
5. /bankapp/bank-accounts/transfer -> Transfer funds from one bank account to another
6. /bankapp/bank-accounts/transfer-history/{bankAccountId} -> Get all transfer history for a specific bank account

