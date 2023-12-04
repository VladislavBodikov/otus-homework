package ru.otus.cache.web;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.otus.cache.domain.Cache;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/cache")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class CacheController {

    Cache<String, Object> cache;

    record CacheElement(String key,Object value){};


    @GetMapping
    @ApiResponse(description = "Получить ключи, которые находятся в кеше")
    Set<String> getCacheKeys(){
        return cache.getKeySet();
    }

    @PostMapping
    @ApiResponse(description = "Отправить элементы в кеш")
    void putToCache(@RequestBody List<CacheElement> elementToCache){
        elementToCache.forEach(e-> cache.put(e.key(),e.value()));
    }



}
