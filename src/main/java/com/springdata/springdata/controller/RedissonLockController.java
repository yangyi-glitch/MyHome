package com.springdata.springdata.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
 * 测试锁
 */

@Api(tags = "测试锁")
@RequestMapping("/Lock")
@RestController
public class RedissonLockController {
    @Autowired
    private RedissonClient redissonClient;
    private RLock lock;

    @ApiOperation("redis可重入锁")
    @PostMapping("/testRedisLock")
    public synchronized String saveUser(@ApiParam("拿锁人") @RequestParam("name") String name) {
        lock = redissonClient.getLock(name);
        boolean b = lock.tryLock();
        if (!b) {
            System.out.println("main没有获取到");
            return name + "获取锁失败";
        }
        try {
            System.out.println("main获取到锁");
            method1();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("释放锁main");
            lock.unlock();
        }
        return name + "获取锁成功";
    }

    private void method1() {
        boolean b = lock.tryLock();
        if (!b) {
            System.out.println("method1没有获取到");
            return;
        }
        try {
            System.out.println("method1获取到锁");
            method2();
            return;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("释放锁method1");
            lock.unlock();
        }
    }

    private void method2() {
        boolean b = lock.tryLock();
        if (!b) {
            System.out.println("method2没有获取到");
            return;
        }
        try {
            System.out.println("method2获取到锁");
            return;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("释放锁method2");
            lock.unlock();
        }
    }
}
