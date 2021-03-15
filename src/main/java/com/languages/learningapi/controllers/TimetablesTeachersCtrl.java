package com.languages.learningapi.controllers;

import java.util.List;

import com.languages.learningapi.models.timetables.TimetablesTeachers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.languages.learningapi.services.ITimetablesTeachersService;

@RestController
@RequestMapping(value = "teachers/timetables")
public class TimetablesTeachersCtrl {
	
	@Autowired
	private ITimetablesTeachersService service;
	
    @RequestMapping(value = "get/{teacher_id}", method = RequestMethod.GET)
    public List<TimetablesTeachers> findAllByTeacherId(@PathVariable(name="teacher_id") Long teacher_id){
    	
		return service.findAllByTeacherId(teacher_id);
    	
    }

}
