package com.pedroesferreira.productservice.service;

import com.pedroesferreira.productservice.dto.ProductRequest;
import com.pedroesferreira.productservice.dto.ProductResponse;
import com.pedroesferreira.productservice.model.Product;
import com.pedroesferreira.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product "+product.getId()+" saved to DB");
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();

        return allProducts.stream().map(this::mapProductToProductResponse).toList();
    }

    private ProductResponse mapProductToProductResponse(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
