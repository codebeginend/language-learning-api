package com.languages.learningapi.services;

import com.languages.learningapi.dao.ITimetablesCoursesDao;
import com.languages.learningapi.dao.ITimetablesDao;
import com.languages.learningapi.models.timetables.AbstractRegTimetables;
import com.languages.learningapi.models.timetables.Timetables;
import com.languages.learningapi.models.timetables.TimetablesCourses;
import com.languages.learningapi.models.timetables.WeekDaysEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TimetablesServiceImpl implements ITimetablesService{
    @Autowired
    private ITimetablesDao timetablesDao;
    
    @Autowired
    ITimetablesCoursesDao timetablesCoursesDao;


    @Autowired
    private TimetablesCoursesServiceImpl timetablesCoursesService;

    @Override
    public Map<WeekDaysEnum, List<Timetables>> findAllByMap(Long course_reg_id) {
        List<Timetables> list = timetablesDao.findAll();
        Long parent_used_id = 0L;
        for (int x=0;x<list.size();x++){
            List<TimetablesCourses> timetablesCourses = timetablesCoursesService.findByTimetables_id(list.get(x).getId(), course_reg_id);
            if(timetablesCourses.size() != 0){
                parent_used_id = list.get(x).getParent_id();
                list.get(x).setUsed(true);
                list.get(x).setReg_timetables(timetablesCourses.stream().map(c-> (AbstractRegTimetables)c).collect(Collectors.toList()));
            }
        }
        
        
        List<Timetables> listParent = timetablesDao.findByParent_id(0L);

        if(parent_used_id != 0L){
            listParent.get(listParent.stream().map(c->c.getId()).collect(Collectors.toList()).indexOf(parent_used_id)).setUsed(true);
        }
        Map<WeekDaysEnum, List<Timetables>> result3 = listParent.stream().collect(
                Collectors.toMap(x -> x.getWeekDay(), x -> list.stream().filter(s-> s.getParent_id() == x.getId()).collect(Collectors.toList())));

        return result3;
    }
    
    
    public Map<WeekDaysEnum, TimetablesCourses> findMapByCourseReg(Long course_reg_id) {
        List<TimetablesCourses> list = timetablesCoursesDao.findByCoursesRegister_id(course_reg_id);
        Map<WeekDaysEnum, TimetablesCourses>  map = new HashMap<WeekDaysEnum, TimetablesCourses>();
        map = list.stream().filter(predicate-> predicate.isUsed()).collect(Collectors.toMap(ss-> ss.getTimetable().getWeekDay(), Function.identity()));
        return map;
    }

    @Override
    public List<Timetables> findAllById(Iterable<Long> id) {
        return this.timetablesDao.findAllById(id);
    }


	@Override
	public void updateTimetablesFromCoursesReg(Long course_reg_id,
			Map<WeekDaysEnum, TimetablesCourses> timetablsesReg) {
		List<TimetablesCourses> timetables = new ArrayList<TimetablesCourses>(timetablsesReg.values());
		for(int index =0;index < timetables.size(); index++) {
			timetables.get(index).setCoursesRegister_id(course_reg_id);
			
		}
		
		this.timetablesCoursesDao.saveAll(timetables);
		// TODO Auto-generated method stub
		
	}
    
}