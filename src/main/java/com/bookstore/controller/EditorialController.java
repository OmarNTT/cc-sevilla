package com.bookstore.controller;

import java.util.List;

import com.bookstore.Response.EditorialResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bookstore.Repository.IBookRepository;
import com.bookstore.model.Editorial;
import com.bookstore.service.EditorialService;

@RestController
@RequestMapping("/bookstore/editorial")
public class EditorialController {

    @Autowired
    EditorialService editorialService;

    @Autowired
    IBookRepository br;

    @GetMapping("")
    public List<EditorialResponse> getAllEditorials(){
        return this.editorialService.getAllEditorials();
    }

    @GetMapping("/{id}")
    public List<EditorialResponse> getEditorialById(@PathVariable int id){
        return this.editorialService.getEditorialById(id);
    }

    @GetMapping("/name")
    public List<EditorialResponse> getEditorialByName(@RequestParam String value){
        return this.editorialService.getEditorialByName(value);
    }

    @PostMapping("")
    public EditorialResponse addEditorial(@RequestBody Editorial editorial){
        return this.editorialService.postEditorial(editorial);
    }

    @PutMapping("/{id}")
    public EditorialResponse updateEditorial(@PathVariable int id, @RequestBody Editorial editorial){
        return this.editorialService.updateEditorial(id, editorial);
    }

    @DeleteMapping("/{id}")
    public String deleteEditorial(@PathVariable int id){
        this.editorialService.deleteEditorialById(id);
        return "Editorial deleted sucessfully";
    }



}
