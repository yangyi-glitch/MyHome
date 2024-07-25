package com.springdata.springdata.config;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);

        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                System.out.println("收到消息的return callback：【】Message,【】Exchange，【】ReplyCode，【】ReplyText，【】RoutingKey");
                System.out.println(returned.getMessage());
                System.out.println(returned.getExchange());
                System.out.println(returned.getReplyCode());
                System.out.println(returned.getReplyText());
                System.out.println(returned.getRoutingKey());
            }
        });
    }
}
