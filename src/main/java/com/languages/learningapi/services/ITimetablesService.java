package com.languages.learningapi.services;

import com.languages.learningapi.models.timetables.Timetables;
import com.languages.learningapi.models.timetables.TimetablesCourses;
import com.languages.learningapi.models.timetables.WeekDaysEnum;

import java.util.List;
import java.util.Map;

public interface ITimetablesService {
    Map<WeekDaysEnum, List<Timetables>> findAllByMap(Long course_reg_id);
    List<Timetables> findAllById(Iterable<Long> id);
    
    Map<WeekDaysEnum, TimetablesCourses> findMapByCourseReg(Long course_reg_id);
    
    void updateTimetablesFromCoursesReg(Long course_reg_id, Map<WeekDaysEnum, TimetablesCourses> timetablsesReg);
}
