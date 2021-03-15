package com.languages.learningapi.dao;

import com.languages.learningapi.models.courses.CoursesTeachers;
import com.languages.learningapi.models.statuses.CoursesTeachersStatusesEnum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICoursesTeachersDao extends JpaRepository<CoursesTeachers, Long>, CrudRepository<CoursesTeachers, Long> {
    List<CoursesTeachers> findAllByTeachers_id(Long teacher_id);
    
    @Query("select cr from CoursesTeachers cr where cr.courses_registers_id = :course_reg_id and cr.teachersStatusesEnum != :name")
    List<CoursesTeachers> findAllByCoursesRegisters_id(Long course_reg_id, CoursesTeachersStatusesEnum name);
}
