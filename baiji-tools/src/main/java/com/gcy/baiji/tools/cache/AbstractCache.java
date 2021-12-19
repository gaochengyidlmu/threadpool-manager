package com.gcy.baiji.tools.cache;

import java.util.Map;

public abstract class AbstractCache<K, V> implements Cache<K, V> {

  protected abstract Map<K, V> getStore();

  @Override
  public void put(K key, V value) {
    getStore().put(key, value);
  }

  @Override
  public V get(K key) {
    V value = getStore().get(key);
    if (value != null) {
      System.out.println("cache hit [" + key + "], value is " + value.toString());
    }
    return value;
  }

  @Override
  public V remove(K key) {
    return getStore().remove(key);
  }
}
