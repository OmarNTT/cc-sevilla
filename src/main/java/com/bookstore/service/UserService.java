package com.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.model.User;
import com.bookstore.repository.IBookRegistryRepository;
import com.bookstore.repository.IUserRepository;

@Service
public class UserService {
	
	@Autowired
	IUserRepository registryRepo;
	
	public void saveUser(User u) {
		this.registryRepo.save(u);
	}
	
	

}
