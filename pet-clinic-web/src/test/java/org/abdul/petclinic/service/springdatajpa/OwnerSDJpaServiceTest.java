package org.abdul.petclinic.service.springdatajpa;

import org.abdul.petclinic.model.Owner;
import org.abdul.petclinic.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {
    @Mock
    private OwnerRepository ownerRepository;
    @InjectMocks
    private OwnerSDJpaService ownerSDJpaService;
    private Owner inputOwner;

    @BeforeEach
    void setUp() {
        inputOwner = Owner.builder().id(1L).firstName("First Name").lastName("Last Name").build();
    }

    @Test
    void shouldReturnOwnerWhenOwnerWithGivenLastNameExists() {
        //given
        when(ownerRepository.findByLastName("Last Name")).thenReturn(ofNullable(inputOwner));
        //then
        assertEquals(inputOwner, ownerSDJpaService.findByLastName("Last Name"));
    }

    @Test
    void shouldReturnNullWhenOwnerWithGivenLastNameNotExists() {
        //given
        when(ownerRepository.findByLastName("Last Name")).thenReturn(Optional.empty());
        //then
        assertNull(ownerSDJpaService.findByLastName("Last Name"));
    }

    @Test
    void shouldReturnAllOwnersWhenNotEmpty() {
        //given
        Owner owner1 = Owner.builder().id(1L).firstName("Name1").build();
        Owner owner2 = Owner.builder().id(2L).firstName("Name2").build();
        Owner owner3 = Owner.builder().id(3L).firstName("Name3").build();
        List<Owner> ownersList = new ArrayList<>(Arrays.asList(owner1, owner2, owner3));
        Set<Owner> ownersSet = new HashSet<>(Arrays.asList(owner1, owner2, owner3));
        when(ownerRepository.findAll()).thenReturn(ownersList);
        //then
        assertEquals(ownersSet, ownerSDJpaService.findAll());
    }

    @Test
    void shouldReturnEmptySetWhenEmpty() {
        when(ownerRepository.findAll()).thenReturn(Collections.emptyList());
        //then
        assertEquals(0, ownerSDJpaService.findAll().size());
    }

    @Test
    void shouldReturnOwnerWhenOwnerWithGivenIdExists() {
        //given
        when(ownerRepository.findById(1L)).thenReturn(Optional.ofNullable(inputOwner));
        //then
        assertEquals(inputOwner, ownerSDJpaService.findById(1L));
    }

    @Test
    void shouldReturnNullWhenOwnerWithGivenIdNotExists() {
        //given
        when(ownerRepository.findById(1L)).thenReturn(Optional.empty());
        //then
        assertNull(ownerSDJpaService.findById(1L));
    }

    @Test
    void shouldInvokeRepositorySaveMethodAndReturnOwnerWhenOwnerIsSaved() {
        //given
        when(ownerRepository.save(inputOwner)).thenReturn(inputOwner);
        //then
        assertEquals(inputOwner, ownerSDJpaService.save(inputOwner));
        verify(ownerRepository, times(1)).save(inputOwner);
    }

    @Test
    void shouldDeleteOwnerIfExists() {
        //when
        ownerSDJpaService.delete(inputOwner);
        //then
        verify(ownerRepository, times(1)).delete(inputOwner);
    }

    @Test
    void deleteById() {
        //when
        ownerSDJpaService.deleteById(1L);
        //then
        verify(ownerRepository, times(1)).deleteById(1L);
    }
}