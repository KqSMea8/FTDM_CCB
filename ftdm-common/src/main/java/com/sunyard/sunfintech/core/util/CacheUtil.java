package com.sunyard.sunfintech.core.util;

import com.sunyard.sunfintech.core.support.cache.CacheManager;
import com.sunyard.sunfintech.core.support.cache.RedisHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheUtil {
	private static CacheManager cacheManager;

	private static final Logger logger = LogManager.getLogger(CacheUtil.class);

	@Bean
	public CacheManager setCache() {
		//cacheManager = new RedissonHelper();
		cacheManager = getCache();
		return cacheManager;
	}

	public static CacheManager getCache() {
        if (cacheManager == null) {
            cacheManager = new RedisHelper();
        }
		return cacheManager;
	}

	/** 获取锁 */
	public static boolean getLock(String key) {
		if (!getCache().exists(key)) {
			synchronized (CacheUtil.class) {
				if (!getCache().exists(key)) {
					if (getCache().setnx(key, String.valueOf(System.currentTimeMillis()))) {
						logger.info("获取redis锁：key=" + key +", 成功");
						return true;
					}
				}
			}
		}
		//设置时间为 一分钟
		int expires = 1000 * 60 * 2;
		String currentValue = (String) getCache().get(key);
		if (currentValue != null && Long.parseLong(currentValue) < System.currentTimeMillis() - expires) {
			unlock(key);
			return getLock(key);
		}
		logger.info("获取redis锁：key=" + key +", 失败");
		return false;
	}

	/** 获取自有锁 */
	public static boolean getLockTime(String key,Long time) {
		if (!getCache().exists(key)) {
			synchronized (CacheUtil.class) {
				if (!getCache().exists(key)) {
					if (getCache().setnx(key, String.valueOf(System.currentTimeMillis()))) {
						return true;
					}
				}
			}
		}
		//
		Long expires = 1000L * time;
		String currentValue = (String) getCache().get(key);
		if (currentValue != null && Long.parseLong(currentValue) < System.currentTimeMillis() - expires) {
			unlock(key);
			return getLock(key);
		}
		return false;
	}

	public static void unlock(String key) {
		getCache().unlock(key);
	}
}