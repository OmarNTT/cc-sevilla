package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.BookRegistry;
import com.bookstore.response.BookRegistryResponse;
import com.bookstore.service.BookRegistryService;

@RestController
@RequestMapping("/bookstore/bookRegistry")
public class BookRegistryController {
	
	@Autowired
	BookRegistryService registryService;
	
	@GetMapping("")
	public List<BookRegistryResponse> getAllRegistries(){
		return registryService.getAllRegistries();
	}
	
	@PostMapping("")
	public BookRegistryResponse addRegistry(@RequestBody BookRegistry registry){
		return this.registryService.addRegistry(registry);
	}
	
	@DeleteMapping("{id}")
	public void deleteRegistry(@PathVariable long id){
		this.registryService.deleteRegistry(id);
	}

}
