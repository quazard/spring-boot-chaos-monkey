package com.example.gateway.service;

import com.example.gateway.config.properties.StoreProperties;
import com.example.gateway.model.response.ResponseType;
import com.example.gateway.model.response.StoreResponse;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StoreService {

    private final WebClient webClient;
    private final CircuitBreaker circuitBreaker;

    public StoreService(
        final StoreProperties storeProperties,
        final CircuitBreakerRegistry circuitBreakerRegistry
    ) {
        this.webClient = WebClient.create(storeProperties.getHost());
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("stores");
    }


    public Mono<StoreResponse> getAllStores() {
        return this.webClient.get()
            .uri("/v1/stores")
            .retrieve()
            .bodyToMono(StoreResponse.class)
            .map(
                sr -> {
                    sr.setResponseType(ResponseType.REMOTE_SERVICE);
                    return sr;
                }
            )
            .transform(CircuitBreakerOperator.of(circuitBreaker))
            .onErrorResume(
                ex -> Mono.just(
                    new StoreResponse(ResponseType.FALLBACK)
                )
            );
    }

}
