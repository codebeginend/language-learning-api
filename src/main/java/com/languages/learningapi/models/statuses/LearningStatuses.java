package com.languages.learningapi.models.statuses;

import javax.persistence.*;

import com.languages.learningapi.models.Teachers;
import com.languages.learningapi.models.courses.CoursesRegisters;
import com.languages.learningapi.models.courses.LessonsJournal;

import java.util.Date;

@Entity
public class LearningStatuses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CoursesRegistersStatusesEnum name;


    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    private Long courses_registers_id;

    private Long teachers_id;

    private int lessonnumber;

    @ManyToOne
    @JoinColumn(name = "teachers_id", insertable = false, updatable = false)
    private Teachers teachers;


    private Long lessons_journal_id;
    @ManyToOne
    @JoinColumn(name = "lessons_journal_id", insertable = false, updatable = false)
    private LessonsJournal lessonsJournal;



    @ManyToOne
    @JoinColumn(name = "courses_registers_id", insertable = false, updatable = false)
    private CoursesRegisters coursesRegisters;

    public CoursesRegistersStatusesEnum getName() {
        return name;
    }

    public void setName(CoursesRegistersStatusesEnum name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public int getLessonnumber() {
        return lessonnumber;
    }

    public void setLessonnumber(int lessonnumber) {
        this.lessonnumber = lessonnumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourses_registers_id() {
        return courses_registers_id;
    }

    public void setCourses_registers_id(Long courses_registers_id) {
        this.courses_registers_id = courses_registers_id;
    }

    public Long getTeachers_id() {
        return teachers_id;
    }

    public void setTeachers_id(Long teachers_id) {
        this.teachers_id = teachers_id;
    }

    public Long getLessons_journal_id() {
        return lessons_journal_id;
    }

    public void setLessons_journal_id(Long lessons_journal_id) {
        this.lessons_journal_id = lessons_journal_id;
    }

    public LessonsJournal getLessonsJournal() {
        return lessonsJournal;
    }

    public void setLessonsJournal(LessonsJournal lessonsJournal) {
        this.lessonsJournal = lessonsJournal;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courses_registers_id == null) ? 0 : courses_registers_id.hashCode());
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
		LearningStatuses other = (LearningStatuses) obj;
		if (courses_registers_id == null) {
			if (other.courses_registers_id != null)
				return false;
		} else if (!courses_registers_id.equals(other.courses_registers_id))
			return false;
		return true;
	}
    
    
}
