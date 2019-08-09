package com.viktoriia.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
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
	
	//Not working
	
	public Map<String, Object> updateTournamentById(String id, Tournament tournament){
		  UpdateRequest updateRequest = new UpdateRequest(INDEX, id)
		          .fetchSource(true);    // Fetch Object after its update
		  Map<String, Object> error = new HashMap<>();
		  error.put("Error", "Unable to update course");
		  try {
		    String tournamentJson = objectMapper.writeValueAsString(tournament);
		    updateRequest.doc(tournamentJson, XContentType.JSON);
		    UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
		    Map<String, Object> sourceAsMap = updateResponse.getGetResult().sourceAsMap();
		    return sourceAsMap;
		    
		  } catch (JsonMappingException e){
			  e.getMessage();
		  } catch (java.io.IOException e){
			  e.getLocalizedMessage();
		  }
		  return error;
		}
	
	public List<Tournament> findAll() throws Exception {
			GetRequest getRequest = new GetRequest(INDEX);
			List<Tournament> tournaments = new ArrayList<>();
			GetResponse getResponse;
			try {
				getResponse = client.get(getRequest, RequestOptions.DEFAULT);
				Map<String, Object> resultMap = getResponse.getSource();

				Tournament tournament = objectMapper.convertValue(resultMap, Tournament.class);
				tournaments.add(tournament);
			} catch (IOException e) {
				LOG.error("There isn't such course");
				e.printStackTrace();
			}
			return tournaments;
    }
	
	private List<Tournament> getSearchResult(SearchResponse response) {

		SearchHits hits = response.getHits();
		SearchHit[] searchHits = hits.getHits();

        List<Tournament> tournaments = new ArrayList<>();

        for (SearchHit hit : searchHits) {
        	tournaments.addAll((Collection<? extends Tournament>) hit.getSourceAsMap());
        }

        return tournaments;
    }

}
