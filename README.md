
# Bill Calculation Management

## Project Overview

The Bill Calculation Management API is a Spring Boot application that integrates with a third-party currency exchange API to retrieve real-time exchange rates. The application calculates the total payable amount for a bill in a specified currency after applying applicable discounts. This API allows users to submit a bill in one currency and get the payable amount in another currency.

## Features

- **Currency Exchange Integration**: Integrates with a currency exchange API to retrieve real-time exchange rates.
- **Discount Calculation**: Applies discounts based on user type, customer tenure, and specific rules (e.g., grocery items, discount for employees, affiliates, etc.).
- **Currency Conversion**: Converts the bill total from the original currency to the target currency using the retrieved exchange rates.
- **API Endpoint**: Exposes an API to calculate the final payable amount in the target currency after applying discounts and conversion.

## Requirements

### Third-Party API Integration

- Integrated with ExchangeRate-API for real-time exchange rates using the API key.
- Example endpoint: `https://v6.exchangerate-api.com/v6/${exchange.rate.api.key}/latest/{base_currency}`

### Discount Logic

- **Employee Discount**: 30% discount for employees.
- **Affiliate Discount**: 10% discount for affiliates.
- **Loyalty Discount**: 5% discount for customers with over 2 years of tenure.
- **Bill Discount**: A $5 discount for every $100 spent on the bill.
- **Exclusion**: Percentage discounts do not apply to grocery items.
- **One Discount Rule**: A user can only get one percentage discount on a bill.

### Authentication

- Authentication is required to access the API endpoints.

### Endpoint

- **POST /v1/{locale}/private/api/calculate**: 
  - Accepts bill details (items, categories, user type, customer tenure, original currency, target currency).
  - Returns the net payable amount in the target currency.

## API Documentation

The API documentation is available using **Swagger UI** and provides details for all available operations.

### `/calculate` Endpoint

**POST /v1/{locale}/private/api/calculate**

#### Request Body Example:
```json
{
  "user": {
    "userType": "employee",
    "customerTenureYears": 3
  },
  "items": [
    {
      "name": "item1",
      "category": "electronics",
      "price": 200
    }
  ],
  "originalCurrency": "USD",
  "targetCurrency": "EUR"
}
```

#### Response Example:
```json
{
  "message": "The payable amount is 180.00 EUR"
}
```

### Response Codes

- `200 OK`: Successful calculation of the bill.
- `400 Bad Request`: Invalid input or request.
- `401 Unauthorized`: Authentication required.
- `403 Forbidden`: Permission issue.
- `500 Internal Server Error`: Server issue.

## Project Setup

### Prerequisites

- Java 11 or higher
- Maven
- API Key for ExchangeRate-API

### Build and Run

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <project-folder>
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. The application will run on `http://localhost:8080`.

### Unit Testing

- Use `@SpringBootTest` to test the application.
- Mock external API calls using `@MockBean` and `Mockito`.

## Technologies Used

- **Spring Boot**: The framework used for building the REST API.
- **Swagger**: For API documentation and testing.
- **Feign**: For making HTTP requests to the third-party currency exchange API.
- **Maven**: For building and managing dependencies.
- **JUnit**: For unit testing.
- **Mockito**: For mocking services in unit tests.

## Code Coverage and Quality

- The project is set up with **JUnit** and **Mockito** for unit testing.
- **JaCoCo** is used for generating code coverage reports.
- Static code analysis tools (e.g., Linting) are configured in the build script.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For any inquiries, please contact:
- Email: taskeenhaider7@gmail.com
