package com.languages.learningapi.services;

import com.languages.learningapi.models.Pupils;
import com.languages.learningapi.models.controller.AddPupilsModel;
import com.languages.learningapi.models.controller.FilterEnums;
import com.languages.learningapi.models.controller.PupilsModel;
import com.languages.learningapi.models.controller.TimetablesModel;
import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;
import com.languages.learningapi.models.timetables.WeekDaysEnum;


import java.util.List;
import java.util.Map;

public interface IPupilsService {
	Map<WeekDaysEnum, List<TimetablesModel>> findByCurrentTeacherMapTimetables(Long teacher_id, List<CoursesRegistersStatusesEnum> statuses, List<Long> course_id, String q);
	List<PupilsModel> findFromCurrentTeacher(Long teacher_id, List<CoursesRegistersStatusesEnum> statuses, List<Long>  course_id, String q);
    List<PupilsModel> findAll(List<CoursesRegistersStatusesEnum> statuses, List<Long>  course_id, String q, List<FilterEnums> filters);
    
    PupilsModel addPupil(AddPupilsModel pupil);
    void updatePupil(PupilsModel pupil, Long id);
    
    Pupils findById(Long id);
    
    Long countAllPupilsSchool();
}
