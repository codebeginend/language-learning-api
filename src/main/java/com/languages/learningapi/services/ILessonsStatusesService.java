package com.languages.learningapi.services;

import java.util.Date;
import java.util.List;

import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;
import com.languages.learningapi.models.statuses.LessonsStatuses;
import com.languages.learningapi.models.controller.CoursesProgressJournalModel;

public interface ILessonsStatusesService {
	void saveJournal(CoursesProgressJournalModel journalModel, Long teacher_id);
	
	List<LessonsStatuses> findAllByTeacherIdByPeriod(Long teacher_id, CoursesRegistersStatusesEnum name, Date first, Date last);
	

	LessonsStatuses findByCurrentFromCourseRegId(Long courseRegId);
}
