package com.sunyard.sunfintech.core.support.cache.jedis;

import redis.clients.jedis.ShardedJedis;

/**
 * @author heroy
 * @version 20170401
 */
public interface Executor<K> {
	public K execute(ShardedJedis jedis);
}
