package com.languages.learningapi.services;

import com.languages.learningapi.models.timetables.TimetablesCourses;

import java.util.List;

public interface ITimetablesCoursesService {
	List<TimetablesCourses> findByCoursesRegister_idAndUsed(Long courses_register_id, boolean used);
    List<TimetablesCourses> findByCoursesRegister_id(Long courses_register_id);
    List<TimetablesCourses> findByTimetables_id(Long timetables_id, Long course_reg_id);
    List<TimetablesCourses> saveAll(List<TimetablesCourses> list);
}
