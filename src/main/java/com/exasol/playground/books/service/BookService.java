package com.exasol.playground.books.service;

import com.exasol.playground.books.model.Book;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class BookService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Book> getAll() {
        final String query = "select b from Book b join fetch b.author";
        return entityManager.createQuery(query, Book.class).getResultList();
    }
}
