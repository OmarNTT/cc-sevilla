package com.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<List<BookRegistryResponse>> getAllRegistries(){
		List<BookRegistryResponse> registries = registryService.getAllRegistries();
		if(registries == null || registries.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(registries, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BookRegistryResponse> getBookById(@PathVariable String id){
		Optional<BookRegistryResponse> opt = this.registryService.getBookRegistryById(id);
		if(opt.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		BookRegistryResponse registry = opt.get();
		return new ResponseEntity<BookRegistryResponse>(registry, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<BookRegistryResponse> addRegistry(@RequestBody BookRegistry registry){
		BookRegistryResponse newReg = this.registryService.addRegistry(registry);
		return new ResponseEntity<>(newReg, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<BookRegistryResponse> updateBook(@PathVariable String id, @RequestBody BookRegistry book){
		BookRegistryResponse updatedReg = registryService.updateBookRegistryById(id, book);
		return new ResponseEntity<BookRegistryResponse>(updatedReg, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteRegistry(@PathVariable String id){
		this.registryService.deleteRegistry(id);
		return new ResponseEntity<>("Book registry deleted succesfully.",HttpStatus.CREATED);
	}

}
