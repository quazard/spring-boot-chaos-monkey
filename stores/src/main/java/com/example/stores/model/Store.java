package com.example.stores.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "stores")
public class Store {

    @Id
    private String id;
    private String name;
    private String location;

}
