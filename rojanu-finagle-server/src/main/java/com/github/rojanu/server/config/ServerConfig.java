package com.github.rojanu.server.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.rojanu.config.SslConfig;

public class ServerConfig {
    public String name;
    public Integer port;
    public Integer adminPort;

    @JsonProperty("ssl")
    public SslConfig sslConfig;
}
