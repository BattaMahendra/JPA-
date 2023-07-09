package com.mahendra.jpal.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.ToString;

@Entity
@ToString
@Getter
public class Course {
	
	public Course(long courseId, String courseName, int credits, Teacher teacher) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.credits = credits;
//		this.courseMaterial = courseMaterial;
		this.teacher = teacher;
	}
	public Course() {
		super();
	}
	public Course(long courseId, String courseName, int credits) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
		this.credits = credits;
	}
	@Id
	@SequenceGenerator(
            name = "course_sequence",
            sequenceName = "course_sequence",
            allocationSize = 1
    )
 @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_sequence"
    )
	private long courseId;
	private String courseName;
	private int credits;
	
//	@OneToOne(
////			give the variable of Course object that you have defined in the CourseMaterial class
//			mappedBy = "course"
//			)
//	private CourseMaterial courseMaterial;
	
	
//	this will create a colomn in course table
	@ManyToOne(
			cascade = CascadeType.ALL
			)
	@JoinColumn(
				name="teacher_id",
				referencedColumnName = "teacherId"
				
			
			)
	private Teacher teacher;
	
//	multiple students can opt multiple courses 
//	so we use manytomany and it can be only done by creating another table with studentId and courseId
//	in that table the relations are defined
	
	@ManyToMany(
			cascade = CascadeType.ALL
			)
	@JoinTable(
//		this is the table that will be newly created	
			name="student_course-table",
//			it is for the colomn of courseId and to map it 
			joinColumns = @JoinColumn(
								name="c_id",
								referencedColumnName = "courseId"
					
					),
//			this inverse join colomns is for mapping and creating studentId in the new table
			inverseJoinColumns = @JoinColumn(
					
								name="s_id",
								referencedColumnName = "studentId"
					)
			)
	private List<Student> students;
	
	
	public  void addStudents(Student s) {
		if(students==null) {
			students=new ArrayList<>();
		    students.add(s);
//		students.add(new Student(12, "mahi", "bmc26g@gmail.com",new Parent("NAg", "891955")));
		}
	}
	
	public long getCourseId() {
		return courseId;
	}
	
	
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
//	public CourseMaterial getCourseMaterial() {
//		return courseMaterial;
//	}
//	public void setCourseMaterial(CourseMaterial courseMaterial) {
//		this.courseMaterial = courseMaterial;
//	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
//	public CourseMaterial getCourseMaterial() {
//		return courseMaterial;
//	}
//	public void setCourseMaterial(CourseMaterial courseMaterial) {
//		this.courseMaterial = courseMaterial;
//	}
	

}
