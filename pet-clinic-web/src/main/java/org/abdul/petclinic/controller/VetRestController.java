package org.abdul.petclinic.controller;

import org.abdul.petclinic.model.Vet;
import org.abdul.petclinic.service.VetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/vets")
public class VetRestController {
    private final VetService vetService;

    public VetRestController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping(produces = "application/json")
    public Set<Vet> getAllVets() {
        return vetService.findAll();
    }
}
