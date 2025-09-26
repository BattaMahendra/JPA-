package com.mahendra.jpal.repository.hibernate;

import com.mahendra.jpal.entity.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
public class HibernateTeacherRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public List<Teacher> findAll() {
        return em.createQuery("select  t from Teacher t", Teacher.class).getResultList();
    }

    @Transactional
    public Teacher findById(Long id) {
        return em.find(Teacher.class, id);
    }

    @Transactional
    public void save(Teacher teacher) {
        em.persist(teacher);
    }

    @Transactional
    public void deleteById(Long id) {
        Teacher teacher = em.find(Teacher.class, id);
        em.remove(teacher);
    }

    @Transactional
    public void learningAboutSessions(){

        Session session = (Session) em.unwrap(Teacher.class);

        //
    }

}
