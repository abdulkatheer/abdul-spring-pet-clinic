package org.abdul.petclinic.controller;

import org.abdul.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping({"", "/", "/index", "/index.html", "/index.htm"})
    public String listOwners(Model response) {
        response.addAttribute("owners", ownerService.findAll());
        return "owners/index";
    }

}
