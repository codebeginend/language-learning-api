package com.languages.learningapi.models.controller;

public class AddPays {
	Long price;
	Long coursesRegId;
	int lessonsCount;
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Long getCoursesRegId() {
		return coursesRegId;
	}
	public void setCoursesRegId(Long coursesRegId) {
		this.coursesRegId = coursesRegId;
	}
	public int getLessonsCount() {
		return lessonsCount;
	}
	public void setLessonsCount(int lessonsCount) {
		this.lessonsCount = lessonsCount;
	}
	
	

}
