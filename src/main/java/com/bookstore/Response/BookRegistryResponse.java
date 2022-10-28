package com.bookstore.Response;


import com.bookstore.model.BookRegistry;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BookRegistryResponse {

    @JsonProperty("BookRegistry_Id")
    private String _id;
    @JsonProperty("BookRegistry_Message")
    private String message;
    @JsonProperty("BookRegistry_Date")
    private Date date;

    public BookRegistryResponse(BookRegistry bookRegistry){
        this._id = bookRegistry.get_id();
        this.message = bookRegistry.getMessage();
        this.date = bookRegistry.getDate();
    }

}
