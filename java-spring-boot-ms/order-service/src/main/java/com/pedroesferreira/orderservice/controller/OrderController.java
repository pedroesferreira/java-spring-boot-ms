package com.pedroesferreira.orderservice.controller;

import com.pedroesferreira.orderservice.dto.OrderRequest;
import com.pedroesferreira.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //"inventory" name is the key after the "instances" key in the application.properties resilience4j.circuitbreaker
    @CircuitBreaker(name="inventory", fallbackMethod = "fallbackMethod")
    //"inventory" name is the key after the "instances" key in the application.properties resilience4j.timeout
    @TimeLimiter(name="inventory")
    //"inventory" name is the key after the "instances" key in the application.properties resilience4j.retry
    @Retry(name="inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
    }

    // fallback method has to have same return type as the original method that has the circuit breaker
    public CompletableFuture<String> fallbackMethod (OrderRequest orderRequest, RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(() -> "Something went wrong!");
    }
}
