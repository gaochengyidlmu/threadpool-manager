package com.gcy.baiji.tools.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultCache<K, V> extends AbstractCache<K, V> {

  private final Map<K, V> store;
  protected final int defaultCapacity = 16;

  public DefaultCache() {
    store = new ConcurrentHashMap<>(16);
  }

  public DefaultCache(int initialCapacity) {
    store = new ConcurrentHashMap<>(initialCapacity);
  }

  protected Map<K, V> getStore() {
    return store;
  }
}
