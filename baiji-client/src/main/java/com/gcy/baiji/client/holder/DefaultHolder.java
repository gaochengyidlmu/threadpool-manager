package com.gcy.baiji.client.holder;

import java.util.HashMap;
import java.util.Map;

public class DefaultHolder<K, V> implements Holder<K, V> {

  private final Map<K, V> monitorMap = new HashMap<K, V>();

  public void put(K key, V value) {
    monitorMap.put(key, value);
  }

  public V get(K key) {
    return monitorMap.get(key);
  }
}
