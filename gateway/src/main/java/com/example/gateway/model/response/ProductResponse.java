package com.example.gateway.model.response;

import com.example.gateway.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductResponse {

    private ResponseType responseType;
    private List<Product> products = new ArrayList<>();

    public ProductResponse() {}

    public ProductResponse(ResponseType responseType) {
        this.responseType = responseType;
    }

}
