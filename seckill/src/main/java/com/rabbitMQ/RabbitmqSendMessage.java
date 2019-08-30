package com.rabbitMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitmqSendMessage {

    @Autowired
    private RabbitTemplate   rt;

    private final String QUEEN_NAME = "MIAOSHA";

    /**
     * 发送消息
     * @param msg
     */
    public void send(String msg)
    {
        rt.convertAndSend(QUEEN_NAME,msg);
    }


}
