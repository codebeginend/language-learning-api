package com.languages.learningapi.dao;

import com.languages.learningapi.models.Pupils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPupilsDao extends JpaRepository<Pupils, Long>, CrudRepository<Pupils, Long> {
    List<Pupils> findAll();
}
