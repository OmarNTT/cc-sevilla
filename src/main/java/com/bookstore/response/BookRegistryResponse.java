package com.bookstore.response;

import java.time.LocalDate;

import com.bookstore.model.BookRegistry;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookRegistryResponse {
	
	private String id;
	private String message;
	private LocalDate date;
	
	public BookRegistryResponse(BookRegistry registry) {
		this.id = registry.getId();
		this.message = registry.getMessage();
		this.date = registry.getDate();
	}

}
