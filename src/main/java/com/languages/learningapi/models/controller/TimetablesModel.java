package com.languages.learningapi.models.controller;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.languages.learningapi.models.timetables.WeekDaysEnum;
import com.languages.learningapi.models.courses.CoursesRegisters;

public class TimetablesModel {
	

    @Enumerated(EnumType.STRING)
    private WeekDaysEnum weekDay;
    

    private int hourse;
    private int minute;
	
	private CoursesRegisters coursesRegister;
	
	

	public TimetablesModel() {
	}

	public TimetablesModel(WeekDaysEnum weekDay, int hourse, int minute, CoursesRegisters coursesRegister) {
		this.weekDay = weekDay;
		this.hourse = hourse;
		this.minute = minute;
		this.coursesRegister = coursesRegister;
	}

	public CoursesRegisters getCoursesRegister() {
		return coursesRegister;
	}

	public void setCoursesRegister(CoursesRegisters coursesRegister) {
		this.coursesRegister = coursesRegister;
	}

	public WeekDaysEnum getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(WeekDaysEnum weekDay) {
		this.weekDay = weekDay;
	}

	public int getHourse() {
		return hourse;
	}

	public void setHourse(int hourse) {
		this.hourse = hourse;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	
}
