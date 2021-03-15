package com.languages.learningapi.models.timetables;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.languages.learningapi.models.Teachers;

@Entity
public class TimetablesTeachers extends AbstractRegTimetables{


    private Long teacher_id;
    
    private int endHourse;
    private int endMinute;

    @ManyToOne()
    @JoinColumn(name = "teacher_id", insertable = false, updatable = false)
    private Teachers teacher;

    public Long getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Long teacher_id) {
        this.teacher_id = teacher_id;
    }

	public Teachers getTeacher() {
		return teacher;
	}

	public void setTeacher(Teachers teacher) {
		this.teacher = teacher;
	}

	public int getEndHourse() {
		return endHourse;
	}

	public void setEndHourse(int endHourse) {
		this.endHourse = endHourse;
	}

	public int getEndMinute() {
		return endMinute;
	}

	public void setEndMinute(int endMinute) {
		this.endMinute = endMinute;
	}


}
