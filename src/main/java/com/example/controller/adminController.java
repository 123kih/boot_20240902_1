package com.example.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Restaurant2;
import com.example.repository.Restaurant2Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

// AdminController는 권한이 ADMIN인 회원만 접근가능
@Controller
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    
    final Restaurant2Repository r2Repository;

    @PostMapping(value = "/restaurant_insert.do")
    public String insertPOST(@ModelAttribute Restaurant2 obj) {
        try{
            log.info(obj.toString());
            r2Repository.save(obj);
            return "redirect:/admin/home.do?menu=1";
        }catch(Exception e){
            log.error(e.getMessage());
            return "redirect:/admin/home.do?menu=1";
        }
        
    }
    

    // 127.0.0.1:8080/ROOT/admin/home.do => login.do => /admin/home.do
    // 127.0.0.1:8080/ROOT/admin/home.do?menu=1 식당
    @GetMapping(value = "/home.do")
    public String homeGET(Model model,
        @RequestParam(name = "menu" , defaultValue = "0") int menu,
        @RequestParam(name = "text" , defaultValue = "") String text,
        @RequestParam(name = "page" , defaultValue = "1") int page) {
        
        if(menu <=0){
            return "redirect:/admin/home.do?menu=1";
        }

        PageRequest pageRequest = PageRequest.of(page-1, 10);
        List<Restaurant2> list = r2Repository.findByNameContainingOrderByNameAsc(text, pageRequest);

        //전체 개수 (11=>2 , 20>2 , 21=>3)
        long total = r2Repository.countByNameContaining(text);
        
        model.addAttribute("menu", menu);
        model.addAttribute("list", list);
        model.addAttribute("pages" , (total-1)/10+1);
         // resources / templates / admin_home.html
        return "admin_home";
    }
    
}
