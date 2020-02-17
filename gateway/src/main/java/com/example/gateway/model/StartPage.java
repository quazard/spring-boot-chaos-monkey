package com.example.gateway.model;

import com.example.gateway.model.response.ProductResponse;
import com.example.gateway.model.response.StoreResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StartPage {

    private ProductResponse productsResponse;
    private StoreResponse storesResponse;

}
