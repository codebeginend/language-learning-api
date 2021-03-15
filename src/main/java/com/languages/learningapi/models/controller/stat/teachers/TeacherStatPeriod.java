package com.languages.learningapi.models.controller.stat.teachers;

import java.util.Map;

import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;

public class TeacherStatPeriod {
	private Long count_finished_lessons;
	private Map<CoursesRegistersStatusesEnum, Long> mapStatusCountPupils;
	public Long getCount_finished_lessons() {
		return count_finished_lessons;
	}
	public void setCount_finished_lessons(Long count_finished_lessons) {
		this.count_finished_lessons = count_finished_lessons;
	}
	public Map<CoursesRegistersStatusesEnum, Long> getMapStatusCountPupils() {
		return mapStatusCountPupils;
	}
	public void setMapStatusCountPupils(Map<CoursesRegistersStatusesEnum, Long> mapStatusCountPupils) {
		this.mapStatusCountPupils = mapStatusCountPupils;
	}
	
	
}
