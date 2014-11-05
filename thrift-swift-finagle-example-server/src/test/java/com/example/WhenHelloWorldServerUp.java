package com.example;

import com.example.api.HelloWorldService;
import com.example.api.InvalidUserException;
import com.example.api.User;
import com.example.client.HelloWorldClientFactory;
import com.twitter.util.Await;
import com.twitter.util.Future;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class WhenHelloWorldServerUp {
    private HelloWorldService helloWorldclient;

    @BeforeClass
    public void setUp() throws Exception {
        helloWorldclient = new HelloWorldClientFactory().getClient();
    }

    @Test
    public void userNameReturnedCorrectly() throws Exception {
        User user = new User(1, "name");
        Future<String> actual = helloWorldclient.sayHello(user);
        String result = Await.result(actual);
        Assert.assertEquals(result, "Hello " + user);
    }

    @Test(expectedExceptions = InvalidUserException.class, expectedExceptionsMessageRegExp = "Invalid User")
    public void exceptionThrownOnEmptyUserName() throws Exception {
        User user = new User(1, "");
        Future<String> actual = helloWorldclient.sayHello(user);
        Await.result(actual);
    }
}
