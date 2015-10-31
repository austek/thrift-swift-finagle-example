package com.example.server.config;

import com.example.config.Config;
import com.example.config.TracingConfig;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FinagleServerConfig extends Config {

    @JsonProperty("server")
    public ServerConfig serverConfig;

    @JsonProperty("tracing")
    public TracingConfig tracingConfig;
}
