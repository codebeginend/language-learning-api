package com.languages.learningapi.dao;

import com.languages.learningapi.models.courses.CoursesRegisters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICoursesRegistersDao extends JpaRepository<CoursesRegisters, Long>, CrudRepository<CoursesRegisters, Long>, JpaSpecificationExecutor<CoursesRegisters> {
    List<CoursesRegisters> findByPupils_id(Long pupil_id);
    
    @Query("select cr from CoursesRegisters cr where cr.courses_id = :course_id and cr.pupils_id = :pupil_id")
    CoursesRegisters findByCourseIdAndPupilId(Long course_id, Long pupil_id);
    
}
