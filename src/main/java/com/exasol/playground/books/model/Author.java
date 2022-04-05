package com.exasol.playground.books.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Author {

    @Id
    @GeneratedValue
    private int dbId;

    @NotNull
    private String name;

    @OneToMany
    private List<Book> books;

}
