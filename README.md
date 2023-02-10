# java-spring-boot-ms

Java Spring Boot app with microservice architecture with 3 different services, Discovery Server and API Gateway.


## Features

- Lombok for reduced code;
- Internal service communication (order-service and inventory-service) with WebClient;
- Different Database integration: product-service uses MongoDB and the rest use MySQL;
- Service Discovery Pattern for all services with Eureka;
- Load Balancing and API Gateway (entry point) with Spring Cloud Gateway;
- Actuator available for order-service;
- Circuit Breaker Pattern implemented with Resilience4j;