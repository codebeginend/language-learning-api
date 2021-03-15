package com.languages.learningapi.services;

import java.util.Date;
import java.util.List;

import com.languages.learningapi.models.controller.stat.retention.RetentionStat;
import com.languages.learningapi.models.controller.stat.school.SchoolPupilsCountStatusPeriod;
import com.languages.learningapi.models.controller.stat.school.TotalSchoolStatModel;
import com.languages.learningapi.models.controller.stat.teachers.TeacherStatPeriod;
import com.languages.learningapi.models.controller.stat.teachers.TotalTeacherStatModel;

public interface IStatService {
	TotalSchoolStatModel findTotalSchoolStat();
	SchoolPupilsCountStatusPeriod findDateSchoolStat(Date firstDate, Date lastDate);
	TotalTeacherStatModel findTotalTeacherStat(Long teacher_id);
	TeacherStatPeriod findPeriodStatOfTeacher(Long teacher_id, List<Long> course_ids, Date first, Date last);
	 RetentionStat findRetentionStat(Date firstDate, Date lastDate);
}
