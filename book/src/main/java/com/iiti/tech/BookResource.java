package com.iiti.tech;

import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

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
    //@Retry(maxRetries = 4,delay = 2,delayUnit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "fallcreate")
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

    public Book fallcreate(@FormParam("title") String title,@FormParam("author") String author, @FormParam("year") int yearOfPublication,@FormParam("genre") String genre) throws FileNotFoundException {
        Book book=new Book();
        book.title=title;
        book.author=author;
        book.yearOfPublication=yearOfPublication;
        book.genre=genre;

        book.creationDate= Instant.now();
//here wrt to our client, book microservice acts as "proxy" that contacts the isbn microservice for isbn number
//and our book microservices will act as "rest client" for the isbn microservice
// hence we need to create the proxy between a and b
        book.isbn13="will get back to you ";
        saveBookOnDisk(book);
        logger.info("book saved on disk : "+ book);
        return book;

    }

    private void saveBookOnDisk(Book book) throws FileNotFoundException {
        String bookJson = JsonbBuilder.create().toJson(book);
        try (PrintWriter out = new PrintWriter("book-" + Instant.now().toEpochMilli() + ".json")) {
            out.println(bookJson);
        }
    }
}
