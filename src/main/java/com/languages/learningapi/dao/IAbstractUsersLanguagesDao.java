package com.languages.learningapi.dao;

import java.util.List;

import com.languages.learningapi.models.languages.AbstractUsersLanguages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IAbstractUsersLanguagesDao extends JpaRepository<AbstractUsersLanguages, Long>{

	@Query("select aul from AbstractUsersLanguages aul where aul.users_id = :user_id")
	List<AbstractUsersLanguages> findByAbstractUsersId(Long user_id);
}
