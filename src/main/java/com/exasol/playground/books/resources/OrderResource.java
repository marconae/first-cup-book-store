package com.exasol.playground.books.resources;

import com.exasol.playground.books.service.OrderEvent;
import com.exasol.playground.books.service.OrderEventProducer;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;

@Log
@Path("order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class OrderResource {

    @Inject
    private OrderEventProducer orderEventProducer;

    @GET
    @Path("fire/{type}")
    public JsonObject order(@PathParam("type") final String type) {
        final OrderEvent event = OrderEvent.create(type);

        orderEventProducer.queue(event);

        final JsonObjectBuilder resultObject = Json.createObjectBuilder();
        resultObject.add("id", event.getOrderId().toString());

        return resultObject.build();
    }

    @GET
    @Path("fire/{type}/{amount}")
    public JsonObject order(@PathParam("type") final String type, @PathParam("amount") final String amount) {

        for (int i = 0; i < Integer.parseInt(amount); i++) {
            final OrderEvent event = OrderEvent.create(type);
            orderEventProducer.queue(event);
        }


        final JsonObjectBuilder resultObject = Json.createObjectBuilder();
        resultObject.add("amount", amount);

        return resultObject.build();
    }
}
