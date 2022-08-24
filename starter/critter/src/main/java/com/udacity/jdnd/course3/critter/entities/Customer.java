package com.udacity.jdnd.course3.critter.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Nationalized;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Primary
@Entity
public class Customer {

    @Id
    @GeneratedValue
    Long id;

    // @Nationalized makes the String unicode (NVARCHAR).
    @Nationalized
    private String name;

    private String phone;

    private String notes;

    @OneToMany(mappedBy = "customer")
    @Fetch(value = FetchMode.SELECT)
     private List<Pet> pets = new ArrayList<Pet>();

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
