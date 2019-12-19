package org.abdul.petclinic.bootstrap;

import org.abdul.petclinic.model.Owner;
import org.abdul.petclinic.model.PetType;
import org.abdul.petclinic.model.Vet;
import org.abdul.petclinic.service.OwnerService;
import org.abdul.petclinic.service.PetTypeService;
import org.abdul.petclinic.service.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private OwnerService ownerService;
    private VetService vetService;
    private PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Owner owner1 = new Owner();
        owner1.setFirstName("Abdul Katheer");
        owner1.setLastName("M");

        Owner owner2 = new Owner();
        owner2.setFirstName("Dhanalakshmi");
        owner2.setLastName("N");

        Owner owner3 = new Owner();
        owner3.setFirstName("Vinothkumar");
        owner3.setLastName("N");

        Vet vet1 = new Vet();
        vet1.setFirstName("Hari");
        vet1.setLastName("K");

        Vet vet2 = new Vet();
        vet2.setFirstName("Nirmal");
        vet2.setLastName("G");

        ownerService.save(owner1);
        ownerService.save(owner2);
        ownerService.save(owner3);

        vetService.save(vet1);
        vetService.save(vet2);
    }
}
