package com.viktoriia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration(exclude={ElasticsearchAutoConfiguration.class})
@ComponentScan
public class ShowjumpingTournamentTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShowjumpingTournamentTrackingApplication.class, args);
	}

}
