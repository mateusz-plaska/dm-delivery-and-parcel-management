package org.delivery.inMemory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface InMemoryRepository<V extends Serializable> {
    void add(String key1, String key2, V value);

    V get(String key1, String key2);

    Set<String> keys();

    Set<Map.Entry<String, V>> entries();

    Collection<V> values();
}
