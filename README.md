# Itaú Unibanco - Transactions API

Solution for [Itau - Transaction challenge](Challenge.md)

Application that receives transactions and calculates statistics on a configurable time window.
    

## Extras implemented

- **Logging**: SLF4J
- **Configuration**: Window size (`stats.seconds`) configurable via properties.
- **Health Endpoint**: Spring Boot Actuator health checks (`/actuator/health`).
- **API Documentation**: Swagger
- **Docker**: Dockerfile
- **Exceptions**: GlobalExceptionHandler

---

## Requirements

- Java 21
- Maven 3.8+
- Docker

---

## Endpoints

- **POST /transaction**: Receive and store a transaction.
- **DELETE /transaction**: Clear all stored transactions.
- **GET /statistics**: Return statistics (`count`, `sum`, `avg`, `min`, `max`) over the last *N* seconds (configurable, default: 60).

## Postman 
### [Itau-Transaction Postman](Itau-Transaction.postman_collection.json)

## Docker

### Build the image

```bash
docker build -t transactions-api .
```

### Run the container

```bash
docker run -p 8080:8080 transactions-api
```

---