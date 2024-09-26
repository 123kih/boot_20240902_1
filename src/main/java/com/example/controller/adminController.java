package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping(value = "/admin")
public class adminController {
    
    @GetMapping(value = "/home.do")
    public String homeGET() {
        return "admin_home";
    }
    
}
