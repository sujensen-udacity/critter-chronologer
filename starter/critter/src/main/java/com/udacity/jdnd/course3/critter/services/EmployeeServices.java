package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Day;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EmployeeServices {

    @Autowired
    EmployeeRepository employeeRepo;

    @Autowired
    SkillRepository skillRepo;

    public Long saveEmployee(Employee employee) {
        if (employeeRepo.findByName(employee.getName()) == null) {
            Employee newEmp = employeeRepo.save(employee);
            return newEmp.getId();
        } else {
            throw new RuntimeException("employee by that name already exists");
        }
    }

    public void saveEmployeeAvailability(Set<Day> days, Employee employee) {
        if (employeeRepo.existsById(employee.getId())) {
            employee.setDays(days);
            employeeRepo.save(employee);
        } else {
            throw new RuntimeException("employee does not exist");
        }
    }

    public Employee getEmployee(Long id) {
        if (employeeRepo.existsById(id)) {
            Employee emp = employeeRepo.getOne(id);
            return emp;
        } else {
            throw new RuntimeException("employee by that id does not exist");
        }
    }

}
