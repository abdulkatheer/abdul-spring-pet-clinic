package org.abdul.petclinic.service.map;

import org.abdul.petclinic.model.Owner;
import org.abdul.petclinic.model.Pet;
import org.abdul.petclinic.model.PetType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    public static final String OWNER1_FIRST_NAME = "owner1FN";
    public static final String OWNER2_FIRST_NAME = "owner2FN";
    public static final String OWNER1_LAST_NAME = "owner1LN";
    public static final String OWNER2_LAST_NAME = "owner2LN";

    private OwnerMapService ownerMapService;

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetMapService(), new PetTypeMapService());
    }

    @Test
    public void shouldReturnAllOwnersWhenNotEmpty() {
        //given
        ownerMapService.save(Owner.builder().firstName(OWNER1_FIRST_NAME).build());
        ownerMapService.save(Owner.builder().firstName(OWNER2_FIRST_NAME).build());
        //when
        Set<Owner> actual = ownerMapService.findAll();
        //then
        Assertions.assertEquals(2, actual.size());
    }

    @Test
    public void shouldReturnEmptySetWhenEmpty() {
        //when
        Set<Owner> actual = ownerMapService.findAll();
        //then
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    public void shouldDeleteOwnerWhenIdIsGiven() {
        //given
        ownerMapService.save(Owner.builder().id(1L).firstName(OWNER1_FIRST_NAME).build());
        //when
        ownerMapService.deleteById(1L);
        //then
        assertNull(ownerMapService.findById(1L));
    }

    @Test
    public void shouldDeleteOwnerWhenOwnerIsGiven() {
        //given
        Owner owner = Owner.builder().id(1L).firstName(OWNER1_FIRST_NAME).build();
        ownerMapService.save(owner);
        //when
        ownerMapService.delete(owner);
        //then
        assertNull(ownerMapService.findById(1L));
    }

    @Test
    public void shouldReturnNpeWhenOwnerIsNull() {
        assertThrows(NullPointerException.class, () -> ownerMapService.save(null));
    }

    @Test
    public void shouldThrowExceptionWhenPetExistsAndPetTypeNotExists() {
        //given
        Owner owner = Owner.builder()
                .pets(Collections.singleton(Pet.builder().build()))
                .build();
        //then
        assertThrows(RuntimeException.class, () -> ownerMapService.save(owner));
    }

    @Test
    public void shouldGenerateIdAndSaveIfIdNotExists() {
        //given
        Owner owner = Owner.builder().firstName(OWNER1_FIRST_NAME).build();
        //when
        ownerMapService.save(owner);
        //then
        assertNotNull(((Owner) ownerMapService.findAll().toArray()[0]).getId());
    }

    @Test
    public void shouldNotGenerateIdAndSaveIfIdExists() {
        //given
        Owner owner = Owner.builder().id(5L).firstName(OWNER1_FIRST_NAME).build();
        //when
        ownerMapService.save(owner);
        //then
        assertEquals(5L, ((Owner) ownerMapService.findAll().toArray()[0]).getId());
    }

    @Test
    public void shouldSavePetsAndPetTypesIfNotAlreadyExists() {
        //given
        Owner owner = Owner.builder()
                .id(1L)
                .pets(Collections.singleton(
                        Pet.builder()
                                .name("Pet Name")
                                .petType(PetType.builder().name("Pet Type").build())
                                .build()))
                .build();
        //when
        ownerMapService.save(owner);
        //then
        assertNotNull(ownerMapService.findById(1L));
        assertEquals(1, ownerMapService.findById(1L).getPets().size());
    }

    @Test
    public void shouldReturnOwnerIfExists() {
        //given
        Owner owner = Owner.builder().id(1L).firstName(OWNER1_FIRST_NAME).build();
        ownerMapService.save(owner);
        //then
        assertEquals(owner, ownerMapService.findById(1L));
    }

    @Test
    public void shoudlReturnNullIfOwnerNotExists() {
        //then
        assertNull(ownerMapService.findById(1L));
    }

    @Test
    public void shouldReturnOwnerWhenLastNameGivenIsExist() {
        //when
        Owner owner1 = Owner.builder().firstName(OWNER1_FIRST_NAME).lastName(OWNER1_LAST_NAME).build();
        Owner owner2 = Owner.builder().firstName(OWNER2_FIRST_NAME).lastName(OWNER2_LAST_NAME).build();
        ownerMapService.save(owner1);
        ownerMapService.save(owner2);
        //then
        assertEquals(owner1, ownerMapService.findByLastName(OWNER1_LAST_NAME).get(0));
        assertEquals(owner2, ownerMapService.findByLastName(OWNER2_LAST_NAME).get(0));
    }

    @Test
    public void shouldReturnEmptyListWhenLastNameGivenIsNotExist() {
        assertEquals(0, ownerMapService.findByLastName(OWNER1_LAST_NAME).size());
    }
}