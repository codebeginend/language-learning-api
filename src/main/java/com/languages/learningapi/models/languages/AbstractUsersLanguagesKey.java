package com.languages.learningapi.models.languages;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
class AbstractUsersLanguagesKey implements Serializable {
 
    @Column(name = "users_id")
    Long users_id;
 
    @Column(name = "languages_id")
    Long languages_id;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((languages_id == null) ? 0 : languages_id.hashCode());
		result = prime * result + ((users_id == null) ? 0 : users_id.hashCode());
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
		AbstractUsersLanguagesKey other = (AbstractUsersLanguagesKey) obj;
		if (languages_id == null) {
			if (other.languages_id != null)
				return false;
		} else if (!languages_id.equals(other.languages_id))
			return false;
		if (users_id == null) {
			if (other.users_id != null)
				return false;
		} else if (!users_id.equals(other.users_id))
			return false;
		return true;
	}
 
    
}