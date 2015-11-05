package com.github.rojanu.test.util.server;

import com.github.rojanu.server.AbstractFinagleServerWithAdminInterface;
import com.google.common.collect.Lists;
import com.twitter.util.Await;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JustInTimeServer {
    private static final Logger logger = LoggerFactory.getLogger(JustInTimeServer.class);

    private final List<AbstractFinagleServerWithAdminInterface> embeddedServers = Lists.newArrayList();

    public static class Builder {
        private final List<AbstractFinagleServerWithAdminInterface> embeddedServers = Lists.newArrayList();

        private Builder() {
        }

        public Builder server(AbstractFinagleServerWithAdminInterface server) {
            if (server != null) {
                embeddedServers.add(server);
            }
            return this;
        }

        public JustInTimeServer build() {
            return new JustInTimeServer(embeddedServers);
        }
    }

    public static Builder Builder() {
        return new Builder();
    }

    private JustInTimeServer(final List<AbstractFinagleServerWithAdminInterface> embeddedServers) {
        this.embeddedServers.addAll(embeddedServers);
    }

    public JustInTimeServer stop() throws Exception {
        for (AbstractFinagleServerWithAdminInterface embeddedServer : embeddedServers) {
            Await.result(embeddedServer.close());
        }
        return this;
    }

    public JustInTimeServer stopSilently() {
        try {
            stop();
        } catch (Exception ignored) {
        }
        return this;
    }

}
