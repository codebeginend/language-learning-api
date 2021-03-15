package com.languages.learningapi.models.controller;

import java.util.List;

import com.languages.learningapi.models.timetables.TimetablesCourses;
import com.languages.learningapi.models.Pupils;
import com.languages.learningapi.models.Teachers;
import com.languages.learningapi.models.courses.Courses;
import com.languages.learningapi.models.courses.Lessons;
import com.languages.learningapi.models.statuses.CoursesRegStatuses;
import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;
import com.languages.learningapi.models.statuses.LearningStatuses;

public class CoursesRegistersModel {
	
	  private Long id;
	  private Long pupil_id;
	  private Pupils pupil;
	  Long createDate;
	  Long courseId;
	  private Courses course;
	  private LearningStatuses currentStatus;
	  private CoursesRegistersStatusesEnum statusName;
	  private CoursesRegistersStatusesEnum statusAdditional;
	  
	  private Teachers teacher;
	  private Long teacherId;
	  private List<TimetablesCourses> timetables;
	  private int lessonsDef;
	  private List<Lessons> lessons;

	  private List<CoursesRegStatuses> statuses;
	  

	  private List<LearningStatuses> learningStatuses;
	  
	  
	public CoursesRegistersModel() {
	}

	public CoursesRegistersModel(Long id, Long pupil_id, Pupils pupil, Long courseId, Courses course) {
		this.id = id;
		this.pupil_id = pupil_id;
		this.pupil = pupil;
		this.courseId = courseId;
		this.course = course;
	}

	public CoursesRegistersStatusesEnum getStatusName() {
		return statusName;
	}

	public void setStatusName(CoursesRegistersStatusesEnum statusName) {
		this.statusName = statusName;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPupil_id() {
		return pupil_id;
	}
	public void setPupil_id(Long pupil_id) {
		this.pupil_id = pupil_id;
	}
	
	public Pupils getPupil() {
		return pupil;
	}

	public void setPupil(Pupils pupil) {
		this.pupil = pupil;
	}

	public Long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	public Courses getCourse() {
		return course;
	}
	public void setCourse(Courses course) {
		this.course = course;
	}
	public LearningStatuses getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(LearningStatuses currentStatus) {
		this.currentStatus = currentStatus;
	}
	public Teachers getTeacher() {
		return teacher;
	}
	public void setTeacher(Teachers teacher) {
		this.teacher = teacher;
	}
	public List<TimetablesCourses> getTimetables() {
		return timetables;
	}
	public void setTimetables(List<TimetablesCourses> timetables) {
		this.timetables = timetables;
	}

	public int getLessonsDef() {
		return lessonsDef;
	}

	public void setLessonsDef(int lessonsDef) {
		this.lessonsDef = lessonsDef;
	}

	public List<Lessons> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lessons> lessons) {
		this.lessons = lessons;
	}

	public CoursesRegistersStatusesEnum getStatusAdditional() {
		return statusAdditional;
	}

	public void setStatusAdditional(CoursesRegistersStatusesEnum statusAdditional) {
		this.statusAdditional = statusAdditional;
	}

	public List<CoursesRegStatuses> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<CoursesRegStatuses> statuses) {
		this.statuses = statuses;
	}

	public List<LearningStatuses> getLearningStatuses() {
		return learningStatuses;
	}

	public void setLearningStatuses(List<LearningStatuses> learningStatuses) {
		this.learningStatuses = learningStatuses;
	}
	
	
}
