package com.languages.learningapi.controllers;

import com.languages.learningapi.dao.ILoginDao;
import com.languages.learningapi.dao.ITeachersDao;
import com.languages.learningapi.models.controller.AddTeachers;
import com.languages.learningapi.models.controller.TeachersModel;
import com.languages.learningapi.models.timetables.WeekDaysEnum;
import com.languages.learningapi.services.ITeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("teachers")
public class TeachersCtrl {

    @Autowired
    ILoginDao loginDao;

    @Autowired
    ITeachersDao teachersDao;

    @Autowired
    ITeachersService teachersService;

    @RequestMapping(method = RequestMethod.POST)
    private void teachersAdd(@RequestBody AddTeachers teacher){
         teachersService.addTeacher(teacher);
    }

    @RequestMapping(method = RequestMethod.GET)
    private List<TeachersModel> usersList(
            @RequestParam(required = false) List<WeekDaysEnum> statuses,
            @RequestParam(required = false) List<Long> course_id,
            @RequestParam(required = false) List<Integer> pupils,
            @RequestParam(required = false) String q){
        return  teachersService.findAll(statuses, course_id, pupils, q);
    }

    @RequestMapping(value="{id}", method = RequestMethod.GET)
    private AddTeachers findById(@PathVariable(name="id") Long id) {
        return teachersService.findByIdTeacherAdd(id);
    }


    @RequestMapping(value="update", method = RequestMethod.POST)
    private AddTeachers update(@RequestBody AddTeachers teachers) {
        return teachersService.update(teachers);
    }
}
