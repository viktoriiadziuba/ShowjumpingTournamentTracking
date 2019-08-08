package com.viktoriia.repository;

import java.io.IOException; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viktoriia.config.ElasticsearchConfig;
import com.viktoriia.model.Course;

@Service
public class CourseService {
	
	private static final Logger LOG = LoggerFactory.getLogger(ElasticsearchConfig.class);

	private final String INDEX = "_coursedata";
	private final String TYPE = "course";
	private RestHighLevelClient restHighLevelClient;
	private ObjectMapper objectMapper;
	
	public CourseService(ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
		this.objectMapper = objectMapper;
		this.restHighLevelClient = restHighLevelClient;
	}
	
	public Course save(Course course) throws IOException {
		course.setId(UUID.randomUUID().toString());
		Map<String, Object> documentMapper = objectMapper.convertValue(course, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX).type(TYPE).id(course.getId()).source(documentMapper);
		
			IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
		
		return course;
	}
	
	public Course getCourseById(String id) {
		GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
		
		GetResponse getResponse;
		try {
			getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
			Map<String, Object> resultMap = getResponse.getSource();

	        return objectMapper
	                .convertValue(resultMap, Course.class);
		} catch (IOException e) {
			LOG.error("There isn't such course");
			e.printStackTrace();
		}
        return null;
	}
	
	public Map<String, Object> updateCourseById(String id, Course book){
		  UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id)
		          .fetchSource(true);    // Fetch Object after its update
		  Map<String, Object> error = new HashMap<>();
		  error.put("Error", "Unable to update course");
		  try {
		    String courseJson = objectMapper.writeValueAsString(book);
		    updateRequest.doc(courseJson, XContentType.JSON);
		    UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
		    Map<String, Object> sourceAsMap = updateResponse.getGetResult().sourceAsMap();
		    return sourceAsMap;
		    
		  } catch (JsonMappingException e){
			  e.getMessage();
		  } catch (java.io.IOException e){
			  e.getLocalizedMessage();
		  }
		  return error;
		}
	
	public void deleteCourseById(String id) {
		  DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
		  try {
		    DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
		  } catch (java.io.IOException e){
		   		e.getLocalizedMessage();
		  }
	}
	
	public List<Course> findAll() throws Exception {
		SearchRequest searchRequest = new SearchRequest(INDEX); 
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
		searchSourceBuilder.query(QueryBuilders.matchAllQuery()); 
		searchRequest.source(searchSourceBuilder); 
		
		SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		
		SearchHit[] searchHit = searchResponse.getHits().getHits();
		List<Course> courses = new ArrayList<>();
		
		while(true) {
			if(searchHit.length == 0) {
				break;
			} else {
				Arrays.stream(searchHit)
                .forEach(hit -> courses
                        .add(objectMapper
                                .convertValue(hit.getSourceAsMap(),
                                		Course.class))
                );
			}
			
			
		}
		return courses;
    }
	
	private List<Course> getSearchResult(SearchResponse response) {

        SearchHit[] searchHit = response.getHits().getHits();

        List<Course> courses = new ArrayList<>();

        if (searchHit.length > 0) {

            Arrays.stream(searchHit)
                    .forEach(hit -> courses
                            .add(objectMapper
                                    .convertValue(hit.getSourceAsMap(),
                                                    Course.class))
                    );
        }

        return courses;
    }
	
	public List<Course> searchByHeight(int height) throws Exception {

        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                .must(QueryBuilders
                        .matchQuery("height", height));

        searchSourceBuilder.query(QueryBuilders
                .nestedQuery("height",
                        queryBuilder,
                        ScoreMode.Avg));

        searchRequest.source(searchSourceBuilder);

        SearchResponse response =
        		restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        return getSearchResult(response);
    }
}