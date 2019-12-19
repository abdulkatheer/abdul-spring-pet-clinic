package org.abdul.petclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WelcomeController {

    @GetMapping({"", "/", "/index", "/index.htm", "/index.html"})
    public String welcome() {
        return "index";
    }

}
