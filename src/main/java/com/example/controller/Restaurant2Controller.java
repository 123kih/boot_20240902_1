package com.example.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.entity.Category2;
import com.example.entity.Menu2;
import com.example.entity.Restaurant2;
import com.example.repository.Category2Repository;
import com.example.repository.Menu2Repository;
import com.example.repository.Restaurant2Repository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
@RequestMapping(value = "/restaurant2")
@RequiredArgsConstructor
public class Restaurant2Controller {
    
    final Category2Repository c2Repository;
    final Restaurant2Repository r2Repository;
    final Menu2Repository m2Repository;
    final HttpSession httpSession;

    @PostMapping(value = "/menu_delete.do")
    public String menuDeletePOST(@RequestParam (name = "no") int no) {
        System.out.println(no);
        m2Repository.deleteById(no);

        return "redirect:/restaurant2/home.do";
    }
    


    @PostMapping(value = "/insert_menu.do")
    public String insertMenuPOST(@ModelAttribute Menu2 obj , @RequestParam(name="catecode") int code) {
        Category2 cate2 = new Category2();
        cate2.setCode(code);

        Restaurant2 restaurant = new Restaurant2();
        String crNumber =(String) httpSession.getAttribute("USERID");
        restaurant.setCrNumber(crNumber);
        
        obj.setCate(cate2);
        obj.setRestaurant(restaurant);
        
        System.out.println(obj.toString());

        m2Repository.save(obj);
        return "redirect:/restaurant2/home.do";
    }
    

    @GetMapping(value = "/login.do")
    public String loginGET() {
        return "restaurant2_login";
    }

    @PostMapping(value = "/logout.do")
    public String logoutPOST() {
        
        //세션정보 초기화 => invalidate()
        httpSession.invalidate();
        return "redirect:/restaurant2/home.do";
    }
    
    @PostMapping(value = "/login.do")
    public String loginPOST(@ModelAttribute Restaurant2 obj) {
        System.out.println(obj.toString());
        
        //1.기존정보 읽기(사업자번호, 암호읽기)
        Restaurant2 ret = r2Repository.findById(obj.getCrNumber()).orElse(null);
        //2.읽은 정보에서 암호를 전달된 obj와 기존정보 ret비교
        if(ret.getPassword().equals(obj.getPassword())){
            //3.암호가 같으면 세션에 등록 후 페이지 이동
            httpSession.setAttribute("LOGGED", 1);
            httpSession.setAttribute("USERID", ret.getCrNumber());
            httpSession.setAttribute("NAME", ret.getName());
            return "redirect:/restaurant2/home.do";
        }

        return "restaurant2_login";
    }
    
    

    @GetMapping(value = "/home.do")
    public String homeGET(Model model) {
        //세션에서 로그인 정보 읽음( 현재는 로그인 정보 없음 )
        Integer logged = (Integer) httpSession.getAttribute("LOGGED");
        //로그인 되지 않았다면 login페이지로 이동
        if(logged == null){
            return "redirect:/restaurant2/login.do";
        }

        //카테고리가져오기
        List<Category2> list = c2Repository.findAll();
        model.addAttribute("list", list);

        //등록한 메뉴 가져오기
        String crNumber = (String) httpSession.getAttribute("USERID");
        List<Menu2> list1 =m2Repository.findByRestaurant_crNumber(crNumber);
        model.addAttribute("list1", list1);
        return "restaurant2_home";
    }
    
}
