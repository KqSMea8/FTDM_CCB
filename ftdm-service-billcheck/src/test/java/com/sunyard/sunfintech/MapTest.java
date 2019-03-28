package com.sunyard.sunfintech;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {
    @Test
    public void test(){
        Map<String,String> accounts = new ConcurrentHashMap<>();
        accounts.put("1","1");
        accounts.put("2","1");
        for(Map.Entry<String,String> entry:accounts.entrySet()){

            accounts.remove(entry.getKey());
        }
    }
}
