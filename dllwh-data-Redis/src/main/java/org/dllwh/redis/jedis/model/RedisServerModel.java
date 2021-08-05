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
 * @创建时间: 2021-08-05 11:35:39
 * @版本: V 1.0.1
 * @since: JDK 1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisServerModel {
	/**
	 * 唯一标示
	 */
	private String ServerId;
	/**
	 * Redis地址
	 */
	private String ServerHost;
	/**
	 * Redis描述
	 */
	private String ServerDescribe;
}
