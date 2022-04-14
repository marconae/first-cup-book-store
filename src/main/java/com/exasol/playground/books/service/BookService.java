package com.exasol.playground.books.service;

import com.exasol.playground.books.model.Author;
import com.exasol.playground.books.model.Book;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;

import java.util.List;

@Stateless
public class BookService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Book> getAll() {
        final String query = "select b from Book b join fetch b.author";
        return entityManager.createQuery(query, Book.class)
                .getResultList();
    }

    public void create(final @NonNull String title, final @NonNull Author author) {
        final Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);

        author.getBooks().add(book);

        entityManager.persist(book);
        entityManager.merge(author);
    }
}
