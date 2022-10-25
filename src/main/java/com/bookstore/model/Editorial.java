package com.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name="Editorials")
public class Editorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="Name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "editorial")
    private Set<Book> booksList;

}
