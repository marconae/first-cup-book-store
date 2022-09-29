package com.exasol.playground.books.service;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import lombok.extern.java.Log;

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

    //@Inject
    //private HazelcastInstance hazelcastInstance;

    @Override
    public void onMessage(final Message message) {
        final OrderEvent event = getOrderEvent(message);
        log.info("Received " + event);

    }

    private OrderEvent getOrderEvent(final Message message) {
        try {
            return message.getBody(OrderEvent.class);
        } catch (final JMSException e) {
            throw new IllegalStateException("Unable to get body of type " + OrderEvent.class.getName());
        }
    }
}
