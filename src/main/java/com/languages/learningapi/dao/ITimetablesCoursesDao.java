package com.languages.learningapi.dao;

import com.languages.learningapi.models.timetables.TimetablesCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITimetablesCoursesDao extends JpaRepository<TimetablesCourses, Long> {
    List<TimetablesCourses> findByCoursesRegister_id(Long courses_register_id);
    List<TimetablesCourses> findByCoursesRegister_idAndUsed(Long courses_register_id, boolean used);
    List<TimetablesCourses> findByTimetable_idAndCoursesRegister_id(Long timetable_id, Long course_reg_id);
}
