package com.pingme.ping.components;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** The Tests. */
class CacheTest {

  private Cache<String, Object> cache;

  @BeforeEach
  void setUp() {
    cache = new Cache<>();
  }

  @Test
  void maxSizeTest() {
    int size = Cache.MAX_SIZE;
    cache.clear();
    for (int i = 0; i < size; i++) {
      cache.put(Integer.toString(i), i);
    }

    assertEquals(1, cache.get("1"));
    assertEquals(Cache.MAX_SIZE, cache.size());

    cache.put("11", 11);

    assertEquals(11, cache.get("11"));
    assertEquals(Cache.MAX_SIZE, cache.size());
  }

  @Test
  void clearTest() {
    int size = Cache.MAX_SIZE * 2;
    for (int i = 0; i < size; i++) {
      cache.put(Integer.toString(i), i);
    }

    cache.clear();

    assertEquals(0, cache.size());
  }
}
