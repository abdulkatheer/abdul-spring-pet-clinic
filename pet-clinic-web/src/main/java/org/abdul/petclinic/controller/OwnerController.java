package org.abdul.petclinic.controller;

import org.abdul.petclinic.model.Owner;
import org.abdul.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

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
    public ModelAndView initFindOwnerForm() {
        ModelAndView modelAndView = new ModelAndView("owners/findOwners");
        modelAndView.addObject(new Owner());
        return modelAndView;
    }

    @GetMapping("/selected")
    public String processFindOwnerForm(Owner owner, BindingResult result, Model response) {
        if (owner.getLastName() == null) {
            owner.setLastName("");
        }

        List<Owner> owners;
        if (owner.getLastName().equals("")) {
            owners = new ArrayList<>(ownerService.findAll());
        } else {
            owners = ownerService.findByLastName(owner.getLastName());
        }

        if (owners.isEmpty()) {
            result.rejectValue("lastName", "notFound", "Not Found");
            return "owners/findOwners";
        } else if (owners.size() == 1) {
            return "redirect:/owners/" + owners.get(0).getId();
        } else {
            response.addAttribute("selections", owners);
            return "owners/ownersList";
        }
    }

    @GetMapping("/{id}")
    public ModelAndView showOwner(@PathVariable("id") Long ownerId) {
        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        Owner owner = ownerService.findById(ownerId);
        modelAndView.addObject(owner);
        return modelAndView;
    }
}
