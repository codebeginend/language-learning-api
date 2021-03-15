package com.languages.learningapi.services;

import com.languages.learningapi.dao.ICoursesDao;
import com.languages.learningapi.dao.IPupilsDao;
import com.languages.learningapi.models.Pupils;
import com.languages.learningapi.models.controller.AddPupilsModel;
import com.languages.learningapi.models.controller.FilterEnums;
import com.languages.learningapi.models.controller.PupilsModel;
import com.languages.learningapi.models.controller.TimetablesModel;
import com.languages.learningapi.models.courses.CoursesRegisters;
import com.languages.learningapi.models.courses.CoursesTeachers;
import com.languages.learningapi.models.timetables.TimetablesCourses;
import com.languages.learningapi.models.timetables.WeekDaysEnum;
import com.languages.learningapi.models.statuses.CoursesRegStatuses;
import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;
import com.languages.learningapi.models.statuses.CoursesTeachersStatusesEnum;
import com.languages.learningapi.models.statuses.LessonsStatuses;
import com.languages.learningapi.models.statuses.PupilsStatuses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PupilsServiceImpl implements IPupilsService{
    @Autowired
    private IPupilsDao pupilsDao;

    @Autowired
    private ICoursesRegistersService coursesRegistersService;
    
    @Autowired
    private ILessonsService lessonsService;
    
    @Autowired
    private ICoursesTeachersService coursesTeachersService;
    
    @Autowired
    private ITimetablesCoursesService timetablesCoursesService;
    
    @Autowired
    private ICoursesDao coursesDao;
    
    @Autowired
    private ILearningStatusesService learningStatusesService;
    
    @Autowired
    private ILessonsStatusesService lessonsStatusesService;
    
    @Autowired
    private IPupilsRegistersStatuses pupilsRegStatuses;
    

    @Override
    public List<PupilsModel> findFromCurrentTeacher(Long teacher_id, List<CoursesRegistersStatusesEnum> statuses, List<Long>  course_id, String q) {
    	List<CoursesRegisters> coursesRegistersList = this.coursesRegistersService.findAllFromCurrentTeacher(teacher_id, statuses, course_id);
        List<PupilsModel> list = new ArrayList<>();
        coursesRegistersList.forEach(data-> {
        	
        	Pupils pupil = findByCourseRegAndFilter(data, q, null);
        	PupilsModel item = null;
        	if(pupil != null) {
        		LessonsStatuses lesrningCurrentStatus = this.lessonsStatusesService.findByCurrentFromCourseRegId(data.getId());
            		item = new PupilsModel(
                			pupil.getId(), pupil.getName(), pupil.getPhone(), pupil.getRegion(), pupil.getGender(), pupil.getAge(), pupil.getLogin_id(), pupil.getLogin(), pupil.getWhatsapp());
            	

            		PupilsStatuses pupilsCurrentStatus = pupilsRegStatuses.findByCurrentStatus(item.getId());
            		
                	if(item != null) {
                  	  item.setTotalDefLessons(item.getTotalDefLessons() + this.lessonsService.findLessonsDefByCourseReg(data.getId()));
                  	}
                	
                	if(item != null) {
                		if(pupilsCurrentStatus != null) {
                          	 item.setCurrentStatus(pupilsCurrentStatus.getName());
                          	 item.setStatus(pupilsCurrentStatus);
                		}
                   	 item.setCurrentLessonStatus(lesrningCurrentStatus);
                   	}
                	
                   	
                  	
                  	List<TimetablesCourses> listTimetables = this.timetablesCoursesService.findByCoursesRegister_idAndUsed(data.getId(), true);
                  	if(item != null && item.getTimetables() != null && listTimetables != null) {
                      	listTimetables.addAll(item.getTimetables());
                      	item.setTimetables(listTimetables);
                  	}else if(item != null && listTimetables != null) {
                      	item.setTimetables(listTimetables);
                  	}
                  	if(item != null) {
                  		list.add(item);
                  	}
        	}
        	

        });
        
        return list;
    }

    @Override
    public List<PupilsModel> findAll(List<CoursesRegistersStatusesEnum> statuses, List<Long> course_id, String q, List<FilterEnums> filters) {
    	List<CoursesRegisters> coursesRegList = this.coursesRegistersService.findAll(statuses, course_id);
    	
        List<PupilsModel> list = new ArrayList<>();
        
        for(int index = 0;index < coursesRegList.size();index++) {
        	Pupils pupil = findByCourseRegAndFilter(coursesRegList.get(index), q, filters);
        	PupilsModel item = null;
        	if(pupil != null) {
        		item = new PupilsModel(
            			pupil.getId(), pupil.getName(), pupil.getPhone(), pupil.getRegion(), pupil.getGender(), pupil.getAge(), pupil.getLogin_id(), pupil.getLogin(), pupil.getWhatsapp());
        	}
        	
        	if(item != null) {
        	  item.setTotalDefLessons(item.getTotalDefLessons() + this.lessonsService.findLessonsDefByCourseReg(coursesRegList.get(index).getId()));
        	}
        	
        	if(item != null) {
        	 item.setCurrentStatus(coursesRegistersService.findByCurrentStatusFromCourseRegId(coursesRegList.get(index).getId()).getName());
        	}
        	
        	List<TimetablesCourses> listTimetables = this.timetablesCoursesService.findByCoursesRegister_idAndUsed(coursesRegList.get(index).getId(), true);
        	if(item != null && item.getTimetables() != null && listTimetables != null) {
            	listTimetables.addAll(item.getTimetables());
            	item.setTimetables(listTimetables);
        	}else if(item != null && listTimetables != null) {
            	item.setTimetables(listTimetables);
        	}
        	if(item != null) {
        		list.add(item);
        	}
        }


        if(filters != null && filters.contains(FilterEnums.NOTLESSONS)) {
        	list = list.stream().filter(predicate-> predicate.getTotalDefLessons() == 0).collect(Collectors.toList());
        }
        
        return  list.stream().distinct().collect(Collectors.toList());
    }
    
    
    
    //Pupils timetables - для страницы расписания с аккаунта учителя
    public Map<WeekDaysEnum, List<TimetablesModel>> findByCurrentTeacherMapTimetables(Long teacher_id, List<CoursesRegistersStatusesEnum> statuses, List<Long> course_id, String q) {
    	List<CoursesRegisters> listCoursesReg = this.coursesRegistersService.findAllFromCurrentTeacher(teacher_id, statuses, course_id);
    	
		
        List<TimetablesModel> timetablesModelList = new ArrayList<>();
        timetablesModelList = coursesRegToPupilsModelAndFilter(listCoursesReg, q);
        
        Map<WeekDaysEnum, List<TimetablesModel>> mapList = timetablesModelList.stream().sorted((a, b)-> Integer.compare(a.getHourse(), b.getHourse())).collect(Collectors.groupingBy(TimetablesModel::getWeekDay, Collectors.toList()));

        Map<WeekDaysEnum, List<TimetablesModel>> listMap = new LinkedHashMap();
     
        if(mapList.size() != 0) {
        	
        Calendar c=Calendar.getInstance();
        SimpleDateFormat sd=new SimpleDateFormat("EEEE", Locale.ENGLISH);
        WeekDaysEnum currentWeekDay = WeekDaysEnum.valueOf(sd.format(c.getTime()).toUpperCase());
        
        
        
        List<TimetablesModel> todayList = mapList.get(currentWeekDay);
        if(todayList != null) {
            mapList.put(WeekDaysEnum.TODAY, todayList);
            mapList.remove(currentWeekDay);
        }

            listMap = mapList.entrySet().stream()
                    .sorted((e1,e2) -> e1.getKey().compareTo(e2.getKey()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2) -> e1, LinkedHashMap::new));
            
        }

        
        return listMap;
    }
    
    //Отдает модели учеников с со всеми курсами и расписанием по ним
    List<TimetablesModel> coursesRegToPupilsModelAndFilter(List<CoursesRegisters> coursesRegistersList, String q){
    	
    	List<TimetablesModel> list = new ArrayList<>();
    	
        if(coursesRegistersList != null){
            coursesRegistersList.stream().forEach(data-> {
            	
        		List<TimetablesCourses> listTimetables = this.timetablesCoursesService.findByCoursesRegister_idAndUsed(data.getId(), true);
        		if(listTimetables.size() != 0) {
        			list.addAll(listTimetables.stream().map(mapper-> new TimetablesModel(mapper.getTimetable().getWeekDay(), mapper.getHourse(), mapper.getMinute(), data)).collect(Collectors.toList())); 
        		}else {

        			list.add(new TimetablesModel(WeekDaysEnum.NOTACCURATE, 0, 0, data)); 
        		}
            });
            
        }
        
       return list.stream().collect(Collectors.toList());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    List<Pupils> findByCoursesRegAndFilter(List<CoursesRegisters> coursesRegistersList, String q, List<FilterEnums> filters){
    	List<Pupils> list = new ArrayList<>();
    	
        if(coursesRegistersList != null){
            coursesRegistersList.stream().forEach(data-> {
            	Pupils pupils = null;
            	boolean isAdd = false;
            	if(filters != null && filters.contains(FilterEnums.NOTTEACHER)) {
            		if(this.coursesTeachersService.findCurrentTeacherByCourseRegId(data.getId()) == null) {
            			isAdd = true;
            		}else {
            			isAdd = false;
            		}
            	}else {
            		isAdd = true;
            	}
            	if(isAdd) {
            		pupils = this.pupilsDao.findById(data.getPupils_id()).filter(f-> q == null? true: f.getName().toLowerCase().contains(q.toLowerCase())?true:
                        f.getPhone().toLowerCase().contains(q.toLowerCase())?true:f.getRegion().toLowerCase().contains(q.toLowerCase())?true:false).orElse(null);
            	}
                
                if(pupils != null){
                    list.add(pupils);
                }
            });
            
            
        }
        
       return list.stream().collect(Collectors.toList());
    }
    
    
    Pupils findByCourseRegAndFilter(CoursesRegisters coursesRegistersList, String q, List<FilterEnums> filters){
    	Pupils item = null;
    	
        if(coursesRegistersList != null){
            	Pupils pupils = null;
            	boolean isAdd = false;
            	if(filters != null && filters.contains(FilterEnums.NOTTEACHER)) {
            		if(this.coursesTeachersService.findCurrentTeacherByCourseRegId(coursesRegistersList.getId()) == null) {
            			isAdd = true;
            		}else {
            			isAdd = false;
            		}
            	}else {
            		isAdd = true;
            	}
            	if(isAdd) {
            		pupils = this.pupilsDao.findById(coursesRegistersList.getPupils_id()).filter(f-> q == null? true: (f.getName()
                        + f.getPhone() + f.getRegion()).toLowerCase().contains(q.toLowerCase()) == true).orElse(null);
            	}
                
                if(pupils != null){
                	item = pupils;
                }
            
            
        }
        
       return item;
    }

	@Override
	public PupilsModel addPupil(AddPupilsModel pupil) {
		// TODO Auto-generated method stub
		Pupils newpupil = new Pupils();
		newpupil.setName(pupil.getPupil().getName());
		newpupil.setPhone(pupil.getPupil().getPhone());
		newpupil.setWhatsapp(pupil.getPupil().getWhatsapp());
		newpupil.setRegion(pupil.getPupil().getRegion());
		newpupil.setGender(pupil.getPupil().getGender());
		newpupil = pupilsDao.save(newpupil);
		
		PupilsStatuses pupilsStatus = new PupilsStatuses();
		pupilsStatus.setName(CoursesRegistersStatusesEnum.PUPILLEARNING);
		pupilsStatus.setPupils_id(newpupil.getId());
		pupilsRegStatuses.add(pupilsStatus);
		
		CoursesRegisters coursesReg = new CoursesRegisters();
		coursesReg.setCourses_id(pupil.getCourse().getCourse_id());
		coursesReg.setPupils_id(newpupil.getId());
		coursesReg = coursesRegistersService.save(coursesReg);
		final Long course_reg_id = coursesReg.getId();
		
		CoursesTeachers teacher = new CoursesTeachers();
		teacher.setCourses_registers_id(course_reg_id);
		teacher.setTeachers_id(pupil.getCourse().getTeacher_id());
		teacher.setTeachersStatusesEnum(CoursesTeachersStatusesEnum.ADD);
		coursesTeachersService.save(teacher);

		CoursesRegStatuses statuses = new CoursesRegStatuses();
		statuses.setCourses_registers_id(coursesReg.getId());

		statuses.setTeachers_id(pupil.getCourse().getTeacher_id());

		
		if(coursesDao.findByNextId(pupil.getCourse().getCourse_id()) != null) {
			statuses.setName(CoursesRegistersStatusesEnum.valueOf(pupil.getCourse().getStatus()));
		}else {
			statuses.setName(CoursesRegistersStatusesEnum.LEARNING);
		}
		
		coursesRegistersService.saveStatus(statuses);

		List<TimetablesCourses> timetables = pupil.getCourse().getTimetables();
		if(pupil.getCourse().getTimetables() != null) {

				timetables = timetables.stream().map(map -> new TimetablesCourses(course_reg_id, map.getTimetable_id() , map.getHourse(), map.getMinute(), true)).collect(Collectors.toList());
				timetablesCoursesService.saveAll(timetables);
			
		}
		
		PupilsModel respData = new PupilsModel(newpupil.getId(), newpupil.getName(), newpupil.getPhone(), newpupil.getRegion(), newpupil.getGender(), newpupil.getAge(), newpupil.getLogin_id(),
				newpupil.getWhatsapp(), 0, timetables);

		return respData;
	}

	@Override
	public Pupils findById(Long id) {
		// TODO Auto-generated method stub
		return this.pupilsDao.findById(id).orElse(null);
	}

	@Override
	public void updatePupil(PupilsModel pupil, Long id) {
		// TODO Auto-generated method stub
		Pupils prevPupil = this.pupilsDao.findById(id).orElse(null);
		prevPupil.setName(pupil.getName());
		prevPupil.setPhone(pupil.getPhone());
		prevPupil.setWhatsapp(pupil.getWhatsapp());
		prevPupil.setRegion(pupil.getRegion());
		prevPupil.setGender(pupil.getGender());
		this.pupilsDao.save(prevPupil);
	}

	@Override
	public Long countAllPupilsSchool() {
		// TODO Auto-generated method stub
		return this.pupilsDao.count();
	}
}
