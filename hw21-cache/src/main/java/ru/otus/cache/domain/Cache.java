package ru.otus.cache.domain;

import java.util.Set;

public interface Cache <K, V> {

    V get(K key);

    void put(K key, V value);

    void invalidate(K key);

    Set<K> getKeySet();
}
