package com.viktoriia.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viktoriia.model.Tournament;
import com.viktoriia.repository.TournamentService;

//add auth
@RestController
@RequestMapping("/tournament")
public class TournamentController {
	
	private TournamentService service;
	
	@Autowired
	public TournamentController(TournamentService service) {
		this.service = service;
	}
	
	@GetMapping("/{id}")
	public Tournament findById(@PathVariable("id") String id) throws IOException {
		return service.getTournamentById(id);
	}
	
	@PostMapping
	public Tournament save(@RequestBody Tournament tournament) throws IOException {
		return service.save(tournament);	
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") String id) {
		service.deleteTournamentById(id);
	}
}
