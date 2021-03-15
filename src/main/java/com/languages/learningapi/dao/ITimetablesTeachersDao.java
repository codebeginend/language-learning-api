package com.languages.learningapi.dao;

import java.util.List;

import com.languages.learningapi.models.timetables.TimetablesTeachers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITimetablesTeachersDao extends JpaRepository<TimetablesTeachers, Long>{
	
	List<TimetablesTeachers> findByTeacher_id(Long teacher_id);

}
