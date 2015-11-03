package com.github.rojanu.service;

import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;
import com.twitter.util.Future;

@ThriftService
public interface BasicFinagleService {

    @ThriftMethod
    Future<String> getName();

    @ThriftMethod
    Future<String> getVersion();

    @ThriftMethod
    Future<String> getBuildInfo() throws ThriftServiceException;

}
