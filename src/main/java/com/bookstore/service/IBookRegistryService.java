package com.bookstore.service;

import com.bookstore.Response.BookRegistryResponse;

import java.util.List;

public interface IBookRegistryService {


    public List<BookRegistryResponse> getAllBookRegistry();
    public List<BookRegistryResponse> getBookRegistryById(String id);
    public BookRegistryResponse postBookRegistry(com.bookstore.model.BookRegistry bookRegistry);
    public BookRegistryResponse updateBookRegistry(String id, com.bookstore.model.BookRegistry bookRegistry);
    public void deleteBookRegistryById(String id);

}
