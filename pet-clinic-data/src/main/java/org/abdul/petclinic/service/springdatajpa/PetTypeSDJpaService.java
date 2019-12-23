package org.abdul.petclinic.service.springdatajpa;

import org.abdul.petclinic.model.PetType;
import org.abdul.petclinic.repository.PetTypeRepository;
import org.abdul.petclinic.service.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Profile("springdatajpa")
public class PetTypeSDJpaService implements PetTypeService {
    private final PetTypeRepository petTypeRepository;

    public PetTypeSDJpaService(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    public Set<PetType> findAll() {
        return StreamSupport
                .stream(petTypeRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet());
    }

    @Override
    public PetType findById(Long id) {
        return petTypeRepository.findById(id).orElse(null);
    }

    @Override
    public PetType save(PetType petType) {
        return petTypeRepository.save(petType);
    }

    @Override
    public void delete(PetType petType) {
        petTypeRepository.delete(petType);
    }

    @Override
    public void deleteById(Long id) {
        petTypeRepository.deleteById(id);
    }
}
