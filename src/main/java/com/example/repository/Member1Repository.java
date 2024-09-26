package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Member1;

//JpaRepository<연결하려는 엔티티의 클래스명 , 엔티티에서 기본키의 타입 >
@Repository
public interface Member1Repository extends JpaRepository< Member1 , String >{
    
}
