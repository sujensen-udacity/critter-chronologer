package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.controllers.dtos.ScheduleDTO;
import com.udacity.jdnd.course3.critter.controllers.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.services.CustomerServices;
import com.udacity.jdnd.course3.critter.services.EmployeeServices;
import com.udacity.jdnd.course3.critter.services.PetServices;
import com.udacity.jdnd.course3.critter.services.ScheduleServices;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    EmployeeServices employeeServices;

    @Autowired
    ScheduleServices scheduleServices;

    @Autowired
    CustomerServices customerServices;

    @Autowired
    PetServices petServices;

    private Schedule convertScheduleDTOToEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        schedule.setEmployees(employeeServices.convertEmployeeIdsToEmployees(scheduleDTO.getEmployeeIds()));
        schedule.setPets(petServices.convertPetIdsToPets(scheduleDTO.getPetIds()));
        schedule.setSkills(scheduleDTO.getActivities());
        return schedule;
    }

    private ScheduleDTO convertEntityToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        scheduleDTO.setEmployeeIds(employeeServices.convertEmployeesToEmployeeIds(schedule.getEmployees()));
        scheduleDTO.setPetIds(petServices.convertPetsToPetIds(schedule.getPets()));
        Set<EmployeeSkill> skills = schedule.getSkills();
        scheduleDTO.setActivities(skills);
        return scheduleDTO;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Long newId = scheduleServices.saveSchedule(convertScheduleDTOToEntity(scheduleDTO));
        Schedule newSchedule = scheduleServices.getSchedule(newId);
        return convertEntityToScheduleDTO(newSchedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOs = new ArrayList<ScheduleDTO>();
        List<Schedule> schedules = scheduleServices.getSchedules();
        for (Schedule s : schedules) {
            scheduleDTOs.add(convertEntityToScheduleDTO(s));
        }
        return scheduleDTOs;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();
        List<Schedule> schedules = scheduleServices.getSchedulesByPet(petId);
        for (Schedule s : schedules) {
            schedulesDTO.add(convertEntityToScheduleDTO(s));
        }
        return schedulesDTO;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();
        List<Schedule> schedules = scheduleServices.getSchedulesByEmployee(employeeId);
        for (Schedule s : schedules) {
            schedulesDTO.add(convertEntityToScheduleDTO(s));
        }
        return schedulesDTO;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> schedulesDTO = new ArrayList<>();
        List<Pet> customerPets = petServices.getPetsByCustomer(customerServices.getCustomer(customerId));
        for (Pet p : customerPets) {
            List<Schedule> schedules = scheduleServices.getSchedulesByPet(p.getId());
            for (Schedule s : schedules) {
                schedulesDTO.add(convertEntityToScheduleDTO(s));
            }
        }
        return schedulesDTO;
    }
}
