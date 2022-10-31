package com.bookstore.controller;


import com.bookstore.model.Book;
import com.bookstore.model.Editorial;
import com.bookstore.response.BookResponse;
import com.bookstore.service.BookRegistryService;
import com.bookstore.service.BookService;
import com.bookstore.service.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookstore/book")
public class BookController {

	@Autowired
	BookService bookService;
	
	@Autowired
	BookRegistryService registryService;

	@Autowired
	EditorialService editorialService;

	@GetMapping("")
	public ResponseEntity<List<BookResponse>> getAllBooks(){
		List<BookResponse> bookList = bookService.getAllBooks();
		if(bookList == null || bookList.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		addLinksToList(bookList);
		ResponseEntity<List<BookResponse>> response = new ResponseEntity<List<BookResponse>>(bookList, HttpStatus.OK);
		return response;
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookResponse> getBookById(@PathVariable long id){
		Optional<BookResponse> opt = bookService.getBookById(id);
		if(opt.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		BookResponse book = opt.get();
		addLinksToBook(book);
		return new ResponseEntity<BookResponse>(book, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<BookResponse> addBook(@RequestBody Book book){
		BookResponse newBook = bookService.addNewBook(book);
		//BookRegistry registry = new BookRegistry(null, "New book added with id "+newBook.getId(), LocalDate.now());
		//this.registryService.addRegistry(registry);
		this.registryService.addRegistry(HttpMethod.POST, newBook.getId());
		return new ResponseEntity<BookResponse>(newBook, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookResponse> updateBook(@PathVariable long id, @RequestBody Book book){
		BookResponse updatedBook = bookService.updateBookById(id, book);
		
		this.registryService.addRegistry(HttpMethod.PUT, updatedBook.getId());
		
		return new ResponseEntity<BookResponse>(updatedBook, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public String deleteBook(@PathVariable long id){
		bookService.deleteBookById(id);
		this.registryService.addRegistry(HttpMethod.DELETE, id);
		return "Book deleted sucessfully";
	}

	@GetMapping("/title")
	public ResponseEntity<List<BookResponse>> getBookByTitle(@RequestParam String value){
		List<BookResponse> bookList = bookService.getBookByTitle(value);
		if(bookList == null || bookList.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		addLinksToList(bookList);
		ResponseEntity<List<BookResponse>> response = new ResponseEntity<List<BookResponse>>(bookList, HttpStatus.OK);
		return response;
	}

	@GetMapping("/searchbyeditorialid/{id}")
	public ResponseEntity<List<BookResponse>> getBookByEditorial(@PathVariable int id){
		Optional<Editorial> opt = editorialService.getEditorialById(id);
		if(opt.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Editorial editorial = opt.get();
		List<BookResponse> bookList = bookService.getBookByEditorial(editorial);
		if(bookList == null || bookList.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		addLinksToList(bookList);
		ResponseEntity<List<BookResponse>> response = new ResponseEntity<List<BookResponse>>(bookList, HttpStatus.OK);
		return response;
	}

	
	private static void addLinksToBook(BookResponse book){
		long id = book.getId();
		Link selfLink = linkTo(BookController.class).slash(id).withSelfRel();
		
		int editorialId = (int) book.getEditorialResponse().getId();
		Link editorialLink = linkTo(methodOn(EditorialController.class)
				.getEditorialById(editorialId)).withRel("editorialRef");
		
		book.add(selfLink).add(editorialLink);
	}

	private static void addLinksToList(List<BookResponse> books){
		for(BookResponse book: books) {
			addLinksToBook(book);
		}
	}


}
