package org.dllwh.rabbitmq.example.simple;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.dllwh.rabbitmq.util.RabbitHelper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 *
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: 简单模式的消息生成者
 * 
 * @author: <a href="mailto:duleilewuhen@sina.com">独泪了无痕</a>
 * @创建时间: 2021-03-01 22:26:17
 * @版本: V 1.0.1
 * @since: JDK 1.8
 */
public final class RabbitProducer {
	private static String QUEUE_NAME = "simple_queue";

	public static void main(String[] args) throws Exception {
		// 建立连接
		Connection conn = RabbitHelper.getConnection("");
		// 创建消息通道
		Channel channel = conn.createChannel();
		Map<String, Object> headers = new HashMap<String, Object>(1);
		// 可以自定义一些自定义的参数和消息一起发送过去
		headers.put("name", "双子孤狼");

		AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
				// 编码
				.contentEncoding("UTF-8")
				// 自定义的属性
				.headers(headers)
				// 消息id
				.messageId(String.valueOf(UUID.randomUUID())).build();
		// 需要发送的消息
		String msg = "Hello, RabbitMQ";

		/**
		 * 通道绑定消息队列
		 *
		 * @param queue      定义的队列名称，如果队列不存在会自动创建
		 * @param durable    队列是否持久化.true:持久化；false:不持久化。队列的声明默认是存放到内存中的，如果rabbitmq重启会丢失，如果想重启之后还存在就要使队列持久化
		 * @param exclusive  是否独占队列 true 独占队列 false 不独占
		 * @param autoDelete 当最后一个消费者断开连接之后队列是否自动被删除 true 自动删除
		 * @param arguments  当前队列附加的参数
		 */
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		/**
		 * 发布消息
		 * @param exchange 交换机名称，如果直接发送信息到队列，则交换机名称为""
		 * @param routingKey 目标队列名称
		 * @param props 设置当前这条消息的属性（设置过期时间 10）
		 * @param bod 消息的内容
		 */
		channel.basicPublish("", QUEUE_NAME, properties, msg.getBytes());

		/**
		 * 关闭通道和连接
		 */
		RabbitHelper.closeConn(channel, conn);
	}
}
