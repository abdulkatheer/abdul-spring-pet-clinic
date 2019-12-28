package org.abdul.petclinic.controller;

import org.abdul.petclinic.service.VetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {
    @Mock
    private VetService vetService;
    @InjectMocks
    private VetController vetController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vetController).build();
    }

    @Test
    void listOfVets() throws Exception {
        //given
        when(vetService.findAll()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/vets"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("vets"))
                .andExpect(view().name("vets/vetList"));
    }
}