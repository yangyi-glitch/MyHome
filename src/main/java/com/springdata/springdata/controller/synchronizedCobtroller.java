package com.springdata.springdata.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "测试synchronized锁")
@RequestMapping("/Lock")
@RestController
public class synchronizedCobtroller {
    @ApiOperation("synchronized可重入锁")
    @PostMapping("/synchronizedTest")
    public synchronized void synchronizedTest() {
            System.out.println("main获取到锁");
            method1();
    }

    private synchronized void method1() {
            System.out.println("method1获取到锁");
            method2();
    }

    private synchronized void method2() {
            System.out.println("method2获取到锁");
    }
}
