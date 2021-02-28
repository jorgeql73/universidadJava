
package com.challenge.university.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {
    @GetMapping(path="/login") 
    public String login1(Model model){
        return "login";
        
    }
    @GetMapping(path="/loginStudent") 
    public String login2(Model model){
        return "loginStudent";
        
    }
}
