package com.gcy.baiji.tools.cache;

public interface EvictableCache<K, V> extends Cache<K, V> {

  void put(K key, V value, long milliseconds);
}
