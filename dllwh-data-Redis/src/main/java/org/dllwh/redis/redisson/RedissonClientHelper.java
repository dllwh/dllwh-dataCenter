package org.dllwh.redis.redisson;

import jodd.util.StringUtil;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 * <p>
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: Redis client的辅助工具类。用于连接Redis服务器 创建不同的Redis Server对应的客户端对象
 * @author: <a href="mailto:duleilewuhen@sina.com">独泪了无痕</a>
 * @创建时间: 2021-07-16 下午11:00
 * @版本: V 1.0.1
 * @since: JDK 1.8
 */
public final class RedissonClientHelper {
    private static RedissonClientHelper redissonHelper;

    private RedissonClientHelper() {
    }

    /**
     * 提供单例模式
     *
     * @return
     */
    public static RedissonClientHelper getInstance() {
        if (redissonHelper == null) {
            synchronized (RedissonClientHelper.class) {
                if (redissonHelper == null) {
                    redissonHelper = new RedissonClientHelper();
                }
            }
        }
        return redissonHelper;
    }

    /**
     * 使用config创建Redisson
     *
     * @param config
     * @return
     */
    public RedissonClient getRedissonClient(Config config) {
        RedissonClient redissonClient = Redisson.create(config);
        System.out.println("成功连接Redis Server");
        return redissonClient;
    }

    /**
     * 使用ip地址和端口创建Redisson
     *
     * @param ip
     * @param port
     * @return
     */
    public RedissonClient getRedissonClient(String ip, String port, String password) {
        Config config = new Config();
        config.setCodec(new org.redisson.client.codec.StringCodec());
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://"+ip + ":" + port);
        if (StringUtil.isNotBlank(password)) {
            singleServerConfig.setPassword(password);
        }
        RedissonClient redissonClient = Redisson.create(config);
        System.out.println("成功连接Redis Server" + "\t" + "连接" + ip + ":" + port + "服务器");
        return redissonClient;
    }

    /**
     * 关闭Redisson客户端连接
     *
     * @param redissonClient
     */
    public void closeRedis(RedissonClient redissonClient) {
        if (!redissonClient.isShutdown()) {
            redissonClient.shutdown();
            System.out.println("成功关闭Redis Client连接");
        }
    }

    /**
     * 获取字符串对象
     *
     * @param redissonClient
     * @param objectName
     * @param <T>
     * @return
     */
    public <T> RBucket<T> getBucket(RedissonClient redissonClient, String objectName) {
        return redissonClient.getBucket(objectName);
    }

    /**
     * 获取Map对象
     * <p>
     * RMap实现了java.util.concurrent.ConcurrentMap接口和java.util.Map接口—±
     *
     * @param redissonClient
     * @param objectName
     * @return
     */
    public <K, V> RMap<K, V> getMap(RedissonClient redissonClient, String objectName) {
        return redissonClient.getMap(objectName);
    }

    /**
     * 获取有序集合
     *
     * @param redissonClient
     * @param objectName
     * @return
     */
    public <V> RSortedSet<V> getSortedSet(RedissonClient redissonClient, String objectName) {
        return redissonClient.getSortedSet(objectName);
    }

    /**
     * 获取集合
     * <p>
     * RSet实现了java.util.Set接口
     *
     * @param redissonClient
     * @param objectName
     * @return
     */
    public <V> RSet<V> getSet(RedissonClient redissonClient, String objectName) {
        return redissonClient.getSet(objectName);
    }

    /**
     * 获取列表
     * <p>
     * RMap实现了java.util.concurrent.ConcurrentMap接口和java.util.Map接口
     *
     * @param redissonClient
     * @param objectName
     * @return
     */
    public <V> RList<V> getList(RedissonClient redissonClient, String objectName) {
        return redissonClient.getList(objectName);
    }

    /**
     * 获取队列
     * <p>
     * RQueue实现了java.util.Queue接口
     *
     * @param redissonClient
     * @param objectName
     * @return
     */
    public <V> RQueue<V> getQueue(RedissonClient redissonClient, String objectName) {
        return redissonClient.getQueue(objectName);
    }

    /**
     * 获取双端队列
     *
     * @param redissonClient
     * @param objectName
     * @return
     */
    public <V> RDeque<V> getDeque(RedissonClient redissonClient, String objectName) {
        return redissonClient.getDeque(objectName);
    }

    /**
     * 获取锁
     * <p>
     * 可重入锁 RLock实现了java.util.concurrent.locks.Lock接口
     *
     * @param redissonClient
     * @param objectName
     * @return
     */
    public RLock getLock(RedissonClient redissonClient, String objectName) {
        return redissonClient.getLock(objectName);
    }

    /**
     * 获取原子数
     *
     * @param redissonClient
     * @param objectName
     * @return
     */
    public RAtomicLong getAtomicLong(RedissonClient redissonClient, String objectName) {
        return redissonClient.getAtomicLong(objectName);
    }

    /**
     * 获取记数锁
     *
     * @param redissonClient
     * @param objectName
     * @return
     */
    public RCountDownLatch getCountDownLatch(RedissonClient redissonClient, String objectName) {
        return redissonClient.getCountDownLatch(objectName);
    }

    /**
     * 获取消息的Topic
     *
     * @param redissonClient
     * @param objectName
     * @return
     */
    public RTopic getTopic(RedissonClient redissonClient, String objectName) {
        return redissonClient.getTopic(objectName);
    }

    /**
     * 二进制流
     *
     * @param redissonClient
     * @param objectName
     * @return
     */
    public RBinaryStream getBinaryStream(RedissonClient redissonClient, String objectName) {
        return redissonClient.getBinaryStream(objectName);
    }
}
