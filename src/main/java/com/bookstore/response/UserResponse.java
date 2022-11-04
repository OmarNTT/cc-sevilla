package com.bookstore.response;

import com.bookstore.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class UserResponse {
	
	private long id;
    private String username;
    private String password;
    private String role;
    
    public UserResponse (User user) {
    	this.id = user.getId();
    	this.username = user.getUsername();
    	this.password = user.getPassword();
    	this.role = user.getRole();
    }
}
