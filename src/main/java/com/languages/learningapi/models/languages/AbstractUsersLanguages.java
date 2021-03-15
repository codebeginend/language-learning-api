package com.languages.learningapi.models.languages;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.languages.learningapi.models.AbstractUsers;
import com.languages.learningapi.models.Languages;

@Entity
public class AbstractUsersLanguages {

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "users_id")
	private Long users_id;
	
	@Column(name ="languages_id")
	private Long languages_id;
	
    @ManyToOne
    @MapsId("users_id")
    @JoinColumn(name = "users_id", insertable = false, updatable = false)
    AbstractUsers users;
 
    @ManyToOne
    @MapsId("languages_id")
    @JoinColumn(name = "languages_id", insertable = false, updatable = false)
    Languages languages;

	public Languages getLanguages() {
		return languages;
	}

	public void setLanguages(Languages languages) {
		this.languages = languages;
	}


	public Long getUsers_id() {
		return users_id;
	}

	public void setUsers_id(Long users_id) {
		this.users_id = users_id;
	}

	public Long getLanguages_id() {
		return languages_id;
	}

	public void setLanguages_id(Long languages_id) {
		this.languages_id = languages_id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
