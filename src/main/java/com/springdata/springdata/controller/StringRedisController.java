package com.springdata.springdata.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Api(tags = "Redis接口")
@RequestMapping("/redis")
@RestController
public class StringRedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @ApiOperation("生产者")
    @PostMapping("/QueuePush")
    public void redisQueuePush(@ApiParam("value") @RequestParam("value") String value) {
        String key = "queue";
        Long aLong = stringRedisTemplate.opsForList().leftPush(key, value);
    }

    @ApiOperation("消费者")
    @PostMapping("/QueuePop")
    public void redisQueuePop() {
        String key = "queue";
        String s = null;
        while (true){
            s = stringRedisTemplate.opsForList().leftPop(key);
            if (s != null){
                System.out.println(s);
            }
        }
    }

    @ApiOperation("生产排行榜")
    @PostMapping("/ZsetPush")
    public void redisZsetPush(@ApiParam("value") @RequestParam("value") String value,
                                @ApiParam("钱") @RequestParam("money") Integer money) {
        String key = "Zset";
        stringRedisTemplate.opsForZSet().add(key,value,money);
    }

    @ApiOperation("排行榜前5名")
    @PostMapping("/ZsetPop")
    public Map<String,Double> redisZsetPop() {
        Map<String,Double> map = new LinkedHashMap<>();
        String key = "Zset";
        Set<String> range = stringRedisTemplate.opsForZSet().reverseRange(key, 0, 4);
        for (String s : range) {
            Double score = stringRedisTemplate.opsForZSet().score(key, s);
            map.put("姓名："+s+"--->金额",score);
        }
        return map;
    }

    @ApiOperation("导出排行榜全部")
    @PostMapping("/ZsetPopAll")
    public Map<String,Double> redisZsetPopAll() {
        Map<String,Double> map = new LinkedHashMap<>();
        String key = "Zset";
        Long aLong = stringRedisTemplate.opsForZSet().zCard(key);
        Set<String> range = stringRedisTemplate.opsForZSet().reverseRange(key, 0, aLong);
        for (String s : range) {
            Double score = stringRedisTemplate.opsForZSet().score(key, s);
            map.put("姓名："+s+"--->金额",score);
        }
        return map;
    }


    @ApiOperation("setNX")
    @PostMapping("/setNX")
    public boolean setNX(@ApiParam("value") @RequestParam("value") String value,
                              @ApiParam("过期时间") @RequestParam("expireTime") Long expireTime) {
        /*Instant instant = LocalDateTime.now().plusSeconds(expireTime).toInstant(ZoneOffset.MAX);
        long l = instant.toEpochMilli();*/
        Boolean falg = stringRedisTemplate.opsForValue().setIfAbsent("Lock",value,10L, TimeUnit.SECONDS);
        return falg;
    }
}
