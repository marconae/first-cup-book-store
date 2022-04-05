package com.exasol.playground.books.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Book {

    @Id
    @GeneratedValue
    private int dbId;

    @NotNull
    private String title;

    @ManyToOne
    private Author author;

}
