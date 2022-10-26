package com.bookstore.Response;

import com.bookstore.model.Book;
import com.bookstore.model.Editorial;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

public class EditorialResponse {

    @JsonProperty("Editorial_Id")
    private long id;
    @JsonProperty("Editorial_Name")
    private String name;
    @JsonProperty("Editorial_Books")
    private Set<BookResponse> booksList;

    public EditorialResponse(Editorial editorial) {
        this.id = editorial.getId();
        this.name = editorial.getName();
        this.booksList = editorial.getBooksList().stream().map(book->{
            return new BookResponse(book);
        }).collect(Collectors.toSet());
    }

    public EditorialResponse(long id,String name){
        this.id = id;
        this.name = name;
    }


}
