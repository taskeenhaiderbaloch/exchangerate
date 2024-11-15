Here’s the consolidated **README.md** combining the original project details with the new additions about OAuth 2.0, Keycloak integration, and additional configuration updates:

---

# Bill Calculation Management

## Project Overview
The Bill Calculation Management API is a Spring Boot application that integrates with a third-party currency exchange API to retrieve real-time exchange rates. The application calculates the total payable amount for a bill in a specified currency after applying applicable discounts. This API allows users to submit a bill in one currency and get the payable amount in another currency.

It includes secure access using **OAuth 2.0** authentication powered by **Keycloak 21**.

---

## Features

- **Currency Exchange Integration**: Retrieves real-time exchange rates via a third-party API.
- **Discount Calculation**: Applies discounts based on user type, customer tenure, and business rules:
    - Employee Discount: `30%` discount for `employees`.
    - Affiliate Discount: `10%` discount for `affiliates`.
    - Loyalty Discount: `5%` discount for customers with `over 2 years` of tenure.
    - Bill Discount: `$5` off for every `$100` spent.
    - Exclusion: Percentage discounts do not apply to grocery items.
    - One Discount Rule: A user can only get one percentage discount on a bill.
- **Currency Conversion**: Converts bill totals from one currency to another.
- **Secure Access**: OAuth 2.0 authentication using Keycloak with role-based access control (RBAC).
- **Swagger API Documentation**: Available via OpenAPI for all endpoints.

---

## Requirements

1. **Third-Party API Integration**
    - Integrated with ExchangeRate-API for real-time exchange rates using the API key.
    - Example endpoint:
      ```plaintext  
      https://v6.exchangerate-api.com/v6/${exchange.rate.api.key}/latest/{base_currency}  
      ```  

2. **Authentication**
    - OAuth 2.0 authentication is required to access API endpoints, managed via Keycloak.
    - Bearer tokens are generated for secure interactions.

3. **Keycloak Integration**
    - Keycloak 21 is used for user authentication, role assignment, and access management.

---

## API Endpoints

### 1. **/calculate**
**POST** `/v1/{locale}/private/api/calculate`
- Accepts bill details (items, categories, user type, customer tenure, original currency, target currency).
- Returns the net payable amount in the target currency.

**Request Example**:
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

**Response Example**:
```json  
{  
  "message": "The payable amount is 180.00 EUR"  
}  
```  

### Response Codes

- **200 OK**: Successful calculation of the bill.
- **400 Bad Request**: Invalid input or request.
- **401 Unauthorized**: Authentication required.
- **403 Forbidden**: Permission issue.
- **500 Internal Server Error**: Server issue.

---

## Keycloak 21 Setup

1. **Install Keycloak**:
    - Download Keycloak 21 from [Keycloak's website](https://www.keycloak.org/downloads).
    - Start the server in development mode:
      ```bash  
      ./bin/kc.sh start-dev  
      ```  

2. **Create a Realm**:
    - Open Keycloak Admin Console (`http://localhost:8080`).
    - Create a realm, e.g., `bill-management`.

3. **Configure a Client**:
    - Create a client with:
        - Client ID: `bill-calculation-api`
        - Protocol: `openid-connect`
        - Access Type: `confidential`
    - Save the client and add:
        - Redirect URI: `http://localhost:8080/*`
        - Note the **Client Secret**.

4. **Define Roles**:
    - Create roles like `USER` and `ADMIN`.
    - Assign roles to users.

---

## Configuration

Update `application.yml` for Keycloak and OAuth 2.0:

```yaml  
spring:  
  security:  
    oauth2:  
      resourceserver:  
        jwt:  
          issuer-uri: http://localhost:8080/realms/bill-management  
  keycloak:  
    realm: bill-management  
    auth-server-url: http://localhost:8080  
    client-id: bill-calculation-api  
    client-secret: <YOUR_CLIENT_SECRET>  
```  

---

## Project Setup

### Prerequisites
- Java 11 or higher
- Maven
- Keycloak 21
- API Key for ExchangeRate-API

### Build and Run

1. **Clone the Repository**:
   ```bash  
   git clone <repository-url>  
   cd <project-folder>  
   ```  

2. **Build the Project**:
   ```bash  
   mvn clean install  
   ```  

3. **Run the Application**:
   ```bash  
   mvn spring-boot:run  
   ```  

4. Access the API at `http://localhost:8080`.

---

## Testing

1. **Unit Testing**:
    - Mock external API calls using `@MockBean` and Mockito.
    - Use `@SpringBootTest` for integration tests.

2. **Authentication Testing**:
    - Generate a Keycloak access token for test users.
    - Test APIs using Bearer tokens in tools like Postman or cURL.

---

## Technologies Used

- **Spring Boot**: Framework for building REST APIs.
- **Swagger**: API documentation.
- **Keycloak**: OAuth 2.0 authentication and role management.
- **Feign**: HTTP client for API integration.
- **Maven**: Build tool.
- **JUnit & Mockito**: Unit testing.
- **JaCoCo**: Code coverage reporting.

---

## Contact

For inquiries, contact:  
**Email**: taskeenhaider7@gmail.com

---  