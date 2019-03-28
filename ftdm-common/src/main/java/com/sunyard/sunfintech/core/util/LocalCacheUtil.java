package com.sunyard.sunfintech.core.util;

import java.util.Map;
import java.util.TreeMap;

/**
 * 本地缓存，存储很少修改的参数
 * @author heroy
 * @version 2018/1/2
 */
public class LocalCacheUtil {
    public static Map<String, Object> locaCache = new TreeMap<String, Object>();

    public static Object getCache(String key){
        return locaCache.get(key);
    }

    public static Object setCache(String key, Object value){
        return locaCache.put(key, value);
    }
}
