package com.viktoriia.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viktoriia.model.Tournament;
import com.viktoriia.service.impl.TournamentServiceImpl;

@RestController
@RequestMapping("/tournament")
public class TournamentController {
	
	@Autowired
	private TournamentServiceImpl service;

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Tournament>> findById(@PathVariable("id") String id) {
		return new ResponseEntity<Optional<Tournament>>(service.findById(id), HttpStatus.OK);
	}
	
}
