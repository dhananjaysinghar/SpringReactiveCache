package com.reactive.cache.utils;

import reactor.core.publisher.Mono;

import java.util.function.Supplier;

public interface CacheService<K, V> {
    Mono<V> writeBack(K cacheKey, Supplier<Mono<V>> supplier);

    void initializeCache(String cacheName);

    void invalidateKey(K cachekey);
}
