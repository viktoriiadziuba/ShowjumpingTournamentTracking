package com.viktoriia.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import org.springframework.format.annotation.DateTimeFormat;

@Document(indexName = "tournamentdata")
public class Tournament {

	@Id
	private String tournamentId;
	private String title;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	private String description;

	public Tournament() {
		
	}

	public String getId() {
		return tournamentId;
	}

	public void setId(String id) {
		this.tournamentId = id;
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
				+ "id=%s "
				+ "title=%s "
				+ "date=%tD "
				+ "description=%s]", tournamentId, title, date, description);
	}
	
}
