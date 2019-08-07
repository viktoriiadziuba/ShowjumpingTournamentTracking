package com.viktoriia.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.viktoriia.model.Course;
import com.viktoriia.repository.CourseRepository;
import com.viktoriia.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepository repository;
	
	@Override
	public Course save(Course course) {
		return repository.save(course);
	}

	@Override
	public Optional<Course> findById(String id) {
		return repository.findById(id);
	}

	@Override
	public Iterable<Course> findAll() {
		return repository.findAll();
	}

	@Override
	public Page<Course> findByHeight(int height, Pageable pageable) {
		return repository.findByHeight(height, pageable);
	}

	@Override
	public long count() {
		return repository.count();
	}

	@Override
	public void delete(Course course) {
		repository.delete(course);
	}

}
