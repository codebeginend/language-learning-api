package com.languages.learningapi.dao;

import java.util.Date;
import java.util.List;

import com.languages.learningapi.models.statuses.CoursesRegistersStatusesEnum;
import com.languages.learningapi.models.statuses.PupilsStatuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPupilsRegistersStatusesDao extends JpaRepository<PupilsStatuses, Long>{

	@Query("select pupilStatus from PupilsStatuses pupilStatus where (select MAX(date) from PupilsStatuses pupilStatusChild where pupilStatusChild.pupils_id = :pupil_id) = pupilStatus.date")
	PupilsStatuses findByCurrentStatus(Long pupil_id);
	
    @Query("select cr from PupilsStatuses cr where (select MAX(id) from PupilsStatuses crs where crs.pupils_id = cr.pupils_id and crs.date <= :first) = cr.id and cr.name not in :arrStatus")
    List<PupilsStatuses> findAllFirstAndNotIntStatuses(Date first, List<CoursesRegistersStatusesEnum> arrStatus);
    
    @Query("select cr from PupilsStatuses cr where (select MAX(id) from PupilsStatuses crs where crs.pupils_id = cr.pupils_id and crs.date > :first and cr.date <= :after) = cr.id and cr.name not in :arrStatus")
    List<PupilsStatuses> findAllFirstAndAfterAndNotInStatuses(Date first, Date after, List<CoursesRegistersStatusesEnum> arrStatus);
    
    @Query("select cr from PupilsStatuses cr where (select MAX(date) from PupilsStatuses crs where crs.pupils_id = cr.pupils_id and crs.date > :first and cr.date <= :after) = cr.date and cr.name in :arrStatus")
    List<PupilsStatuses> findAllFirstAndAfterAndInStatuses(Date first, Date after, List<CoursesRegistersStatusesEnum> arrStatus);
    
}
