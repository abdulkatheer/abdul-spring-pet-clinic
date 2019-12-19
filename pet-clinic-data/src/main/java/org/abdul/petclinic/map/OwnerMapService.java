package org.abdul.petclinic.map;

import org.abdul.petclinic.model.Owner;
import org.abdul.petclinic.model.Pet;
import org.abdul.petclinic.service.OwnerService;
import org.abdul.petclinic.service.PetService;
import org.abdul.petclinic.service.PetTypeService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {
    private PetService petService;
    private PetTypeService petTypeService;

    public OwnerMapService(PetService petService, PetTypeService petTypeService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Owner owner) {
        super.delete(owner);
    }

    @Override
    public Owner save(Owner owner) {
        if (owner == null) {
            throw new NullPointerException("Object cannot be null");
        } else {
            //if has pets
            if (owner.getPets().size() > 0) {
                owner.getPets().forEach(pet -> {
                    if (pet.getPetType() == null) {
                        throw new RuntimeException("PetType is required");
                    } else {
                        //if has not id, then save and generate ID
                        if (pet.getPetType().getId() == null) {
                            pet.setPetType(petTypeService.save(pet.getPetType()));
                        }
                    }

                    //if pet has no id, save and generate ID
                    Pet savedPet = petService.save(pet);
                    pet.setId(savedPet.getId());
                });
            }
        }
        return super.save(owner);
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return null;
    }
}
