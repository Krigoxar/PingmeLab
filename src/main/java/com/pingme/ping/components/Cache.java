package com.pingme.ping.components;

import java.util.*;

import org.springframework.stereotype.Component;

@Component
public class Cache<T1, T2> {
    private Map<T1, T2> map = new HashMap<T1, T2>();
    private Random rand = new Random();
    public int size = 10;

    public boolean containsKey(T1 param) {
        return map.containsKey(param);
    }

    public Object remove(T1 param) {
        return map.remove(param);
    }

    public Object put(T1 param1, T2 param2) {
        if (map.size() > size) {
            var obj = map.keySet().toArray();
            int i = rand.nextInt() % size;
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
