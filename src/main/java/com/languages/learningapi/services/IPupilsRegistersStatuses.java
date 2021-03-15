package com.languages.learningapi.services;

import java.util.Date;
import java.util.List;

import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;
import com.languages.learningapi.models.statuses.PupilsStatuses;

public interface IPupilsRegistersStatuses {
	void add(PupilsStatuses status);

	PupilsStatuses findByCurrentStatus(Long id);
	

	List<PupilsStatuses> findByBeforeIsNotStatuses(Date first, List<CoursesRegistersStatusesEnum> arrStatus);
	List<PupilsStatuses> findByBeforeAndAfterIsNotStatuses(Date first, Date after, List<CoursesRegistersStatusesEnum> arrStatus);
	List<PupilsStatuses> findByBeforeAndAfterIsStatuses(Date first, Date after, List<CoursesRegistersStatusesEnum> arrStatus);
}
