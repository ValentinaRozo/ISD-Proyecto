package com.calderon_garcia_rozo.modelo;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;



public final class Dht {
    public static ConcurrentHashMap<String, ArrayList<Integer> > dht = new ConcurrentHashMap<String, ArrayList<Integer>>();

    
    public static  ArrayList<Integer> gettest(String key) {
        return dht.get(key);       
    }

    public static int gety() {
        return dht.size();       
    }
}
