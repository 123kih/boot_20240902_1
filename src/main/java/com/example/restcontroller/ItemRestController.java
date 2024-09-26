package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Item;
import com.example.mapper.ItemMapper;

@RestController
@RequestMapping(value = "/api/item")
//127.0.0.1:8080/ROOT/api/item/list.json
public class ItemRestController {
    
    @Autowired
    ItemMapper iMapper;

    @GetMapping(value = "/list.json")
    public Map<String, Object> listGET() {
        Map<String,Object> map = new HashMap<>();
        List<Item> list = iMapper.selectItemOne();

        map.put("list",list);
        return map;
    }
    
}
