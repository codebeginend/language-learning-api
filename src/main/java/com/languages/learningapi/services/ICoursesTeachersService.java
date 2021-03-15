package com.languages.learningapi.services;

import com.languages.learningapi.models.courses.CoursesTeachers;
import com.languages.learningapi.models.statuses.CoursesTeachersStatusesEnum;

import java.util.List;

public interface ICoursesTeachersService {
    List<CoursesTeachers> findAllByTeachers_id(Long teacher_id);
    List<CoursesTeachers> findAllFromCurrentTeacher(Long teacher_id);

    CoursesTeachers findCurrentTeacherByCourseRegId(Long course_reg_id);
    int findDefCountByMaxStatusAndTeacher(CoursesTeachersStatusesEnum status, Long teacher_id);
    
    void saveAll(List<CoursesTeachers> list);
    void save(CoursesTeachers item);
}
