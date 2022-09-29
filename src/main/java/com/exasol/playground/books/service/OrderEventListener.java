package com.exasol.playground.books.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.cp.ISemaphore;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import lombok.extern.java.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.exasol.playground.books.service.OrderEventProducer.*;

@Log
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "connectionFactoryLookup",
                propertyValue = CONNECTION_FACTORY_JNDI),
        @ActivationConfigProperty(
                propertyName = "destinationLookup",
                propertyValue = QUEUE_JNDI),
        @ActivationConfigProperty(
                propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(
                propertyName = "resourceAdapter",
                propertyValue = "artemis-rar")
})
public class OrderEventListener implements MessageListener {

    @Inject
    private HazelcastInstance hazelcastInstance;

    @Override
    public void onMessage(final Message message) {
        ISemaphore orderEventSemaphore = hazelcastInstance.getCPSubsystem().getSemaphore("orderEventSemaphore");
        orderEventSemaphore.init(4);

        try {
            orderEventSemaphore.acquire();
            Thread.sleep(1000L);
            try {
                final OrderEvent event = getOrderEvent(message);
                log.info("Received " + event);
                hazelcastInstance.getCPSubsystem().getAtomicLong(getHostname()).incrementAndGet();
            } finally {
                orderEventSemaphore.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warning("MDB was interrupted while waiting for the semaphore");
            throw new RuntimeException("MDB was interrupted", e);
        }

    }

    private String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private OrderEvent getOrderEvent(final Message message) {
        try {
            return message.getBody(OrderEvent.class);
        } catch (final JMSException e) {
            throw new IllegalStateException("Unable to get body of type " + OrderEvent.class.getName());
        }
    }
}
