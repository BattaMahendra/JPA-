package com.mahendra.jpal.controller;


import com.mahendra.jpal.entity.Teacher;
import com.mahendra.jpal.repository.HibernateTeacherRepository;
import com.mahendra.jpal.repository.JdbcTeacherRepository;
import com.mahendra.jpal.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository; // Spring Data JPA

    @Autowired
    private HibernateTeacherRepository hibernateTeacherRepository; // Hibernate

    @Autowired
    private JdbcTeacherRepository jdbcTeacherRepository; // Plain JDBC

    // Spring Data JPA methods
    public List<Teacher> findAllJpa() {
        return teacherRepository.findAll();
    }

    public Optional<Teacher> findByIdJpa(Long id) {
        return teacherRepository.findById(id);
    }

    public Teacher saveJpa(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public void deleteByIdJpa(Long id) {
        teacherRepository.deleteById(id);
    }

    // Hibernate methods
    public List<Teacher> findAllHibernate() {
        return hibernateTeacherRepository.findAll();
    }

    public Teacher findByIdHibernate(Long id) {
        return hibernateTeacherRepository.findById(id);
    }

    public void saveHibernate(Teacher teacher) {
        hibernateTeacherRepository.save(teacher);
    }

    public void deleteByIdHibernate(Long id) {
        hibernateTeacherRepository.deleteById(id);
    }

    // Plain JDBC methods
    public List<Teacher> findAllJdbc() {
        return jdbcTeacherRepository.findAll();
    }

    public Teacher findByIdJdbc(Long id) {
        return jdbcTeacherRepository.findById(id);
    }

    public void saveJdbc(Teacher teacher) {
        jdbcTeacherRepository.save(teacher);
    }

    public void deleteByIdJdbc(Long id) {
        jdbcTeacherRepository.deleteById(id);
    }
}
