package com.iiti.tech;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;

import java.time.Instant;
import java.util.Random;

@Path("/api/numbers")
public class NumberResource {

    @Inject
    Logger logger;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
   public IsbnNumbers  generateIsbnNumbers(){
     IsbnNumbers isbnNumbers =new IsbnNumbers();
     isbnNumbers.isbn13="13-"+new Random().nextInt(100_000_000);
     isbnNumbers.isbn10="10-"+new  Random().nextInt(100_000);
     isbnNumbers.generationDate= Instant.now();

     //if someone connects to "/api/numbers" it will generate logs on console
     logger.info("Number Generated :"+ isbnNumbers);


     return isbnNumbers;
    }
}
