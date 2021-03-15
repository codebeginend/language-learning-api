package com.languages.learningapi.dao;

import com.languages.learningapi.models.statuses.CoursesRegStatuses;
import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ICoursesRegistersStatusesDao extends JpaRepository<CoursesRegStatuses, Long>{
	@Query("select cr from CoursesRegStatuses cr where cr.courses_registers_id = :course_id")
    List<CoursesRegStatuses> findByCourseRegisters_id(Long course_id);
    
    @Query("select cr from CoursesRegStatuses cr where cr.courses_registers_id = :course_id")
    List<CoursesRegStatuses> findByCourseReg_id(Long course_id);
    
    
    @Query("select cr from CoursesRegStatuses cr where cr.date >= :first and cr.date <= :last")
    List<CoursesRegStatuses> findByPeriod(Date first, Date last);
    @Query("select cr from CoursesRegStatuses cr where cr.courses_registers_id in :ids and cr.date >= :first and cr.date <= :last")
    List<CoursesRegStatuses> findListCourseRegIdByPeriod(Iterable<Long> ids, Date first, Date last);
    @Query("select cr from CoursesRegStatuses cr where cr.teachers_id = :teachers_id and cr.date >= :first and cr.date <= :last")
    List<CoursesRegStatuses> findTeacherIdByPeriod(Long teachers_id, Date first, Date last);
    @Query("select cr from CoursesRegStatuses cr where cr.courses_registers_id in :ids")
    List<CoursesRegStatuses> findAllByCoursesRegistersId(Iterable<Long> ids);
    

    @Query("select cr from CoursesRegStatuses cr where (select MAX(id) from CoursesRegStatuses crs where crs.courses_registers_id = cr.courses_registers_id and crs.date <= :first) = cr.id and cr.name not in :arrStatus")
    List<CoursesRegStatuses> findAllFirstAndNotIntStatuses(Date first, List<CoursesRegistersStatusesEnum> arrStatus);
    
    @Query("select cr from CoursesRegStatuses cr where (select MAX(id) from CoursesRegStatuses crs where crs.courses_registers_id = cr.courses_registers_id and crs.date > :first and cr.date <= :after) = cr.id and cr.name not in :arrStatus")
    List<CoursesRegStatuses> findAllFirstAndAfterAndNotIntStatuses(Date first, Date after, List<CoursesRegistersStatusesEnum> arrStatus);
    
    @Query("select cr from CoursesRegStatuses cr where (select MAX(date) from CoursesRegStatuses crs where crs.courses_registers_id = cr.courses_registers_id and crs.date > :first and cr.date <= :after) = cr.date and cr.name in :arrStatus")
    List<CoursesRegStatuses> findAllFirstAndAfterAndInStatuses(Date first, Date after, List<CoursesRegistersStatusesEnum> arrStatus);
}
