package com.example.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TracingConfig {
    public String server;
    public Integer port;
    @JsonProperty("sample-rate")
    public Float sampleRate;
}
