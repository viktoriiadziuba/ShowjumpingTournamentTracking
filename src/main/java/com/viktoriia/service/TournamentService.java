package com.viktoriia.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.viktoriia.model.Tournament;

public interface TournamentService {
	
	Tournament save(Tournament tournament);
	
	Optional<Tournament> findById(String id);
	
	Iterable<Tournament> findAll();
	
	Page<Tournament> findByTitle(String title, Pageable pageable);
	
	long count();
	
	void delete(Tournament tournament);

}
