package com.example.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Chat2;
import com.example.repository.Chat2Repository;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.*;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping(value = "/api/chat2")
@RequiredArgsConstructor
public class Chat2RestController {
    
    final Chat2Repository c2Repository;

    //메세지 등록 => 추가해던 메시지 번호를 반환기능 포함.
    //127.0.0.1:8080/ROOT/api/chat2/insert.json
    // {"send" : "aaa" , "recv" : "bbb" , "msg" : "msg"}
    @PostMapping(value = "/insert.json")
    public Map<String , Object> selectOneGET(@RequestBody Chat2 obj) {
        Map<String , Object> map = new HashMap<>();
        try{
            Chat2 ret = c2Repository.save(obj);
            map.put("status", 0);
            if (ret != null) {
                map.put("result", ret.getNo());
                map.put("status", 200);
            }
        }catch(Exception e) {
            System.err.println(e.getMessage());
            map.put("status" , -1);
        }
        
        return map;
    }
    //메세지 번호를 전달하면 1개의 메세지를 반환
    @GetMapping(value = "/selectone.json")
    public Map<String , Object> selectOneGET( @RequestParam("no") int no) {
        Map<String , Object> map = new HashMap<>();
        try{
            Chat2 obj = c2Repository.findById(no).orElse(null);
            map.put("result", obj);
            map.put("status", 200);
        }catch(Exception e ){
            System.err.println(e.getMessage());
            map.put("status", -1);
        }
        return map;
    }

    //메세지 조회 >> recv가 일치하고 , no가 큰것만 10개 조회 
    @GetMapping(value = "/select.json")
    public Map<String , Object> selectGET(@RequestParam ("recv")String recv , @RequestParam("no") int no) {
        Map<String , Object> map = new HashMap<>();
        try{
            List<Chat2> list = c2Repository.findByRecvAndNoGreaterThanOrderByNoDesc(recv, no);
            map.put("list", list);
            map.put("status", 200);
        }catch(Exception e ){
            System.err.println(e.getMessage());
            map.put("status", -1);
        }
        return map;
    }
    
}
