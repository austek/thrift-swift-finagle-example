package com.example.server;

import com.example.api.HelloWorldService;
import com.twitter.finagle.builder.ServerBuilder;
import com.twitter.finagle.builder.ServerConfig;
import com.twitter.finagle.exp.swift.SwiftService;
import com.twitter.finagle.thrift.ThriftServerFramedCodec;

import java.net.InetSocketAddress;

public class HelloWorldServer {

    public HelloWorldServer() {
        HelloWorldService impl = new HelloWorldServiceImpl();
        int port = 9510;
        System.out.println("Starting server:"+
        "\n\tPort: "+ port);
        InetSocketAddress address = new InetSocketAddress(port);
        ServerBuilder<byte[], byte[], ServerConfig.Yes, ServerConfig.Yes, ServerConfig.Yes> serverBuilder = ServerBuilder
                .get()
                .codec(ThriftServerFramedCodec.get())
                .bindTo(address)
                .name("thrift-swift-finagle-example")
                .maxConcurrentRequests(10);

        SwiftService swiftService = new SwiftService(impl);
        serverBuilder.unsafeBuild(swiftService);
    }

    public static void main(String[] args) {
        new HelloWorldServer();
    }
}
