package com.example.restcontroller;

import java.util.*;

import org.apache.ibatis.annotations.Delete;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Member1;
import com.example.repository.Member1Repository;
import com.example.token.JWTUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping(value = "/api/member1")
@RequiredArgsConstructor
public class Member1RestController {
    
    final Member1Repository member1Repository;
    BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
    final JWTUtil jwtUtil;
    final HttpSession httpSession;

    //정보변경 (로그인완료) => filter 통과 후 => 127.0.0.1:8080/ROOT/api/member1/update.json
    //const body = {name, phone, age } + token(아이디, 권한이 포함되어 있음)
    @PutMapping(value = "update.json")
    public Map<String,Object> updatePUT(
            @RequestBody Member1 obj , 
            @RequestHeader(name="token") String token) {
        Map<String,Object> map = new HashMap<>();
        try{
            String userid = (String) httpSession.getAttribute("ID");
            System.out.println("RestController =>" + userid);

            obj.setId(userid);
            
            //.update 기능이 없음으로 .save기능 사용해야함. insert, update
            Member1 obj1 = member1Repository.findById(userid).orElse(null);
            obj1.setAge(obj.getAge());
            obj1.setName(obj.getName());
            obj1.setPhone(obj.getPhone());

            //obj1을 저장
            member1Repository.save(obj1);
            map.put("status", 200);
        }
        catch(Exception e){
            map.put("status", -1);
        }
        return map;
    }

    //암호변경 (로그인완료) -검증 =>127.0.0.1:8080/ROOT/api/member1/updatepw.json
    //token + {"pw" : "현재암호" , "newpw" : "바꿀암호"} =>entity newpw 변수없음.만들어야됨
    @PutMapping(value = "updatepw.json")
    public Map<String,Object> updatepwPUT(@RequestBody Member1 obj ) {
        Map<String,Object> map = new HashMap<>();
        try{
            System.out.println(obj.toString());
            String userid = (String) httpSession.getAttribute("ID");

            //1. userid를 이용해 기존 정보 가져와서 obj1에 보관
            obj.setId(userid);
            Member1 obj1 = member1Repository.findById(userid).orElse(null);
            
            member1Repository.save(obj1);

            //2. obj의 pw와 obj1의 pw를 비교해서 같으면 변경 아니면 변경안되게
            if(bcpe.matches(obj.getPw(), obj1.getPw()) == true){
                obj1.setPw( bcpe.encode(obj.getNewpw()) );

                //3. 2번이 참이면 obj newpw에 있는 암호를 encode 시켜서 obj1의 pw로 변경하고 저장
                member1Repository.save(obj1);
            }
            map.put("status", 200);
        }
        catch(Exception e){
            map.put("status", -1);
        }
        return map;
    }

    //회원탈퇴 (로그인완료) -검증 =>127.0.0.1:8080/ROOT/api/member1/delete.json
    //토큰 + {"pw" : "현재암호"}
    @DeleteMapping(value = "/delete.json")
    public Map<String , Object> deleteDELETE(@RequestBody Member1 obj){
        Map<String,Object> map = new HashMap<>();
        try{
            String userid = (String) httpSession.getAttribute("ID");
            System.out.println("RestController => "+userid);

            //1. 아이디를 이용해 기본정보 읽음 obj1에 저장
            Member1 obj1 = member1Repository.findById(userid).orElse(null);

            //2.obj의 pw와 obj1의 pw비교
            if(bcpe.matches(obj.getPw(), obj1.getPw()) == true){
                //3.2번이 참이면 삭제
                member1Repository.deleteById(userid);
            }
            map.put("status", 200);
        }
        catch(Exception e){
            map.put("status", -1);
        }
    return map;
    }


    //로그인(post) =>127.0.0.1:8080/ROOT/api/member1/login.json
    //const body = {id:'c1' , 'pw':'c1'}
    @PostMapping(value = "/login.json")
    public Map<String , Object> loginPOST(@RequestBody Member1 obj) {
        Map<String, Object> map = new HashMap<>();
        try{
            Member1 ret=member1Repository.findById(obj.getId()).orElse(null);
            map.put("status",0);
            if(ret != null){
                //matches (암호 전 문자, 암호 후 문자)
                if(bcpe.matches(obj.getPw(), ret.getPw())){
                    //로그인 가능 시점
                    //토큰에 보관하고자 하는 내용들 .. ex) 아이디 , 권한 등...
                    Map<String, Object> tokenData = new HashMap<>();
                    tokenData.put("ID", ret.getId());
                    tokenData.put("ROLE", ret.getRole());

                    String tokenString = jwtUtil.createToken(tokenData);
                    map.put("status", 200);
                    map.put("result", tokenString);
                }
            }
        }
        catch(Exception e){
            map.put("status", -1);
        }
        return map;
    }
    

    //회원가입 (post)
    //127.0.0.1:8080/ROOT/api/member1/join.json
    //body = { id:'aa', pw:'pw', name, phone, age }
    @PostMapping(value = "/join.json")
    public Map<String,Object> joinPOST(@RequestBody Member1 obj) {
        Map<String,Object> map = new HashMap<>();

        try{
            //암호를 hash하기
        
            obj.setPw( bcpe.encode(obj.getPw()) );
            obj.setRole("CUSTOMER");

            Member1 ret = member1Repository.save(obj);
            if(ret != null){
                System.out.println(obj.toString());
                map.put("status",200);
            }
            else{
                map.put("status",0);
            }
        }
        catch(Exception e){
            map.put("status",-1);
        }
        return map;
    }
    
}
