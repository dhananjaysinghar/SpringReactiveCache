package com.reactive.cache.controller;

import com.reactive.cache.service.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TokenController {

    @Autowired
    private TokenGenerator tokenGenerator;

    public Mono<ServerResponse> getToken(ServerRequest serverRequest) {
        return ServerResponse.ok().body(tokenGenerator.getTokens(), String.class);
    }

}
