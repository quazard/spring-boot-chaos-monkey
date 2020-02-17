package com.example.stores.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.WebFilter;

@Configuration
public class WebFilterConfig {

    private static final String CONTEXT_PATH = "/v1";

    @Bean
    public WebFilter contextPathWebFilter() {
        return (exchange, chain) -> {
            final ServerHttpRequest request = exchange.getRequest();

            if (request.getURI().getPath().startsWith(CONTEXT_PATH)) {
                return chain.filter(
                    exchange.mutate()
                        .request(
                            request.mutate()
                                .contextPath(CONTEXT_PATH)
                                .build()
                        )
                        .build()
                );
            }

            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        };
    }

}
