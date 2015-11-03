package com.github.rojanu.contact.rest.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.rojanu.config.TracingConfig;
import com.github.rojanu.config.client.ClientConfig;

import io.dropwizard.Configuration;

public class ContactRestServerConfig  extends Configuration {
    @JsonProperty("tracing")
    public TracingConfig tracingConfig;

    public ClientConfig client;
}
