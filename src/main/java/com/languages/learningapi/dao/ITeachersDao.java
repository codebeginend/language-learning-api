package com.languages.learningapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.languages.learningapi.models.Teachers;

@Repository
public interface ITeachersDao extends JpaRepository<Teachers, Long>, CrudRepository<Teachers, Long> {
    Teachers findByLogin_id(Long login_id);
}
