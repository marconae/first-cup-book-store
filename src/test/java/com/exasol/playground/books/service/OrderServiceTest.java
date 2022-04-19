package com.exasol.playground.books.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    void order() {
        final OrderEvent a = OrderEvent.create("A");
        assertThat(a).isNotNull();
    }
}