package com.iiti.tech;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.time.Instant;

@Path("/api/books")
public class BookResource {

    //injects are for creating EjB(enterprise java beans equivalent to kind of objects)
    @Inject
    @RestClient
    NumberProxy proxy;

    @Inject
    Logger logger;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Book create(@FormParam("title") String title,@FormParam("author") String author, @FormParam("year") int yearOfPublication,@FormParam("genre") String genre){
        Book book=new Book();
        book.title=title;
        book.author=author;
        book.yearOfPublication=yearOfPublication;
        book.genre=genre;

        book.creationDate= Instant.now();
//here wrt to our client, book microservice acts as "proxy" that contacts the isbn microservice for isbn number
//and our book microservices will act as "rest client" for the isbn microservice
// hence we need to create the proxy between a and b
        book.isbn13=proxy.generateIsbnNumbers().isbn13;
        logger.info("book created : "+ book);
        return book;

    }
}
