package com.viktoriia.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viktoriia.model.Course;
import com.viktoriia.service.CourseService;

//add auth
@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseService service;

	@GetMapping
	public ResponseEntity<List<Course>> getAll() throws Exception {
		List<Course> courses = service.findAll();
		if(!courses.equals(null)) {
			return new ResponseEntity<List<Course>>(courses, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Course> findById(@PathVariable("id") String id) throws IOException {
		Course course = service.getCourseById(id);
		if(!course.equals(null)) {
			return new ResponseEntity<Course>(course, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<Course> save(@RequestBody Course course) throws IOException {
		if(!course.getTournamentTitle().equals(null) && course.getHeight() != 0 && !course.getTime().equals(null) && !course.getParticipants().equals(null)) {
			service.save(course);	
			return new ResponseEntity<Course>(course, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		service.deleteCourseById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
