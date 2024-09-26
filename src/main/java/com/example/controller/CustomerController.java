package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Member1;
import com.example.repository.Member1Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping(value = "/customer")
public class CustomerController {
    
    @Autowired
    Member1Repository member1Repository;

    @PostMapping(value = "/joinaction.do")
    public String joinPOST(@ModelAttribute Member1 obj) {
        
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        obj.setPw(bcpe.encode(obj.getPw()));
        System.out.println(obj.toString());
        
        member1Repository.save(obj);
        return "redirect:/home.do";
    }

    @GetMapping(value = "/home.do")
    public String homeGET() {
        
        return "customer_home";
    }
    
    
}
