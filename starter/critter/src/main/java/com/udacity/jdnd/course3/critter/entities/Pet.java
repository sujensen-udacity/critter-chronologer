package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.controllers.enums.PetType;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Pet {

    @Id
    @GeneratedValue
    Long id;

    // @Nationalized makes the String unicode (NVARCHAR).
    @Nationalized
    private String name;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @Enumerated(EnumType.STRING)
    private PetType type;

    private LocalDate birthday;

    private String notes;

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
