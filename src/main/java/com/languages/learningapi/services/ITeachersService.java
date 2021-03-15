package com.languages.learningapi.services;

import com.languages.learningapi.models.Teachers;
import com.languages.learningapi.models.controller.AddTeachers;
import com.languages.learningapi.models.controller.TeachersModel;
import com.languages.learningapi.models.timetables.WeekDaysEnum;

import java.util.List;

public interface ITeachersService {
    List<TeachersModel> findAll(List<WeekDaysEnum> statuses, List<Long>  course_id, List<Integer> pupils, String q);
    Teachers findById(Long id);
    Long countAllSchool();
    void addTeacher(AddTeachers teacher);

    AddTeachers findByIdTeacherAdd(Long id);
    AddTeachers update(AddTeachers teachers);
}
