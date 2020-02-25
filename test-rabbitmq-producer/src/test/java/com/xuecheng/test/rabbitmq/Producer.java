package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @program: ServiceProject
 * @description:
 * @author: Linn
 * @create: 2020-02-24 14:52
 **/
public class Producer {
    //队列名称
    private static final String QUEUE = "hello,world";

    public static void main(String[] args) {
        //通过链接工厂，创建新的链接
        ConnectionFactory factory = new ConnectionFactory();
        //服务器ip地址
        factory.setHost("127.0.0.1");
        //端口,mq默认端口5672
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        //设置虚拟机
        factory.setVirtualHost("/");
        try {
            //建立新的链接
            Connection connection = factory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();
            //声明队列，如果队列在mq中没有就要创建
            //参数：String queues, boolean durable, boolean exclusive, boolean var4, Map<String, Object> arguments
            /*
            *1.queues : 队列名称
            *2.durable ：是否持久化，如果持久化，mq重启后队列依旧存在
            *3.exclusive : 是否独占链接，如果connect关闭，则自动删除。
            *4.autoDelete ：是否自动删除，exclusive 与autoDelete同时为true，可以创建临时队列
            *5.arguments : 可以设置一个队列的扩展参数
            * */
            channel.queueDeclare(QUEUE, true, false, false, null);

            //发送消息
            //参数：String exchange, String routingKey, BasicProperties props， byte[] body
            /*
             *1.exchange : 交换机，如果不指定，则是mq默认交换机
             *2.routingKey ：路由Key，交换机更具路由Key来将消息转发到指定的队列,如果使用默认交换机，则rouingKey为队列名称
             *3.props : 消息属性
             *4.body ：消息类容
             * */
            String message = "Hello, Linn !";
            channel.basicPublish("",QUEUE,null,message.getBytes());
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
