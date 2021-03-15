package com.languages.learningapi.controllers;

import com.languages.learningapi.models.timetables.TimetablesCourses;
import com.languages.learningapi.models.timetables.WeekDaysEnum;
import com.languages.learningapi.services.ITimetablesCoursesService;
import com.languages.learningapi.services.ITimetablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "timetables")
public class TimetablesCtrl {

    @Autowired
    private ITimetablesCoursesService timetablesCoursesService;

    @Autowired
    private ITimetablesService timetablesService;


    @RequestMapping(value = "courses/{courses_register_id}", method = RequestMethod.GET)
    private List<TimetablesCourses> findByCourseId(@PathVariable(name = "courses_register_id", required = true) Long courses_register_id){
        return this.timetablesCoursesService.findByCoursesRegister_id(courses_register_id);
    }

    @RequestMapping(value = "coursereg/{course_reg_id}", method = RequestMethod.GET)
    private Map<WeekDaysEnum, TimetablesCourses> getListData(@PathVariable(name = "course_reg_id") Long course_reg_id){
        return timetablesService.findMapByCourseReg(course_reg_id);
    }
    
    @RequestMapping(value = "coursereg/{course_reg_id}", method = RequestMethod.PUT)
    private void getListData(@PathVariable(name = "course_reg_id") Long course_reg_id, @RequestBody Map<WeekDaysEnum, TimetablesCourses> timetablesReg){
         timetablesService.updateTimetablesFromCoursesReg(course_reg_id, timetablesReg);
    }
    
    
    
}
