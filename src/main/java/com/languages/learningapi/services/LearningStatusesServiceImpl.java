package com.languages.learningapi.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.languages.learningapi.dao.ILearningStatusesDao;
import com.languages.learningapi.models.controller.CoursesRegistersModel;
import com.languages.learningapi.models.courses.CoursesRegisters;
import com.languages.learningapi.models.courses.CoursesTeachers;
import com.languages.learningapi.models.statuses.CoursesRegStatuses;
import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;
import com.languages.learningapi.models.statuses.LearningStatuses;
import com.languages.learningapi.models.timetables.TimetablesCourses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LearningStatusesServiceImpl implements ILearningStatusesService {
	

    @Autowired
    private ICoursesTeachersService coursesTeachersService;
    
    @Autowired
    private ICoursesRegistersService coursesRegService;
    
    @Autowired
    private ILearningStatusesDao learningStatusesDao;
    
    @Autowired
    private ITimetablesCoursesService timetablesCoursesService;

	@Override
	public List<CoursesRegistersModel> findByCurrentStatusAndPupil_idAndTeacherId(
            List<CoursesRegistersStatusesEnum> statusesEnum, Long pupil_id, Long teacher_id) {
        List<CoursesRegisters> list = this.coursesRegService.findByPupils_id(pupil_id);
        List<CoursesRegistersModel> coursesRegistersModel = list.stream().map(mapper-> new CoursesRegistersModel(mapper.getId(), mapper.getPupils_id(), mapper.getPupils(), mapper.getCourses_id(), mapper.getCourses())).collect(Collectors.toList());
        List<CoursesRegistersModel> result = new ArrayList();
        for (int x=0;x< coursesRegistersModel.size(); x++){
        	CoursesTeachers coursesTeacher = this.coursesTeachersService.findCurrentTeacherByCourseRegId(coursesRegistersModel.get(x).getId());
        	if(coursesTeacher != null && coursesTeacher.getTeachers_id() == teacher_id) {
        		CoursesRegStatuses courseCurrentStatus = this.coursesRegService.findByCurrentStatusFromCourseRegId(coursesRegistersModel.get(x).getId());
        		
        		if(courseCurrentStatus.getName() != CoursesRegistersStatusesEnum.FINISHED) {
            		List<LearningStatuses> statuses = this.learningStatusesDao.findByCourseRegisters_id(coursesRegistersModel.get(x).getId());
            		
            		List<CoursesRegistersStatusesEnum> listStatIgnore = new ArrayList();
            		listStatIgnore.add(CoursesRegistersStatusesEnum.FINISHED);
            		listStatIgnore.add(CoursesRegistersStatusesEnum.THREWANDDISAPPEARED);
            		
            		if(statuses.size() != 0 && !listStatIgnore.contains(statuses.stream().max((a, b)-> a.getDate().compareTo(b.getDate())).map(map-> map.getName()).get())) {
            			statuses = statuses.stream().sorted(Comparator.comparingLong(LearningStatuses::getId)).collect(Collectors.toList());
            			coursesRegistersModel.get(x).setLearningStatuses(statuses);

                		result.add(coursesRegistersModel.get(x));
            		}
        		}
        	}
        }

        
        
		for(int index =0;index < result.size(); index++) {
			List<TimetablesCourses> listTimetables = this.timetablesCoursesService.findByCoursesRegister_idAndUsed(result.get(index).getId(), true);
			if(listTimetables.size() != 0) {
				result.get(index).setTimetables(listTimetables); 
			}
		}
        
        return result;
	}
	
	

}
