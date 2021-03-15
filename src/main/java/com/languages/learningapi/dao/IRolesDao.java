package com.languages.learningapi.dao;

import com.languages.learningapi.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolesDao extends JpaRepository<Roles, Long> {
    Roles findFirstByName(String name);
}
