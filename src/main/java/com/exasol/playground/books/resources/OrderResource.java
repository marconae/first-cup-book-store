package com.exasol.playground.books.resources;

import com.exasol.playground.books.service.OrderEvent;
import com.exasol.playground.books.service.OrderEventProducer;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class OrderResource {

    @Inject
    private OrderEventProducer orderEventProducer;

    @Inject
    private OrderEventClusteredService clusteredService;

    @GET
    @Path("fire/{type}")
    public JsonObject order(@PathParam("type") final String type) {
        final OrderEvent event = OrderEvent.create(type);

        fireEvent(event);

        final JsonObjectBuilder resultObject = Json.createObjectBuilder();
        resultObject.add("id", event.getOrderId().toString());

        return resultObject.build();
    }

    @GET
    @Path("fire/{type}/{count}")
    public JsonObject orderMany(@PathParam("type") final String type, @PathParam("count") final String count) {
        final int eventCount = Integer.parseInt(count);

        final JsonObjectBuilder resultObject = Json.createObjectBuilder();
        final JsonArrayBuilder idArray = Json.createArrayBuilder();

        for (int i = 0; i < eventCount; i++) {
            final OrderEvent event = OrderEvent.create(type);
            fireEvent(event);
            idArray.add(event.getOrderId().toString());
        }

        resultObject.add("ids", idArray.build());

        return resultObject.build();
    }

    private void fireEvent(final OrderEvent event) {
        if(event.getType().equals("ejb")) {
            clusteredService.fire(event);
        } else {
            orderEventProducer.queue(event);
        }
    }
}
