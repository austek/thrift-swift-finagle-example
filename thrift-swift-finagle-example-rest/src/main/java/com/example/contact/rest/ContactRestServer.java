package com.example.contact.rest;

import com.example.contact.rest.config.ContactRestServerConfig;
import com.example.contact.rest.resource.ContactResource;
import com.github.kristofa.brave.ServerTracer;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ContactRestServer extends Application<ContactRestServerConfig> {

    public static void main(String[] args) throws Exception {
        new ContactRestServer().run(args);
    }

    @Override
    public void initialize(Bootstrap<ContactRestServerConfig> bootstrap) {

    }

    @Override
    public void run(ContactRestServerConfig contactRestServerConfig, Environment environment) throws Exception {
        //ServerTracer serverTracer = getServerTracer(contactRestServerConfig, environment);
        ServerTracer serverTracer = null;

        environment.jersey().register(new ContactResource(contactRestServerConfig.client, serverTracer));
    }

//    private ServerTracer getServerTracer(ContactRestServerConfig contactRestServerConfig, Environment environment) {
//        SpanCollector spanCollector = new ZipkinSpanCollector(
//                contactRestServerConfig.tracing.server,
//                contactRestServerConfig.tracing.port);
//
//        int sampleRate = 0;
//        try {
//            sampleRate = contactRestServerConfig.tracing.sampleRate;
//        } catch (Exception ignored) {
//        }
//
//        ServerTracer serverTracer = Brave.getServerTracer(spanCollector,
//                ImmutableList.of((TraceFilter) new FixedSampleRateTraceFilter(sampleRate)));
//
//        if (serverTracer != null) {
//            EndPointSubmitter endPointSubmitter = Brave.getEndPointSubmitter();
//            environment.servlets()
//                    .addFilter("tracing-filter", new ServletTraceFilter(serverTracer, endPointSubmitter))
//                    .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
//        }
//
//        return serverTracer;
//    }
}
