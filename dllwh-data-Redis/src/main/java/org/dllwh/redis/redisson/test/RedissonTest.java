package org.dllwh.redis.redisson.test;

import net.bytebuddy.TypeCache;
import org.dllwh.redis.redisson.RedissonClientHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.redisson.api.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 * <p>
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: 测试代码
 * @author: <a href="mailto:duleilewuhen@sina.com">独泪了无痕</a>
 * @创建时间: 2021-07-17 下午8:33
 * @版本: V 1.0.1
 * @since: JDK 1.8
 */
public class RedissonTest {
    private RedissonClient redissonClient;
    private final RedissonClientHelper redissonClientHelper = RedissonClientHelper.getInstance();

    /**
     * 创建客户端连接服务器的redisson对象
     */
    @Before
    public void before() {
        System.out.println("每次在测试方法运行之前运行此方法");
        String ip = "127.0.0.1";
        String port = "6379";
        redissonClient = redissonClientHelper.getRedissonClient(ip, port, "");
    }

    /**
     * 用于关闭客户端连接服务器的redisson对象
     */
    @After
    public void after() {
        redissonClientHelper.closeRedis(redissonClient);
        System.out.println("每次测试方法运行完之后运行此方法");
    }

    /**
     * RBucket 映射为 redis server 的 string 类型
     * 只能存放最后存储的一个字符串
     * redis server 命令:
     * 查看所有键---->keys *
     * 查看key的类型--->type testBucket
     * 查看key的值 ---->get testBucket
     */
    @Test
    public void testGetBucket() {
        RBucket<String> rBucket = redissonClientHelper.getBucket(redissonClient, "testBucket");
        // 同步放置
        rBucket.set("redisBucketASync");
        // 异步放置
        rBucket.setAsync("测试");
        // 通过key获取value
        System.out.println(rBucket.get());
    }

    @Test
    public void testGetBuckets() {
        RBuckets buckets = redissonClient.getBuckets();
        Map<String, Object> loadedBuckets = buckets.get("myBucket1", "myBucket2", "myBucket3");

        Map<String, Object> map = new HashMap();
        map.put("myBucket1", "myBucket1");
        map.put("myBucket2", "myBucket1");

        // 利用Redis的事务特性，同时保存所有的通用对象桶，如果任意一个通用对象桶已经存在则放弃保存其他所有数据。
        buckets.trySet(map);
        // 同时保存全部通用对象桶。
        buckets.set(map);
    }

    /**
     * RMap  映射为  redis server 的 hash 类型
     * 分为
     * put(返回键值) 、 fast(返回状态)
     * 同步    异步
     * redis server 命令:
     * 查看所有键---->keys *
     * 查看key的类型--->type testMap
     * 查看key的值 ---->hgetall testMap
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void testGetMap() throws InterruptedException, ExecutionException {

        RMap<String, Object> testGetMap = redissonClientHelper.getMap(redissonClient, "testGetMap");
        // 设置map中key-value
        testGetMap.put("userName", "独泪了无痕");
        testGetMap.put("email", "duleilewuhen@sina.com");
        // 设置key有效期
        testGetMap.expire(300, TimeUnit.SECONDS);
        // 通过key获取value
        System.out.println(redissonClientHelper.getMap(redissonClient, "testGetMap").get("name"));


        RMap<String, Integer> rMap = redissonClientHelper.getMap(redissonClient, "testMap");
        // 清除集合
        rMap.clear();
        // 添加key-value 返回之前关联过的值
        Integer firstInteger = rMap.put("111", 111);
        System.out.println("firstInteger: " + firstInteger);

        // 添加key-value 返回之前关联过的值
        Integer secondInteger = rMap.putIfAbsent("222", 222);
        System.out.println("secondInteger: " + secondInteger);

        // 移除key-value
        Integer thirdInteger = rMap.remove("222");
        System.out.println("thirdInteger: " + thirdInteger);

        // 添加key-value 不返回之前关联过的值
        boolean third = rMap.fastPut("333", 333);
        System.out.println("third: " + third);
        Future<Boolean> fiveFuture = rMap.fastPutAsync("444", 444);
        System.out.println("fiveFuture: " + fiveFuture.isDone());

        // 异步移除key
        Future<Long> sixFuture = rMap.fastRemoveAsync("444");
        System.out.println("sixFuture: " + sixFuture.get());

        // 遍历集合
        for (String key : rMap.keySet()) {
            System.out.println(key + ":" + rMap.get(key));
        }
    }

    /**
     * RSortedSet 映射为 redis server 的 list 类型
     * 存储以有序集合的形式存放
     * redis server 命令:
     * 查看所有键---->keys *
     * 查看key的类型--->type testSortedSet
     * 查看key的值 ---->lrange testSortedSet 0 10
     */
    @Test
    public void testGetSortedSet() {
        RSortedSet<Integer> rSortedSet = redissonClientHelper.getSortedSet(redissonClient, "testSortedSet");
        // 清除集合
        rSortedSet.clear();

        rSortedSet.add(45);
        rSortedSet.add(12);
        rSortedSet.addAsync(45);
        rSortedSet.add(100);

        // 输出结果集
        System.out.println(Arrays.toString(rSortedSet.toArray()));
    }

