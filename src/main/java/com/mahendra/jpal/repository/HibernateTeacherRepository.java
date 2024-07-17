package com.mahendra.jpal.repository;

import com.mahendra.jpal.entity.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class HibernateTeacherRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public List<Teacher> findAll() {
        return getCurrentSession().createQuery("from Teacher", Teacher.class).list();
    }

    @Transactional
    public Teacher findById(Long id) {
        return getCurrentSession().get(Teacher.class, id);
    }

    @Transactional
    public void save(Teacher teacher) {
        getCurrentSession().saveOrUpdate(teacher);
    }

    @Transactional
    public void deleteById(Long id) {
        Teacher teacher = getCurrentSession().load(Teacher.class, id);
        getCurrentSession().delete(teacher);
    }
}
