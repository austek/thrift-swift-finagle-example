package com.example.server.config;

import com.example.config.SslConfig;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerConfig {
    public String name;
    public Integer port;
    public Integer adminPort;

    @JsonProperty("ssl")
    public SslConfig sslConfig;
}
