package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.controllers.enums.EmployeeSkillEnum;

import javax.persistence.*;

@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Enumerated(EnumType.STRING)
    private EmployeeSkillEnum name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeSkillEnum getName() {
        return name;
    }

    public void setName(EmployeeSkillEnum name) {
        this.name = name;
    }
}
