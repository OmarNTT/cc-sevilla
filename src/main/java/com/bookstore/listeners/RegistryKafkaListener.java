package com.bookstore.listeners;

import org.springframework.stereotype.Component;

import com.bookstore.model.BookRegistry;
import com.bookstore.service.BookRegistryService;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

@Component
public class RegistryKafkaListener {
	
	@Autowired 
	private BookRegistryService registryService;
	
	@KafkaListener(
			topics = "book_registry",
			groupId = "test"
	)
	void listener(String data) {
		System.out.println("\nData received:\n"+data);
		BookRegistry br = new BookRegistry(data, LocalDate.now());
		this.registryService.addRegistry(br);
	}

}
