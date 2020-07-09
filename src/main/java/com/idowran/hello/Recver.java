package com.idowran.hello;

import com.idowran.utils.ConnextionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recver {
    private final static String QUEUE = "testHello"; // 队列的名字

    public static void main(String[] args) throws Exception{
        // 获取连接, 创建通道
        Connection connection = ConnextionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 绑定队列
        channel.queueDeclare(QUEUE, false, false, false, null);

        // 接收消息
//      第二个参数, 自动确认
        channel.basicConsume(QUEUE, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 获取消息, 如果没有消息, 会一直等待
                String routingKey   = envelope.getRoutingKey();
                String contentType  = properties.getContentType();
                long deliveryTag    = envelope.getDeliveryTag();
                System.out.println(new String(body));
            }
        });
    }
}
