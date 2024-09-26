package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.Item;
import com.example.entity.Item1;
import com.example.entity.Item1Image;
import com.example.entity.Item1ImageProjection;
import com.example.entity.ItemImage;
import com.example.entity.Member1;
import com.example.mapper.ItemImageMapper;
import com.example.mapper.ItemMapper;
import com.example.repository.Item1ImageRepository;
import com.example.repository.Item1Repository;

import lombok.RequiredArgsConstructor;






@Controller
@RequestMapping("/seller")
@RequiredArgsConstructor

public class SellerController {
    
    final ItemMapper iMapper;
    final ItemImageMapper iiMapper;
    final ResourceLoader resourceLoader;
    final Item1Repository item1Repository;
    final Item1ImageRepository item1ImageRepository;

    //<img th:src="/ROOT/seller/image?no=1"/>
    @GetMapping(value = "/image")
    public ResponseEntity<byte[]> imagePreview(@RequestParam(name="no") int no) throws IOException {
        Item1Image obj = item1ImageRepository.findById(no).orElse(null);
        HttpHeaders headers= new HttpHeaders();
        ResponseEntity<byte[]> response = null;

        //DB에 이미지가 있는경우
        if(obj!= null){
            if(obj.getFiledata().length > 0 ){
                headers.setContentType(MediaType.parseMediaType(obj.getFiletype()));
                response = new ResponseEntity<>(obj.getFiledata(), headers , HttpStatus.OK);
                return response;
            }
        }

        InputStream in= resourceLoader.getResource("classpath:/static/img/default.png").getInputStream();
        headers.setContentType(MediaType.IMAGE_PNG);
        response = new ResponseEntity<>(in.readAllBytes(), headers , HttpStatus.OK);
        return response;
    }

    @PostMapping(value = "/item1imageinsertone.do")
    public String item1imagePOST(
        Model model,
        @ModelAttribute Item1Image obj, @RequestParam(name="file") MultipartFile file) throws IOException{

        System.out.println(obj.getItemno().getNo());
        System.out.println((file.getOriginalFilename()));

        obj.setFilename(file.getOriginalFilename());
        obj.setFiletype(file.getContentType());
        obj.setFilesize(file.getSize());
        obj.setFiledata(file.getBytes());

        item1ImageRepository.save(obj);

        model.addAttribute("msg", "파일이등록되었습니다.");
        model.addAttribute("url", "/seller/item1imageinsert.do?no="+obj.getItemno().getNo());

        return "alert";
    }

    // 127.0.0.1:8080/ROOT/seller/iteminsert.do
    @GetMapping(value = "/item1imageinsert.do")
    public String insertGET(@RequestParam(name="no",defaultValue = "0") int no, Model model) {
        
        //Item1Image에서 itemno가 일치하는 모든 이미지 번호 가져오기
        if(no==0){
            //물품번호 없으면 목록으로 이동시킴
            return "redirect:/seller/home.do?menu=1";
        }
        List<Item1ImageProjection> list = item1ImageRepository.findByItemno_no(no);
        model.addAttribute("list",list);
        return "item1image/insert";
    }


    @PostMapping(value = "/item1insert.do")
    public String iteminsertPOST(@ModelAttribute Item1 obj , @AuthenticationPrincipal User user) {
        
        if(user != null){
        System.out.println(obj.toString());
        
        //외래키 Member1 객체생성
        Member1 sid = new Member1();
        //sid 객체에 아이디 저장
        sid.setId(user.getUsername());
        //obj에 외래키 sid 객체 저장
        obj.setSid(sid);

        item1Repository.save(obj);
        return "redirect:/seller/home.do?menu=1";
    }
    return "redirect:/login.do";
}
    
    @GetMapping("/home.do")
    public String homeGET(
                            @RequestParam(name="menu" , defaultValue = "0") int menu,
                            @AuthenticationPrincipal User user , Model model) {
        
        if(menu==0){
            return "redirect:/seller/home.do?menu=1";
        }

        if(user != null){
        String id = user.getUsername();
        
        List<Item1> list= item1Repository.findBySid_id(id);
        model.addAttribute("list", list); // 등록된 물품 정보 불러오기
        model.addAttribute("user", user); // 로그인 사용자 정보 불러오기
        return "seller_home";
    }
    //로그인 안되있으면
    return "redirect:/login.do";
    }
    
