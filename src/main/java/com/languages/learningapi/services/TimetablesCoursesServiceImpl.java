package com.languages.learningapi.services;

import com.languages.learningapi.dao.ITimetablesCoursesDao;
import com.languages.learningapi.models.timetables.TimetablesCourses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetablesCoursesServiceImpl implements ITimetablesCoursesService{

    @Autowired
    private ITimetablesCoursesDao timetablesCoursesDao;

    
    @Override
    public List<TimetablesCourses> findByCoursesRegister_id(Long courses_register_id) {
        return timetablesCoursesDao.findByCoursesRegister_id(courses_register_id);
    }
    
    @Override
    public List<TimetablesCourses> findByCoursesRegister_idAndUsed(Long courses_register_id, boolean used) {
        return timetablesCoursesDao.findByCoursesRegister_idAndUsed(courses_register_id, used);
    }

    @Override
    public List<TimetablesCourses> findByTimetables_id(Long timetables_id, Long course_reg_id) {
        return this.timetablesCoursesDao.findByTimetable_idAndCoursesRegister_id(timetables_id, course_reg_id);
    }

	@Override
	public List<TimetablesCourses> saveAll(List<TimetablesCourses> list) {
		// TODO Auto-generated method stub
		return timetablesCoursesDao.saveAll(list);
	}
}
