package org.abdul.petclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping({"/error", "/error.html", "/error.htm"})
    public String oopsNotFound() {
        return "error";
    }
}
