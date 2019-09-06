package com.viktoriia.controller;

import com.viktoriia.model.Tournament;
import com.viktoriia.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tournament")
public class TournamentRestController {

    @Autowired
    private TournamentService service;

    @GetMapping
    public ResponseEntity<List<Tournament>> findAll() throws Exception {
        List<Tournament> tournaments = service.findAll();
        if (tournaments != null) {
            return new ResponseEntity<>(tournaments, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> findById(@PathVariable("id") String id) {
        Tournament tournament = service.getTournamentById(id);
        if (tournament != null) {
            return new ResponseEntity<>(tournament, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Tournament> save(@RequestBody Tournament tournament) throws IOException {
        if (tournament.getTitle() != null && tournament.getDate() != null) {
            service.save(tournament);
            return new ResponseEntity<>(tournament, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        service.deleteTournamentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
