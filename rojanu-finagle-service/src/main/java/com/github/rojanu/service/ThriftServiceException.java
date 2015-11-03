package com.github.rojanu.service;

import com.facebook.swift.codec.ThriftConstructor;
import com.facebook.swift.codec.ThriftStruct;

@ThriftStruct
public final class ThriftServiceException extends ThriftException {

    @ThriftConstructor
    public ThriftServiceException(final String code, final String msg) {
        super(code, msg);
    }

}
