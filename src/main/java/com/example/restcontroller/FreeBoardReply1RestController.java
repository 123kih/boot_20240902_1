package com.example.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.FreeBoardReply1;
import com.example.repository.FreeBoardReply1Repository;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



//@Controller 는 return타입이 String

@RequiredArgsConstructor
@RestController // return타입이 Map<String,Object>
@RequestMapping(value = "/api/freeboardreply1")
public class FreeBoardReply1RestController {
    
    final FreeBoardReply1Repository fbrRepository;
    // post(DB에 추가) put(변경), delete(삭제) >>>@RequesstBody, @RequestParam 둘다 가능
    //                                          @ModelAttribute(이미지가있음)
    //get(조회) >> @RequestParam 으로 조회
    //@RequestBody = PostMan에서 body, raw, Json으로 바꾸고 send

    //게시글 목록에서 상세화면으로 이동하면 1개의 게시글 표시되고
    //관련 답글이 목록으로 
    @GetMapping(value = "/selectlist.json")
    public Map<String,Object> selectlistGET(@RequestParam (name="bno") int bno) {
        Map<String,Object> map = new HashMap<>();

        try{
            //fbrRepository.findById 
            List<FreeBoardReply1> list = fbrRepository.findByBno_no(bno);
            map.put("status", 200);
            map.put("result", list);
        }catch(Exception e){
            map.put("status", -1);
        }  
        return map;
    
    }
    
    //삭제
    @DeleteMapping(value = "/delete.json")
    public Map<String,Object> deletePOST(@RequestBody FreeBoardReply1 obj){
        Map<String,Object> map = new HashMap<>();

        try{
            fbrRepository.deleteById(obj.getNo());
            map.put("status", 200);
        }catch(Exception e){
            map.put("status", -1);
        }  
        return map;
    }

    //댓글 추가
    @PostMapping(value = "/insert.json")
    public Map<String,Object> insertPOST(@RequestBody FreeBoardReply1 obj) {
        Map<String,Object> map = new HashMap<>();

        try{
            fbrRepository.save(obj);
            System.out.println(obj.toString());
            map.put("status", 200);
        }catch(Exception e){
            map.put("status", -1);
        }  
        return map;
    }
    
}
