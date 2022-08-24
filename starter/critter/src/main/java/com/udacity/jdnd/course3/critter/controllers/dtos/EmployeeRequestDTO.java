package com.udacity.jdnd.course3.critter.controllers.dtos;

import com.udacity.jdnd.course3.critter.controllers.enums.EmployeeSkillEnum;

import java.time.LocalDate;
import java.util.Set;

/**
 * Represents a request to find available employees by skills. Does not map
 * to the database directly.
 */
public class EmployeeRequestDTO {
    private Set<EmployeeSkillEnum> skills;
    private LocalDate date;

    public Set<EmployeeSkillEnum> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkillEnum> skills) {
        this.skills = skills;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
