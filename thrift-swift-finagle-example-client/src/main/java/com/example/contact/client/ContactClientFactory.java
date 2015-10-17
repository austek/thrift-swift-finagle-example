package com.example.contact.client;

import com.example.config.ClientConfig;
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

    public synchronized CloseableClient getClient(ContactService service) {
        if (client != null) {
            return client;
        }

        Thrift.Client thriftClient = Thrift.client();
        Object iface = thriftClient.newIface(clientConfig.serverConfig.hosts, "contact-service", ContactService.class);

        Service<ThriftClientRequest, byte[]> thriftClientRequestService =
                Thrift.client().newClient(clientConfig.serverConfig.hosts, "contact-service").toService();

        return new CloseableClient(thriftClientRequestService, null);
    }
}
