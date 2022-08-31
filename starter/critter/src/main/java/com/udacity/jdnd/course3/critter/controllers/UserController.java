package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.controllers.dtos.CustomerDTO;
import com.udacity.jdnd.course3.critter.controllers.dtos.EmployeeDTO;
import com.udacity.jdnd.course3.critter.controllers.dtos.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.controllers.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
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
    PetServices petServices;

    @Autowired
    CustomerServices customerServices;

    private Employee convertEmployeeDTOToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        // name
        employee.setName(employeeDTO.getName());
        // skills
        employee.setSkills(employeeDTO.getSkills());
        // days available
        employee.setDays(employeeDTO.getDaysAvailable());
        return employee;
    }

    private EmployeeDTO convertEntityToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        // id, name
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        // skills
        employeeDTO.setSkills(employee.getSkills());
        // days available
        employeeDTO.setDaysAvailable(employee.getDays());
        return employeeDTO;
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
        employeeServices.saveEmployeeAvailability(daysAvailable, employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        LocalDate reqDate = employeeDTO.getDate();
        DayOfWeek reqDOW = reqDate.getDayOfWeek();
        Set<EmployeeSkill> reqSkills = employeeDTO.getSkills();
        Set<Employee> employees = employeeServices.getEmployeeByAvailabilitySkills(reqDOW, reqSkills);
        for (Employee e : employees) {
            employeeDTOs.add(convertEntityToEmployeeDTO(e));
        }
        return employeeDTOs;
    }

}
