

package com.example.restcontroller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Item1;
import com.example.entity.Item1Image;
import com.example.entity.Item1ImageProjection;
import com.example.repository.Item1ImageRepository;
import com.example.repository.Item1Repository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping(value="/api/home1")
@RequiredArgsConstructor
public class Home1RestController {
    
    final Item1Repository item1Repository;
    final Item1ImageRepository item1ImageRepository;
    final ResourceLoader resourceLoader;

    // 127.0.0.1:8080/ROOT/api/home1/itemlist.do
    @GetMapping(value="/itemlist.do")
    public Map<String, Object> getMethodName( HttpServletRequest request ) {
        Map<String, Object> map = new HashMap<>();
        try {
            PageRequest pageRequest = PageRequest.of( 0, 12, Sort.by("name").ascending() );
            // 12개의 물품목록 가져오기(판매순, 베스트, 가격할인, 이름으로 정렬해서 12 )
            Page<Item1> list = item1Repository.findAll(pageRequest);

            for(Item1 obj : list.getContent()) {
                Item1ImageProjection img = item1ImageRepository.findTop1ByItemno_noOrderByNoAsc( obj.getNo() );
                obj.setImageurl( request.getContextPath() + "/api/home1/image?no=0&ts=" + System.currentTimeMillis() );
                if(img != null){
                    obj.setImageurl( request.getContextPath() + "/api/home1/image?no=" + img.getNo() + "&ts=" + System.currentTimeMillis() );
                }
            }
            map.put("status", 200);
            map.put("result", list.getContent());
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
            map.put("status", -1);
        }
        return map;
    }
    
    
    // 127.0.0.1:8080/ROOT/api/home1/image
    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> imagePreview(@RequestParam(name = "no") int no) throws IOException {
        Item1Image obj = item1ImageRepository.findById(no).orElse(null);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<byte[]> response = null;

        // DB에 이미지가 있는 경우
        if (obj != null) {
            if (obj.getFiledata().length > 0) {
                headers.setContentType(MediaType.parseMediaType(obj.getFiletype()));
                response = new ResponseEntity<>(obj.getFiledata(), headers, HttpStatus.OK);
                return response;
            }
        }

        // DB에 이미지가 없는 경우
        InputStream in = resourceLoader.getResource("classpath:/static/img/default.png").getInputStream();
        headers.setContentType(MediaType.IMAGE_PNG);
        response = new ResponseEntity<>(in.readAllBytes(), headers, HttpStatus.OK);
        return response;
    }

}