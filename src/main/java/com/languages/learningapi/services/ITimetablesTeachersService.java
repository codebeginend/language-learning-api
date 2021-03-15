package com.languages.learningapi.services;

import java.util.List;

import com.languages.learningapi.models.timetables.TimetablesTeachers;

public interface ITimetablesTeachersService {
	
	List<TimetablesTeachers> findAllByTeacherId(Long teacher_id);

}
