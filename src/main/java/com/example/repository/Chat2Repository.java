package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Chat2;
import java.util.*;

@Repository
public interface Chat2Repository extends JpaRepository<Chat2 , Integer>{
    
    // ... WHERE recv = ? AND no > ?
    List<Chat2> findByRecvAndNoGreaterThanOrderByNoDesc(String recv, int no);

    // ... WHERE recv =? ORDER BY no ASC
    List<Chat2> findByRecvOrderByNoDesc(String recv);
}
