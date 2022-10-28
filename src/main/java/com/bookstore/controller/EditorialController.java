package com.bookstore.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

import com.bookstore.model.Editorial;
import com.bookstore.response.BookResponse;
import com.bookstore.response.EditorialResponse;
import com.bookstore.service.EditorialService;

@RestController
@RequestMapping("/bookstore/editorial")
public class EditorialController {

    @Autowired
    EditorialService editorialService;

    @GetMapping("")
    public List<EditorialResponse> getAllEditorials(){
    	List<EditorialResponse> editorials = this.editorialService.getAllEditorials();
    	addLinksToList(editorials);
        return editorials;
    }

    @GetMapping("/{id}")
    public EditorialResponse getEditorialById(@PathVariable final int id){
    	Editorial editorial = this.editorialService.getEditorialById(id).get();
    	EditorialResponse response = new EditorialResponse(editorial);
    	addLinksToEditorial(response);
        return response;
    }

    @GetMapping("/name")
    public List<EditorialResponse> getEditorialByName(@RequestParam String value){
    	List<EditorialResponse> editorials = this.editorialService.getEditorialByName(value);
    	addLinksToList(editorials);
        return editorials;
    }

    @PostMapping("")
    public EditorialResponse addEditorial(@RequestBody Editorial editorial){
    	EditorialResponse response = this.editorialService.postEditorial(editorial);
    	addLinksToEditorial(response);
        return response;
    }

    @PutMapping("/{id}")
    public EditorialResponse updateEditorial(@PathVariable int id, @RequestBody Editorial editorial){
    	EditorialResponse response = this.editorialService.updateEditorial(id, editorial);
    	addLinksToEditorial(response);
        return response;
    }

    @DeleteMapping("/{id}")
    public String deleteEditorial(@PathVariable int id){
        this.editorialService.deleteEditorialById(id);
        return "Editorial deleted sucessfully";
    }
    
    private static void addLinksToEditorial(EditorialResponse editorial){
		long id = editorial.getId();
		Link selfLink = linkTo(EditorialController.class).slash(id).withSelfRel();
		
		editorial.add(selfLink);
		
		for(BookResponse book: editorial.getBooksList()) {
			long bookId = book.getId();
			Link bookLink = linkTo(methodOn(BookController.class)
					.getBookById(bookId)).withRel("Book_"+bookId+"_Ref");
			editorial.add(bookLink);
		}
	}

	private static void addLinksToList(List<EditorialResponse> editorials){
		for(EditorialResponse editorial: editorials) {
			addLinksToEditorial(editorial);
		}
	}

}
