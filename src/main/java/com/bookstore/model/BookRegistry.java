package com.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class BookRegistry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String _id;
    private String message;
    private Date date;

    public BookRegistry(String message, Date date) {
        this.message = message;
        this.date = date;
    }
}
