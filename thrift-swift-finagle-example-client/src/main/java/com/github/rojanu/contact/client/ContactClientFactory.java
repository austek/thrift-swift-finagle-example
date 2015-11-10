package com.github.rojanu.contact.client;

import com.github.rojanu.client.AbstractClientFactory;
import com.github.rojanu.client.CloseableClient;
import com.github.rojanu.config.client.ClientConfig;
import com.github.rojanu.contact.api.ContactService;
import com.twitter.util.Future;

public class ContactClientFactory extends AbstractClientFactory<ContactService<Future>>{

    public ContactClientFactory(ClientConfig clientConfig) {
        super(clientConfig, ContactService<Future>.class);
    }

    public CloseableClient<ContactService<Future>> getClient() {
        return getCloseableClient();
    }

    public CloseableClient<ContactService<Future>> getClient(String name) {
        return getCloseableClient(name);
    }
}
