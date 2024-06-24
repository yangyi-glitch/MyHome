package com.springdata.springdata.utils;

import java.time.LocalDateTime;

public class RedisData {
    private LocalDateTime expireTime;
    private Object data;

    public RedisData() {
    }

    public RedisData(LocalDateTime expireTime, Object data) {
        this.expireTime = expireTime;
        this.data = data;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
