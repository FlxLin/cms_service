package com.xuecheng.test.rabbitmq;

import com.xuecheng.test.rabbitmq.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: ServiceProject
 * @description:
 * @author: Linn
 * @create: 2020-02-25 20:08
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer_spring {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void testRabbitmq(){

        String message = "Hello, Linn!";
        /*
        * 1.交换机名称
        * 2.routingKey
        * 3.message
        * */
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM, RabbitmqConfig.QUEUE_INFORM_SMS, message);

    }
}
