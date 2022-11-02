package com.bookstore.service;


import com.bookstore.model.Book;
import com.bookstore.model.Editorial;
import com.bookstore.repository.IBookRepository;
import com.bookstore.repository.IEditorialRepository;
import com.bookstore.response.BookResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

	@Autowired
	IBookRepository iBookRepository;

	@Autowired
	IEditorialRepository editorialRepository;
	
	@Cacheable("books")
	public List<BookResponse> getAllBooks(){
		System.out.println("Primera vez");
		List<Book> books = iBookRepository.findAll();
		List<BookResponse> bookResponses = new ArrayList<>();
		books.stream().forEach(book -> {
			bookResponses.add(new BookResponse(book));
		});
		return bookResponses;
	}
	
	@Cacheable(value = "books", key = "#id")
	public Optional<BookResponse> getBookById(long id){
		Optional<Book> book = iBookRepository.findById(id);
		if(book.isPresent()){
			return Optional.of(new BookResponse(book.get()));
		}
		return Optional.empty();
	}
	
	@Cacheable("books")
	public List<BookResponse> getBookByTitle(String title){
		List<Book> books = iBookRepository.findBookByTitle(title);
		List<BookResponse> bookResponses = new ArrayList<>();
		books.stream().forEach(book -> {
			bookResponses.add(new BookResponse(book));
		});
		return bookResponses;
	}
	
	@Cacheable("books")
	public List<BookResponse> getBookByEditorial(long editorialId){
		Optional<Editorial> opt = editorialRepository.findById(editorialId);
		if(opt.isEmpty()) {
			return null;
		}
		Editorial editorial = opt.get();
		List<Book> books = iBookRepository.findBookByEditorial(editorial);
		List<BookResponse> bookResponses = new ArrayList<>();
		books.stream().forEach(book -> {
			bookResponses.add(new BookResponse(book));
		});
		return bookResponses;
	}
	
	@CacheEvict(value = "books", allEntries = true)
	public BookResponse addNewBook(Book book){
		return new BookResponse(iBookRepository.save(book));
	}

	@CacheEvict(value = "books", allEntries = true)
	public BookResponse updateBookById(long id,Book book){
		Optional<Book> bookOptional = iBookRepository.findById(id);
		if(bookOptional.isPresent()){
			Book bookToUpdate = bookOptional.get();
			bookToUpdate.setTitle(book.getTitle());
			bookToUpdate.setEditorial(book.getEditorial());
			bookToUpdate.setAuthor(book.getAuthor());
			bookToUpdate.setPages(book.getPages());
			bookToUpdate.setDescription(book.getDescription());
			bookToUpdate.setPublishDate(book.getPublishDate());
			return new BookResponse(iBookRepository.save(bookToUpdate));
		}
		return null;

	}
	
	@CacheEvict(value = "books", allEntries = true)
	public void deleteBookById(long id){
		iBookRepository.deleteById(id);
	}

}
