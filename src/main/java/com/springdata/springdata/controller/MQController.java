package com.springdata.springdata.controller;


import cn.hutool.core.lang.UUID;
import com.springdata.springdata.dto.CustDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Api(tags = "MQ练习")
@RequestMapping("/MqSend")
@RestController
public class MQController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    Logger log = Logger.getLogger("com.springdata.springdata");


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
        rabbitTemplate.convertAndSend(exchangeName,"",custDTO);
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

    /**
     * 生产者消息确认的几种返回值情况
     * 消息投递MQ，但是路由失败。会return路由异常，返回ACK
     * 临时消息投递MQ，并入队成功，返回ACK
     * 持久化消息投递ACK，并入队成功，返回ACK
     * 其他情况返回NACK，告知投递失败
     */
    @ApiOperation("生产者消息确认（判断MQ是否收到消息）")
    @PostMapping("/confirmCallBack")
    public void confirmCallBack() {
        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
        cd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("消息回调失败");
            }

            @Override
            public void onSuccess(CorrelationData.Confirm result) {
                System.out.println("收到confirm callback回执");
                if (result.isAck()){
                    log.log(Level.WARNING,"消息发送成功");
                    System.out.println("消息发送成功，收到ACK");
                }else{
                    System.out.println("消息发送失败，收到NACK，原因{}"+result.getReason());
                }
            }
        });
        rabbitTemplate.convertAndSend("yangyi.direct11","red","生产者消息确认",cd);
    }
}
