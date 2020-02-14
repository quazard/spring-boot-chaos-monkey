package com.example.products.controller;

import com.example.products.model.Product;
import com.example.products.repository.ProductRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(
        final ProductRepository productRepository
    ) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public Flux<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

}
