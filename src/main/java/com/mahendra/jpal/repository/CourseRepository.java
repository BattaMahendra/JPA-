package com.mahendra.jpal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendra.jpal.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
