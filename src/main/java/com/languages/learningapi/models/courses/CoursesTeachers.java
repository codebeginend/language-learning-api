package com.languages.learningapi.models.courses;

import javax.persistence.*;

import com.languages.learningapi.models.statuses.CoursesTeachersStatusesEnum;
import com.languages.learningapi.models.Teachers;

import java.util.Date;

@Entity
//@IdClass(CoursesTeachersId.class)
public class CoursesTeachers {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long teachers_id;

    private Long courses_registers_id;

    @ManyToOne
    @JoinColumn(name = "teachers_id", insertable = false, updatable = false)
    private Teachers teachers;

    @ManyToOne
    @JoinColumn(name = "courses_registers_id", insertable = false, updatable = false)
    private CoursesRegisters coursesRegisters;

    @Enumerated(EnumType.STRING)
    private CoursesTeachersStatusesEnum teachersStatusesEnum;

    private Date date = new Date();
    
    
    public CoursesTeachers() {}
    public CoursesTeachers(Long teachers_id, Long courses_registers_id,
			CoursesTeachersStatusesEnum teachersStatusesEnum) {
		this.teachers_id = teachers_id;
		this.courses_registers_id = courses_registers_id;
		this.teachersStatusesEnum = teachersStatusesEnum;
	}
    
    

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTeachers_id() {
        return teachers_id;
    }

    public void setTeachers_id(Long teachers_id) {
        this.teachers_id = teachers_id;
    }

    public Long getCourses_registers_id() {
        return courses_registers_id;
    }

    public void setCourses_registers_id(Long courses_registers_id) {
        this.courses_registers_id = courses_registers_id;
    }

    public CoursesTeachersStatusesEnum getTeachersStatusesEnum() {
        return teachersStatusesEnum;
    }

    public void setTeachersStatusesEnum(CoursesTeachersStatusesEnum teachersStatusesEnum) {
        this.teachersStatusesEnum = teachersStatusesEnum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courses_registers_id == null) ? 0 : courses_registers_id.hashCode());
		result = prime * result + ((teachers_id == null) ? 0 : teachers_id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoursesTeachers other = (CoursesTeachers) obj;
		if (courses_registers_id == null) {
			if (other.courses_registers_id != null)
				return false;
		} else if (!courses_registers_id.equals(other.courses_registers_id))
			return false;
		if (teachers_id == null) {
			if (other.teachers_id != null)
				return false;
		} else if (!teachers_id.equals(other.teachers_id))
			return false;
		return true;
	}
    
    
    
}
