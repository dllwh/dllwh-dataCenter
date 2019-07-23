package org.dllwh.rabbitmq.single.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 * 把今天最好的表现当作明天最新的起点．．～
 *
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: 单机
 * @创建者: 独泪了无痕--duleilewuhen@sina.com
 * @创建时间: 2019年4月27日 下午11:37:11
 * @版本: V1.0.1
 * @since: JDK 1.8
 */
public abstract class RabbitEndPoint {
	private static String	host			= "192.168.1.129";
	private static int		port			= 5672;
	private static String	virtualHost		= "/";
	private static String	userName		= "admin";
	private static String	passWord		= "123456";
	protected Connection	connection;
	protected Channel		channel;							// 信道
	protected String		exchangeName	= "";				// 交换机
	protected String		queueName		= "";				// 队列
	protected String		routingKey		= "";				// 路由键

	public RabbitEndPoint(String queueName) throws IOException, TimeoutException {
		super();
		this.queueName = queueName;
		// 获取到连接
		connection = getConnection(null);
	}

	public RabbitEndPoint(String connectionName, String queueName) throws IOException, TimeoutException {
		super();
		this.queueName = queueName;
		// 获取到连接
		connection = getConnection(connectionName);
		// 从连接中创建 channel实例
	}

	public RabbitEndPoint(String connectionName, String exchangeName, String queueName)
			throws IOException, TimeoutException {
		super();
		this.exchangeName = exchangeName;
		this.queueName = queueName;
		// 获取到连接
		connection = getConnection(connectionName);
	}

	/**
	 * 
	 * @方法描述: 从连接中创建 channel实例
	 * @return
	 * @throws IOException
	 */
	public Channel getChannelInstance() throws IOException {
		if (channel == null) {
			channel = connection.createChannel();
		}

		if (StringUtils.isNotEmpty(exchangeName) && StringUtils.isNotEmpty(routingKey)) {
			// 声明交换机 (交换机名, 交换机类型, 是否持久化, 是否自动删除, 交换机属性);
			channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true, false, new HashMap<>());
			// 声明队列 (队列名, 是否持久化, 是否排他, 是否自动删除, 队列属性);
			channel.queueDeclare(queueName, true, false, false, new HashMap<>());
			// 将队列Binding到交换机上 (队列名, 交换机名, Routing key, 绑定属性);
			channel.queueBind(queueName, exchangeName, routingKey, new HashMap<>());
		} else {
			// 声明队列 (队列名, 是否持久化, 是否排他, 是否自动删除, 队列属性);
			channel.queueDeclare(queueName, false, false, false, null);
		}

		return channel;
	}

	/**
	 * @方法描述: 关闭channel和connection。并非必须，因为隐含是自动调用的。
	 */
	public void close(Channel channel, Connection connection) throws Exception {
		if (channel != null) {
			channel.close();
		}
		if (connection != null) {
			connection.close();
		}
	}

	public void disConnect() throws Exception {
		if (channel != null) {
			channel.close();
		}
		if (connection != null) {
			connection.close();
		}
	}

	/**
	 * @方法描述: 获取MQ的连接
	 * @param host
	 * @param port
	 * @param virtualHost
	 * @param userName
	 * @param passWord
	 * @return
	 * @throws TimeoutException
	 * @throws IOException
	 */
	private Connection getConnection(String connectionName) throws IOException, TimeoutException {
		if (connection == null) {
			ConnectionFactory connectionFactory = getConnectionFactory();
			// 创建一个连接
			if (StringUtils.isNotBlank(connectionName)) {
				return connectionFactory.newConnection(connectionName);
			} else {
				return connectionFactory.newConnection();
			}
		} else {
			return connection;
		}
	}

	/**
	 * @方法描述: 获取工厂
	 * @return
	 */
	private static ConnectionFactory getConnectionFactory() {
		// 定义一个连接工厂
		ConnectionFactory connectionFactory = new ConnectionFactory();
		// 设置服务端地址（域名地址/ip）
		connectionFactory.setHost(host);
		// 设置服务器端口号
		connectionFactory.setPort(port);
		// 设置虚拟主机(相当于数据库中的库)
		connectionFactory.setVirtualHost(virtualHost);
		// 设置用户名
		connectionFactory.setUsername(userName);
		// 设置密码
		connectionFactory.setPassword(passWord);
		// 网络异常自动连接恢复
		connectionFactory.setAutomaticRecoveryEnabled(true);
		/**
		 * 如果由于异常而导致恢复失败（例如，RabbitMQ节点仍然无法访问），则会在固定的时间间隔（默认值为5秒）后重试。间隔可以配置：
		 * 每10秒尝试重试连接一次
		 */
		connectionFactory.setNetworkRecoveryInterval(10000);

		// 设置ConnectionFactory属性信息
		Map<String, Object> connectionFactoryPropertiesMap = new HashMap<>();
		connectionFactoryPropertiesMap.put("principal", "duleilewuhen@sina");
		connectionFactoryPropertiesMap.put("description", "初识RabbitMq");
		connectionFactoryPropertiesMap.put("emailAddress", "duleilewuhen@sina.com");

		// 设置客户端属性
		connectionFactory.setClientProperties(connectionFactoryPropertiesMap);

		return connectionFactory;
	}
}