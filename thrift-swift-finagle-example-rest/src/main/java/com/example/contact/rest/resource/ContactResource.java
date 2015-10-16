package com.example.contact.rest.resource;

import com.example.contact.client.CloseableClient;
import com.github.kristofa.brave.ServerTracer;

public class ContactResource {

    private CloseableClient client;

    private final ServerTracer serverTracer;

    public ContactResource(ServerTracer serverTracer) {
        this.serverTracer = serverTracer;
    }
}
