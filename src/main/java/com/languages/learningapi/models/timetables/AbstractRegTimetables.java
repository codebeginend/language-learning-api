package com.languages.learningapi.models.timetables;

import javax.persistence.*;

@Entity
public class AbstractRegTimetables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long timetable_id;

    @ManyToOne
    @JoinColumn(name = "timetable_id", insertable = false, updatable = false)
    private Timetables timetable;


    @Column()
    private int hourse;
    private int minute;
    
    private boolean used;
    
    
    

    public AbstractRegTimetables() {
	}

	public AbstractRegTimetables(Long timetable_id, int hourse, int minute, boolean used) {
		this.timetable_id = timetable_id;
		this.hourse = hourse;
		this.minute = minute;
		this.used = used;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHourse() {
        return hourse;
    }

    public void setHourse(int hourse) {
    	this.hourse = hourse;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
    
	public Long getTimetable_id() {
        return timetable_id;
    }

    public void setTimetable_id(Long timetable_id) {
        this.timetable_id = timetable_id;
    }

    public Timetables getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetables timetable) {
        this.timetable = timetable;
    }

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
}
