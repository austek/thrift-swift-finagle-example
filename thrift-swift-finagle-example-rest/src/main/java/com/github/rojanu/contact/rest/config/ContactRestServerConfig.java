package com.github.rojanu.contact.rest.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.rojanu.config.BraveTraceConfiguration;
import com.github.rojanu.config.client.ClientConfig;

import io.dropwizard.Configuration;

public class ContactRestServerConfig  extends Configuration {
    @JsonProperty("tracing")
    private BraveTraceConfiguration braveTraceConfiguration = new BraveTraceConfiguration();

    public BraveTraceConfiguration getBraveTraceConfiguration() {
        return braveTraceConfiguration;
    }

    public void setRequestTrackerConfiguration(BraveTraceConfiguration configuration) {
        this.braveTraceConfiguration = configuration;
    }

    public ClientConfig client;
}
