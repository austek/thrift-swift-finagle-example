package com.github.rojanu.contact.client;

import com.github.rojanu.client.AbstractClientFactory;
import com.github.rojanu.client.CloseableClient;
import com.github.rojanu.config.client.ClientConfig;
import com.github.rojanu.contact.api.ContactService;

public class ContactClientFactory extends AbstractClientFactory<ContactService>{

    public ContactClientFactory(ClientConfig clientConfig) {
        super(clientConfig, ContactService.class);
    }

    public CloseableClient<ContactService> getClient() {
        return getCloseableClient();
    }
}
