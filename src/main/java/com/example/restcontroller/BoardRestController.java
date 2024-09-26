package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Board;
import com.example.mapper.BoardMapper;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value = "/api/board")
public class BoardRestController {
    
    @Autowired
    BoardMapper bMapper;
    //127.0.0.1:8080/ROOT/api/board/list.json
    
    @GetMapping(value = "/list.json")
    public Map<String, Object> listGET(){
        List<Board>list = bMapper.selectBoardList();    
        Map<String,Object> map = new HashMap<>();
        map.put("result",1);
        map.put("list", list);
        return map;
    }
    
}
    

