package com.exasol.playground.books.resources;

import com.exasol.playground.books.service.OrderEvent;
import com.exasol.playground.books.service.OrderEventCallable;
import fish.payara.cluster.Clustered;
import fish.payara.cluster.DistributedLockType;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.Singleton;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.net.UnknownHostException;

@Log
@Singleton
@Clustered(lock = DistributedLockType.LOCK_NONE)
public class OrderEventClusteredService implements Serializable {

    private static final long serialVersionUID = -8705885347664661974L;

    @Asynchronous
    public void fire(final OrderEvent orderEvent) {
        final OrderEventCallable callable = new OrderEventCallable(orderEvent);
        try {
            final String result = callable.call();
            log.info("EJB Host " + result + " Thread " + Thread.currentThread().getId());
        } catch (final UnknownHostException e) {
            throw new IllegalStateException(e);
        }
    }

}
