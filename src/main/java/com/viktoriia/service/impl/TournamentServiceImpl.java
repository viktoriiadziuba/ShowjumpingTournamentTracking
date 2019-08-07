package com.viktoriia.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.viktoriia.model.Tournament;
import com.viktoriia.repository.TournamentRepository;
import com.viktoriia.service.TournamentService;

@Service
public class TournamentServiceImpl implements TournamentService {
	
	@Autowired
	private TournamentRepository repository;

	@Override
	public Tournament save(Tournament tournament) {
		return repository.save(tournament);
	}

	@Override
	public Optional<Tournament> findById(String id) {
		return repository.findById(id);
	}

	@Override
	public Iterable<Tournament> findAll() {
		return repository.findAll();
	}

	@Override
	public Page<Tournament> findByTitle(String title, Pageable pageable) {
		return repository.findByTitle(title, pageable);
	}

	@Override
	public long count() {
		return repository.count();
	}

	@Override
	public void delete(Tournament tournament) {
		repository.delete(tournament);
	}

}
