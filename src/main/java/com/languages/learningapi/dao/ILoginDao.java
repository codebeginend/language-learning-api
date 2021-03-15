package com.languages.learningapi.dao;

import com.languages.learningapi.models.Login;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILoginDao extends JpaRepository<Login, Long>, CrudRepository<Login, Long> {

    @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.LOAD)
    Login findWithRoleByUsername(String username);
}
