package org.abdul.petclinic.bootstrap;

import org.abdul.petclinic.model.*;
import org.abdul.petclinic.service.OwnerService;
import org.abdul.petclinic.service.PetTypeService;
import org.abdul.petclinic.service.SpecialityService;
import org.abdul.petclinic.service.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;

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
        PetType dog = PetType.builder()
                .name("Dog")
                .build();

        PetType cat = PetType.builder()
                .name("Cat")
                .build();

        Owner owner1 = Owner.builder()
                .firstName("Mohideen Abdul Katheer")
                .lastName("Mohamed Amsa")
                .address("221B, Baker Street")
                .city("London")
                .pincode("613891")
                .mobileNo("9998391993")
                .build();

        Pet jimmy = Pet.builder()
                .name("Jimmy")
                .petType(dog)
                .owner(owner1)
                .birthDate(LocalDate.of(2018, 12, 11))
                .build();

        //Association
        owner1.setPets(Collections.singleton(jimmy));

        Owner owner2 = Owner.builder()
                .firstName("Dhanalakshmi")
                .lastName("Narayanan")
                .address("126, Layman St")
                .city("Silicon Valley")
                .pincode("927201")
                .mobileNo("7399279199")
                .build();

        Pet pussy = Pet.builder()
                .name("Pussy")
                .petType(cat)
                .owner(owner2)
                .birthDate(LocalDate.of(2019, 1, 12))
                .build();

        //Association
        owner2.setPets(Collections.singleton(pussy));

        Owner owner3 = Owner.builder()
                .firstName("Vinothkumar")
                .lastName("Natarajan")
                .address("321, Moogambigai Nagar")
                .city("Dindigul")
                .pincode("624005")
                .mobileNo("9994103083")
                .build();

        Pet rocky = Pet.builder()
                .name("Rocky")
                .petType(dog)
                .owner(owner3)
                .birthDate(LocalDate.of(2018, 11, 13))
                .build();

        //Association
        owner3.setPets(Collections.singleton(rocky));

        Speciality radiology = Speciality.builder()
                .description("Radiology")
                .build();

        Speciality surgery = Speciality.builder()
                .description("Surgery")
                .build();

        Speciality dentistry = Speciality.builder()
                .description("Dentistry")
                .build();

        Vet vet1 = Vet.builder()
                .firstName("Hari")
                .lastName("Krishnan")
                .specialities(Collections.singleton(surgery))
                .build();

        Vet vet2 = Vet.builder()
                .firstName("Nirmal")
                .lastName("Raj")
                .specialities(Collections.singleton(dentistry))
                .build();

        petTypeService.save(dog);
        petTypeService.save(cat);

        ownerService.save(owner1);
        ownerService.save(owner2);
        ownerService.save(owner3);

        specialityService.save(radiology);
        specialityService.save(dentistry);
        specialityService.save(surgery);

        vetService.save(vet1);
        vetService.save(vet2);
    }
}
