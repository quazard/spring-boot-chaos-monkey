package com.example.gateway.controller;

import com.example.gateway.model.StartPage;
import com.example.gateway.model.response.ProductResponse;
import com.example.gateway.model.response.ResponseType;
import com.example.gateway.model.response.StoreResponse;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class GatewayController {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public GatewayController(
        final CircuitBreakerRegistry circuitBreakerRegistry
    ) {
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    @GetMapping("/start-page")
    public Mono<StartPage> getStartPage() {
        return Mono.zip(getProducts(), getStores())
            .map(
                t -> {
                    final StartPage startPage = new StartPage();
                    startPage.setProductsResponse(t.getT1());
                    startPage.setStoresResponse(t.getT2());

                    return startPage;
                }
            );
    }

    private Mono<ProductResponse> getProducts() {
        return WebClient.create("http://localhost:8080").get()
            .uri("/v1/products")
            .retrieve()
            .bodyToMono(ProductResponse.class)
            .map(pr -> {
                pr.setResponseType(ResponseType.REMOTE_SERVICE);
                return pr;
            })
            .transform(CircuitBreakerOperator.of(circuitBreakerRegistry.circuitBreaker("products")))
            .onErrorResume(ex -> Mono.just(new ProductResponse(ResponseType.FALLBACK)));
    }

    private Mono<StoreResponse> getStores() {
        return WebClient.create("http://localhost:8081").get()
            .uri("/v1/stores")
            .retrieve()
            .bodyToMono(StoreResponse.class)
            .map(pr -> {
                pr.setResponseType(ResponseType.REMOTE_SERVICE);
                return pr;
            })
            .transform(CircuitBreakerOperator.of(circuitBreakerRegistry.circuitBreaker("stores")))
            .onErrorResume(ex -> Mono.just(new StoreResponse(ResponseType.FALLBACK)));
    }

}
