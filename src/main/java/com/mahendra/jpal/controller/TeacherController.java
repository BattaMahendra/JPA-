package com.mahendra.jpal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahendra.jpal.entity.Teacher;
import com.mahendra.jpal.repository.TeacherRepository;

@RestController
@RequestMapping("/t")
public class TeacherController {

	@Autowired
	private TeacherRepository teacherRepository;
	
	@GetMapping("/gT")
	public List<Teacher> getAllTeachers(){
		
		return teacherRepository.findAll();
		
	}
	
	@PostMapping("/addT")
	public Teacher addTeacher(@RequestBody Teacher c) {
		
		return teacherRepository.save(c);
	}

	
}
