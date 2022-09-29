package com.exasol.playground.books.service;

import fish.payara.cluster.Clustered;
import fish.payara.cluster.DistributedLockType;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

@Log
@Singleton
@Clustered(callPostConstructOnAttach = false, callPreDestoyOnDetach = false, lock = DistributedLockType.LOCK)
public class OrderEventBrokerCache implements Serializable {

    private static final long serialVersionUID = -6237933411744175370L;

    @PostConstruct
    void onPostConstruct() {
        log.info("Broker cache init...");
    }

    private final AtomicInteger eventCount = new AtomicInteger();

    public void increment() {
        eventCount.incrementAndGet();
    }

    public int getEventCount() {
        return eventCount.get();
    }

}
