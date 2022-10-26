package com.bookstore.controller;


import com.bookstore.Repository.IBookRepository;
import com.bookstore.model.Book;
import com.bookstore.model.Editorial;
import com.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookstore")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable long id){
        return bookService.getBookById(id);
    }

    @PostMapping("")
    public Book addBook(@RequestBody Book book){
        return bookService.addNewBook(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable long id, @RequestBody Book book){
        return bookService.updateBookById(id, book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable long id){
        bookService.deleteBookById(id);
    }

    @GetMapping("/title/{title}")
    public List<Book> getBookByTitle(@PathVariable String title){
        return bookService.getBookByTitle(title);
    }

    @GetMapping("/editorial/{editorial}")
    public List<Book> getBookByEditorial(@PathVariable Editorial editorial){
        return bookService.getBookByEditorial(editorial);
    }



}
