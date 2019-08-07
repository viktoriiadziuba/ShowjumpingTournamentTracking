package com.viktoriia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.viktoriia.model.Course;

@Repository
public interface CourseRepository extends ElasticsearchRepository<Course, String> {

	Page<Course> findByHeight(int height, Pageable pageable);
		
}
