package com.example;

import com.example.api.model.Contact;
import com.example.api.ContactNotFoundException;
import com.example.client.CloseableClient;
import com.example.client.HelloWorldClientFactory;
import com.example.server.HelloWorldServer;
import com.example.server.ContactServiceImpl;
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
        client = new HelloWorldClientFactory().getClient(new ContactServiceImpl());
    }

    @AfterMethod
    public void tearDown() throws Exception {
        client.close();
    }

    @Test
    public void userNameReturnedCorrectly() throws Exception {
        Contact contact = new Contact(1, "name", surname, number);
        Future<String> actual = client.get().sayHello(contact);
        String result = Await.result(actual);
        Assert.assertEquals(result, "Hello " + contact);
    }

    @Test(expectedExceptions = ContactNotFoundException.class, expectedExceptionsMessageRegExp = "Invalid Contact")
    public void exceptionThrownOnEmptyUserName() throws Exception {
        Contact contact = new Contact(1, "", surname, number);
        Future<String> actual = client.get().sayHello(contact);
        Await.result(actual);
    }
}
