package com.languages.learningapi.services;

import com.languages.learningapi.dao.ILessonsJournalDao;
import com.languages.learningapi.models.courses.LessonsJournal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonsJournalServiceImpl implements ILessonsJournalService{

	@Autowired
	private ILessonsJournalDao lessonsJournalDao;
	
	@Override
	public LessonsJournal save(LessonsJournal lessonJournal) {
		// TODO Auto-generated method stub
		return lessonsJournalDao.save(lessonJournal);
	}

}
