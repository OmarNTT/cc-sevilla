package com.bookstore.controller;


import com.bookstore.Repository.IBookRepository;
import com.bookstore.Response.BookResponse;
import com.bookstore.model.Book;
import com.bookstore.model.Editorial;
import com.bookstore.service.BookService;
import com.bookstore.service.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookstore/book")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    EditorialService editorialService;

    @GetMapping("")
    public List<BookResponse> getAllBooks(){
       return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Optional<BookResponse> getBookById(@PathVariable long id){
        return bookService.getBookById(id);
    }

    @PostMapping("")
    public BookResponse addBook(@RequestBody Book book){
        return bookService.addNewBook(book);
    }

    @PutMapping("/{id}")
    public BookResponse updateBook(@PathVariable long id, @RequestBody Book book){
        return bookService.updateBookById(id, book);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable long id){
        bookService.deleteBookById(id);
        return "Book deleted sucessfully";
    }

    @GetMapping("/title")
    public List<BookResponse> getBookByTitle(@RequestParam String value){
        return bookService.getBookByTitle(value);
    }

    @GetMapping("/searchbyeditorialid/{id}")
    public List<BookResponse> getBookByEditorial(@PathVariable int id){
        return bookService.getBookByEditorial((Editorial) editorialService.getEditorialById(id));
    }



}
