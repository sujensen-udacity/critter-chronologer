package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.controllers.enums.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {

    @Id
    @GeneratedValue
    Long id;

    @ManyToMany
    @JoinTable(name="employee_schedule", joinColumns = {@JoinColumn(name="schedule_id")}, inverseJoinColumns = {@JoinColumn(name="employee_id")})
    private List<Employee> employees;

    @ManyToMany
    @JoinTable(name="pet_schedule", joinColumns = {@JoinColumn(name="schedule_id")}, inverseJoinColumns = {@JoinColumn(name="pet_id")})
    private List<Pet> pets;

    @Column
    private LocalDate date;

    @ElementCollection
    private Set<EmployeeSkill> skills;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }
}
