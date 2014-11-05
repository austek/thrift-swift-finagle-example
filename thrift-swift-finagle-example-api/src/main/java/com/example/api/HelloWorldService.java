package com.example.api;

import com.facebook.swift.service.ThriftException;
import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;
import com.twitter.util.Future;

@ThriftService
public interface HelloWorldService {

    @ThriftMethod(exception = {
            @ThriftException(type = InvalidUserException.class, id=1)
    })
    Future<String> sayHello(User user) throws InvalidUserException;
}
