package com.bookstore.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.bookstore.Response.EditorialResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bookstore.Repository.IBookRepository;
import com.bookstore.model.Editorial;
import com.bookstore.service.EditorialService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/bookstore/editorial")
public class EditorialController {

    @Autowired
    EditorialService editorialService;

    @Autowired
    IBookRepository br;

    @GetMapping("")
    public List<EditorialResponse> getAllEditorials(){
        List<EditorialResponse> editorials = this.editorialService.getAllEditorials();
        addLinksToEditorialLink(editorials);
        return editorials;
    }

    @GetMapping("/{id}")
    public List<EditorialResponse> getEditorialById(@PathVariable int id){
        List<EditorialResponse> editorials = this.editorialService.getEditorialById(id);
        addLinksToEditorialLink(editorials);
        return editorials;
    }

    @GetMapping("/name")
    public List<EditorialResponse> getEditorialByName(@RequestParam String value){
        List<EditorialResponse> editorials =  this.editorialService.getEditorialByName(value);
        addLinksToEditorialLink(editorials);
        return editorials;
    }

    @PostMapping("")
    public EditorialResponse addEditorial(@RequestBody Editorial editorial){
        EditorialResponse editorials =  this.editorialService.postEditorial(editorial);
        addLinksToEditorial(editorials);
        return editorials;
    }

    @PutMapping("/{id}")
    public EditorialResponse updateEditorial(@PathVariable int id, @RequestBody Editorial editorial){
        EditorialResponse editorials =  this.editorialService.updateEditorial(id, editorial);
        addLinksToEditorial(editorials);
        return editorials;
    }

    @DeleteMapping("/{id}")
    public String deleteEditorial(@PathVariable int id){
        this.editorialService.deleteEditorialById(id);
        return "Editorial deleted sucessfully";
    }

    public void addLinksToEditorialLink(List<EditorialResponse> editorials){
        editorials.stream().forEach(editorial->addLinksToEditorial(editorial));
    }

    public void addLinksToEditorial(EditorialResponse editorial){
        editorial.add(linkTo(methodOn(EditorialController.class)
                .getEditorialById((int) editorial.getId())).withSelfRel());
        AtomicInteger number = new AtomicInteger(0);
        editorial.getBooksList().stream()
                .forEach(book->editorial.add(linkTo(methodOn(BookController.class)
                        .getBookById((int) book.getId())).withRel("BookTitle("+book.getTitle()+")")));
    }



}
