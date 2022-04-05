package com.exasol.playground.books.service;

import com.exasol.playground.books.model.Book;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("book")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Stateless
public class BookResource {

    @Inject
    private BookService bookService;

    @GET
    @Path("all")
    public List<Book> getAll() {
        return bookService.getAll();
    }
}