    /**
     * RSet 映射为 redis server 的set 类型
     * redis server 命令:
     * 查看所有键---->keys *
     * 查看key的类型--->type testSet
     * 查看key的值 ---->smembers testSet
     */
    @Test
    public void testGetSet() {
        RSet<Integer> rSet = redissonClientHelper.getSet(redissonClient, "testSet");
        // 清除集合
        rSet.clear();

        Collection<Integer> c = Arrays.asList(12, 45, 12, 34, 56, 78);
        rSet.addAll(c);
        // 输出结果集
        System.out.println(Arrays.toString(rSet.toArray()));
        // 设置有效期
        rSet.expire(300, TimeUnit.SECONDS);
    }

    /**
     * RList 映射为 redis server的list类型
     * redis server 命令:
     * 查看所有键---->keys *
     * 查看key的类型--->type testList
     * 查看key的值 ---->lrange testList 0 10
     */
    @Test
    public void testGetList() {
        RList<Integer> rList = redissonClientHelper.getList(redissonClient, "testList");
        // 清除集合
        rList.clear();
        Collection<Integer> c = Arrays.asList(12, 45, 12, 34, 56, 78);
        rList.addAll(c);
        // 设置有效期
        rList.expire(300, TimeUnit.SECONDS);

        // 输出结果集
        System.out.println(Arrays.toString(rList.toArray()));
    }

    /**
     * RQueue 映射为 redis server的list类型
     * 队列--先入先出
     * redis server 命令:
     * 查看所有键---->keys *
     * 查看key的类型--->type testQueue
     * 查看key的值 ---->lrange testQueue 0 10
     */
    @Test
    public void testGetQueue() {
        RQueue<Integer> rQueue = redissonClientHelper.getQueue(redissonClient, "testQueue");
        //清除队列
        rQueue.clear();
        Collection<Integer> c = Arrays.asList(12, 45, 12, 34, 56, 78);
        rQueue.addAll(c);
        //查看队列元素
        System.out.println(rQueue.peek());
        System.out.println(rQueue.element());
        //移除队列元素
        System.out.println(rQueue.poll());
        System.out.println(rQueue.remove());
        //输出队列
        System.out.println(Arrays.toString(rQueue.toArray()));
    }

    /**
     * RDeque 映射为 redis server 的 list类型
     * 双端队列--对头和队尾都可添加或者移除，也遵循队列的先入先出
     * redis server 命令:
     * 查看所有键---->keys *
     * 查看key的类型--->type testDeque
     * 查看key的值 ---->lrange testDeque 0 10
     */
    @Test
    public void testGetDeque() {
        RDeque<Integer> rDeque = redissonClientHelper.getDeque(redissonClient, "testDeque");
        //清空双端队列
        rDeque.clear();
        Collection<Integer> c = Arrays.asList(12, 45, 12, 34, 56, 78);
        rDeque.addAll(c);
        //对头添加元素
        rDeque.addFirst(100);
        //队尾添加元素
        rDeque.addLast(200);
        System.out.println(Arrays.toString(rDeque.toArray()));
        //查看对头元素
        System.out.println(rDeque.peek());
        System.out.println(rDeque.peekFirst());
        //查看队尾元素
        System.out.println(rDeque.peekLast());
        System.out.println(Arrays.toString(rDeque.toArray()));
        //移除对头元素
        System.out.println(rDeque.poll());
        System.out.println(rDeque.pollFirst());
        //移除队尾元素
        System.out.println(rDeque.pollLast());
        System.out.println(Arrays.toString(rDeque.toArray()));
        //添加队尾元素
        System.out.println(rDeque.offer(300));
        System.out.println(rDeque.offerFirst(400));
        System.out.println(Arrays.toString(rDeque.toArray()));
        //移除对头元素
        System.out.println(rDeque.pop());
        //显示双端队列的元素
        System.out.println(Arrays.toString(rDeque.toArray()));

    }

