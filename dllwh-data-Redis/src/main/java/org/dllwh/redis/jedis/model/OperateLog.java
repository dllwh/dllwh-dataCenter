package org.dllwh.redis.jedis.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 *
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: 操作日志
 *
 * @author: <a href="mailto:duleilewuhen@sina.com">独泪了无痕</a>
 * @创建时间: 2021-08-05 11:30:48
 * @版本: V 1.0.1
 * @since: JDK 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperateLog implements Serializable {

	private static final long serialVersionUID = -8896238070357830105L;
	/**
	 * slowlog唯一编号id
	 */
	private long id;
	/**
	 * 查询的时间戳
	 */
	private String executeTime;
	/**
	 * 查询的耗时（微妙），如表示本条命令查询耗时47微秒
	 */
	private String usedTime;
	/**
	 * 查询命令，完整命令为 SLOWLOG GET，slowlog最多保存前面的31个key和128字符
	 */
	private String args;
}
