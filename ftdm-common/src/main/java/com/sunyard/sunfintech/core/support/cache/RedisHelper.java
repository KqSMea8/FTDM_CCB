package com.sunyard.sunfintech.core.support.cache;

import com.sunyard.sunfintech.core.util.PropertiesUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存辅助类
 * @author heroy
 * @version 20170401
 */
public final class RedisHelper implements CacheManager, ApplicationContextAware {

    private RedisTemplate<Serializable, Serializable> redisTemplate = null;
    private Integer EXPIRE = PropertiesUtil.getInt("redis.expiration");

    protected ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    // 获取连接
    @SuppressWarnings("unchecked")
    private RedisTemplate<Serializable, Serializable> getRedis() {
        if (redisTemplate == null) {
            synchronized (RedisHelper.class) {
                if (redisTemplate == null) {
                    redisTemplate = (RedisTemplate<Serializable, Serializable>)applicationContext
                            .getBean("redisTemplate");
                }
            }
        }
        return redisTemplate;
    }

    public final Object get(final String key) {
//       expire(key, EXPIRE);
        return getRedis().boundValueOps(key).get();
    }

    @Override
    public Object getAndNotSetAlive(String key) {
        return getRedis().boundValueOps(key).get();
    }

    public final Set<Object> getAll(final String pattern) {
        Set<Object> values = new HashSet<>();
        Set<Serializable> keys = getRedis().keys(pattern);
        for (Serializable key : keys) {
            expire(key.toString(), EXPIRE);
            values.add(getRedis().opsForValue().get(key));
        }
        return values;
    }

    public final void set(final String key, final Serializable value, int seconds) {
        getRedis().boundValueOps(key).set(value);
        expire(key, seconds);
    }

    public final void set(final String key, final Serializable value) {
        getRedis().boundValueOps(key).set(value);
        //默认超时24小时
        getRedis().expire(key, EXPIRE*144, TimeUnit.SECONDS);
    }

    public final Boolean exists(final String key) {
        return getRedis().hasKey(key);
    }

    public final void del(final String key) {
        getRedis().delete(key);
    }

    public final void delAll(final String pattern) {
        getRedis().delete(getRedis().keys(pattern));
    }

    public final String type(final String key) {
        expire(key, EXPIRE);
        return getRedis().type(key).getClass().getName();
    }

    /**
     * 在某段时间后失效
     *
     * @return
     */
    public final Boolean expire(final String key, final int seconds) {
        return getRedis().expire(key, seconds, TimeUnit.SECONDS);
//        return true;
    }

    /**
     * 在某个时间点失效
     *
     * @param key
     * @param unixTime
     * @return
     */
    public final Boolean expireAt(final String key, final long unixTime) {
        return getRedis().expireAt(key, new Date(unixTime));
    }

    public final Long ttl(final String key) {
        return getRedis().getExpire(key, TimeUnit.SECONDS);
    }

    public final void setrange(final String key, final long offset, final String value) {
        getRedis().boundValueOps(key).set(value, offset);
        expire(key, EXPIRE);
    }

    public final String getrange(final String key, final long startOffset, final long endOffset) {
        expire(key, EXPIRE);
        return getRedis().boundValueOps(key).get(startOffset, endOffset);
    }

    public final Object getSet(final String key, final Serializable value) {
        expire(key, EXPIRE);
        return getRedis().boundValueOps(key).getAndSet(value);
    }

    public boolean setnx(String key, Serializable value) {
        boolean success=getRedis().boundValueOps(key).setIfAbsent(value);
        //默认超时24小时
        if(success){
            expire(key, EXPIRE*144);
        }
        return success;
    }

    public void unlock(String key) {
        del(key);
    }

    public void hset(String key, String field, String value) {
        getRedis().boundHashOps(key).put(field, value);
    }

    public Object hget(String key, String field) {
        return getRedis().boundHashOps(key).get(field);
    }

    public void hdel(String key, String field) {
        getRedis().boundHashOps(key).delete(field);
    }

    public long increment(String key, long num){
        long res=getRedis().opsForValue().increment(key, num);
        getRedis().expire(key, EXPIRE*144, TimeUnit.SECONDS);
        return res;
    }

    @Override
    public Object rightPop(String key) {
        return getRedis().opsForList().rightPop(key);
    }

    @Override
    public Long leftPush(String key, String value) {
        return getRedis().opsForList().leftPush(key,value);
    }

    @Override
    public Long leftPushAll(String key, String... values) {
        return getRedis().opsForList().leftPushAll(key,values);
    }

    @Override
    public Long size(String key) {
        return getRedis().opsForList().size(key);
    }

    @Override
    public List<Serializable> getRange(String key, long start, long end) {
        return getRedis().opsForList().range(key,start,end);
    }

    @Override
    public Long addToSet(String key, String value) {
        return getRedis().opsForSet().add(key,value);
    }

    @Override
    public Set<Serializable> getFromSet(String key) {
        return getRedis().opsForSet().members(key);
    }

    @Override
    public Long getSetSize(String key) {
        return getRedis().opsForSet().size(key);
    }

    @Override
    public Long removeFromSet(String key, String value) {
        return getRedis().opsForSet().remove(key,value);
    }

    @Override
    public Set<Serializable> getLikeKeys(String likeKey) {
        return getRedis().keys(likeKey);
    }

    @Override
    public DataType getType(String key) {
        return getRedis().type(key);
    }

    @Override
    public Cursor<Serializable> scanSet(String key, ScanOptions scanOptions) {
        return getRedis().opsForSet().scan(key,scanOptions);
    }
}
