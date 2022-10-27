package com.bookstore;


import com.bookstore.controller.BookController;
import com.bookstore.service.BookService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookstoreIntegracionTests {

    @Autowired
    BookController bookController;

    @MockBean
    BookService userService;
    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "http://localhost:8081/bookstore";

    @Test
    @Order(1)
    public void shouldGetAllBooks() throws Exception{
        System.out.println("Test - Get all books");
        mockMvc.perform(MockMvcRequestBuilders
                        .get(baseUrl + "/book")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }



}
