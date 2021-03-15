package com.languages.learningapi.controllers;

import com.languages.learningapi.dao.ILoginDao;
import com.languages.learningapi.dao.IPupilsDao;
import com.languages.learningapi.dao.ITeachersDao;
import com.languages.learningapi.models.Login;
import com.languages.learningapi.models.Pupils;
import com.languages.learningapi.models.controller.AddPupilsModel;
import com.languages.learningapi.models.controller.FilterEnums;
import com.languages.learningapi.models.controller.PupilsModel;
import com.languages.learningapi.models.controller.TimetablesModel;
import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;
import com.languages.learningapi.models.timetables.WeekDaysEnum;

import java.util.Map;

import com.languages.learningapi.services.IPupilsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("pupils")
public class PupilsCtrl {

    @Autowired
    IPupilsDao pupilsDao;

    @Autowired
    IPupilsService pupilsService;

    @Autowired
    ILoginDao loginDao;

    @Autowired
    ITeachersDao teachersDao;

    @RequestMapping(value = "teacher/me", method = RequestMethod.GET)
    private List<PupilsModel> getFromCurrentTeacher(
            @RequestParam(required = false) List<CoursesRegistersStatusesEnum> statuses,
            @RequestParam(required = false) List<Long> course_id,
            @RequestParam(required = false) String q){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return pupilsService.findFromCurrentTeacher(teachersDao.findByLogin_id(((Login)auth.getPrincipal()).getId()).getId(),
                statuses, course_id, q);
    }
    
    @RequestMapping(value = "teacher/me/timetables", method = RequestMethod.GET)
    private Map<WeekDaysEnum, List<TimetablesModel>> getFromCurrentTeacherMapTimetables(
            @RequestParam(required = false) List<CoursesRegistersStatusesEnum> statuses,
            @RequestParam(required = false) List<Long> course_id,
            @RequestParam(required = false) String q){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return pupilsService.findByCurrentTeacherMapTimetables(teachersDao.findByLogin_id(((Login)auth.getPrincipal()).getId()).getId(),
                statuses, course_id, q);
    }

    @RequestMapping(method = RequestMethod.GET)
    private List<PupilsModel> usersList(
            @RequestParam(required = false) List<CoursesRegistersStatusesEnum> statuses,
            @RequestParam(required = false) List<Long> course_id,
            @RequestParam(required = false) List<FilterEnums> filters,
            @RequestParam(required = false) String q
            ){
        return  pupilsService.findAll(statuses, course_id, q, filters);
    }

    
    @RequestMapping(value="course", method = RequestMethod.POST)
    private PupilsModel addPupil(@RequestBody AddPupilsModel pupil) {
    	return pupilsService.addPupil(pupil);
    }
    
    @RequestMapping(value="{id}", method = RequestMethod.GET)
    private Pupils findById(@PathVariable(name="id") Long id) {
		return pupilsService.findById(id);
    }
    
    @RequestMapping(value="{id}", method = RequestMethod.PUT)
    private void updatePupil(@PathVariable(name="id") Long id, @RequestBody PupilsModel pupil) {
    	this.pupilsService.updatePupil(pupil, id);
    }


}
