package com.bookstore.service.implemented;


import com.bookstore.Repository.IBookRepository;
import com.bookstore.Response.BookResponse;
import com.bookstore.model.Book;
import com.bookstore.model.Editorial;
import com.bookstore.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService {


    @Autowired
    private IBookRepository iBookRepository;

    @Override
    @Cacheable(value="books")
    public List<BookResponse> getAllBooks(){
        try{
            System.out.println("🟩🟩Fetching books from database");
            List<Book> books = iBookRepository.findAll();
            List<BookResponse> bookResponses = new ArrayList<>();
            books.stream().forEach(book -> {
                bookResponses.add(new BookResponse(book));
            });
            return bookResponses;
        }catch (Exception e){
            System.out.println("🟥🟥Error fetching books from database");
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    @Cacheable(value="books",key="#id")
    public Optional<BookResponse> getBookById(long id){
        System.out.println("🟩🟩 Called Get Book By Id from Database");
        Optional<Book> book = iBookRepository.findById(id);
        if(book.isPresent()){
            return Optional.of(new BookResponse(book.get()));
        }
        return Optional.empty();
    }

    @Override
    @Cacheable(value="books")
    public List<BookResponse> getBookByTitle(String title){
        List<Book> books = iBookRepository.findBookByTitle(title);
        List<BookResponse> bookResponses = new ArrayList<>();
        books.stream().forEach(book -> {
            bookResponses.add(new BookResponse(book));
        });
        return bookResponses;
    }

    @Override
    @Cacheable(value="books")
    public List<BookResponse> getBookByEditorial(Editorial editorial){
        List<Book> books = iBookRepository.findBookByEditorial(editorial);
        List<BookResponse> bookResponses = new ArrayList<>();
        books.stream().forEach(book -> {
            bookResponses.add(new BookResponse(book));
        });
        return bookResponses;
    }

    @Override
    @CacheEvict(value = "books", allEntries=true)
    public BookResponse addNewBook(Book book){
        return new BookResponse(iBookRepository.save(book));
    }

    @Override
    @CacheEvict(value = "books", allEntries=true)
    public BookResponse updateBookById(long id,Book book){
        Optional<Book> bookOptional = iBookRepository.findById(id);
        if(bookOptional.isPresent()){
            Book bookToUpdate = bookOptional.get();
            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setEditorial(book.getEditorial());
            bookToUpdate.setAuthor(book.getAuthor());
            bookToUpdate.setPages(book.getPages());
            bookToUpdate.setDescription(book.getDescription());
            bookToUpdate.setPublishDate(book.getPublishDate());
            return new BookResponse(iBookRepository.save(bookToUpdate));
        }
        return null;

    }

    @Override
    @CacheEvict(value = "books", allEntries=true)
    public void deleteBookById(long id){
        iBookRepository.deleteById(id);
    }


}
