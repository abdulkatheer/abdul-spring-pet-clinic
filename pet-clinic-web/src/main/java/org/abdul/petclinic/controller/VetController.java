package org.abdul.petclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vets")
public class VetController {

   @GetMapping({"", "/", "/index", "/index.htm", "/index.html"})
   public String listOfVets() {
      return "vets/index";
   }

}
