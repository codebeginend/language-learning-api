package com.languages.learningapi.services;

import java.util.List;

import com.languages.learningapi.dao.ITimetablesTeachersDao;
import com.languages.learningapi.models.timetables.TimetablesTeachers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimetablesTeachersServiceImpl implements ITimetablesTeachersService{
	
	@Autowired
    ITimetablesTeachersDao timetablesTeachersDao;

	@Override
	public List<TimetablesTeachers> findAllByTeacherId(Long teacher_id) {
		// TODO Auto-generated method stub
		return timetablesTeachersDao.findByTeacher_id(teacher_id);
	}

}
