package com.languages.learningapi.models.timetables;

import javax.persistence.*;
import java.util.List;

@Entity
public class Timetables {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WeekDaysEnum weekDay;

    private Long parent_id;

    @Transient
    private boolean isUsed = false;
    @Transient
    private boolean isUpdate = false;


    @OneToMany(mappedBy = "timetable")
    List<AbstractRegTimetables> reg_timetables;

    public WeekDaysEnum getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDaysEnum weekDay) {
        this.weekDay = weekDay;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<AbstractRegTimetables> getReg_timetables() {
        return reg_timetables;
    }

    public void setReg_timetables(List<AbstractRegTimetables> reg_timetables) {
        this.reg_timetables = reg_timetables;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }
}
