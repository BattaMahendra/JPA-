package com.mahendra.jpal.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Teacher {

	

	public Teacher() {
		super();
	}

//	public Teacher(long teacherId, String teacherName, List<Course> courseList) {
//		super();
//		this.teacherId = teacherId;
//		this.teacherName = teacherName;
//		this.courseList = courseList;
//	}

	@Id
	@SequenceGenerator(
            name = "teacher_sequence",
            sequenceName = "teacher_sequence",
            allocationSize = 1
    )
 @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "teacher_sequence"
    )
	private long teacherId;
	private String teacherName;
	
//	this will create column in course table 
//	it wont create any table in the teachers table
//	a teacher_id column is created in the course table 
	
//	@OneToMany(
//			cascade = CascadeType.ALL
//			)
//	@JoinColumn(
//			
//			name="teacher_id",
//			referencedColumnName = "teacherId"
//			)
//	private List<Course> courseList;

	public long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(long teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

//	public List<Course> getCourseList() {
//		return courseList;
//	}
//
//	public void setCourseList(List<Course> courseList) {
//		this.courseList = courseList;
//	}
	
}
