package com.example.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientConfig extends Config {
    @JsonProperty("server")
    public ServerConfig serverConfig;
    @JsonProperty("tracing")
    public TracingConfig tracingConfig;
    @JsonProperty("use-embedded-server")
    public Boolean useEmbeddedServer = false;

    public static class ServerConfig {
        public String hosts;
        public String version;
        public String trustStorePath;
        public String trustStorePassword;
    }
}
