package com.bookstore;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.bookstore.model.BookRegistry;
import com.bookstore.repository.IBookRegistryRepository;


@SpringBootApplication
@EnableCaching
@EnableWebSecurity
//@EnableMongoRepositories(basePackageClasses = IBookRegistryRepository.class)
public class BookstoreApplication implements CommandLineRunner{

	@Autowired
	IBookRegistryRepository repo;
	
    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		repo.insert(new BookRegistry(null, "Algo1", LocalDate.now()));
		repo.insert(new BookRegistry(null, "Algo2", LocalDate.now()));
		repo.insert(new BookRegistry(null, "Algo3", LocalDate.now()));
		repo.insert(new BookRegistry(null, "Algo4", LocalDate.now()));
		System.out.println(repo.findAll());
	}

}
