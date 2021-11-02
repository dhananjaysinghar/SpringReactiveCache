package com.reactive.cache.utils;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Service;
import reactor.cache.CacheMono;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@Service
public class CaffeineCacheServiceImpl<K, V> implements CacheService<K, V> {

    private Cache<K, V> cache;
    private final CacheManager cacheManager;

    public CaffeineCacheServiceImpl(CaffeineCacheManager caffeineCacheManager) {
        this.cacheManager = caffeineCacheManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initializeCache(String cacheName) {
        this.cache = (Cache<K, V>)
                Objects.requireNonNull(this.cacheManager.getCache(cacheName))
                        .getNativeCache();
    }

    @Override
    public void invalidateKey(K cacheKey) {
        this.cache.invalidate(cacheKey);
    }

    @Override
    public Mono<V> writeBack(K cacheKey, Supplier<Mono<V>> supplier) {
        return getCacheMono(cacheKey, supplier);
    }

    private Mono<V> getCacheMono(K cacheKey, Supplier<Mono<V>> supplier) {
        return CacheMono
                .lookup(key -> Mono.defer(() -> {
                    V ifPresent = cache.getIfPresent(cacheKey);
                    log.info("Cache hit: {} for key: {}", ifPresent != null, cacheKey);
                    return Mono.justOrEmpty(ifPresent).map(Signal::next);
                }), cacheKey)
                .onCacheMissResume(Mono.defer(() -> {
                    log.info("Cache miss for key: {}", cacheKey);
                    return supplier.get();
                }))
                .andWriteWith((key, signal) -> Mono.fromRunnable(() -> {
                            log.info("Cache put for key: {}", key);
                            Optional.ofNullable(signal.get())
                                    .ifPresent(value -> cache.put(key, value));
                        }
                ));
    }
}
