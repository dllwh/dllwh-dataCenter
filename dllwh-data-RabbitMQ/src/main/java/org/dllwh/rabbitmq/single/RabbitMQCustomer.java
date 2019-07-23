package org.dllwh.rabbitmq.single;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.dllwh.rabbitmq.single.base.RabbitEndPoint;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 把今天最好的表现当作明天最新的起点．．～
 *
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: 消息Customer
 * @创建者: 独泪了无痕--duleilewuhen@sina.com
 * @创建时间: 2019年7月23日 上午12:04:37
 * @版本: V1.0.1
 * @since: JDK 1.8
 */
@Slf4j
public class RabbitMQCustomer extends RabbitEndPoint implements Runnable, Consumer {

	public RabbitMQCustomer(String queueName) throws Exception {
		super(queueName);
	}

	public RabbitMQCustomer(String exchangeName, String queueName) throws Exception {
		super(exchangeName, queueName);
	}

	@Override
	public void handleConsumeOk(String consumerTag) {

	}

	@Override
	public void handleCancelOk(String consumerTag) {

	}

	@Override
	public void handleCancel(String consumerTag) throws IOException {

	}

	/**
	 * 获取到达消息
	 */
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties basicProperties,
			byte[] bytes) throws IOException {
		// 参数2：false为确认收到消息，rabbitmq可以将此消息从队列中删除；ture为拒绝收到消息
		channel.basicAck(envelope.getDeliveryTag(), false);
		Map<?, ?> map = (HashMap<?, ?>) SerializationUtils.deserialize(bytes);
		log.info("Message Number " + map.get("message number") + " received.");
	}

	@Override
	public void handleShutdownSignal(String consumerTag, ShutdownSignalException signalException) {

	}

	@Override
	public void handleRecoverOk(String consumerTag) {
		log.info("Consumer " + consumerTag + " registered");
	}

	@Override
	public void run() {
		try {

			channel = getChannelInstance();

			// 设置消费者客户端最多接收未被ack的消息的个数
			// channel.basicQos(64);
			// 消费者订阅消息 监听如上声明的队列 (队列名, 是否自动应答, 消费者标签, 消费者)
			channel.basicConsume(queueName, true, this);
			// logger.info("-------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}