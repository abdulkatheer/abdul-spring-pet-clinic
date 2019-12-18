package org.abdul.petclinic.controller;

import org.abdul.petclinic.service.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PetClinicController {

    private PetService petService;

    public PetClinicController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping({"", "/", "/index", "/index.htm", "/index.html"})
    public String listOfPets(Model response) {
        response.addAttribute("pets", petService.findAll());
        return "pets/index";
    }

}
