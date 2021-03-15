package com.languages.learningapi.models.timetables;

import com.languages.learningapi.models.courses.CoursesRegisters;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TimetablesCourses extends AbstractRegTimetables{

    private Long coursesRegister_id;

    @ManyToOne()
    @JoinColumn(name = "coursesRegister_id", insertable = false, updatable = false)
    private CoursesRegisters coursesRegister;
    
    

	public TimetablesCourses() {
	}

	public TimetablesCourses(Long coursesRegister_id, Long timetable_id, int hourse, int minute, boolean used) {
		super(timetable_id, hourse, minute, used);
		this.coursesRegister_id = coursesRegister_id;
		// TODO Auto-generated constructor stub
	}

	public Long getCoursesRegister_id() {
		return coursesRegister_id;
	}

	public void setCoursesRegister_id(Long coursesRegister_id) {
		this.coursesRegister_id = coursesRegister_id;
	}
    
    
}
