package com.viktoriia;

import org.springframework.boot.SpringApplication; 
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.viktoriia.repository")
public class ShowjumpingTournamentTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShowjumpingTournamentTrackingApplication.class, args);
	}

}
