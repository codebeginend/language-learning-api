package com.languages.learningapi.controllers;

import com.languages.learningapi.dao.ICoursesRegistersDao;
import com.languages.learningapi.dao.ITeachersDao;
import com.languages.learningapi.models.Login;
import com.languages.learningapi.models.controller.AddPays;
import com.languages.learningapi.models.controller.CoursesRegistersModel;
import com.languages.learningapi.models.courses.CoursesRegisters;
import com.languages.learningapi.models.statuses.CoursesRegStatuses;
import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;
import com.languages.learningapi.services.ILessonsService;
import com.languages.learningapi.controllers.specifications.CoursesRegistersSpecificationsBuilder;
import com.languages.learningapi.services.ICoursesRegistersService;
import com.languages.learningapi.services.ILearningStatusesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("courses/registers")
public class CoursesRegistersCtrl {

    @Autowired
    private ICoursesRegistersDao coursesRegistersDao;

    @Autowired
    private ICoursesRegistersService coursesRegistersService;
    
    @Autowired
    private ILearningStatusesService learningStatusesService;

    @Autowired
    private ITeachersDao teachersDao;

    @Autowired
    ILessonsService lessonsService;
    
    @RequestMapping(method = RequestMethod.POST)
    private CoursesRegisters coursesRegistersAdd(){
        CoursesRegisters coursesRegisters = new CoursesRegisters(2L, new Date(), 1L, 1L);
        return coursesRegistersDao.save(coursesRegisters);
    }

    @RequestMapping(value = "teacher/me", method = RequestMethod.GET)
    private List<CoursesRegisters> getAllFromCurrentTeacher(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Long> listLong = new ArrayList();
        listLong.add(1L);
        return coursesRegistersService.findAllFromCurrentTeacher(teachersDao.findByLogin_id(((Login)authentication.getPrincipal()).getId()).getId(),
                new ArrayList<>(), listLong);
    }

    @RequestMapping(value = "teacher/me/pupils", method = RequestMethod.GET)
    private List<CoursesRegisters> getMeAllPupilsFromCurrentTeacher(@RequestParam(value = "search") String search){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        CoursesRegistersSpecificationsBuilder builder = new CoursesRegistersSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        builder.with("teacherId", ":", teachersDao.findByLogin_id(((Login)authentication.getPrincipal()).getId()).getId());
        Specification<CoursesRegisters> spec = builder.build();

        return coursesRegistersService.findAll(spec);
    }


    @RequestMapping(value = "teacher/pupil/active/{pupil_id}", method = RequestMethod.GET)
    private CoursesRegisters findByLearningAndPupilid(@PathVariable(name = "pupil_id") Long pupil_id){
        List<CoursesRegistersStatusesEnum> list = new ArrayList<>();
        list.add(CoursesRegistersStatusesEnum.FINISHED);
        return coursesRegistersService.findByCurrentStatusAndPupil_id(list, pupil_id);
    }
    
    @RequestMapping(value = "teacher/pupil/current/{pupil_id}", method = RequestMethod.GET)
    private List<CoursesRegistersModel> findByLearningAndPupilidFromCurrentTeacher(@PathVariable(name = "pupil_id") Long pupil_id){
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	
        List<CoursesRegistersStatusesEnum> list = new ArrayList<>();
        //list.add(CoursesRegistersStatusesEnum.FINISHED);
        return learningStatusesService.findByCurrentStatusAndPupil_idAndTeacherId(list, pupil_id, teachersDao.findByLogin_id(((Login)authentication.getPrincipal()).getId()).getId());
    }
    
    
    
    @RequestMapping(value = "teacher/pupil/{pupil_id}", method = RequestMethod.GET)
    private List<CoursesRegistersModel> findByPupilid(@PathVariable(name = "pupil_id") Long pupil_id){
        return coursesRegistersService.findByPupils_idToModel(pupil_id);
    }
    
    @RequestMapping(value="teacher/{courseRegId}", method = RequestMethod.PUT)
    private void updateCourseRegModel(@PathVariable(name="courseRegId") Long courseRegId, @RequestBody CoursesRegistersModel model) {
    	this.coursesRegistersService.updateModel(model, courseRegId);
    }

    @RequestMapping(value = "statuses/{course_reg_id}", method = RequestMethod.GET)
    private List<CoursesRegStatuses> findByCourseRegId(@PathVariable(name = "course_reg_id") Long course_reg_id){
        return coursesRegistersService.findByCourses_registers_id_id(course_reg_id);
    }
    
    @RequestMapping(value="pays", method = RequestMethod.POST)
    private void addPays(@RequestBody AddPays model) {
    	this.lessonsService.addPays(model);
    }
    
    @RequestMapping(value="pays/{payId}", method = RequestMethod.PUT)
    private void addPays(@PathVariable(name="payId") Long courseRegId, @RequestBody int countLessonsDef) {
    	this.lessonsService.editPay(courseRegId, countLessonsDef);
    }
}
