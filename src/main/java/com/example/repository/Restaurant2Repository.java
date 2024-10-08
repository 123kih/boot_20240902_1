package com.example.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Restaurant2;
import java.util.*;

@Repository
public interface Restaurant2Repository extends JpaRepository<Restaurant2,String>{

    //목록
    List<Restaurant2> findByNameContainingOrderByNameAsc(String name, Pageable pageable);

    //페이지네이션에서 사용할 전체 개수( 상호명 검색 가능 )
    long countByNameContaining(String name);
}
