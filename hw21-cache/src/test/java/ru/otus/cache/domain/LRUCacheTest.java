package ru.otus.cache.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.cache.configuration.CacheProperties;
import ru.otus.cache.repository.CacheRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;


class LRUCacheTest {

    Cache<String,Integer> cache;
//    LRUCache<String,Counter> cache;
    ExecutorService executorService = Executors.newFixedThreadPool(15);
    CacheProperties cacheProperties = new CacheProperties();
    CacheRepository<String, Integer> cacheRepository = new CacheRepository<>(new ObjectMapper());


    @BeforeEach
    void init(){

        cache = new LRUCacheImpl<>(cacheProperties,cacheRepository);
        cache.put("1",10);
        cache.put("2",20);
        cache.put("3",30);

        System.out.println("TEST CACHE INITIALIZED");

    }

    @Test
    void putKeys(){
        Set<String> keys = cache.getKeySet();
        assertEquals("[1, 2, 3]", keys.toString());

        cache.put("1",10);
        assertEquals("[1, 2, 3]", keys.toString());
    }

    @Test
    void getKeys(){
        Set<String> keys = cache.getKeySet();
        assertEquals("[1, 2, 3]", keys.toString());

        cache.get("1");

        assertEquals("[2, 3, 1]", keys.toString());
    }

    @Test
    void get_multithreadTest() throws InterruptedException {
        String key = "1";
        Integer expectedValue = cache.get(key);
        System.out.println("MULTI-THREAD TASKS BEGIN");
        List<Callable<Boolean>> calculateTasks = new ArrayList<>();
        for (int i = 0; i < 1000; i++){
            calculateTasks.add(addOne(key));
            calculateTasks.add(minusOne(key));
        }

        List<Future<Boolean>> futures = executorService.invokeAll(calculateTasks);
        futures.forEach(f-> {
            try {
                f.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("MULTI-THREAD TASKS END");

        Integer actualValue = cache.get(key);
        Assertions.assertEquals(expectedValue,actualValue);
    }

    private Callable<Boolean> addOne(String key){
        return ()-> {
            cache.put(key,cache.get(key) + 1);
            return true;
        };
    }

    private Callable<Boolean> minusOne(String key){
        return ()-> {
            cache.put(key,cache.get(key) - 1);
            return true;
        };
    }
}