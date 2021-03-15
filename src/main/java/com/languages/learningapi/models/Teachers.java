package com.languages.learningapi.models;

import com.languages.learningapi.models.courses.CoursesTeachers;
import com.languages.learningapi.models.timetables.TimetablesTeachers;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Teachers extends AbstractUsers{
    @OneToMany(mappedBy = "teachers")
    private List<CoursesTeachers> coursesTeachers;
    
    @OneToMany(mappedBy="teacher")
    private List<TimetablesTeachers> timetables;
    
    

    public Teachers() {
		// TODO Auto-generated constructor stub
	}

	public Teachers(Long id, String name, String phone, String region, int gender, int age, Long login_id,
			Login login) {
		super(id, name, phone, region, gender, age, login_id, login);
		// TODO Auto-generated constructor stub
	}

	public Teachers(String name, String phone, String region, int gender, int age, Long login_id) {
		super(name, phone, region, gender, age, login_id);
		// TODO Auto-generated constructor stub
	}
	
	

	public Teachers(Long id, String name, String phone, String region, int gender, int age) {
		super(id, name, phone, region, gender, age);
		// TODO Auto-generated constructor stub
	}


	public List<TimetablesTeachers> getTimetables() {
		return timetables;
	}

	public void setTimetables(List<TimetablesTeachers> timetables) {
		this.timetables = timetables;
	}
	
	
    
    
}
