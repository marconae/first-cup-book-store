package com.exasol.playground.books.resources;

import com.exasol.playground.books.dto.BookDto;
import com.exasol.playground.books.model.Author;
import com.exasol.playground.books.model.Book;
import com.exasol.playground.books.service.AuthorService;
import com.exasol.playground.books.service.BookService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Optional;

@Path("book")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class BookResource {

    @Inject
    private BookService bookService;

    @Inject
    private AuthorService authorService;

    @GET
    @Path("all")
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @PUT
    @Path("create")
    public void create(final BookDto book) {
        final Optional<Author> author = authorService.getByName(book.getAuthorName());

        if(author.isEmpty()) {
            final String msg = String.format("Author '%s' not found", book.getAuthorName());
            throw new IllegalArgumentException(msg);
        }

        bookService.create(book.getTitle(), author.get());
    }
}
