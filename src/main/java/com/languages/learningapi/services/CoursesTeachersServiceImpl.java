package com.languages.learningapi.services;

import com.languages.learningapi.dao.ICoursesTeachersDao;
import com.languages.learningapi.models.courses.CoursesRegisters;
import com.languages.learningapi.models.courses.CoursesTeachers;
import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;
import com.languages.learningapi.models.statuses.CoursesTeachersStatusesEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoursesTeachersServiceImpl implements ICoursesTeachersService{
    @Autowired
    private ICoursesTeachersDao coursesTeachersDao;
    
    @Autowired
    private ICoursesRegistersService coursesRegService;

    @Override
    public List<CoursesTeachers> findAllByTeachers_id(Long teacher_id) {
        return this.coursesTeachersDao.findAllByTeachers_id(teacher_id);
    }

    @Override
    public List<CoursesTeachers> findAllFromCurrentTeacher(Long teacher_id) {
        List<CoursesTeachers> coursesTeachers = this.coursesTeachersDao.findAllByTeachers_id(teacher_id);
        List<CoursesTeachers> list = new ArrayList<>();
        if(coursesTeachers != null)
             list = coursesTeachers.stream().filter(ct-> coursesTeachers.stream().filter(predicate-> predicate.getCourses_registers_id() == ct.getCourses_registers_id()).max((a,b)-> a.getDate().compareTo(b.getDate())).get().getTeachersStatusesEnum() == CoursesTeachersStatusesEnum.ADD && ct.getTeachersStatusesEnum() == CoursesTeachersStatusesEnum.ADD).collect(Collectors.toList());
        return list.stream().distinct().collect(Collectors.toList());
    }
    
    @Override
    public int findDefCountByMaxStatusAndTeacher(CoursesTeachersStatusesEnum status, Long teacher_id) {
        List<CoursesTeachers> coursesTeachers = this.coursesTeachersDao.findAllByTeachers_id(teacher_id);
        List<CoursesTeachers> list = new ArrayList<>();
        if(coursesTeachers != null) {

				list = coursesTeachers.stream().filter(ct-> coursesTeachers.stream().filter(predicate-> predicate.getCourses_registers_id() == ct.getCourses_registers_id()).max((a1, a2)-> a1.getDate().compareTo(a2.getDate())).get().getTeachersStatusesEnum() == status).collect(Collectors.toList());

            
        }
		//отсеивание курсов у которых нет статуса LEARNING и POSTPONED
        

        
        List<CoursesRegistersStatusesEnum> arrStatus = new ArrayList();
        arrStatus.add(CoursesRegistersStatusesEnum.LEARNING);
        arrStatus.add(CoursesRegistersStatusesEnum.POSTPONED);
        arrStatus.add(CoursesRegistersStatusesEnum.EXPECTATION);
        arrStatus.add(CoursesRegistersStatusesEnum.AGREED);
        
		List<CoursesRegisters> listCoursesReg = this.coursesRegService.findAllByArrIdByMaxStatuses(list.stream().map(mapper-> mapper.getCourses_registers_id()).distinct().collect(Collectors.toList()) , arrStatus);
        
		listCoursesReg = listCoursesReg.stream().distinct().collect(Collectors.toList());
		
        return listCoursesReg.size();
    }
    
    @Override
    public CoursesTeachers findCurrentTeacherByCourseRegId(Long course_reg_id) {
        List<CoursesTeachers> coursesTeachers = this.coursesTeachersDao.findAllByCoursesRegisters_id(course_reg_id, CoursesTeachersStatusesEnum.DELETE);
        return coursesTeachers.stream().max((a1, a2)-> a1.getDate().compareTo(a2.getDate())).filter(predicate-> predicate.getTeachersStatusesEnum() == CoursesTeachersStatusesEnum.ADD).orElse(null);
    }

	@Override
	public void saveAll(List<CoursesTeachers> list) {
		// TODO Auto-generated method stub
		this.coursesTeachersDao.saveAll(list);
	}

	@Override
	public void save(CoursesTeachers item) {
		this.coursesTeachersDao.save(item);
		// TODO Auto-generated method stub
		
	}
}
