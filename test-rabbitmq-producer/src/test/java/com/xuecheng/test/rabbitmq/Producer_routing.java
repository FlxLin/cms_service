package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @program: ServiceProject
 * @description:
 * @author: Linn
 * @create: 2020-02-25 11:34
 **/
public class Producer_routing {
    //队列名称
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_ROUTING_INFORM = "exchange_routing_inform";

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
            channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_SMS, true, false, false, null);

            //声明交换机 String exchange, BuiltinExchangeType type
            /**
             * 参数明细
             * 1、交换机名称
             * 2、交换机类型，fanout、topic、direct、headers
             *          fanout:对应工作模式是 Publish/Subscribe模式
             *          direct：对应工作模式是 Routing模式
             *          topic：对应工作模式是 Topics模式
             *          headers：对应工作模式是 Header模式
             */
            channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);

            //交换机和队列绑定String queue, String exchange, String routingKey
            /**
             * 参数明细
             * 1、队列名称
             * 2、交换机名称
             * 3、路由key
             */
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_ROUTING_INFORM,QUEUE_INFORM_EMAIL);
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_ROUTING_INFORM,QUEUE_INFORM_SMS);


            //发送消息
            //参数：String exchange, String routingKey, BasicProperties props， byte[] body
            /*
             *1.exchange : 交换机，如果不指定，则是mq默认交换机
             *2.routingKey ：路由Key，交换机更具路由Key来将消息转发到指定的队列,如果使用默认交换机，则rouingKey为队列名称
             *3.props : 消息属性
             *4.body ：消息类容
             * */
            for (int i = 0; i < 5; i++) {
                String message = "send message to user";
                channel.basicPublish(EXCHANGE_ROUTING_INFORM,QUEUE_INFORM_SMS,null,message.getBytes());
            }
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
