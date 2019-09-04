package com.viktoriia.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viktoriia.model.Tournament;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class TournamentService {

    private static final Logger LOG = LoggerFactory.getLogger(TournamentService.class);

    private final String INDEX = "tournamentdata";
    private RestHighLevelClient client;
    private ObjectMapper objectMapper;

    @Autowired
    public TournamentService(ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
        this.objectMapper = objectMapper;
        this.client = restHighLevelClient;
    }

    public Tournament save(Tournament tournament) throws IOException {
        tournament.setId(UUID.randomUUID().toString());

        Map<String, Object> tournamentMapper = objectMapper.convertValue(tournament, Map.class);
        IndexRequest indexRequest = new IndexRequest(INDEX).id(tournament.getId()).source(tournamentMapper);

        client.index(indexRequest, RequestOptions.DEFAULT);

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
        }
        return null;
    }

    public void deleteTournamentById(String id) {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX, id);
        try {
            client.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (java.io.IOException e) {
            e.getLocalizedMessage();
        }
    }

    public List<Tournament> findAll() throws Exception {

        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse =
                client.search(searchRequest, RequestOptions.DEFAULT);

        return getSearchResult(searchResponse);
    }

    private List<Tournament> getSearchResult(SearchResponse response) {
        SearchHit[] searchHit = response.getHits().getHits();
        List<Tournament> profileDocuments = new ArrayList<>();

        if (searchHit.length > 0) {
            Arrays.stream(searchHit)
                    .forEach(hit -> profileDocuments
                            .add(objectMapper
                                    .convertValue(hit.getSourceAsMap(),
                                            Tournament.class))
                    );
        }
        return profileDocuments;
    }
}
