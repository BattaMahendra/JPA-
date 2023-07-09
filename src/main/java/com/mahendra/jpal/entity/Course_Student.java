package com.mahendra.jpal.entity;

public class Course_Student {
	
	public Course_Student() {
		super();
	}
	public Course_Student(Course course, Student student) {
		super();
		this.course = course;
		this.student = student;
	}
	
	private Course course;
	private Student student;
	
	
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	

}
