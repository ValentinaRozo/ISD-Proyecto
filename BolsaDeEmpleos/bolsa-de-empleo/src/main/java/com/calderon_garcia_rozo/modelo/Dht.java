package com.calderon_garcia_rozo.modelo;
import java.util.concurrent.ConcurrentHashMap;




public class Dht {
    public static ConcurrentHashMap<String, Object> dht = new ConcurrentHashMap<String, Object>();

    public static Object gettest(String key) {
        return dht.get(key);       
    }

    public static int gety() {
        return dht.size();       
    }
}
