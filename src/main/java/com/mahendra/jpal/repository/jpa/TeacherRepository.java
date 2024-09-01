package com.mahendra.jpal.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mahendra.jpal.entity.Teacher;

@Repository
public interface TeacherRepository  extends JpaRepository<Teacher, Long> {

}




