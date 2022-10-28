package com.bookstore.model;

import lombok.*;


import javax.persistence.*;
import java.time.LocalDate;


@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
@Entity
@Table(name="book")
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

    @Column(name="num_pages")
    private int pages;

    @Column(name="Descripcion")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "editorial_id",nullable = false, referencedColumnName = "id")
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
