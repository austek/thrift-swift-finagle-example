package com.example.client;

import com.example.api.HelloWorldService;
import com.twitter.finagle.Service;
import com.twitter.finagle.thrift.ThriftClientRequest;

import java.io.IOException;
import java.util.Objects;

public class CloseableClient implements AutoCloseable {

    private final Service<ThriftClientRequest, byte[]> transport;
    private final HelloWorldService o;

    public CloseableClient(final Service<ThriftClientRequest, byte[]> transport, final HelloWorldService o) {
        Objects.requireNonNull(o);
        this.transport = transport;
        this.o = o;
    }

    public HelloWorldService get() {
        return o;
    }

    @Override
    public void close() throws IOException {
        if (transport != null) {
            transport.close();
        }
    }

    public void closeSilently() {
        try {
            close();
        } catch (IOException ex) {
        }
    }
}
