package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.MemberAddress1;

//BIGINT는 반환 타입이 Long
@Repository
public interface MemberAddress1Repository extends JpaRepository<MemberAddress1,Long>{
    
    //SELECT * FROM memeberaddress1 WHERE memberid = 'aaa'
    List<MemberAddress1> findByMemberid_id(String id);
}
