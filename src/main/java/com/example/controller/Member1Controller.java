package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Member1;
import com.example.repository.Member1Repository;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping(value = "/member1")
public class Member1Controller {
    
    @Autowired
    Member1Repository member1Repository;
    
    @Autowired
    HttpSession httpSession;

    @PostMapping("/login.do")
    public String loginPOST(@ModelAttribute Member1 obj) {
        System.out.println(obj.toString());
        
        //아이디를 이용해 저장소에서 1개를 꺼냄 , 없으면 null로 꺼내짐
        Member1 obj1 =member1Repository.findById(obj.getId()).orElse(null);

        //아이디 존재시
        if(obj1 != null){
            //꺼낸 회원암호와 전달된 암호를 비교해 같은지 확인
            if(obj1.getPw().equals(obj.getPw())){
                //로그인 성공
                httpSession.setAttribute("login", 1);
                httpSession.setAttribute("loginid", obj.getId());
            }
        }
        return "redirect:/member1/home.do";
    }
    
    @PostMapping(value = "/logout.do")
    public String logoutPOST() {

        httpSession.invalidate();
        return "redirect:/member1/home.do";
    }
    

    @GetMapping(value = "/login.do")
    public String loginGET() {
        return "member1login";
    }
    

    @GetMapping(value = "/list.do")
    public String listGET(Model model) {
        List<Member1> list = member1Repository.findAll();
        model.addAttribute("list", list);
        return "member1list";
    }
    

    //127.0.0.1:8080/ROOT/member1/join.do
    @GetMapping(value = {"/home.do" , "/main.do" , "/"})
    public String homeGET() {
        return "member1home";
    }

    //127.0.0.1:8080/ROOT/member1/join.do

    @GetMapping(value = "/join.do")
    public String joinGET() {
        return "member1join" ;
    }

    @PostMapping(value = "/join.do")
    public String joinPOST(@ModelAttribute Member1 obj) {

        System.out.println(obj.toString());
        member1Repository.save(obj);

        //html을 표시X , 페이지의 이동(redirect)
        return "redirect:/member1/home.do";
    }
    
    
}
