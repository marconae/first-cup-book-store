package com.exasol.playground.books.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "Builder")
public class HealthDto {
    private String hostName;
    private long maxHeap;
    private long tick;
    private long instanceCount;
}
