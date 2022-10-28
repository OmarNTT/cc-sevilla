package com.bookstore.Repository;


import com.bookstore.model.BookRegistry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IBookRegistryRepository extends MongoRepository<BookRegistry, String> {
}
