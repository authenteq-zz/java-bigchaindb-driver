package com.authenteq.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SelfSortingLinkedMap<K,V> extends LinkedHashMap<K,V> {
    private TreeMap<K,V> mapDelegate = new TreeMap<>();

    public V put(K key, V value) {
        return mapDelegate.put(key, value);
    }

    public V get(Object key) {
        return mapDelegate.get(key);
    }

    public V remove(Object key) {
        return mapDelegate.remove(key);
    }

    public int size() {
        return mapDelegate.size();
    }

    public boolean isEmpty() {
        return mapDelegate.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return mapDelegate.containsKey(key);
    }

    public Set<K> keySet() {
        return mapDelegate.keySet();
    }

    public Collection<V> values() {
        return mapDelegate.values();
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return mapDelegate.entrySet();
    }
}

