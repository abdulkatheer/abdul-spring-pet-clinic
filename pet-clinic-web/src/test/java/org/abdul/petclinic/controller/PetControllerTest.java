package org.abdul.petclinic.controller;

import org.abdul.petclinic.model.Owner;
import org.abdul.petclinic.model.Pet;
import org.abdul.petclinic.model.PetType;
import org.abdul.petclinic.service.OwnerService;
import org.abdul.petclinic.service.PetService;
import org.abdul.petclinic.service.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {
    private static final String CREATE_OR_UPDATE_PET_FORM = "pets/createOrUpdatePetForm";

    @Mock
    private OwnerService ownerService;
    @Mock
    private PetService petService;
    @Mock
    private PetTypeService petTypeService;
    @InjectMocks
    private PetController petController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    @Test
    void shouldPopulateOwnerInTheOwnerAttribute() throws Exception {
        //given
        Owner owner = Owner.builder().id(1L).build();
        when(ownerService.findById(1L)).thenReturn(owner);

        //then
        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attribute("owner", notNullValue()))
                .andExpect(model().attribute("pet", notNullValue()))
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))))
                .andExpect(view().name(CREATE_OR_UPDATE_PET_FORM));
        verify(ownerService, times(1)).findById(1L);
    }

    @Test
    void shouldReturnErrorsWhenPetWithSameNameAlreadyExists() throws Exception {
        //given
        Owner owner = Owner.builder().id(1L).build();
        Pet pet = Pet.builder().id(1L).name("Jimmy").birthDate(LocalDate.of(2019, 12, 31)).build();
        owner.setPets(Collections.singleton(pet));

        //then
        mockMvc.perform(post("/owners/1/pets/new")
                .flashAttr("owner", owner)
                .flashAttr("pet", pet))
                .andExpect(model().hasErrors())
                .andExpect(view().name(CREATE_OR_UPDATE_PET_FORM));
    }

    @Test
    void shouldReturnErrorsWhenPetWithSameNameNotExistsButHasFieldErrors() throws Exception {
        //given
        Owner owner = Owner.builder().id(1L).build();
        Pet pet = Pet.builder().name("Jimmy").build();

        //then
        mockMvc.perform(post("/owners/1/pets/new")
                .flashAttr("owner", owner)
                .flashAttr("pet", pet))
                .andExpect(model().attributeHasFieldErrorCode("pet", "petType", "required"))
                .andExpect(model().attributeHasFieldErrorCode("pet", "birthDate", "required"))
                .andExpect(view().name(CREATE_OR_UPDATE_PET_FORM));
        verify(ownerService, never()).findById(1L);
    }

    @Test
    void shouldSavePetAndOwnerAndRedirectWhenPetWithSameNameNotExistsAndIsValid() throws Exception {
        //given
        Owner owner = Owner.builder().id(1L).build();
        Pet pet = Pet.builder().name("Jimmy").petType(PetType.builder().name("Dog").build()).birthDate(LocalDate.of(2019, 12, 31)).build();

        //then
        mockMvc.perform(post("/owners/1/pets/new")
                .flashAttr("owner", owner)
                .flashAttr("pet", pet))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/owners/1"));
        verify(petService, times(1)).save(pet);
    }

    @Test
    void testInitUpdateForm() throws Exception {
        //given
        Pet pet = Pet.builder().id(1L).build();
        when(petService.findById(1L)).thenReturn(pet);

        //then
        mockMvc.perform(get("/owners/1/pets/1/edit").param("petId", String.valueOf(1L)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute("pet", hasProperty("id", is(1L))))
                .andExpect(view().name(CREATE_OR_UPDATE_PET_FORM));
    }

    @Test
    void shouldReturnToEditPageWhenValidationErrorExists() throws Exception {
        //given
        Pet pet = Pet.builder().id(1L).build();
        Owner owner = Owner.builder().id(1L).build();

        //then
        mockMvc.perform(post("/owners/1/pets/1/edit").flashAttr("pet", pet).flashAttr("owner", owner))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().hasErrors())
                .andExpect(model().attribute("pet", hasProperty("id", is(1L))))
                .andExpect(view().name(CREATE_OR_UPDATE_PET_FORM));
    }

    @Test
    void shouldSavePetAndReturnToOwnerPageWhenPetIsValid() throws Exception {
        //given
        Pet pet = Pet.builder()
                .id(1L)
                .name("Jimmy")
                .birthDate(LocalDate.of(2019, 12, 31))
                .petType(PetType.builder().id(1L).name("Dog").build())
                .build();
        Owner owner = Owner.builder().id(1L).build();
        owner.addPet(pet);

        //then
        mockMvc.perform(post("/owners/1/pets/1/edit").flashAttr("pet", pet).flashAttr("owner", owner))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/owners/1"));
        verify(petService, times(1)).save(pet);
    }
}