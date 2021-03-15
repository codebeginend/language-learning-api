package com.languages.learningapi.dao;

import java.util.Date;
import java.util.List;

import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;
import com.languages.learningapi.models.statuses.LessonsStatuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ILessonsStatusesDao extends JpaRepository<LessonsStatuses, Long>{

    @Query("select cr from LessonsStatuses cr where cr.courses_registers_id = :course_id")
    List<LessonsStatuses> findByCourseRegisters_id(Long course_id);
    @Query("select cr from LessonsStatuses cr where cr.courses_registers_id = :course_id and name = :name")
    List<LessonsStatuses> findByCourseRegisters_idAndName(Long course_id, CoursesRegistersStatusesEnum name);
    @Query("select cr from LessonsStatuses cr where cr.teachers_id = :teachers_id and cr.name = :name and cr.date >= :first and cr.date <= :last")
    List<LessonsStatuses> findByTeachers_idAndName(Long teachers_id, CoursesRegistersStatusesEnum name, Date first, Date last);
    
    @Query("select cr from LessonsStatuses cr where cr.courses_registers_id = :course_reg_id and cr.date = (select MAX(cr.date) from LearningStatuses cr where cr.courses_registers_id = :course_reg_id)")
    LessonsStatuses findByCurrentFromCourseRegId(Long course_reg_id);
}

