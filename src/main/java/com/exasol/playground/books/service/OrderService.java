package com.exasol.playground.books.service;

import fish.payara.cluster.Clustered;
import fish.payara.cluster.DistributedLockType;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.Value;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log
@Singleton
@Clustered(callPostConstructOnAttach = false, callPreDestoyOnDetach = false, lock = DistributedLockType.LOCK)
public class OrderService implements Serializable {

    private static final long serialVersionUID = -9175936705500948387L;

    @Resource
    private transient ManagedExecutorService executorService;

    @Inject
    private transient HealthService healthService;

    private Map<String, OrderEvent> orderEventMap;

    @PostConstruct
    private void onPostConstruct() {
        log.info("OrderService: post construct");
        orderEventMap = new HashMap<>();
    }

    public List<String> getQueuedEvents() {
        return orderEventMap.values()
                .stream()
                .map(OrderEvent::toString)
                .collect(Collectors.toList());
    }

    public void onOrderEvent(final @Observes OrderEvent event) {
        orderEventMap.put(event.getType(), event);
        order(event);
    }

    public void order(final OrderEvent event) {
        log.info("Submitting Order " + event.getOrderId());
        orderEventMap.remove(event.getType());
        final OrderRunnable runnable = OrderRunnable.create(event, healthService.getHostName());
        executorService.execute(runnable);
    }

    @Value(staticConstructor = "create")
    private static class OrderRunnable implements Runnable {

        OrderEvent orderEvent;
        String hostname;

        @Override
        public void run() {
            log.info("Processing event " + orderEvent.getOrderId());
            log.info("Running on " + hostname);

            try {
                Thread.sleep(100);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }

            log.info("Runnable done.");
        }
    }

}
