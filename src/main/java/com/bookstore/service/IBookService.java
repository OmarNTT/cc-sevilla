package com.bookstore.service;

import com.bookstore.Response.BookResponse;
import com.bookstore.model.Book;
import com.bookstore.model.Editorial;

import java.util.List;
import java.util.Optional;

public interface IBookService {

    public List<BookResponse> getAllBooks();
    public Optional<BookResponse> getBookById(long id);
    public List<BookResponse> getBookByTitle(String title);
    public List<BookResponse> getBookByEditorial(Editorial editorial);
    public BookResponse addNewBook(com.bookstore.model.Book book);
    public BookResponse updateBookById(long id, Book book);
    public void deleteBookById(long id);

}
