package com.languages.learningapi.dao;

import com.languages.learningapi.models.courses.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICoursesDao extends JpaRepository<Courses, Long>{
	
	@Query("select c from Courses c where c.next_id = :next_id")
	Courses findByNextId(Long next_id);

}
