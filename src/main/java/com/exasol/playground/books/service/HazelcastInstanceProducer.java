package com.exasol.playground.books.service;

/*
import com.hazelcast.core.HazelcastInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@ApplicationScoped
public class HazelcastInstanceProducer {

    @Default
    @Produces
    public HazelcastInstance produce() {
        try {
            final Context ctx = new InitialContext();
            return (HazelcastInstance) ctx.lookup("payara/Hazelcast");
        } catch (final NamingException e) {
            throw new IllegalStateException("Unable to lookup payara/Hazelcast", e);
        }

    }

}
*/