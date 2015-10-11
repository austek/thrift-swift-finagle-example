package com.example.contact.rest;

import com.example.contact.rest.config.ContactRestServerConfig;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ContactRestServer extends Application<ContactRestServerConfig> {
    @Override
    public void initialize(Bootstrap<ContactRestServerConfig> bootstrap) {

    }
    @Override
    public void run(ContactRestServerConfig contactRestServerConfig, Environment environment) throws Exception {

    }

    public static void main(String[] args) throws Exception {
        new ContactRestServer().run(args);
    }
}
