package com.bookstore.model;

import java.time.LocalDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@AllArgsConstructor 
@NoArgsConstructor
@ToString

@Document
public class BookRegistry {
	@Id
	private String id;
	private String message;
	private LocalDate date;
	
	public BookRegistry(String msg, LocalDate date) {
		this.message = msg; 
		this.date = date;
	}
}

