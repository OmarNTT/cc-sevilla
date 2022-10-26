package com.bookstore;

import com.bookstore.Repository.IBookRepository;
import com.bookstore.Repository.IEditorialRepository;
import com.bookstore.model.Book;
import com.bookstore.model.Editorial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;


//Ejecutar con H2 Database

@DataJpaTest
class BookstoreApplicationTests {

    @Autowired
    IBookRepository iBookRepository;

    @Autowired
    IEditorialRepository iEditorialRepository;


    @BeforeEach
    public void init(){
        Book book1 = new Book(1,"TestTitle1","TestAuthor1", LocalDate.now(),11,"TestDescription1","CustomTitle1",null);
        Book book2 = new Book(2,"TestTitle2","TestAuthor2", LocalDate.now(),12,"TestDescription2","CustomTitle2",null);

        Editorial editorial1 = new Editorial(1,"Editorial1", Set.of(book1));
        Editorial editorial2 = new Editorial(2,"Editorial2",Set.of(book2));

        iBookRepository.save(book1);
        iBookRepository.save(book2);
        iEditorialRepository.save(editorial1);
        iEditorialRepository.save(editorial2);
    }

    @Nested
    @DisplayName("Book Methods")
    class BookMethods{

        /*
         * getAllBooks
         * getBookById
         * getBookByTitle
         * getBookByEditorial
         * addNewBook
         * updateBookById
         * deleteBookById
         * */


        @Test
        @Order(1)
        public void getAllBooks(){
            List<Book> response = iBookRepository.findAll();
            System.out.println(response);
            assertThat(response).isNotEmpty();
        }

        @Test
        @Order(2)
        public void getBookById(){
            List<Book> request = iBookRepository.findAll();
            Long idToFound = request.get(0).getId();

            Optional<Book> response = iBookRepository.findById(idToFound);
            assertTrue(response.isPresent());
        }

        @Test
        @Order(3)
        public void getByTitle(){
            List<Book> request = iBookRepository.findAll();
            String titleToFound = request.get(0).getTitle();

            List<Book> response = iBookRepository.findBookByTitle(titleToFound);
            assertThat(response).isNotEmpty();
        }

        @Test
        @Order(4)
        public void getByEditorial(){
            List<Book> request = iBookRepository.findAll();
            Editorial editorialToFound = request.get(0).getEditorial();

            List<Book> response = iBookRepository.findBookByEditorial(editorialToFound);
            assertThat(response).isNotEmpty();
        }

        @Test
        @Order(5)
        public void addNewBook(){

            Book book = new Book("TestTitle3","TestAuthor3", LocalDate.now(),13,"TestDescription3",null);
            int sizeBeforeInsert = iBookRepository.findAll().size();
            iBookRepository.save(book);
            int sizeAfterInsert = iBookRepository.findAll().size();

            assertEquals(sizeBeforeInsert,sizeAfterInsert-1);

        }

        @Test
        @Order(5)
        public void deleteBookById(){

            Book bookToDelete = iBookRepository.findAll().get(0);
            int sizeBeforeDeleted = iBookRepository.findAll().size();
            iBookRepository.deleteById(bookToDelete.getId());
            int sizeAfterDeleted = iBookRepository.findAll().size();
            assertEquals(sizeBeforeDeleted,sizeAfterDeleted+1);
        }

        @Test
        @Order(6)
        public void updateBookById(){

            Book bookToUpdate = iBookRepository.findAll().get(0);
            String titleBeforeUpdate = bookToUpdate.getTitle();
            bookToUpdate.setTitle("NewTitleOfBook");

            Book bookUpdated = iBookRepository.save(bookToUpdate);
            String titleAfterUpdate = bookUpdated.getTitle();
            assertNotEquals(titleBeforeUpdate,titleAfterUpdate);

        }
    }


    @Nested
    @DisplayName("Editorial Methods")
    class EditorialMethods{

        @Test
        public void addNewEditorial() {
            Editorial newEditorial = new Editorial();
            newEditorial.setName("Cerbero");
            int sizeBeforeSave = iEditorialRepository.findAll().size();
            iEditorialRepository.save(newEditorial);
            int sizeAfterSave = iEditorialRepository.findAll().size();
            assertEquals(sizeAfterSave-1, sizeBeforeSave);
        }

        @Test
        public void deleteEditorialById() {
            long id = iEditorialRepository.findAll().get(0).getId();
            int sizeBeforeSave = iEditorialRepository.findAll().size();
            iEditorialRepository.deleteById(id);
            int sizeAfterSave = iEditorialRepository.findAll().size();

            assertEquals(sizeAfterSave+1, sizeBeforeSave);
        }

        @Test
        public void getAllEditorials() {
            List<Editorial> allEditorials = iEditorialRepository.findAll();
            assertThat(allEditorials).isNotEmpty();
        }

        @Test
        public void getEditorialById() {
            long id = iEditorialRepository.findAll().get(0).getId();
            Optional<Editorial> optEditorial = iEditorialRepository.findById(id);
            assertTrue(optEditorial.isPresent());
            optEditorial.ifPresent(
                    (editorial)->assertEquals(id, editorial.getId())
            );
        }

        @Test
        public void getEditorialByName() {
            String name = iEditorialRepository.findAll().get(0).getName();
            List<Editorial> editorials = iEditorialRepository.findEditorialByName(name);
            assertThat(editorials).isNotEmpty();
        }

        @Test
        public void updateEditorial() {
            Editorial editorial = iEditorialRepository.findAll().get(0);
            String oldName = editorial.getName();
            String newName = "NewEditorialName";
            editorial.setName(newName);
            Editorial updatedEditorial = iEditorialRepository.save(editorial);

            assertNotEquals(updatedEditorial.getName(), oldName);
            assertEquals(updatedEditorial.getName(), newName);
        }

    }


}