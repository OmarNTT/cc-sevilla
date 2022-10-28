package com.bookstore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.model.BookRegistry;

@Repository
public interface IBookRegistryRepository extends MongoRepository<BookRegistry, Long>{

}
