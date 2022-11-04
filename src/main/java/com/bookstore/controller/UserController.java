package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.User;
import com.bookstore.repository.IUserRepository;
import com.bookstore.response.BookResponse;
import com.bookstore.service.BookService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	IUserRepository userRepo;
	
	@GetMapping("")
	public ResponseEntity<List<User>> geAlltUser(){
		List<User> u = userRepo.findAll();
		if(u == null) {
			return new ResponseEntity<>(HttpStatus.FOUND);
		}
		ResponseEntity<List<User>> response = new ResponseEntity<List<User>>(u, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/login")
	public ResponseEntity<User> getUser(@RequestBody String username, @RequestBody String password){
		User u = userRepo.findByUsernameAndPassword(username, password);
		if(u == null) {
			return new ResponseEntity<>(HttpStatus.FOUND);
		}
		ResponseEntity<User> response = new ResponseEntity<User>(u, HttpStatus.OK);
		return response;
	}

}
