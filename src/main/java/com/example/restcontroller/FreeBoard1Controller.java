package com.example.restcontroller;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.FreeBoard1;
import com.example.entity.FreeBoard1Projection;
import com.example.repository.FreeBoard1Repository;

import lombok.RequiredArgsConstructor;

import java.util.*;

@RestController
@RequestMapping(value = "/api/freeboard1")
@RequiredArgsConstructor
public class FreeBoard1Controller {

    final FreeBoard1Repository fb1Repository;

    // 1개 조회(이미지X)
    // 127.0.0.1:8080/ROOT/api/freeboard1/selectone.json?no=7
    @GetMapping(value = "/selectone.json")
    public Map<String, Object> selectOneGET(@RequestParam(name = "no", defaultValue = "0") int no) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("status", 0);
            if (no > 0) {
                FreeBoard1Projection obj = fb1Repository.findByNo(no);
                if (obj != null) {
                    map.put("result", obj);
                    map.put("status", 200);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            map.put("status", -1);
        }
        return map;
    }

    // 변경 (제목, 내용, 작성자)
    // 127.0.0.1:8080/ROOT/api/freeboard1/selectone.json
    // body = {title:"a", "content":"c", "writer":"w", no:7}
    @PutMapping(value = "/update.json")
    public Map<String, Object> updatePUT(@RequestBody FreeBoard1 obj) {
        Map<String, Object> map = new HashMap<>();
        try {
            // 1. no 를 이용해서 기존 내용을 DB 에서 1개 꺼내서 ret에 보관
            FreeBoard1 ret = fb1Repository.findById(obj.getNo()).orElse(null);
            map.put("status", 0);

            // 2. obj의 제목, 내용, 작성자를 ret 에 보관
            if (ret != null) {
                ret.setTitle(obj.getTitle());
                ret.setContent(obj.getContent());
                ret.setWriter(obj.getWriter());

                // 3. 저장소를 이용하여 save(ret)
                fb1Repository.save(ret);
                map.put("status", 200);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            map.put("status", -1);
        }
        return map;
    }

    // 삭제
    @DeleteMapping(value = "/delete.json")
    public Map<String, Object> delete(@RequestBody FreeBoard1 obj) {
        Map<String, Object> map = new HashMap<>();
        try {
            fb1Repository.deleteById(obj.getNo());
            map.put("status", 200);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            map.put("status", -1);
        }
        return map;
    }

    // 다음글
    // 127.0.0.1:8080/ROOT/api/freeboard1/nextpage.json?no=7
    @GetMapping(value = "/nextpage.json")
    public Map<String, Object> nextPageGET(@RequestParam(name = "no", defaultValue = "0") int no) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("next", 0);
            map.put("status", 0);
            if (no > 0) {
                FreeBoard1Projection obj = fb1Repository.findTop1ByNoGreaterThanOrderByNoAsc(no);
                if (obj != null) {
                    map.put("next", obj.getNo());
                    map.put("status", 200);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            map.put("status", -1);
        }
        return map;
    }

    // 이전글
    // 127.0.0.1:8080/ROOT/api/freeboard1/prevpage.json?no=7
    @GetMapping(value = "/prevpage.json")
    public Map<String, Object> prevPageGET(@RequestParam(name = "no", defaultValue = "0") int no) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("prev", 0);
            map.put("status", 0);
            if (no > 0) {
                FreeBoard1Projection obj = fb1Repository.findTop1ByNoLessThanOrderByNoDesc(no);
                if (obj != null) {
                    map.put("prev", obj.getNo());
                    map.put("status", 200);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            map.put("status", -1);
        }
        return map;
    }

    // 127.0.0.1:8080/ROOT/api/freeboard1/updatehit.json?no=34
    // 조회수1증가하기
    @PutMapping(value = "/updatehit.json")
    public Map<String, Object> updateHitPUT(@RequestParam(name = "no", defaultValue = "0") int no) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("status", 0);
            if (no > 0) {
                fb1Repository.updateQueryHit(no);
                map.put("status", 200);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            map.put("status", -1);
        }
        return map;
    }

    // 127.0.0.1:8080/ROOT/api/freeboard1/selectlist.json?page=1&text=검색어
    // 조회하기
    @GetMapping(value = "/selectlist.json")
    public Map<String, Object> selectGET(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "text", defaultValue = "") String text) {
        Map<String, Object> map = new HashMap<>();
        try {
            PageRequest pageRequest = PageRequest.of((page - 1), 10);
            List<FreeBoard1Projection> list = fb1Repository.findByTitleContainingOrderByNoDesc(text, pageRequest);

            long total = fb1Repository.countByTitleContainingOrderByNoDesc(text);
            map.put("status", 200);
            map.put("result", list);
            map.put("total", total);
        } catch (Exception e) {
            map.put("status", -1);
        }
        return map;
    }

    // 127.0.0.1:8080/ROOT/api/freeboard1/insert.json
    // title, content, writer, file정보
    @PostMapping(value = "/insert.json")
    public Map<String, Object> insertPOST(@ModelAttribute FreeBoard1 obj,
            @RequestParam(name = "file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        try {
            System.out.println(obj.toString());
            System.out.println(file.getOriginalFilename());
            obj.setFilename(file.getOriginalFilename());
            obj.setFilesize(file.getSize());
            obj.setFiletype(file.getContentType());
            obj.setFiledata(file.getBytes());
            obj.setHit(1);

            fb1Repository.save(obj);

            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", -1);
        }
        return map;
    }

}