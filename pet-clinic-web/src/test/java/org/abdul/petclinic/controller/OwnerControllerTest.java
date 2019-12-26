package org.abdul.petclinic.controller;

import org.abdul.petclinic.model.Owner;
import org.abdul.petclinic.service.OwnerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    private static final String LAST_NAME = "Mohamed Amsa";
    @Mock
    private OwnerService ownerService;
    private Set<Owner> owners;
    @InjectMocks
    private OwnerController ownerController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();
        owners.add(Owner.builder().id(1L).build());
        owners.add(Owner.builder().id(2L).build());
        owners.add(Owner.builder().id(3L).build());

        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    public void listOwners() throws Exception {
        //given
        when(ownerService.findAll()).thenReturn(owners);
        //then
        mockMvc.perform(get("/owners"))
                .andExpect(status().is(200))
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", Matchers.hasSize(3)));
        verify(ownerService, times(1)).findAll();
    }

    @Test
    public void showOwner() throws Exception {
        //given
        when(ownerService.findById(1L)).thenReturn(Owner.builder().id(1L).firstName("Abdul").build());

        //when
        mockMvc.perform(get("/owners/1"))
                .andExpect(status().is(200))
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))))
                .andExpect(model().attribute("owner", hasProperty("firstName", is("Abdul"))));
    }

    @Test
    public void initFindOwnerForm() throws Exception {
        //when
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().is(200))
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    public void shouldReturnNotFoundErrorWhenOwnersNotFound() throws Exception {
        //given
        Owner owner = Owner.builder().lastName(LAST_NAME).build();
        when(ownerService.findByLastNameLike(LAST_NAME)).thenReturn(Collections.emptyList());

        //when
        mockMvc.perform(get("/owners/selected").param("lastName", LAST_NAME))
                .andExpect(status().is(200))
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void shouldForwardToDisplaySingleOwnerDetailsWhenSingleOwnerFound() throws Exception {
        //given
        Owner owner = Owner.builder()
                .id(1L)
                .firstName("Abdul Katheer")
                .lastName(LAST_NAME)
                .build();
        when(ownerService.findByLastNameLike("Mohamed Amsa")).thenReturn(Collections.singletonList(owner));

        //when
        mockMvc.perform(get("/owners/selected").param("lastName", LAST_NAME))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void shouldReturnViewForListingOwnerDetailsWhenMultipleOwnerFound() throws Exception {
        //given
        Owner owner1 = Owner.builder()
                .id(1L)
                .firstName("Abdul Katheer")
                .lastName(LAST_NAME)
                .build();
        Owner owner2 = Owner.builder()
                .id(2L)
                .firstName("Faizal Ahamed")
                .lastName(LAST_NAME)
                .build();
        when(ownerService.findByLastNameLike(LAST_NAME)).thenReturn(Arrays.asList(owner1, owner2));

        //when
        mockMvc.perform(get("/owners/selected").param("lastName", LAST_NAME))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("selections", hasSize(2)))
                .andExpect(view().name("owners/ownersList"));
    }
}