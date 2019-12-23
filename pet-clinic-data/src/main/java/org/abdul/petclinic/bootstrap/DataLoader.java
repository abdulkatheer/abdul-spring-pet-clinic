package org.abdul.petclinic.bootstrap;

import org.abdul.petclinic.model.*;
import org.abdul.petclinic.service.OwnerService;
import org.abdul.petclinic.service.PetTypeService;
import org.abdul.petclinic.service.SpecialityService;
import org.abdul.petclinic.service.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
    }

    @Override
    public void run(String... args) throws Exception {
        PetType dog = new PetType();
        dog.setName("Dog");

        PetType cat = new PetType();
        cat.setName("Cat");

        Owner owner1 = new Owner();
        owner1.setFirstName("Abdul Katheer");
        owner1.setLastName("M");
        owner1.setAddress("221B, Baker Street");
        owner1.setCity("London");
        owner1.setPincode("613891");
        owner1.setMobileNo("9998391993");

        Pet jimmy = new Pet();
        jimmy.setName("Jimmy");
        jimmy.setPetType(dog);
        jimmy.setBirthDate(LocalDate.of(2018, 12, 11));

        //Association
        jimmy.setOwner(owner1);
        owner1.getPets().add(jimmy);

        Owner owner2 = new Owner();
        owner2.setFirstName("Dhanalakshmi");
        owner2.setLastName("N");
        owner2.setAddress("126, Layman St");
        owner2.setCity("Silicon Valley");
        owner2.setPincode("927201");
        owner2.setMobileNo("7399279199");

        Pet pussy = new Pet();
        pussy.setName("Pussy");
        pussy.setPetType(cat);
        pussy.setBirthDate(LocalDate.of(2019, 1, 12));

        //Association
        pussy.setOwner(owner2);
        owner2.getPets().add(pussy);

        Owner owner3 = new Owner();
        owner3.setFirstName("Vinothkumar");
        owner3.setLastName("N");
        owner3.setAddress("321, Moogambigai Nagar");
        owner3.setCity("Dindigul");
        owner3.setPincode("624005");
        owner3.setMobileNo("9994103083");

        Pet rocky = new Pet();
        rocky.setName("Rocky");
        rocky.setBirthDate(LocalDate.of(2018, 11, 13));
        rocky.setPetType(dog);

        //Association
        rocky.setOwner(owner3);
        owner3.getPets().add(rocky);

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");

        Speciality dentistry = new Speciality();
        dentistry.setDescription("dentistry");

        Vet vet1 = new Vet();
        vet1.setFirstName("Hari");
        vet1.setLastName("K");
        vet1.getSpecialities().add(radiology);
        vet1.getSpecialities().add(surgery);

        Vet vet2 = new Vet();
        vet2.setFirstName("Nirmal");
        vet2.setLastName("G");
        vet2.getSpecialities().add(dentistry);

        ownerService.save(owner1);
        ownerService.save(owner2);
        ownerService.save(owner3);

        vetService.save(vet1);
        vetService.save(vet2);
    }
}
