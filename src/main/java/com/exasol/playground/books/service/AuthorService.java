package com.exasol.playground.books.service;

import com.exasol.playground.books.model.Author;
import com.exasol.playground.books.model.Book;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

@Stateless
public class AuthorService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Author> getAll() {
        final String query = "select a from Author a";
        return entityManager.createQuery(query, Author.class).getResultList();
    }

    public Optional<Author> getByName(final @NonNull String name) {
        final String query = "select a from Author a where a.name = :name";
        try {
            final Author author = entityManager.createQuery(query, Author.class).setParameter("name", name)
                    .getSingleResult();
            return Optional.of(author);
        } catch (final NoResultException e) {
            return Optional.empty();
        }
    }

    public void create(final @NonNull String name) {
        final Author author = new Author();
        author.setName(name);

        entityManager.persist(author);
    }
}
