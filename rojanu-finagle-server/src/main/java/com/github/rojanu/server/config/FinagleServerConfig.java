package com.github.rojanu.server.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.rojanu.config.Config;
import com.github.rojanu.config.TracingConfig;

public class FinagleServerConfig extends Config {

    @JsonProperty("server")
    public ServerConfig serverConfig;

    @JsonProperty("tracing")
    public TracingConfig tracingConfig;
}
