package com.languages.learningapi.services;

import java.util.Date;
import java.util.List;

import com.languages.learningapi.dao.IPupilsRegistersStatusesDao;
import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;
import com.languages.learningapi.models.statuses.PupilsStatuses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PupilsRegistersStatusesServiceImpl implements IPupilsRegistersStatuses{

	@Autowired
    IPupilsRegistersStatusesDao pupilsRegistersStatusesDao;
	
	
	@Override
	public void add(PupilsStatuses status) {
		// TODO Auto-generated method stub
		pupilsRegistersStatusesDao.save(status);
	}


	@Override
	public PupilsStatuses findByCurrentStatus(Long id) {
		// TODO Auto-generated method stub
		return pupilsRegistersStatusesDao.findByCurrentStatus(id);
	}


	@Override
	public List<PupilsStatuses> findByBeforeIsNotStatuses(Date first, List<CoursesRegistersStatusesEnum> arrStatus) {
		// TODO Auto-generated method stub
		return this.pupilsRegistersStatusesDao.findAllFirstAndNotIntStatuses(first, arrStatus);
	}


	@Override
	public List<PupilsStatuses> findByBeforeAndAfterIsNotStatuses(Date first, Date after,
			List<CoursesRegistersStatusesEnum> arrStatus) {
		// TODO Auto-generated method stub
		return this.pupilsRegistersStatusesDao.findAllFirstAndAfterAndNotInStatuses(first, after, arrStatus);
	}


	@Override
	public List<PupilsStatuses> findByBeforeAndAfterIsStatuses(Date first, Date after,
			List<CoursesRegistersStatusesEnum> arrStatus) {
		// TODO Auto-generated method stub
		return this.pupilsRegistersStatusesDao.findAllFirstAndAfterAndInStatuses(first, after, arrStatus);
	}
}
