package org.dllwh.redis.jedis;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import redis.clients.jedis.Jedis;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 *
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: 基于jedis的redis操作工具类
 *
 * @author: <a href="mailto:duleilewuhen@sina.com">独泪了无痕</a>
 * @创建时间: 2021-08-05 11:03:01
 * @版本: V 1.0.1
 * @since: JDK 1.8
 */
public final class RedisHelper {
	// redis主机的ip
	private String host;
	// redis主机的端口号
	private int port;
	// 登录口令
	private String password;

	public RedisHelper(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public RedisHelper(String host, int port, String password) {
		this.host = host;
		this.port = port;
		this.password = password;
	}

	/**
	 * 获取操作redis的jedis对象，并选择redis库。
	 * 
	 * @param index 默认是0号库，可传入1-16之间的数选择库存放数据
	 */
	protected synchronized Jedis getRedisClient(int index) {
		Jedis jedis = new Jedis(host, port);
		if (isNotBlank(password)) {
			jedis.auth(password);
		}
		if (index < 0 || index > 16) {
			index = 1;
		}
		jedis.select(index);
		return jedis;
	}

	/**
	 * @方法描述:关闭redis的客户端
	 * @param jedisClient
	 */
	protected void closeRedisClient(Jedis jedisClient) {
		if (jedisClient != null) {
			jedisClient.close();
		}
	}

}
