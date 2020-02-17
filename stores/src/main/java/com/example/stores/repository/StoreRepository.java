package com.example.stores.repository;

import com.example.stores.model.Store;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends ReactiveCrudRepository<Store, String> {
}
