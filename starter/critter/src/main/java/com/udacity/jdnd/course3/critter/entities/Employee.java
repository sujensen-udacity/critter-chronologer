package com.udacity.jdnd.course3.critter.entities;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    // @Nationalized makes the String unicode (NVARCHAR).
    @Nationalized
    private String name;

    // When an employee is saved, also persist their skills
    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Skill> skills;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Day> days;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skill) {
        this.skills = skill;
    }

    public Set<Day> getDays() {
        return days;
    }

    public void setDays(Set<Day> days) {
        this.days = days;
    }
}
