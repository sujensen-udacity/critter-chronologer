package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.entities.Day;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;

public interface DayRepository extends JpaRepository<Day, Long> {

    Day findByDayofweek(DayOfWeek name);
}
