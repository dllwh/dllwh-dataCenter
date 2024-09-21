package org.dllwh.redis.redisson;

import io.netty.util.concurrent.FutureListener;
import org.redisson.Redisson;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RAtomicLongAsync;
import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 * <p>
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: TODO(这里用一句话描述这个类的作用)
 * @author: <a href="mailto:duleilewuhen@sina.com">独泪了无痕</a>
 * @创建时间: 2021-07-02 下午1:12
 * @版本: V 1.0.1
 * @since: JDK 1.8
 * @see <a href="">TODO(连接内容简介)</a>
 */
public final class SingleRedissonTest {
	private RedissonClient client;
	private RedissonReactiveClient reactiveClient;
	private RedissonRxClient rxClient;

	/**
	 * 创建链接客户端
	 */
	public static RedissonClient getRedissonClient() {
		System.out.println("reids session starting...");
		// 创建配置
		Config config = new Config();
		config.setCodec(new org.redisson.client.codec.StringCodec());
		// 指定使用单节点部署方式
		SingleServerConfig singleServerConfig = config.useSingleServer();
		// 配置 Redis 服务器地址
		singleServerConfig.setAddress("redis://172.20.96.237:6379");
		// 设置密码
		singleServerConfig.setPassword("smartdot@2020");

		// 创建客户端(发现这步非常耗时，基本在2秒-4秒左右)
		RedissonClient redissonClient = Redisson.create(config);
		return redissonClient;
	}

	/**
	 * 关闭连接
	 */
	public static void closeRedis(RedissonClient redisson) {
		redisson.shutdown();
	}
}
