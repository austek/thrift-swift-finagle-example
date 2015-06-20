package com.example;

import com.example.api.InvalidUserException;
import com.example.api.User;
import com.example.client.CloseableClient;
import com.example.client.HelloWorldClientFactory;
import com.example.server.HelloWorldServiceImpl;
import com.twitter.util.Await;
import com.twitter.util.Future;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class WhenHelloWorldServerUp {
    private CloseableClient helloWorldclient;

    @BeforeClass
    public void setUp() throws Exception {
        helloWorldclient = new HelloWorldClientFactory().getClient(new HelloWorldServiceImpl());
    }

    @Test
    public void userNameReturnedCorrectly() throws Exception {
        User user = new User(1, "name");
        Future<String> actual = helloWorldclient.get().sayHello(user);
        String result = Await.result(actual);
        Assert.assertEquals(result, "Hello " + user);
    }

    @Test(expectedExceptions = InvalidUserException.class, expectedExceptionsMessageRegExp = "Invalid User")
    public void exceptionThrownOnEmptyUserName() throws Exception {
        User user = new User(1, "");
        Future<String> actual = helloWorldclient.get().sayHello(user);
        Await.result(actual);
    }
}
