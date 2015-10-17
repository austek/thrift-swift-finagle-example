package com.example.contact.rest.resource;

import com.codahale.metrics.annotation.Timed;
import com.example.config.ClientConfig;
import com.example.contact.api.model.ContactRequest;
import com.example.contact.client.CloseableClient;
import com.example.contact.client.ContactClientFactory;
import com.github.kristofa.brave.ServerTracer;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

    private CloseableClient client;

    private final ServerTracer serverTracer;

    public ContactResource(ClientConfig clientConfig, ServerTracer serverTracer) {
        client = new ContactClientFactory(clientConfig).getClient();
        this.serverTracer = serverTracer;
    }

    @POST
    @Timed
    public Response addContact(ContactRequest contactRequest){
        client.get().create(contactRequest);
        return Response.ok().build();
    }
}
