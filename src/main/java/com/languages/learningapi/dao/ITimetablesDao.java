package com.languages.learningapi.dao;

import com.languages.learningapi.models.timetables.Timetables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITimetablesDao extends JpaRepository<Timetables, Long> {

    @Query("select t from Timetables t where t.parent_id = :parent_id")
    List<Timetables> findByParent_id(Long parent_id);
}
