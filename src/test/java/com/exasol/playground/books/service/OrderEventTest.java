package com.exasol.playground.books.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderEventTest {

    @Test
    void order() {
        final OrderEvent a = OrderEvent.create("A");
        assertThat(a).isNotNull();
    }
}