package com.languages.learningapi.models.controller.stat.school;

import java.util.Map;

import com.languages.learningapi.models.courses.ReiterationEnums;
import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;

public class SchoolPupilsCountStatusPeriod {

	private Map<CoursesRegistersStatusesEnum, Long> mapStatusCountPupils;
	
	
	private Map<ReiterationEnums, Long> paysOfReiteration;
	private Map<String, Long> paysOfDay;
	private int totalCountPays;
	private Long totalPrice;
	

	public Map<CoursesRegistersStatusesEnum, Long> getMapStatusCountPupils() {
		return mapStatusCountPupils;
	}

	public void setMapStatusCountPupils(Map<CoursesRegistersStatusesEnum, Long> mapStatusCountPupils) {
		this.mapStatusCountPupils = mapStatusCountPupils;
	}

	public Map<ReiterationEnums, Long> getPaysOfReiteration() {
		return paysOfReiteration;
	}

	public void setPaysOfReiteration(Map<ReiterationEnums, Long> paysOfReiteration) {
		this.paysOfReiteration = paysOfReiteration;
	}

	public Map<String, Long> getPaysOfDay() {
		return paysOfDay;
	}

	public void setPaysOfDay(Map<String, Long> paysOfDay) {
		this.paysOfDay = paysOfDay;
	}

	public int getTotalCountPays() {
		return totalCountPays;
	}

	public void setTotalCountPays(int totalCountPays) {
		this.totalCountPays = totalCountPays;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}
}
