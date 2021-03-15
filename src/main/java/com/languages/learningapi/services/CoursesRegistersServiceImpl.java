package com.languages.learningapi.services;

import com.languages.learningapi.dao.ICoursesDao;
import com.languages.learningapi.dao.ICoursesRegistersDao;
import com.languages.learningapi.dao.ICoursesRegistersStatusesDao;
import com.languages.learningapi.dao.ILearningStatusesDao;
import com.languages.learningapi.models.Teachers;
import com.languages.learningapi.models.controller.CoursesRegistersModel;
import com.languages.learningapi.models.courses.Courses;
import com.languages.learningapi.models.courses.CoursesRegisters;
import com.languages.learningapi.models.courses.CoursesTeachers;
import com.languages.learningapi.models.statuses.CoursesRegStatuses;
import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;
import com.languages.learningapi.models.statuses.CoursesTeachersStatusesEnum;
import com.languages.learningapi.models.statuses.LearningStatuses;
import com.languages.learningapi.models.timetables.TimetablesCourses;
import com.speakarabic.augustapi.models.courses.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoursesRegistersServiceImpl implements ICoursesRegistersService{

    @Autowired
    private ICoursesRegistersDao coursesRegistersDao;

    @Autowired
    private ICoursesTeachersService coursesTeachersService;

    @Autowired
    private ICoursesRegistersStatusesDao statusesDao;
    
    @Autowired
    private ILearningStatusesDao learningStatusesDao;
    
    @Autowired
    private ITeachersService teachersService;
    
    @Autowired
    private ITimetablesCoursesService timetablesCoursesService;
    
    @Autowired
    private ICoursesDao coursesDao;
    
    @Autowired
    private LessonsServiceImpl lessonsService;
    

    @Override
    public List<CoursesRegisters> findAll(Specification specification) {
        return this.coursesRegistersDao.findAll(specification);
    }


    
    
    @Override
    public List<CoursesRegisters> findAllFromCurrentTeacher(Long teacher_id, List<CoursesRegistersStatusesEnum> arrStatus, List<Long>  course_id) {

        List<CoursesRegisters> list = new ArrayList<>();
        List<CoursesTeachers> coursesTeachers = this.coursesTeachersService.findAllFromCurrentTeacher(teacher_id);
        coursesTeachers.stream().forEach(data->{
            CoursesRegisters coursesRegisters = this.coursesRegistersDao.findById(data.getCourses_registers_id())
                    .filter(f-> isCurrentStatusesContains(f.getId(), arrStatus) && (course_id != null? course_id.contains(f.getCourses_id()):true)).orElse(null);
            if(coursesRegisters != null){
                list.add(coursesRegisters);
            }
        });
        return list;
    }
    
    @Override
    public List<CoursesRegisters> findAllByArrIdByMaxStatuses(List<Long> arrRegId, List<CoursesRegistersStatusesEnum> arrStatus){
    	List<CoursesRegisters> list = this.coursesRegistersDao.findAllById(arrRegId);
    	list = list.stream().filter(predicate-> arrStatus.contains(this.statusesDao.findByCourseReg_id(predicate.getId()).stream().max((a1, a2)-> a1.getDate().compareTo(a2.getDate())).get().getName()) ).collect(Collectors.toList());
    	return list;
    }

    @Override
    public List<CoursesRegisters> findAll(List<CoursesRegistersStatusesEnum> arrStatus, List<Long> course_id) {
           return this.coursesRegistersDao.findAll().stream()
                    .filter(f-> isCurrentStatusesContains(f.getId(), arrStatus) && (course_id != null? course_id.contains(f.getCourses_id()):true)).collect(Collectors.toList());
    }
    
    @Override
    public List<CoursesRegistersModel> findByPupils_idToModel(Long pupil_id) {

		List<CoursesRegistersStatusesEnum> listStat = new ArrayList<>();
		listStat.add(CoursesRegistersStatusesEnum.EXPECTATION);
		listStat.add(CoursesRegistersStatusesEnum.REFUSED);
    	List<CoursesRegistersModel> list = this.coursesRegistersDao.findByPupils_id(pupil_id).stream().map(data -> new CoursesRegistersModel(data.getId(), data.getPupils_id(), data.getPupils(), data.getCourses_id(), data.getCourses())).collect(Collectors.toList());
    	for(int x = 0; x<list.size(); x++) {
    		List<CoursesRegStatuses> currentStatuses = statusesDao.findByCourseReg_id(list.get(x).getId());
    		List<LearningStatuses> statusList = learningStatusesDao.findByCourseRegisters_idAndName(list.get(x).getId(), CoursesRegistersStatusesEnum.LEARNING);
    		
    		if(!listStat.contains(currentStatuses.stream().max((a, b)-> a.getDate().compareTo(b.getDate())).orElse(null).getName())) {
    			if(statusList.size() != 0) {
    				list.get(x).setCreateDate(statusList.stream().min((a, b)-> a.getDate().compareTo(b.getDate())).get().getDate().getTime());
    			}
				

				CoursesTeachers coursesTeacher = this.coursesTeachersService.findCurrentTeacherByCourseRegId(list.get(x).getId());
			if(coursesTeacher != null) {
				Teachers teacher =teachersService.findById(coursesTeacher.getTeachers_id());
	       		 list.get(x).setTeacher(teacher);
	       		 list.get(x).setTeacherId(teacher.getId());
			}
       		 list.get(x).setTimetables(timetablesCoursesService.findByCoursesRegister_id(list.get(x).getId()));
    		 list.get(x).setLessonsDef(lessonsService.findLessonsDefByCourseReg(list.get(x).getId()));
    		 list.get(x).setLessons(lessonsService.findAllByCourseReg(list.get(x).getId()).stream().sorted((a1, a2)-> a1.getId().compareTo(a2.getId())).collect(Collectors.toList()));
    		}
    		list.get(x).setCurrentStatus(currentStatuses.stream().max((a, b)-> a.getDate().compareTo(b.getDate())).orElse(null));

    		 list.get(x).setStatusName(list.get(x).getCurrentStatus().getName());

    	}
         return list;
    }

    @Override
    public List<CoursesRegisters> findByPupils_id(Long pupil_id) {
        return this.coursesRegistersDao.findByPupils_id(pupil_id);
    }

    @Override
    public CoursesRegisters findByCurrentStatusAndPupil_id(List<CoursesRegistersStatusesEnum> statusesEnum, Long pupil_id) {
        List<CoursesRegisters> list = this.findByPupils_id(pupil_id);
        for (int x=0;x< list.size(); x++){
            list.get(x).setStatuses(this.statusesDao.findByCourseRegisters_id(list.get(x).getId()));
        }
        CoursesRegisters item = list.stream().filter(f-> !statusesEnum.contains(f.getStatuses().stream().max((a, b)-> a.getDate().compareTo(b.getDate())).get().getName())).findFirst().orElse(null);

        return item;
    }

    @Override
    public List<CoursesRegStatuses> findByCourses_registers_id_id(Long course_reg_id) {
        return this.statusesDao.findByCourseRegisters_id(course_reg_id);
    }

    @Override
    public List<CoursesRegisters> findBy_id(Iterable<Long> id) {
        return this.coursesRegistersDao.findAllById(id);
    }

    public boolean isCurrentStatusesContains(Long course_id, List<CoursesRegistersStatusesEnum> statusesEnumList){
        if(statusesEnumList != null){
            if(statusesEnumList.contains(this.statusesDao.findByCourseRegisters_id(course_id).stream().filter(f-> CoursesRegistersStatusesEnum.getCourseStatuses().contains(f.getName())).max((a1, a2)-> a1.getDate().compareTo(a2.getDate())).get().getName())){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    }


	@Override
	public CoursesRegisters save(CoursesRegisters coursesReg) {
		// TODO Auto-generated method stub
		return this.coursesRegistersDao.save(coursesReg);
	}


	@Override
	public CoursesRegStatuses saveStatus(CoursesRegStatuses coursesRegStatus) {
		// TODO Auto-generated method stub
		return statusesDao.save(coursesRegStatus);
	}


	@Override
	public void updateModel(CoursesRegistersModel model, Long courseRegId) {
		// TODO Auto-generated method stub
		CoursesRegisters courseReg = this.coursesRegistersDao.findById(courseRegId).orElse(null);
		CoursesTeachers teacher = this.coursesTeachersService.findCurrentTeacherByCourseRegId(courseRegId);
		
		if(model.getTeacherId() != null) {
			List<CoursesTeachers> teachers = new ArrayList<CoursesTeachers>();
			CoursesTeachers coursesTeacher = this.coursesTeachersService.findCurrentTeacherByCourseRegId(courseRegId);
			if(coursesTeacher == null) {
				teachers.add(new CoursesTeachers(model.getTeacherId(), courseRegId, CoursesTeachersStatusesEnum.ADD));
			}else if(coursesTeacher.getTeachers_id() != model.getTeacherId()){
				teachers.add(new CoursesTeachers(model.getTeacherId(), courseRegId, CoursesTeachersStatusesEnum.ADD));
				teachers.add(new CoursesTeachers(coursesTeacher.getTeachers_id(), coursesTeacher.getCourses_registers_id(), CoursesTeachersStatusesEnum.DELETE));
				
				//Создаем новый статус LEARNING для нового учителя что бы знать что у него новый ученик в этот момент
				if(model.getStatusName() == null && model.getStatusName() != CoursesRegistersStatusesEnum.LEARNING) {
					CoursesRegStatuses newTeacherStatus = new CoursesRegStatuses();
					newTeacherStatus.setName(CoursesRegistersStatusesEnum.LEARNING);
					newTeacherStatus.setTeachers_id(model.getTeacherId());
					newTeacherStatus.setCourses_registers_id(courseRegId);
					this.statusesDao.save(newTeacherStatus);
				}
			}
			this.coursesTeachersService.saveAll(teachers);
		}
		
		if(model.getStatusName() != null) {
			
			
			CoursesRegStatuses status = new CoursesRegStatuses();
			status.setName(model.getStatusName());
			status.setTeachers_id(model.getTeacherId());
			status.setCourses_registers_id(courseRegId);
			
			this.statusesDao.save(status);
			
			//Добавить следующий курс в ожидание
			if(model.getStatusName() == CoursesRegistersStatusesEnum.FINISHED) {
				Long nextCourseId = getNextCourseId(courseReg.getCourses_id());
				if(nextCourseId != null) {
					if(this.findByCourseIdAndPupilId(nextCourseId, courseReg.getPupils_id()) == null) {
						CoursesRegisters nextCourseReg = new CoursesRegisters();
						nextCourseReg.setCourses_id(nextCourseId);
						nextCourseReg.setPupils_id(courseReg.getPupils_id());
						this.coursesRegistersDao.save(nextCourseReg);
						
						CoursesRegStatuses statusNextCourse = new CoursesRegStatuses();
						statusNextCourse.setName(CoursesRegistersStatusesEnum.EXPECTATION);
						statusNextCourse.setCourses_registers_id(nextCourseReg.getId());
						statusNextCourse.setTeachers_id(model.getTeacherId());
						this.statusesDao.save(statusNextCourse);
					}
					
				}else{
					CoursesRegStatuses currentStatus = new CoursesRegStatuses();
					status.setName(CoursesRegistersStatusesEnum.PUPILFINISHED);
					this.statusesDao.save(status);
				}
			}

		}
		
		if(model.getStatusAdditional() != null) {
			CoursesRegStatuses statusAdditional = new CoursesRegStatuses();
			statusAdditional.setName(model.getStatusAdditional());
			statusAdditional.setCourses_registers_id(courseRegId);
			this.statusesDao.save(statusAdditional);
			if(model.getStatusAdditional() == CoursesRegistersStatusesEnum.AGREED && model.getStatusName() == null) {
				CoursesRegStatuses status = new CoursesRegStatuses();
				status.setName(CoursesRegistersStatusesEnum.LEARNING);
				status.setCourses_registers_id(courseRegId);
				this.statusesDao.save(status);
			}else{
				CoursesRegStatuses status = new CoursesRegStatuses();
				status.setName(CoursesRegistersStatusesEnum.PUPILFINISHED);
				this.statusesDao.save(status);
			}
		}
		

	}
	
	
	public Long getNextCourseId(Long course_id) {
		Courses course = this.coursesDao.findById(course_id).orElse(null);
		if(course != null) {
			if(course.getNext_id() != null) {
				return this.coursesDao.findById(course.getNext_id()).orElse(null).getId();
			}
		}
		
		return null;
		
	}


	@Override
	public CoursesRegisters findByCourseIdAndPupilId(Long course_id, Long pupil_id) {
		// TODO Auto-generated method stub
		return this.coursesRegistersDao.findByCourseIdAndPupilId(course_id, pupil_id);
	}


	@Override
	public List<CoursesRegistersModel> findByCurrentStatusAndPupil_idAndTeacherId(
			List<CoursesRegistersStatusesEnum> statusesEnum, Long pupil_id, Long teacher_id) {
        List<CoursesRegisters> list = this.findByPupils_id(pupil_id);
        List<CoursesRegistersModel> coursesRegistersModel = list.stream().map(mapper-> new CoursesRegistersModel(mapper.getId(), mapper.getPupils_id(), mapper.getPupils(), mapper.getCourses_id(), mapper.getCourses())).collect(Collectors.toList());
        List<CoursesRegistersModel> result = new ArrayList();
        for (int x=0;x< coursesRegistersModel.size(); x++){
        	CoursesTeachers coursesTeacher = this.coursesTeachersService.findCurrentTeacherByCourseRegId(coursesRegistersModel.get(x).getId());
        	if(coursesTeacher != null && coursesTeacher.getTeachers_id() == teacher_id) {
        		List<CoursesRegStatuses> statuses = this.statusesDao.findByCourseRegisters_id(coursesRegistersModel.get(x).getId());
        		if(statuses.size() != 0) {
        			statuses = statuses.stream().sorted(Comparator.comparingLong(LearningStatuses::getId)).collect(Collectors.toList());
        			coursesRegistersModel.get(x).setStatuses(statuses);
        		}
        		
        		result.add(coursesRegistersModel.get(x));
        	}
        }
        if(statusesEnum != null) {
        	result = result.stream().filter(f-> !statusesEnum.contains(f.getStatuses().stream().max((a, b)-> a.getDate().compareTo(b.getDate())).get().getName())).collect(Collectors.toList());
        }
        
        
		for(int index =0;index < result.size(); index++) {
			List<TimetablesCourses> listTimetables = this.timetablesCoursesService.findByCoursesRegister_idAndUsed(result.get(index).getId(), true);
			if(listTimetables.size() != 0) {
				result.get(index).setTimetables(listTimetables); 
			}
		}
        
        return result;
	}




	@Override
	public List<CoursesRegisters> findBy_idAndIsNotStatuses(Iterable<Long> ids,
			List<CoursesRegistersStatusesEnum> status) {
		List<CoursesRegisters> list = new ArrayList();
		ids.forEach(id-> {
			if(!isCurrentStatusesContains(id, status)) {
				CoursesRegisters course = this.coursesRegistersDao.findById(id).orElse(null);
				if(course != null) {

					list.add(course);
				}
			}
		});
		return list;
	}




	@Override
	public List<CoursesRegisters> findAllIdList(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return this.coursesRegistersDao.findAllById(ids);
	}
	
	


	@Override
	public List<CoursesRegStatuses> findCoursesRegStatusesByPeriod(Date first, Date last) {
		return this.statusesDao.findByPeriod(first, last);
	}

	@Override
	public List<CoursesRegStatuses> findAllByIdList(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return this.statusesDao.findAllById(ids);
	}

	@Override
	public List<CoursesRegStatuses> findAllByCourseRegIdList(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return this.statusesDao.findAllByCoursesRegistersId(ids);
	}




	@Override
	public List<CoursesRegStatuses> findCoursesRegStatusesByListIdsByPeriod(Iterable<Long> ids, Date first, Date last) {
		// TODO Auto-generated method stub
		return this.statusesDao.findListCourseRegIdByPeriod(ids, first, last);
	}




	@Override
	public List<CoursesRegStatuses> findCoursesRegStatusesByTeacherIdByPeriod(Long teacher_id, Date first, Date last) {
		// TODO Auto-generated method stub
		return this.statusesDao.findTeacherIdByPeriod(teacher_id, first, last);
	}




	@Override
	public CoursesRegStatuses findByCurrentStatusFromCourseRegId(Long id) {
		// TODO Auto-generated method stub
		return this.statusesDao.findByCourseReg_id(id).stream().max((a, b)-> a.getDate().compareTo(b.getDate())).get();
	}




	@Override
	public List<CoursesRegStatuses> findByBeforeIsNotStatuses(Date first,
			List<CoursesRegistersStatusesEnum> arrStatus) {
		// TODO Auto-generated method stub
		return this.statusesDao.findAllFirstAndNotIntStatuses(first, arrStatus);
	}




	@Override
	public List<CoursesRegStatuses> findByBeforeAndAfterIsNotStatuses(Date first, Date after,
			List<CoursesRegistersStatusesEnum> arrStatus) {
		// TODO Auto-generated method stub
		return this.statusesDao.findAllFirstAndAfterAndNotIntStatuses(first, after, arrStatus);
	}




	@Override
	public List<CoursesRegStatuses> findByBeforeAndAfterIsStatuses(Date first, Date after,
			List<CoursesRegistersStatusesEnum> arrStatus) {
		// TODO Auto-generated method stub
		return this.statusesDao.findAllFirstAndAfterAndInStatuses(first, after, arrStatus);
	}
}
