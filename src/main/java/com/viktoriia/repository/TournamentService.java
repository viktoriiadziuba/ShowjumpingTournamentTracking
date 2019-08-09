package com.viktoriia.repository;

import java.io.IOException; 
import java.util.Map;
import java.util.UUID;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viktoriia.config.ElasticsearchConfig;
import com.viktoriia.model.Tournament;

@Service
public class TournamentService {
	
	private static final Logger LOG = LoggerFactory.getLogger(ElasticsearchConfig.class);

	private final String INDEX = "tournamentdata";
	private RestHighLevelClient client;
	private ObjectMapper objectMapper;
	
	public TournamentService(ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
		this.objectMapper = objectMapper;
		this.client = restHighLevelClient;
	}
	
	public Tournament save(Tournament tournament) throws IOException {
		UUID uuid = UUID.randomUUID();
		tournament.setId(uuid.toString());
        Map<String, Object> tournamentMapper = objectMapper.convertValue(tournament, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX).id(tournament.getId()).source(tournamentMapper);
		
			IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
		
		return tournament;
	}
	
	public Tournament getTournamentById(String id) {
		GetRequest getRequest = new GetRequest(INDEX, id);
		
		GetResponse getResponse;
		try {
			getResponse = client.get(getRequest, RequestOptions.DEFAULT);
			Map<String, Object> resultMap = getResponse.getSource();

	        return objectMapper
	                .convertValue(resultMap, Tournament.class);
		} catch (IOException e) {
			LOG.error("There isn't such tournament");
			e.printStackTrace();
		}
        return null;
	}
	
	public void deleteTournamentById(String id) {
		  DeleteRequest deleteRequest = new DeleteRequest(INDEX, id);
		  try {
		    DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
		  } catch (java.io.IOException e){
		   		e.getLocalizedMessage();
		  }
	}
}
