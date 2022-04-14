package com.exasol.playground.books.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@NamedEntityGraph(name = Book.GRAPH_BOOK_COMPLETE, attributeNodes = {
        @NamedAttributeNode("author")
})
public class Book {

    public static final String GRAPH_BOOK_COMPLETE = "book.complete";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dbId;

    @NotNull
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    @Override
    public String toString() {
        return "Book{" +
                "dbId=" + dbId +
                ", author=" + author +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        final Book book = (Book) o;
        return Objects.equals(getTitle(), book.getTitle()) && Objects.equals(getAuthor(), book.getAuthor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getAuthor());
    }
}