    /**
     * RLock 映射为redis server的string 类型
     * string中存放 线程标示、线程计数
     * 查看所有键---->keys *
     * 查看key的类型--->type testLock1
     * 查看key的值 ---->get testLock1
     * 如果想在redis server中 看到 testLock1
     * 就不能使用   rLock.unlock();
     * 因为使用 rLock.unlock(); 之后 就会删除redis server中的 testLock1
     */
    @Test
    public void testGetLock() {
        // 1、获取一把锁，只要锁的名字一样，就是同一把锁
        RLock rLock = redissonClientHelper.getLock(redissonClient, "testLock1");
        if (rLock.isLocked()) {
            rLock.unlock();
        } else {
            // 2、加锁
            // 2.1)、阻塞式等待。默认加的锁都是30s时间。
            // 2.2)、锁的自动续期，如果业务超长，运行期间自动给锁续上新的30s。不用担心业务时间长，锁自动过期被用特
            // 2.3)、加锁的业务只要运行完成，就不会给当前锁续期，即使不手动解锁，锁默认在30s以后自动删除。
            rLock.lock();
        }

        try {
            System.out.println("加锁成功，执行业务..." + Thread.currentThread().getId());
            System.out.println(rLock.getName());
            System.out.println(rLock.getHoldCount());
            System.out.println(rLock.isLocked());

            Thread.sleep(30000);
        } catch (Exception e) {

        } finally {
            // 3、解锁将设解锁代码没有运行，redisson会不会出现死锁
            System.out.println("释放锁..." + Thread.currentThread().getId());
            rLock.unlock();
        }
    }

    /**
     * RAtomicLong 映射为redis server的string 类型
     * string中数值
     * 查看所有键---->keys *
     * 查看key的类型--->type testAtomicLong
     * 查看key的值 ---->get testAtomicLong
     */
    @Test
    public void testGetAtomicLong() {
        RAtomicLong rAtomicLong = redissonClientHelper.getAtomicLong(redissonClient, "testAtomicLong");
        rAtomicLong.set(100);
        System.out.println(rAtomicLong.addAndGet(200));
        System.out.println(rAtomicLong.decrementAndGet());
        System.out.println(rAtomicLong.get());
    }

    /**
     * RCountDownLatch 映射为redis server的string 类型
     * string中数值
     * 闭锁--等待其他线程中的操作都做完 在进行操作
     * 查看所有键---->keys *
     * 查看key的类型--->type testCountDownLatch
     * 查看key的值 ---->get testCountDownLatch
     */
    @Test
    public void testGetCountDownLatch() throws InterruptedException {
        RCountDownLatch rCountDownLatch = redissonClientHelper.getCountDownLatch(redissonClient, "testCountDownLatch");
        System.out.println(rCountDownLatch.getCount());
        System.out.println(rCountDownLatch.getCount());
        rCountDownLatch.await(10, TimeUnit.SECONDS);
        System.out.println(rCountDownLatch.getCount());
    }

    /**
     * 消息队列的订阅者
     *
     * @throws InterruptedException
     */
    @Test
    public void testGetTopicSub() throws InterruptedException {
        RTopic rTopic = redissonClientHelper.getTopic(redissonClient, "testTopic");

        //等待发布者发布消息
        RCountDownLatch rCountDownLatch = redissonClientHelper.getCountDownLatch(redissonClient, "testCountDownLatch");
        rCountDownLatch.trySetCount(1);
        rCountDownLatch.await();
    }

    /**
     * 消息队列的发布者
     */
    @Test
    public void testGetTopicPub() {
        RTopic rTopic = redissonClientHelper.getTopic(redissonClient, "testTopic");
        System.out.println(rTopic.publish("今天是儿童节，大家儿童节快乐"));
        //发送完消息后 让订阅者不再等待
        RCountDownLatch rCountDownLatch = redissonClientHelper.getCountDownLatch(redissonClient, "testCountDownLatch");
        rCountDownLatch.countDown();
    }

    /**
     * 布隆过滤器
     */
    @Test
    public void testGetBloomFilter() {
        RBloomFilter seqIdBloomFilter = redissonClientHelper.getBloomFilter(redissonClient, "testGetBloomFilter");
        // 初始化预期插入的数据量为10000000和期望误差率为0.01
        seqIdBloomFilter.tryInit(10000000, 0.01);
        // 插入部分数据
        seqIdBloomFilter.add("123");
        seqIdBloomFilter.add("456");
        seqIdBloomFilter.add("789");
        // 判断是否存在
        System.out.println(seqIdBloomFilter.contains("123"));
        System.out.println(seqIdBloomFilter.contains("789"));
        System.out.println(seqIdBloomFilter.contains("100"));
    }
}
