package com.mahendra.jpal;

import com.mahendra.jpal.entity.Course;
import com.mahendra.jpal.service.HibernateConceptsService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpaLApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaLApplication.class, args);
	}

	// In spring boot we generally use @PostConstruct to test some non controller methods
	//instead of that we can use this also

	@Bean
	CommandLineRunner run(HibernateConceptsService service){
		return args -> service.demonstrateEntityLifecycle();
	}

	@Bean
	ApplicationRunner runner(HibernateConceptsService service) {
		return args -> {

			Long courseId = service.createCourseWithMaterial();

			// Session closed after this call (since it's a separate transaction)
			Course course = service.getCourseById(courseId);

			try {
				// Accessing Lazy collection (courseMaterials) outside transaction
				System.out.println("Accessing lazy materials...");
				// This will throw LazyInitializationException
				System.out.println("Materials: " + course.getCourseMaterials().size());
			} catch (Exception e) {
				System.out.println("Caught: " + e.getClass().getSimpleName());
				System.out.println("Reason: Session closed. Cannot load Lazy data.");
			}

		};
	}


}
