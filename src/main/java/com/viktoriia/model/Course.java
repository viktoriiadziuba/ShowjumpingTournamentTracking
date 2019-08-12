package com.viktoriia.model;

import java.time.LocalTime;  
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Course {

	private String id;
	private int height;
	private String description;
	
	@JsonFormat(pattern = "HH:mm")
	private LocalTime time;
	private List<Participant> participants;
	private String tournamentTitle;
	

	public Course() {
		
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

	public String getTournamentTitle() {
		return tournamentTitle;
	}

	public void setTournamentTitle(String tournamentTitle) {
		this.tournamentTitle = tournamentTitle;
	}

	@Override
	public String toString() {
		return String.format("[Course "
				+ "id=%d "
				+ "height=%d "
				+ "description=%s "
				+ "time=%tH "
				+ "tournamentId=%s "
				+ "\n" + "%s]", id, height, description, time, tournamentTitle, participants);
				
	}
	
	
}
