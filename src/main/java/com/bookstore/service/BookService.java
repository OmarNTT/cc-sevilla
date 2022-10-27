package com.bookstore.service;


import com.bookstore.Repository.IBookRepository;
import com.bookstore.Repository.IEditorialRepository;
import com.bookstore.Response.BookResponse;
import com.bookstore.model.Book;
import com.bookstore.model.Editorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {


    @Autowired
    IBookRepository iBookRepository;

    @Autowired
    IEditorialRepository iditorialRepository;

    public List<BookResponse> getAllBooks(){
        List<Book> books = iBookRepository.findAll();
        List<BookResponse> bookResponses = new ArrayList<>();
        books.stream().forEach(book -> {
            bookResponses.add(new BookResponse(book));
        });
        return bookResponses;
    }

    public Optional<BookResponse> getBookById(long id){
        Optional<Book> book = iBookRepository.findById(id);
        if(book.isPresent()){
            return Optional.of(new BookResponse(book.get()));
        }
        return Optional.empty();
    }

    public List<BookResponse> getBookByTitle(String title){
        List<Book> books = iBookRepository.findBookByTitle(title);
        List<BookResponse> bookResponses = new ArrayList<>();
        books.stream().forEach(book -> {
            bookResponses.add(new BookResponse(book));
        });
        return bookResponses;
    }

    public List<BookResponse> getBookByEditorial(Editorial editorial){
        List<Book> books = iBookRepository.findBookByEditorial(editorial);
        List<BookResponse> bookResponses = new ArrayList<>();
        books.stream().forEach(book -> {
            bookResponses.add(new BookResponse(book));
        });
        return bookResponses;
    }

    public BookResponse addNewBook(Book book){
        return new BookResponse(iBookRepository.save(book));
    }

    public BookResponse updateBookById(long id,Book book){
        Optional<Book> bookOptional = iBookRepository.findById(id);
        if(bookOptional.isPresent()){
            Book bookToUpdate = bookOptional.get();
            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setEditorial(book.getEditorial());
            bookToUpdate.setAuthor(book.getAuthor());
            bookToUpdate.setPages(book.getPages());
            bookToUpdate.setDescription(book.getDescription());
            bookToUpdate.setPublishDate(book.getPublishDate());
            return new BookResponse(iBookRepository.save(bookToUpdate));
        }
        return null;

    }

    public void deleteBookById(long id){
        iBookRepository.deleteById(id);
    }


}
