package com.idowran.route;

import com.idowran.utils.ConnextionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class RouteSender {

    private final static String EXCHANGE_NAME = "testRoute"; // 交换机的名字

    public static void main(String[] args) throws Exception{
        // 获取连接
        Connection connection = ConnextionUtil.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 定义一个交换机, direct 路由模式
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        
        // 发送内容
        channel.basicPublish(EXCHANGE_NAME, "key1", null, "路由模式的消息1".getBytes());
        channel.basicPublish(EXCHANGE_NAME, "key2", null, "路由模式的消息2".getBytes());
        channel.basicPublish(EXCHANGE_NAME, "key3", null, "路由模式的消息3".getBytes());

        // 关闭通道和连接
        channel.close();
        connection.close();
    }
}
