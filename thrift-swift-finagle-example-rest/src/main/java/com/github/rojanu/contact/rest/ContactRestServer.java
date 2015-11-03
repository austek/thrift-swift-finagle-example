package com.github.rojanu.contact.rest;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.EmptySpanCollectorImpl;
import com.github.kristofa.brave.FixedSampleRateTraceFilter;
import com.github.kristofa.brave.ServerRequestInterceptor;
import com.github.kristofa.brave.ServerResponseInterceptor;
import com.github.kristofa.brave.ServerTracer;
import com.github.kristofa.brave.SpanCollector;
import com.github.kristofa.brave.TraceFilter;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.http.SpanNameProvider;
import com.github.kristofa.brave.jaxrs2.BraveContainerRequestFilter;
import com.github.kristofa.brave.jaxrs2.BraveContainerResponseFilter;
import com.github.kristofa.brave.zipkin.ZipkinSpanCollector;
import com.github.rojanu.contact.rest.config.ContactRestServerConfig;
import com.github.rojanu.contact.rest.resource.ContactResource;
import com.google.common.collect.ImmutableList;

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
        contactRestServerConfig.client.tracingConfig = contactRestServerConfig.tracingConfig;
        ServerTracer serverTracer = getServerTracer(contactRestServerConfig, environment);

        environment.jersey().register(new ContactResource(contactRestServerConfig.client, serverTracer));
    }

    private ServerTracer getServerTracer(ContactRestServerConfig contactRestServerConfig, Environment environment) {
        SpanCollector spanCollector;
        if(contactRestServerConfig.tracingConfig != null ) {
            spanCollector = new ZipkinSpanCollector(
                    contactRestServerConfig.tracingConfig.server,
                    contactRestServerConfig.tracingConfig.port);
        } else {
            spanCollector = new EmptySpanCollectorImpl();
        }
        int sampleRate = 0;
        try {
            sampleRate = Math.round(1 / contactRestServerConfig.tracingConfig.sampleRate);
        } catch (Exception ignored) {}

        ImmutableList<TraceFilter> traceFilters = ImmutableList.of((TraceFilter) new FixedSampleRateTraceFilter(sampleRate));
        Brave brave = new Brave.Builder("contact-rest")
                .spanCollector(spanCollector)
                .traceFilters(traceFilters)
                .build();


        ServerTracer serverTracer = brave.serverTracer();

        ServerRequestInterceptor requestInterceptor = new ServerRequestInterceptor(serverTracer);
        SpanNameProvider spanNameProvider = new DefaultSpanNameProvider();
        BraveContainerRequestFilter containerRequestFilter = new BraveContainerRequestFilter(requestInterceptor, spanNameProvider);
        environment.jersey().register(containerRequestFilter);

        ServerResponseInterceptor responseInterceptor = new ServerResponseInterceptor(serverTracer);
        BraveContainerResponseFilter containerResponseFilter = new BraveContainerResponseFilter(responseInterceptor);
        environment.jersey().register(containerResponseFilter);

        return serverTracer;
    }
}
