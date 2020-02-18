package com.example.gateway.model;

import com.example.gateway.model.response.ProductResponse;
import com.example.gateway.model.response.StoreResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StartPage {

    private ProductResponse productsResponse;
    private StoreResponse storesResponse;

}
