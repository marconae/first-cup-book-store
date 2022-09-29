package com.exasol.playground.books.resources;

import com.exasol.playground.books.dto.HealthDto;
import com.exasol.playground.books.service.HealthService;
import com.exasol.playground.books.service.OrderEventBrokerCache;
import fish.payara.cdi.jsr107.impl.NamedCache;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import javax.cache.Cache;

@Path("health")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class HealthResource {

    private static final String TICK_KEY = "tick-count";
    protected static final String CLUSTER_CACHE_KEY = "cluster-cache";
    protected static final String INSTANCE_COUNT_KEY = "instance-count";

    @Inject
    private HealthService healthService;

    @Inject
    @NamedCache(cacheName = CLUSTER_CACHE_KEY)
    private Cache<String, Integer> cache;

    @Inject
    private OrderEventBrokerCache brokerCache;

    @GET
    public HealthDto getHealth() {
        return HealthDto.builder()
                .maxHeap(healthService.getMaxHeap())
                .hostName(healthService.getHostName())
                .instanceCount(cache.get(INSTANCE_COUNT_KEY))
                .eventCount(brokerCache.getEventCount())
                .tick(getTick())
                .build();
    }

    private long getTick() {
        if (!cache.containsKey(TICK_KEY)) {
            cache.put(TICK_KEY, 0);
        }

        final int tick = cache.get(TICK_KEY) + 1;
        cache.put(TICK_KEY, tick);

        return tick;
    }
}
