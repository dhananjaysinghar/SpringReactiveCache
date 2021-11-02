package com.reactive.cache.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TokenGenerator {

    @Autowired
    private TokenService tokenService;

    public Mono<String> getTokens() {
        return tokenService.getToken();
    }
}
