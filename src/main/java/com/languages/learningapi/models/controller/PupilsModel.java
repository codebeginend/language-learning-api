package com.languages.learningapi.models.controller;

import java.util.List;

import com.languages.learningapi.models.timetables.TimetablesCourses;
import com.languages.learningapi.models.Login;
import com.languages.learningapi.models.Pupils;
import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;
import com.languages.learningapi.models.statuses.LearningStatuses;
import com.languages.learningapi.models.statuses.PupilsStatuses;

public class PupilsModel extends Pupils{
	private int totalDefLessons;
	private List<TimetablesCourses> timetables;
	private CoursesRegistersStatusesEnum currentStatus;
	private PupilsStatuses status;
	private LearningStatuses currentLessonStatus;

	public CoursesRegistersStatusesEnum getCurrentStatus() {
		return currentStatus;
	}


	public void setCurrentStatus(CoursesRegistersStatusesEnum currentStatus) {
		this.currentStatus = currentStatus;
	}


	public PupilsModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public PupilsModel(Long id, String name, String phone, String region, int gender, int age, Long login_id,
			Login login, String whatsapp) {
		super(id, name, phone, region, gender, age, login_id, login, whatsapp);
		// TODO Auto-generated constructor stub
	}
	
	


	public PupilsModel( Long id, String name, String phone, String region, int gender, int age, Long login_id,
			String whatsapp, int totalDefLessons, List<TimetablesCourses> timetables) {
		super(id, name, phone, region, gender, age, login_id, whatsapp);
		this.totalDefLessons = totalDefLessons;
		this.timetables = timetables;
	}


	public int getTotalDefLessons() {
		return totalDefLessons;
	}
	public void setTotalDefLessons(int totalDefLessons) {
		this.totalDefLessons = totalDefLessons;
	}
	public List<TimetablesCourses> getTimetables() {
		return timetables;
	}
	public void setTimetables(List<TimetablesCourses> timetables) {
		this.timetables = timetables;
	}


	public LearningStatuses getCurrentLessonStatus() {
		return currentLessonStatus;
	}


	public void setCurrentLessonStatus(LearningStatuses currentLessonStatus) {
		this.currentLessonStatus = currentLessonStatus;
	}


	public PupilsStatuses getStatus() {
		return status;
	}


	public void setStatus(PupilsStatuses status) {
		this.status = status;
	}


	
	
}
