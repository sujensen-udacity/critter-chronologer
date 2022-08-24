package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.controllers.enums.EmployeeSkillEnum;
import com.udacity.jdnd.course3.critter.entities.Skill;
import com.udacity.jdnd.course3.critter.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SkillServices {

    @Autowired
    SkillRepository skillRepo;

    public Skill getSkillByName(EmployeeSkillEnum name) {
        if (skillRepo.findByName(name) != null) {
            return skillRepo.findByName(name);
        } else {
            Skill newSkill = new Skill();
            newSkill.setName(name);
            return skillRepo.save(newSkill);
        }
    }
}
