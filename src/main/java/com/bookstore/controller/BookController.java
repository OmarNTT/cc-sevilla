package com.bookstore.controller;


import com.bookstore.Response.BookResponse;
import com.bookstore.Response.EditorialResponse;
import com.bookstore.model.Book;
import com.bookstore.model.BookRegistry;
import com.bookstore.model.Editorial;
import com.bookstore.service.implemented.BookRegistryService;
import com.bookstore.service.implemented.BookService;
import com.bookstore.service.implemented.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/bookstore/book")
public class BookController {


    @Autowired
    private BookService bookService;
    @Autowired
    private BookRegistryService bookRegistryService;
    @Autowired
    private EditorialService editorialService;

    @GetMapping("")
    public ResponseEntity<List<BookResponse>> getAllBooks(){
        Logger("Fetched get all books");
        try{
            List<BookResponse> books = bookService.getAllBooks();
            addLinksToBookList(books);
            if(books.size() == 0){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            ResponseEntity<List<BookResponse>> response =
                    new ResponseEntity<List<BookResponse>>(books, HttpStatus.OK);
            return response;
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable long id){
        Logger("Fetched get books by id with id: " + id);
        try{
            BookResponse book = bookService.getBookById(id).get();
            addLinksToBook(book);
            if(book == null){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            ResponseEntity<BookResponse> response =
                    new ResponseEntity<BookResponse>(book, HttpStatus.OK);
            return response;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<BookResponse> addBook(@RequestBody Book book){
        Logger("Fetched add new book");
       try{
              BookResponse bookResponse = bookService.addNewBook(book);
              addLinksToBook(bookResponse);
              if(bookResponse == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
              }
              ResponseEntity<BookResponse> response =
                     new ResponseEntity<BookResponse>(bookResponse, HttpStatus.OK);
              return response;
       }catch (Exception e){
           return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable long id, @RequestBody Book book){
        Logger("Fetched update book with id: " + id);
        try{
            BookResponse bookResponse = bookService.updateBookById(id, book);
            addLinksToBook(bookResponse);
            if(bookResponse == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            ResponseEntity<BookResponse> response =
                    new ResponseEntity<BookResponse>(bookResponse, HttpStatus.OK);
            return response;
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable long id){
        Logger("Fetched delete book with id: " + id);
        bookService.deleteBookById(id);
        return "Book deleted sucessfully";
    }

    @GetMapping("/title")
    public ResponseEntity<List<BookResponse>> getBookByTitle(@RequestParam String value){
        Logger("Fetched get books by title with value: " + value);
        try{
            List<BookResponse> books = bookService.getBookByTitle(value);
            addLinksToBookList(books);
            if(books == null || books.size() == 0){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            ResponseEntity<List<BookResponse>> response =
                    new ResponseEntity<List<BookResponse>>(books, HttpStatus.OK);
            return response;
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchbyeditorialid/{id}")
    public ResponseEntity<List<BookResponse>> getBookByEditorial(@PathVariable int id){
        Logger("Fetched get books by editorial with id: " + id);
        try{
            EditorialResponse editorialToFind = editorialService.getEditorialById(id).get(0);
            Editorial editorial = new Editorial();
            editorial.setId(editorialToFind.getId());
            List<BookResponse> books = bookService.getBookByEditorial(editorial);
            addLinksToBookList(books);
            if(books == null || books.size() == 0){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            ResponseEntity<List<BookResponse>> response = new ResponseEntity<List<BookResponse>>(books, HttpStatus.OK);
            return response;
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void addLinksToBook(BookResponse book){
        long id = book.getId();
        Link selfLink = linkTo(BookController.class).slash(id).withSelfRel();
        int editorialId = (int) book.getEditorialResponse().getId();
        Link editorialLink = linkTo(methodOn(EditorialController.class)
                .getEditorialById(editorialId)).withRel("editorial");
        book.add(selfLink);
        book.add(editorialLink);
        EditorialResponse e = book.getEditorialResponse();
    }


    public List<BookResponse> addLinksToBookList(List<BookResponse> books){
        for(BookResponse book: books) {
            this.addLinksToBook(book);
        }
        return books;
    }

    public void Logger(String message){
        Date fetchedDate = new Date();
        BookRegistry bookRegistry = new BookRegistry(message, fetchedDate);
        bookRegistryService.postBookRegistry(bookRegistry);
    }



}
