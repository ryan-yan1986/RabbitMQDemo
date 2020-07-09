package com.idowran.publish;

import com.idowran.utils.ConnextionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class PublishRecver2 {
    private final static String EXCHANGE_NAME = "testExchange"; // 交换机的名字
    private final static String QUEUE = "textPubQueue2";

    public static void main(String[] args) throws Exception{
        // 获取连接, 创建通道
        Connection connection = ConnextionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 绑定队列
        channel.queueDeclare(QUEUE, false, false, false, null);
        // 将队列绑定到交换机
        channel.queueBind(QUEUE, EXCHANGE_NAME, "");
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
