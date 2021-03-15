package com.languages.learningapi.models.courses;

import com.languages.learningapi.models.statuses.CoursesRegStatuses;
import com.languages.learningapi.models.timetables.TimetablesCourses;
import com.languages.learningapi.models.Pupils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class CoursesRegisters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long pupils_id;

    @ManyToOne
    @JoinColumn(name = "pupils_id", insertable = false, updatable = false)
    private Pupils pupils;

    private Date create_date;

    @Column
    private Long courses_id;
    

    @OneToMany(mappedBy = "coursesRegister", fetch = FetchType.LAZY)
    private List<TimetablesCourses> timetables = new ArrayList<>();
    
    

    public void setCourses_id(Long courses_id) {
		this.courses_id = courses_id;
	}
    
    

	public void setPupils_id(Long pupils_id) {
		this.pupils_id = pupils_id;
	}
	
	
	
	



	public List<TimetablesCourses> getTimetables() {
		return timetables;
	}



	public void setTimetables(List<TimetablesCourses> timetables) {
		this.timetables = timetables;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pupils_id == null) ? 0 : pupils_id.hashCode());
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
		CoursesRegisters other = (CoursesRegisters) obj;
		if (pupils_id == null) {
			if (other.pupils_id != null)
				return false;
		} else if (!pupils_id.equals(other.pupils_id))
			return false;
		return true;
	}





	@ManyToOne
    @JoinColumn(name = "courses_id", insertable = false, updatable = false)
    private Courses courses;

    @OneToMany(mappedBy = "coursesRegisters")
    private List<CoursesTeachers> coursesTeachers = new ArrayList<>();

    @OneToMany(mappedBy = "coursesRegisters", fetch = FetchType.LAZY)
    private List<CoursesRegStatuses> statuses = new ArrayList();


    public List<CoursesRegStatuses> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<CoursesRegStatuses> statuses) {
        this.statuses = statuses;
    }

    public CoursesRegisters() {
    }

    /**
     *
     * @param pupils_id
     * @param create
     * @param courses_id
     * @param teachers_id
     */
    public CoursesRegisters(Long pupils_id, Date create, Long courses_id, Long teachers_id) {
        this.pupils_id = pupils_id;
        this.create_date = create;
        this.courses_id = courses_id;
    }

    public void addTeacher(CoursesTeachers coursesTeachers){
        this.coursesTeachers.add(coursesTeachers);
    }

    public Long getId() {
        return id;
    }

    public Long getPupils_id() {
        return pupils_id;
    }

    public Pupils getPupils() {
        return pupils;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public Long getCourses_id() {
        return courses_id;
    }

    public Courses getCourses() {
        return courses;
    }

    public List<CoursesTeachers> getTeachers() {
        return coursesTeachers;
    }
}
