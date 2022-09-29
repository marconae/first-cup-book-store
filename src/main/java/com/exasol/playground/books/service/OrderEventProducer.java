package com.exasol.playground.books.service;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.*;
import lombok.NonNull;
import lombok.extern.java.Log;

import static com.exasol.playground.books.service.OrderEventProducer.QUEUE_JNDI;
import static com.exasol.playground.books.service.OrderEventProducer.QUEUE_LABEL;

@Log
@JMSDestinationDefinition(
        name = QUEUE_JNDI,
        interfaceName = "javax.jms.Queue",
        destinationName = QUEUE_LABEL
)
@ApplicationScoped
public class OrderEventProducer {

    public static final String CONNECTION_FACTORY_JNDI = "jms/__defaultConnectionFactory";
    public static final String QUEUE_LABEL = "orderEventQueue";
    public static final String QUEUE_JNDI = "java:global/jms/orderEventQueue";

    @Inject
    @JMSConnectionFactory(CONNECTION_FACTORY_JNDI)
    private transient JMSContext context;

    @Resource(lookup = QUEUE_JNDI)
    private transient Queue queue;

    public void queue(final @NonNull OrderEvent orderEvent) {
        log.info("Queuing: " + orderEvent);

        final ObjectMessage objectMessage = context.createObjectMessage(orderEvent);
        context.createProducer()
                .send(queue, objectMessage);
    }

}
