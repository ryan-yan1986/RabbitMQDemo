package com.idowran.topic;

import com.idowran.utils.ConnextionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class TopicSender {

    private final static String EXCHANGE_NAME = "testTopic"; // 交换机的名字

    public static void main(String[] args) throws Exception{
        // 获取连接
        Connection connection = ConnextionUtil.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 定义一个交换机, topic 主题模式
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        
        // 发送内容
        channel.basicPublish(EXCHANGE_NAME, "key.1", null, "topic消息 key.1".getBytes());
        channel.basicPublish(EXCHANGE_NAME, "key.1.2", null, "topic消息 key.1.2".getBytes());
        channel.basicPublish(EXCHANGE_NAME, "abc.1.2", null, "topic消息 abc.1.2".getBytes());
        
        // 关闭通道和连接
        channel.close();
        connection.close();
    }
}
