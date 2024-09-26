package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Member;
import com.example.mapper.MemberMapper;






@Controller
@RequestMapping(value = "/member")
public class MemberController{

    // 1. 매퍼 객체 생성
    @Autowired
    MemberMapper memberMapper;

    //127.0.0.1:8080/ROOT/member/list.do
    @GetMapping("/list.do")
    public String listGET(Model model) {
        // 1. DB에서 회원정보 가져오기
        List<Member> list= memberMapper.selectMemberList();
        System.out.println(list.toString());
        
        // 2.html로 전달
        model.addAttribute("list",list);

        // 3. html 레너링
        return "memberlist";
    }

    //127.0.0.1:8080/ROOT/member/join.do
    @GetMapping("/join.do")
    public String getMethodName() {

        //render = html파일을 화면에 표시
        return "join"; //resources/templates/join.html 을 return
    }

    @PostMapping("/join.do")
    public String joinPOST(@ModelAttribute Member obj) {
        System.out.println(obj.toString());
        // 2. DB 처리하고
        memberMapper.insertMemberOne(obj);

        //redirect = html을 보여주는게 아니라 주소를 바꾸고 엔터키 누른 것

        //127.0.0.1:8080/ROOT/hmoe.do 엔터키
        return "redirect:/home.do";
    }
    
}