package com.pedroesferreira.orderservice.repository;

import com.pedroesferreira.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
