package com.languages.learningapi.models.courses;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Lessons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int def_count;
    private int count;
    private int def_minus_count;
    private int minus_count;
    private Long price;
    private Date date  = new Date();

    private Long courses_registers_id;

    @ManyToOne
    @JoinColumn(name = "courses_registers_id", insertable = false, updatable = false)
    private CoursesRegisters coursesRegisters;

    
    //Идентификатор платежей
    @Enumerated(EnumType.STRING)
    ReiterationEnums reiteration;

    
    //Статус окончания уроков, что бы знать что курс не закончен и нужна оплата
    @Enumerated(EnumType.STRING)
    LessonsStatusesEnum statuses;
    
    //Ссылка на изменения остатка уроков
    //private Long child_id;
    
    private Long parent_id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDef_count() {
        return def_count;
    }

    public void setDef_count(int def_count) {
        this.def_count = def_count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDef_minus_count() {
        return def_minus_count;
    }

    public void setDef_minus_count(int def_minus_count) {
        this.def_minus_count = def_minus_count;
    }

    public int getMinus_count() {
        return minus_count;
    }

    public void setMinus_count(int minus_count) {
        this.minus_count = minus_count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getCourses_registers_id() {
        return courses_registers_id;
    }

    public void setCourses_registers_id(Long courses_registers_id) {
        this.courses_registers_id = courses_registers_id;
    }

    public ReiterationEnums getReiteration() {
        return reiteration;
    }

    public void setReiteration(ReiterationEnums reiteration) {
        this.reiteration = reiteration;
    }

    public LessonsStatusesEnum getStatuses() {
        return statuses;
    }

    public void setStatuses(LessonsStatusesEnum statuses) {
        this.statuses = statuses;
    }

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

//	public Long getChild_id() {
//		return child_id;
//	}
//
//	public void setChild_id(Long child_id) {
//		this.child_id = child_id;
//	}
    
	
    
}
