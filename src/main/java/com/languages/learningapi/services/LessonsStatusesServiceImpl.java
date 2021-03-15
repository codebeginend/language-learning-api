package com.languages.learningapi.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.languages.learningapi.dao.ILessonsDao;
import com.languages.learningapi.dao.ILessonsStatusesDao;
import com.languages.learningapi.models.courses.LessonsJournal;
import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;
import com.languages.learningapi.models.statuses.LessonsStatuses;
import com.languages.learningapi.models.statuses.PupilsStatuses;
import com.languages.learningapi.services.lessons.LessonsModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.languages.learningapi.models.controller.CoursesProgressJournalModel;

@Service
public class LessonsStatusesServiceImpl implements ILessonsStatusesService{

	@Autowired
	private ILessonsService lessonsService;
	
	@Autowired
	private IPupilsRegistersStatuses pupilsRegStatusesService;
	
	@Autowired
	private ILessonsJournalService lessonsJournalService;
	
	@Autowired
	private ILessonsStatusesDao dao;

	@Autowired
	private ILessonsDao lessonsDao;
	
	@Override
	public void saveJournal(CoursesProgressJournalModel journalModel, Long teacher_id) {
		LessonsModelService lesson = new LessonsModelService(lessonsService, journalModel.getCourse_reg_id());

		//Определить номер текущего урока
		if(lesson.getLesson() != null) {
			if(lesson.getCurrentTotalLessonFromLesson() > 0){
				List<CoursesRegistersStatusesEnum> pupilsStatuses = new ArrayList();
				pupilsStatuses.add(CoursesRegistersStatusesEnum.PUPILLEARNING);
				pupilsStatuses.add(CoursesRegistersStatusesEnum.PUPILFINISHED);
				pupilsStatuses.add(CoursesRegistersStatusesEnum.PUPILPOSTPONED);
				pupilsStatuses.add(CoursesRegistersStatusesEnum.PUPILCANCELPOSPONED);
				pupilsStatuses.add(CoursesRegistersStatusesEnum.PUPILTHREWANDDISAPPEARED);
				
				if(!pupilsStatuses.contains(journalModel.getStatus())) {

					LessonsStatuses lessonsStatuses = new LessonsStatuses();

					lessonsStatuses.setCourses_registers_id(journalModel.getCourse_reg_id());
					lessonsStatuses.setLessonnumber(lesson.getNextLessonNumberFromLesson());
					lessonsStatuses.setTeachers_id(teacher_id);
					lessonsStatuses.setName(journalModel.getStatus());
					
					//Сохранить журнал если статус == FINISHEDLESSON
					if(journalModel.getStatus() == CoursesRegistersStatusesEnum.FINISHEDLESSON) {
						LessonsJournal lessonJournal = new LessonsJournal(journalModel.getScore(), journalModel.getTheme(), journalModel.getHomework(), journalModel.getNote());
						lessonJournal = lessonsJournalService.save(lessonJournal);
						lessonsStatuses.setLessons_journal_id(lessonJournal.getId());
						
						//Сминусовать урок
						lesson.minusCount();
						lesson.update();
					}
					dao.save(lessonsStatuses);
				}else {
					PupilsStatuses pupilStatuses = new PupilsStatuses();
					pupilStatuses.setName(journalModel.getStatus());
					pupilStatuses.setTeachers_id(teacher_id);
					pupilsRegStatusesService.add(pupilStatuses);
				}
				

			}
		}
		//Сохранить статус
	}



	@Override
	public List<LessonsStatuses> findAllByTeacherIdByPeriod(Long teacher_id, CoursesRegistersStatusesEnum name, Date first, Date last) {
		// TODO Auto-generated method stub
		return this. dao.findByTeachers_idAndName(teacher_id, name, first, last);
	}


	public LessonsStatuses findByCurrentFromCourseRegId(Long courseRegId) {
		return this.dao.findByCurrentFromCourseRegId(courseRegId);
	}

}
