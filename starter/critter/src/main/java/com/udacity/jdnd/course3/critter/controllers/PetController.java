package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.controllers.dtos.PetDTO;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.CustomerServices;
import com.udacity.jdnd.course3.critter.services.PetServices;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetServices petServices;

    @Autowired
    CustomerServices customerServices;

    public Pet convertPetDTOToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setBirthday(petDTO.getBirthDate());
        pet.setCustomer(customerServices.getCustomer(petDTO.getOwnerId()));
        return pet;
    }

    public PetDTO convertEntityToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setBirthDate(pet.getBirthday());
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        Long newPetId = petServices.savePet(convertPetDTOToEntity(petDTO));
        Pet newPet = petServices.getPet(newPetId);
        return convertEntityToPetDTO(newPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petServices.getPet(petId);
        return convertEntityToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<PetDTO> returnPetDTOs = new ArrayList<PetDTO>();
        List<Pet> pets = petServices.getPets();
        for (Pet p : pets) {
            returnPetDTOs.add(convertEntityToPetDTO(p));
        }
        return returnPetDTOs;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> returnPetsDTOs = new ArrayList<PetDTO>();
        List<Pet> pets = petServices.getPetsByCustomer(customerServices.getCustomer(ownerId));
        for (Pet p : pets) {
            returnPetsDTOs.add(convertEntityToPetDTO(p));
        }
        return returnPetsDTOs;
    }
}
