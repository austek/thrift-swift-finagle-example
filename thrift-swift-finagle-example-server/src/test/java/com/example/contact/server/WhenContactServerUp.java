package com.example.contact.server;

import com.example.config.ClientConfig;
import com.example.contact.client.CloseableClient;
import com.example.contact.client.ContactClientFactory;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class WhenContactServerUp {
    private CloseableClient client;
    private ContactServer server;

    @BeforeClass
    public void setUpClass() throws Exception {
        server = new ContactServer();
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
        ClientConfig clientConfig = null;
        client = new ContactClientFactory(clientConfig).getClient();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        client.close();
    }
//
//    @Test
//    public void userNameReturnedCorrectly() throws Exception {
//        Contact contact = new Contact(1, "name", surname, number);
//        Future<String> actual = client.get().sayHello(contact);
//        String result = Await.result(actual);
//        Assert.assertEquals(result, "Hello " + contact);
//    }
//
//    @Test(expectedExceptions = ContactNotFoundException.class, expectedExceptionsMessageRegExp = "Invalid Contact")
//    public void exceptionThrownOnEmptyUserName() throws Exception {
//        Contact contact = new Contact(1, "", surname, number);
//        Future<String> actual = client.get().sayHello(contact);
//        Await.result(actual);
//    }
}
