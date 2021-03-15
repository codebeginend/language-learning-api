package com.languages.learningapi.models.controller;

import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;

public class CoursesProgressJournalModel {
	private Long course_reg_id;
	private Long teacher_id;
	private CoursesRegistersStatusesEnum status;
	private int score;
	
    private String theme;

    private String homework;

    private String note;

	public Long getCourse_reg_id() {
		return course_reg_id;
	}

	public void setCourse_reg_id(Long course_reg_id) {
		this.course_reg_id = course_reg_id;
	}

	public Long getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(Long teacher_id) {
		this.teacher_id = teacher_id;
	}

	public CoursesRegistersStatusesEnum getStatus() {
		return status;
	}

	public void setStatus(CoursesRegistersStatusesEnum status) {
		this.status = status;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getHomework() {
		return homework;
	}

	public void setHomework(String homework) {
		this.homework = homework;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
    
    
}
