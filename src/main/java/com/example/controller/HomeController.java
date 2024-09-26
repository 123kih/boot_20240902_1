package com.example.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.entity.Member1;
import com.example.repository.Member1Repository;

import lombok.RequiredArgsConstructor;



@Controller
//컨트롤러 위치 설정
@RequiredArgsConstructor

public class HomeController {

    final Member1Repository member1Repository;

    @GetMapping(value = "/sellerjoin.do")
    public String sellerjoinGET() {
        return "sellerjoin";
    }
    
    
    @PostMapping(value = "/sellerjoinaction.do")
    public String joinPOST(@ModelAttribute Member1 obj, Model model) {
        System.out.println(obj.toString());
        
        //암호를 암호화 시킴
        BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
        obj.setPw(bcpe.encode(obj.getPw()));

        Member1 ret = member1Repository.save(obj);
        
        if(ret == null){
            model.addAttribute("msg","회원가입 실패");
            model.addAttribute("url","/ROOT/sellerjoin.do");
        }
        else{
            model.addAttribute("msg", "회원가입성공");
            model.addAttribute("url", "/home.do");
        }
        
        //알림창 표시시점
        return "alert";
    }

    @GetMapping("/403page.do")
    public String page403GET() {
        return "page403";
    }
    
    //127.0.0.1:8080/ROOT/home.do
    @GetMapping("/home.do")
    public String getMethodName(@AuthenticationPrincipal User user) {
        if(user!=null){
            System.out.println("로그인중 => "+user.toString());
        }
        else{
            System.out.println("로그아웃");
        }
        return "home"; // (home은 파일명) home.jsp , home.html 같은 파일을 화면에 출력 
    }

    @GetMapping("/login.do")
    public String loginGET() {
        return "login";
    }
    
    @GetMapping("/join.do")
    public String joinGET() {
        
        return "customer_join";
    }
    
    
    //127.0.0.1:8080/ROOT/main.do
    @GetMapping("/main.do")
    public String mainGET() {

        return "home";
    }
    
    
}
