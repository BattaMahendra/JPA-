package com.mahendra.jpal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mahendra.jpal.entity.Student;

import jakarta.transaction.Transactional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	
//	these are custom defined JPA methods 
//	for these all we don't need to write SQL queries 
//	we can directly get them by small techniques
//	JPA internally provides all these methods if we use proper variables defined in the class
//	just use the variable in the class with proper java syntax
	
	public List<Student> findByStudentName(String studentName);
	
//	here parent is a embedded class in the student class
//	so in order to find student records by parent name 
//	just define embedded class name (Parent) followed by
//	particular variable in that embedded class(parentName) by proper java syntax
	
	public List<Student> findByParentParentName(String parentName);
	
//	this is JPL query and it is different from native SQL queries
//	here instead of table names we use class names
//	the ?1 means take the first parameter as value for the query	
	
	@Query("select s from Student s where s.studentEmail=?1")
	Student findByStudentssssEmailAdrress(String emailAdress);
	
	
//	Instead of JPQL we can also use normal SQL queries 
//	for that we have to add a property called nativeQuery=true
//	in value property add the required query
	@Query(
			value = "select * from student where student_email= ?1",
			nativeQuery = true
			
			)
	Student findByStudentEmailAddressUsingNativeSQL_Query(String emailAddress);
	
	
//	Nmaed Parameters instead of using Question mark for passing values in SQL query
//lets use the same above example for named params
//	instead of using ?1 just use the defined parameter starting with a colon Eg  :name 
	
	@Query(
			value = "select * from student where student_email=:emailId",
			nativeQuery = true
			
			)
	Student findByStudentEmailAddressUsingNativeSQL_QueryBYNamedParams(@Param("emailId") String emailAddress);
	
	
//	what if we want to update some data in table
//	we need to use @modifier
//	Then we have to use @Transactional , this makes this query a transaction and if it is successful then only something is returned
//	we can have many operations under a transaction
//	ideally it is used in service layer so that  we can access many repos and can keep a number of database operations under a 
//	single transaction and transaction becomes successful if and only if all the operations are successful
	
	@Transactional
	@Modifying
	@Query(
			value = "update student set student_name=?1 where student_email=?2",
			nativeQuery = true
			
			)
	int updateAStudentNameByEmailAddress(String name,String emailAddress);
	
	

}
