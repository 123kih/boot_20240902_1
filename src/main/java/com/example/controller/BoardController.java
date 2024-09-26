package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entity.Board;
import com.example.mapper.BoardMapper;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/board")
public class BoardController {
    
    @Autowired
    BoardMapper bMapper;
    
    @Autowired
    HttpSession httpSession;

    @PostMapping(value = "/update.do")
    public String updatePost(@ModelAttribute Board obj) {
        System.out.println(obj.toString());
        bMapper.updateBoardOne(obj);
        return "redirect:/board/content.do?no=" + obj.getNo();
    }
    
    //@ModelAttribute은 화면에서 데이터를 불러오는것 , Model은 데이터를 화면으로 출력하는 것(Select) 
    //@RequestParam은 /board/update.do?no= ? 여기서 no의 번호를 불러오기 위함(한개)
    @GetMapping(value = "/update.do")
    public String updateGET(@RequestParam(name="no")int no, Model model) {
        
        Board obj = bMapper.selectBoardOne(no);
        model.addAttribute("obj",obj);
        return "boardUpdate";
    }
    
    @PostMapping(value = "/delete.do")
    public String deletePOST(@RequestParam(name = "no") int no) {
        System.out.println("삭제하기 위해 전달된 글번호 => " + no);
        //post는 html을 표시하지 않고 주소창에 주소입력후 엔터키 수행하는 redirect 사용해야함
        
        bMapper.deleteBoardOne(no);
        return "redirect:/board/list.do";
    }
    

    @GetMapping("/write.do")
    public String writeGET() {
        return "boardwrite";
    }
    
    //@ModelAttribute은 화면에서 데이터를 불러오는것
    @PostMapping(value = "/write.do")
    public String writePOST(@ModelAttribute Board obj) {
        //0.사용자가 입력한 항목 받기( 제목, 내용 , 작성자)
        System.out.println(obj.toString());

        //1. obj를 이용해 DB에 추가
        bMapper.insertBoardOne(obj);

        //2.목록으로 이동
        return "redirect:/board/list.do";
    }
    

    //127.0.0.1:8080/ROOT/board/list.do  ?  no = 3
    @GetMapping(value = "/content.do")
    public String contentGET(@RequestParam(name="no")int no , Model model) {
        System.out.println(no);

        int ck = (int) httpSession.getAttribute("hitcheck"); //key를 통해 가져오기
        if(ck==1){
        //0.조회수 증가
        bMapper.updateBoardHit(no);
        httpSession.setAttribute("hitcheck", 0);
        }
        //1.DB에서 가져오기
        Board obj = bMapper.selectBoardOne(no);

        //2.html로 전달
        model.addAttribute("obj" , obj);

        //3.boardcontent.html 출력
        return "boardcontent";
    }
    
    //127.0.0.1:8080/ROOT/board/list.do
    @GetMapping(value = "list.do")
    public String listGET(Model model) {

        httpSession.setAttribute("hitcheck", 1); //세션에 추가하기 (key를 이용해서)
        
        
        //1.DB에서 가져오기
        List<Board> list=bMapper.selectBoardList();
        System.out.println(list.toString());

        //2.html로 전송
        model.addAttribute("list",list);

        //3.html 표시  resources/ templates / boardlist.html 출력
        return "boardlist";
    }
    
}
