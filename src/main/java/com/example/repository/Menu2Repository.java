package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Menu2;

@Repository
public interface Menu2Repository extends JpaRepository<Menu2,Integer>{
    
    //SELECT * FROM menu2 WHERE restaurant = ?
    //findByABC_code ==> ABC객체의 변수 code 를 조건
    List<Menu2> findByRestaurant_crNumber(String crNumber);
}
