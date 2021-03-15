package com.languages.learningapi.models;

import com.languages.learningapi.models.courses.CoursesRegisters;

import javax.persistence.*;
import java.util.List;

@Entity
public class Pupils extends AbstractUsers {
    private String whatsapp;

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }
    
    
    

    public Pupils() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Pupils(Long id, String name, String phone, String region, int gender, int age, Long login_id, Login login, String whatsapp) {
		super(id, name, phone, region, gender, age, login_id, login);
		this.whatsapp = whatsapp;
		// TODO Auto-generated constructor stub
	}
	
	




	public Pupils(Long id, String name, String phone, String region, int gender, int age, Long login_id, String whatsapp) {
		super(id, name, phone, region, gender, age, login_id);
		this.whatsapp = whatsapp;
	}






	@OneToMany(mappedBy = "pupils", cascade = CascadeType.ALL)
    private List<CoursesRegisters> coursesRegisters;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
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
		Pupils other = (Pupils) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
    
    
    
}
