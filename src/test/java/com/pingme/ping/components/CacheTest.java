package com.pingme.ping.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** The Tests. */
public class CacheTest {

  private Cache<String, Object> cache;
  public static final int MAX_CACHE_SIZE = 10;

  @BeforeEach
  void setUp() {
    cache = new Cache<>();
  }

  @Test
  void maxSizeTest() {
    cache.clear();
    for (int i = 0; i < MAX_CACHE_SIZE; i++) {
      cache.put(Integer.toString(i), i);
    }

    assertEquals(1, cache.get("1"));
    assertEquals(MAX_CACHE_SIZE, cache.size());

    cache.put("11", 11);

    assertEquals(11, cache.get("11"));
    assertEquals(MAX_CACHE_SIZE, cache.size());
  }

  @Test
  void clearTest() {
    int size = MAX_CACHE_SIZE * 2;
    for (int i = 0; i < size; i++) {
      cache.put(Integer.toString(i), i);
    }

    cache.clear();

    assertEquals(0, cache.size());
  }

  @Test
  void testRemoveKey() {
    cache.put("key1", "value1");
    cache.put("key2", "value2");
    cache.remove("key1");
    assertNull(cache.get("key1"));
  }
}
