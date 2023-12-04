package ru.otus.cache.domain;

import java.util.Set;

public interface PersistableCache<K, V> extends Cache<K,V>{

    void persist();
}
