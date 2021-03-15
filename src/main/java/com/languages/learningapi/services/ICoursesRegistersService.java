package com.languages.learningapi.services;

import com.languages.learningapi.models.controller.CoursesRegistersModel;
import com.languages.learningapi.models.courses.CoursesRegisters;
import com.languages.learningapi.models.statuses.CoursesRegStatuses;
import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;

import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public interface ICoursesRegistersService {
    List<CoursesRegisters> findAll(Specification specification);
    List<CoursesRegisters> findAllFromCurrentTeacher(Long teacher_id, List<CoursesRegistersStatusesEnum> arrStatus, List<Long>  course_id);
    List<CoursesRegisters> findAll(List<CoursesRegistersStatusesEnum> arrStatus, List<Long>  course_id);
    List<CoursesRegisters> findByPupils_id(Long pupil_id);
    List<CoursesRegistersModel> findByPupils_idToModel(Long pupil_id);

    CoursesRegisters findByCurrentStatusAndPupil_id(List<CoursesRegistersStatusesEnum> statusesEnum, Long pupil_id);
    List<CoursesRegistersModel> findByCurrentStatusAndPupil_idAndTeacherId(
			List<CoursesRegistersStatusesEnum> statusesEnum, Long pupil_id, Long teacher_id);
    List<CoursesRegStatuses> findByCourses_registers_id_id(Long course_reg_id);
    List<CoursesRegisters> findBy_id(Iterable<Long> id);
    List<CoursesRegisters> findBy_idAndIsNotStatuses(Iterable<Long> id, List<CoursesRegistersStatusesEnum> status);
    List<CoursesRegisters> findAllIdList(Iterable<Long> ids);
    CoursesRegisters save(CoursesRegisters coursesReg);
    CoursesRegStatuses saveStatus(CoursesRegStatuses coursesRegStatus);
    
    List<CoursesRegisters> findAllByArrIdByMaxStatuses(List<Long> arrRegId, List<CoursesRegistersStatusesEnum> arrStatus);
    
    CoursesRegisters findByCourseIdAndPupilId(Long course_id, Long pupil_id);
    
    void updateModel(CoursesRegistersModel model, Long courseRegId);
    
    

	List<CoursesRegStatuses> findCoursesRegStatusesByPeriod(Date first, Date last);

	List<CoursesRegStatuses> findByBeforeIsNotStatuses(Date first, List<CoursesRegistersStatusesEnum> arrStatus);
	
	List<CoursesRegStatuses> findByBeforeAndAfterIsNotStatuses(Date first, Date after, List<CoursesRegistersStatusesEnum> arrStatus);
	List<CoursesRegStatuses> findByBeforeAndAfterIsStatuses(Date first, Date after, List<CoursesRegistersStatusesEnum> arrStatus);
	
	List<CoursesRegStatuses> findCoursesRegStatusesByListIdsByPeriod(Iterable<Long> ids, Date first, Date last);
	List<CoursesRegStatuses> findCoursesRegStatusesByTeacherIdByPeriod(Long teacher_id, Date first, Date last);
	
	List<CoursesRegStatuses> findAllByIdList(Iterable<Long> ids);
	List<CoursesRegStatuses> findAllByCourseRegIdList(Iterable<Long> ids);
	CoursesRegStatuses findByCurrentStatusFromCourseRegId(Long id);
}
