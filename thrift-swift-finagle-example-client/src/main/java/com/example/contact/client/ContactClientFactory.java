package com.example.contact.client;

import com.example.contact.api.ContactService;
import com.twitter.finagle.Service;
import com.twitter.finagle.Thrift;
import com.twitter.finagle.thrift.ThriftClientRequest;

public class ContactClientFactory {

    private CloseableClient client;

    public synchronized CloseableClient getClient(ContactService service) {
        if (client != null) {
            return client;
        }
        Service<ThriftClientRequest, byte[]> thriftClientRequestService = Thrift.newClient("127.0.0.1:9510").toService();

        return new CloseableClient(thriftClientRequestService, service);
    }
}
