package com.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name="editorial")
public class Editorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator="editorial_seq")
    private long id;

    @Column(name="Name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "editorial",fetch = FetchType.LAZY)
    private Set<Book> booksList;

}
