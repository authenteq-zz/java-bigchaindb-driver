/*
 * (C) Copyright 2017 Authenteq (https://authenteq.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Bohdan Bezpartochnyi <bohdan@authenteq.com>
 */

package com.authenteq.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Since Android org.json implementation uses strictly typed LinkedHashMap internally
 * it's impossible to just substitute TreeMap instead of it. To make it self sorting
 * this little hack should be made.
 * @param <K>
 * @param <V>
 */
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

