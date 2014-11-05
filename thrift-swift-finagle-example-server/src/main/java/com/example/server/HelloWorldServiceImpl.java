package com.example.server;

import com.example.api.HelloWorldService;
import com.example.api.InvalidUserException;
import com.example.api.User;
import com.twitter.util.Future;
import com.twitter.util.Promise;

public class HelloWorldServiceImpl implements HelloWorldService {
    @Override
    public Future<String> sayHello(User user) throws InvalidUserException {
        final Promise<String> promise = new Promise<>();
        if(user == null || user.getName() == null || user.getName().isEmpty()){
            promise.setException(new InvalidUserException());
        }else{
            promise.setValue("Hello "+ user);
        }
        return promise;
    }
}
