package org.dllwh.redis.jedis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 *
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: TODO(这里用一句话描述这个类的作用)
 *
 * @author: <a href="mailto:duleilewuhen@sina.com">独泪了无痕</a>
 * @创建时间: 2021-08-05 11:29:35
 * @版本: V 1.0.1
 * @since: JDK 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientInfoModel {
	/**
	 * 连接的总持续时间以秒为单位
	 */
	private Long ageSeconds;
	/**
	 * 当前的数据库ID
	 */
	private int database;
	/**
	 * 客户机的主机
	 */
	private String host;
	/**
	 * 端口
	 */
	private int port;
	/**
	 * 连接的空闲时间(单位秒)
	 */
	private int idleSeconds;
	/**
	 * 最后一个命令中
	 */
	private String lastCommand;
	/**
	 * 订阅数量
	 */
	private int patternSubscriptionCount;
	/**
	 * 原始内容
	 */
	private String raw;
	/**
	 * 订阅频道数量
	 */
	private int subscriptionCount;
	/**
	 * 执行事务的命令数量
	 */
	private int transactionCommandLength;
}
