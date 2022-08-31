package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.controllers.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.*;

@Transactional
@Service
public class EmployeeServices {

    @Autowired
    EmployeeRepository employeeRepo;

    public List<Employee> convertEmployeeIdsToEmployees(List<Long> ids) {
        List<Employee> employees = new ArrayList<Employee>();
        if (ids != null) {
            employees = employeeRepo.findAllById(ids);
        }
        return employees;
    }

    public List<Long> convertEmployeesToEmployeeIds(List<Employee> employees) {
        List<Long> ids = new ArrayList<Long>();
        if (employees != null) {
            for (Employee e : employees) {
                ids.add(e.getId());
            }
        }
        return ids;
    }

    public Long saveEmployee(Employee employee) {
        Employee newEmp = employeeRepo.saveAndFlush(employee);
        return newEmp.getId();
    }

    public void saveEmployeeAvailability(Set<DayOfWeek> days, Employee employee) {
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

    public Set<Employee> getEmployeeByAvailabilitySkills(DayOfWeek day, Set<EmployeeSkill> skills) {
        Map<Employee, Integer> empCount = new HashMap<>();
        for (EmployeeSkill s : skills) {
            Set<Employee> sSet = employeeRepo.findDistinctByDaysContainingAndSkillsContaining(day, s);
            for (Employee e : sSet) {
                if (empCount.containsKey(e)) {
                    Integer eCount = empCount.get(e);
                    empCount.put(e, eCount + 1);
                } else {
                    empCount.put(e, 1);
                }
            }
        }
        Set<Employee> retEmp = new HashSet<>();
        Set<Employee> keys = empCount.keySet();
        for (Employee e : keys) {
            if (empCount.get(e) == skills.size()) {
                retEmp.add(e);
            }
        }
        return retEmp;
    }

}
