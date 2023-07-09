package com.mahendra.jpal.entity;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.ToString;

@Entity
public class CourseMaterial {
	
	public CourseMaterial() {
		super();
	}

	public CourseMaterial(long courseMaterialId, String url, Course course) {
		super();
		this.courseMaterialId = courseMaterialId;
		this.url = url;
		this.course = course;
	}

	@Id
	@SequenceGenerator(
            name = "course_material_sequence",
            sequenceName = "course_material_sequence",
            allocationSize = 1
    )
 @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "course_material_sequence"
    )
	private long courseMaterialId;
	private String url;
	
	@OneToOne(
			
			
			
//			we use cascade to enter data into course table from course material
//			whenever we add a new course material , the course is directly added into course table
			
				cascade = CascadeType.ALL,
				
				fetch = FetchType.LAZY
			
			)
	@JoinColumn(
//					this name will be kept for the colomn in this courseMaterial table
					name="cId",
//					to which colomn in the Course table it is referring to
					referencedColumnName = "courseId"
			
			)
	@LazyToOne(value = LazyToOneOption.NO_PROXY)
	private Course course;

	public long getCourseMaterialId() {
		return courseMaterialId;
	}

	public void setCourseMaterialId(long courseMaterialId) {
		this.courseMaterialId = courseMaterialId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

//	@Override
//	public String toString() {
//		return "CourseMaterial [courseMaterialId=" + courseMaterialId + ", url=" + url + "]";
//	}

}
