package com.example.stores.controller;

import com.example.stores.model.Store;
import com.example.stores.repository.StoreRepository;
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
@RequestMapping("/stores")
public class StoreController {

    private StoreRepository storeRepository;

    public StoreController(
        final StoreRepository storeRepository
    ) {
        this.storeRepository = storeRepository;
    }

    @GetMapping
    public Mono<Map<String, Collection<Store>>> getAllStores() {
        return this.storeRepository
            .findAll()
            .collectList()
            .map(
                l -> ImmutableMap.of("stores", l)
            );
    }

    @PostMapping
    public Mono<Store> createStore(
        @RequestBody final Store store
    ) {
        return this.storeRepository.save(store);
    }

}
