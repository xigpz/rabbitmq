package com.example.rabbitmqdemo.web;


import com.example.rabbitmqdemo.entity.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用于测试的控制类
 * Created by yanggm
 */
@RestController
public class SendMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * http://localhost:8080/send?message=hello
     *
     * @param message
     * @return
     */
    @RequestMapping("/send")
    public String sendMQ(String message) {
        //CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //rabbitTemplate.convertAndSend("my-queue", (Object) message,correlationData);
        rabbitTemplate.convertAndSend("my-queue",message);
        return "OK!Message send success!";
    }

    /**
     * http://localhost:8080/send/user
     *
     * @return
     */
    @RequestMapping("/send/user")
    public String sendUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("yanggm");
        user.setPassword("123456");
        rabbitTemplate.convertAndSend("my-user", user);
        return "OK!User send success!";
    }

    /**
     * http://localhost:8080/send/topicTest
     *
     * @return
     */
    @RequestMapping("/send/topicTest")
    public String sendTopic() {
        String msg1 = "I am topic.mesaage msg======";
        System.out.println("sender1 : " + msg1);
        this.rabbitTemplate.convertAndSend("exchange", "topic.message", msg1);

        String msg2 = "I am topic.mesaages msg########";
        System.out.println("sender2 : " + msg2);
        this.rabbitTemplate.convertAndSend("exchange", "topic.messages", msg2);
        return "OK!topic send success!";
    }

    /**
     * http://localhost:8080/send/fanoutTest
     *
     * @return
     */
    @RequestMapping("/send/fanoutTest")
    public String send() {
        String msgString="fanoutSender :hello ygm";
        System.out.println(msgString);
        this.rabbitTemplate.convertAndSend("fanoutExchange","abcd.ee", msgString);
        return "OK!fanout send success!";
    }

}