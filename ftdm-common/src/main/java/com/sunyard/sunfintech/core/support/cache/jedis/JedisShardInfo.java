/**
 * 
 */
package com.sunyard.sunfintech.core.support.cache.jedis;

import org.apache.commons.lang3.StringUtils;

/**
 * 重写不可以设置空密码
 * @author heroy
 * @version 20170401
 */
public class JedisShardInfo extends redis.clients.jedis.JedisShardInfo {

	public JedisShardInfo(String host, int port) {
		super(host, port);
	}

	@Override
	public void setPassword(String password) {
		if (StringUtils.isNotBlank(password)) {
			super.setPassword(password);
		}
	}
}
