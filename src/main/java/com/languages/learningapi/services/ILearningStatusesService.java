package com.languages.learningapi.services;

import java.util.List;

import com.languages.learningapi.models.controller.CoursesRegistersModel;
import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;

public interface ILearningStatusesService {
    List<CoursesRegistersModel> findByCurrentStatusAndPupil_idAndTeacherId(
            List<CoursesRegistersStatusesEnum> statusesEnum, Long pupil_id, Long teacher_id);
    
}
