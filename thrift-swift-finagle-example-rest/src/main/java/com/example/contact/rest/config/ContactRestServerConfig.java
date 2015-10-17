package com.example.contact.rest.config;

import com.example.config.TracingConfig;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class ContactRestServerConfig  extends Configuration {
    @JsonProperty("tracing")
    public TracingConfig tracing;
}
