package com.viktoriia.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "showjumping", type = "tournament")
public class Tournament {

	@Id
	private String id;
	
	private String title;

	private LocalDate date;
	
	private String description;
	

	public Tournament() {
		
	}

	public Tournament(String id, String title, LocalDate date, String description) {
		super();
		this.id = id;
		this.title = title;
		this.date = date;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return String.format("[Toutnament "
				+ "id=%d "
				+ "title=%s "
				+ "date=%tD "
				+ "description=%s]", id, title, date, description);
	}
	
	
}
