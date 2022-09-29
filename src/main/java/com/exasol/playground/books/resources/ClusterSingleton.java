package com.exasol.playground.books.resources;

import fish.payara.cdi.jsr107.impl.NamedCache;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import lombok.extern.java.Log;

import javax.cache.Cache;
import java.io.Serializable;

@Log
@Singleton
@Startup
public class ClusterSingleton implements Serializable {

    private static final long serialVersionUID = -4068105668458598756L;

    @Inject
    @NamedCache(cacheName = HealthResource.CLUSTER_CACHE_KEY)
    private transient Cache<String, Integer> cache;

    @PostConstruct
    public void init() {
        log.info("Registering instance");
        if (!cache.putIfAbsent(HealthResource.INSTANCE_COUNT_KEY, 1)) {
            final int count = cache.get(HealthResource.INSTANCE_COUNT_KEY);
            cache.put(HealthResource.INSTANCE_COUNT_KEY, count + 1);
        }
    }
}
