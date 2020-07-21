package com.idowran.work;

import com.idowran.utils.ConnextionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class WorkRecver1 {
    private final static String QUEUE = "testWork"; // 队列的名字

    public static void main(String[] args) throws Exception{
        // 获取连接, 创建通道
        Connection connection = ConnextionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 绑定队列
        channel.queueDeclare(QUEUE, false, false, false, null);
        
        // 告诉服务器, 在我们没有确认当前消息完成之前, 不要给我发新的消息
        channel.basicQos(1);
        // 接收消息
        // 创建consumer, 集成DefaultConsumer, 并重写handleDelivery方法
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 当我们收到消息的时候, 会调用这个方法
                String routingKey   = envelope.getRoutingKey();
                String contentType  = properties.getContentType();
                long deliveryTag    = envelope.getDeliveryTag();
//                System.out.println("消费者1收到的内容: " + new String(body));
                
                System.out.println("body: " + new String(body));
                System.out.println("routingKey: " + routingKey);
                System.out.println("contentType: " + contentType);
                System.out.println("deliveryTag: " + deliveryTag);
//                try{
//                    Thread.sleep(20);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
                // 手动确认消息已收到, 参数2 false 确认收到消息 true 拒绝收到消息
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

//      第二个参数, false 不自动确认, 所以要程序手动确认
        channel.basicConsume(QUEUE, false, consumer);
    }
}
