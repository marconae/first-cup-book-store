package com.exasol.playground.books.service;

import com.hazelcast.collection.IQueue;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import jakarta.annotation.Resource;
import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.enterprise.concurrent.ManagedExecutorService;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import lombok.extern.java.Log;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;

import static com.exasol.playground.books.service.OrderEventProducer.QUEUE_JNDI;

@Log
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destinationLookup",
                propertyValue = QUEUE_JNDI),
        @ActivationConfigProperty(
                propertyName = "destinationType",
                propertyValue = "javax.jms.Queue")
})
public class OrderEventListener implements MessageListener {

    @Resource
    private ManagedExecutorService managedExecutorService;

    @Inject
    private OrderEventBrokerCache brokerCache;

    @Inject
    private HazelcastInstance hazelcastInstance;

    @Override
    public void onMessage(final Message message) {
        final OrderEvent event = getOrderEvent(message);
        brokerCache.increment();
        final OrderEventCallable eventCallable = new OrderEventCallable(event);

        if (event.getType().equals("hazelcast")) {
            log.info("Submitting to Hazelcast");
            submitToHazelcastExecutor(eventCallable);
        } else if (event.getType().equals("managed")) {
            log.info("Submitting to Managed Executor");
            submitToManagedExecutorService(eventCallable);
        } else {
            log.info("Invoking callable directly");
            invokeCallableDirectly(eventCallable);
        }

    }

    private void invokeCallableDirectly(final OrderEventCallable eventCallable) {
        try {
            final String result = eventCallable.call();
            log.info("Callable done, ran on host " + result);
        } catch (final UnknownHostException e) {
            log.log(Level.WARNING, "Unable to obtain hostname", e);
        }
    }

    private void submitToHazelcastExecutor(final OrderEventCallable eventCallable) {
        final IExecutorService executorService = hazelcastInstance.getExecutorService("default");
        final Future<String> futureResult = executorService.submit(eventCallable);
        waitForFuture(futureResult);
    }

    private void submitToManagedExecutorService(final OrderEventCallable eventCallable) {

        final Future<String> futureResult = managedExecutorService.submit(eventCallable);

        waitForFuture(futureResult);
    }

    private void waitForFuture(final Future<String> futureResult) {
        try {
            final String hostName = futureResult.get();
            log.info("Callable done, ran on host " + hostName);
        } catch (final InterruptedException e) {
            log.log(Level.WARNING, "Callable interrupted", e);
            Thread.currentThread().interrupt();
        } catch (final ExecutionException e) {
            log.log(Level.WARNING, "Callable aborted", e);
        }
    }

    private OrderEvent getOrderEvent(final Message message) {
        try {
            return message.getBody(OrderEvent.class);
        } catch (final JMSException e) {
            throw new IllegalStateException("Unable to get body of type " + OrderEvent.class.getName());
        }
    }

    private void test() {
        final IQueue<Object> testQueue = hazelcastInstance.getQueue("test");
    }
}
