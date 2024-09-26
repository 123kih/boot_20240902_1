package com.example.restcontroller;


import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Item1;
import com.example.repository.Item1Repository;


import lombok.RequiredArgsConstructor;


@RestController // react, vue.js , android , ios 처럼 화면구성을 따로 할때 resccontroller사용
@RequestMapping(value = "/api/item1")
@RequiredArgsConstructor

public class Item1ResController {

    final Item1Repository item1Repository;

    //물품 1개 조회
    //127.0.0.1:8080/ROOT/api/item1/selectone.json?no=1
    @GetMapping(value = "/selectone.json")
    public Map<String,Object> selectOneGET (@RequestParam (name="no")int no) {
        Map<String,Object> map = new HashMap<>();
        try{
            Item1 obj = item1Repository.findById(no).orElse(null);
            map.put("status",200);
            map.put("result",obj);
        }
        catch(Exception e){
            map.put("status",-1);
        }
        return map;
    }
    

    // Mapping 종류 : get (조회), post(DB에 추가), delete(삭제) , put(변경)
    
    //판매할 물품을 조회
    //127.0.0.1:8080/ROOT/api/item1/selectlist.json?page=1
    @GetMapping(value = "/selectlist.json")
    public Map<String, Object> selectListGET(@RequestParam(name="page") int page){
        Map<String , Object> map = new HashMap<>();
        
        try{
            //pageRequest.of(페이지번호(0부터) , 페이지개수, 정렬)
            PageRequest pageRequest = PageRequest.of((page-1),10,Sort.by("name").ascending());
            Page<Item1>list = item1Repository.findAll(pageRequest);
            long total = item1Repository.count();

            map.put("status" , 200);
            map.put("result",list.getContent());
            map.put("total", total);
        }
        catch(Exception e){
            map.put("status" , -1);
        }
        return map;
    }
    
    
}
