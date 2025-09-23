package com.mahendra.jpal.service;


import com.mahendra.jpal.entity.Teacher;
import com.mahendra.jpal.repository.hibernate.HibernateTeacherRepository;
import com.mahendra.jpal.repository.jdbc.JdbcTeacherRepository;
import com.mahendra.jpal.repository.jpa.TeacherRepository;
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

    public void learnHibernateStates(){

        // 1️⃣ Transient
       Teacher teacher = new Teacher();
       teacher.setTeacherName("Ranga Naidu");
        System.out.println("Transient state : "+teacher);

        // 2️⃣ Persistent
        //now it will be save into DB
        Teacher teacherFromDB = teacherRepository.save(teacher); // persistent state
        long id = teacherFromDB.getTeacherId();

        /*
        * The teacher is still tracked but not committed into DB yet*/
        teacher.setTeacherName(teacher.getTeacherName() +" Updated"); // not saved in db

        teacherRepository.flush(); //  // forces UPDATE to DB now


        // 3️⃣ Detached
        // Simulate detach by clearing persistence context

        // in real JPA EntityManager: em.detach(emp) or transaction ends
        // here we assume emp is detached
        System.out.println("\n\nDetached: making changes won't be auto-saved");
        teacher.setTeacherName(teacher.getTeacherName()+" detached"); // not tracked now

        // 4️⃣ Merge
        teacher.setTeacherName(teacher.getTeacherName()+" detached");
        teacherRepository.save(teacher); // merges detached entity, updates DB

        // finally delete for retesting everytime
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
