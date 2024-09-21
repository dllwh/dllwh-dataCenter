package org.dllwh.rabbitmq.example.simple;

import com.rabbitmq.client.*;
import java.io.IOException;

import org.dllwh.rabbitmq.util.RabbitHelper;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 *
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: 消息的使用者
 * 
 * @author: <a href="mailto:duleilewuhen@sina.com">独泪了无痕</a>
 * @创建时间: 2021-03-01 22:28:03
 * @版本: V 1.0.1
 * @since: JDK 1.8
 */
public final class RabbitCustomer {
	private static String QUEUE_NAME = "simple_queue";

	public static void main(String[] args) throws Exception {
		// 建立连接
		Connection conn = RabbitHelper.getConnection("");
		// 创建消息通道
		Channel channel = conn.createChannel();
		// 声明队列 队列名，是否持久化，是否独占模式，无消息后是否自动删除，消息携带参数
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println("正在等待接收消息...");
		// 创建消费者
		Consumer consumer = new DefaultConsumer(channel) {
			/**
			 * 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
			 *
			 * @param consumerTag
			 * @param envelope
			 * @param properties
			 * @param body        消息队列中取出的消息
			 * @throws IOException
			 */
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				System.out.println("收到消息: " + new String(body, "UTF-8"));
				if (properties != null) {
					System.out.println("当前消息ID为：" + properties.getMessageId());
					System.out.println("收到自定义属性：" + properties.getHeaders().get("name"));
				}
			}
		};
		/**
		 * 自动回复队列应答 -- RabbitMQ中的消息确认机制
		 *
		 * 参数1：String queue 队列名称 参数2：boolean autoAck 开启消息的自动确认机制 参数3：Consumer callback
		 * 消费时回调接口
		 */
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}
