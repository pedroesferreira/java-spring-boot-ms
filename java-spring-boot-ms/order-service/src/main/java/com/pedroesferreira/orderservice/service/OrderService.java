package com.pedroesferreira.orderservice.service;

import com.pedroesferreira.orderservice.dto.InventoryResponse;
import com.pedroesferreira.orderservice.dto.OrderItemsDto;
import com.pedroesferreira.orderservice.dto.OrderRequest;
import com.pedroesferreira.orderservice.model.Order;
import com.pedroesferreira.orderservice.model.OrderItems;
import com.pedroesferreira.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public String placeOrder (OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItems> orderItems = orderRequest.getOrderItemsDtoList().stream().map(this::mapToDto).toList();

        order.setOrderItemsList(orderItems);

        // call inventory service to check if it has stock
        // get List of items in the order and map them into a list of strings
        List<String> skuCodes = order.getOrderItemsList().stream().map(orderItem -> orderItem.getSkuCode()).toList();

        // uriBuilder will build the params as: /api/inventory?skuCode=item1_code&skuCode=item2_code
        // .block() makes the request synchronous
        InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        // iterates each object in array and checks if it's in stock
        Boolean allProductsInStock = Arrays.stream(inventoryResponsesArray).allMatch(inventoryResponse -> inventoryResponse.getIsInStock());

        if (allProductsInStock) {
            orderRepository.save(order);
            return "Order placed!";
        } else {
            throw new IllegalArgumentException("At least 1 product not in stock!");
        }
    }

    private OrderItems mapToDto(OrderItemsDto orderItemsDto) {
        OrderItems orderItems = new OrderItems();
        orderItems.setQuantity(orderItemsDto.getQuantity());
        orderItems.setPrice(orderItemsDto.getPrice());
        orderItems.setSkuCode(orderItemsDto.getSkuCode());

        return orderItems;
    }
}
