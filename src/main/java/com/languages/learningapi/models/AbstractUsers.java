package com.languages.learningapi.models;


import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.languages.learningapi.models.languages.AbstractUsersLanguages;

@Entity
public abstract  class AbstractUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String region;
    private int gender;
    private int age;

    @Column
    private Long login_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "login_id", insertable = false, updatable = false)
    private Login login;
    
    @OneToMany(mappedBy = "users")
    List<AbstractUsersLanguages> languages;

    public AbstractUsers() {
	}

	public AbstractUsers(Long id, String name, String phone, String region, int gender, int age, Long login_id,
			Login login) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.region = region;
		this.gender = gender;
		this.age = age;
		this.login_id = login_id;
		this.login = login;
	}
	
	
	public AbstractUsers(Long id, String name, String phone, String region, int gender, int age, Long login_id) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.region = region;
		this.gender = gender;
		this.age = age;
		this.login_id = login_id;
	}

    public AbstractUsers(String name, String phone, String region, int gender, int age, Long login_id) {
        super();
        this.name = name;
        this.phone = phone;
        this.region = region;
        this.gender = gender;
        this.age = age;
        this.login_id = login_id;
    }

	public AbstractUsers(Long id, String name, String phone, String region, int gender, int age) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.region = region;
		this.gender = gender;
		this.age = age;
	}

	
	
	

	@Override
    public String toString() {
        return this.getLanguages().stream().map(map -> map.getLanguages().getName()).collect(Collectors.toList()).spliterator() + getName() + getPhone();
    }

	
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Long getLogin_id() {
        return login_id;
    }

    public void setLogin_id(Long login_id) {
        this.login_id = login_id;
    }

	public List<AbstractUsersLanguages> getLanguages() {
		return languages;
	}

	public void setLanguages(List<AbstractUsersLanguages> languages) {
		this.languages = languages;
	}
}
