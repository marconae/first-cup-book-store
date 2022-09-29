package com.exasol.playground.books.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;

@Log
@RequiredArgsConstructor
public class OrderEventCallable implements Callable<String>, Serializable {

    private static final long serialVersionUID = 6009624900153279557L;
    private final OrderEvent orderEvent;

    @Override
    public String call() throws UnknownHostException {
        final String hostName = InetAddress.getLocalHost().getHostName();

        log.info("Processing event " + orderEvent.getOrderId());
        log.info("Running on " + hostName);
        log.info("Thread: " + Thread.currentThread().getId());

        sleep();

        return hostName;
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
