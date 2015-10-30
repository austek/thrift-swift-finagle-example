package com.example.contact.client;

import com.example.config.client.ClientConfig;
import com.example.contact.api.ContactService;
import com.twitter.finagle.Service;
import com.twitter.finagle.Thrift;
import com.twitter.finagle.thrift.ThriftClientRequest;

public class ContactClientFactory {

    private CloseableClient client;

    private ClientConfig clientConfig;

    public ContactClientFactory(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    public synchronized CloseableClient getClient() {
        if (client != null) {
            return client;
        }

        Thrift.Client thriftClient = Thrift.client();

        Service<ThriftClientRequest, byte[]> thriftClientRequestService =
                thriftClient.newClient(clientConfig.serverConfig.hosts, "contact-service").toService();

        //ContactService o = (ContactService)SwiftProxy.newJavaClient(thriftClientRequestService, ContactService.class);
        ContactService o = thriftClient.newIface(clientConfig.serverConfig.hosts, "contact-service", ContactService.class);

        return new CloseableClient(thriftClientRequestService, o);
    }
}
