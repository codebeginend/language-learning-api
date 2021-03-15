package com.languages.learningapi.dao;

import java.util.Date;
import java.util.List;

import com.languages.learningapi.models.courses.Lessons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ILessonsDao extends JpaRepository<Lessons, Long>{
	
	@Query("select l from Lessons l where l.courses_registers_id = :courseRegId")
	List<Lessons> findByCourseRegId(Long courseRegId);
	
	@Query("select l from Lessons l where l.date >= :first and l.date <= :last and l.parent_id = null")
	List<Lessons> findByPeriodPaysAndNotParentId(Date first, Date last);

	@Query("select l from Lessons  l where l.courses_registers_id = :course_reg_id and l.parent_id = null")
	List<Lessons> findByCurrentLesson(Long course_reg_id);
}
