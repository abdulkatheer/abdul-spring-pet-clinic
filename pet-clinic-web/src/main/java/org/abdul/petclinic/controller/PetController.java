package org.abdul.petclinic.controller;

import lombok.extern.slf4j.Slf4j;
import org.abdul.petclinic.model.Owner;
import org.abdul.petclinic.model.Pet;
import org.abdul.petclinic.model.PetType;
import org.abdul.petclinic.service.OwnerService;
import org.abdul.petclinic.service.PetService;
import org.abdul.petclinic.service.PetTypeService;
import org.abdul.petclinic.validator.PetValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/owners/{ownerId}/")
@Slf4j
public class PetController {
    private static final String CREATE_OR_UPDATE_PET_FORM = "pets/createOrUpdatePetForm";

    private final PetService petService;
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;

    public PetController(PetService petService, OwnerService ownerService, PetTypeService petTypeService) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
    }

    @InitBinder("owner")
    public void ownerInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @InitBinder("pet")
    public void petInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new PetValidator());
    }

    @ModelAttribute("types")
    public Set<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") Long id) {
        log.info("Fetching from DB for ID : {}", id);
        return ownerService.findById(id);
    }

    @GetMapping("pets/new")
    public String initCreateForm(Owner owner, Model model) {
        log.info("Init Form : {}" ,owner);
        Pet pet = new Pet();
        owner.addPet(pet);
        model.addAttribute("pet", pet);
        return CREATE_OR_UPDATE_PET_FORM;
    }

    @PostMapping("pets/new")
    public String processCreateForm(Owner owner, @Valid Pet pet, BindingResult bindingResult, Model model) {
        if (owner.getPet(pet.getName(), true) != null) {
            bindingResult.rejectValue("name", "duplicate",
                    String.format("Pet with name %s already exists", pet.getName()));
        }

        log.info("Inside Process form : {}", owner);
        owner.addPet(pet);
        if (bindingResult.hasErrors()) {
            log.info("Pet has Errors. So returning to same page!");
            return CREATE_OR_UPDATE_PET_FORM;
        }

        petService.save(pet);
        return "redirect:/owners/{ownerId}";
    }

    @GetMapping("pets/{petId}/edit")
    public String initUpdateForm(@PathVariable("petId") Long id, Model model) {
        Pet pet = petService.findById(id);
        model.addAttribute("pet", pet);
        return CREATE_OR_UPDATE_PET_FORM;
    }

    @PostMapping("pets/{petId}/edit")
    public String processUpdateForm(@Valid Pet pet, BindingResult bindingResult, Owner owner, Model model) {
        if (bindingResult.hasErrors()) {
            log.info("Pet has errors, returning to same page!");
            return CREATE_OR_UPDATE_PET_FORM;
        }
        owner.addPet(pet);
        petService.save(pet);
        return "redirect:/owners/{ownerId}";
    }
}
