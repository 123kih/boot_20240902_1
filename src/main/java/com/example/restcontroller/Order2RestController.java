package com.example.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Order2;
import com.example.entity.Order2Step;
import com.example.entity.Oreder2View;
import com.example.repository.Order2Repository;
import com.example.repository.Order2StepRepository;
import com.example.repository.Order2ViewRepository;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



//react.js , vue.js , angular.js , android , ios , flutter 등.... 과 연동할때 restcontroller
@RestController
@RequestMapping(value = "/api/order2")
@RequiredArgsConstructor
public class Order2RestController {

    final Order2ViewRepository o2vRepository;
    final Order2Repository o2Repository;
    final Order2StepRepository o2sRepository;

    //주문에 대한 거래내역 조회 ( 언제 주문 했고, 언제 취소 했는지 등등 )
    //127.0.0.1:8080/ROOT/api/order2/selectone.json?no=14
    @GetMapping(value = "/selectone.json")
    public Map<String, Object> selectOneGET(@RequestParam(name = "orderno") int orderno) {
        Map<String, Object> map = new HashMap<>();
        try{
            List<Order2Step> list = o2sRepository.findByOrderno_noOrderByCodeAsc(orderno);
            
            map.put("result", list);
            map.put("status", 200);
        }catch(Exception e ){
            map.put("status", -1);
        }
        return map;
    }
    

    //주문 상태 읽기
    //127.0.0.1:8080/ROOT/api/order2/selectstep.json?no=14
    @GetMapping(value = "/selectstep.json")
    public Map<String,Object> selectstepPOST(@RequestParam(name = "no") int no) {
        Map<String , Object> map = new HashMap<>();
        try{
            Order2Step obj = o2sRepository.findTop1ByOrderno_noOrderByCodeDesc(no);
            map.put("result", obj);
            map.put("stauts", 200);
        }catch(Exception e){
            System.out.println(e.getMessage());
            map.put("status", -1);
        }
        
        return map;
    }
    


    //주문취소
    //127.0.0.1:8080/ROOT/api/order2/delete.json
    //{"no" : 14}
    @PostMapping(value = "/delete.json")
    public Map<String, Object> deletePOST(@RequestBody Order2 obj) {
        Map<String, Object> map = new HashMap<>();
        try{
            Order2Step obj2 = new Order2Step();
            obj2.setOrderno(obj);
            obj2.setOrderstep(0);
            Order2Step ret = o2sRepository.save(obj2);
            
            map.put("status", 0);
            if(ret != null){
                map.put("stauts", 200);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            map.put("status", -1);
        }
        
        return map;
    }

    //고객아이디 , 메뉴번호 , 수량
    //127.0.0.1:8080/ROOT/api/order2/insert.json
    //{"customerid" : {"id" :"c1"} , "menu" : {"no" : 1} , "qty" : 1}
    @PostMapping(value = "/insert.json")
    public Map<String, Object> insertPOST(@RequestBody Order2 obj) {
        System.out.println(obj.toString());
        Map<String, Object> map = new HashMap<>();
        try{
            //주문 등록 실패시 0 반환
            map.put("status", 0);
            // 1. 주문 테이블
            Order2 ret = o2Repository.save(obj);
            System.out.println(ret.toString());

            if(ret != null){
                System.out.println(ret.getNo());
                // 2. 주문상태 테이블
                Order2Step obj2 = new Order2Step();
                obj2.setOrderstep(1);
                obj2.setOrderno(ret);
                o2sRepository.save(obj2);
                //2개의 테이블이 저장되었다면 200 반환

                map.put("stauts", 200);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            map.put("status", -1);
        }
        
        return map;
    }
    
    //조회하기
    @GetMapping(value = "/selectall.json")
    public Map<String, Object> selectallGET() {
        Map<String, Object> map = new HashMap<>();
        try{
            List<Oreder2View> list = o2vRepository.findAll();
            map.put("stauts", 200);
            map.put("list", list);
        }catch(Exception e){
            System.out.println(e.getMessage());
            map.put("status", -1);
        }
        
        return map;
    }
    
}
