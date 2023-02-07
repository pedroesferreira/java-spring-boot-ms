package com.pedroesferreira.productservice.repository;

import com.pedroesferreira.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
