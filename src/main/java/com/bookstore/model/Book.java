package com.bookstore.model;

import lombok.*;


import javax.persistence.*;
import java.time.LocalDate;


@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name="Books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="Title")
    private String title ;

    @Column(name="Author")
    private String author;

    @Column(name="Publish")
    private LocalDate publishDate;

    @Column(name="Page")
    private int pages;

    @Column(name="Descripcion")
    private String description;

    @ManyToOne
    @JoinColumn(name = "editorial_id", nullable = true, updatable = false)
    private Editorial editorial;

    public Book(String title, String author, LocalDate publishDate, int pages, String description, Editorial editorial) {
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
        this.pages = pages;
        this.description = description;
        this.editorial = editorial;
    }
}
