package ru.otus.cache.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.cache.domain.LRUCacheImpl;
import ru.otus.cache.domain.PersistableCache;
import ru.otus.cache.repository.CacheRepository;

@Configuration
public class CacheConfiguration {

    @Bean
    CacheRepository<String,Object> cacheRepository(ObjectMapper objectMapper){
        return new CacheRepository<>(objectMapper);
    }

    @Bean
    PersistableCache<String,Object> cache(CacheProperties properties, CacheRepository<String, Object> cacheRepository){
        return new LRUCacheImpl<>(properties,cacheRepository);
    }
}
