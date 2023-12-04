package ru.otus.cache.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.cache.configuration.CacheProperties;
import ru.otus.cache.domain.Cache;
import ru.otus.cache.domain.LRUCache;
import ru.otus.cache.domain.LRUCacheImpl;
import ru.otus.cache.domain.PersistableCache;

import java.lang.ref.SoftReference;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CacheRepositoryTest {

    PersistableCache<String,Integer> cache;

    CacheProperties cacheProperties = new CacheProperties();

    CacheRepository<String,Integer> repository = new CacheRepository<>(new ObjectMapper());

    @BeforeEach
    void init(){
        cache = new LRUCacheImpl<>(cacheProperties,repository);
        cache.put("1",10);
        cache.put("2",20);
        cache.put("3",30);
        Integer integer = cache.get("1");
        integer = null;
        System.out.println("TEST CACHE INITIALIZED");

    }

    @Test
    void save() {
        cache.persist();
    }

    @Test
    void get() {
        Map<String, SoftReference<Integer>> stringSoftReferenceMap = repository.get();
        System.out.println(stringSoftReferenceMap);
    }
}