package org.dllwh.redis.jedis.command;

import java.util.List;
import java.util.Map;

import org.dllwh.redis.jedis.model.*;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 * <p>
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: Redis 服务器命令
 * @author: <a href="mailto:duleilewuhen@sina.com">独泪了无痕</a>
 * @创建时间: 2021-08-05 11:27:00
 * @版本: V 1.0.1
 * @since: JDK 1.8
 * @see <a href="redisdoc.com/server/index.html">Server（服务器）</a>
 */
public interface RedisServerCommand {
    /**
     * 返回关于 当前Redis 服务器的各种信息和统计数值
     *
     * @return
     * @throws Exception
     */
    List<RedisServerInfo> getRedisServerInfo() throws Exception;

    /**
     * 根据分段获取redis info信息
     *
     * @param section
     * @return
     * @throws Exception
     */
    String getRedisInfoBySection(String section) throws Exception;

    /**
     * 用于返回所有连接到服务器的客户端信息和统计数据
     *
     * @return
     * @throws Exception
     */
    List<ClientInfo> getClientList() throws Exception;

    /**
     * 关闭客户端连接
     *
     * @param addr
     * @return
     * @throws Exception
     */
    boolean kill(String addr) throws Exception;

    /**
     * 获取占用内存大小:返回当前数据库的 key 的数量
     *
     * @return
     * @throws Exception
     */
    Long dbSize() throws Exception;

    /**
     * 获取当前redis使用内存大小情况
     *
     * @return
     * @throws Exception
     */
    Map<String, Object> getMemoryInfo() throws Exception;

    /**
     * 用于测试与服务器的连接是否仍然生效，或者用于测量延迟值
     *
     * @return
     * @throws Exception
     */
    boolean isPing() throws Exception;

    /**
     * 如果ping通，返回true，如果ping不通，则每隔1秒尝试一次重连，如果3次重连失败，则认为连接失败
     *
     * @return
     * @throws Exception
     */
    boolean isConnRedisRetry() throws Exception;
}
