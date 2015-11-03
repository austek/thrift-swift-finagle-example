package com.github.rojanu.client;

import com.github.rojanu.service.BasicFinagleService;
import com.twitter.finagle.Service;
import com.twitter.finagle.thrift.ThriftClientRequest;

import java.io.IOException;
import java.util.Objects;

public class CloseableClient<T extends BasicFinagleService> implements AutoCloseable {

    private final Service<ThriftClientRequest, byte[]> transport;
    private final T o;

    public CloseableClient(final Service<ThriftClientRequest, byte[]> transport, final T o) {
        Objects.requireNonNull(o);
        this.transport = transport;
        this.o = o;
    }

    public T get() {
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
        } catch (IOException ignored) {
        }
    }
}
