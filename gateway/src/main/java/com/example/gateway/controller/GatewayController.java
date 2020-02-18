package com.example.gateway.controller;

import com.example.gateway.model.StartPage;
import com.example.gateway.service.ProductService;
import com.example.gateway.service.StoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GatewayController {

    private final ProductService productService;
    private final StoreService storeService;

    public GatewayController(
        final ProductService productService,
        final StoreService storeService
    ) {
        this.productService = productService;
        this.storeService = storeService;
    }

    @GetMapping("/start-page")
    public Mono<StartPage> getStartPage() {
        return Mono.zip(
            this.productService.getAllProducts(),
            this.storeService.getAllStores()
        )
            .map(
                t -> StartPage.builder()
                    .productsResponse(t.getT1())
                    .storesResponse(t.getT2())
                    .build()
            );
    }

}
