package com.bookstore.response;

import com.bookstore.model.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter @Setter @ToString @EqualsAndHashCode
public class BookResponse extends RepresentationModel<BookResponse>{

    @JsonProperty("Book_Id")
    private long id;
    @JsonProperty("Book_Name")
    private String title ;
    @JsonProperty("Book_Author")
    private String author;
    @JsonProperty("Book_Publish_Date")
    private LocalDate publishDate;
    @JsonProperty("Book_Total_Pages")
    private int pages;
    @JsonProperty("Book_Description")
    private String description;
    @JsonProperty("Book_Editorial")
    private EditorialResponse editorialResponse;

    public BookResponse(Book book){
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publishDate = book.getPublishDate();
        this.pages = book.getPages();
        this.description = book.getDescription();
        this.editorialResponse = new EditorialResponse(book.getEditorial().getId(),book.getEditorial().getName());
    }
    
    @JsonProperty("links")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Override
	public Links getLinks() {
		return super.getLinks();
	}
    
}

