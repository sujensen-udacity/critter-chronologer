package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class PetServices {

    @Autowired
    PetRepository petRepo;

    @Autowired
    CustomerRepository customerRepo;

    public List<Pet> convertPetIdsToPets(List<Long> ids) {
        List<Pet> pets = new ArrayList<Pet>();
        if (ids != null) {
            pets = petRepo.findAllById(ids);
        }
        return pets;
    }

    public List<Long> convertPetsToPetIds(List<Pet> pets) {
        List<Long> ids = new ArrayList<Long>();
        if (pets != null) {
            for (Pet p : pets) {
                ids.add(p.getId());
            }
        }
        return ids;
    }

    public Long savePet(Pet pet) {
        // Make sure the customer exists (foreign key constraint)
        if (customerRepo.existsById(pet.getCustomer().getId())) {
            Pet savedPet = petRepo.saveAndFlush(pet);
            return savedPet.getId();
        } else {
            throw new RuntimeException("customer not found by that id; pet not saved");
        }
    }

    public Pet getPet(Long id) {
        if (petRepo.existsById(id)) {
            Pet pet = petRepo.getOne(id);
            return pet;
        } else {
            throw new RuntimeException("pet by that id does not exist");
        }
    }

    public List<Pet> getPets() {
        List<Pet> pets = petRepo.findAll();
        return pets;
    }

    public List<Pet> getPetsByCustomer(Customer customer) {
        List<Pet> pets = petRepo.findByCustomer(customer);
        return pets;
    }
}
