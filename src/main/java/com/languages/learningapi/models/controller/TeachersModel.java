package com.languages.learningapi.models.controller;

import java.util.List;

import com.languages.learningapi.models.Login;
import com.languages.learningapi.models.Teachers;
import com.languages.learningapi.models.languages.AbstractUsersLanguages;

public class TeachersModel extends Teachers{
	private int pupilsDefCounts;
	private List<AbstractUsersLanguages> languages;
	
	
	

	public TeachersModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeachersModel(Long id, String name, String phone, String region, int gender, int age, Long login_id,
			Login login) {
		super(id, name, phone, region, gender, age, login_id, login);
		// TODO Auto-generated constructor stub
	}
	
	

	public TeachersModel(Long id, String name, String phone, String region, int gender, int age) {
		super(id, name, phone, region, gender, age);
		// TODO Auto-generated constructor stub
	}
	
	public TeachersModel(Long id, String name, String phone, String region, int gender, int age, List<AbstractUsersLanguages> languages) {
		super(id, name, phone, region, gender, age);
		this.languages = languages;
		// TODO Auto-generated constructor stub
	}

	public int getPupilsDefCounts() {
		return pupilsDefCounts;
	}

	public void setPupilsDefCounts(int pupilsDefCounts) {
		this.pupilsDefCounts = pupilsDefCounts;
	}

	public List<AbstractUsersLanguages> getLanguages() {
		return languages;
	}

	public void setLanguages(List<AbstractUsersLanguages> languages) {
		this.languages = languages;
	}
	
	
}
