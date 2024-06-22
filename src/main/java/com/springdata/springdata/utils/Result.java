package com.springdata.springdata.utils;

import lombok.Data;

@Data
public class Result<T> {
    private final int code = 200;
    private T date;
    private String msg;
}
