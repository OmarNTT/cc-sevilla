package com.bookstore.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.bookstore.model.BookRegistry;
import com.bookstore.repository.IBookRegistryRepository;
import com.bookstore.response.BookRegistryResponse;

@Service
public class BookRegistryService {
	
	@Autowired
	IBookRegistryRepository registryRepo;
	
	public List<BookRegistryResponse> getAllRegistries(){
		return registryRepo.findAll().stream()
				.map(reg->new BookRegistryResponse(reg))
				.collect(Collectors.toList());
	}
	
	public BookRegistryResponse addRegistry(@RequestBody BookRegistry registry){
		return new BookRegistryResponse(this.registryRepo.save(registry));
	}
	
	public void deleteRegistry(long id){
		this.registryRepo.deleteById(id);;
	}
}
