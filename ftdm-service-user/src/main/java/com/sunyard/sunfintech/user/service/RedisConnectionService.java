package com.sunyard.sunfintech.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sunyard.sunfintech.core.base.BaseServiceSimple;
import com.sunyard.sunfintech.core.util.CacheUtil;
import com.sunyard.sunfintech.pub.provider.IRedisConnectionService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.connection.DataType;

/**
 * @author heroy
 * @version 2017/5/25
 * @see IRedisConnectionService
 */
@Service(interfaceClass = IRedisConnectionService.class)
@CacheConfig(cacheNames = "reidsConnectionService")
@org.springframework.stereotype.Service("reidsConnectionService")
public class RedisConnectionService extends BaseServiceSimple implements IRedisConnectionService {
    @Override
    public void set(String key, String value) {
        CacheUtil.getCache().set(key, value);
    }

    @Override
    public void set(String key, String value, int second) {
        CacheUtil.getCache().set(key, value, second);
    }

    @Override
    public void del(String key) {
        CacheUtil.getCache().del(key);
    }

    @Override
    public Object get(String key) {
        return CacheUtil.getCache().getAndNotSetAlive(key);
    }

    @Override
    public DataType getType(String key) {
        return CacheUtil.getCache().getType(key);
    }

    @Override
    public long increment(String key, long count) {
        return CacheUtil.getCache().increment(key, count);
    }

    @Override
    public boolean expire(String key, int seconds) {
        return CacheUtil.getCache().expire(key, seconds);
    }

    @Override
    public long ttl(String key) {
        return CacheUtil.getCache().ttl(key);
    }
}
