package org.dllwh.rabbitmq.example.work;

import com.rabbitmq.client.*;
import org.dllwh.rabbitmq.util.RabbitHelper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 把今天最好的表现当作明天最新的起点．．～
 * <p>
 * Today the best performance as tomorrow newest starter!
 *
 * @类描述: TODO(这里用一句话描述这个类的作用)
 * @author: <a href="mailto:duleilewuhen@sina.com">独泪了无痕</a>
 * @创建时间: 2021-03-08 14:59
 * @版本: V 1.0.1
 * @since: JDK 1.8
 * @see <a href="">TODO(连接内容简介)</a>
 */
public final class RabbitCustomer {
    private static String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws Exception {
        customerOne();
        customerTwo();
    }

    /**
     * 消费者 1
     * @throws Exception
     */
    public static void customerOne() throws Exception {
        Connection conn = RabbitHelper.getConnection("");
        Channel channel = conn.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        System.out.println("正在等待接收消息...");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                LocalDateTime localDateTime = LocalDateTime.now();
                String format4 = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                System.out.println("【消费1】："+ format4 + "收到消息: " + new String(body, "UTF-8"));
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

    /**
     * 消费者 2
     * @throws Exception
     */
    public static void customerTwo() throws Exception {
        Connection conn = RabbitHelper.getConnection("");
        Channel channel = conn.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        System.out.println("正在等待接收消息...");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                LocalDateTime localDateTime = LocalDateTime.now();
                String format4 = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                System.out.println("【消费2】："+ format4 + "收到消息: " + new String(body, "UTF-8"));
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
