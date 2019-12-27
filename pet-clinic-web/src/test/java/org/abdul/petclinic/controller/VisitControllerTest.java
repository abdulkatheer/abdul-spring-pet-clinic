package org.abdul.petclinic.controller;

import org.abdul.petclinic.model.Pet;
import org.abdul.petclinic.model.Visit;
import org.abdul.petclinic.service.PetService;
import org.abdul.petclinic.service.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
    private static final String CREATE_NEW_VISIT_URI = "/owners/1/pets/1/visits/new";
    private static final String CREATE_OR_UPDATE_VISIT_FORM = "pets/createOrUpdateVisitForm";

    @Mock
    private PetService petService;
    @Mock
    private VisitService visitService;
    @InjectMocks
    private VisitController visitController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
    }

    @Test
    void initCreateForm() throws Exception {
        //given
        when(petService.findById(1L)).thenReturn(Pet.builder().id(1L).build());

        //then
        mockMvc.perform(get(CREATE_NEW_VISIT_URI).param("petId", String.valueOf(1L)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(CREATE_OR_UPDATE_VISIT_FORM));
        verify(petService, times(1)).findById(1L);
    }

    @Test
    void shouldSaveVisitAndRedirectToOwnersPageWhenVisitHasNoValidationErrors() throws Exception {
        //given
        Visit visit = Visit.builder()
                .id(1L)
                .date(LocalDate.of(2019, 12, 31))
                .description("Cold")
                .build();

        //then
        mockMvc.perform(post(CREATE_NEW_VISIT_URI).flashAttr("visit", visit))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/owners/1"));
        verify(visitService, times(1)).save(visit);
    }
}