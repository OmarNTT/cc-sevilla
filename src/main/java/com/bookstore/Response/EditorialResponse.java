package com.bookstore.Response;

import com.bookstore.model.Book;
import com.bookstore.model.Editorial;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Getter @Setter @Data
public class EditorialResponse extends RepresentationModel<EditorialResponse> {

    @JsonProperty("Editorial_Id")
    private long id;
    @JsonProperty("Editorial_Name")
    private String name;
    @JsonProperty("Editorial_Books")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<BookResponse> booksList;


    public EditorialResponse(Editorial editorial) {
        this.id = editorial.getId();
        this.name = editorial.getName();
        if (editorial.getBooksList() != null) {
            this.booksList = editorial.getBooksList().stream().map(book -> {
                return new BookResponse(book);
            }).collect(Collectors.toSet());
        }
    }

    public EditorialResponse(long id, String name) {
        this.id = id;
        this.name = name;
    }


    @Override
    @JsonProperty("links")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    public Links getLinks() {
        return super.getLinks();
    }
}


