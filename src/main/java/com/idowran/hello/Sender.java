package com.idowran.hello;

import com.idowran.utils.ConnextionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sun.istack.internal.localization.NullLocalizable;

public class Sender {

    private final static String QUEUE = "testHello"; // 队列的名字

    public static void main(String[] args) throws Exception{
        // 获取连接
        Connection connection = ConnextionUtil.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 声明队列, 如果队列存在, 则什么都不做, 如果不存在才创建
        // 参数1 队列的名字,
        // 参数2 是否持久化队列, 我们的队列模式是在内存中的, 如果rabbitmq重启会丢失
        // 参数3 是否排外, 1 当我们的连接关闭后是否会自动删除队列, 2 是否私有当天前队列,如果私有了, 其他通道不可以访问当前队列, 如果为true, 一般是一个队列只属于一个消费者
        // 参数4 是否自动删除
        // 参数5 我们的一些其他参数
        channel.queueDeclare(QUEUE, false, false, false, null);
        // 发送内容
        channel.basicPublish("", QUEUE, null, "logId:1,sync:1".getBytes());

        // 关闭通道和连接
        channel.close();
        connection.close();
    }
}
