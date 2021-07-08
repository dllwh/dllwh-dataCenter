package org.dllwh.rabbitmq.util;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

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
	private static final ResourceBundle resourceBundle;
	/**
	 * 读取maven工程的properties配置文件
	 */
	static {
		resourceBundle = ResourceBundle.getBundle("RabbitMQ");
	}

	private static String getKey(String key) {
		return resourceBundle.getString(key);
	}

	/**
	 * 获取MQ的连接
	 *
	 * @param connectionName
	 * @return MQ连接资源
	 * @throws Exception
	 * @see <a href="">为了省事这里就抛出异常了，不处理了，开发时别这么干</a>
	 */
	public static Connection getConnection(String connectionName) throws Exception {
		/**
		 * 1.创建连接工厂
		 */
		ConnectionFactory connectionFactory = new ConnectionFactory();
		/**
		 * 2.在工厂对象中设置RabbitMQ相关连接信息
		 */
		// 设置服务端地址（域名地址/ip）
		connectionFactory.setHost(getKey("rabbit.host"));
		// 设置虚拟主机(相当于数据库中的库)
		connectionFactory.setVirtualHost(getKey("rabbit.VirtualHos"));
		// 设置用户名
		connectionFactory.setUsername(getKey("rabbit.username"));
		// 设置密码
		connectionFactory.setPassword(getKey("rabbit.password"));
		// 设置服务器端口号
		connectionFactory.setPort(5672);
		// 开启自动连接恢复
		connectionFactory.setAutomaticRecoveryEnabled(true);
		// 如果因为异常导致恢复失败（比如RabbitMQ节点尚不可用），在固定的时间间隔（默认5秒）进行重试
		connectionFactory.setNetworkRecoveryInterval(10000);

		/**
		 * 3. 设置客户端属性
		 */
		// 设置ConnectionFactory属性信息
		Map<String, Object> connectionFactoryPropertiesMap = new HashMap<>();
		connectionFactoryPropertiesMap.put("principal", "duleilewuhen@sina");
		connectionFactoryPropertiesMap.put("description", "初识RabbitMq");
		connectionFactoryPropertiesMap.put("emailAddress", "duleilewuhen@sina.com");
		connectionFactory.setClientProperties(connectionFactoryPropertiesMap);

		/**
		 * 4.通过工厂对象获取与MQ的链接
		 */
		Connection connection = null;
		if (StringUtils.isNotBlank(connectionName)) {
			connection = connectionFactory.newConnection(connectionName);
		} else {
			connection = connectionFactory.newConnection();
		}
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
