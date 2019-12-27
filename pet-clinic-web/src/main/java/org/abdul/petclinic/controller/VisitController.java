package org.abdul.petclinic.controller;

import org.abdul.petclinic.model.Pet;
import org.abdul.petclinic.model.Visit;
import org.abdul.petclinic.service.PetService;
import org.abdul.petclinic.service.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}/")
public class VisitController {
    private static final String CREATE_OR_UPDATE_VISIT_FORM = "pets/createOrUpdateVisitForm";

    private final PetService petService;
    private final VisitService visitService;

    public VisitController(PetService petService, VisitService visitService) {
        this.petService = petService;
        this.visitService = visitService;
    }

    @ModelAttribute("visit")
    public Visit loadVisit(@PathVariable("petId") Long id, Model model) {
        Pet pet = petService.findById(id);
        Visit visit = Visit.builder().build();
        pet.addVisit(visit);
        model.addAttribute("pet", pet);
        return visit;
    }

    @GetMapping("visits/new")
    public String initCreateForm() {
        return CREATE_OR_UPDATE_VISIT_FORM;
    }

    @PostMapping("visits/new")
    public String processCreateForm(@Valid Visit visit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return CREATE_OR_UPDATE_VISIT_FORM;
        }
        visitService.save(visit);
        return "redirect:/owners/{ownerId}";
    }
}
