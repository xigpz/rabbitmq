package com.example.rabbitmqdemo.consumer;



import com.example.rabbitmqdemo.entity.User;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Created by yanggm
 */
@Component
public class ReceiveDirect {

    @RabbitListener(queues = "my-queue")
    public void receiveMessage(Message message, Channel channel){
        String messageRec = new String(message.getBody());
        System.out.println("接收到的字符串消息是 => " + messageRec);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "my-user")
    public void receiveObject(User user,Message message,Channel channel){
        System.out.println("------ 接收实体对象 ------");
        System.out.println("接收到的实体对象是 => " + user);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}