package com.languages.learningapi.dao;

import com.languages.learningapi.models.courses.LessonsJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILessonsJournalDao extends JpaRepository<LessonsJournal, Long>{
	
}
