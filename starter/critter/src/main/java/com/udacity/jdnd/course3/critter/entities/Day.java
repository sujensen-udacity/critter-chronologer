package com.udacity.jdnd.course3.critter.entities;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Enumerated(EnumType.STRING)
    DayOfWeek dayofweek;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(DayOfWeek dayofweek) {
        this.dayofweek = dayofweek;
    }
}
