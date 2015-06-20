package com.example.server;

import com.twitter.finagle.builder.Server;
import com.twitter.finagle.builder.ServerBuilder;
import com.twitter.finagle.builder.ServerConfig;
import com.twitter.finagle.exp.swift.SwiftService;
import com.twitter.finagle.thrift.ThriftServerFramedCodec;

import java.net.InetSocketAddress;

public class HelloWorldServer {
    public static final String NAME = "thrift-swift-finagle-example";
    public static final int PORT = 9510;
    private Server server;
    private final ServerBuilder<byte[], byte[], ServerConfig.Yes, ServerConfig.Yes, ServerConfig.Yes> serverBuilder;

    public HelloWorldServer() {
        InetSocketAddress address = new InetSocketAddress(PORT);
        serverBuilder = ServerBuilder
                .get()
                .codec(ThriftServerFramedCodec.get())
                .bindTo(address)
                .name(NAME)
                .maxConcurrentRequests(10);
    }

    public void start() {
        if (server == null) {
            System.out.println("Starting server:\n\tPort: " + PORT);
            SwiftService swiftService = new SwiftService(new HelloWorldServiceImpl());
            server = serverBuilder.unsafeBuild(swiftService);
        }
    }

    public void stop() {
        if (server != null) {
            System.out.println("Stopping Server...");
            server.close();
        }
    }
}
