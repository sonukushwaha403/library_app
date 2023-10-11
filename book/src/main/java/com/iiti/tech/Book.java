package com.iiti.tech;

import jakarta.json.bind.annotation.JsonbProperty;

import java.time.Instant;

public class Book {
    public String title;
    public  String author;
    public  int yearOfPublication;
    public  String genre;
    @JsonbProperty("isbn_13")
    public String isbn13;
    public Instant creationDate;

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                ", genre='" + genre + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
