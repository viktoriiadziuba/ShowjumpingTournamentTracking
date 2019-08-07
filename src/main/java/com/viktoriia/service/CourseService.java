package com.viktoriia.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.viktoriia.model.Course;

public interface CourseService {
	
	Course save(Course course);
	
	Optional<Course> findById(String id);
	
	Iterable<Course> findAll();

	Page<Course> findByHeight(int height, Pageable pageable);
		
	long count();
	
	void delete(Course article);
}
