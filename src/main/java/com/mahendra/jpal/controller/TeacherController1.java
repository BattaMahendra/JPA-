package com.mahendra.jpal.controller;

import java.util.List;

import com.mahendra.jpal.repository.jdbc.WithOnlyJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahendra.jpal.entity.Teacher;
import com.mahendra.jpal.repository.jpa.TeacherRepository;

@RestController
@RequestMapping("/t")
public class TeacherController1 {

	@Autowired
	private TeacherRepository teacherRepository;
	
	@GetMapping("/gT")
	public List<Teacher> getAllTeachers(){
		
		return teacherRepository.findAll();
		
	}

	@GetMapping("/gT/usingJDBC")
	public List<Teacher> getAllTeachersUsingJDBC(){
		WithOnlyJDBC  withOnlyJDBC = new WithOnlyJDBC();
		return withOnlyJDBC.getAllTeachers();
	}
	
	@PostMapping("/addT")
	public Teacher addTeacher(@RequestBody Teacher c) {
		
		return teacherRepository.save(c);
	}

	
}
