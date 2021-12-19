package com.gcy.baiji.tools.cache;

import java.util.LinkedList;

/**
 * LRU 在 java 中可以通过 LinkedHashMap 实现，不过这里就直接采用最暴力的方式，额外记录一个 link list，
 * 将使用的节点刷新到头部。当 store 中的元素个数超过了 maxSize 时，剔除掉 list 中最后的节点。
 *
 * @param <K>
 * @param <V>
 */
public class LruEvictableCache<K, V> extends DefaultEvictableCache<K, V> implements
    LruCache<K, V> {

  private final LinkedList<K> lruStore = new LinkedList<>();
  private int maxSize = 256;


  public LruEvictableCache() {
    super();
  }

  public LruEvictableCache(int maxSize) {
    super();
    this.maxSize = maxSize;
  }

  public LruEvictableCache(int maxSize, int initialCapacity) {
    super(initialCapacity);
    this.maxSize = maxSize;
  }

  @Override
  public void put(K key, V value) {
    refresh(key);
    super.put(key, value);
    if (needRemoveOldestElement()) {
      removeOldestElement();
    }
  }

  @Override
  public void put(K key, V value, long milliseconds) {
    refresh(key);
    super.put(key, value, milliseconds);
    if (needRemoveOldestElement()) {
      removeOldestElement();
    }
  }

  @Override
  public V get(K key) {
    refresh(key);
    return super.get(key);
  }

  // remove 的逻辑是从头向尾进行遍历，所以高频值放在头部比较合适
  private void refresh(K key) {
    lruStore.remove(key);
    lruStore.addFirst(key);
  }

  protected boolean needRemoveOldestElement() {
    return getStore().size() >= maxSize;
  }

  private void removeOldestElement() {
    K k = lruStore.removeLast();
    getStore().remove(k);
  }

  public int getMaxSize() {
    return maxSize;
  }

  public void setMaxSize(int maxSize) {
    this.maxSize = maxSize;
  }
}
