package com.viktoriia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.viktoriia.model.Tournament;

@Repository
public interface TournamentRepository extends ElasticsearchRepository<Tournament, String> {
	
	Page<Tournament> findByTitle(String title, Pageable pageable);

}
