package com.pingme.ping.components;

import java.util.HashMap;
import java.util.Map;

/** Thew MultitonCounter class. */
public final class MultitonCounter {

  private static Map<String, MultitonCounter> counters = new HashMap<>();

  private volatile Long count;

  private MultitonCounter() {
    count = 0L;
  }

  public static synchronized MultitonCounter getInstance(String name) {
    return counters.computeIfAbsent(name, sus -> new MultitonCounter());
  }

  public synchronized void count() {
    count++;
  }

  public synchronized Long getCount() {
    return count;
  }
}
