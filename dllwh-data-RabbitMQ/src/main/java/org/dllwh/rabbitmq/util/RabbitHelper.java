package org.dllwh.rabbitmq.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 * <p>
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: Rabbit帮助类
 * @author: <a href="mailto:duleilewuhen@sina.com">独泪了无痕</a>
 * @创建时间: 2021-01-07 22:47
 * @版本: V 1.0.1
 * @since: JDK 1.8
 */
public final class RabbitHelper {
	/**
	 * 获取MQ的连接
	 *
	 * @return MQ连接资源
	 * @throws Exception
	 * @see <a href="">为了省事这里就抛出异常了，不处理了，开发时别这么干</a>
	 */
	public static Connection getConnection() throws Exception {
		// 1.创建连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		// 2.在工厂对象中设置RabbitMQ相关连接信息
		factory.setHost("192.168.200.207");
		factory.setVirtualHost("/");
		factory.setUsername("admin");
		factory.setPassword("admin");
		factory.setPort(5672);
		// 开启自动连接恢复
		factory.setAutomaticRecoveryEnabled(true);
		// 如果因为异常导致恢复失败（比如RabbitMQ节点尚不可用），在固定的时间间隔（默认5秒）进行重试
		factory.setNetworkRecoveryInterval(10000);
		// 3.通过工厂对象获取与MQ的链接
		Connection connection = factory.newConnection();
		return connection;
	}

	/**
	 * 关闭连接
	 * 
	 * @param channel
	 * @param connection
	 */
	public static void closeConn(Channel channel, Connection connection) {
		try {
			if (channel != null) {
				channel.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
