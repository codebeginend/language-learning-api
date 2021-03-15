package com.languages.learningapi.models.controller;

import java.util.List;

import com.languages.learningapi.models.timetables.TimetablesCourses;

public class CoursesAdd {
	private Long course_id;
	private Long teacher_id;
	private String status;
	private List<TimetablesCourses> timetables;
	public Long getCourse_id() {
		return course_id;
	}
	public void setCourse_id(Long course_id) {
		this.course_id = course_id;
	}
	
	public Long getTeacher_id() {
		return teacher_id;
	}
	public void setTeacher_id(Long teacher_id) {
		this.teacher_id = teacher_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<TimetablesCourses> getTimetables() {
		return timetables;
	}
	public void setTimetables(List<TimetablesCourses> timetables) {
		this.timetables = timetables;
	}
}
