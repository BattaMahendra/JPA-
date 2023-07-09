package com.mahendra.jpal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
//?\@AllArgsConstructor
//@NoArgsConstructor
@ToString
@Entity
@Table(
		
		uniqueConstraints = @UniqueConstraint(
				
				name="email_unique",
				columnNames = "studentEmail"
				
				)
		)
public class Student {
	
	
	public Student(long studentId, String studentName, String studentEmail, Parent parent) {
		super();
		
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentEmail = studentEmail;
		this.parent = parent;
	}
	public Student() {
		super();
	}
	public Student(long studentId, String studentName, String studentEmail) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentEmail = studentEmail;
	}
	
	
	@Id
	@SequenceGenerator(
	            name = "student_sequence",
	            sequenceName = "student_sequence",
	            allocationSize = 1
	    )
	 @GeneratedValue(
	            strategy = GenerationType.SEQUENCE,
	            generator = "student_sequence"
	    )
	private long studentId;
	private String studentName;
	@Column(nullable = false)
	private String studentEmail;
	@Embedded
	private Parent parent;
	
	
	
	public long getStudentId() {
		return studentId;
	}
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentEmail() {
		return studentEmail;
	}
	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}
	public Parent getParent() {
		return parent;
	}
	public void setParent(Parent parent) {
		this.parent = parent;
	}
	
}