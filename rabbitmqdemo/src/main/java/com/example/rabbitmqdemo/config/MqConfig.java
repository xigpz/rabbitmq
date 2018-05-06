package com.example.rabbitmqdemo.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 特别说明：如果在配置文件中声明了 Queue 对象，就不用在控制台创建队列了
 * Created by yanggm
 */
@Configuration
public class MqConfig {
    /**
     * 声明接收字符串的队列
     *
     * @return
     */
    @Bean
    public Queue stringQueue() {
        return new Queue("my-queue",true, false, false);
    }

    /**
     * 声明接收 User 对象的队列
     *
     * @return
     */
    @Bean
    public Queue userQueue() {
        return new Queue("my-user");
    }

    /**
     * 声明用来测试top模式的队列1
     *
     * @return
     */
    @Bean
    public Queue queueMessage() {
        return new Queue("topic.message");
    }

    /**
     * 声明用来测试top模式的队列2
     *
     * @return
     */
    @Bean
    public Queue queueMessages() {
        return new Queue("topic.messages");
    }

    /**
     * 声明交换机
     * @return
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }

    /**
     * 将队列topic.message与exchange绑定，binding_key为topic.message,就是完全匹配
     * @param queueMessage
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    /**
     * 将队列topic.messages与exchange绑定，binding_key为topic.#,模糊匹配
     * @param queueMessages
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }

    //===============以下是验证Fanout Exchange的队列==========
    @Bean
    public Queue AMessage() {
        return new Queue("fanout.A");
    }

    @Bean
    public Queue BMessage() {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue CMessage() {
        return new Queue("fanout.C");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingExchangeA(Queue AMessage,FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }
    //===============以上是验证Fanout Exchange的队列==========


}
