package com.bookstore.listeners;

import com.bookstore.service.implemented.BookRegistryService;
import org.springframework.stereotype.Component;

import com.bookstore.model.BookRegistry;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

@Component
public class RegistryKafkaListener {

    @Autowired
    private BookRegistryService registryService;

    @KafkaListener(
            topics = "bookstore_registry",
            groupId = "test"
    )
    void listener(String data) {
        System.out.println("\nData received:\n"+data);
        BookRegistry bookRegistry = new BookRegistry(data, new Date());
        this.registryService.postBookRegistry(bookRegistry);
    }

}
