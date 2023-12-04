package ru.otus.cache.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ru.otus.cache.configuration.CacheProperties;
import ru.otus.cache.repository.CacheRepository;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Цель работы - реализовать Least Recently Used кеш, которые сам восстанавливается после сбоя.
 * Все необходимые параметры (такие как, например, интервал потери данных, максимальный размер кеша и тд должны быть заданы в конфигурационном файле).
 * Например время потери данных 30сек, размер кеша - 1000 значений.
 * Реализовать можно, например, на основе Spring приложения (с предоставлением REST API), завёрнутого в Docker контейнер.
 */

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public abstract class LRUCache<K, V> implements PersistableCache<K, V> {

    int capacity;

    @Getter
    Map<K, SoftReference<V>> cache;

    Map<K, Lock> locks = new ConcurrentHashMap<>();

    CacheProperties properties;
    CacheRepository<K, V> repository;


    public LRUCache(CacheProperties properties, CacheRepository<K, V> cacheRepository) {
        this.capacity = properties.getCapacity();
        this.cache = new LinkedHashMap<>(capacity);
        cache.putAll(cacheRepository.get());
        this.properties = properties;
        this.repository = cacheRepository;
    }

    @Override
    public Set<K> getKeySet() {
        return cache.keySet();
    }


    public boolean exists(K key) {
        return cache.containsKey(key);
    }

    @Override
    public V get(K key) {
        Lock lock = getOrCreateLock(key);
        try {
            if (lock.tryLock(30, TimeUnit.SECONDS)) {
                try {
                    return getLocked(key);
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("");
    }

    private V getLocked(K key) {
        Objects.requireNonNull(key);
        SoftReference<V> refValue = cache.remove(key);
        V value = refValue == null ? null : refValue.get();

        if (value == null) {
            throw new NoSuchElementException();
        }

        cache.put(key, refValue);
        return value;
    }


    @Override
    public void put(K key, V value) {
        Lock lock = getOrCreateLock(key);
        try {
            if (lock.tryLock(30, TimeUnit.SECONDS)) {
                try {
                    putLocked(key, value);
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void putLocked(K key, V value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        if (exists(key)) {
            cache.put(key, new SoftReference<>(value));
        } else {
            removeFirstElementIfCapacityIsFull();
            cache.put(key, new SoftReference<>(value));
        }
    }

    private void removeFirstElementIfCapacityIsFull() {
        if (cache.size() == capacity) {
            K keyForRemove = cache.keySet().iterator().next();
            cache.remove(keyForRemove);
            locks.remove(keyForRemove);
        }
    }

    private Lock getOrCreateLock(K key) {
        synchronized (key) {
            Lock lock = locks.get(key);
            if (lock == null) {
                lock = new ReentrantLock();
                locks.put(key, lock);
            }
            return lock;
        }
    }

    @Override
    public void invalidate(K key) {
        cache.remove(key);
        locks.remove(key);
    }

    @Override
    public void persist() {
        repository.save(cache);
    }

}
