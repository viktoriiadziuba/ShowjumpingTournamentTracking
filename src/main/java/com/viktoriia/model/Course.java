package com.viktoriia.model;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Parent;

@Document(indexName = "showjumping", type = "course")
public class Course {

	@Id
	private String id;
	
	private int height;
	
	private String description;
	
	private LocalTime time;
	
	@Field(type = FieldType.Nested)
	private List<Participant> participants;
	
	@Parent(type = "tournament")
	private String tournamentId;
	

	public Course() {
		
	}

	public Course(String id, int height, String description, LocalTime time, List<Participant> participants,
			String tournamentId) {
		super();
		this.id = id;
		this.height = height;
		this.description = description;
		this.time = time;
		this.participants = participants;
		this.tournamentId = tournamentId;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

	public String getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(String tournamentId) {
		this.tournamentId = tournamentId;
	}

	@Override
	public String toString() {
		return String.format("[Course "
				+ "id=%d "
				+ "height=%d "
				+ "description=%s "
				+ "time=%tH "
				+ "tournamentId=%s "
				+ "\n" + "%s]", id, height, description, time, tournamentId, participants);
				
	}
	
	
}
