package org.dllwh.rabbitmq.single;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.dllwh.rabbitmq.single.base.RabbitEndPoint;

import com.rabbitmq.client.AMQP.BasicProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 *
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: 消费者Producer
 * @创建者: 独泪了无痕--duleilewuhen@sina.com
 * @创建时间: 2019年4月27日 下午9:46:33
 * @版本: V1.0.1
 * @since: JDK 1.8
 */
@Slf4j
public class RabbitMQProducer extends RabbitEndPoint {
	public RabbitMQProducer(String queueName) throws Exception {
		super(queueName);
	}

	public RabbitMQProducer(String exchangeName, String queueName) throws Exception {
		super(exchangeName, queueName);
	}

	/**
	 * @方法描述: 向消息队列中发布一条消息
	 * @param object
	 * @throws Exception
	 */
	public void publish(Serializable object) throws Exception {
		byte[] msg = SerializationUtils.serialize(object);
		if (StringUtils.isNotEmpty(exchangeName) && StringUtils.isNotEmpty(routingKey)) {
			sendMessageToExchange(routingKey, msg);
		} else {
			sendMessageToExchange(msg);
		}
		log.info("MessagePublisher消息推送: message={}", object);
	}

	/**
	 * @方法描述: 有绑定Key的Exchange发送
	 * @throws IOException
	 */
	private void sendMessageToExchange(String routingKey, byte[] msg) throws IOException {
		channel = getChannelInstance();
		BasicProperties basicProperties = null;
		channel.basicPublish(exchangeName, routingKey, basicProperties, msg);
	}

	/**
	 * @方法描述: 没有绑定KEY的Exchange发送
	 * @throws IOException
	 */
	private void sendMessageToExchange(byte[] msg) throws IOException {
		channel = getChannelInstance();
		BasicProperties basicProperties = null;
		channel.basicPublish("", queueName, basicProperties, msg);
	}
}