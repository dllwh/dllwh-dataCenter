package org.dllwh.rabbitmq.common.handler;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 *
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: 消息处理器接口，通过实现该接口来定制自己的消息处理逻辑
 * @创建者: 独泪了无痕--duleilewuhen@sina.com
 * @创建时间: 2019年7月23日 下午10:52:02
 * @版本: V1.0.1
 * @since: JDK 1.8
 */
public interface MessageHandler {
	/**
	 * @方法描述: 处理消息
	 * @param message
	 *            消息
	 * @return true-如果消息最终被消费掉,该消息会从队列中移除 false-如果消息没有被消费，该消息会保持在队列中
	 */
	public boolean handleMessage(String message);
}