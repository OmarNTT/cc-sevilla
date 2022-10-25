package com.bookstore.Repository;

import com.bookstore.model.Book;
import com.bookstore.model.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IBookRepository extends JpaRepository<Book,Long> {

    List<Book> findBookByTitle(String title);
    List<Book> findBookByEditorial(Editorial editorial);

}
