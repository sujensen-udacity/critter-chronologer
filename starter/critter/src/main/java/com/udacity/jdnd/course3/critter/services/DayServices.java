package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Day;
import com.udacity.jdnd.course3.critter.repositories.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

@Service
public class DayServices {

    @Autowired
    DayRepository dayRepo;

    public Day getDayByName(DayOfWeek dw) {
        if (dayRepo.findByDayofweek(dw) != null) {
            return dayRepo.findByDayofweek(dw);
        } else {
            Day day = new Day();
            day.setDayofweek(dw);
            return dayRepo.save(day);
        }
    }

}
