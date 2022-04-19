package com.exasol.playground.books.resources;

import com.exasol.playground.books.service.OrderEvent;
import com.exasol.playground.books.service.OrderService;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.json.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class OrderResource {

    @Inject
    private Event<OrderEvent> orderEvent;

    @Inject
    private OrderService orderService;

    @GET
    @Path("fire/{type}")
    public JsonObject order(@PathParam("type") String type) {
        final OrderEvent event = OrderEvent.create(type);

        orderEvent.fire(event);

        final JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        objectBuilder.add("id", event.getOrderId().toString());

        return objectBuilder.build();
    }

    @GET
    @Path("list")
    public JsonArray getOrderQueue() {
        final JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        orderService.getQueuedEvents().forEach(arrayBuilder::add);
        return arrayBuilder.build();
    }
}
