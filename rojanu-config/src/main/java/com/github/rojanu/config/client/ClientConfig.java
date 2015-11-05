package com.github.rojanu.config.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.rojanu.config.Config;
import com.github.rojanu.config.TracingConfig;

public class ClientConfig extends Config {
    @JsonProperty("server")
    public ServerConfig serverConfig;
    @JsonProperty("tracing")
    public TracingConfig tracingConfig;
    @JsonProperty("use-jit-server")
    public Boolean useJustInTimeServer = false;

    public static class ServerConfig {
        public String hosts;
    }
}
