package com.idowran.topic;

import com.idowran.utils.ConnextionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class TopicRecver2 {
    private final static String EXCHANGE_NAME = "testTopic"; // 交换机的名字
    private final static String QUEUE = "textTopicQueue2";

    public static void main(String[] args) throws Exception{
        // 获取连接, 创建通道
        Connection connection = ConnextionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 绑定队列
        channel.queueDeclare(QUEUE, false, false, false, null);
        // 将队列绑定到交换机, 第三个参数key1 绑定交换机的时候会指定一个标记, 和它一样的标记的消息才会被当前消费者收到
        channel.queueBind(QUEUE, EXCHANGE_NAME, "key.#");
        // 如果要接收多个标记的消息, 只需要再次执行一次绑定
        channel.queueBind(QUEUE, EXCHANGE_NAME, "abc.#");
        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 获取消息, 如果没有消息, 会一直等待
                System.out.println("消费者2:" + new String(body));
                // 确认消息
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // 接收消息
//      第二个参数, 不自动确认
        channel.basicConsume(QUEUE, false, consumer);
    }
}
