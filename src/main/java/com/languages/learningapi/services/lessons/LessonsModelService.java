package com.languages.learningapi.services.lessons;

import com.languages.learningapi.models.courses.Lessons;
import com.languages.learningapi.services.ILessonsService;

public class LessonsModelService {

    private ILessonsService lessonsServiceo;

    private Lessons lesson;

    public LessonsModelService(ILessonsService lessonsService, Long course_reg_id){
        this.lessonsServiceo = lessonsService;
        this.lesson = this.lessonsServiceo.findByCurrentLesson(course_reg_id).get(0);
    }


    //Количество уроков на счету
    public int getCurrentTotalLessonFromLesson(){
        return (lesson.getCount()+lesson.getDef_count()) - (lesson.getMinus_count()+lesson.getDef_minus_count());
    }

    //Текущий урок
    public int getNextLessonNumberFromLesson(){
        return  (lesson.getMinus_count()+lesson.getDef_minus_count())+1;
    }

    public Lessons getLesson() {
        return lesson;
    }

    public void minusCount() {
        lesson.setMinus_count((lesson.getMinus_count()+1));
    }

    public void update() {
        lessonsServiceo.save(lesson);
    }
}
