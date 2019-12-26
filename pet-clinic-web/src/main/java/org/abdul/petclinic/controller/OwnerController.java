package org.abdul.petclinic.controller;

import org.abdul.petclinic.model.Owner;
import org.abdul.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping({"", "/", "/index", "/index.html", "/index.htm"})
    public String listOwners(Model response) {
        response.addAttribute("owners", ownerService.findAll());
        return "owners/index";
    }

    @GetMapping("/find")
    public String findOwner() {
        //Not Implemented Yet
        return "owners/index";
    }

    @GetMapping("/{id}")
    public ModelAndView showOwner(@PathVariable("id") Long ownerId) {
        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        Owner owner = ownerService.findById(ownerId);
        modelAndView.addObject(owner);
        return modelAndView;
    }
}
