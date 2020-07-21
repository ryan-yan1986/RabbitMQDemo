package com.idowran.publish;

import com.idowran.utils.ConnextionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class PublishSender {

    private final static String EXCHANGE_NAME = "testExchange"; // 交换机的名字

    public static void main(String[] args) throws Exception{
        // 获取连接
        Connection connection = ConnextionUtil.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 定义一个交换机, fanout 发布订阅模式
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        
        // 发送内容
        // 因为交换机是没有保存消息的功能的, 所以如果没有消费者, 消息会丢失
        channel.basicPublish(EXCHANGE_NAME, "", null, "发布/订阅模式的消息".getBytes());

        // 关闭通道和连接
        channel.close();
        connection.close();
    }
}
