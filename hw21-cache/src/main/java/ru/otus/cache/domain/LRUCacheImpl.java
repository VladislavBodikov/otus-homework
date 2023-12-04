package ru.otus.cache.domain;

import org.springframework.stereotype.Service;
import ru.otus.cache.configuration.CacheProperties;
import ru.otus.cache.repository.CacheRepository;

public class LRUCacheImpl<K,V> extends LRUCache<K,V>{

    public LRUCacheImpl(CacheProperties properties, CacheRepository<K, V> cacheRepository) {
        super(properties, cacheRepository);
    }

}
