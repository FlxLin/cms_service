package com.xuecheng.test.rabbitmq.mq;

import com.xuecheng.test.rabbitmq.config.RabbitmqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: ServiceProject
 * @description:
 * @author: Linn
 * @create: 2020-02-25 20:32
 **/
@Component
public class ReceiveHandler {

    @RabbitListener(queues = {RabbitmqConfig.QUEUE_INFORM_SMS})
    public void receive_message(Message message){//参数还可以有 channel 去到通道信息
        System.out.println("receive message is :" + message.getBody());
    }
}
