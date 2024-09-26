package com.springdata.springdata;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringdataApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringdataApplication.class, args);
    }

    @Bean
    public MessageConverter jacksonMessageConverter(){
        Jackson2JsonMessageConverter jjcc = new Jackson2JsonMessageConverter();
        jjcc.setCreateMessageIds(true);
        return jjcc;
    }
}
