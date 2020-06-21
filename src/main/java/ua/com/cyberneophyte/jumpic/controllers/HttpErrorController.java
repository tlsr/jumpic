package ua.com.cyberneophyte.jumpic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HttpErrorController {

    @GetMapping("/403")
    public String accessDenied(){
        return "/errorPages/accessDenied.html";
    }
}
