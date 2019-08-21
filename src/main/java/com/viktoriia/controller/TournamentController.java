package com.viktoriia.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viktoriia.model.Tournament;
import com.viktoriia.service.TournamentService;

//add auth
@RestController
@RequestMapping("/tournament")
public class TournamentController {
	
	@Autowired
	private TournamentService service;
	
	
	@GetMapping
	public ResponseEntity<List<Tournament>> findAll() throws Exception {
		List<Tournament> tournaments = service.findAll();
		if(!tournaments.equals(null)) {
			return new ResponseEntity<List<Tournament>>(tournaments, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tournament> findById(@PathVariable("id") String id) throws IOException {
		Tournament tournament = service.getTournamentById(id);
		if(!tournament.equals(null)) {
			return new ResponseEntity<Tournament>(tournament, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<Tournament> save(@RequestBody Tournament tournament) throws IOException {
		if(!tournament.getTitle().equals(null) && !tournament.getDate().equals(null)) {
			service.save(tournament);	
			return new ResponseEntity<Tournament>(tournament, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		service.deleteTournamentById(id);
		return new ResponseEntity<Void>(HttpStatus.OK); 
	}
}
