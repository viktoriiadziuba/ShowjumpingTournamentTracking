package com.viktoriia.repository;

import java.io.IOException; 
import java.util.ArrayList;
import java.util.Collection;
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
import org.elasticsearch.search.SearchHits;
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

	private final String INDEX = "coursedata";
	private RestHighLevelClient client;
	private ObjectMapper objectMapper;
	
	public CourseService(ObjectMapper objectMapper, RestHighLevelClient restHighLevelClient) {
		this.objectMapper = objectMapper;
		this.client = restHighLevelClient;
	}
	
	public Course save(Course course) throws IOException {
		UUID uuid = UUID.randomUUID();
		course.setId(uuid.toString());
        Map<String, Object> courseMapper = objectMapper.convertValue(course, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX).id(course.getId()).source(courseMapper);
		
			IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
		
		return course;
	}
	
	public Course getCourseById(String id) {
		GetRequest getRequest = new GetRequest(INDEX, id);
		
		GetResponse getResponse;
		try {
			getResponse = client.get(getRequest, RequestOptions.DEFAULT);
			Map<String, Object> resultMap = getResponse.getSource();

	        return objectMapper
	                .convertValue(resultMap, Course.class);
		} catch (IOException e) {
			LOG.error("There isn't such course");
			e.printStackTrace();
		}
        return null;
	}
	
	public void deleteCourseById(String id) {
		  DeleteRequest deleteRequest = new DeleteRequest(INDEX, id);
		  try {
		    DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
		  } catch (java.io.IOException e){
		   		e.getLocalizedMessage();
		  }
	}
	
	//not working
	
	public Map<String, Object> updateCourseById(String id, Course course){
		  UpdateRequest updateRequest = new UpdateRequest(INDEX, id)
		          .fetchSource(true);    // Fetch Object after its update
		  Map<String, Object> error = new HashMap<>();
		  error.put("Error", "Unable to update course");
		  try {
		    String courseJson = objectMapper.writeValueAsString(course);
		    updateRequest.doc(courseJson, XContentType.JSON);
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
	
	public List<Course> findAll() throws Exception {
			GetRequest getRequest = new GetRequest(INDEX);
			List<Course> courses = new ArrayList<>();
			GetResponse getResponse;
			try {
				getResponse = client.get(getRequest, RequestOptions.DEFAULT);
				Map<String, Object> resultMap = getResponse.getSource();

		        Course course = objectMapper.convertValue(resultMap, Course.class);
		        courses.add(course);
			} catch (IOException e) {
				LOG.error("There isn't such course");
				e.printStackTrace();
			}
			return courses;
    }
	
	private List<Course> getSearchResult(SearchResponse response) {

		SearchHits hits = response.getHits();
		SearchHit[] searchHits = hits.getHits();

        List<Course> courses = new ArrayList<>();

        for (SearchHit hit : searchHits) {
        	courses.addAll((Collection<? extends Course>) hit.getSourceAsMap());
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
        		client.search(searchRequest, RequestOptions.DEFAULT);

        return getSearchResult(response);
    }
}
