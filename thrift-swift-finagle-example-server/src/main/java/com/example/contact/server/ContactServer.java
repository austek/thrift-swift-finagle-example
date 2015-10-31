package com.example.contact.server;

import com.example.contact.server.dao.impl.InMemoryRepository;
import com.example.server.config.FinagleServerConfig;
import com.twitter.finagle.ListeningServer;
import com.twitter.finagle.Thrift;
import com.twitter.finagle.exp.swift.SwiftService;
import com.twitter.util.Await;

import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;

public class ContactServer {
    public static final String DEFAULT_NAME = "thrift-swift-finagle-example";
    public static final int DEFAULT_PORT = 9510;
    private FinagleServerConfig config;
    private ListeningServer server;

    public ContactServer(FinagleServerConfig config) {
        this.config = config;
        if (StringUtils.isBlank(this.config.serverConfig.name)) {
            this.config.serverConfig.name = DEFAULT_NAME;
        }
        if (this.config.serverConfig.port == null || this.config.serverConfig.port < 1000) {
            this.config.serverConfig.port = DEFAULT_PORT;
        }
    }

    public void start() throws Exception {
        System.out.println("Starting server:\n\tPort: " + this.config.serverConfig.port);
        Thrift.Server thriftServer = Thrift.server();
        server = thriftServer.serve(
                new InetSocketAddress(this.config.serverConfig.port),
                new SwiftService(
                        new ContactServiceImpl(
                                new InMemoryRepository()
                        )
                )
        );
        Await.ready(server);
    }

    public void stop() throws Exception {
        if (server != null) {
            System.out.println("Stopping Server...");
            Await.result(server.close());
        }
    }
}
