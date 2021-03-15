package com.languages.learningapi.dao;

import java.util.List;

import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;
import com.languages.learningapi.models.statuses.LearningStatuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ILearningStatusesDao extends JpaRepository<LearningStatuses, Long>{

    @Query("select cr from LearningStatuses cr where cr.courses_registers_id = :course_id")
    List<LearningStatuses> findByCourseRegisters_id(Long course_id);
    @Query("select cr from LearningStatuses cr where cr.courses_registers_id = :course_id and name = :name")
    List<LearningStatuses> findByCourseRegisters_idAndName(Long course_id, CoursesRegistersStatusesEnum name);
    @Query("select cr from LearningStatuses cr where cr.courses_registers_id = :course_id and name in :name")
    List<LearningStatuses> findByCourseRegisters_idAndStatuses(Long course_id, List<CoursesRegistersStatusesEnum> name);
    
    
}
