package com.exasol.playground.books.service;

import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

@Value(staticConstructor = "create")
public class OrderEvent implements Serializable {

    private static final long serialVersionUID = -3591199295859189395L;

    UUID orderId = UUID.randomUUID();
    String type;
}
