package org.abdul.petclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "owners")
public class Owner extends Person {

    @Builder
    public Owner(Long id, String firstName, String lastName,
                 String address, String city, String pincode,
                 String mobileNo, Set<Pet> pets) {
        super(id, firstName, lastName);
        this.address = address;
        this.city = city;
        this.pincode = pincode;
        this.mobileNo = mobileNo;
        this.pets = pets == null ? new HashSet<>() : pets;
    }

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "mobile_no")
    private String mobileNo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();

    public void addPet(Pet pet) {
        if (pet.isNew()) {
            addPetInternal(pet);
        }
        pet.setOwner(this);
    }

    public Pet getPet(String petName, boolean ignoreNewPet) {
        return this.pets.stream()
                .filter(pet -> !ignoreNewPet || !pet.isNew())
                .filter(pet -> pet.getName().equalsIgnoreCase(petName))
                .findFirst()
                .orElse(null);
    }

    private void addPetInternal(Pet pet) {
        if (pets == null) {
            pets = new HashSet<>();
        }
        pets.add(pet);
    }
}
