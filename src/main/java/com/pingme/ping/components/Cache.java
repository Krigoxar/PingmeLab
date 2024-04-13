package com.pingme.ping.components;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * The `Cache` class is a generic class in Java that implements a simple cache with key-value pairs
 * and a fixed size, supporting operations like `containsKey`, `remove`, `put`, `clear`, and `get`.
 */
@Component
public class Cache<T1, T2> {
  private Map<T1, T2> map = new HashMap<>();
  private SecureRandom rand = new SecureRandom();
  public static final int SIZE = 10;

  public boolean containsKey(T1 param) {
    return map.containsKey(param);
  }

  public Object remove(T1 param) {
    return map.remove(param);
  }

  /**
   * This Java function puts a key-value pair into a map, removing a random entry if the map size
   * exceeds a specified limit.
   *
   * @param param1 It looks like the code snippet you provided is a method that puts key-value pairs
   *     into a map with a size constraint. The method first checks if the map size exceeds a
   *     certain limit (`SIZE`). If it does, it removes a random key from the map before putting the
   *     new key-value pair.
   * @param param2 param2 is the value associated with the key param1 in the map.
   * @return The method is returning the result of the `map.put(param1, param2)` call.
   */
  public Object put(T1 param1, T2 param2) {
    if (map.size() > SIZE) {
      var obj = map.keySet().toArray();
      int i = rand.nextInt() % SIZE;
      map.remove(obj[i]);
      return map.put(param1, param2);
    }

    return map.put(param1, param2);
  }

  public void clear() {
    map.clear();
  }

  public T2 get(T1 param) {
    return map.get(param);
  }
}
