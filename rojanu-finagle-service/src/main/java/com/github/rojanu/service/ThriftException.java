package com.github.rojanu.service;

import com.facebook.swift.codec.ThriftField;

public class ThriftException extends Exception {

    private final String code;
    private final String msg;

    public ThriftException(final String code, final String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    @ThriftField(1)
    public String getCode() {
        return code;
    }

    @ThriftField(2)
    public String getMsg() {
        return msg;
    }
}
