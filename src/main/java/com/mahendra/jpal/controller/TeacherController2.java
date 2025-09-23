package com.mahendra.jpal.controller;


import com.mahendra.jpal.entity.Teacher;
import com.mahendra.jpal.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
public class TeacherController2 {

    @Autowired
    private TeacherService teacherService;

    // Spring Data JPA endpoints
    @GetMapping("/jpa")
    public List<Teacher> getAllTeachersJpa() {
        return teacherService.findAllJpa();
    }

    @GetMapping("/jpa/{id}")
    public Optional<Teacher> getTeacherByIdJpa(@PathVariable Long id) {
        return teacherService.findByIdJpa(id);
    }

    @PostMapping("/jpa")
    public Teacher createTeacherJpa(@RequestBody Teacher teacher) {
        return teacherService.saveJpa(teacher);
    }

    @DeleteMapping("/jpa/{id}")
    public void deleteTeacherJpa(@PathVariable Long id) {
        teacherService.deleteByIdJpa(id);
    }

    @GetMapping("/states")
    public void learnHIbernateStates(){
        teacherService.learnHibernateStates();
    }

    // Hibernate endpoints
    @GetMapping("/hibernate")
    public List<Teacher> getAllTeachersHibernate() {
        return teacherService.findAllHibernate();
    }

    @GetMapping("/hibernate/{id}")
    public Teacher getTeacherByIdHibernate(@PathVariable Long id) {
        return teacherService.findByIdHibernate(id);
    }

    @PostMapping("/hibernate")
    public void createTeacherHibernate(@RequestBody Teacher teacher) {
        teacherService.saveHibernate(teacher);
    }

    @DeleteMapping("/hibernate/{id}")
    public void deleteTeacherHibernate(@PathVariable Long id) {
        teacherService.deleteByIdHibernate(id);
    }

    // JDBC endpoints
    @GetMapping("/jdbc")
    public List<Teacher> getAllTeachersJdbc() {
        return teacherService.findAllJdbc();
    }

    @GetMapping("/jdbc/{id}")
    public Teacher getTeacherByIdJdbc(@PathVariable Long id) {
        return teacherService.findByIdJdbc(id);
    }

    @PostMapping("/jdbc")
    public void createTeacherJdbc(@RequestBody Teacher teacher) {
        teacherService.saveJdbc(teacher);
    }

    @DeleteMapping("/jdbc/{id}")
    public void deleteTeacherJdbc(@PathVariable Long id) {
        teacherService.deleteByIdJdbc(id);
    }
}
