package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Order2Step;

@Repository
public interface Order2StepRepository extends JpaRepository<Order2Step,Integer>{
    
    //물품번호를 전달하면 가장 마지막 상태 정보를 읽음
    Order2Step findTop1ByOrderno_noOrderByCodeDesc(int no);

    //주문 1개에 대한 상태 전체 표시
    List<Order2Step> findByOrderno_noOrderByCodeAsc(int no);
}
