package com.mahendra.jpal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mahendra.jpal.entity.Course;
import com.mahendra.jpal.entity.CourseMaterial;
import com.mahendra.jpal.entity.Course_Student;
import com.mahendra.jpal.repository.CourseMaterialRepository;
import com.mahendra.jpal.repository.CourseRepository;

@RestController
@RequestMapping("/c")
public class CourseController {
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private CourseMaterialRepository courseMaterialRepository;
	
	@GetMapping("/gC")
	public List<Course> getAllCourses(){
		
		return courseRepository.findAll();
		
	}
	@PostMapping("/addC")
	public Course addAcourse(@RequestBody Course c) {
		
//		c.addStudents();
		return courseRepository.save(c);
	}
	
//	in order to add a course along with student , I created a new class consisting of course nad student variables
	
	@PostMapping("/addCS")
	public Course addCourseAlongWithStudent(@RequestBody Course_Student cs) {
		cs.getCourse().addStudents(cs.getStudent());
		return courseRepository.save(cs.getCourse());
	}

	@GetMapping("/gCM")
	public List<CourseMaterial> getAllCourseMaterials(){
		

		return courseMaterialRepository.findAll();
		
	}
	
	@PostMapping("/addCM")
	public CourseMaterial addAcourseMaterial(@RequestBody CourseMaterial c) {
		return courseMaterialRepository.save(c);
	}
}
