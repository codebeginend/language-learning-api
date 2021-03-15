package com.languages.learningapi.services;

import java.util.Date;
import java.util.List;

import com.languages.learningapi.models.controller.AddPays;
import com.languages.learningapi.models.courses.CoursesRegisters;
import com.languages.learningapi.models.courses.Lessons;
import com.languages.learningapi.models.courses.LessonsStatusesEnum;
import com.languages.learningapi.models.courses.ReiterationEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.languages.learningapi.dao.ILessonsDao;

@Service
public class LessonsServiceImpl implements ILessonsService{
	
	@Autowired
	ILessonsDao lessonsDao;
	
	@Autowired
	ICoursesRegistersService corsesRegService;

	@Override
	public int findLessonsDefByCourseReg(Long course_reg_id) {
			Lessons item = getCurrentLesson(course_reg_id);
			if(item != null) {
				return (item.getCount() + item.getDef_count()) - (item.getMinus_count() + item.getDef_minus_count());
			}else {
				return 0;
			}
		// TODO Auto-generated method stub
	}
	
	@Override
	public int findLessonsDefByPupil(Long pupilId) {
        int countDef = 0;
        List<CoursesRegisters> coursesRegList = this.corsesRegService.findByPupils_id(pupilId);
        if(coursesRegList != null) {
        	for(int ind = 0; ind < coursesRegList.size(); ind++) {
        		countDef += findLessonsDefByCourseReg(coursesRegList.get(ind).getId());
        	}
        }
        
        return countDef;
        
	}

	@Override
	public List<Lessons> findAllByCourseReg(Long course_reg_id) {
		// TODO Auto-generated method stub
		return lessonsDao.findByCourseRegId(course_reg_id);
	}
	
	@Override
	public void addPays(AddPays model) {
		Lessons newItem = new Lessons();
		newItem.setCount(model.getLessonsCount());
		newItem.setCourses_registers_id(model.getCoursesRegId());
		newItem.setPrice(model.getPrice());
		Lessons item = this.getCurrentLesson(model.getCoursesRegId());
		if(item != null) {
			item.setStatuses(LessonsStatusesEnum.PAYEND);
			newItem.setReiteration(item.getReiteration().next());
			newItem.setStatuses(LessonsStatusesEnum.PAYBEGIN);
			newItem.setDef_count(item.getCount() + item.getDef_count());
			newItem.setDef_minus_count(item.getMinus_count() + item.getDef_minus_count());
			this.lessonsDao.save(item);
		}else {

			newItem.setReiteration(ReiterationEnums.C1);
			newItem.setStatuses(LessonsStatusesEnum.PAYBEGIN);
		}
		this.lessonsDao.save(newItem);
	}

	@Override
	public void editPay(Long courseRegId, int editCount) {
		int lessonsCount = this.findLessonsDefByCourseReg(courseRegId);
		Lessons parent = this.getCurrentLesson(courseRegId);
		Lessons child = new Lessons();
		child.setCount(parent.getCount());
		child.setMinus_count(parent.getMinus_count());
		child.setDef_count(parent.getDef_count());
		child.setDef_minus_count(parent.getDef_minus_count());
		child.setCourses_registers_id(parent.getCourses_registers_id());
		child.setPrice(parent.getPrice());
		child.setReiteration(parent.getReiteration());
		if(lessonsCount > editCount) {
			int count = child.getCount() - (lessonsCount - editCount);
			if(count < 0) {
				count = 0;
				int def_count = (lessonsCount - editCount) - child.getCount();
				child.setDef_count(child.getDef_count() - def_count);
			}
			child.setCount(count);
			if((child.getCount()+child.getDef_count()) - (child.getMinus_count()+child.getDef_minus_count()) < 0){
				child.setStatuses(LessonsStatusesEnum.PAYEND);
			}else {
				child.setStatuses(LessonsStatusesEnum.PAYBEGIN);
			}
		}else if(lessonsCount != editCount){
			child.setCount(editCount);
			child.setStatuses(LessonsStatusesEnum.PAYBEGIN);
		}
		child.setParent_id(parent.getId());
		this.lessonsDao.save(child);
	}
	
	Lessons getCurrentLesson(Long courseRegId) {
		List<Lessons> list = this.findAllByCourseReg(courseRegId);

		if(list.size() != 0) {
			return list.stream().filter(predicate -> list.stream().max((a1, a2) -> a1.getDate().compareTo(a2.getDate())).get().getId() == predicate.getId()).findFirst().orElse(null);
		}else {
			return null;
		}
	}

	@Override
	public Lessons findCurrentLesson(Long course_reg_id) {
		List<Lessons> list = this.findAllByCourseReg(course_reg_id);
		Lessons lesson = list.stream().max((a, b)-> a.getDate().compareTo(b.getDate())).orElse(null);
		return lesson;
	}

	@Override
	public void save(Lessons lesson) {
		// TODO Auto-generated method stub
		this.lessonsDao.save(lesson);
	}

	@Override
	public List<Lessons> findByPeriodAndNotParentId(Date first, Date last) {
		// TODO Auto-generated method stub
		return this.lessonsDao.findByPeriodPaysAndNotParentId(first, last);
	}

	@Override
	public List<Lessons> findByCurrentLesson(Long course_reg_id) {
		return this.lessonsDao.findByCurrentLesson(course_reg_id);
	}

}
