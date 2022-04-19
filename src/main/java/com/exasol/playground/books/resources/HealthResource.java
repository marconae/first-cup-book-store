package com.exasol.playground.books.resources;

import com.exasol.playground.books.dto.HealthDto;
import com.exasol.playground.books.service.HealthService;
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

    private static final String CACHE_KEY = "tick-count";

    @Inject
    private HealthService healthService;

    @Inject
    @NamedCache(cacheName = "cluster-cache")
    private Cache<String, Long> tickCache;

    @GET
    public HealthDto getHealth() {
        return HealthDto.builder()
                .maxHeap(healthService.getMaxHeap())
                .hostName(healthService.getHostName())
                .tick(getTick())
                .build();
    }

    private long getTick() {
        if(!tickCache.containsKey(CACHE_KEY)) {
            tickCache.put(CACHE_KEY, 0L);
        }

        final long tick = tickCache.get(CACHE_KEY) + 1;
        tickCache.put(CACHE_KEY, tick);

        return tick;
    }
}
