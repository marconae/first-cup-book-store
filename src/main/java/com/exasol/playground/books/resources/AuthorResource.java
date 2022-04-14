package com.exasol.playground.books.resources;

import com.exasol.playground.books.dto.AuthorDto;
import com.exasol.playground.books.model.Author;
import com.exasol.playground.books.service.AuthorService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

@Path("author")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class AuthorResource {

    @Inject
    private AuthorService authorService;

    @GET
    @Path("all")
    public List<Author> getAll() {
        return authorService.getAll();
    }

    @PUT
    @Path("create")
    public void createAuthor(final AuthorDto authorDto) {
        authorService.create(authorDto.getName());
    }
}
