package org.dllwh.redis.redisson;

import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 * <p>
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: TODO(这里用一句话描述这个类的作用)
 * @author: <a href="mailto:duleilewuhen@sina.com">独泪了无痕</a>
 * @创建时间: 2021-07-09 下午5:02
 * @版本: V 1.0.1
 * @since: JDK 1.8
 * @see <a href="">TODO(连接内容简介)</a>
 */
public final class RedissonLockHelper {
    public void testReentrantLock(RedissonClient redisson) {
        RLock lock = redisson.getLock("anyLock");
        try {
            // 1. 最常见的使用方法
            lock.lock();

            // 2. 支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁
            lock.lock(10, TimeUnit.SECONDS);

            // 3. 尝试加锁，最多等待3秒，上锁以后10秒自动解锁
            boolean res = lock.tryLock(3, 10, TimeUnit.SECONDS);
            if (res) {
                System.out.println("此处可以处理相关业务逻辑");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void testFairLock(RedissonClient redisson) {
        RLock fairLock = redisson.getFairLock("anyLock");
        try {
            // 最常见的使用方法
            fairLock.lock();
            // 支持过期解锁功能, 10秒钟以后自动解锁,无需调用unlock方法手动解锁
            fairLock.lock(10, TimeUnit.SECONDS);
            // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean res = fairLock.tryLock(100, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            fairLock.unlock();
        }
    }

    public void testMultiLock(RedissonClient redisson1, RedissonClient redisson2, RedissonClient redisson3) {
        // 可以是不同的客户端
        RLock lock1 = redisson1.getLock("lock1");
        RLock lock2 = redisson2.getLock("lock2");
        RLock lock3 = redisson3.getLock("lock3");

        RedissonMultiLock lock = new RedissonMultiLock(lock1, lock2, lock3);

        try {
            // 方法一：同时加锁：lock1 lock2 lock3, 所有的锁都上锁成功才算成功。
            lock.lock();

            // 方法二：尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void testRedLock(RedissonClient redisson1, RedissonClient redisson2, RedissonClient redisson3) {
        RLock lock1 = redisson1.getLock("lock1");
        RLock lock2 = redisson2.getLock("lock2");
        RLock lock3 = redisson3.getLock("lock3");
        RedissonRedLock lock = new RedissonRedLock(lock1, lock2, lock3);
        try {
            // 同时加锁：lock1 lock2 lock3, 红锁在大部分节点上加锁成功就算成功。
            lock.lock();
            // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
