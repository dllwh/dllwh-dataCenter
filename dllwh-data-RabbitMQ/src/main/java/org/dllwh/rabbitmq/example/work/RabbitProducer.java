package org.dllwh.rabbitmq.example.work;

import org.dllwh.rabbitmq.util.RabbitHelper;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 *
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: 工作模式的消息生成者
 * 
 * @author: <a href="mailto:duleilewuhen@sina.com">独泪了无痕</a>
 * @创建时间: 2021-03-01 22:29:20
 * @版本: V 1.0.1
 * @since: JDK 1.8
 */
public final class RabbitProducer {
	private static String QUEUE_NAME = "work_queue";

	public static void main(String[] args) throws Exception {
		// 建立连接
		Connection conn = RabbitHelper.getConnection("");
		// 创建消息通道
		Channel channel = conn.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		// 需要发送的消息
		String msg = "Hello, RabbitMQ ";

		for (int i = 0; i < 10; i++) {
			channel.basicPublish("", QUEUE_NAME, null, (msg + i).getBytes());
		}

		/**
		 * 关闭通道和连接
		 */
		RabbitHelper.closeConn(channel, conn);
	}
}
