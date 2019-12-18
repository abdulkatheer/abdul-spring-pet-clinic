package org.abdul.petclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/owners")
public class OwnerController {

   @GetMapping({"", "/", "/index", "/index.html", "/index.htm"})
   public String listOwners() {
      return "owners/index";
   }

}
