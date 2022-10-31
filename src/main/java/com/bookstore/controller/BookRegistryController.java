package com.bookstore.controller;


import com.bookstore.Response.BookRegistryResponse;
import com.bookstore.model.BookRegistry;
import com.bookstore.service.implemented.BookRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookstore/bookregistry")
public class BookRegistryController {

    @Autowired
    private BookRegistryService bookRegistryService;

    @GetMapping("")
    public List<BookRegistryResponse> getAllBookRegistry(){
        return bookRegistryService.getAllBookRegistry();
    }

    @GetMapping("/{id}")
    public List<BookRegistryResponse> getBookRegistryById(@PathVariable("id") String id){
        return bookRegistryService.getBookRegistryById(id);
    }

    @PostMapping("")
    public BookRegistryResponse addBookRegistry(@RequestBody BookRegistry bookRegistry){
        return bookRegistryService.postBookRegistry(bookRegistry);
    }

    @PutMapping("/{id}")
    public BookRegistryResponse updateBookRegistry(@PathVariable("id") String id, @RequestBody BookRegistry bookRegistry){
        return bookRegistryService.updateBookRegistry(id, bookRegistry);
    }

    @DeleteMapping("/{id}")
    public void deleteBookRegistry(@PathVariable("id") String id){
        bookRegistryService.deleteBookRegistryById(id);
    }




}
