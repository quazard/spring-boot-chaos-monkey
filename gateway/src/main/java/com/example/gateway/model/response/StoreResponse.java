package com.example.gateway.model.response;

import com.example.gateway.model.Store;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StoreResponse {

    private ResponseType responseType;
    private List<Store> stores = new ArrayList<>();

    public StoreResponse() {}

    public StoreResponse(ResponseType responseType) {
        this.responseType = responseType;
    }

}
