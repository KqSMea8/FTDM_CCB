package com.sunyard.sunfintech.pub.provider;

import org.springframework.data.redis.connection.DataType;

/**
 * Created by Lid on 2017/7/17.
 */
public interface IRedisConnectionService {
    void set(String key, String value);

    void set(String key, String value, int second);

    void del(String key);

    Object get(String key);

    DataType getType(String key);

    long increment(String key, long count);

    boolean expire(String key, int seconds);

    long ttl(String key);
}
