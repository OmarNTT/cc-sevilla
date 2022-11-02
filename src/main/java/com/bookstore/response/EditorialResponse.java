package com.bookstore.response;


import com.bookstore.model.Editorial;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

@Data
public class EditorialResponse extends RepresentationModel<EditorialResponse> implements Serializable{

	@JsonProperty("Editorial_Id")
	private long id;
	@JsonProperty("Editorial_Name")
	private String name;
	@JsonProperty("Editorial_Books")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Set<BookResponse> booksList;

	public EditorialResponse(Editorial editorial) {
		this.id = editorial.getId();
		this.name = editorial.getName();
		this.booksList = editorial.getBooksList().stream()
				.map(book->new BookResponse(book))
				.collect(Collectors.toSet());
	}

	public EditorialResponse(long id,String name){
		this.id = id;
		this.name = name;
	}

	@JsonProperty("links")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Override
	public Links getLinks() {
		return super.getLinks();
	}


}
