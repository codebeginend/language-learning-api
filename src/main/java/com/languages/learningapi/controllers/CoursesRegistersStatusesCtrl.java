package com.languages.learningapi.controllers;

import com.languages.learningapi.dao.ITeachersDao;
import com.languages.learningapi.models.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.languages.learningapi.models.controller.CoursesProgressJournalModel;
import com.languages.learningapi.services.ILessonsStatusesService;

@RestController
@RequestMapping(value="courses/registers/statuses")
public class CoursesRegistersStatusesCtrl {
	
	@Autowired
	ILessonsStatusesService learningStatusesService;
	
    @Autowired
    private ITeachersDao teachersDao;
	
	
	@RequestMapping(value="journal", method = RequestMethod.POST)
	private void addJourlan(@RequestBody CoursesProgressJournalModel journalModel) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		learningStatusesService.saveJournal(journalModel, teachersDao.findByLogin_id(((Login)authentication.getPrincipal()).getId()).getId());
	}

}
