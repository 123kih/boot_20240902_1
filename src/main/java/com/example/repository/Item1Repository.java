package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Item1;

@Repository
public interface Item1Repository extends JpaRepository<Item1,Integer>{
    
    // sid가 Member1 객체이기 때문에 Member1엔티티의 기본키 변수인 id까지 표시
    // ex) findBySid_id 
    // mybatis 로 작성 : SELECET * FROM item1 WHERE sid=#{sid};

    // jpa 로 작성
    List<Item1> findBySid_id(String id);
    
}
