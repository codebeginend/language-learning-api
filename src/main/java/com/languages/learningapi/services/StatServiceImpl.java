package com.languages.learningapi.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.languages.learningapi.models.controller.stat.retention.RetentionStat;
import com.languages.learningapi.models.controller.stat.school.SchoolPupilsCountStatusPeriod;
import com.languages.learningapi.models.controller.stat.school.TotalSchoolStatModel;
import com.languages.learningapi.models.controller.stat.teachers.TeacherStatPeriod;
import com.languages.learningapi.models.controller.stat.teachers.TotalTeacherStatModel;
import com.languages.learningapi.models.courses.CoursesRegisters;
import com.languages.learningapi.models.courses.CoursesTeachers;
import com.languages.learningapi.models.courses.Lessons;
import com.languages.learningapi.models.courses.ReiterationEnums;
import com.languages.learningapi.models.statuses.CoursesRegStatuses;
import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;
import com.languages.learningapi.models.statuses.LessonsStatuses;
import com.languages.learningapi.models.statuses.PupilsStatuses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatServiceImpl implements IStatService {
	
	@Autowired
	private IPupilsService pupilsService;
	
	@Autowired
	private ITeachersService teachersService;
	
	@Autowired
	private ICoursesRegistersService coursesRegService;
	
	@Autowired
	private IPupilsRegistersStatuses pupilsRegStatuses;
	
	@Autowired
	private ILessonsService lessonsService;
	
	@Autowired
	private ICoursesTeachersService coursesTeachersService;
	
	@Autowired
	private ILessonsStatusesService lessonsStatusesService;

	@Override
	public TotalSchoolStatModel findTotalSchoolStat() {
		TotalSchoolStatModel stat = new TotalSchoolStatModel();
		stat.setCount_pupils(pupilsService.countAllPupilsSchool());
		stat.setCount_teachers(teachersService.countAllSchool());
		// TODO Auto-generated method stub
		return stat;
	}
	
	public SchoolPupilsCountStatusPeriod findDateSchoolStat(Date firstDate, Date lastDate){
		SchoolPupilsCountStatusPeriod statModel = new SchoolPupilsCountStatusPeriod();
		
		//ученики по дате (Закончили обучение, ушли, бросили, исчезли)
		List<CoursesRegStatuses> list = this.coursesRegService.findCoursesRegStatusesByPeriod(firstDate, lastDate);
		List<CoursesRegStatuses> listNew = list.stream().filter(fil-> list.stream().filter(fil2-> fil2.getCourses_registers_id() == fil.getCourses_registers_id())
				.max((a, b) -> a.getDate().compareTo(b.getDate())).get().getId() == fil.getId())
				.collect(Collectors.toList());
		
		Map<CoursesRegistersStatusesEnum, Long> mapStatusCountPupils = listNew.stream().collect(Collectors.groupingBy(CoursesRegStatuses::getName, Collectors.counting()));
		statModel.setMapStatusCountPupils(mapStatusCountPupils);
		
		//Оплаты учеников по дате
		List<Lessons> lessonsList = lessonsService.findByPeriodAndNotParentId(firstDate, lastDate);
		//Сколько оплат сделали ученики всего
		statModel.setTotalCountPays(lessonsList.size());
		
		//Сколько оплат сделали ученики каждый день
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Map<String, Long> paysOfDay = lessonsList.stream().collect(Collectors.groupingBy(ss-> dateFormat.format(ss.getDate()), Collectors.counting()));;
		statModel.setPaysOfDay(paysOfDay);
		
		//Сколько оплат C1, C2 ...
		Map<ReiterationEnums, Long> paysOfReiteration = lessonsList.stream().collect(Collectors.groupingBy(Lessons::getReiteration, Collectors.counting()));
		statModel.setPaysOfReiteration(paysOfReiteration);
		
		//Общая сумма денег
		statModel.setTotalPrice(lessonsList.stream().mapToLong(Lessons::getPrice).sum());
		
		return statModel;
	}
	
	public RetentionStat findRetentionStat(Date firstDate, Date lastDate){
		RetentionStat statModel = new RetentionStat();

		//Статусы
		List<CoursesRegistersStatusesEnum> arrStatus = new ArrayList();
		arrStatus.add(CoursesRegistersStatusesEnum.PUPILPOSTPONED);
		arrStatus.add(CoursesRegistersStatusesEnum.PUPILFINISHED);
		arrStatus.add(CoursesRegistersStatusesEnum.PUPILTHREWANDDISAPPEARED);
		

		//Сколько всего за начало период учеников
		List<PupilsStatuses> beforeArr = this.pupilsRegStatuses.findByBeforeIsNotStatuses(firstDate, arrStatus);
		statModel.setBeforePupilsCount(beforeArr.size());

		//Пришли за период
		List<PupilsStatuses> beforeAfterArr = this.pupilsRegStatuses.findByBeforeAndAfterIsNotStatuses(firstDate, lastDate, arrStatus);
		
		List<CoursesRegistersStatusesEnum> arrStatusOutflow = new ArrayList();
		arrStatusOutflow.add(CoursesRegistersStatusesEnum.PUPILPOSTPONED);
		arrStatusOutflow.add(CoursesRegistersStatusesEnum.PUPILFINISHED);
		arrStatusOutflow.add(CoursesRegistersStatusesEnum.PUPILTHREWANDDISAPPEARED);
		List<CoursesRegStatuses> outflowCount = this.coursesRegService.findByBeforeAndAfterIsStatuses(firstDate, lastDate, arrStatusOutflow);
		
		statModel.setAfterPupilsCount((beforeAfterArr.size() - outflowCount.size()) + beforeArr.size());
		
		if((statModel.getAfterPupilsCount() - beforeAfterArr.size()) != 0 && beforeArr.size() != 0) {
			statModel.setRetention((statModel.getAfterPupilsCount() - beforeAfterArr.size()) / beforeArr.size() * 100);
			//statModel.setRetention(statModel.getRetention() - (statModel.getRetention() * 2));

			statModel.setOutflow(outflowCount.size() * 100 / beforeArr.size());
		}

		
		statModel.setNewPupilsCount(beforeAfterArr.size());
		statModel.setOutflowPupilsCount(outflowCount.size());

		
		return statModel;
	}

	@Override
	public TotalTeacherStatModel findTotalTeacherStat(Long teacher_id) {
		TotalTeacherStatModel statData = new TotalTeacherStatModel();
		//Общее количество учеников
		List<CoursesTeachers> listCoursesTeachers = coursesTeachersService.findAllFromCurrentTeacher(teacher_id);
		List<CoursesRegisters> listCoursesReg = this.coursesRegService.findAllIdList(listCoursesTeachers.stream().map(mapper-> mapper.getCourses_registers_id()).collect(Collectors.toList()));
		List<CoursesRegStatuses> listCoursesRegStatuses = this.coursesRegService.findAllByCourseRegIdList(listCoursesReg.stream().map(mapper-> mapper.getId()).collect(Collectors.toList()));
		List<CoursesRegistersStatusesEnum> courseStat =  new ArrayList();
		courseStat.add(CoursesRegistersStatusesEnum.LEARNING);
		courseStat.add(CoursesRegistersStatusesEnum.AGREED);
		courseStat.add(CoursesRegistersStatusesEnum.POSTPONED);
		courseStat.add(CoursesRegistersStatusesEnum.EXPECTATION);
		Long total_count_pupils = listCoursesRegStatuses.stream().filter(fil -> courseStat.contains(listCoursesRegStatuses.stream().filter(fil2-> fil2.getCourses_registers_id() == fil.getCourses_registers_id()).max((a, b)-> a.getDate().compareTo(b.getDate())).get().getName())).distinct().count();
		statData.setTotal_count_pupils(total_count_pupils);
		Long total_count_gone_pupils = listCoursesRegStatuses.stream().filter(fil -> listCoursesRegStatuses.stream().filter(fil2-> fil2.getCourses_registers_id() == fil.getCourses_registers_id()).max((a, b)-> a.getDate().compareTo(b.getDate())).get().getName() == CoursesRegistersStatusesEnum.THREWANDDISAPPEARED).distinct().count();
		statData.setTotal_count_gone_pupils(total_count_gone_pupils);
		return statData;
	}
	
	public TeacherStatPeriod findPeriodStatOfTeacher(Long teacher_id, List<Long> course_ids, Date first, Date last) {
		TeacherStatPeriod statData = new TeacherStatPeriod();
		List<CoursesTeachers> listCoursesTeachers = coursesTeachersService.findAllFromCurrentTeacher(teacher_id);
		
			//Количество уроков пройденных учениками
			List<LessonsStatuses> listLessonsStatuses = lessonsStatusesService.findAllByTeacherIdByPeriod(teacher_id, CoursesRegistersStatusesEnum.FINISHEDLESSON, first, last);
			if(listLessonsStatuses != null && listLessonsStatuses.size() != 0) {
				statData.setCount_finished_lessons(listLessonsStatuses.stream().filter(fil-> fil.getName() == CoursesRegistersStatusesEnum.FINISHEDLESSON).count());
			}
			//ученики по дате (Закончили обучение, ушли, бросили, исчезли)
			List<CoursesRegStatuses> list = this.coursesRegService.findCoursesRegStatusesByTeacherIdByPeriod(teacher_id, first, last);
			List<CoursesRegStatuses> listNew = list.stream().filter(fil-> list.stream().filter(fil2-> fil2.getCourses_registers_id() == fil.getCourses_registers_id())
					.max((a, b) -> a.getDate().compareTo(b.getDate())).get().getId() == fil.getId())
					.collect(Collectors.toList());
			Map<CoursesRegistersStatusesEnum, Long> mapStatusCountPupils = listNew.stream().collect(Collectors.groupingBy(CoursesRegStatuses::getName, Collectors.counting()));
			statData.setMapStatusCountPupils(mapStatusCountPupils);
		
		
		return statData;
	}
	
}
