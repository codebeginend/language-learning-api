package com.languages.learningapi.models.statuses;

import java.util.ArrayList;
import java.util.List;

public enum  CoursesRegistersStatusesEnum {
    //courses
	//Начал курс
    LEARNING,
    //Закончил курс
    FINISHED,
    //Отложил курс
    POSTPONED,
    //Бросил и изчез с курса
    THREWANDDISAPPEARED,
    
    //Ожидание, согласился, отказался
    EXPECTATION,
    AGREED,
    REFUSED,
    
    
    //lessons
    FINISHEDLESSON,
    THREWLESSON,
    POSTPONEDLESSON,
    RETURNEDLESSON,
    CANCELEDLESSONPUPILRESPECTFUL,
    CANCELEDLESSONPUPILNOTRESPECTFUL,
    CANCELEDLESSONTEACHERRESPECTFUL,
    CANCELEDLESSONTEACHERNOTRESPECTFUL,
	
	
	//Pupils
    //Начал обучение
    PUPILLEARNING,
    //Закончил обучение
    PUPILFINISHED,
	//Отложил учебу
	PUPILPOSTPONED,
	//Вернулся к обучению
	PUPILCANCELPOSPONED,
	
	//Бросил и изчез
	PUPILTHREWANDDISAPPEARED;

    public static List<CoursesRegistersStatusesEnum> getCourseStatuses(){
        List<CoursesRegistersStatusesEnum> coursesRegistersStatuses = new ArrayList<>();
        coursesRegistersStatuses.add(LEARNING);
        //coursesRegistersStatuses.add(POSTPONED);
        coursesRegistersStatuses.add(FINISHED);
        //coursesRegistersStatuses.add(THREWANDDISAPPEARED);
        coursesRegistersStatuses.add(EXPECTATION);
        coursesRegistersStatuses.add(AGREED);
        coursesRegistersStatuses.add(REFUSED);

        return coursesRegistersStatuses;
    }
}
