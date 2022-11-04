package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.model.BookRegistry;
import com.bookstore.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
	
	User findByUsernameAndPassword(String username, String password);

}
