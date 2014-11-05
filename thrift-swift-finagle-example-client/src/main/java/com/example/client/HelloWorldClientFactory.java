package com.example.client;

import com.example.api.HelloWorldService;
import com.twitter.finagle.Service;
import com.twitter.finagle.builder.ClientBuilder;
import com.twitter.finagle.exp.swift.SwiftProxy;
import com.twitter.finagle.thrift.ThriftClientFramedCodec;
import com.twitter.finagle.thrift.ThriftClientRequest;
import com.twitter.util.Duration;

import java.util.concurrent.TimeUnit;

public class HelloWorldClientFactory {
    private HelloWorldService client;

    public synchronized HelloWorldService getClient() {
        if (client != null) {
            return client;
        }
        Service<ThriftClientRequest, byte[]> transport = ClientBuilder.safeBuild(
                ClientBuilder.get()
                        .codec(ThriftClientFramedCodec.get())
                        .hostConnectionLimit(10)
                        .hosts("127.0.0.1:9510")
                        .tcpConnectTimeout(Duration.apply(20, TimeUnit.SECONDS))
                        .connectionTimeout(Duration.apply(30, TimeUnit.SECONDS))
        );
        client = (HelloWorldService) SwiftProxy.newJavaClient(transport, HelloWorldService.class);
        return client;
    }
}
