package org.abdul.petclinic.controller;

import org.abdul.petclinic.model.Owner;
import org.abdul.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private static final String CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private static final String VIEW_OWNER_REDIRECT_PREFIX = "redirect:/owners/";
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
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
            owners = ownerService.findByLastNameLike(owner.getLastName());
        }

        if (owners.isEmpty()) {
            result.rejectValue("lastName", "notFound", "Not Found");
            return "owners/findOwners";
        } else if (owners.size() == 1) {
            return VIEW_OWNER_REDIRECT_PREFIX + owners.get(0).getId();
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

    @GetMapping("/new")
    public String initCreateForm(Model response) {
        response.addAttribute("owner", new Owner());
        return CREATE_OR_UPDATE_OWNER_FORM;
    }

    @PostMapping("/new")
    public String processCreateForm(@Valid Owner owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return CREATE_OR_UPDATE_OWNER_FORM;
        }
        ownerService.save(owner);
        return VIEW_OWNER_REDIRECT_PREFIX + owner.getId();
    }

    @GetMapping("/{id}/edit")
    public String initUpdateForm(@PathVariable("id") Long id, Model response) {
        Owner owner = ownerService.findById(id);
        if (owner == null) {
            return "redirect:/owners/new";
        }
        response.addAttribute("owner", owner);
        return CREATE_OR_UPDATE_OWNER_FORM;
    }

    @PostMapping("/{id}/edit")
    public String processUpdateForm(@Valid Owner owner, @PathVariable("id") Long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return CREATE_OR_UPDATE_OWNER_FORM;
        } else {
            owner.setId(id);
            ownerService.save(owner);
            return VIEW_OWNER_REDIRECT_PREFIX + owner.getId();
        }
    }

}
