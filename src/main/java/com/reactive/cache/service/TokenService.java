package com.reactive.cache.service;

import com.reactive.cache.utils.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class TokenService {

    @Autowired
    private CacheService<String, String> caffeineCache;

    @Value("${spring.cache.cache-names}")
    private String cacheName;

    @PostConstruct
    private void postConstruct(){
        caffeineCache.initializeCache(cacheName);
    }

    public Mono<String> getToken() {
        return caffeineCache.writeBack(cacheName, this::generateToken)
                .doOnSuccess(e-> log.info("get token"));
    }

    public Mono<String> generateToken() {
        return Mono.just(UUID.randomUUID() + " : " + LocalDateTime.now().toString());
    }
}
