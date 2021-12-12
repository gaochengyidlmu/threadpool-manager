package com.gcy.baiji.client.holder;

/**
 * 数据持有者
 * 1. 插入数据
 * 2. 获取数据
 */
public interface Holder<K, V> {

  void put(K key, V value);

  V get(K key);
}
