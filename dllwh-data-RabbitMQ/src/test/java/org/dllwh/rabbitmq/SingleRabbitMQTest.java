package org.dllwh.rabbitmq;

import java.util.HashMap;

import org.dllwh.rabbitmq.single.RabbitMQCustomer;
import org.dllwh.rabbitmq.single.RabbitMQProducer;

/**
 * 
 * 把今天最好的表现当作明天最新的起点．．～
 *
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: 单机rabbitMQ 测试
 * @创建者: 独泪了无痕--duleilewuhen@sina.com
 * @创建时间: 2019年7月23日 上午12:56:47
 * @版本: V1.0.1
 * @since: JDK 1.8
 */
public class SingleRabbitMQTest {
	public static void main(String[] args) {
		try {
			RabbitMQCustomer consumer = new RabbitMQCustomer(null, "hello");
			Thread consumerThread = new Thread(consumer);
			consumerThread.start();

			RabbitMQProducer producer = new RabbitMQProducer(null, "hello");

			for (int i = 0; i < 100; i++) {
				HashMap<String, Object> message = new HashMap<String, Object>();
				message.put("message number", i);
				producer.publish(message);
			}
			producer.disConnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
