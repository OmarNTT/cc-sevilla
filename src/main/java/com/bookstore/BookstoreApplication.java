package com.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;



@SpringBootApplication
public class BookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    CommandLineRunner runner(KafkaTemplate<String,String> kafkaTemplate){
        return args -> {
            kafkaTemplate.send("bookstore","Hello World");
        };
    }

}
