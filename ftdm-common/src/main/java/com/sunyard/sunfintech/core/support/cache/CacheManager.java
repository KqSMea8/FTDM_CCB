package com.sunyard.sunfintech.core.support.cache;

import com.sun.prism.PixelFormat;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 缓存接口
 * @author heroy
 * @version 20170401
 *
 */
public interface CacheManager {
	public Object get(final String key);

	public Object getAndNotSetAlive(final String key);

	public Set<Object> getAll(final String pattern);

	public void set(final String key, final Serializable value, int seconds);

	public void set(final String key, final Serializable value);

	public Boolean exists(final String key);

	public void del(final String key);

	public void delAll(final String pattern);

	public String type(final String key);

	public Boolean expire(final String key, final int seconds);

	public Boolean expireAt(final String key, final long unixTime);

	public Long ttl(final String key);

	public Object getSet(final String key, final Serializable value);
	
	public boolean setnx(final String key, final Serializable value);

	public void unlock(String key);

	public void hset(String key, String field, String value);

	public Object hget(String key, String field);

	public void hdel(String key, String field);

	public long increment(String key, long num);

	public Object rightPop(String key);

	public Long leftPush(String key, String value);

	public Long leftPushAll(String key, String... values);

	public Long size(String key);

	public List<Serializable> getRange(String key, long start, long end);

	public Long addToSet(String key,String value);

	public Set<Serializable> getFromSet(String key);

	public Long getSetSize(String key);

	public Long removeFromSet(String key,String value);

	//keys *
	public Set<Serializable> getLikeKeys(String likeKey);

	public DataType getType(String key);

	public Cursor<Serializable> scanSet(String key, ScanOptions scanOptions);
}
