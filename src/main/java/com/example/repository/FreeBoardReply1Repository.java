package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.FreeBoardReply1;

//jpa하이버네이트
//jpa는 엔티티를 만든 후 저장소(repository)를 만들어야함
//mybatis는 엔티티를 만든 후 mapper인 쿼리문을 작성해야함
@Repository
public interface FreeBoardReply1Repository extends JpaRepository<FreeBoardReply1,Integer>{
    //insert, update , delete , select , selectone
    //나머지는 만들어서 사용해야함.

    //쿼리문(mybatis) : SELECT * FROM freeboardreply1 WHERE bno = #{bno}
    //https://docs.spring.io/spring-data/jpa/docs/2.7.10/reference/html/#jpa.query-methods.query-creation
    List<FreeBoardReply1> findByBno_no(int no);
}
