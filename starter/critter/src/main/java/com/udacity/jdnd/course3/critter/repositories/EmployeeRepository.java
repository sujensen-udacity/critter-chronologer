package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.controllers.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Set<Employee> findDistinctByDaysContainingAndSkillsContaining(DayOfWeek day, EmployeeSkill skill);


}
