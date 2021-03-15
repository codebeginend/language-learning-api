package com.languages.learningapi.controllers;

import java.util.Date;
import java.util.List;

import com.languages.learningapi.models.controller.stat.retention.RetentionStat;
import com.languages.learningapi.models.controller.stat.school.SchoolPupilsCountStatusPeriod;
import com.languages.learningapi.models.controller.stat.school.TotalSchoolStatModel;
import com.languages.learningapi.models.controller.stat.teachers.TeacherStatPeriod;
import com.languages.learningapi.models.controller.stat.teachers.TotalTeacherStatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.languages.learningapi.services.IStatService;

@RestController
@RequestMapping("stat/mobile")
public class MobileStatController {
	
	@Autowired
	private IStatService statService;

	
	@RequestMapping(value="total/school", method = RequestMethod.GET)
	private TotalSchoolStatModel getTotalStatSchool() {
		return statService.findTotalSchoolStat();
	}
	
	@RequestMapping(value="date/school/{firstDate}/{lastDate}", method = RequestMethod.GET)
	private SchoolPupilsCountStatusPeriod getDateStatInSchool(@PathVariable(name="firstDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date firstDate,
                                                              @PathVariable(name="lastDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date lastDate) {
		lastDate.setHours(24);
		return this.statService.findDateSchoolStat(firstDate, lastDate);
		}
	
	
	@RequestMapping(value="total/teacher/{teacher_id}", method = RequestMethod.GET)
	private TotalTeacherStatModel getTotalStatTeacher(@PathVariable(name ="teacher_id") Long teacher_id) {
		return statService.findTotalTeacherStat(teacher_id);
	}
	
	@RequestMapping(value="period/teacher", method = RequestMethod.GET)
	private TeacherStatPeriod getPeriodStatTeacher(@RequestParam(required = true, name="teacher_id") Long teacher_id, @RequestParam(required = false) List<Long> course_ids, @RequestParam(name="first") @DateTimeFormat(pattern = "dd-MM-yyyy") Date first,
                                                   @RequestParam(name="last") @DateTimeFormat(pattern = "dd-MM-yyyy") Date last) {
		last.setHours(24);
		return statService.findPeriodStatOfTeacher(teacher_id, course_ids, first, last);
	}
	
	@RequestMapping(value="date/retention/{first}/{last}", method = RequestMethod.GET)
	private RetentionStat getDateStatRetention(@PathVariable(name="first") @DateTimeFormat(pattern = "dd-MM-yyyy") Date firstDate,
                                               @PathVariable(name="last") @DateTimeFormat(pattern = "dd-MM-yyyy") Date lastDate) {
		lastDate.setHours(24);
		return this.statService.findRetentionStat(firstDate, lastDate);
		}
}
