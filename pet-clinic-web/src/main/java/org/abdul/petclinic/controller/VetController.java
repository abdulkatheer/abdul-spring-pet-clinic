package org.abdul.petclinic.controller;

import org.abdul.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vets")
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping({"", "/", "/index", "/index.htm", "/index.html"})
    public String listOfVets(Model response) {
        response.addAttribute("vets", vetService.findAll());
        return "vets/index";
    }

}
