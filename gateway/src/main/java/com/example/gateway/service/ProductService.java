package com.example.gateway.service;

import com.example.gateway.config.properties.ProductProperties;
import com.example.gateway.model.response.ProductResponse;
import com.example.gateway.model.response.ResponseType;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final WebClient webClient;
    private final CircuitBreaker circuitBreaker;

    public ProductService(
        final ProductProperties productProperties,
        final CircuitBreakerRegistry circuitBreakerRegistry
    ) {
        this.webClient = WebClient.create(productProperties.getHost());
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("products");
    }


    public Mono<ProductResponse> getAllProducts() {
        return this.webClient.get()
            .uri("/v1/products")
            .retrieve()
            .bodyToMono(ProductResponse.class)
            .map(
                pr -> {
                    pr.setResponseType(ResponseType.REMOTE_SERVICE);
                    return pr;
                }
            )
            .transform(CircuitBreakerOperator.of(circuitBreaker))
            .onErrorResume(
                ex -> Mono.just(
                    new ProductResponse(ResponseType.FALLBACK)
                )
            );
    }

}
