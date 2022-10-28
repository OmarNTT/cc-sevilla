package com.bookstore;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.bookstore.service.BookService;


@WebMvcTest
public class BookstoreIntegracionTests {
    @Autowired
    private TestRestTemplate template;
    
    private String baseUrl = "http://localhost:8081/bookstore";
    private final int OK = 200;
    private final int CREATED = 201;
    @Nested
    @DisplayName("Book Entity Tests")
    class BookController{
        @Test
        public void getAllBooks() {
            ResponseEntity<String> response = template.getForEntity(baseUrl+"/book", String.class);
            assertEquals(OK, response.getStatusCodeValue());
            assertNotNull(response.getBody());
        }
        @Test
        public void getBookById() {
            ResponseEntity<String> response = template.getForEntity(baseUrl+"/book/1", String.class);
            assertEquals(OK, response.getStatusCodeValue());
            assertNotNull(response.getBody());
        }
        @Test
        public void getBooksByEditorialId() {
            ResponseEntity<String> response = template.getForEntity(baseUrl+"/book/searchbyeditorialid/2", String.class);
            assertEquals(OK, response.getStatusCodeValue());
            assertNotNull(response.getBody());
        }
        @Test
        public void getBooksByTitle() {
            ResponseEntity<String> response = template.getForEntity(baseUrl+"/book/title?value=HarryPotter", String.class);
            assertEquals(OK, response.getStatusCodeValue());
            assertNotNull(response.getBody());
        }
        @Test
        public void addBook() {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject bookObject = new JSONObject();
            try {
                bookObject.put("title", "Viaje al centro de la Tierra");
                bookObject.put("description", "El profesor Otto Lidenbrock ha descubierto un pergamino que contiene texto "
                        + "en clave. Sus intentos por descifrarlo fallan, hasta que su sobrino Axel encuentra la manera de "
                        + "entenderlo. El mensaje explica como llegar al centro de la Tierra.");
                bookObject.put("publishDate", "2010-11-11");
                bookObject.put("pages",20);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ResponseEntity<String>  response = template.postForEntity(baseUrl+"/book", new HttpEntity<String>(bookObject.toString(), headers), String.class);
            assertEquals(response.getStatusCodeValue(), OK);
            assertNotNull(response.getBody());
        }
        @Test
        public void updateBook() {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject bookObject = new JSONObject();
            try {
                bookObject.put("title", "Viaje al centro de la Tierra");
                bookObject.put("description", "El profesor Otto Lidenbrock ha descubierto un pergamino que contiene texto "
                        + "en clave. Sus intentos por descifrarlo fallan, hasta que su sobrino Axel encuentra la manera de "
                        + "entenderlo. El mensaje explica como llegar al centro de la Tierra.");
                bookObject.put("publishDate", "2010-11-11");
                bookObject.put("pages",20);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ResponseEntity<Void> response = template.exchange(baseUrl+"/book/1", HttpMethod.PUT,
                    new HttpEntity<String>(bookObject.toString(), headers), Void.class);
            assertEquals(response.getStatusCodeValue(), OK);
            assertNotNull(response.getBody());
            ResponseEntity<String> responseGet = template.getForEntity(baseUrl+"/book/1", String.class);
        }
        @Test
        public void deleteBookById() {
            ResponseEntity<String> response = template.exchange(baseUrl+"/book/1", HttpMethod.DELETE,
                    HttpEntity.EMPTY, String.class);
            assertEquals(response.getStatusCodeValue(), CREATED);
        }
    }
}