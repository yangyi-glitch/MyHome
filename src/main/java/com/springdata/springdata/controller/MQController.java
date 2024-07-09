package com.springdata.springdata.controller;


import com.springdata.springdata.dto.CustDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Api(tags = "MQ练习")
@RequestMapping("/MqSend")
@RestController
public class MQController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ApiOperation("直接队列发送")
    @PostMapping("/queueRevice")
    public void queueRevice(@ApiParam("保存") @RequestBody CustDTO custDTO) {
        String queueName = "simple.queue";
        Map<String,String> map = new HashMap<>();
        map.put("name",custDTO.getCustName());
        map.put("address",custDTO.getCustAddress());
        rabbitTemplate.convertAndSend(queueName, map);
    }

    /**
     * fanout交换机会发送到每个绑定的队列
     * 每个监听者都会接收到消息
     */
    @ApiOperation("使用fanout交换机路由队列发送")
    @PostMapping("/fanoutExchangeRevice")
    public void fanoutExchangeRevice(@ApiParam("保存") @RequestBody CustDTO custDTO) {
        String exchangeName = "yangyi.fanout";
        Map<String,String> map = new HashMap<>();
        map.put("name",custDTO.getCustName());
        map.put("address",custDTO.getCustAddress());
        rabbitTemplate.convertAndSend(exchangeName,"",map);
    }
    /**
     * direct交换机会根据路由来发送消息到队列
     * direct路由只能指定一个key
     */
    @ApiOperation("使用direct交换机路由队列发送")
    @PostMapping("/directExchangeRevice")
    public void directExchangeRevice(@ApiParam("红/蓝") @RequestParam String color) {
        String exchangeName = "yangyi.direct";
        String key = null;
        if (color.equals("红")){key = "red";}else if (color.equals("蓝")){key = "blue";}else {return;}
        rabbitTemplate.convertAndSend(exchangeName,key,color);
    }
    /**
     * topic交换机会根据路由来发送消息到队列
     * topic路由可以指定多个单词以‘.’隔开
     * ‘#’代表0或多个单词
     * ‘*’代表一个单词
     */
    @ApiOperation("topic-给china.#发送")
    @PostMapping("/topicExchangeRevice1")
    public void topicExchangeRevice1() {
        String exchangeName = "yangyi.toptic";
        String mes = "中国-天气和新闻";
        rabbitTemplate.convertAndSend(exchangeName,"china.eat",mes);
    }

    @ApiOperation("topic-给japan.#发送")
    @PostMapping("/topicExchangeRevice2")
    public void topicExchangeRevice2() {
        String exchangeName = "yangyi.toptic";
        String mes = "日本-天气和新闻";
        rabbitTemplate.convertAndSend(exchangeName,"japan.news",mes);
    }

    @ApiOperation("给自定义交换机发送")
    @PostMapping("/mySelfDefined")
    public void mySelfDefined() {
        String exchangeName = "MySelfDefined.exchange";
        String mes = "自定义";
        rabbitTemplate.convertAndSend(exchangeName,"defind",mes);
    }
}
