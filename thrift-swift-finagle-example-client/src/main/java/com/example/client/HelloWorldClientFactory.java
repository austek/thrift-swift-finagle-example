package com.example.client;

import com.example.api.ContactService;
import com.twitter.finagle.Service;
import com.twitter.finagle.Thrift;
import com.twitter.finagle.thrift.ThriftClientRequest;

public class HelloWorldClientFactory {
    private CloseableClient client;

    public synchronized CloseableClient getClient(ContactService service) {
        if (client != null) {
            return client;
        }
        Service<ThriftClientRequest, byte[]> thriftClientRequestService = Thrift.newClient("127.0.0.1:9510").toService();

        return new CloseableClient(thriftClientRequestService, service);
    }
}
