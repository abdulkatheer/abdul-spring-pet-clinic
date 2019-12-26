package org.abdul.petclinic.service.map;

import org.abdul.petclinic.model.Owner;
import org.abdul.petclinic.model.Pet;
import org.abdul.petclinic.service.OwnerService;
import org.abdul.petclinic.service.PetService;
import org.abdul.petclinic.service.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Profile({"default", "map"})
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {
    private final PetService petService;
    private final PetTypeService petTypeService;

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
    public List<Owner> findByLastName(String lastName) {
        return super.findAll().stream()
                .filter(owner -> owner.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Owner> findByLastNameLike(String lastNamePattern) {
        final String any = ".*";
        final String pattern = any + lastNamePattern + any;
        return super.findAll().stream()
                .filter(owner -> Pattern.matches(pattern, owner.getLastName()))
                .collect(Collectors.toList());
    }
}
