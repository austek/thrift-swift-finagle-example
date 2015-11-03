package com.github.rojanu.contact.rest.resource;

import com.codahale.metrics.annotation.Timed;
import com.github.kristofa.brave.ServerTracer;
import com.github.rojanu.client.CloseableClient;
import com.github.rojanu.config.client.ClientConfig;
import com.github.rojanu.contact.api.ContactService;
import com.github.rojanu.contact.api.model.Contact;
import com.github.rojanu.contact.client.ContactClientFactory;
import com.github.rojanu.contact.rest.core.ExampleResponse;
import com.github.rojanu.contact.rest.model.RestContactRequest;
import com.twitter.util.Await;
import com.twitter.util.Future;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

    private CloseableClient<ContactService> client;

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
            e.printStackTrace();
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
    @Path("/{id}")
    public Response getContact(@PathParam("id") String id) {
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

    @GET
    @Timed
    public Response getContact() {
        try {
            Future<List<Contact>> contactFuture = client.get().getAll();
            List<Contact> contacts = Await.result(contactFuture);
           return Response.ok(contacts).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    new ExampleResponse(
                            ContactService.ERROR_CODE_CONTACT_NOT_FOUND,
                            e.getMessage()
                    )
            ).build();
        }
    }

    @DELETE
    @Timed
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") String id) {
        try {
            Future<String> contactFuture = client.get().delete(id);
            Await.result(contactFuture);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    new ExampleResponse(
                            ContactService.ERROR_CODE_CONTACT_NOT_FOUND,
                            e.getMessage()
                    )
            ).build();
        }
    }

    @PUT
    @Timed
    @Path("/{id}")
    public Response updateContact(@PathParam("id") String id, RestContactRequest restContactRequest) {
        try {
            Future<Contact> contactFuture = client.get().update(id, RestContactRequest.to(restContactRequest));
            Contact updateContact = Await.result(contactFuture);
            return Response.ok(updateContact).build();
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