    @PostMapping(value = "/itemimageupdate.do")
    public String itemImageUpdatePOST(@ModelAttribute ItemImage obj, 
                                        @RequestParam(name="file")MultipartFile file) throws IOException {
        
        obj.setFilename(file.getOriginalFilename());
        obj.setFilesize((int)file.getSize());
        obj.setFiletype(file.getContentType());
        obj.setFiledata(file.getBytes());

        iiMapper.updateItemImageOne(obj);

        return "redirect:/seller/itemimageinsert.do?itemno="+obj.getItemno();
    }
    

    //값을 하나만 받을거면 RequestParam 사용해도되고 많다면 @ModelAttribute 사용
    @PostMapping(value = "/itemimagedelete.do")
    public String imtemImageDeletePOST(@ModelAttribute ItemImage obj) {
        iiMapper.deleteItemImageOne(obj.getNo());

        return "redirect:/seller/itemimageinsert.do?itemno="+obj.getItemno();
    }
    

    //String html파일을 표시 주소변경
    //ResponseEntity<byte[]> 이미지, 동영상
    //127.0.0.1:8080/ROOT/seller/image?no=1
    
    //<img th:src="@{/seller/image(no=1)}"/> 형식으로 html에 표시할거기 때문에 .do로 표현 안함
    // @GetMapping(value = "/image")
    // public ResponseEntity<byte[]> imagePreview(@RequestParam(name="no") int no) throws IOException {
    //     ItemImage obj = iiMapper.selecItemImage(no);
    //     HttpHeaders headers= new HttpHeaders();
    //     ResponseEntity<byte[]> response = null;

    //     if(obj!= null){
    //         if(obj.getFiledata().length > 0 ){
    //             headers.setContentType(MediaType.parseMediaType(obj.getFiletype()));
    //             response = new ResponseEntity<>(obj.getFiledata(), headers , HttpStatus.OK);
    //             return response;
    //         }
    //     }

    //     InputStream in= resourceLoader.getResource("classpath:/static/img/default.png").getInputStream();
    //     headers.setContentType(MediaType.IMAGE_PNG);
    //     response = new ResponseEntity<>(in.readAllBytes(), headers , HttpStatus.OK);
    //     return response;
    // }
    

    @PostMapping(value = "/itemimageinsert.do")
    public String itemImagePOST(@ModelAttribute ItemImage obj , @RequestParam(name="file") MultipartFile file) 
    throws IOException {
        obj.setFilename(file.getOriginalFilename());
        obj.setFilesize((int)file.getSize()); //filesize는 long타입
        obj.setFiletype(file.getContentType());
        obj.setFiledata(file.getBytes()); //IOException 발생하니 오류 처리해야함
        System.out.println(obj.toString());

        iiMapper.insertItemImageOne(obj);
        return "redirect:/seller/itemlist.do";
    }
    

    @GetMapping(value = "/itemimageinsert.do")
    public String itemImageInsertGET(@RequestParam(name = "itemno" )int no , Model model) {
        List<ItemImage> list = iiMapper.selectItemImageNo(no);
        System.out.println(list.toString());
        model.addAttribute("list", list);
        
        return "itemimageinsert";
    }
    
    // 127.0.0.1:8080/ROOT/seller/iteminsert.do
    @GetMapping(value = "/iteminsert.do")
    public String insertGET() {

        return "iteminsert";
    }

    @PostMapping(value = "/iteminsert.do")
    public String insertPOST(@ModelAttribute Item obj) {
        System.out.println(obj.toString());
        
        iMapper.insertItemOne(obj);

        return "redirect:/seller/itemlist.do";
        
    }
    

    // 127.0.0.1:8080/ROOT/seller/itemlist.do
    @GetMapping(value = "/itemlist.do")
    public String listGET(Model model) {
        List<Item> list= iMapper.selectItemOne();

        model.addAttribute("list",list);

        return "itemlist";
    }
    
}
