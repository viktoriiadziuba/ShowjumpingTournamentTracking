package com.viktoriia.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viktoriia.model.Course;
import com.viktoriia.repository.CourseService;

//add auth
@RestController
@RequestMapping("/course")
public class CourseController {

	private CourseService service;
	
	@Autowired
	public CourseController(CourseService service) {
		this.service = service;
	}

	@GetMapping("{id}")
	public Course findById(@PathVariable("id") String id) throws IOException {
		return service.getCourseById(id);
	}
	
	@GetMapping
	public List<Course> getAll() throws Exception {
		return service.findAll();
	}
	
	@PostMapping
	public Course save(@RequestBody Course course) throws IOException {
		return service.save(course);	
	}
	
	@PutMapping("{id}")
    public Map<String, Object> updateCourseById(@RequestBody Course course, @PathVariable String id) {
      return service.updateCourseById(id, course);
    }
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable("id") String id) {
		service.deleteCourseById(id);
	}
	
	@GetMapping(value = "search")
	public List<Course> search(
	            @RequestParam(value = "height") int height) 
	            throws Exception {
	return service.searchByHeight(height);
	}
}
