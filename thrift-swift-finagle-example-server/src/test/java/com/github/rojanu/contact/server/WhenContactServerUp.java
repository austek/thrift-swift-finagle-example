package com.github.rojanu.contact.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.rojanu.client.CloseableClient;
import com.github.rojanu.config.ConfigValidationException;
import com.github.rojanu.config.client.ClientConfig;
import com.github.rojanu.contact.client.ContactClientFactory;
import com.github.rojanu.server.AbstractFinagleServerWithAdminInterface;
import com.github.rojanu.test.util.server.ConfigurableTest;
import com.github.rojanu.test.util.server.JustInTimeServer;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;

public class WhenContactServerUp extends ConfigurableTest<ClientConfig> {
    private CloseableClient client;

    private JustInTimeServer justInTimeServer;

    @BeforeClass
    public void beforeClass() throws IOException, ConfigValidationException {
        ClientConfig clientConfig = loadConfig(new TypeReference<ClientConfig>() {});
        if (clientConfig.useJustInTimeServer) {
            System.out.println("Starting just in time servers");
            AbstractFinagleServerWithAdminInterface server =
                    Server$.MODULE$.runEmbedded("classpath:config/development/contact-server.yaml");
            justInTimeServer = JustInTimeServer.Builder()
                    .server(server)
                    .build()
            ;
            clientConfig = server.getClientConfig(clientConfig);
        }
        client = new ContactClientFactory(clientConfig).getCloseableClient();
    }

    @AfterClass
    public void afterClass() throws IOException {
        if (justInTimeServer != null) {
            System.out.println("Stopping just in time servers");
            justInTimeServer.stopSilently();
        }
        client.close();
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
