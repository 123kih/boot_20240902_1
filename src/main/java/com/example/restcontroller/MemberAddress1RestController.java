package com.example.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Member1;
import com.example.entity.MemberAddress1;
import com.example.service.MemberAddress1Service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping(value = "/api/memberaddress1")
@RequiredArgsConstructor
public class MemberAddress1RestController {
    
    //여기서 service인터페이스 객체를 생성 구현부를 생성하지 않음(설계도면만 설계함)
    final MemberAddress1Service maService;
    final HttpSession httpSession;


    //삭제
    //127.0.0.1:8080/ROOT/api/memberaddress1/delete.json
    //+토큰
    // @DeleteMapping(value = "/delete.json")
    // public Map<String, Object> deleteOne(@RequestBody MemberAddress1 obj){
    //     Map<String , Object> map = new HashMap<>();
    //     try{    
    //         String userId = (String) httpSession.getAttribute("ID");

    //         maService.deleteAddress(obj.getNo(),obj.get);

    //     }catch(Exception e){
    //         map.put("status", -1);
    //     }
    //     return map; 
    // }

    //조회
    //127.0.0.1:8080/ROOT/api/memberaddress1/selectlist.json
    //+토큰
    @GetMapping(value = "/selectlist.json")
    public Map<String,Object> selectlistOne(){
        Map<String, Object> map = new HashMap<>();
        try{
            String userId = (String) httpSession.getAttribute("ID");
            List<MemberAddress1> list = maService.selectAddressList(userId);
            
            map.put("status", 200);
            map.put("result", list);
        }catch(Exception e){
            map.put("status", -1);
        }
        return map;
    }
    
    //추가
    //127.0.0.1:8080/ROOT/api/memberaddress1/insert.json
    //토큰 + {"postCode":"1" , "address" : "부산" , "address1" : "상세주소"}
    @PostMapping(value = "/insert.json")
    public Map<String,Object> selectOne(@RequestBody MemberAddress1 obj){
        Map<String, Object> map = new HashMap<>();
        try{
            String userId = (String) httpSession.getAttribute("ID");
            
            Member1 m1 = new Member1();
            
            m1.setId(userId);
            obj.setMemberid(m1);

            maService.insertAddress(obj);
            map.put("status", 200);
        }catch(Exception e){
            map.put("status", -1);
        }
        return map;
    }
}
