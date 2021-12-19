package com.gcy.baiji.tools.cache;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class LruEvictableCacheTests {

  @Test
  public void testPut() {
    LruEvictableCache<String, Integer> cache = new LruEvictableCache<>(8);

    for (int i = 0; i < 10; i++) {
      cache.put(String.valueOf(i), i);
    }
    Assert.assertNull(cache.get("0"));

    cache.get("2");
    cache.put("10", 10);
    Assert.assertNull(cache.get("3"));
  }

}
