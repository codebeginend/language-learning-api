package com.languages.learningapi.services;

import java.util.Date;
import java.util.List;

import com.languages.learningapi.models.controller.AddPays;
import com.languages.learningapi.models.courses.Lessons;

public interface ILessonsService {
	int findLessonsDefByCourseReg(Long course_reg_id);
	Lessons findCurrentLesson(Long course_reg_id);
	int findLessonsDefByPupil(Long pupilId);
	List<Lessons> findAllByCourseReg(Long course_reg_id);
	void addPays(AddPays model);
	void editPay(Long courseRegId, int editCount);
	void save(Lessons lesson);
	List<Lessons> findByPeriodAndNotParentId(Date first, Date last);

    List<Lessons> findByCurrentLesson(Long course_reg_id);
}
