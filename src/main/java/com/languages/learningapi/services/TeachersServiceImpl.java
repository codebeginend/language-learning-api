package com.languages.learningapi.services;

import com.languages.learningapi.dao.*;
import com.languages.learningapi.models.Login;
import com.languages.learningapi.models.Roles;
import com.languages.learningapi.models.RolesEnum;
import com.languages.learningapi.models.Teachers;
import com.languages.learningapi.models.controller.AddTeachers;
import com.languages.learningapi.models.controller.TeachersModel;
import com.languages.learningapi.models.courses.CoursesRegisters;
import com.languages.learningapi.models.courses.CoursesTeachers;
import com.languages.learningapi.models.languages.AbstractUsersLanguages;
import com.languages.learningapi.models.statuses.CoursesTeachersStatusesEnum;
import com.languages.learningapi.models.timetables.Timetables;
import com.languages.learningapi.models.timetables.TimetablesCourses;
import com.languages.learningapi.models.timetables.WeekDaysEnum;
import com.speakarabic.augustapi.dao.*;
import com.speakarabic.augustapi.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeachersServiceImpl implements ITeachersService{

    @Autowired
    private ITeachersDao teachersDao;

    @Autowired
    ICoursesTeachersDao coursesTeachers;

    @Autowired
    ITimetablesCoursesService timetablesCoursesService;

    @Autowired
    ITimetablesService timetablesService;

    @Autowired
    ICoursesRegistersService coursesRegistersService;
    
    @Autowired
    ITimetablesTeachersService timetablesTeachersService;
    
    @Autowired
    ICoursesTeachersService coursesTeachersService;
    
    @Autowired
    IAbstractUsersLanguagesDao usersLanguagesDao;

    @Autowired
    ILoginDao loginDao;

    @Autowired
    IRolesDao rolesDao;

    @Override
    public List<TeachersModel> findAll(List<WeekDaysEnum> timetables, List<Long> course_id, List<Integer> pupils, String q) {
        List<Teachers> list = this.teachersDao.findAll();
        List<Teachers> teachersList = new ArrayList<>();
        
        
        List<AbstractUsersLanguages> listLang = new ArrayList();
        
        for(int index=0; index < list.size();index++) {
        	list.get(index).setLanguages(this.usersLanguagesDao.findByAbstractUsersId(list.get(index).getId()));
        }
        
        //Формируем новый список учителей 
        if(timetables != null || course_id != null){
            List<TimetablesCourses> timetablesCourses = new ArrayList<>();
            List<Timetables> timetablesList = new ArrayList<>();
            list.forEach((data)->{
                boolean isAdd = false;
                List<CoursesTeachers> coursesTeachers = this.coursesTeachers.findAllByTeachers_id(data.getId()).stream()
                        .filter(f-> f.getTeachersStatusesEnum() != CoursesTeachersStatusesEnum.DELETE).collect(Collectors.toList());
                if(coursesTeachers != null){
                    if(course_id != null){
                        List<CoursesRegisters> coursesRegistersList = new ArrayList<>();
                           coursesRegistersList.addAll(this.coursesRegistersService.findBy_id(coursesTeachers.stream().map(m-> m.getCourses_registers_id()).collect(Collectors.toList())));
                        if( coursesRegistersList.stream().map(m-> m.getCourses_id()).collect(Collectors.toList()).containsAll(course_id)){
                            isAdd = true;
                            coursesRegistersList.stream().forEach(dataCT-> {
                                timetablesCourses.addAll(this.timetablesCoursesService.findByCoursesRegister_id(dataCT.getId()));
                            });
                        }
                    }else{
                        isAdd = true;
                        coursesTeachers.stream().forEach(dataCT-> {
                            timetablesCourses.addAll(this.timetablesCoursesService.findByCoursesRegister_id(dataCT.getCourses_registers_id()));
                        });
                    }
                }

                timetablesList.addAll(timetablesService.findAllById(timetablesCourses.stream().map(m-> m.getTimetable_id()).collect(Collectors.toList())));
                if(timetables != null){
                    if(timetablesList.stream().map(m-> m.getWeekDay()).collect(Collectors.toList()).containsAll(timetables)){
                        isAdd = true;
                    }else{
                        isAdd = false;
                    }
                }
               

                if(isAdd){
                    teachersList.add(data);
                }

            });

        }else{
            teachersList.addAll(list);
        }
        
        List<TeachersModel> result = teachersList.stream().filter(f-> q == null?true: (f.getName() + f.getPhone() + f.getLanguages().stream().map(map-> map.getLanguages().getName()).collect(Collectors.toList()).spliterator()).toLowerCase().contains(q.toLowerCase())).map(mapper -> new TeachersModel(
        		mapper.getId(), mapper.getName(), mapper.getPhone(), mapper.getRegion(), mapper.getGender(), mapper.getAge(), mapper.getLanguages())).collect(Collectors.toList());
        

        for(int x=0; x < result.size();x++) {
        	result.get(x).setTimetables(this.timetablesTeachersService.findAllByTeacherId(result.get(x).getId()));
        	result.get(x).setPupilsDefCounts(this.coursesTeachersService.findDefCountByMaxStatusAndTeacher(CoursesTeachersStatusesEnum.ADD, result.get(x).getId()));
        }

        return result.stream().sorted((a, b)-> a.getId().compareTo(b.getId())).collect(Collectors.toList());
    }

	@Override
	public Teachers findById(Long id) {
		// TODO Auto-generated method stub
		return this.teachersDao.findById(id).orElse(null);
	}

	@Override
	public Long countAllSchool() {
		// TODO Auto-generated method stub
		return this.teachersDao.count();
	}

    @Override
    public void addTeacher(AddTeachers teacher) {
        Login login = new Login();
        login.setUsername(teacher.getLogin());
        login.setPassword(teacher.getPassword());
        login.setEnabled(true);
        Roles roles = this.rolesDao.findFirstByName(RolesEnum.ROLE_TEACHER.toString());
        login.addRole(roles);
        login = this.loginDao.save(login);
        Teachers teachers = new Teachers(teacher.getName(), teacher.getPhone(), teacher.getRegion(), teacher.getGender(), teacher.getAge(), login.getId());
        teachersDao.save(teachers);
    }



    @Override
    public AddTeachers findByIdTeacherAdd(Long id) {
        Teachers teachers = this.teachersDao.findById(id).orElse(null);
        AddTeachers addTeachers = new AddTeachers();
        addTeachers.setId(teachers.getId());
        addTeachers.setAge(teachers.getAge());
        addTeachers.setGender(teachers.getGender());
        addTeachers.setName(teachers.getName());
        addTeachers.setPhone(teachers.getPhone());
        addTeachers.setRegion(teachers.getRegion());
        addTeachers.setLogin(teachers.getLogin().getUsername());
        addTeachers.setPassword(teachers.getLogin().getPassword());
        return addTeachers;
    }

    @Override
    public AddTeachers update(AddTeachers teachers) {
        Teachers teacher = this.teachersDao.findById(teachers.getId()).orElse(null);
        teacher.setName(teachers.getName());
        teacher.setAge(teachers.getAge());
        teacher.setGender(
                teachers.getGender());
        teacher.setPhone(teachers.getPhone());
        teacher.setRegion(teachers.getRegion());
        teachersDao.save(teacher);
        Login login = this.loginDao.findById(teacher.getLogin_id()).orElse(null);
        login.setUsername(teachers.getLogin());
        login.setPassword(teachers.getPassword());
        loginDao.save(login);
        return findByIdTeacherAdd(teacher.getId());
    }
}
