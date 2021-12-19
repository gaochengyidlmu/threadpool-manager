package com.gcy.baiji.tools.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultEvictableCache<K, V> extends DefaultCache<K, V> implements
    EvictableCache<K, V> {

  private final Map<K, Long> ttlStore;

  public DefaultEvictableCache() {
    super();
    ttlStore = new ConcurrentHashMap<>(defaultCapacity);
  }

  public DefaultEvictableCache(int initialCapacity) {
    super(initialCapacity);
    ttlStore = new ConcurrentHashMap<>(initialCapacity);
  }

  protected Map<K, Long> getTtlStore() {
    return ttlStore;
  }

  @Override
  public void put(K key, V value, long milliseconds) {
    getTtlStore().put(key, System.currentTimeMillis() + milliseconds);
    super.put(key, value);
  }

  @Override
  public V get(K key) {
    Long ttl = getTtlStore().get(key);
    if (ttl == null) {
      return super.get(key);
    }

    long now = System.currentTimeMillis();
    if (ttl < now) {
      getTtlStore().remove(key);
      getStore().remove(key);
      return null;
    }
    return super.get(key);
  }
}
