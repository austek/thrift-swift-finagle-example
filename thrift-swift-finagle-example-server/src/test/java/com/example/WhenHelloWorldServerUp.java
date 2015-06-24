package com.example;

import com.example.api.InvalidUserException;
import com.example.api.User;
import com.example.client.CloseableClient;
import com.example.client.HelloWorldClientFactory;
import com.example.server.HelloWorldServer;
import com.example.server.HelloWorldServiceImpl;
import com.twitter.util.Await;
import com.twitter.util.Future;
import org.testng.Assert;
import org.testng.annotations.*;

public class WhenHelloWorldServerUp {
    private CloseableClient client;
    private HelloWorldServer server;

    @BeforeClass
    public void setUpClass() throws Exception {
        server = new HelloWorldServer();
        new Thread(){
            @Override
            public void run() {
                try {
                    server.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @AfterClass
    public void tearDownClass() throws Exception {
        server.stop();
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        client = new HelloWorldClientFactory().getClient(new HelloWorldServiceImpl());
    }

    @AfterMethod
    public void tearDown() throws Exception {
        client.close();
    }

    @Test
    public void userNameReturnedCorrectly() throws Exception {
        User user = new User(1, "name");
        Future<String> actual = client.get().sayHello(user);
        String result = Await.result(actual);
        Assert.assertEquals(result, "Hello " + user);
    }

    @Test(expectedExceptions = InvalidUserException.class, expectedExceptionsMessageRegExp = "Invalid User")
    public void exceptionThrownOnEmptyUserName() throws Exception {
        User user = new User(1, "");
        Future<String> actual = client.get().sayHello(user);
        Await.result(actual);
    }
}
