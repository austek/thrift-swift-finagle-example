package com.github.rojanu.contact.rest;

import com.github.kristofa.brave.ServerTracer;
import com.github.rojanu.BraveBundle;
import com.github.rojanu.config.BraveTraceConfiguration;
import com.github.rojanu.contact.rest.config.ContactRestServerConfig;
import com.github.rojanu.contact.rest.resource.ContactResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ContactRestServer extends Application<ContactRestServerConfig> {
    private ServerTracer serverTracer;

    public static void main(String[] args) throws Exception {
        new ContactRestServer().run(args);
    }

    @Override
    public void initialize(Bootstrap<ContactRestServerConfig> bootstrap) {
        BraveBundle<ContactRestServerConfig> braveBundle = new BraveBundle<ContactRestServerConfig>() {
            @Override
            public BraveTraceConfiguration getBraveTraceConfiguration(ContactRestServerConfig contactRestServerConfig) {
                return contactRestServerConfig.getBraveTraceConfiguration();
            }
        };
        bootstrap.addBundle(braveBundle);
        serverTracer = braveBundle.getServerTracer();
    }

    @Override
    public void run(ContactRestServerConfig contactRestServerConfig, Environment environment) throws Exception {
        environment.jersey().register(new ContactResource(contactRestServerConfig.client, serverTracer));
    }
}
