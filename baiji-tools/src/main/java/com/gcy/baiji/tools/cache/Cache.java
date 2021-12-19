package com.gcy.baiji.tools.cache;

public interface Cache<K, V> {

  void put(K key, V value);

  V get(K key);

  V remove(K key);

}
