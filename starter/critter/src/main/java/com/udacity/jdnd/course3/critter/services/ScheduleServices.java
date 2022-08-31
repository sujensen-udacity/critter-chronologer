package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ScheduleServices {

    @Autowired
    ScheduleRepository scheduleRepo;

    @Autowired
    EmployeeRepository employeeRepo;

    @Autowired
    PetRepository petRepo;

    public Long saveSchedule(Schedule schedule) {
        Schedule newSchedule = scheduleRepo.save(schedule);
        return newSchedule.getId();
    }

    public Schedule getSchedule(Long id) {
        if (scheduleRepo.existsById(id)) {
            Schedule schedule = scheduleRepo.getOne(id);
            return schedule;
        } else {
            throw new RuntimeException("schedule by that id does not exist");
        }
    }

    public List<Schedule> getSchedules() {
        List<Schedule> schedules = scheduleRepo.findAll();
        return schedules;
    }

    public List<Schedule> getSchedulesByPet(Long id) {
        Optional<Pet> pet = petRepo.findById(id);
        if (pet.isPresent()) {
            List<Schedule> schedules = scheduleRepo.findByPetsContaining(pet.get());
            return schedules;
        } else {
            throw new RuntimeException("pet by that id does not exist");
        }
    }

    public List<Schedule> getSchedulesByEmployee(Long id) {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (employee.isPresent()) {
            List<Schedule> schedules = scheduleRepo.findByEmployeesContaining(employee.get());
            return schedules;
        } else {
            throw new RuntimeException("employee by that id does not exist");
        }
    }
}
