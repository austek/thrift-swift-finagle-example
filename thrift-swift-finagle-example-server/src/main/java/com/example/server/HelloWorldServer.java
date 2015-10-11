package com.example.server;

import com.twitter.finagle.ListeningServer;
import com.twitter.finagle.Thrift;
import com.twitter.finagle.exp.swift.SwiftService;
import com.twitter.finagle.param.Label;
import com.twitter.util.Await;

import java.net.InetSocketAddress;

public class HelloWorldServer {
    public static final String NAME = "thrift-swift-finagle-example";
    public static final int PORT = 9510;
    private ListeningServer server;

    public void start() throws Exception {
        System.out.println("Starting server:\n\tPort: " + PORT);
        Thrift.Server thriftServer = (Thrift.Server) Thrift.server()
                .configured(new Label(NAME).mk());
        server = thriftServer.serve(new InetSocketAddress(PORT), new SwiftService(new ContactServiceImpl()));
        Await.ready(server);
    }

    public void stop() throws Exception {
        if (server != null) {
            System.out.println("Stopping Server...");
            Await.result(server.close());
        }
    }
}
