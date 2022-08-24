package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CustomerServices {

    @Autowired
    CustomerRepository customerRepo;

    public Long saveCustomer(Customer customer) {
        if (customerRepo.findByName(customer.getName()) == null) {
            Customer newCust = customerRepo.save(customer);
            return newCust.getId();
        } else {
            throw new RuntimeException("customer by that name already exists");
        }
    }

    public Customer getCustomer(Long id) {
        if (customerRepo.existsById(id)) {
            Customer customer = customerRepo.getOne(id);
            return customer;
        } else {
            throw new RuntimeException("customer by that id does not exist");
        }
    }

    public List<Customer> getCustomers() {
        List<Customer> customers = customerRepo.findAll();
        return customers;
    }
}
