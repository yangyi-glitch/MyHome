package com.springdata.springdata.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "MQ练习")
@RequestMapping("/MqSend")
@RestController
public class MQController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ApiOperation("发送消息")
    @PostMapping("/MqSendMassage")
    public void MqSendMassage(@ApiParam("用户id集合") @RequestParam("massage") String massage) {
        String queueName = "simple.queue";
        Map<String, String> map = new HashMap<>();
        map.put("name", massage);
        map.put("age", "23");
        map.put("phone", "123456");
        map.put("address", "北京");
        rabbitTemplate.convertAndSend(queueName, map);
    }
}
