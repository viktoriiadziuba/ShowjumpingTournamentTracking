package com.viktoriia.controller;

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

//add auth
@RestController
@RequestMapping("/tournament")
public class TournamentController {
	
//	@GetMapping("/{id}")
//	public ResponseEntity<Tournament> findById(@PathVariable("id") String id) {
//		return new ResponseEntity<Tournament>(service.findById(id), HttpStatus.OK);
//	}
//	
//	@GetMapping
//	public ResponseEntity<List<Tournament>> getAll() {
//		List<Tournament> tournaments = (List<Tournament>) service.findAll();
//		return new ResponseEntity<List<Tournament>>(tournaments, HttpStatus.OK);
//	}
//	
//	@PostMapping
//	public ResponseEntity<Void> save(@RequestBody Tournament tournament) {
//		service.save(tournament);
//		return new ResponseEntity<>(HttpStatus.CREATED);
//	}
//	
//	@GetMapping("{title}")
//	public ResponseEntity<Tournament> findByTitle(@PathVariable("title") String title) {
//		Tournament tournament = service.findByTitle(title);
//		return new ResponseEntity<Tournament>(tournament, HttpStatus.OK);
//	}
//	
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
//		Tournament tournament = service.findById(id);
//		if(tournament != null) {
//			service.delete(tournament);
//			return new ResponseEntity<Void>(HttpStatus.OK);
//		}
//		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
//	}
}
