package com.mahendra.jpal.repository.jpa;

import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendra.jpal.entity.CourseMaterial;

@Repository
public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, Long> {

    /*
    * 💡If we don't have Spring DATA JPA then ==> DAO → EntityManager → JPQL
    * 🛑 Repository Interface → Spring generates implementation automatically
    *
    * ===> Spring Data JPA can generate queries from method names.
    * */

}
