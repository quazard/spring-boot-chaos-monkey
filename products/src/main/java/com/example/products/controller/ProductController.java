package com.example.products.controller;

import com.example.products.model.Product;
import com.example.products.repository.ProductRepository;
import com.google.common.collect.ImmutableMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;

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
    public Mono<Map<String, Collection<Product>>> getAllProducts() {
        return this.productRepository
            .findAll()
            .collectList()
            .map(
                l -> ImmutableMap.of("products", l)
            );
    }

    @PostMapping
    public Mono<Product> createProduct(
        @RequestBody final Product product
    ) {
        return this.productRepository.save(product);
    }

}
