package com.mahendra.jpal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahendra.jpal.entity.Student;
import com.mahendra.jpal.repository.StudentRepository;

@RestController
@RequestMapping("/s")
public class StudentController {
	
	@Autowired
	StudentRepository studentRepository;
	
	@GetMapping("/ga")
	public List<Student> getAllStudentList(){
		return studentRepository.findAll();
	}
	
	@PostMapping("/add")
	public Student addAStudent(@RequestBody Student student) {
		return studentRepository.save(student);
	}
	
	@GetMapping("/gB/{id}")
	public Optional<Student> findById(@PathVariable long id) {
		return studentRepository.findById(id);
	}
	
	@GetMapping("/gBN/{studentName}")
	public List<Student> findByName(@PathVariable String studentName) {
		return studentRepository.findByStudentName(studentName);
	}
	
	@GetMapping("/gBPN/{parentName}")
	public List<Student> findByParentName(@PathVariable String parentName) {
		return studentRepository.findByParentParentName(parentName);
	}
	
	@GetMapping("/gBE/{emailAddress}")
	public Student findByEmailAddress(@PathVariable String emailAddress) {
		return studentRepository.findByStudentssssEmailAdrress(emailAddress);
	}
	
	@GetMapping("/gBES/{emailAddress}")
	public Student findByEmailAddressBySQL(@PathVariable String emailAddress) {
		return studentRepository
				.findByStudentEmailAddressUsingNativeSQL_Query(emailAddress);
	}
	
	@GetMapping("/gBESN/{emailAddress}")
	public Student findByEmailAddressBySQLWithNamedParam(@PathVariable String emailAddress) {
		return studentRepository
				.findByStudentEmailAddressUsingNativeSQL_QueryBYNamedParams(emailAddress);
	}
	
	@PutMapping("/uBE/{n}/{e}")
	public int updateStudentNameByEmailAddressInNativeQuery(@PathVariable("n") String studentName,@PathVariable("e") String emailAddress) {
		return studentRepository
				.updateAStudentNameByEmailAddress(studentName, emailAddress);
	}
	
	@PutMapping("/u")
	public Student updateStudent(@RequestBody Student student) {
		if(studentRepository.existsById(student.getStudentId())){
			return studentRepository.save(student);
		}
		
		return null;
		
	}

}
