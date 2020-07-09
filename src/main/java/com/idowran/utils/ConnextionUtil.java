package com.idowran.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 创建连接的工具类
 */
public class ConnextionUtil {

    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("183.61.16.156");
        factory.setPort(5672);
        factory.setUsername("test");
        factory.setPassword("admin");
        factory.setVirtualHost("/test");
        return factory.newConnection();
    }
}
