package com.viktoriia.repository;

import org.springframework.stereotype.Repository;

import com.viktoriia.model.Tournament;

@Repository
public interface TournamentRepository {
	
	Tournament findByTitle(String title);
	
	Tournament getById(String id);

}
