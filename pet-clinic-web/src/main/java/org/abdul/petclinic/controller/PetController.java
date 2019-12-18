package org.abdul.petclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pets")
public class PetController {

   @GetMapping({"", "/", "/index", "/index.htm", "/index.html"})
   public String listOfPets() {
      return "pets/index";
   }

}
