package com.mahendra.jpal.service;

import com.mahendra.jpal.entity.Course;
import com.mahendra.jpal.entity.CourseMaterial;
import com.mahendra.jpal.entity.Teacher;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service explaining Hibernate concepts practically.
 * Designed for a developer with ~4 years experience.
 * 
 * Concepts covered:
 * 1. SessionFactory vs Session
 * 2. Entity Lifecycle (Transient, Persistent, Detached, Removed)
 * 3. First Level Cache (Persistence Context)
 * 4. N+1 Select Problem & Solution
 * 5. Lazy Initialization Exception
 */
@Service
public class HibernateConceptsService {


    @PersistenceContext
    private EntityManager em;

    @Autowired
    private EntityManagerFactory emf;

    /**
     * 1. SessionFactory vs Session
     * ----------------------------
     * - SessionFactory (Singleton): Created once. Heavy. Holds DB connection pool.
     * - Session (Prototype): Created per request. Light. Wraps a JDBC connection.
     */
    public void demonstrateSessionFactoryVsSession() {
        // Unwrap JPA EntityManagerFactory to get Hibernate SessionFactory
        SessionFactory sessionFactory = emf.unwrap(SessionFactory.class);
        System.out.println("SessionFactory (One per app): " + sessionFactory);

        // Open a new Session
        try (Session session = sessionFactory.openSession()) {
            System.out.println("Session created (One per request): " + session);

        }
    }

    /**
     * 2. Entity Lifecycle
     * -------------------
     * Entities move through 4 states: Transient, Persistent, Detached, Removed.
     */

    @Transactional
    public void demonstrateEntityLifecycle() {
        // 1. Transient: Just a Java object. Not in DB.
        Teacher teacher = new Teacher();
        teacher.setTeacherName("Lifecycle Demo");

        // 2. Persistent: Managed by Hibernate.
        em.persist(teacher);
        
        // Auto-dirty checking: Hibernate detects this change and saves it automatically
        teacher.setTeacherName("Lifecycle Demo - Updated");
        
        em.flush(); // Force SQL execution  -- reflected in table but still not committed (not finalized)

        // 3. Detached: No longer tracked by Hibernate.
        em.detach(teacher);
        teacher.setTeacherName("This won't be saved");

        // 4. Removed: Delete from DB
        Teacher managed = em.merge(teacher); // Re-attach (Merge returns the managed instance)

        em.flush();

        // Removed
        em.remove(managed);

        // finally no update in DB as we didn't commit
    }

    /**
     * 3. First Level Cache
     * --------------------
     * Hibernate caches entities within the same Transaction (Session).
     * Repeated queries for the same ID in the same transaction don't hit the DB.
     */
    @Transactional
    public void demonstrateFirstLevelCache() {
        Teacher t = new Teacher();
        t.setTeacherName("Cache Demo");
        em.persist(t);
        em.flush();
        Long id = t.getTeacherId();
        
        em.clear(); // Clear cache to simulate fresh start

        System.out.println("Fetching from DB...");
        Teacher t1 = em.find(Teacher.class, id); // SQL executed

        System.out.println("Fetching from Cache (No SQL)...");
        Teacher t2 = em.find(Teacher.class, id); // No SQL executed

        System.out.println("Same instance? " + (t1 == t2)); // true
    }

    /**
     * 4. N+1 Select Problem
     * ---------------------
     * Fetching a list of entities + fetching their related entities = N+1 queries.
     * Even with EAGER fetch, if not using JOIN FETCH, Hibernate might issue separate selects.
     */
    @Transactional
    public void demonstrateNPlusOneProblem() {
        createTeacherWithCourses("N+1 Teacher");
        em.flush();
        em.clear();

        System.out.println("--- N+1 START ---");
        // 1 Query for Teachers
        List<Teacher> teachers = em.createQuery("SELECT t FROM Teacher t", Teacher.class).getResultList();
        
        for (Teacher t : teachers) {
            // N Queries for Courses (if Lazy or separate select Eager)
            System.out.println("Teacher: " + t.getTeacherName() + ", Courses: " + t.getCourseList().size());
        }
        System.out.println("--- N+1 END ---");
    }

    /**
     * Solution: JOIN FETCH
     * --------------------
     * Tells Hibernate to fetch the association in the SAME query.
     */
    @Transactional
    public void solveNPlusOneProblem() {
        em.clear();
        System.out.println("--- N+1 SOLUTION START ---");
        
        // Fetch everything in 1 Query
        List<Teacher> teachers = em.createQuery(
                "SELECT t FROM Teacher t JOIN FETCH t.courseList", Teacher.class)
                .getResultList();
        
        for (Teacher t : teachers) {
            System.out.println("Teacher: " + t.getTeacherName() + ", Courses: " + t.getCourseList().size());
        }
        System.out.println("--- N+1 SOLUTION END ---");
    }

    /**
     * 5. Lazy Initialization Exception
     * --------------------------------
     * Happens when accessing a Lazy relationship after the Session is closed.
     */



    // --- Helpers ---

    private void createTeacherWithCourses(String name) {
        Teacher t = new Teacher();
        t.setTeacherName(name);
        List<Course> courses = new ArrayList<>();
        Course c = new Course();
        c.setCourseName(name + " Course");
        c.setCredits(3);
        c.setTeacher(t);
        courses.add(c);
        t.setCourseList(courses);
        em.persist(t);
    }

    @Transactional
    public Long createCourseWithMaterial() {
        Course c = new Course();
        c.setCourseName("Lazy Demo");
        c.setCredits(3);
        
        CourseMaterial cm = new CourseMaterial();
        cm.setUrl("http://lazy.com");
        cm.setCourse(c);
        
        // Persisting CourseMaterial cascades to Course because of CascadeType.ALL
        em.persist(c);
        return c.getCourseId();
    }

    @Transactional
    public Course getCourseById(Long id) {
        return em.find(Course.class, id);
    }


}
