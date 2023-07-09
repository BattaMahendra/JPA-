package com.mahendra.jpal.repository;

import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahendra.jpal.entity.CourseMaterial;

@Repository
public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, Long> {

}
