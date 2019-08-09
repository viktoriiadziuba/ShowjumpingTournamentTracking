package com.viktoriia.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude; 

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Tournament {

	private String id;
	private String title;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	private String description;
	

	public Tournament() {
		
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
