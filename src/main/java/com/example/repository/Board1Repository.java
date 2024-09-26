package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Board1;

@Repository
//테이블 > 엔티티 > 저장소 
//JpaRepository<엔티티클래스명,엔티티의 기본타입> (int >>Integer , long >> Long)
public interface Board1Repository extends JpaRepository<Board1,Integer>{
    
}
