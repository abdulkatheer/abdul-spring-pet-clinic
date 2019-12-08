package org.abdul.petclinic.service;

import org.abdul.petclinic.model.Pet;

import java.util.Set;

public interface PetService {
    Pet findById(Long id);

    void save(Pet pet);

    Set<Pet> findAll();
}
