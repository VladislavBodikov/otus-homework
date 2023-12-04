package ru.otus.cache.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.cache.configuration.CacheProperties;
import ru.otus.cache.domain.Cache;
import ru.otus.cache.domain.PersistableCache;
import ru.otus.cache.repository.CacheRepository;

import java.util.concurrent.TimeUnit;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class CacheManager {

        PersistableCache<String,Object> cache;

        @Scheduled(fixedRateString = "#{cacheProperties.dataLostSeconds}",timeUnit = TimeUnit.SECONDS)
        public void persistCache(){
                cache.persist();
        }

}
