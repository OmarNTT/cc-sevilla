package com.bookstore.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.bookstore.model.BookRegistry;
import com.bookstore.repository.IBookRegistryRepository;
import com.bookstore.response.BookRegistryResponse;

@Service
public class BookRegistryService {

	@Autowired
	IBookRegistryRepository registryRepo;
	
	@Cacheable("registries")
	public List<BookRegistryResponse> getAllRegistries(){
		return registryRepo.findAll().stream()
				.map(reg->new BookRegistryResponse(reg))
				.collect(Collectors.toList());
	}
	
	@Cacheable(value = "registries", key = "#id")
	public Optional<BookRegistryResponse> getBookRegistryById(String id){
		Optional<BookRegistry> book = registryRepo.findById(id);
		if(book.isPresent()){
			return Optional.of(new BookRegistryResponse(book.get()));
		}
		return Optional.empty();
	}
	
	@CacheEvict(value = "registries", allEntries = true)
	public BookRegistryResponse addRegistry(BookRegistry registry){
		return new BookRegistryResponse(this.registryRepo.save(registry));
	}
	
	@CacheEvict(value = "registries", allEntries = true)
	public BookRegistryResponse updateBookRegistryById(String id,BookRegistry registry){
		Optional<BookRegistry> opt = this.registryRepo.findById(id);
		if(opt.isEmpty())
			return null;
		
		registry.setId(id);
		return new BookRegistryResponse(this.registryRepo.save(registry));
	}
	
	@CacheEvict(value = "registries", allEntries = true)
	public void deleteRegistry(String id){
		this.registryRepo.deleteById(id);
	}

	public void addRegistry(HttpMethod method, long id) {
		String msg;
		switch(method) {
		case DELETE: 
			msg = "Book with id " + id + " deleted succesfully"; break;
		case POST: 
			msg = "New book added with id "+id; break;
		case PUT:
			msg = "Book with id "+ id + " updated";break;
		default:
			msg = "";
		}
		BookRegistry br = new BookRegistry(msg, LocalDate.now());
		this.addRegistry(br);
	}
}
