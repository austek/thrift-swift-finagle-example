package com.example.contact.rest.resource;

import com.codahale.metrics.annotation.Timed;
import com.example.config.ClientConfig;
import com.example.contact.api.ContactService;
import com.example.contact.api.model.Contact;
import com.example.contact.client.CloseableClient;
import com.example.contact.client.ContactClientFactory;
import com.example.contact.rest.core.ExampleResponse;
import com.example.contact.rest.model.RestContactRequest;
import com.github.kristofa.brave.ServerTracer;
import com.twitter.util.Await;
import com.twitter.util.Future;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    public Response addContact(RestContactRequest restContactRequest) {
        try {
            Future<Contact> contactFuture = client.get().create(RestContactRequest.to(restContactRequest));
            Contact contact = Await.result(contactFuture);
            return Response.ok(contact).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    new ExampleResponse(
                            ContactService.ERROR_CODE_CONTACT_NOT_FOUND,
                            e.getMessage()
                    )
            ).build();
        }
    }

    @GET
    @Timed
    @Path("{id}")
    public Response getContact(@PathParam("{id}") String id) {
        try {
            Future<Contact> contactFuture = client.get().get(id);
            Contact contact = Await.result(contactFuture);
           return Response.ok(contact).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    new ExampleResponse(
                            ContactService.ERROR_CODE_CONTACT_NOT_FOUND,
                            e.getMessage()
                    )
            ).build();
        }
    }
}
