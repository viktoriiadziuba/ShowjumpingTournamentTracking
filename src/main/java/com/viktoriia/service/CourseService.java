package com.viktoriia.service;

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
	
}
