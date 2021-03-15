package com.languages.learningapi.controllers.specifications;

import com.languages.learningapi.models.courses.CoursesRegisters;
import com.languages.learningapi.models.courses.CoursesTeachers;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class CoursesRegistersSpecification implements Specification<CoursesRegisters> {
    private SearchCriteria criteria;

    public CoursesRegistersSpecification(final SearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    public SearchCriteria getCriteria() {
        return criteria;
    }

    @Override
    public Predicate toPredicate(Root<CoursesRegisters> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if(criteria.getOperation().equalsIgnoreCase(":")){
            if(criteria.getKey().equals("teacherId")) {
                Join<CoursesRegisters, CoursesTeachers> pupilsJoin = root.join("coursesTeachers");
                return builder.equal(pupilsJoin.<Long> get("teachers_id"), criteria.getValue());
            }
        }
        return null;
    }
}
