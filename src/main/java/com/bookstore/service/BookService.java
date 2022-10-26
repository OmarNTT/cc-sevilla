package com.bookstore.service;


import com.bookstore.Repository.IBookRepository;
import com.bookstore.model.Book;
import com.bookstore.model.Editorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    /*
     * getAllBooks
     * getBookById
     * getBookByTitle
     * getBookByEditorial
     * addNewBook
     * updateBookById
     * deleteBookById
     * */

    @Autowired
    IBookRepository iBookRepository;

    public List<Book> getAllBooks(){
        return iBookRepository.findAll();
    }

    public Optional<Book> getBookById(long id){
        return iBookRepository.findById(id);
    }

    public List<Book> getBookByTitle(String title){
        return iBookRepository.findBookByTitle(title);
    }

    public List<Book> getBookByEditorial(Editorial editorial){
        return iBookRepository.findBookByEditorial(editorial);
    }

    public Book addNewBook(Book book){
        return iBookRepository.save(book);
    }

    public Book updateBookById(long id,Book book){
        Optional<Book> bookOptional = iBookRepository.findById(id);
        if(bookOptional.isPresent()){

            Book bookToUpdate = bookOptional.get();
            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setEditorial(book.getEditorial());
            bookToUpdate.setAuthor(book.getAuthor());
            bookToUpdate.setPages(book.getPages());
            bookToUpdate.setDescription(book.getDescription());
            bookToUpdate.setPublishDate(book.getPublishDate());
            return iBookRepository.save(bookToUpdate);
        }
        return null;
    }


    public void deleteBookById(long id){
        iBookRepository.deleteById(id);
    }


}
