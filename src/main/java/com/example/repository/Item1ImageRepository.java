package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Item1Image;
import com.example.entity.Item1ImageProjection;

@Repository
public interface Item1ImageRepository extends JpaRepository<Item1Image,Integer>{
    
    // 물품에 해당하는 모든 이미지 조회
    // SELECT NO FROM item1image WHERE itemno=#{itemno}
    List<Item1ImageProjection> findByItemno_no(int no);

    // 물품이미지중 대표이미지 1개 조회
    // SELECT no FROM item1image WHERE itemno=#{itemno} ORDER BY no ASC LIMIT 1;
    Item1ImageProjection findTop1ByItemno_noOrderByNoAsc(int no);
}
