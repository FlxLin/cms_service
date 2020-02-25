package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @program: ServiceProject
 * @description:
 * @author: Linn
 * @create: 2020-02-25 12:55
 **/
public class Consumer_subscribe_sms {

    //队列名称
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_FANOUT_INFORM="exchange_fanout_inform";

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
        //建立新的链接
        try {
            //建立新的链接
            Connection connection = factory.newConnection();
            //创建通道
            Channel channel = connection.createChannel();

            //声明队列，如果队列在mq中没有就要创建
            //参数：String queues, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
            /*
             *1.queues : 队列名称
             *2.durable ：是否持久化，如果持久化，mq重启后队列依旧存在
             *3.exclusive : 是否独占链接，如果connect关闭，则自动删除。
             *4.autoDelete ：是否自动删除，exclusive 与autoDelete同时为true，可以创建临时队列
             *5.arguments : 可以设置一个队列的扩展参数
             * */
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
            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);

            //交换机和队列绑定String queue, String exchange, String routingKey
            /**
             * 参数明细
             * 1、队列名称
             * 2、交换机名称
             * 3、路由key
             */
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_FANOUT_INFORM,"");

            //实现消费方法
            DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
                //当接收到消息时候，此方法被调用
                /*
                 * consumerTag: 消费者标签，可选项
                 * envelope：信封，通过envelope
                 *properties：消息属性
                 * body：消息类容
                 * */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    //交换机
                    String exchange = envelope.getExchange();
                    //消息id，可用于确认消息已接收
                    long deliveryTag = envelope.getDeliveryTag();
                    //消息类容
                    String message = new String(body,"utf-8");
                    System.out.println("receive : " + message);
                }
            };

            //监听队列
            //参数：String queue, boolean autoAck, Consumer callback
            /*
             *1.queue: 队列名称
             * autoAck: 自定回复
             *callback: 消费方法，消费者接到消息时候要执行的方法
             * */
            channel.basicConsume(QUEUE_INFORM_SMS,true,defaultConsumer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
