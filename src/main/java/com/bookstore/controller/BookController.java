package com.bookstore.controller;


import com.bookstore.Repository.IBookRepository;
import com.bookstore.Response.BookResponse;
import com.bookstore.Response.EditorialResponse;
import com.bookstore.model.Book;
import com.bookstore.model.Editorial;
import com.bookstore.service.BookService;
import com.bookstore.service.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<BookResponse>> getAllBooks(){
        try{
            List<BookResponse> books = bookService.getAllBooks();
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
        try{
            BookResponse book = bookService.getBookById(id).get();
            if(book == null){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            ResponseEntity<BookResponse> response =
                    new ResponseEntity<BookResponse>(book, HttpStatus.OK);
            return response;
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<BookResponse> addBook(@RequestBody Book book){
       try{
              BookResponse bookResponse = bookService.addNewBook(book);
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
        try{
            BookResponse bookResponse = bookService.updateBookById(id, book);
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
        bookService.deleteBookById(id);
        return "Book deleted sucessfully";
    }

    @GetMapping("/title")
    public ResponseEntity<List<BookResponse>> getBookByTitle(@RequestParam String value){
        try{
            List<BookResponse> books = bookService.getBookByTitle(value);
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
        try{
            EditorialResponse editorialToFind = editorialService.getEditorialById(id).get(0);
            Editorial editorial = new Editorial();
            editorial.setId(editorialToFind.getId());
            List<BookResponse> books = bookService.getBookByEditorial(editorial);
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



}
