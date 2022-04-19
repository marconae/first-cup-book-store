package com.exasol.playground.books.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.net.InetAddress;
import java.net.UnknownHostException;

@ApplicationScoped
public class HealthService {

    public long getMaxHeap() {
        return Runtime.getRuntime().maxMemory() / 1024 / 1024;
    }

    public String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (final UnknownHostException e) {
            throw new IllegalStateException(e);
        }
    }
}
