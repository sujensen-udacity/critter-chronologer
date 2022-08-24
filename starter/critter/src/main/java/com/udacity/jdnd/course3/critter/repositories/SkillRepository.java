package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.controllers.enums.EmployeeSkillEnum;
import com.udacity.jdnd.course3.critter.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    Skill findByName(EmployeeSkillEnum name);
}
