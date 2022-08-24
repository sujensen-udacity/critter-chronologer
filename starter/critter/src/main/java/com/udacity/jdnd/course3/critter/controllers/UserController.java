package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.controllers.dtos.CustomerDTO;
import com.udacity.jdnd.course3.critter.controllers.dtos.EmployeeDTO;
import com.udacity.jdnd.course3.critter.controllers.dtos.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.controllers.enums.EmployeeSkillEnum;
import com.udacity.jdnd.course3.critter.entities.*;
import com.udacity.jdnd.course3.critter.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    EmployeeServices employeeServices;

    @Autowired
    SkillServices skillServices;

    @Autowired
    DayServices dayServices;

    @Autowired
    PetServices petServices;

    @Autowired
    CustomerServices customerServices;

    private Employee convertEmployeeDTOToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        // name
        employee.setName(employeeDTO.getName());
        // skills
        Set<Skill> newSkills = convertSkillToEntity(employeeDTO.getSkills());
        employee.setSkills(newSkills);
        // days available
        Set<Day> days = convertDayOfWeekToEntity(employeeDTO.getDaysAvailable());
        employee.setDays(days);
        return employee;
    }

    private EmployeeDTO convertEntityToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        // id, name
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        // skills
        Set<EmployeeSkillEnum> skills = convertSkillsToEnum(employee.getSkills());
        employeeDTO.setSkills(skills);
        // days available
        Set<DayOfWeek> days = convertDaytoEnum(employee.getDays());
        employeeDTO.setDaysAvailable(days);
        return employeeDTO;
    }

    private Set<Skill> convertSkillToEntity(Set<EmployeeSkillEnum> skills) {
        Set<Skill> skillRet = new HashSet<Skill>();
        if (skills != null) {
            for (EmployeeSkillEnum en : skills) {
                Skill s = new Skill();
                s.setName(en);
                skillRet.add(s);
            }
        }
        return skillRet;
    }

    private Set<EmployeeSkillEnum> convertSkillsToEnum(Set<Skill> skills) {
        Set<EmployeeSkillEnum> skillEn = new HashSet<EmployeeSkillEnum>();
        if (skills != null) {
            for (Skill s : skills) {
                EmployeeSkillEnum en = s.getName();
                skillEn.add(en);
            }
        }
        return skillEn;
    }


    private Set<Day> convertDayOfWeekToEntity(Set<DayOfWeek> dayOfWeek) {
        Set<Day> days = new HashSet<Day>();
        if (dayOfWeek != null) {
            for (DayOfWeek dw : dayOfWeek) {
                Day newDay = dayServices.getDayByName(dw);
                days.add(newDay);
            }
        }
        return days;
    }

    private Set<DayOfWeek> convertDaytoEnum(Set<Day> days) {
        Set<DayOfWeek> dow = new HashSet<DayOfWeek>();
        if (days != null) {
            for (Day d : days) {
                dow.add(d.getDayofweek());
            }
        }
        return dow;
    }

    private Customer convertCustomerDTOToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        // name, phone, notes
        customer.setName(customerDTO.getName());
        customer.setPhone(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());
        // pets
        if (customerDTO.getPetIds() != null) {
            customer.setPets(petServices.convertPetIdsToPets(customerDTO.getPetIds()));
        }
        return customer;
    }

    private CustomerDTO convertEntityToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        // id, name, phone, notes
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhone());
        customerDTO.setNotes(customer.getNotes());
        // pets
        customerDTO.setPetIds(petServices.convertPetsToPetIds(petServices.getPetsByCustomer(customer)));
        return customerDTO;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Long newId = customerServices.saveCustomer(convertCustomerDTOToEntity(customerDTO));
        Customer newCustomer = customerServices.getCustomer(newId);
        return convertEntityToCustomerDTO(newCustomer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOs = new ArrayList<CustomerDTO>();
        List<Customer> customers = customerServices.getCustomers();
        for (Customer c : customers) {
            customerDTOs.add(convertEntityToCustomerDTO(c));
        }
        return customerDTOs;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petServices.getPet(petId);
        Long ownerId = pet.getCustomer().getId();
        Customer customer = customerServices.getCustomer(ownerId);
        return convertEntityToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Long newId = employeeServices.saveEmployee(convertEmployeeDTOToEntity(employeeDTO));
        employeeDTO.setId(newId);
        return employeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeServices.getEmployee(employeeId);
        return convertEntityToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employeeServices.getEmployee(employeeId);
        Set<Day> availability = convertDayOfWeekToEntity(daysAvailable);
        employeeServices.saveEmployeeAvailability(availability, employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

}
